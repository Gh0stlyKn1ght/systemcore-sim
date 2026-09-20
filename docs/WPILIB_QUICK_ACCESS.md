# WPILib Quick Access

## Local reference cache

Run one script from the repository root:

```powershell
./scripts/fetch-wpilib-reference.ps1
```

```bash
bash ./scripts/fetch-wpilib-reference.sh
```

The scripts fetch pinned, shallow, sparse checkouts into:

- `.reference/wpilib-docs`
- `.reference/allwpilib`

The cache is intentionally excluded from Git. Commit only the lock file when changing upstream snapshots.

## Documentation paths fetched

- `software/systemcore-info`
- `wpilib-tools/robot-simulation`
- `commandbased`
- `dashboards`
- `hardware-apis`
- `networktables`
- networking and SystemCore imaging
- yearly overview and known issues

## Source areas fetched

- Java WPILib
- Java examples
- command framework
- NetworkTables
- math and control
- HAL
- simulation GUI
- design documents

## Direct official links

- [SystemCore introduction](https://github.com/wpilibsuite/wpilib-docs/blob/main/source/docs/software/systemcore-info/systemcore-introduction.rst)
- [SystemCore imaging](https://github.com/wpilibsuite/wpilib-docs/blob/main/source/docs/zero-to-robot/step-3/imaging-your-systemcore.rst)
- [Robot simulation](https://github.com/wpilibsuite/wpilib-docs/tree/main/source/docs/software/wpilib-tools/robot-simulation)
- [Command-based programming](https://github.com/wpilibsuite/wpilib-docs/tree/main/source/docs/software/commandbased)
- [WPILib Java examples](https://github.com/wpilibsuite/allwpilib/tree/main/wpilibjExamples/src/main/java/org/wpilib/examples)
- [Java simulation implementation](https://github.com/wpilibsuite/allwpilib/tree/main/wpilibj/src/main/java/org/wpilib/simulation)
- [SystemCore test repository](https://github.com/wpilibsuite/SystemcoreTesting)
- [SystemCore public OS material](https://github.com/LimelightVision/systemcore-os-public)

## Project generation

Do not construct a GradleRIO project by copying random files from `allwpilib`. Generate it with the installed WPILib VS Code extension so the Gradle wrapper, repositories, native libraries, and simulation GUI dependencies match the selected release.

Choose:

- Language: Java
- Project type: Command Robot
- Team number: 8721
- Desktop support: enabled
- Folder: this repository

Before committing the generated project, record its version in `VERSIONS.md` and verify:

```text
WPILib: Simulate Robot Code
```

The terminal equivalent depends on the generated release. Prefer the command exposed by the installed WPILib tooling and confirm it against the fetched documentation.
