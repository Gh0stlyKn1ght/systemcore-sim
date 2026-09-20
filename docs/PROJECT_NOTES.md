# Project Notes

## Objective

Give FRC Team 8721 students a working Java/WPILib environment that behaves like a SystemCore-oriented robot project before the physical controller is available.

The first version is a behavioral simulator. It teaches software architecture and control-system reasoning without pretending to reproduce the real controller's electrical or real-time behavior.

## MVP

- Java command-based WPILib project.
- Desktop simulation from VS Code.
- Differential-drive physics.
- Simulated encoders and IMU.
- Robot enable, disable, autonomous, teleop, and test modes.
- NT4 telemetry visible in Glass, Elastic, or AdvantageScope.
- SmartIO ownership and duplicate-port fault model.
- Vision result model with `tx`, `ty`, latency, timestamp, and status.
- Optional Limelight 2 adapter.
- One complete lesson that fits a 42-minute class.

## Build phases

| Phase | Deliverable | Exit condition |
|---|---|---|
| 0 | Notes and upstream reference access | Sources and scope are reproducible |
| 1 | Desktop simulator | Students can drive a simulated robot |
| 2 | Teaching console | State, I/O, CAN, vision, logs, and faults are visible |
| 3 | Vision lab | Students implement P-control and reject stale data |
| 4 | Hardware adapter | The same subsystem commands work on a test bench |
| 5 | Competition hardening | Deployment and recovery procedures are validated |

## Immediate backlog

- [ ] Generate the Java command-based WPILib project.
- [ ] Pin the tested WPILib/SystemCore version in `VERSIONS.md`.
- [ ] Add `DriveIO`, `IMUIO`, `VisionIO`, and `SmartIO`.
- [ ] Implement the simulation adapters first.
- [ ] Add CI compile and unit tests.
- [ ] Create a known-good demo tag.
- [ ] Create a broken fault-diagnosis exercise tag.
- [ ] Validate on every classroom operating system.

## Rules

- Keep 2027 Alpha dependencies isolated from competition code until tested.
- Do not couple commands directly to vendor classes.
- Do not teach simulated behavior as electrical truth.
- Do not include private security research or exploit material.
- Record every upstream version and image used during testing.
