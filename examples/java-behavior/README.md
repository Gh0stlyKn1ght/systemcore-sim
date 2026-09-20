# Java behavior example

This executable example diagnoses Driver Station and CAN-device readiness, then converts valid robot state and driver input into a bounded differential-drive request. It has no WPILib or vendor dependency, so its decision rules can run before hardware integration.

## Run

```bash
npm run test:java
```

The command uses Java source-file mode to compile the source in memory and execute its checks. Java 17 or newer is sufficient for this example. The 2027 WPILib project itself uses the Java version supplied by that WPILib release.

## Contract

- Disabled, non-teleop, invalid, stale, and deadband input returns a neutral request.
- Input age is valid from 0 through 100 milliseconds.
- Forward and turn inputs are clamped to the range `[-1.0, 1.0]`.
- Active requests are scaled to 75 percent before final clamping.
- Every drive decision returns a reason suitable for telemetry.
- Driver Station diagnosis keeps radio, robot, protocol, robot-code, gamepad, enable, and mode state separate.
- CAN-device diagnosis keeps topology, termination, full address, configurator visibility, firmware, vendor dependency, and Java API compatibility separate.

## Files

- `BehaviorCheck.java`: behavior types, station and device diagnosis, decision logic, and twenty-seven executable checks.

The single-file layout makes the artifact runnable in a minimal environment. A robot project can split the nested types into normal source files without changing the contract.
