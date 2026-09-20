# Systemcore research dossier

**Research lead: Gh0stly / @Gh0stlyKn1ght | As of: 2026-09-20 | Status: initial research baseline, not production certification**

## Research question

What can public evidence establish about Systemcore, which parts of the 2027 transition remain unsettled, and what is the smallest honest simulator that will help students learn before hardware arrives?

## Current scope decision

The documented FRC workflow is **WPILib-based, not simply ROS 2**. Begin with [roboRIO and Systemcore explained layer by layer](CONTROLLER_LAYERS.md), then read the [ROS 2 comparison and supporting sources](ROS2_COMPARISON.md) and [project-value assessment](PROJECT_VALUE.md) before adding dependencies or expanding the simulator.

Our proposed contribution is a small, reproducible teaching and validation layer on existing WPILib simulation. It is not a replacement OS, middleware stack, or physics engine. Whether custom code improves on an adapted upstream example remains a question to evaluate, not an established result.

All substantive findings must be saved in this project under the [repository-first research workflow](RESEARCH_WORKFLOW.md). The first follow-up is the [2026-09-20 scope assessment and subsequent clarifications](updates/2026-09-20.md).

## Reading route

| Order | Read | Outcome |
|---|---|---|
| 0 | [Controller layers](CONTROLLER_LAYERS.md) | Separate hardware, firmware, Linux, HAL, WPILib, application code, Driver Station, and ROS 2 |
| 1 | [Hardware](HARDWARE.md) | Distinguish published alpha specifications from beta changes |
| 2 | [System architecture](SYSTEM_ARCHITECTURE.md) | Explain the control, telemetry, configuration, and vision paths |
| 3 | [Software](SOFTWARE.md) | Understand migration and release compatibility |
| 4 | [Community field notes](COMMUNITY.md) | Separate firsthand experience from predictions |
| 5 | [Evidence ledger](EVIDENCE.md) | Know which answers are not settled |
| 6 | [Linux simulation plan](LINUX_SIMULATION.md) | Understand the proposed implementation without building yet |
| 7 | [Passive OSINT](PASSIVE_OSINT.md) | Continue research without probing third-party systems |
| 8 | [Research roadmap](ROADMAP.md) | Decide when evidence is sufficient to start building |

Use the [source register](SOURCES.md), [controller-layer source appendix](CONTROLLER_LAYERS.md#sources-and-review-boundaries), and [ROS 2 comparison source appendix](ROS2_COMPARISON.md#sources-and-access-notes) for provenance, the [snapshot](SNAPSHOT.md) for the initial review's version references and retrieval limitations, and the [glossary](GLOSSARY.md) for vocabulary. Dated follow-ups record subsequent changes without rewriting the initial snapshot as though they all happened together.

## Evidence language

**Official** means directly published by FIRST, WPILib, or the relevant vendor. It can still be draft, incomplete, or superseded. **Reported** means an identifiable community author's experience or statement. **Observed** is reserved for our own reproducible test results; none exist yet. **Proposed** means our design decision. **Unknown** means the reviewed sources do not settle the question.

Do not summarize a product's development history as though every feature existed simultaneously. Record both publication time and the hardware/software version discussed. A 2025 problem may be fixed; a 2026 release note claiming a fix still needs bench verification for our use case.

## What this project is not

It is not a vendor-approved manual, a final 2027 rulebook, a purchasing guarantee, an exact firmware emulator, an active vulnerability assessment, or a claim to have read every community post. The useful outcome is a traceable body of knowledge with explicit limits, not a claim of omniscience.

## Immediate conclusion

Keep the project in research mode. Before implementation, freeze one compatible software profile and prove the smallest upstream simulation example on a supported host. Build around application behavior and failure handling, not the appearance of the vendor dashboard. This is an engineering recommendation, not a statement that local validation has already succeeded.
