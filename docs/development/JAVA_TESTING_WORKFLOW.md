# Java robot code: build, test, simulate, then validate hardware

**Research lead and original design: Gh0stly / @Gh0stlyKn1ght**  
**Decision date: 2026-09-20 | Proposed workflow | No robot code or CI implemented yet**

## What this repository should deliver

A Java/WPILib robot application that can be developed and tested before Systemcore hardware is available. Every meaningful behavior should have a repeatable check, and the same application decisions should be reusable behind separately validated simulation and real-device adapters.

The first deliverable is not another simulator engine, a controller OS image, a ROS 2 workspace, or a dashboard-only mockup. It is robot code plus evidence of its behavior. Use the [toolkit](FRC_TOOLKIT.md) to prepare one known environment, then implement a small end-to-end example.

This document advances the coding and testing plan. It does not claim that the repository already contains the Java source tree, Gradle wrapper, tests, or GitHub Actions workflow described below.

## Start with one official Java template

Use the selected release's Java command-based template with desktop support. Choose one framework generation and record it. Do not mix Commands v2/v3, older `edu.wpi.first` imports, and newer `org.wpilib` examples indiscriminately. The 2027 changelog explicitly documents these transitions. [2027 changes](https://docs.wpilib.org/en/latest/docs/yearly-overview/yearly-changelog.html)

First build the unmodified generated project. A failure at that point is an environment or template/dependency problem, not a reason to add custom robot logic. Preserve the generated build configuration and native-library test setup unless a documented need requires a change.

Our first example should be a simple differential drive with simulated encoders and heading. Keep its physics parameters explicitly hypothetical until measured. Add one manual-drive behavior, one distance-limited autonomous behavior, and one stale-observation fault. Do not start with swerve, a vision stack, multiple planners, and every vendor dependency at once.

## Proposed code organization

The paths below are a design, not existing files. Keep the actual template's entry points and package conventions.

```text
src/main/java/<team-package>/
  Robot.java                  Template lifecycle entry point
  RobotContainer.java         Wiring for the selected command-based template
  subsystems/drive/           Drivetrain behavior and its I/O boundary
  io/                         Only interfaces actually shared by the example
  simulation/                 Physics adapters and controlled fault injection
  telemetry/                  Small helpers only when repeated use warrants them

src/test/java/<team-package>/
  control/                    Pure logic and input-contract tests
  subsystems/                 Adapter/subsystem behavior tests
  simulation/                 Repeatable scenario tests

src/main/deploy/               Versioned runtime assets when needed
vendordeps/                    Only explicitly selected compatible dependencies
```

Do not create empty packages for features we have not implemented. `RobotContainer` is a template/framework convention, not a controller requirement. The design should remain simple enough for a student to follow one input through to one output.

## Separate decisions from device access

A subsystem decides what should happen. Its adapter reads and writes the selected hardware or software model. Both adapters must use the same documented units, sign conventions, timestamps, validity rules, and meaning of a stop request.

For example, `DriveIO` might supply wheel positions and velocities plus observation validity, and accept requested left/right voltages. This is a proposed application contract, not a new WPILib API. Use existing types where useful and avoid a generic abstraction over every possible motor controller.

A simulation adapter updates a WPILib physics model and returns modeled measurements. A real adapter uses the selected device API. Merely sharing an interface does not establish identical dynamics, timing, or fault behavior. Hardware comparison is a later test stage.

## Four levels of verification

| Stage | What it asks | Proposed evidence | What it cannot prove |
|---|---|---|---|
| Build | Do imports, types, dependencies, and artifacts assemble? | Build output and exact version profile | Correct behavior |
| Automated Java tests | Do selected decisions and subsystem contracts behave correctly? | Assertions, test count, failure details, and HTML/XML reports | Unmodeled hardware behavior |
| Desktop simulation | Does the integrated program behave coherently against the chosen model? | Recorded scenario, telemetry, expected result, and repeat run | Actual wiring, electrical limits, or real-time deadlines |
| Safe hardware validation | Does the real device behave as assumed? | Identified hardware/image/firmware, procedure, measurements, and discrepancies | Universal safety or compatibility with untested configurations |

WPILib provides desktop simulation and Java unit-test support. A simulator's launch and an automated test suite are different checks. Vendor simulation coverage varies, so new dependencies must pass a desktop smoke test before becoming required classroom dependencies. [Simulation](https://docs.wpilib.org/en/latest/docs/software/wpilib-tools/robot-simulation/introduction.html), [unit testing](https://docs.wpilib.org/en/latest/docs/software/wpilib-tools/robot-simulation/unit-testing.html)

## Initial acceptance tests

These are proposed requirements, not passed tests or official controller timing guarantees.

| Test | Expected application behavior |
|---|---|
| Zero manual input | Requests neutral output |
| Input outside permitted range | Explicitly clamps or rejects according to the written contract |
| Small joystick noise | Does not create unwanted motion under the selected deadband policy |
| Disabled state with a nonzero request | Application requests neutral outputs; hardware enable gating remains a separate layer |
| Autonomous completes or is canceled | Outputs return to the intended stopped state |
| Old or invalid sensor observation | Code takes its documented fallback and reports the invalidity, rather than treating it as fresh data |
| Repeat scenario with the same inputs | State/events remain equivalent within declared numerical tolerances |
| Conflicting logical port/device configuration | Initialization fails clearly before the exercise can command motion |

Not every mechanism should respond to every fault by blindly setting zero: a gravity-loaded mechanism can require a different physical strategy. The first exercise is a drivetrain model. Future mechanisms need their own hazard analysis and stop/hold contract.

