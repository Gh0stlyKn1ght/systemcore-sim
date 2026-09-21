# Evidence, contradictions, and unknowns

Research lead: @Gh0stlyKn1ght. Snapshot: 2026-09-21. Confidence describes the reviewed statement, not readiness for competition. Prior claims are retained with correction or supersession notes when new evidence changes the snapshot.

## Claim ledger

| ID | Narrow claim | Evidence status | Source / limit |
|---|---|---|---|
| C01 | Systemcore is the announced next control-system direction for FRC 2027 | Official direction | [S03](SOURCES.md#s03), [S09](SOURCES.md#s09); not a substitute for final rules |
| C02 | Early and beta hardware have different power/recovery/input details | Official test instructions | [S05](SOURCES.md#s05) |
| C03 | Original PDF numerical specifications apply to draft alpha hardware | Official historical draft | [S02](SOURCES.md#s02) |
| C04 | Current testing matrix pairs image 14+ with WPILib alpha 7+ | Official prerelease matrix | [S05](SOURCES.md#s05); do not extrapolate indefinitely |
| C05 | OS release 15 was marked unreleased in the 2026-09-20 inspected README | Historical official repository state, **superseded 2026-09-21** | [S06](SOURCES.md#s06); Alpha 15/Beta 15 prerelease assets were published 2026-09-19, see C15 and [S39](SOURCES.md#s39) |
| C06 | Debian 13 and Ubuntu 26.04 appear in current prerequisites | Official mutable documentation | [S10](SOURCES.md#s10); local validation not performed |
| C07 | Kali is not a listed supported host | Official list plus bounded inference | [S10](SOURCES.md#s10); this is not proof it cannot run |
| C08 | New DS desktop Linux/macOS support differs from FMS support | Official announcement | [S13](SOURCES.md#s13) |
| C09 | CANivore-added buses are permitted in the 2027 preview as transition support | Official preview | [S08](SOURCES.md#s08); recheck final manual and Team Update 00 |
| C10 | Phoenix and REV have version-specific Systemcore integrations | Official vendor material | [S18](SOURCES.md#s18), [S19](SOURCES.md#s19), [S20](SOURCES.md#s20) |
| C11 | AndyMark documents a Systemcore IPK workflow | Official index | [S23](SOURCES.md#s23); exact package version unverified |
| C12 | Team 604 reported substantial workload-specific performance improvement and practical issues | Firsthand community report | [S27](SOURCES.md#s27); not our benchmark |
| C13 | A useful behavioral simulator can precede real hardware | Proposed architecture supported by existing simulation | [S07](SOURCES.md#s07), [S14](SOURCES.md#s14) |
| C14 | Exact hardware emulation is not delivered or validated | Project status | No full-system simulator or hardware-parity validation has been performed |
| C15 | Limelight published Systemcore Alpha 15 build 382 and Beta 15 build 212 on 2026-09-19 | Official published prerelease | [S39](SOURCES.md#s39); GitHub marks them prerelease and the changelog still carries final-release caveats |
| C16 | FIRST Driver Station 2027.0.0-alpha-8 was published 2026-09-14 with FMS, simulation-camera, dashboard, memory, and alert fixes | Official released alpha build | [S41](SOURCES.md#s41); alpha naming means this is not treated as a final season release |
| C17 | Important WPILib changes have merged after the tagged alpha-7 release | Official upstream source, unreleased relative to alpha 7 | [S42](SOURCES.md#s42); do not teach `main` behavior as released alpha-7 behavior |
| C18 | REV's current documented A301 test stack includes REVLib alpha 7, RHC2 1.4.2, and A301 prerelease 17 | Official test-program material | [S18](SOURCES.md#s18); firmware and library compatibility remains version-coupled |
| C19 | The checked CTRE 26.70.0-alpha-2 note explicitly names Systemcore image 14, not image 15 | Official vendor release note | [S20](SOURCES.md#s20); no claim is made that image 15 is incompatible, only that explicit image-15 validation was not established here |
| C20 | Limelight documents different inactivity behavior for Alpha and Beta OLEDs in image-15 notes | Official vendor changelog claim | [S40](SOURCES.md#s40); not measured by this project |
| C21 | A 2026-09-20 Chief Delphi thread shows active supply/fallback concern but does not establish a verified shortage or final rule | Community report | [S43](SOURCES.md#s43); hearsay and informed comments are separated from official evidence |
| C22 | No new firsthand Reddit test result was promoted in the 2026-09-21 update | Search outcome with coverage limitation | Recent public search did not yield a stronger report; absence is not proof that none exists |

## Contradictions and stale guidance

| Topic | Sources disagree or differ | Resolution for this project |
|---|---|---|
| Release-15 status | S06 snapshot said image 15 unreleased; S39 shows Alpha 15/Beta 15 assets published 2026-09-19 | Preserve S06 as a dated snapshot; current status is published prerelease, not final/stable |
| USB addressing | Older image values differ from current guide | Select by image and host OS; never hardcode one universal subnet |
| Power and input bias | Early drawings differ from beta instructions | Hardware-revision-specific documentation takes precedence |
| OLED inactivity behavior | Alpha and Beta image-15 behavior differs | Treat display behavior as revision-specific; do not infer controller health from one revision's display rule |
| Brownout values | Draft PDF contains differing entries and alpha caveats | Keep unknown/configurable; bench validation required |
| Physical length | Early FIRST overview and dimensioned alpha drawing differ slightly | Do not make production mounts from a summary |
| Kernel/runtime versions | Early PDF versus later OS notes | Tie every software statement to an image release |
| SystemcoreTesting WPILib prose | Tooling/matrix say alpha 7 while one old tip says alpha 6 is latest | Use the explicit alpha-7 release and compatibility matrix; retain the stale sentence only as documentation debt |
| Driver Station pin | WPILib alpha-7 release note names DS alpha 7, but DS alpha 8 was published later | Pin DS separately from WPILib and use the release appropriate for the test/event |
| Telemetry examples | Older test README still shows SmartDashboard-style code; newer changelog changes APIs | Generate examples from a pinned current template later |
| Counter support | May removed-features page says unsupported; newer alpha hardware notes add counter-related support | Investigate exact current API and semantics, not a blanket yes/no |
| LabVIEW | Older unsupported status versus September work-underway statement | Future work is not an available release; unsupported today is not permanent abandonment |
| Host architecture/prerequisites | Alpha-7 release note names Ubuntu 26.04 while mutable current install docs also list Debian 13 and broader unsupported-glibc guidance | Record whether evidence comes from historical release notes or current mutable docs; validate the actual chosen host |
| Python version | RobotPy notes and top-level compatibility information can move at different times | Do not assemble an untested hybrid stack |
| CTRE image scope | CTRE 26.70.0-alpha-2 explicitly says image 14; top-level SystemcoreTesting says image >=14 for alpha7+ | Do not infer that every vendor independently validates every newer image; image-15 CTRE validation remains an open test/documentation item |
| AndyMark legality quote | Old forum quotation not found on current listing | Use current FIRST rules, not a stale retailer quotation |

Sources: [S02](SOURCES.md#s02), [S04](SOURCES.md#s04), [S05](SOURCES.md#s05), [S06](SOURCES.md#s06), [S08](SOURCES.md#s08), [S09](SOURCES.md#s09), [S10](SOURCES.md#s10), [S11](SOURCES.md#s11), [S12](SOURCES.md#s12), [S20](SOURCES.md#s20), [S22](SOURCES.md#s22), [S25](SOURCES.md#s25), [S39](SOURCES.md#s39), [S41](SOURCES.md#s41), [S42](SOURCES.md#s42).

## Open questions, prioritized

| Priority | Question | Why it matters | Evidence needed |
|---|---|---|---|
| P0 | Which exact release profile will we support first now that image 15 prereleases exist? | Prevent incompatible APIs and native dependencies | Installed minimal example with recorded Alpha/Beta hardware revision, image, WPILib, DS, vendors, and firmware |
| P0 | What are the current SmartIO ownership, counter, and mode semantics? | Avoid teaching incorrect hardware rules | Pinned source/API review, then bench checks |
| P0 | Which state/input path works in the selected desktop simulation release? | Required for the first lesson | Minimal release-matched simulation acceptance test |
| P0 | What are the final FRC controller/radio requirements? | Competition legality | Final 2027 Game Manual and Team Updates |
| P0 | Does CTRE 26.70.0-alpha-2 operate correctly with the published Systemcore image-15 prereleases? | Vendor-stack compatibility | Explicit CTRE documentation or bounded hardware regression test |
| P1 | What is the production revision, price, package contents, and delivery plan? | Procurement and mounting | Explicit current FIRST/vendor announcement |
| P1 | What is the exact onboard IMU and calibration behavior? | Sensor assumptions and tests | Official identification or validated hardware evidence |
| P1 | Which native CAN-FD vendor features are fully supported? | Bus architecture and procurement | Current vendor statements plus device-level tests |
| P1 | Are boot-time USB logging and client-connectivity issues resolved for our selected profile? | Reliability and evidence retention | Repeated power-cycle regression tests |
| P1 | What is the current RobotPy compatibility set? | A second teaching path | Matching packages, vendors, and successful tests |
| P1 | Which post-alpha7 `allwpilib` changes land in the next tagged release? | Prevent curriculum drift against `main` | Next release notes/tag comparison |
| P2 | Is full source sufficient for reproducible controller-image rebuilding? | Separate OS/emulation research | Source/license/build inventory, not a repository name |
| P2 | How do thermal load and vision affect deadlines? | Later performance sizing | Controlled hardware measurements |

## Evidence promotion rule

A report becomes an **Observed** result only after we record the environment, procedure, inputs, measurements, and artifacts necessary to reproduce it. A vendor's claimed fix remains **Official** until we test our relevant case. A merged upstream commit remains **merged/unreleased** until a tagged release containing it is identified. A new implementation decision must cite the evidence it depends on or label its parameter as hypothetical.

No document in this dossier should claim 100% knowledge or 100% project completion. Unknowns are part of the research output.
