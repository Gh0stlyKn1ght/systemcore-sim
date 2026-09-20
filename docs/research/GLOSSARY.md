# Working glossary

Research lead: @Gh0stlyKn1ght. Snapshot: 2026-09-20. These are explanatory definitions for the dossier, not new product specifications.

| Term | Meaning in this project |
|---|---|
| Systemcore | Limelight's new controller platform discussed for the FRC 2027 transition |
| Hardware alpha / beta | Distinct physical unit generations in the public testing program, not just software quality labels |
| CM5 | Compute Module 5, the Linux-capable host module |
| RP2350 | The separate microcontroller used for timing-oriented I/O functions |
| SmartIO | Configurable I/O resources whose function and ownership must be tracked |
| HAL | Hardware abstraction layer, separating robot-facing APIs from hardware or simulation implementations |
| CAN | A device communication bus; a bus identity is distinct from an individual device number |
| CAN-FD | CAN with flexible data-rate capabilities; hardware capability alone does not establish vendor-feature compatibility |
| CANivore | CTRE's CAN interface, with separate integration and competition-policy considerations |
| Motioncore | An additional control-system component appearing in the shared testing program; not a synonym for Systemcore |
| Driver Station | Operator-side software controlling robot state and handling inputs/feedback |
| FMS | Field Management System; competition integration is not implied by ordinary desktop connectivity |
| NT4 | NetworkTables 4, a telemetry/data communication protocol used by WPILib tooling |
| IPK | A package artifact used by documented Systemcore package workflows |
| Sysroot | Target headers and libraries used when compiling software for another runtime environment |
| Cross-compilation | Building for a different target architecture/runtime than the development host |
| Desktop simulation | Running robot behavior with simulated hardware on a development machine |
| Full-system emulation | Modeling a machine and its devices sufficiently to run its software stack, a different and larger task |
| Replay | Feeding recorded inputs through code to examine reproducibility and changed behavior |
| Stale data | A once-valid observation that is too old for the current decision |
| Ground truth | An independent reference used to assess measurement accuracy, not merely a second uncalibrated sensor |
| OSINT | Research using openly available information; it does not automatically imply zero direct network requests |
| Passive research | In this dossier, collection from third-party or already stored information without deliberately probing the target |
| Low-impact collection | Deliberate retrieval of a known public document, distinguishable from strictly passive research |

Platform-specific context: [S03](SOURCES.md#s03), [S05](SOURCES.md#s05), [S13](SOURCES.md#s13), [S15](SOURCES.md#s15), [S17](SOURCES.md#s17), [S19](SOURCES.md#s19), [S21](SOURCES.md#s21). General technical references: [S34](SOURCES.md#s34), [S35](SOURCES.md#s35), [S36](SOURCES.md#s36), [S37](SOURCES.md#s37).
