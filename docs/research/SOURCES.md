# Source register

Research lead: @Gh0stlyKn1ght. Access date for this review: **2026-09-21**. Entries distinguish an article's date from our access date. Mutable pages may change. Source IDs are used throughout the dossier. Earlier source states are retained when a later source supersedes them.

## Official hardware and program information

<a id="s01"></a>
### S01: Limelight development-unit listing and supplied image

[Product listing](https://limelightvision.io/products/systemcore-development-unit). Undated product page. [Supplied image](https://limelightvision.io/cdn/shop/files/68049783-9d89-4c4e-aae3-32a3091318a1.png?v=1749759827&width=800). Listing reviewed; image remotely embedded. A local image download was unsuccessful. The listing is not a final pricing announcement.

<a id="s02"></a>
### S02: Systemcore alpha specification

[Limelight PDF](https://downloads.limelightvision.io/documents/systemcore_specifications_june15_2025_alpha.pdf). Draft dated 2025-06-15, with later revision entries including 2025-10-01. Fourteen pages; relevant tables and drawings inspected as page images. Historical alpha specification, not a production datasheet.

<a id="s03"></a>
### S03: FIRST controller hardware update

[FIRST, 2025-03-19](https://community.firstinspires.org/march-updates-on-the-future-robot-controller). Hardware ports, integration plans, and FRC/FTC context.

<a id="s04"></a>
### S04: FIRST alpha first wave

[FIRST, 2025-06-12](https://community.firstinspires.org/systemcore-alpha-testing-first-wave). Early distribution and physical overview. Historical announcement.

<a id="s05"></a>
### S05: Official Systemcore testing guide

[WPILib SystemcoreTesting README](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/README.md). Public test-program instructions, revision differences, compatibility tables, networking, and recovery. Repository commit recorded during the 2026-09-21 review. Its tooling table and compatibility matrix reference WPILib alpha 7, while one older prose tip still says alpha 6 is latest; the explicit release link and matrix take precedence for this dossier.

<a id="s06"></a>
### S06: Limelight OS changelog and public resources, 2026-09-20 snapshot

[systemcore-os-public README at `05eabac5`](https://github.com/LimelightVision/systemcore-os-public/blob/05eabac5f48b6d95032d80191b919c63c5fd94fa/README.md). Reviewed release sections 10 through 15 and repository tree on 2026-09-20. At that snapshot, release 15 was described as unreleased. **Superseded on 2026-09-21 by S39 and S40, which document published Alpha 15/Beta 15 prerelease assets and a newer changelog commit.** Retained to preserve the historical evidence state.

<a id="s07"></a>
### S07: FIRST transition preparation guidance

[FIRST team blast, 2026-07-09](https://info.firstinspires.org/frc-7-9-2026). Encourages prerelease simulation; public-sale details were still being finalized on that date.

<a id="s08"></a>
### S08: FIRST 2027 rule preview

[FIRST, 2026-08-31](https://community.firstinspires.org/2026-event-robot-rule-preview-for-2027-season-part-1). Includes CANivore transition policy. The page explicitly says rules are not all finalized and directs teams to the final Game Manual and Team Update 00. Preview, not the complete final Game Manual.

<a id="s09"></a>
### S09: FIRST and NI transition statement

[FIRST, 2026-09-10](https://community.firstinspires.org/thank-you-ni-opening-engineering-doors-for-a-generation-of-students). RIO transition and early LabVIEW-on-Systemcore work. Work underway is not an available release.

## WPILib and software

<a id="s10"></a>
### S10: WPILib installation prerequisites

[Installation guide](https://docs.wpilib.org/en/latest/docs/zero-to-robot/step-2/wpilib-setup.html). Mutable current documentation. Prerequisites reviewed separately from historical release notes. Current documentation lists Debian 13 Trixie and Ubuntu 26.04 among supported 64-bit Linux hosts and states that other distributions meeting the glibc requirement may work but are unsupported.

<a id="s11"></a>
### S11: WPILib 2027 changelog

[New for 2027](https://docs.wpilib.org/en/latest/docs/yearly-overview/yearly-changelog.html). Reviewed alpha 5, 6, and 7 changes. Changelog entries are release-specific, not universal guarantees about older templates.

<a id="s12"></a>
### S12: Removed features

[WPILib removed features](https://docs.wpilib.org/en/latest/docs/yearly-overview/removed-features.html). Page displays 2026-05-05 update date. Some hardware statements require reconciliation with newer alpha changes.

<a id="s13"></a>
### S13: New Driver Station announcement

[WPILib, 2026-04-24](https://wpilib.org/blog/the-2027-first-driver-station). Platform support, FMS restrictions, input handling, and design rationale.

<a id="s14"></a>
### S14: Desktop simulation introduction

[WPILib simulation](https://docs.wpilib.org/en/latest/docs/software/wpilib-tools/robot-simulation/introduction.html). Existing simulation capability. Check instructions against the chosen 2027 template.

<a id="s15"></a>
### S15: Systemcore introduction

[WPILib Systemcore introduction](https://docs.wpilib.org/en/2027/docs/software/systemcore-info/systemcore-introduction.html). Processor overview and links. The 2027 documentation route redirected to the mutable latest site during the earlier review.

<a id="s16"></a>
### S16: WPILib source and existing project pins

[WPILib source](https://github.com/wpilibsuite/allwpilib), [documentation source](https://github.com/wpilibsuite/wpilib-docs), and [2027 alpha 7 release](https://github.com/wpilibsuite/allwpilib/releases/tag/v2027.0.0-alpha-7). Alpha 7 was published 2026-09-01. Its release notes say Systemcore image 14 is required, Alpha 6 and earlier vendordeps are incompatible, and the release includes major migration changes. Existing local-reference pins are preserved in [the project lock](../../wpilib-reference.lock). They are reading snapshots, not proof of tested release compatibility.

<a id="s17"></a>
### S17: Public cross-compilation configuration

[Limelight cross.cmake](https://github.com/LimelightVision/systemcore-os-public/blob/05eabac5f48b6d95032d80191b919c63c5fd94fa/crosscomp_examples/cpp/cross.cmake). Explicit aarch64 target, Buildroot-named compiler, and sysroot configuration. File inspected, not executed.

## Vendor integrations

<a id="s18"></a>
### S18: REV 2027 test-program notes

[REV.md](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/REV.md). REVLib `2027.0.0-alpha-7`, Hardware Client 2 `1.4.2` Systemcore IPK, CANPort migration, firmware guidance, stale-signal APIs, and multi-bus support. For non-A301 devices the note points to current 2026 firmware rather than a universal 2027 firmware release.

<a id="s19"></a>
### S19: CTRE Systemcore integration notes

[CTR-Phoenix.md](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/CTR-Phoenix.md). API-to-firmware compatibility and CANivore IPK instructions.

<a id="s20"></a>
### S20: CTRE release changelog

[CTRE changelog, 2026-09-18 section](https://api.ctr-electronics.com/changelog#20260918). Phoenix 6 `26.70.0-alpha-2` compatibility with WPILib alpha 7 and Systemcore Alpha/Beta image 14, requiring 26.70.x device firmware. Does not support roboRIO. Includes API and Systemcore/Motioncore changes. Performance numbers remain vendor claims until reproduced.

<a id="s21"></a>
### S21: LimelightLib 2 concepts

[LimelightVision.md](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/LimelightVision.md). Reviewed compatibility, camera instances, result status, coordinates, pose queues, and configuration-override sections; not every trailing section was inspected.

<a id="s22"></a>
### S22: RobotPy test-program notes

[robotpy.md](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/robotpy.md). Deployment guidance exists, but the page has historically lagged the top-level compatibility matrix. Do not assemble a mixed untested Python stack from separate alpha notes.

<a id="s23"></a>
### S23: AndyMark Systemcore configuration index

[CAN Device Configuration](https://docs.andymark.com/frc-electronics/can-device-configuration). Official index explicitly describes an AndyMark IPK for browser-based Systemcore device setup and diagnostics. Linked nested download/instructions pages could not be retrieved in the 2026-09-21 review, so package version and installation details remain unverified.

<a id="s24"></a>
### S24: AndyMark legacy CAN utility

[AndyMark CAN Interface Utility](https://docs.andymark.com/frc-electronics/can-device-configuration/andymark-can-interface-utility). Explicitly separates roboRIO/2026-or-older tooling from Systemcore/2027 IPK tooling.

<a id="s25"></a>
### S25: AndyMark roboRIO listing

[NI roboRIO 2.0](https://andymark.com/products/ni-roborio-2-0). Current page reviewed. A historical forum quotation about legality was not reproduced by a current exact-text lookup; do not promote the old quotation to a current rule.

<a id="s26"></a>
### S26: AndyMark public Markdown documentation

[Documentation home](https://docs.andymark.com/) and [CAN firmware index](https://docs.andymark.com/frc-electronics/can-device-firmware.md). Publisher advertises Markdown forms and an `llms.txt` index. The latter was inaccessible in the earlier review.

## Community primary reports and discussion

<a id="s27"></a>
### S27: Team 604 field reports

[Eugene Fang, Team 604 Quixilver](https://www.chiefdelphi.com/t/team-604-quixilver-systemcore-alpha-testing/504903). Updates dated 2025-08-02, 2025-11-16, and 2026-07-28, plus replies. Firsthand reports, not independent benchmark reproduction.

<a id="s28"></a>
### S28: CANivore purchasing and compatibility debate

[Chief Delphi thread](https://www.chiefdelphi.com/t/canivore-and-2027-control-system/503391). Discussion began 2025-06-19. Shows concerns and speculation; later FIRST/CTRE sources supersede parts of it.

<a id="s29"></a>
### S29: FRC/FTC rollout discussion

[Chief Delphi, page 4](https://www.chiefdelphi.com/t/systemcore-motioncore-rollout-questions-frc-ftc/508694?page=4). Reviewed 2025-12-01/02 posts. Contains explicit guesses, older procurement assumptions, and a WPILib maintainer statement about shared software direction.

<a id="s30"></a>
### S30: Reddit programming migration question

[Systemcore Question, r/FRC](https://www.reddit.com/r/FRC/comments/1qgmr5c/systemcore_question/). Reviewed visible post and comments. Rendered page uses relative age; no precise timestamp is asserted here. Useful for identifying confusion about telemetry and dashboard changes.

<a id="s31"></a>
### S31: Reddit Python discussion

[Let's hope that Systemcore is Python Friendly, r/FRC](https://www.reddit.com/r/FRC/comments/1sr6weq/lets_hope_that_systemcore_is_python_friendly/). Visible comments reviewed; rendered relative age recorded rather than inventing an absolute date. Performance and future-legality assertions are not accepted as specifications.

<a id="s32"></a>
### S32: Teardown discussion

[Systemcore Teardown Pictures, Chief Delphi](https://www.chiefdelphi.com/t/systemcore-teardown-pictures/503555). Public visual reference located. This dossier does not claim component identification or a complete teardown audit from those photographs.

<a id="s33"></a>
### S33: Unresolved IMU-model lead

[IMU model/maker question, r/FRC](https://www.reddit.com/r/FRC/comments/1wkvfaz/imu_modelmaker_in_systemcore/). Search lead located; direct retrieval failed. Not usable evidence for a chip model.

## Research methods and simulation limits

<a id="s34"></a>
### S34: Passive scanning terminology

[CyCognito passive scanning glossary](https://www.cycognito.com/glossary/passive-scanning.php). User-supplied commercial explainer, used only for terminology, not as authority on Systemcore behavior.

<a id="s35"></a>
### S35: Active versus passive reconnaissance

[CyCognito comparison](https://www.cycognito.com/learn/exposure-management/active-vs-passive-reconnaissance/). User-supplied methodological background. Our playbook uses an explicit distinction between third-party research and direct public-document GET requests.

<a id="s36"></a>
### S36: Linux SocketCAN

[Linux kernel documentation](https://docs.kernel.org/networking/can.html). Primary reference for CAN network interfaces and virtual CAN. A virtual bus is not an electrical timing model.

<a id="s37"></a>
### S37: QEMU generic ARM platform

[QEMU virt documentation](https://www.qemu.org/docs/master/system/arm/virt.html). Primary reference showing that the generic virtual platform is not a model of one physical board.

<a id="s38"></a>
### S38: Cable specification retrieval limitation

[Official cable PDF link](https://downloads.limelightvision.io/documents/systemcore_motioncore_cable_specifications.pdf). Located in S05, but retrieval failed in the earlier review. This dossier does not claim to have reviewed its cable drawings.

## 2026-09-21 additions

<a id="s39"></a>
### S39: Published Systemcore Alpha 15 and Beta 15 prereleases

Limelight GitHub releases published 2026-09-19: [Alpha 15 build 382](https://github.com/LimelightVision/systemcore-os-public/releases/tag/limelightosr-2027.0.0-alpha15-382) and [Beta 15 build 212](https://github.com/LimelightVision/systemcore-os-public/releases/tag/limelightosr-2027.0.0-beta15-212). Both GitHub releases are marked prerelease and identify source commit `81dd853e1fd31e0b6e372018d9556840c9136435` with build date `2026-09-18T18:29:18-07:00`. Both provide image/update assets, aarch64 toolchains, kernel-source archives, and license manifests. Supersedes S06 only on the narrow question of whether release-15 assets had been published.

<a id="s40"></a>
### S40: Current Limelight image-15 changelog commit

[Limelight commit `8783d4c40a159fd9e17458c56f5ca9e708d2e767`, 2026-09-21](https://github.com/LimelightVision/systemcore-os-public/commit/8783d4c40a159fd9e17458c56f5ca9e708d2e767). Current README lists image-15 boot, Motioncore OTA, memory, camera, eMMC, and Motioncore changes. This commit adds that Beta OLEDs remain on at reduced brightness/framerate after inactivity while Alpha OLEDs continue to turn off. Release-note claim only; not independently tested here.

<a id="s41"></a>
### S41: FIRST Driver Station alpha 8 release

[FirstDriverStation-Public v2027.0.0-alpha-8](https://github.com/wpilibsuite/FirstDriverStation-Public/releases/tag/v2027.0.0-alpha-8), published 2026-09-14. Patch release over alpha 7. Release notes list simulation camera loopback, dashboard lookup, FMS, memory-leak, alert-key, and FTA UDP-flow fixes. Assets are published for Linux x64/arm64, Windows x64/arm64, and macOS. GitHub metadata says `prerelease: false`, but the version is explicitly alpha; classified here as a published alpha build, not a final season release.

<a id="s42"></a>
### S42: Selected post-alpha-7 WPILib main commits

Merged `allwpilib` source changes reviewed on 2026-09-21. These are **not** evidence that the functionality is present in the tagged alpha-7 release:

- [AOS to NetworkTables bridge, `813dc402`, 2026-09-16](https://github.com/wpilibsuite/allwpilib/commit/813dc4022128119375a7a33aaa4dd4d8a85da5c1): publishes selected AOS channels to NT and includes a Systemcore build target for `aosnt-bridge`.
- [Simulation DS alerts, `e7823869`, 2026-09-18](https://github.com/wpilibsuite/allwpilib/commit/e7823869d8240bae01f0f4e9233c87322337adcb).
- [Simulation device tree display, `5a659b2c`, 2026-09-18](https://github.com/wpilibsuite/allwpilib/commit/5a659b2c3b3549079cf3934a0da0826d6ba6d16a).
- [Java module support, `8e42a614`, 2026-09-18](https://github.com/wpilibsuite/allwpilib/commit/8e42a614691fdf6c1cf89d68f0fb98b2e26fbc03).
- [mrclib team-number connection, `e199568d`, 2026-09-18](https://github.com/wpilibsuite/allwpilib/commit/e199568da13f10926eae6ec3bca4bb0430452449).
- [RobotPy native-wheel parity work, `793da538`, 2026-09-16](https://github.com/wpilibsuite/allwpilib/commit/793da538b8d4f0cc3746b472968e3f96a7b5562c).
- [NT outgoing-queue bookkeeping, `d8da5256`, 2026-09-14](https://github.com/wpilibsuite/allwpilib/commit/d8da52562fe46cf8e49dbff0aaeea0faaff0438e) and [subscription send-mode recomputation, `5ae4836b`, 2026-09-14](https://github.com/wpilibsuite/allwpilib/commit/5ae4836b076b44aa6e071ef65d5417c44178ace1).

<a id="s43"></a>
### S43: Chief Delphi 2027 availability and fallback discussion

[When will FIRST state we can still use the roboRIO for 2027 season?, Chief Delphi, started 2026-09-20](https://www.chiefdelphi.com/t/when-will-first-state-we-can-still-use-the-roborio-for-2027-season/524319). Contains hearsay, explicitly unofficial distribution claims, informed community comments, and planning concerns. Useful for identifying team uncertainty, but not evidence of a verified Systemcore shortage, official delivery schedule, or final roboRIO legality.

## How to add a source

Record title, publisher or author, exact URL, publication date if visible, access date, relevant section/page/post, hardware revision, software version, and retrieval method. Add a commit or artifact hash when genuinely captured. Distinguish an inaccessible lead from a reviewed source. Never invent a date, checksum, or test outcome.
