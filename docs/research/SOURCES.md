# Source register

Research lead: @Gh0stlyKn1ght. Access date for this review: **2026-09-20**. Entries distinguish an article's date from our access date. Mutable pages may change. Source IDs are used throughout the dossier.

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

[WPILib SystemcoreTesting README](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/README.md). Public test-program instructions, revision differences, compatibility tables, networking, and recovery. Repository commit recorded during this review; some older paragraphs lag current matrix entries.

<a id="s06"></a>
### S06: Limelight OS changelog and public resources

[systemcore-os-public README](https://github.com/LimelightVision/systemcore-os-public/blob/05eabac5f48b6d95032d80191b919c63c5fd94fa/README.md). Reviewed release sections 10 through 15 and repository tree. Release 15 is marked unreleased. Public binaries and examples do not establish that every component's source is published.

<a id="s07"></a>
### S07: FIRST transition preparation guidance

[FIRST team blast, 2026-07-09](https://info.firstinspires.org/frc-7-9-2026). Encourages prerelease simulation; public-sale details were still being finalized on that date.

<a id="s08"></a>
### S08: FIRST 2027 rule preview

[FIRST, 2026-08-31](https://community.firstinspires.org/2026-event-robot-rule-preview-for-2027-season-part-1). Includes CANivore transition policy. Preview, not the complete final Game Manual.

<a id="s09"></a>
### S09: FIRST and NI transition statement

[FIRST, 2026-09-10](https://community.firstinspires.org/thank-you-ni-opening-engineering-doors-for-a-generation-of-students). RIO transition and early LabVIEW-on-Systemcore work.

## WPILib and software

<a id="s10"></a>
### S10: WPILib installation prerequisites

[Installation guide](https://docs.wpilib.org/en/latest/docs/zero-to-robot/step-2/wpilib-setup.html). Mutable current documentation. Prerequisites reviewed separately from stale embedded release labels.

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

[WPILib Systemcore introduction](https://docs.wpilib.org/en/2027/docs/software/systemcore-info/systemcore-introduction.html). Processor overview and links. The 2027 documentation route redirected to the mutable latest site during this review.

<a id="s16"></a>
### S16: WPILib source and existing project pins

[WPILib source](https://github.com/wpilibsuite/allwpilib), [documentation source](https://github.com/wpilibsuite/wpilib-docs), and [2027 alpha 7 release](https://github.com/wpilibsuite/allwpilib/releases/tag/v2027.0.0-alpha-7). Existing local-reference pins are preserved in [the project lock](../../wpilib-reference.lock). They are reading snapshots, not proof of tested release compatibility.

<a id="s17"></a>
### S17: Public cross-compilation configuration

[Limelight cross.cmake](https://github.com/LimelightVision/systemcore-os-public/blob/05eabac5f48b6d95032d80191b919c63c5fd94fa/crosscomp_examples/cpp/cross.cmake). Explicit aarch64 target, Buildroot-named compiler, and sysroot configuration. File inspected, not executed.

## Vendor integrations

<a id="s18"></a>
### S18: REV 2027 test-program notes

[REV.md](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/REV.md). REVLib alpha 7, Hardware Client 2, and firmware guidance. Includes stale-signal and multi-bus API changes.

<a id="s19"></a>
### S19: CTRE Systemcore integration notes

[CTR-Phoenix.md](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/CTR-Phoenix.md). API-to-firmware compatibility and CANivore IPK instructions.

<a id="s20"></a>
### S20: CTRE release changelog

[CTRE changelog, 2026-09-18 section](https://api.ctr-electronics.com/changelog#20260918). Phoenix 26.70.0-alpha-2 compatibility and API changes. Prefer the dated section over an undated forum prediction.

<a id="s21"></a>
### S21: LimelightLib 2 concepts

[LimelightVision.md](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/LimelightVision.md). Reviewed compatibility, camera instances, result status, coordinates, pose queues, and configuration-override sections; not every trailing section was inspected.

<a id="s22"></a>
### S22: RobotPy test-program notes

[robotpy.md](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/robotpy.md). Deployment guidance exists, but the page still recommends an alpha-5-era package. Do not infer a tested alpha 7 Python stack from it.

<a id="s23"></a>
### S23: AndyMark Systemcore configuration index

[CAN Device Configuration](https://docs.andymark.com/frc-electronics/can-device-configuration). Official index explicitly describes an AndyMark IPK for browser-based Systemcore device setup and diagnostics. Linked download/instructions pages could not be retrieved in this session, so package version and installation details remain unverified.

<a id="s24"></a>
### S24: AndyMark legacy CAN utility

[AndyMark CAN Interface Utility](https://docs.andymark.com/frc-electronics/can-device-configuration/andymark-can-interface-utility). Explicitly separates roboRIO/2026-or-older tooling from Systemcore/2027 IPK tooling.

<a id="s25"></a>
### S25: AndyMark roboRIO listing

[NI roboRIO 2.0](https://andymark.com/products/ni-roborio-2-0). Current page reviewed. A historical forum quotation about legality was not reproduced by a current exact-text lookup; do not promote the old quotation to a current rule.

<a id="s26"></a>
### S26: AndyMark public Markdown documentation

[Documentation home](https://docs.andymark.com/) and [CAN firmware index](https://docs.andymark.com/frc-electronics/can-device-firmware.md). Publisher advertises Markdown forms and an `llms.txt` index. The latter was inaccessible in this session.

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

[Official cable PDF link](https://downloads.limelightvision.io/documents/systemcore_motioncore_cable_specifications.pdf). Located in S05, but retrieval failed. This dossier does not claim to have reviewed its cable drawings.

## How to add a source

Record title, publisher or author, exact URL, publication date if visible, access date, relevant section/page/post, hardware revision, software version, and retrieval method. Add a commit or artifact hash when genuinely captured. Distinguish an inaccessible lead from a reviewed source. Never invent a date, checksum, or test outcome.
