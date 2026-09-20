# FRC development toolkit: Java, testing, and Systemcore

**Research lead and original synthesis: Gh0stly / @Gh0stlyKn1ght**  
**Reviewed: 2026-09-20 | Setup recommendations, not an installation record**

## Project direction

The intended deliverable is real Java/WPILib robot code with automated tests, desktop simulation, useful telemetry, and a later hardware adapter. Documentation supports that deliverable; it is not a substitute for running code. ROS 2 is not a prerequisite for this repository. Java is our selected language, not the only FRC language. See [the controller layers](../research/CONTROLLER_LAYERS.md) and [the testing workflow](JAVA_TESTING_WORKFLOW.md).

The application has not been generated yet. This update documents what to install and how to validate the first implementation. No installer, firmware update, robot deployment, simulator, or test suite was run.

## Install by responsibility, not by collecting every tool

| Tool | What it is for | Initial priority | Source |
|---|---|---|---|
| Official WPILib installer, including its VS Code environment and Java toolchain | Editing, building, debugging, and preparing robot code | Required for the selected Java/WPILib baseline | [T01] |
| Git | Versioned code, rollback, review, and reproducible source identity | Required by our workflow; GitHub Desktop is optional, not a replacement requirement | [T20] |
| WPILib Simulation GUI | Running robot code against simulated inputs and outputs | First milestone | [T03] |
| AdvantageScope | Investigating telemetry and logs from real or simulated code | First milestone; launch through WPILib tools | [T05] |
| Elastic | Driver-facing dashboard and test displays | Add a small layout when needed; not a replacement for Driver Station | [T06] |
| FIRST Driver Station, matched 2027 release | Operator inputs, enable/disable, and robot mode selection for Systemcore | Hardware workflow; do not make it a prerequisite for our first software-only simulation | [T02], [T07] |
| Browser and Limelight Hardware Manager | Systemcore configuration, supported update/recovery workflows | Needed when working with an actual controller | [T02] |
| Vendor configuration applications | Device identity, firmware, configuration, and diagnostics | Only for hardware actually selected | Vendor table below |

Several of these tools arrive with the WPILib installation. Check its Start Tool menu before installing another independent copy. Use the bundled Java/toolchain and the project's Gradle wrapper, rather than assembling unrelated current versions. Do not build allwpilib from source just to write a student robot program. [T01], [T04]

**Recommended minimum for the first lesson:** WPILib, Git, Simulation GUI, and AdvantageScope. Elastic can be added without making a custom web dashboard. JUnit is a test dependency inside the Java project, not a separate desktop app to install. [T03], [T05], [T08]

## CAN tooling: application, library, and firmware are different

The user's term `icann` could not be confidently matched to a particular FRC application. This guide covers CAN-bus configuration and diagnostics without pretending that identification is settled.

| Device/workflow | Configuration application | Robot-code dependency | Important distinction |
|---|---|---|---|
| REV hardware | REV Hardware Client 2 | Compatible REVLib | Installing the client does not add REVLib to a Java project. [T09], [T11] |
| CTRE hardware, including appropriate Talon/CANcoder/Pigeon devices | Phoenix Tuner X | Compatible Phoenix 6 | Tuner, API, firmware, and generated examples have separate compatibility requirements. [T10], [T12] |
| AndyMark CAN devices on Systemcore | AndyMark Systemcore IPK, used through a browser | Device-specific integration as documented | The public index establishes this workflow; exact package download/build was not validated. [T13] |
| Controller-level bus diagnosis | Systemcore's web diagnostics and CAN views | Not a universal replacement for vendor APIs | OS behavior depends on image version. [existing OS research](../research/SOFTWARE.md) |
| Optional Linux CAN learning | SocketCAN tools and a virtual or supported physical CAN interface | Separate from the first Java application | SocketCAN is an operating-system interface, not an FRC motor-controller configuration application. [T19] |

A CANivore is hardware, not an app to install. The matching driver/package is a different artifact. A desktop simulator does not require purchasing or attaching one. Hardware-attached simulation is a separate vendor-specific setup, not permission to connect a simulated program to live actuators unexpectedly. [T03], [T12]

Do not assume that a tool listing generic CAN devices can configure every manufacturer's specialized feature. Identify the actual device, bus, firmware, API, and tool before selecting a setup procedure.

### REV client update

REV marks the original Hardware Client as end of life and points to Hardware Client 2. RHC2 lists Windows, macOS, and Linux support, with its own OS and device-firmware requirements. Its general overview requires 26.x-or-newer device firmware. Do not blindly upgrade older hardware simply to satisfy a new desktop application: review the existing robot's compatibility and export configuration first. [T09], [T09A]

### Tuner X platform boundary

CTRE lists Windows 11, macOS 14+, Android 9+, and iOS 15+ for Tuner X. Linux desktop is not in that list. This is a Tuner application restriction, not a claim that Phoenix Java code cannot run in Linux simulation. Check the vendor's selected library/platform support separately. [T10]

Some diagnostic applications can command outputs or override robot code. Treat their motor-control features as active hardware tests, not harmless read-only inspection. No such actions were performed during this review.

