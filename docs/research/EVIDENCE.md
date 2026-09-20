# Evidence, contradictions, and unknowns

Research lead: @Gh0stlyKn1ght. Snapshot: 2026-09-20. Confidence describes the reviewed statement, not readiness for competition.

## Claim ledger

| ID | Narrow claim | Evidence status | Source / limit |
|---|---|---|---|
| C01 | Systemcore is the announced next control-system direction for FRC 2027 | Official direction | [S03](SOURCES.md#s03), [S09](SOURCES.md#s09); not a substitute for final rules |
| C02 | Early and beta hardware have different power/recovery/input details | Official test instructions | [S05](SOURCES.md#s05) |
| C03 | Original PDF numerical specifications apply to draft alpha hardware | Official historical draft | [S02](SOURCES.md#s02) |
| C04 | Current testing matrix pairs image 14+ with WPILib alpha 7+ | Official prerelease matrix | [S05](SOURCES.md#s05); do not extrapolate indefinitely |
| C05 | OS release 15 is marked unreleased in inspected README | Official repository state | [S06](SOURCES.md#s06) |
| C06 | Debian 13 and Ubuntu 26.04 appear in current prerequisites | Official documentation | [S10](SOURCES.md#s10); local validation not performed |
| C07 | Kali is not a listed supported host | Official list plus bounded inference | [S10](SOURCES.md#s10); this is not proof it cannot run |
| C08 | New DS desktop Linux/macOS support differs from FMS support | Official announcement | [S13](SOURCES.md#s13) |
| C09 | CANivore-added buses are permitted in the 2027 preview as transition support | Official preview | [S08](SOURCES.md#s08); recheck final manual |
| C10 | Phoenix and REV have version-specific Systemcore integrations | Official vendor material | [S18](SOURCES.md#s18), [S19](SOURCES.md#s19), [S20](SOURCES.md#s20) |
| C11 | AndyMark documents a Systemcore IPK workflow | Official index | [S23](SOURCES.md#s23); exact download unverified |
| C12 | Team 604 reported substantial workload-specific performance improvement and practical issues | Firsthand community report | [S27](SOURCES.md#s27); not our benchmark |
| C13 | A useful behavioral simulator can precede real hardware | Proposed architecture supported by existing simulation | [S07](SOURCES.md#s07), [S14](SOURCES.md#s14) |
| C14 | Exact hardware emulation is not delivered or validated | Project status | No simulator implementation in this update |

## Contradictions and stale guidance

| Topic | Sources disagree or differ | Resolution for this project |
|---|---|---|
| USB addressing | Older image values differ from current guide | Select by image and host OS; never hardcode one universal subnet |
| Power and input bias | Early drawings differ from beta instructions | Hardware-revision-specific documentation takes precedence |
| Brownout values | Draft PDF contains differing entries and alpha caveats | Keep unknown/configurable; bench validation required |
| Physical length | Early FIRST overview and dimensioned alpha drawing differ slightly | Do not make production mounts from a summary |
| Kernel/runtime versions | Early PDF versus later OS notes | Tie every software statement to an image release |
| Telemetry examples | Older test README still shows SmartDashboard-style code; newer changelog changes APIs | Generate examples from a pinned current template later |
| Counter support | May removed-features page says unsupported; newer alpha hardware notes add counter-related support | Investigate exact current API and semantics, not a blanket yes/no |
| LabVIEW | Older unsupported status versus September work-underway statement | Future work is not an available release; unsupported today is not permanent abandonment |
| Host architecture | General install page and alpha-7 installer changes differ on ARM availability | Use a conservative x86-64 first profile; inspect actual release assets |
| Python version | RobotPy note lags current WPILib matrix | Do not assemble an untested hybrid stack |
| AndyMark legality quote | Old forum quotation not found on current listing | Use current FIRST rules, not a stale retailer quotation |

Sources: [S02](SOURCES.md#s02), [S04](SOURCES.md#s04), [S05](SOURCES.md#s05), [S06](SOURCES.md#s06), [S09](SOURCES.md#s09), [S10](SOURCES.md#s10), [S11](SOURCES.md#s11), [S12](SOURCES.md#s12), [S22](SOURCES.md#s22), [S25](SOURCES.md#s25).

## Open questions, prioritized

| Priority | Question | Why it matters | Evidence needed |
|---|---|---|---|
| P0 | Which exact release profile will we support first? | Prevent incompatible APIs and native dependencies | Installed minimal example with recorded versions |
| P0 | What are the current SmartIO ownership, counter, and mode semantics? | Avoid teaching incorrect hardware rules | Pinned source/API review, then bench checks |
| P0 | Which state/input path works in alpha-7 desktop simulation? | Required for the first lesson | Minimal simulation acceptance test |
| P0 | What are the final FRC controller/radio requirements? | Competition legality | Final Game Manual and updates |
| P1 | What is the production revision, price, package contents, and delivery plan? | Procurement and mounting | Explicit current FIRST/vendor announcement |
| P1 | What is the exact onboard IMU and calibration behavior? | Sensor assumptions and tests | Official identification or validated hardware evidence |
| P1 | Which native CAN-FD vendor features are fully supported? | Bus architecture and procurement | Current vendor statements plus device-level tests |
| P1 | Are boot-time USB logging and client-connectivity issues resolved for our profile? | Reliability and evidence retention | Repeated power-cycle regression tests |
| P1 | What is the current RobotPy compatibility set? | A second teaching path | Matching packages, vendors, and successful tests |
| P2 | Is full source sufficient for reproducible controller-image rebuilding? | Separate OS/emulation research | Source/license/build inventory, not a repository name |
| P2 | How do thermal load and vision affect deadlines? | Later performance sizing | Controlled hardware measurements |

## Evidence promotion rule

A report becomes an **Observed** result only after we record the environment, procedure, inputs, measurements, and artifacts necessary to reproduce it. A vendor's claimed fix remains **Official** until we test our relevant case. A new implementation decision must cite the evidence it depends on or label its parameter as hypothetical.

No document in this dossier should claim 100% knowledge or 100% project completion. Unknowns are part of the research output.
