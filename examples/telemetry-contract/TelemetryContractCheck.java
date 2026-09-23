import java.util.Objects;

public final class TelemetryContractCheck {
  private static final long MAX_AGE_MICROS = 100_000;

  record Observation(
      double value,
      long observedAtMicros,
      boolean present,
      boolean valid,
      boolean authorized) {}

  record Decision(double request, boolean accepted, String reason) {
    Decision {
      Objects.requireNonNull(reason);
    }
  }

  static Decision decide(Observation observation, long nowMicros) {
    if (!observation.present()) {
      return neutral("missing observation");
    }
    if (!observation.valid() || !Double.isFinite(observation.value())) {
      return neutral("invalid observation");
    }
    if (!observation.authorized()) {
      return neutral("unauthorized producer");
    }

    long ageMicros = nowMicros - observation.observedAtMicros();
    if (ageMicros < 0) {
      return neutral("future observation time");
    }
    if (ageMicros > MAX_AGE_MICROS) {
      return neutral("stale observation");
    }

    return new Decision(clamp(observation.value()), true, "accepted observation");
  }

  private static Decision neutral(String reason) {
    return new Decision(0.0, false, reason);
  }

  private static double clamp(double value) {
    return Math.max(-1.0, Math.min(1.0, value));
  }

  private static void expect(
      String name, Decision actual, double request, boolean accepted, String reason) {
    if (Double.compare(actual.request(), request) != 0
        || actual.accepted() != accepted
        || !actual.reason().equals(reason)) {
      throw new AssertionError(name + " failed: " + actual);
    }
    System.out.println("PASS " + name + " -> " + actual.reason());
  }

  public static void main(String[] args) {
    long now = 1_000_000;

    expect("fresh boundary", decide(new Observation(0.6, now - MAX_AGE_MICROS, true, true, true), now), 0.6, true, "accepted observation");
    expect("stale", decide(new Observation(0.6, now - MAX_AGE_MICROS - 1, true, true, true), now), 0.0, false, "stale observation");
    expect("missing", decide(new Observation(0.6, now, false, true, true), now), 0.0, false, "missing observation");
    expect("invalid", decide(new Observation(0.6, now, true, false, true), now), 0.0, false, "invalid observation");
    expect("not finite", decide(new Observation(Double.NaN, now, true, true, true), now), 0.0, false, "invalid observation");
    expect("unauthorized", decide(new Observation(0.6, now, true, true, false), now), 0.0, false, "unauthorized producer");
    expect("future timestamp", decide(new Observation(0.6, now + 1, true, true, true), now), 0.0, false, "future observation time");
    expect("bounded request", decide(new Observation(4.0, now, true, true, true), now), 1.0, true, "accepted observation");
  }
}
