# Classroom Lab Sequence

Each lab is structured for a 42-minute period: 5-minute brief, 25-minute build, 8-minute test, and 4-minute reflection.

| Lab | Student outcome | Evidence |
|---|---|---|
| 1. Robot lifecycle | Explain init, disabled, autonomous, teleop, and test | Correct state transitions |
| 2. Drive safely | Add deadband, limits, and differential drive | Controlled simulated motion |
| 3. Sensors | Read encoders and IMU through interfaces | Values match simulated motion |
| 4. Feedback control | Tune a P controller using plots | Error converges |
| 5. Telemetry | Publish useful NT4 values | Dashboard displays named signals |
| 6. Vision aiming | Align from `tx` and reject bad status | Robot stops on stale/no data |
| 7. Pose | Compare odometry and AprilTag estimates | Student explains drift and correction |
| 8. Fault diagnosis | Trace I/O, CAN, vision, and power faults | Written fault tree and recovery |
| 9. Autonomous | Compose commands with timeouts and safe exits | Repeatable simulated routine |
| 10. Hardware handoff | Replace one Sim adapter with Real | Existing subsystem tests still pass |

## First demonstration

1. Start a known-good simulation.
2. Show disabled and enabled behavior.
3. Plot target, measurement, error, and output.
4. Inject a stale vision result.
5. Require the robot to reject the result.
6. Change one named control constant.
7. Re-run and compare.
8. Explain how the same command will later use a real adapter.
