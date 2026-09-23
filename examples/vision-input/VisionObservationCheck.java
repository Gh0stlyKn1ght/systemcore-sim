import java.util.ArrayList;
import java.util.List;

public final class VisionObservationCheck {
  private static final long MAX_AGE_MICROS = 100_000;
  private static final double MAX_AMBIGUITY = 0.20;
  private static final double MAX_DISTANCE_METERS = 8.0;
  private static final double AIM_KP = 0.8;
  private static final double MAX_CORRECTION = 0.45;

  enum Decision {
    ACCEPTED,
    NO_TARGET,
    INVALID_VALUE,
    UNAUTHORIZED,
    FUTURE_TIMESTAMP,
    STALE,
    LOW_QUALITY,
    OUT_OF_RANGE
  }

  record VisionObservation(
      boolean targetPresent,
      double yawRadians,
      double distanceMeters,
      long capturedAtMicros,
      double ambiguity,
      int tagCount,
      boolean sourceAuthorized) {}

  record AimResult(Decision decision, double correction, long ageMicros) {
    boolean accepted() {
      return decision == Decision.ACCEPTED;
    }
  }

  static AimResult evaluate(VisionObservation observation, long nowMicros) {
    if (observation == null || !observation.targetPresent()) {
      return rejected(Decision.NO_TARGET);
    }
    if (!Double.isFinite(observation.yawRadians())
        || !Double.isFinite(observation.distanceMeters())
        || !Double.isFinite(observation.ambiguity())
        || observation.ambiguity() < 0.0
        || observation.tagCount() < 1) {
      return rejected(Decision.INVALID_VALUE);
    }
    if (!observation.sourceAuthorized()) {
      return rejected(Decision.UNAUTHORIZED);
    }

    long ageMicros = nowMicros - observation.capturedAtMicros();
    if (ageMicros < 0) {
      return new AimResult(Decision.FUTURE_TIMESTAMP, 0.0, ageMicros);
    }
    if (ageMicros > MAX_AGE_MICROS) {
      return new AimResult(Decision.STALE, 0.0, ageMicros);
    }
    if (observation.ambiguity() > MAX_AMBIGUITY) {
      return new AimResult(Decision.LOW_QUALITY, 0.0, ageMicros);
    }
    if (observation.distanceMeters() < 0.0
        || observation.distanceMeters() > MAX_DISTANCE_METERS) {
      return new AimResult(Decision.OUT_OF_RANGE, 0.0, ageMicros);
    }

    double correction = clamp(observation.yawRadians() * AIM_KP, -MAX_CORRECTION, MAX_CORRECTION);
    return new AimResult(Decision.ACCEPTED, correction, ageMicros);
  }

  private static AimResult rejected(Decision decision) {
    return new AimResult(decision, 0.0, -1);
  }

  private static double clamp(double value, double minimum, double maximum) {
    return Math.max(minimum, Math.min(maximum, value));
  }

  public static void main(String[] args) {
    List<String> failures = new ArrayList<>();
    long now = 1_000_000;

    expect(failures, "fresh target", evaluate(observation(now - 25_000), now), Decision.ACCEPTED, 0.08);
    expect(failures, "missing target", evaluate(new VisionObservation(false, 0.1, 2.0, now, 0.05, 1, true), now), Decision.NO_TARGET, 0.0);
    expect(failures, "non-finite yaw", evaluate(new VisionObservation(true, Double.NaN, 2.0, now, 0.05, 1, true), now), Decision.INVALID_VALUE, 0.0);
    expect(failures, "unauthorized source", evaluate(new VisionObservation(true, 0.1, 2.0, now, 0.05, 1, false), now), Decision.UNAUTHORIZED, 0.0);
    expect(failures, "future timestamp", evaluate(observation(now + 1), now), Decision.FUTURE_TIMESTAMP, 0.0);
    expect(failures, "stale target", evaluate(observation(now - MAX_AGE_MICROS - 1), now), Decision.STALE, 0.0);
    expect(failures, "high ambiguity", evaluate(new VisionObservation(true, 0.1, 2.0, now, 0.21, 1, true), now), Decision.LOW_QUALITY, 0.0);
    expect(failures, "impossible distance", evaluate(new VisionObservation(true, 0.1, -0.1, now, 0.05, 1, true), now), Decision.OUT_OF_RANGE, 0.0);
    expect(failures, "zero tags", evaluate(new VisionObservation(true, 0.1, 2.0, now, 0.05, 0, true), now), Decision.INVALID_VALUE, 0.0);
    expect(failures, "positive clamp", evaluate(new VisionObservation(true, 2.0, 2.0, now, 0.05, 2, true), now), Decision.ACCEPTED, MAX_CORRECTION);
    expect(failures, "negative clamp", evaluate(new VisionObservation(true, -2.0, 2.0, now, 0.05, 2, true), now), Decision.ACCEPTED, -MAX_CORRECTION);

    if (!failures.isEmpty()) {
      failures.forEach(failure -> System.err.println("FAIL " + failure));
      throw new AssertionError(failures.size() + " vision checks failed");
    }
    System.out.println("PASS: 11 vision freshness, quality, authority, and correction checks");
  }

  private static VisionObservation observation(long capturedAtMicros) {
    return new VisionObservation(true, 0.1, 2.0, capturedAtMicros, 0.05, 1, true);
  }

  private static void expect(
      List<String> failures,
      String name,
      AimResult actual,
      Decision expectedDecision,
      double expectedCorrection) {
    if (actual.decision() != expectedDecision
        || Math.abs(actual.correction() - expectedCorrection) > 1e-9) {
      failures.add(name + " expected=" + expectedDecision + "/" + expectedCorrection + " actual=" + actual);
    }
    if (!actual.accepted() && actual.correction() != 0.0) {
      failures.add(name + " rejected observation produced a non-neutral correction");
    }
  }
}