## Host choices for this project

| Host | Role we recommend | Qualification |
|---|---|---|
| Windows 11 x64 | Broadest practical single-machine choice for FRC development, vendor tools, and the event operator workflow | Recommendation based on the tools below, not a requirement for all code development |
| Debian 13 or Ubuntu 26.04, 64-bit | Supported Linux candidates for this 2027 WPILib development profile | Verify the actual release assets and installed environment |
| Kali | Research workstation; use a supported guest for the first reproducible simulation | Not listed as a supported WPILib distribution |
| Existing Ubuntu 24.04 ROS 2 environment | Keep its working ROS setup separate | An RHC2-compatible OS is not necessarily a 2027-WPILib-compatible OS |

Current WPILib prerequisites list Debian 13 and Ubuntu 26.04 and say other Linux distributions with glibc >= 2.41 may work but are unsupported. Do not manually replace a working system's glibc for this project. [T01]

The new Driver Station has Linux/macOS desktop versions, but its announced FMS support excludes those desktop platforms. We recommend retaining a Windows operator laptop for events. An ordinary bench connection, a simulator connection, and event/FMS compatibility are separate checks. [T07]

## Version profile to validate, not a tested lock

| Component | Research candidate from the inspected sources |
|---|---|
| WPILib | `2027.0.0-alpha-7` |
| Java | 25, matched to the WPILib distribution |
| Real controller image, when needed | Alpha 14 for alpha hardware, or Beta 14 for beta hardware |
| Limelight Hardware Manager | `2.0.11` linked by the testing guide |
| REVLib, only when selected | `2027.0.0-alpha-7` |
| Phoenix 6, only when selected | `26.70.0-alpha-2`, with its corresponding firmware requirements |
| Dashboard builds | Use the matching 2027 builds linked from SystemcoreTesting |

Sources: [T02], [T04], [T11], [T12]. A controller OS image is not required to run a desktop-only simulator. Keep the existing `wpilib-reference.lock` as a reading snapshot; do not relabel it as a tested dependency lock. Recheck this candidate before installation or a deliberate upgrade.

Avoid the mutable installation page's embedded old release card when choosing a 2027 installer. Follow the explicit release linked by SystemcoreTesting. Some documentation paragraphs lag newer prereleases. [T01], [T02]

## Useful later, not prerequisites

| Tool | Purpose | When to add it |
|---|---|---|
| SysId | Fit mechanism models from recorded input/output measurements | After we have a working mechanism and a safe characterization procedure. It is included in WPILib. [T14] |
| Choreo + matching ChoreoLib | Author and execute drivetrain trajectories | After basic driving, odometry, and tests work. [T15] |
| PathPlanner + matching PathPlannerLib | Alternative path-planning workflow | The inspected alpha-7 matrix lists no compatible PathPlannerLib release. Recheck rather than assuming a stable download will work. [T02], [T16] |
| AdvantageKit | Logging/replay framework incorporated into robot code | Add for an explicit replay requirement, not merely to use AdvantageScope. [T17] |
| Limelight vision configuration and matched library | Configure and consume selected camera results | After synthetic vision tests; see [existing software research](../research/SOFTWARE.md). |
| PhotonVision | Alternative vision software and associated integration | Only after choosing the camera/coprocessor architecture and verifying a matching release. Not a required parallel vision stack. [T18] |
| Radio web configuration | Configure the actual robot radio for the bench | For a VH-109, WPILib documents browser-based configuration. Event procedures are separate. [T21] |

Do not add every planner, dashboard, vision framework, or vendor library to the starter. Each added dependency needs an actual use case and a supported desktop-test path.

## Older FRC guides: recognize these without installing them here

NI FRC Game Tools provides the older FRC Driver Station and roboRIO imaging tools on Windows. Java teams using that workflow do not need the complete LabVIEW development package merely to run those utilities. This is legacy context, not our proposed Systemcore simulation dependency. Do not use the roboRIO imaging tool for Systemcore. [T22]

For the 2027 setup, do not center lessons on retired Shuffleboard, SmartDashboard desktop app, RobotBuilder, or PathWeaver. The current migration guide points toward Elastic, AdvantageScope, and newer path tools. Distinguish a retired desktop application from a similarly named historical robot API example. [T16]

ROS 2, Gazebo, GNS3, a custom Linux distribution, a generic CAN adapter, and a full C++ IDE are not prerequisites for the first Java-only exercise. Any later native-code or vendor build requirement must be reviewed separately.

## Installation acceptance checklist

These are proposed checks, not completed tasks.

- [ ] Record the host OS/architecture, selected installer, Java runtime, and WPILib version.
- [ ] Open the WPILib-provided VS Code environment and generate the selected Java template with desktop support.
- [ ] Build the untouched template before adding dependencies.
- [ ] Run an automated test and confirm a deliberately failing assertion is actually reported as failed.
- [ ] Launch Simulation GUI and verify the intended input/mode path.
- [ ] Inspect one named measurement in AdvantageScope; save the layout and a sample log.
- [ ] Repeat from a clean checkout and prepare the required dependencies for offline classroom use.

