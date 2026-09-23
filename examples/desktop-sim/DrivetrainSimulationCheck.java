import java.util.ArrayList;
import java.util.List;

public final class DrivetrainSimulationCheck {
  private static final double DT_SECONDS = 0.020;
  private static final double MAX_SPEED_METERS_PER_SECOND = 3.0;
  private static final double TRACK_WIDTH_METERS = 0.60;
  private static final double RESPONSE_TIME_SECONDS = 0.25;
  private static final double TOLERANCE = 1e-9;

  enum Fault {
    NONE,
    RIGHT_ENCODER_REVERSED,
    HEADING_FROZEN
  }

  record DriveRequest(double left, double right) {}

  record ModelState(
      double leftVelocity,
      double rightVelocity,
      double leftDistance,
      double rightDistance,
      double headingRadians) {}

  record SensorSnapshot(
      double leftDistance,
      double rightDistance,
      double headingRadians,
      Fault activeFault) {}

  record ScenarioResult(ModelState truth, SensorSnapshot sensors) {}

  static final class DifferentialDriveModel {
    private double leftVelocity;
    private double rightVelocity;
    private double leftDistance;
    private double rightDistance;
    private double headingRadians;
    private final double initialHeadingRadians;

    DifferentialDriveModel() {
      initialHeadingRadians = headingRadians;
    }

    void step(DriveRequest request) {
      double leftTarget = clamp(request.left()) * MAX_SPEED_METERS_PER_SECOND;
      double rightTarget = clamp(request.right()) * MAX_SPEED_METERS_PER_SECOND;
      double response = Math.min(1.0, DT_SECONDS / RESPONSE_TIME_SECONDS);

      leftVelocity += (leftTarget - leftVelocity) * response;
      rightVelocity += (rightTarget - rightVelocity) * response;
      leftDistance += leftVelocity * DT_SECONDS;
      rightDistance += rightVelocity * DT_SECONDS;
      headingRadians +=
          ((rightVelocity - leftVelocity) / TRACK_WIDTH_METERS) * DT_SECONDS;
    }

    ModelState truth() {
      return new ModelState(
          leftVelocity, rightVelocity, leftDistance, rightDistance, headingRadians);
    }

    SensorSnapshot observe(Fault fault) {
      double reportedRightDistance =
          fault == Fault.RIGHT_ENCODER_REVERSED ? -rightDistance : rightDistance;
      double reportedHeading =
          fault == Fault.HEADING_FROZEN ? initialHeadingRadians : headingRadians;
      return new SensorSnapshot(leftDistance, reportedRightDistance, reportedHeading, fault);
    }

    private static double clamp(double value) {
      return Math.max(-1.0, Math.min(1.0, value));
    }
  }

  static ScenarioResult run(int steps, DriveRequest request, Fault fault) {
    DifferentialDriveModel model = new DifferentialDriveModel();
    for (int step = 0; step < steps; step++) {
      model.step(request);
    }
    return new ScenarioResult(model.truth(), model.observe(fault));
  }

  public static void main(String[] args) {
    List<String> failures = new ArrayList<>();

    ScenarioResult straight = run(100, new DriveRequest(0.6, 0.6), Fault.NONE);
    check(failures, "straight left distance is positive", straight.truth().leftDistance() > 0.0);
    check(failures, "straight right distance is positive", straight.truth().rightDistance() > 0.0);
    checkNear(
        failures,
        "straight distances agree",
        straight.truth().leftDistance(),
        straight.truth().rightDistance());
    checkNear(failures, "straight heading remains zero", straight.truth().headingRadians(), 0.0);

    ScenarioResult turning = run(100, new DriveRequest(0.6, -0.6), Fault.NONE);
    check(
        failures,
        "opposite requests create rotation",
        Math.abs(turning.truth().headingRadians()) > 1.0);
    check(
        failures,
        "opposite requests produce opposite distances",
        turning.truth().leftDistance() * turning.truth().rightDistance() < 0.0);

    ScenarioResult repeated = run(100, new DriveRequest(0.6, -0.6), Fault.NONE);
    checkState(failures, "scenario is deterministic", turning.truth(), repeated.truth());

    ScenarioResult reversed =
        run(100, new DriveRequest(0.6, 0.6), Fault.RIGHT_ENCODER_REVERSED);
    check(
        failures,
        "reversed encoder preserves positive model truth",
        reversed.truth().rightDistance() > 0.0);
    checkNear(
        failures,
        "reversed encoder reports opposite sign",
        reversed.sensors().rightDistance(),
        -reversed.truth().rightDistance());

    ScenarioResult frozen =
        run(100, new DriveRequest(0.6, -0.6), Fault.HEADING_FROZEN);
    check(
        failures,
        "frozen heading preserves modeled rotation",
        Math.abs(frozen.truth().headingRadians()) > 1.0);
    checkNear(failures, "frozen heading reports initial value", frozen.sensors().headingRadians(), 0.0);

    DifferentialDriveModel coast = new DifferentialDriveModel();
    for (int step = 0; step < 50; step++) {
      coast.step(new DriveRequest(0.8, 0.8));
    }
    double movingVelocity = coast.truth().leftVelocity();
    for (int step = 0; step < 50; step++) {
      coast.step(new DriveRequest(0.0, 0.0));
    }
    check(
        failures,
        "neutral request decelerates the model",
        Math.abs(coast.truth().leftVelocity()) < Math.abs(movingVelocity));

    ScenarioResult bounded = run(100, new DriveRequest(4.0, 4.0), Fault.NONE);
    check(
        failures,
        "requests are bounded to maximum speed",
        bounded.truth().leftVelocity() <= MAX_SPEED_METERS_PER_SECOND);

    if (!failures.isEmpty()) {
      failures.forEach(failure -> System.err.println("FAIL " + failure));
      throw new AssertionError(failures.size() + " simulation checks failed");
    }

    System.out.println("PASS: 12 deterministic drivetrain simulation checks");
    System.out.printf(
        "Baseline after 2.00 s: left=%.3f m right=%.3f m heading=%.3f rad%n",
        straight.truth().leftDistance(),
        straight.truth().rightDistance(),
        straight.truth().headingRadians());
  }

  private static void check(List<String> failures, String name, boolean condition) {
    if (!condition) {
      failures.add(name);
    }
  }

  private static void checkNear(List<String> failures, String name, double actual, double expected) {
    check(failures, name + " expected=" + expected + " actual=" + actual, Math.abs(actual - expected) <= TOLERANCE);
  }

  private static void checkState(
      List<String> failures, String name, ModelState actual, ModelState expected) {
    checkNear(failures, name + " left velocity", actual.leftVelocity(), expected.leftVelocity());
    checkNear(failures, name + " right velocity", actual.rightVelocity(), expected.rightVelocity());
    checkNear(failures, name + " left distance", actual.leftDistance(), expected.leftDistance());
    checkNear(failures, name + " right distance", actual.rightDistance(), expected.rightDistance());
    checkNear(failures, name + " heading", actual.headingRadians(), expected.headingRadians());
  }
}