## How to write reliable tests

Use JUnit assertions for concrete expected behavior, not only print statements. Keep pure control decisions independent of actual devices where practical. HAL-based tests must initialize the appropriate desktop HAL and release allocated devices; do not let static state or a previous test's resource ownership determine the next test's outcome. The official Java unit-test examples show the test source set and lifecycle hooks. [Unit testing](https://docs.wpilib.org/en/latest/docs/software/wpilib-tools/robot-simulation/unit-testing.html)

Our design requirements: inject a controllable clock for time-dependent decisions, give randomized scenarios a recorded seed, compare floating-point values with justified tolerances, and reset scheduler/telemetry state when a test touches it. Avoid wall-clock sleeps as a substitute for controlled simulation time.

Before trusting the test runner, temporarily introduce a failing assertion and verify failure is reported. Restore it afterward. A green build with zero discovered tests does not establish tested behavior.

## Day-to-day commands after the project exists

**The current documentation repository does not yet have a Gradle project. Do not expect these commands to run here before generating the application.** Once generated, use its wrapper in its configured WPILib environment, not a separately installed global Gradle.

```bash
# Linux/macOS, from the generated robot-project root:
./gradlew --version
./gradlew tasks --all
./gradlew test
./gradlew build
```

```powershell
# Windows PowerShell, from the generated robot-project root:
.\gradlew.bat --version
.\gradlew.bat tasks --all
.\gradlew.bat test
.\gradlew.bat build
```

Use `WPILib: Test Robot Code` for the editor test workflow and `WPILib: Simulate Robot Code` for the initial GUI launch. Inspect the Java report at `build/reports/tests/test/index.html` when generated. Build/test/deploy policy must be verified in the generated project; do not disable failing tests simply to deploy. [Unit testing](https://docs.wpilib.org/en/latest/docs/software/wpilib-tools/robot-simulation/unit-testing.html), [Gradle tasks](https://docs.wpilib.org/en/latest/docs/software/advanced-gradlerio/gradlew-tasks.html)

### Simulation command discrepancy

The inspected 2027 changelog says alpha 7 uses the Java `run` task for simulation, while the mutable simulation introduction renders `./gradlew runa`. This discrepancy was observed in documentation, not resolved by execution. Do not copy either blindly as a validated command. Start with the matching WPILib editor command, inspect `tasks --all`, and record the actual working CLI command in the first validation report. Older `simulateJava` instructions must also be checked against the selected template. [Changelog](https://docs.wpilib.org/en/latest/docs/yearly-overview/yearly-changelog.html), [simulation introduction](https://docs.wpilib.org/en/latest/docs/software/wpilib-tools/robot-simulation/introduction.html)

The real-Driver-Station simulation extension is also a separate compatibility question in the prerelease changelog. Our first milestone should use Simulation GUI inputs rather than depend on an unverified real-DS connection.

## Telemetry is part of the test interface

For the first drivetrain, record the selected mode, input request, requested output, measured/modelled velocity and distance, heading, observation age/validity, and the reason for any fallback. Give every number a unit and identify synthetic data.

Use AdvantageScope to compare a desired value with the measured/modelled value. Use Elastic for a small operator display when needed. Watching a historical log in a viewer is not the same as re-executing modified code against recorded inputs. The latter needs an explicit replay design; AdvantageKit is an optional framework for that later requirement. [AdvantageScope](https://docs.wpilib.org/en/latest/docs/software/dashboards/advantagescope.html), [Elastic](https://docs.wpilib.org/en/latest/docs/software/dashboards/elastic.html), [AdvantageKit](https://docs.advantagekit.org/)

## Continuous integration, once there is code

Add one GitHub Actions workflow that builds the selected project and runs tests on a pinned compatible environment. Preserve failed-test reports and logs. Keep GUI/manual checks and hardware checks separate from headless CI, and never place a real robot on the automated test path. WPILib documents this build-and-test approach, but its sample container and toolchain versions must be reconciled with our chosen prerelease. [WPILib CI guide](https://docs.wpilib.org/en/latest/docs/software/advanced-gradlerio/robot-code-ci.html)

Our proposed CI policy: read-only repository permissions by default, no deployment credentials, no firmware writes, no automatic dependency upgrades, and explicit failure when tests are missing. Only add a passing-build badge after a real workflow run succeeds.

## From software-only to the bench

Begin disconnected from real actuators. Later, identify each physical device and export its configuration before changing firmware or settings. Verify sensor direction, units, gearing assumptions, limits, motor inversion, bus selection, and startup state against the model. Test with bounded outputs, a clear work area, a designated operator, and a ready stop method.

Motor-control features in Tuner, REV tools, and other utilities can be separate control paths. Do not assume 'robot code disabled' makes every diagnostic action read-only. Physical bench procedures need an explicit safety review. Simulation does not establish a safe test envelope.

## Definition of the first completed milestone

A clean checkout on one documented host builds, discovers and runs its tests, launches one drivable simulation, records useful data, and reproduces one deliberately broken sensor scenario with an understandable fallback. Another person can restore the known-good example from the instructions.

Only then add vendor-specific hardware integration or a second mechanism. Keep the existing research available, but use working code, recorded tests, and measured limitations as the progress evidence.

## Review boundary

Sources above were reviewed on 2026-09-20. This is a proposed testing contract and source-backed setup procedure. No application generation, code compilation, test execution, CI execution, controller connection, or classroom validation occurred during this documentation update.