Install and validate on one machine before rolling out to the classroom. Preserve existing working environments and keep firmware changes out of the software-only setup.

## Sources and retrieval notes

All sources were reviewed on 2026-09-20. These links supplement the main research register. Primary documentation establishes tool purpose and published support, not our successful installation.

| ID | Official source | Scope |
|---|---|---|
| T01 | [WPILib installation][T01] | Prerequisites and bundled environment; contains an older embedded release card |
| T02 | [SystemcoreTesting guide][T02] | Current tooling and version matrix; README blob observed `e2710a939b7d8ebe09dfe7865c623fc06730f331` |
| T03 | [Desktop simulation][T03] | Desktop support, launch UI, and vendor limitations |
| T04 | [2027 changelog][T04] | Java version, release changes, and evolving simulation tasks |
| T05 | [AdvantageScope][T05] | Programmer-oriented data/log visualization |
| T06 | [Elastic][T06] | Driver-facing dashboard |
| T07 | [2027 Driver Station announcement][T07] | Desktop platform support versus FMS scope |
| T08 | [WPILib unit testing][T08] | Java tests, test source directory, reports, and build behavior |
| T09 / T09A | [RHC2 overview][T09] / [original RHC end-of-life notice][T09A] | Tool and device requirements; desktop app retirement |
| T10 | [Phoenix Tuner X][T10] | Application purpose and supported platforms |
| T11 / T12 | [REV test integration][T11] / [CTRE test integration][T12] | Version-specific context from the existing research dossier |
| T13 | [AndyMark configuration index][T13] | Public IPK workflow; package build/download not validated |
| T14 | [SysId][T14] | Model identification and included application |
| T15 / T16 | [Choreo][T15] / [2027 removed tools][T16] | Planner purpose and migration guidance |
| T17 / T18 | [AdvantageKit][T17] / [PhotonVision][T18] | Optional framework categories, not verified alpha-7 installations |
| T19 | [Linux SocketCAN][T19] | Software CAN interfaces; not an electrical simulator |
| T20 | [WPILib CI guide][T20] | GitHub-based build/test workflow; example must be adapted to selected release |
| T21 / T22 | [Radio setup][T21] / [NI Game Tools][T22] | Hardware configuration and older roboRIO utility context |
| T23 | [Gradle wrapper tasks][T23] | Project wrapper and task discovery |

The exact intended `icann` tool is still unresolved. No unverified application with that name has been added to the install list. Search results unrelated to FIRST Robotics were discarded. The optional PathPlanner link-following attempt failed; its role and alpha-7 caveat are based on WPILib's migration guidance and testing matrix, not an inspected current PathPlanner installer.

No forum popularity survey was performed for this checklist. 'Common tools' means the relevant official ecosystem toolkit, not a measured usage ranking. No hardware model, vendor ownership, installed tool, or successful test is assumed from a recommendation.

[T01]: https://docs.wpilib.org/en/latest/docs/zero-to-robot/step-2/wpilib-setup.html
[T02]: https://github.com/wpilibsuite/SystemcoreTesting/blob/main/README.md
[T03]: https://docs.wpilib.org/en/latest/docs/software/wpilib-tools/robot-simulation/introduction.html
[T04]: https://docs.wpilib.org/en/latest/docs/yearly-overview/yearly-changelog.html
[T05]: https://docs.wpilib.org/en/latest/docs/software/dashboards/advantagescope.html
[T06]: https://docs.wpilib.org/en/latest/docs/software/dashboards/elastic.html
[T07]: https://wpilib.org/blog/the-2027-first-driver-station
[T08]: https://docs.wpilib.org/en/latest/docs/software/wpilib-tools/robot-simulation/unit-testing.html
[T09]: https://docs.revrobotics.com/rev-hardware-client-2
[T09A]: https://docs.revrobotics.com/rev-hardware-client
[T10]: https://v6.docs.ctr-electronics.com/en/stable/docs/tuner/index.html
[T11]: ../research/SOFTWARE.md#rev-details-worth-retaining
[T12]: ../research/SOFTWARE.md#ctre-details-worth-retaining
[T13]: https://docs.andymark.com/frc-electronics/can-device-configuration
[T14]: https://docs.wpilib.org/en/latest/docs/software/advanced-controls/system-identification/introduction.html
[T15]: https://choreo.autos/
[T16]: https://docs.wpilib.org/en/latest/docs/yearly-overview/removed-features.html
[T17]: https://docs.advantagekit.org/
[T18]: https://docs.photonvision.org/en/latest/
[T19]: https://docs.kernel.org/networking/can.html
[T20]: https://docs.wpilib.org/en/latest/docs/software/advanced-gradlerio/robot-code-ci.html
[T21]: https://docs.wpilib.org/en/latest/docs/zero-to-robot/step-3/radio-programming.html
[T22]: https://docs.wpilib.org/en/stable/docs/zero-to-robot/step-2/frc-game-tools.html
[T23]: https://docs.wpilib.org/en/latest/docs/software/advanced-gradlerio/gradlew-tasks.html
