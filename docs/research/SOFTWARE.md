# Software stack and migration

Research lead: @Gh0stlyKn1ght. Snapshot: 2026-09-21. **Observed documentation and release versions, not locally tested versions.**

## Research baseline

The official testing guide currently pairs Systemcore image 14 or newer with WPILib 2027 alpha 7 or newer. Since the prior snapshot, Limelight has published **Alpha 15 build 382** and **Beta 15 build 212** as GitHub prereleases. This corrects the previous “release 15 unreleased” note without converting prerelease images into final season software. Choose the image for the actual hardware generation. [S05](SOURCES.md#s05) [S39](SOURCES.md#s39)

| Layer | Publicly documented research reference | Caveat |
|---|---|---|
| Controller OS | Alpha 15 build 382 / Beta 15 build 212 | Published prereleases; separate hardware images |
| WPILib | `v2027.0.0-alpha-7` | Latest tagged WPILib release found; breaking changes from earlier alphas |
| Java | 25 on documented Systemcore release-10+ stack | Use matching bundled tooling and release docs |
| C++ | C++23, GCC 14 Linux toolchain direction | Match the actual release/toolchain |
| Driver Station | `v2027.0.0-alpha-8` is the newest published DS found | Released alpha build, not final season release |
| Desktop tools | Matching Elastic / AdvantageScope / simulation GUI for chosen profile | Do not mix arbitrary 2026 binaries or `main` features into a tagged profile |

Sources: [S05](SOURCES.md#s05), [S11](SOURCES.md#s11), [S16](SOURCES.md#s16), [S39](SOURCES.md#s39), [S41](SOURCES.md#s41).

## Release profile is now a tuple, not one version number

A meaningful 2027 test record should include all of these values:

```text
Systemcore hardware revision
Systemcore image tag/build
Motioncore firmware, if used
WPILib release/tag
Driver Station release/tag
Vendor library version
Device firmware version
Configuration-tool version
Desktop OS and architecture
Robot source commit
```

The general SystemcoreTesting matrix saying image `>=14` works with WPILib alpha 7+ is useful coordination guidance, but it does not prove that each vendor independently validated every newer image. For example, CTRE's dated 2026-09-18 note explicitly names Systemcore image 14. Image-15-specific CTRE validation was not established in this review. [S05](SOURCES.md#s05) [S20](SOURCES.md#s20)

## Controller Linux is not the simulation host

OS release-10 notes describe Linux 6.12.77, a change to 16 KB pages, Java 25, GCC 14, and Python 3.13.12. These supersede the old alpha document's kernel/toolchain description for those images. Public cross-compilation configuration explicitly targets `aarch64` using an `aarch64-buildroot-linux-gnu` toolchain and sysroot. [S06](SOURCES.md#s06) [S17](SOURCES.md#s17)

This does not make the controller Ubuntu, Debian, or Kali. Those are candidate development hosts. An x86-64 package, desktop JNI library, or generic ARM root filesystem is not automatically a controller-compatible artifact. Cross-compilation, desktop simulation, and full-system emulation solve different problems.

Image-15 release assets include separate aarch64 cross-compilation toolchains and kernel-source archives for Alpha and Beta releases. Their publication improves public build resources, but no toolchain was downloaded or executed by this project. [S39](SOURCES.md#s39)

## Migration changes that affect lesson design

The 2027 changelog documents Java namespace migration from `edu.wpi.first` to `org.wpilib`, C++ namespace changes, OpModes, Commands v3, explicit `CANPort` selection, and alpha-7 Telemetry/Tunable APIs. It also records nanosecond timestamp changes and changes to Java simulation task wiring. These are substantive migration topics, not just a new deployment target. [S11](SOURCES.md#s11) [S16](SOURCES.md#s16)

The removed-features page retires old Java dashboard tools and NT3, points users toward Elastic/AdvantageScope, and lists removed APIs and hardware interfaces. Its older counter-related statements conflict with newer hardware-support notes, so do not turn that whole page into an immutable checklist. [S12](SOURCES.md#s12)

Our migration worksheet should classify changes as:

| Change class | Review question |
|---|---|
| Names/imports | Did the importer resolve every reference, including vendors? |
| Lifecycle | Which constructor, mode, and scheduler pattern does the chosen template use? |
| Types and units | Did a value's units, enum type, or timestamp convention change? |
| Telemetry | Is this a released API, an old compatibility example, or a merged-only source change? |
| Hardware | Does the selected controller revision and image expose the old interface? |
| Behavior | Does the migrated code still fail safely and report stale data? |

Do not promise that global search-and-replace migrates a robot correctly. Compile checks catch some errors; replay and behavioral tests are still required.

## Tagged alpha 7 versus post-alpha7 `main`

The latest tagged WPILib release found is `v2027.0.0-alpha-7`, published 2026-09-01. Several relevant changes merged afterward and therefore belong in a separate evidence class until a later tagged release contains them. [S16](SOURCES.md#s16) [S42](SOURCES.md#s42)

### Architecture: AOS to NetworkTables bridge

Commit `813dc402` from 2026-09-16 adds AOS and an `aosnt` bridge. Tagged AOS channels are translated into ordinary NetworkTables topics so dashboards and DataLog consumers can keep using the NT-facing surface. The commit states that the first target is realtime `mrccomm` control data and that the bridge runs as a separate process, including a Systemcore build. [S42](SOURCES.md#s42)

This is an important architecture direction, but it does **not** make Systemcore a ROS 2 controller. The documented FRC application workflow remains WPILib-based. For this repository, Java/WPILib stays the reference application path.

### Simulation and NetworkTables work

Post-alpha7 commits also add Driver Station alerts to the simulation GUI, improve simulated-device tree display, connect `mrclib` team number handling, and fix NetworkTables queue/subscription bookkeeping. These changes are relevant to future simulator acceptance tests but are not represented as alpha-7 released behavior. [S42](SOURCES.md#s42)

### RobotPy work

A 2026-09-16 main-branch commit improves native-wheel parity while explicitly noting remaining work such as type-stub and Linux wheel-processing gaps. Treat this as active upstream development, not proof of a complete release-matched RobotPy stack. [S42](SOURCES.md#s42)

## Vendor compatibility worksheet

The following is a documentation snapshot, not an installation lock. [S05](SOURCES.md#s05)

| Integration | Current alpha-7-era reference | Required follow-up |
|---|---|---|
| CTRE Phoenix 6 | `26.70.0-alpha-2` | Firmware `26.70.x`; checked note explicitly names Systemcore image 14 |
| REVLib | `2027.0.0-alpha-7` | Match device firmware and changed signal APIs |
| REV Hardware Client 2 | `1.4.2` Systemcore arm64 IPK | Tool version is separate from REVLib and device firmware |
| A301 firmware | `2027.0.0-prerelease.17` | Protocol-breaking prerelease-15 boundary requires coordinated REVLib/RHC2 versions |
| ReduxLib | `2027.0.0-alpha-7` in testing matrix | Verify vendor release before use |
| AdvantageKit | `27.0.0-alpha-5` in testing matrix | Verify sample/template compatibility |
| ChoreoLib | `2027.0.0-alpha-3` in testing matrix | Validate generated trajectory workflow |
| PathPlannerLib | No compatible alpha-7 release listed in matrix | Do not infer permanent lack of support |
| ThriftyLib | No compatible alpha-7 release listed in matrix | Recheck before selection |
| LimelightLib 2 | Alpha-7 vendordep | Match OS generation and timestamp expectations |
| AndyMark utility | Systemcore-specific IPK documented | Exact package build remains unverified here |

### CTRE details worth retaining

CTRE's 2026-09-18 release explicitly matches WPILib alpha 7 and Systemcore Alpha/Beta image 14, requires corresponding 26.70.x firmware, and does not support roboRIO. The integration guide supplies a matching CANivore package and uses `CANBus(CANPort)` for native ports. The vendor changelog adds bus-status counts/state, multi-bus signal operations, Telemetry/Alerts integration, and Systemcore bus-utilization reporting. API support, device firmware, configuration tools, simulation support, and controller-image validation must still be checked independently. [S19](SOURCES.md#s19) [S20](SOURCES.md#s20)

CTRE's “up to 40%” Java native-call improvement is a vendor-reported performance figure, not an independently observed result.

### REV details worth retaining

REV's alpha-7 notes replace raw bus integers with `CANPort`, rename `getBusId()` to `getCanPort()`, and retain signal wrappers that expose validity/recentness. Hardware Client 2 `1.4.2` is linked for Systemcore. The A301 page links firmware prerelease 17 and preserves the warning that firmware prerelease 15 introduced a breaking CAN-protocol transition requiring compatible REVLib and RHC2 versions. [S18](SOURCES.md#s18)

For non-A301 devices, the current note points to the latest 2026 firmware rather than inventing universal 2027 firmware: SPARK Flex `26.1.6`, SPARK MAX `26.1.5`, and PDH/PH/Servo Hub/MAXSpline Encoder `26.1.3`. [S18](SOURCES.md#s18)

### AndyMark details worth retaining

The official configuration index describes an IPK-based browser workflow for Systemcore. Its legacy utility page explicitly limits the older workflow to roboRIO/2026-or-earlier and points 2027 users elsewhere. The linked nested IPK details were still inaccessible in the 2026-09-21 review. Do not invent a version or claim installation succeeded. [S23](SOURCES.md#s23) [S24](SOURCES.md#s24)

## Python and LabVIEW

RobotPy deployment guidance exists, while upstream RobotPy packaging is still actively changing on `allwpilib` main. Treat that as evidence of a supported development direction, not proof of an internally consistent release profile assembled from arbitrary main-branch commits. Python deserves a separate compatibility exercise; Java remains this project's first teaching path. [S22](SOURCES.md#s22) [S42](SOURCES.md#s42)

LabVIEW remains a time-sensitive status question. The older WPILib page says it is not currently supported; FIRST's 2026-09-10 statement says early work to bring LabVIEW to Systemcore is underway. These can both be true. Do not claim either a ready-to-install release or permanent abandonment. [S12](SOURCES.md#s12) [S09](SOURCES.md#s09)

## Driver Station and desktop simulation

The new Driver Station is available for Windows, macOS, and Linux, and alpha 8 was published on 2026-09-14. Its release notes describe it as a patch over alpha 7 and include FMS fixes, simulation camera-loopback support, dashboard lookup fixes, display/console memory-leak fixes, and alert-key fixes. [S41](SOURCES.md#s41)

The SystemcoreTesting guide says the NI Driver Station remains compatible with currently released Systemcore images, while newer features require the new station. That is different from saying the new station supports roboRIO or that either station's competition legality is finalized outside the Game Manual. [S05](SOURCES.md#s05)

For our first simulator, use the matching WPILib simulation UI and verify its state/input path. Driver Station alpha 8 and post-alpha7 simulation-GUI changes are separate moving parts, so a real DS-to-desktop-simulation connection remains an acceptance test, not an assumed feature. [S14](SOURCES.md#s14) [S41](SOURCES.md#s41) [S42](SOURCES.md#s42)

## Documentation debt that should stay visible

- SystemcoreTesting's README currently contains a stale prose tip saying alpha 6 is latest even though its tooling link and matrix reference alpha 7. [S05](SOURCES.md#s05)
- Limelight's image-15 README heading still warns that listed changes may not all be present until final release even though GitHub prerelease assets have been published. [S39](SOURCES.md#s39) [S40](SOURCES.md#s40)
- A merged `allwpilib` main commit is not interchangeable with a tagged WPILib release. [S42](SOURCES.md#s42)

Those are reasons to pin exact sources and dates, not reasons to smooth over the differences.
