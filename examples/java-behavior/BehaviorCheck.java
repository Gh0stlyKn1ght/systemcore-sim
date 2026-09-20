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

  enum OperatingMode {
    NONE,
    AUTONOMOUS,
    TELEOP,
    UTILITY
  }

  enum StationState {
    RADIO_UNREACHABLE,
    ROBOT_UNREACHABLE,
    COMMUNICATIONS_INCOMPLETE,
    ROBOT_CODE_NOT_RUNNING,
    GAMEPAD_MISSING,
    DISABLED,
    WRONG_MODE,
    READY
  }

  enum DeviceState {
    TOPOLOGY_UNVERIFIED,
    TERMINATION_UNVERIFIED,
    DUPLICATE_ADDRESS,
    DEVICE_NOT_VISIBLE,
    FIRMWARE_MISMATCH,
    VENDOR_DEPENDENCY_MISSING,
    API_MISMATCH,
    READY
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

  record StationSnapshot(
      boolean radioReachable,
      boolean robotReachable,
      boolean udpConnected,
      boolean tcpConnected,
      boolean robotCodeRunning,
      boolean gamepadConnected,
      boolean enabled,
      OperatingMode mode) {}

  record DeviceAddress(String bus, String manufacturer, String deviceType, int deviceNumber) {}

  record DeviceSnapshot(
      boolean topologyVerified,
      boolean terminationVerified,
      boolean addressUnique,
      boolean visibleInConfigurator,
      boolean firmwareCompatible,
      boolean vendorDependencyInstalled,
      boolean apiCompatible) {}

  static final class StationAnalyzer {
    StationState analyze(StationSnapshot snapshot) {
      if (!snapshot.radioReachable()) {
        return StationState.RADIO_UNREACHABLE;
      }

      if (!snapshot.robotReachable()) {
        return StationState.ROBOT_UNREACHABLE;
      }

      if (!snapshot.udpConnected() || !snapshot.tcpConnected()) {
        return StationState.COMMUNICATIONS_INCOMPLETE;
      }

      if (!snapshot.robotCodeRunning()) {
        return StationState.ROBOT_CODE_NOT_RUNNING;
      }

      if (!snapshot.gamepadConnected()) {
        return StationState.GAMEPAD_MISSING;
      }

      if (!snapshot.enabled()) {
        return StationState.DISABLED;
      }

      if (snapshot.mode() != OperatingMode.TELEOP) {
        return StationState.WRONG_MODE;
      }

      return StationState.READY;
    }
  }

  static final class DeviceAnalyzer {
    DeviceState analyze(DeviceSnapshot snapshot) {
      if (!snapshot.topologyVerified()) {
        return DeviceState.TOPOLOGY_UNVERIFIED;
      }

      if (!snapshot.terminationVerified()) {
        return DeviceState.TERMINATION_UNVERIFIED;
      }

      if (!snapshot.addressUnique()) {
        return DeviceState.DUPLICATE_ADDRESS;
      }

      if (!snapshot.visibleInConfigurator()) {
        return DeviceState.DEVICE_NOT_VISIBLE;
      }

      if (!snapshot.firmwareCompatible()) {
        return DeviceState.FIRMWARE_MISMATCH;
      }

      if (!snapshot.vendorDependencyInstalled()) {
        return DeviceState.VENDOR_DEPENDENCY_MISSING;
      }

      if (!snapshot.apiCompatible()) {
        return DeviceState.API_MISMATCH;
      }

      return DeviceState.READY;
    }
  }

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
    StationAnalyzer stationAnalyzer = new StationAnalyzer();
    DeviceAnalyzer deviceAnalyzer = new DeviceAnalyzer();
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

    StationSnapshot readyStation =
        new StationSnapshot(true, true, true, true, true, true, true, OperatingMode.TELEOP);

    checkStation(
        failures,
        "radio reachability is checked first",
        stationAnalyzer.analyze(
            new StationSnapshot(false, false, false, false, false, false, false, OperatingMode.NONE)),
        StationState.RADIO_UNREACHABLE);

    checkStation(
        failures,
        "robot can be unreachable after radio responds",
        stationAnalyzer.analyze(
            new StationSnapshot(true, false, false, false, false, false, false, OperatingMode.NONE)),
        StationState.ROBOT_UNREACHABLE);

    checkStation(
        failures,
        "partial protocol status is not complete communications",
        stationAnalyzer.analyze(
            new StationSnapshot(true, true, true, false, false, false, false, OperatingMode.NONE)),
        StationState.COMMUNICATIONS_INCOMPLETE);

    checkStation(
        failures,
        "communications do not prove robot code is running",
        stationAnalyzer.analyze(
            new StationSnapshot(true, true, true, true, false, true, false, OperatingMode.TELEOP)),
        StationState.ROBOT_CODE_NOT_RUNNING);

    checkStation(
        failures,
        "running code does not prove a gamepad is present",
        stationAnalyzer.analyze(
            new StationSnapshot(true, true, true, true, true, false, false, OperatingMode.TELEOP)),
        StationState.GAMEPAD_MISSING);

    checkStation(
        failures,
        "healthy disabled station remains disabled",
        stationAnalyzer.analyze(
            new StationSnapshot(true, true, true, true, true, true, false, OperatingMode.TELEOP)),
        StationState.DISABLED);

    checkStation(
        failures,
        "enabled autonomous is not teleop-ready",
        stationAnalyzer.analyze(
            new StationSnapshot(true, true, true, true, true, true, true, OperatingMode.AUTONOMOUS)),
        StationState.WRONG_MODE);

    checkStation(
        failures,
        "all required teleop conditions are ready",
        stationAnalyzer.analyze(readyStation),
        StationState.READY);

    DeviceSnapshot readyDevice = new DeviceSnapshot(true, true, true, true, true, true, true);

    checkDevice(
        failures,
        "device diagnosis starts with documented topology",
        deviceAnalyzer.analyze(new DeviceSnapshot(false, false, false, false, false, false, false)),
        DeviceState.TOPOLOGY_UNVERIFIED);

    checkDevice(
        failures,
        "termination is independent of topology",
        deviceAnalyzer.analyze(new DeviceSnapshot(true, false, false, false, false, false, false)),
        DeviceState.TERMINATION_UNVERIFIED);

    checkDevice(
        failures,
        "duplicate full addresses block readiness",
        deviceAnalyzer.analyze(new DeviceSnapshot(true, true, false, true, true, true, true)),
        DeviceState.DUPLICATE_ADDRESS);

    checkDevice(
        failures,
        "wiring readiness does not prove configurator visibility",
        deviceAnalyzer.analyze(new DeviceSnapshot(true, true, true, false, true, true, true)),
        DeviceState.DEVICE_NOT_VISIBLE);

    checkDevice(
        failures,
        "visible devices can still have incompatible firmware",
        deviceAnalyzer.analyze(new DeviceSnapshot(true, true, true, true, false, true, true)),
        DeviceState.FIRMWARE_MISMATCH);

    checkDevice(
        failures,
        "configured hardware does not install a project dependency",
        deviceAnalyzer.analyze(new DeviceSnapshot(true, true, true, true, true, false, true)),
        DeviceState.VENDOR_DEPENDENCY_MISSING);

    checkDevice(
        failures,
        "installed libraries can still use an incompatible API",
        deviceAnalyzer.analyze(new DeviceSnapshot(true, true, true, true, true, true, false)),
        DeviceState.API_MISMATCH);

    checkDevice(
        failures,
        "all documented device boundaries are ready",
        deviceAnalyzer.analyze(readyDevice),
        DeviceState.READY);

    DeviceAddress mainBusMotor = new DeviceAddress("main", "REV", "motor", 3);
    DeviceAddress duplicateMainBusMotor = new DeviceAddress("main", "REV", "motor", 3);
    DeviceAddress auxiliaryBusMotor = new DeviceAddress("aux", "REV", "motor", 3);

    checkBoolean(
        failures,
        "matching bus manufacturer type and number is a duplicate address",
        mainBusMotor.equals(duplicateMainBusMotor),
        true);

    checkBoolean(
        failures,
        "the same device number on another bus is a different full address",
        mainBusMotor.equals(auxiliaryBusMotor),
        false);

    if (!failures.isEmpty()) {
      failures.forEach(message -> System.err.println("FAIL: " + message));
      System.exit(1);
    }

    System.out.println("PASS: 27 Java behavior, Driver Station, and CAN device checks");
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

  private static void checkStation(
      List<String> failures, String name, StationState actual, StationState expected) {
    if (actual != expected) {
      failures.add(name + " expected=" + expected + " actual=" + actual);
    }
  }

  private static void checkDevice(
      List<String> failures, String name, DeviceState actual, DeviceState expected) {
    if (actual != expected) {
      failures.add(name + " expected=" + expected + " actual=" + actual);
    }
  }

  private static void checkBoolean(
      List<String> failures, String name, boolean actual, boolean expected) {
    if (actual != expected) {
      failures.add(name + " expected=" + expected + " actual=" + actual);
    }
  }
}
