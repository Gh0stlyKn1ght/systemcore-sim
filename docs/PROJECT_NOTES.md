# Project notes

Updated: 2026-09-20. Research lead: @Gh0stlyKn1ght.

## Current directive

**Documentation first. Do not start implementation until the research gates are approved.** The [research dossier](research/README.md) supersedes the earlier suggestion to immediately generate a Java project.

## Objective

Give FRC Team 8721 students a Java/WPILib environment for learning robot behavior before physical Systemcore hardware is available. Teach architecture, controls, telemetry, and diagnosis without presenting simulated behavior as electrical truth.

## Proposed first implementation

A small differential-drive example with simulated encoders and IMU, a single vision source, explicit robot-state handling, NT4 telemetry, and one fault-diagnosis exercise. Small interfaces separate robot behavior from real and simulated I/O. Expand to multi-bus CAN, additional cameras, and a teaching console only after this vertical slice is reliable.

The classroom lesson must fit a 42-minute period. It is not a finished or tested lesson yet.

## Gates

| Gate | Deliverable | Status |
|---|---|---|
| Research | Source register, hardware distinctions, software matrix, unknowns | Initial dossier written; unresolved items remain |
| Architecture | Fidelity contract and reproducibility profile approved | Proposed |
| Environment | Minimal upstream example validated on supported Linux | Not attempted |
| Implementation | Small behavioral simulator and tests | Not started |
| Classroom | One reproducible lesson and recovery path | Not started |
| Hardware | Measured adapter validation on identified controller revision | No hardware testing performed |

Detailed exit criteria are in [the research roadmap](research/ROADMAP.md). Existing [classroom lab notes](CLASSROOM_LABS.md) remain a backlog, not evidence of delivery.

## Rules

Keep prerelease tools isolated from competition projects. Record controller hardware revision, OS image, WPILib, vendor dependencies, firmware, and host together. Never silently update `wpilib-reference.lock`. Do not include private exploit research in this classroom repository. Do not promise FMS compatibility or exact hardware emulation from desktop simulation.
