# Software stack and migration

Research lead: @Gh0stlyKn1ght. Snapshot: 2026-09-20. **Observed documentation versions, not locally tested versions.**

## Research baseline

The official testing guide currently pairs image 14 or newer with WPILib 2027 alpha 7 or newer. The Limelight repository identifies Alpha 14/Beta 14 releases and marks Alpha 15/Beta 15 unreleased. Never install a release merely because its number is highest in a README. Choose the image for the actual hardware generation. [S05](SOURCES.md#s05) [S06](SOURCES.md#s06)

| Layer | Publicly documented research reference | Caveat |
|---|---|---|
| Controller OS | Alpha 14 / Beta 14 | Separate hardware images; prerelease |
| WPILib | `v2027.0.0-alpha-7` | Breaking changes from earlier alphas |
| Java | 25 | Use matching bundled tooling |
| C++ | C++23, GCC 14 Linux toolchain direction | Match the actual release |
| Driver Station | New 2027 FIRST Driver Station | Platform and FMS constraints are separate |
| Desktop tools | Current matching Elastic / AdvantageScope / simulation GUI | Do not mix arbitrary 2026 binaries |

Sources: [S05](SOURCES.md#s05), [S11](SOURCES.md#s11), [S13](SOURCES.md#s13).

## Controller Linux is not the simulation host

OS release-10 notes describe Linux 6.12.77, a change to 16 KB pages, Java 25, GCC 14, and Python 3.13.12. These supersede the old alpha document's kernel/toolchain description for those images. Public cross-compilation configuration explicitly targets `aarch64` using an `aarch64-buildroot-linux-gnu` toolchain and sysroot. [S06](SOURCES.md#s06) [S17](SOURCES.md#s17)

This does not make the controller Ubuntu, Debian, or Kali. Those are candidate development hosts. An x86-64 package, desktop JNI library, or generic ARM root filesystem is not automatically a controller-compatible artifact. Cross-compilation, desktop simulation, and full-system emulation solve different problems.

## Migration changes that affect lesson design

The 2027 changelog documents Java namespace migration from `edu.wpi.first` to `org.wpilib`, C++ namespace changes, OpModes, Commands v3, explicit `CANPort` selection, and alpha-7 Telemetry/Tunable APIs. It also records nanosecond timestamp changes and changes to Java simulation task wiring. These are substantive migration topics, not just a new deployment target. [S11](SOURCES.md#s11)

The removed-features page retires old Java dashboard tools and NT3, points users toward Elastic/AdvantageScope, and lists removed APIs and hardware interfaces. Its older counter-related statements conflict with newer hardware-support notes, so do not turn that whole page into an immutable checklist. [S12](SOURCES.md#s12)

Our proposed migration worksheet should classify changes as:

| Change class | Review question |
|---|---|
| Names/imports | Did the importer resolve every reference, including vendors? |
| Lifecycle | Which constructor, mode, and scheduler pattern does the chosen template use? |
| Types and units | Did a value's units, enum type, or timestamp convention change? |
| Telemetry | Is this a new API, an old compatibility example, or a dashboard protocol? |
| Hardware | Does the new physical controller expose the old interface? |
| Behavior | Does the migrated code still fail safely and report stale data? |

Do not promise that global search-and-replace migrates a robot correctly. Compile checks catch some errors; replay and behavioral tests are still required.

## Vendor compatibility worksheet

The following is a documentation snapshot, not an installation lock. [S05](SOURCES.md#s05)

| Integration | Alpha-7-era reference | Required follow-up |
|---|---|---|
| CTRE Phoenix 6 | `26.70.0-alpha-2` | Firmware `26.70.x`; check dated vendor notes |
| REVLib | `2027.0.0-alpha-7` | Match device firmware and changed signal APIs |
| ReduxLib | `2027.0.0-alpha-7` in testing matrix | Verify its vendor release before use |
| AdvantageKit | `27.0.0-alpha-5` in testing matrix | Verify sample/template compatibility |
| ChoreoLib | `2027.0.0-alpha-3` in testing matrix | Validate generated trajectory workflow |
| PathPlannerLib | No compatible alpha-7 release listed in matrix | Do not infer permanent lack of support |
| ThriftyLib | No compatible alpha-7 release listed in matrix | Recheck before selection |
| LimelightLib 2 | Alpha-7 vendordep | Match OS generation and timestamp expectations |
| AndyMark utility | Systemcore-specific IPK documented | Exact package build unverified here |

### CTRE details worth retaining

CTRE's 2026-09-18 release explicitly matches WPILib alpha 7 and Systemcore image 14, requires corresponding firmware, and does not support roboRIO. The integration guide supplies a matching CANivore package and uses `CANBus(CANPort)` for native ports. It warns that Tuner X mechanism generation has not yet caught up. API support, device firmware, configuration tools, and simulation support must be checked independently. [S19](SOURCES.md#s19) [S20](SOURCES.md#s20)

### REV details worth retaining

REV's alpha notes add explicit bus selection, replace raw bus integers with `CANPort`, and introduce signal wrappers that distinguish valid/recent values. They also identify a Systemcore Hardware Client 2 IPK. The current note directs non-A301 devices toward listed 2026 firmware rather than inventing a universal 2027 firmware update. [S18](SOURCES.md#s18)

### AndyMark details worth retaining

The official configuration index describes an IPK-based browser workflow for Systemcore. Its legacy utility page explicitly limits the older workflow to roboRIO/2026-or-earlier and points 2027 users elsewhere. We inspected the index and legacy guidance, but the linked IPK download and installation pages were inaccessible in this session. Do not invent a version or claim installation succeeded. [S23](SOURCES.md#s23) [S24](SOURCES.md#s24)

## Python and LabVIEW

RobotPy deployment guidance exists, but the inspected testing note still specifies an alpha-5-era package. Treat that as evidence of a supported development direction, not proof of an internally consistent alpha-7 package set. Python deserves a separate future compatibility exercise; Java remains this project's proposed first teaching path. [S22](SOURCES.md#s22)

LabVIEW is a good example of why time matters. The older WPILib page says it is not currently supported; FIRST's 2026-09-10 statement says early work to bring LabVIEW to Systemcore is underway. These can both be true. Do not claim either a ready-to-install release or permanent abandonment. [S12](SOURCES.md#s12) [S09](SOURCES.md#s09)

## Driver Station and desktop simulation

The new Driver Station is announced for Windows, macOS, and Linux, with x64/ARM64 availability. Its announced FMS support is restricted to Windows and a possible approved hardware appliance, not Linux/macOS desktop. Linux input permissions are a setup concern, not a reason to run the entire development environment as root. [S13](SOURCES.md#s13)

The test guide says the NI Driver Station remains compatible with released Systemcore images, with newer features requiring the new station. That is different from saying the new station supports roboRIO. [S05](SOURCES.md#s05)

For our first simulator, use the matching WPILib simulation UI and verify its state/input path. The changelog describes evolving Driver Station simulation support, so a real new-DS-to-desktop-sim connection is a separate acceptance test, not an assumed feature. [S11](SOURCES.md#s11) [S14](SOURCES.md#s14)
