# SystemCore Classroom Simulator

A Java/WPILib teaching project for FRC Team 8721. The goal is to let students learn robot lifecycle, command-based programming, simulation, telemetry, controls, vision, SmartIO concepts, and fault diagnosis before real SystemCore hardware is available.

## Current status

**Phase 0: project and reference bootstrap**

The repository currently contains the project notes and a reproducible way to fetch the relevant WPILib documentation and source code. The executable robot project is the next phase.

## Start here

1. Read [the project notes](docs/PROJECT_NOTES.md).
2. Read [WPILib quick access](docs/WPILIB_QUICK_ACCESS.md).
3. Fetch the pinned WPILib reference material:
   - Windows PowerShell: `./scripts/fetch-wpilib-reference.ps1`
   - Linux/macOS: `bash ./scripts/fetch-wpilib-reference.sh`
4. Use the installed WPILib VS Code command **WPILib: Create a new project** to generate a Java command-based project in this repository.
5. Run it with **WPILib: Simulate Robot Code**.

The fetch scripts place upstream material in `.reference/`, which is intentionally ignored by Git. This keeps the repository small while giving the coach and students fast local source access.

## Notes

- [Project scope and backlog](docs/PROJECT_NOTES.md)
- [Architecture](docs/ARCHITECTURE.md)
- [Classroom lab sequence](docs/CLASSROOM_LABS.md)
- [WPILib quick access](docs/WPILIB_QUICK_ACCESS.md)
- [Public repository findings](docs/RESEARCH_FINDINGS.md)
- [Tested versions](VERSIONS.md)

## Architecture rule

Robot commands and subsystems must depend on small interfaces such as `DriveIO`, `VisionIO`, `IMUIO`, and `SmartIO`. Simulation implementations come first. Real SystemCore implementations are added only after hardware validation.

## Scope boundary

This is a classroom and robot-development project, not an exact SystemCore emulator. It does not reproduce RP2350 firmware, electrical timing, FMS behavior, vendor firmware, or the SystemCore OS image.

Private security research does not belong in this student-facing repository.

## Upstream sources

- [WPILib documentation](https://github.com/wpilibsuite/wpilib-docs)
- [WPILib source](https://github.com/wpilibsuite/allwpilib)
- [SystemCore public OS material](https://github.com/LimelightVision/systemcore-os-public)
- [SystemCore testing repository](https://github.com/wpilibsuite/SystemcoreTesting)

## License

Project licensing is not yet selected. Upstream repositories retain their own licenses. The reference-fetch workflow does not copy upstream source into this repository.
