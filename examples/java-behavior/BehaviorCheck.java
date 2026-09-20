import java.util.ArrayList;
import java.util.List;

public final class BehaviorCheck {
  private static final double DEADBAND = 0.08;
  private static final double OUTPUT_SCALE = 0.75;
  private static final long MAX_INPUT_AGE_MS = 100;

  enum Reason {
    READY,
    DISABLED,
    WRONG_MODE,
    INVALID_INPUT,
    STALE_INPUT,
    DEADBAND
  }

  record DriveInput(
      boolean enabled,
      boolean teleop,
      double forward,
      double turn,
      long sampleAgeMs) {}

  record DriveRequest(double left, double right) {
    static DriveRequest neutral() {
      return new DriveRequest(0.0, 0.0);
    }
  }

  record DriveDecision(DriveRequest request, Reason reason) {}

  static final class DriveController {
    DriveDecision decide(DriveInput input) {
      if (!input.enabled()) {
        return neutral(Reason.DISABLED);
      }

      if (!input.teleop()) {
        return neutral(Reason.WRONG_MODE);
      }

      if (!Double.isFinite(input.forward())
          || !Double.isFinite(input.turn())
          || input.sampleAgeMs() < 0) {
        return neutral(Reason.INVALID_INPUT);
      }

      if (input.sampleAgeMs() > MAX_INPUT_AGE_MS) {
        return neutral(Reason.STALE_INPUT);
      }

      double forward = applyDeadband(input.forward());
      double turn = applyDeadband(input.turn());

      if (forward == 0.0 && turn == 0.0) {
        return neutral(Reason.DEADBAND);
      }

      double left = clamp((forward + turn) * OUTPUT_SCALE);
      double right = clamp((forward - turn) * OUTPUT_SCALE);
      return new DriveDecision(new DriveRequest(left, right), Reason.READY);
    }

    private static DriveDecision neutral(Reason reason) {
      return new DriveDecision(DriveRequest.neutral(), reason);
    }

    private static double applyDeadband(double value) {
      return Math.abs(value) <= DEADBAND ? 0.0 : clamp(value);
    }

    private static double clamp(double value) {
      return Math.max(-1.0, Math.min(1.0, value));
    }
  }

  public static void main(String[] args) {
    DriveController controller = new DriveController();
    List<String> failures = new ArrayList<>();

    checkDecision(
        failures,
        "disabled input is neutral",
        controller.decide(new DriveInput(false, true, 1.0, 0.0, 0)),
        new DriveRequest(0.0, 0.0),
        Reason.DISABLED);

    checkDecision(
        failures,
        "non-teleop input is neutral",
        controller.decide(new DriveInput(true, false, 1.0, 0.0, 0)),
        new DriveRequest(0.0, 0.0),
        Reason.WRONG_MODE);

    checkDecision(
        failures,
        "negative sample age is invalid",
        controller.decide(new DriveInput(true, true, 1.0, 0.0, -1)),
        new DriveRequest(0.0, 0.0),
        Reason.INVALID_INPUT);

    checkDecision(
        failures,
        "non-finite input is invalid",
        controller.decide(new DriveInput(true, true, Double.NaN, 0.0, 0)),
        new DriveRequest(0.0, 0.0),
        Reason.INVALID_INPUT);

    checkDecision(
        failures,
        "101 ms input is stale",
        controller.decide(new DriveInput(true, true, 1.0, 0.0, 101)),
        new DriveRequest(0.0, 0.0),
        Reason.STALE_INPUT);

    checkDecision(
        failures,
        "100 ms boundary is accepted",
        controller.decide(new DriveInput(true, true, 1.0, 0.0, 100)),
        new DriveRequest(0.75, 0.75),
        Reason.READY);

    checkDecision(
        failures,
        "deadband boundary is neutral",
        controller.decide(new DriveInput(true, true, 0.08, -0.08, 0)),
        new DriveRequest(0.0, 0.0),
        Reason.DEADBAND);

    checkDecision(
        failures,
        "forward request is scaled",
        controller.decide(new DriveInput(true, true, 1.0, 0.0, 0)),
        new DriveRequest(0.75, 0.75),
        Reason.READY);

    checkDecision(
        failures,
        "mixed request is bounded",
        controller.decide(new DriveInput(true, true, 1.0, 1.0, 0)),
        new DriveRequest(1.0, 0.0),
        Reason.READY);

    if (!failures.isEmpty()) {
      failures.forEach(message -> System.err.println("FAIL: " + message));
      System.exit(1);
    }

    System.out.println("PASS: 9 Java behavior checks");
  }

  private static void checkDecision(
      List<String> failures,
      String name,
      DriveDecision actual,
      DriveRequest expectedRequest,
      Reason expectedReason) {
    if (!expectedRequest.equals(actual.request()) || expectedReason != actual.reason()) {
      failures.add(
          name
              + " expected="
              + new DriveDecision(expectedRequest, expectedReason)
              + " actual="
              + actual);
    }
  }
}
