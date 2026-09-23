# SystemCore research and classroom simulator

**Research lead and project author: [Gh0stly / @Gh0stlyKn1ght](https://github.com/Gh0stlyKn1ght)**  
**Research snapshot: 2026-09-20 | Phase 0: course frontend and architecture | Robot implementation: not started**

An independent, documentation-first research project for understanding Limelight Systemcore and the FRC 2027 transition, followed by a Java/WPILib behavioral simulator for FRC Team 8721.

![Limelight Systemcore development-unit product rendering, showing the early hardware enclosure and connectors](https://limelightvision.io/cdn/shop/files/68049783-9d89-4c4e-aae3-32a3091318a1.png?v=1749759827&width=800)

*Image credit: Limelight Vision. [Original product page](https://limelightvision.io/products/systemcore-development-unit). This early development-unit rendering is not a final-production wiring reference. The image is embedded from the vendor CDN, not relicensed as project artwork. [Attribution and image status](ATTRIBUTION.md).*

## Course frontend

The repository now includes a verified **Fumadocs + Next.js course frontend**. The course begins at `/docs` and gives each controller layer its own section:

1. physical hardware;
2. startup and I/O support;
3. operating environment;
4. hardware abstraction;
5. WPILib and vendor libraries; and
6. the team-written robot application.

Each layer compares roboRIO and Systemcore, identifies what can be tested in software, and marks what still requires physical validation. The frontend was type-checked and production-built before it was committed. This does not mean the Java/WPILib robot application exists yet.

For local course development:

```bash
npm install
npm run dev
```

Use `npm run types:check` and `npm run build` before merging course changes.

## Course build status

Modules 01 through 10 now have complete first editions:

- controller layers;
- WPILib foundations;
- Java robot code;
- Driver Station;
- CAN and devices;
- NetworkTables and telemetry;
- deterministic desktop simulation;
- automated testing;
- bounded autonomous behavior; and
- vision observation contracts.

The repository includes hardware-free Java checks for behavior, telemetry freshness and authority, drivetrain simulation, 1,681 automated input pairs with mutation detection, autonomous completion and fault paths, and vision freshness, geometry, quality, authority, and bounded corrections. Module 11 remains blocked on compatible Systemcore hardware and reviewed bench procedures.

See the [implementation status](content/docs/implementation-status.mdx), [course map](content/docs/course-map.mdx), and [build guide](content/docs/build-guide.mdx). The build guide starts with existing classroom computers, treats Raspberry Pi 5 systems as optional shared coprocessors, and reserves Systemcore-specific claims for real supported hardware.

Verification:

```bash
npm test
npm run types:check
npm run build
```

The current verified production build generates 286 pages across documentation, searchable content, LLM-readable routes, and Open Graph images.

## Coding and testing goal

The intended deliverable is **Java/WPILib robot code that we can build, test automatically, run in desktop simulation, and later compare with real Systemcore hardware**. **Java is the primary implementation, teaching, and validation language for this repository.** ROS 2 is not a prerequisite. Research supports this deliverable rather than replacing implementation.

C++ and Python remain supported research tracks. When Systemcore, WPILib, vendor-library, simulation, deployment, or tooling behavior differs by language, we will document those findings and maintain compatibility notes or focused examples. They should not delay the Java reference implementation.

Start with the [FRC apps and installation checklist](docs/development/FRC_TOOLKIT.md) and [Java build, test, and simulation workflow](docs/development/JAVA_TESTING_WORKFLOW.md). They distinguish software-only development tools, hardware configuration apps, robot-code libraries, and device firmware. The Java application and CI described there have not been created yet.

## Start with the research

Read the [research dossier](docs/research/README.md), then the [evidence and contradictions ledger](docs/research/EVIDENCE.md). Specifications, software releases, community observations, and our proposed simulator behavior are deliberately separated.

| Question | Documentation |
|---|---|
| What is publicly established about the hardware? | [Hardware and revision differences](docs/research/HARDWARE.md) |
| How do the processors, buses, vision, and robot program fit together? | [System architecture](docs/research/SYSTEM_ARCHITECTURE.md) |
| What software is changing for 2027? | [Software and vendor compatibility](docs/research/SOFTWARE.md) |
| What are teams actually reporting? | [Reddit and Chief Delphi field notes](docs/research/COMMUNITY.md) |
| How should we simulate it on Linux? | [Debian, Ubuntu, and Kali plan](docs/research/LINUX_SIMULATION.md) |
| How can we investigate without probing vendor infrastructure? | [Passive OSINT playbook](docs/research/PASSIVE_OSINT.md) |
| What is verified, unresolved, or next? | [Evidence ledger](docs/research/EVIDENCE.md), [research gates](docs/research/ROADMAP.md), [snapshot](docs/research/SNAPSHOT.md) |
| Where did each claim come from? | [Source register](docs/research/SOURCES.md) |

## Current boundary

This update adds research and planning only. It does not generate a robot application, install dependencies, flash a controller, run a simulator, or validate hardware. The existing reference scripts and `wpilib-reference.lock` remain available and unchanged; see [WPILib quick access](docs/WPILIB_QUICK_ACCESS.md).

The intended first implementation is a **behavioral simulator**, not an exact Systemcore emulator. Robot behavior will sit behind small interfaces such as `DriveIO`, `IMUIO`, `VisionIO`, and `SmartIO`. We will reuse WPILib desktop simulation rather than reproduce its HAL or build a new operating system. See the [project scope](docs/PROJECT_NOTES.md) and [architecture decision](docs/ARCHITECTURE.md).

**Before building:** approve the research gates, select one compatible prerelease profile, and validate a minimal upstream example on a supported host. Existing classroom labs are proposals, not tested functionality.

## Research standard

Every consequential claim must identify its source, hardware generation, software version when relevant, and evidence class. An official alpha specification is not a final production guarantee. A community report is not our measurement. A public repository name does not establish that every component is open source.

Public research uses search engines, public documents, published repositories, and community discussions. No vendor port scans, endpoint guessing, authentication attempts, or robot-network interaction are part of this project.

## Credits and licensing

Research direction, original synthesis, architecture decisions, and classroom adaptation belong to **Gh0stly / @Gh0stlyKn1ght**, with AI-assisted source discovery and drafting disclosed in [ATTRIBUTION.md](ATTRIBUTION.md). FIRST, WPILib contributors, Limelight Vision, vendors, and community authors retain credit for their own work. This project is not endorsed by those organizations.

Project licensing remains undecided. No upstream code, firmware, OS image, or vendor photograph is automatically covered by a future project license.
