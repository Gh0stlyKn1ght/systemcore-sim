# Systemcore is not simply ROS 2

**Research lead: Gh0stly / @Gh0stlyKn1ght | Reviewed: 2026-09-20 | Public-source assessment, not a firmware inventory**

## Question and conclusion

Does Systemcore just use ROS 2, making this project redundant?

**No. The reviewed official FRC programming workflow is WPILib-based, not a ROS 2 application workflow.** The official testing guide directs teams to install WPILib, create and deploy a WPILib robot project, and use Driver Station. It provides a RobotPy route for Python. That is positive evidence about the supported workflow, rather than an inference from a failed keyword search. [R02](#r02)

The physical controller, its Linux operating environment, WPILib, NetworkTables, and ROS 2 are different layers. Sharing Linux, ARM processors, robot sensors, or publish/subscribe terminology does not make two platforms the same software stack.

This conclusion is bounded: we did not unpack controller images, obtain a complete software bill of materials, trace every process, or audit every vendor component. We cannot establish the universal absence of every ROS-related component. We also do not claim that a team could never integrate ROS 2. Neither possibility makes ROS 2 the documented native FRC programming model.

For the investment decision, see [Project value and scope](PROJECT_VALUE.md).

## Separate the layers

| Layer | Systemcore/FRC evidence | Relationship to ROS 2 |
|---|---|---|
| Physical controller | CM5 host, RP2350 reconfigurable I/O, onboard interfaces and sensing | Hardware is not a middleware framework [R01](#r01) |
| Controller operating environment | Limelight OS images, services, packages, and target toolchain | Running Linux does not establish a ROS 2 dependency [existing OS research](SOFTWARE.md) |
| Robot application | WPILib robot-project creation and deployment; vendor libraries | This is the documented application workflow, not a ROS-node launch procedure [R02](#r02) |
| Operator control | Driver Station participates in robot enablement and mode operation | Do not replace this with an assumed ROS topic contract [R02](#r02) |
| Telemetry/data exchange | NetworkTables implements typed, timestamped publish/subscribe through a server | Similar terminology does not mean ROS wire-protocol compatibility [R04](#r04) |
| ROS 2 application middleware | ROS client libraries use the RMW abstraction; DDS-backed implementations and a Zenoh implementation exist | This is a separate ecosystem and dependency decision [R03](#r03) [R08](#r08) |
| Desktop simulation | WPILib runs robot code against simulated hardware and models | ROS 2 is not a prerequisite listed by that workflow [R05](#r05) [R06](#r06) |

NetworkTables is not the whole robot control system. In particular, do not teach that every Driver Station message or CAN motor command travels through a NetworkTables topic. The table separates these responsibilities rather than collapsing them into one network arrow.

### Conceptual comparison

```text
Documented Systemcore/FRC workflow:
  Student robot application
    -> WPILib hardware abstractions and vendor APIs
    -> controller-specific hardware implementation
    -> robot I/O and devices

  Operator state/input: Driver Station integration
  Shared telemetry/data: NetworkTables and dashboard clients

ROS 2 communication architecture:
  Robot applications / ROS nodes
    -> ROS client libraries
    -> RMW middleware abstraction
    -> selected middleware implementation
```

This is an explanatory comparison, not a complete vendor process graph or electrical schematic. [R02](#r02) [R03](#r03) [R04](#r04)

## Why the distinction changes our design

Our first simulator should exercise the APIs, mode behavior, data contracts, and failure handling that the FRC student program will actually use. Recreating the lesson only as a ROS 2 node graph would test a different application integration. A similar-looking simulated drivetrain would not demonstrate that the student's WPILib code works.

This is our engineering conclusion from the documented workflows, not a statement that ROS 2 is inferior or that the two ecosystems cannot cooperate.

There is still substantial conceptual overlap: modular interfaces, coordinate frames, sensor freshness, timing, observability, state estimation, and failure handling. Those are useful ideas to transfer. Specific ROS graph behavior, middleware security configuration, or DDS assumptions must not be transplanted into NetworkTables without new evidence.

**ROS 2 itself should not be equated with one transport in every deployment.** The original RMW design discusses DDS abstraction; the current official `rmw_zenoh` repository provides a concrete non-DDS implementation. This also illustrates why broad claims such as "all robotics uses DDS" are unreliable. [R03](#r03) [R08](#r08)

## The actual duplication risk

The relevant existing capability is **WPILib simulation**, not ROS 2. WPILib already provides desktop execution, a simulation UI, dashboard connections, and mechanism models such as differential drive, flywheel, elevator, and arm simulation. We should reuse those facilities. [R05](#r05) [R06](#r06)

A project that merely renames an upstream drivetrain example or redraws a dashboard has limited additional engineering value. A well-attributed adaptation can still be useful for teaching, but it should not be marketed as a new simulation engine or original platform research.

Our proposed contribution is narrower: a documented Systemcore-oriented version profile, small reproducible exercises, explicit data-validity contracts, and repeatable fault scenarios. These are proposals, not claims that we have already implemented a missing feature or surveyed every alternative project.

## Could ROS 2 be integrated later?

A separate coprocessor or host could be investigated as an integration point, with an explicit bridge into the FRC application. That is a future design option, not verified native Systemcore support, a tested deployment recipe, or a claim of competition legality.

Such a bridge would need a documented mapping for units, frames, timestamps, validity, message ownership, and authority to request motion. A cautious first experiment would export observations only, without creating a new actuator-command path. These are proposed constraints, not an implementation.

Do not add ROS 2, Gazebo, a bridge, or controller-image modifications to the first classroom milestone. They would introduce another set of dependencies before we have proved the basic WPILib exercise.

## Evidence ledger for this follow-up

| ID | Finding | Classification | Limit |
|---|---|---|---|
| R-C01 | The official Systemcore FRC setup is a WPILib workflow | Official | Does not inventory every installed component |
| R-C02 | NetworkTables and ROS middleware are distinct communication systems | Official descriptions plus architectural comparison | Similar concepts do not prove interoperability |
| R-C03 | WPILib already provides relevant desktop and physics simulation | Official | Vendor simulation support is not universal |
| R-C04 | ROS 2 integration is unnecessary for our first proposed lesson | Project recommendation | Not a prohibition on later integration |
| R-C05 | A focused teaching/validation layer may justify the project | Hypothesis | Requires a useful implemented exercise and evaluation |

No new hardware observation, vulnerability, performance benchmark, or working simulator is claimed.

## Sources and access notes

All sources below were reviewed on **2026-09-20**. R identifiers are local to this comparison and supplement the [main source register](SOURCES.md).

<a id="r01"></a>
### R01: WPILib Systemcore introduction

[Official page](https://docs.wpilib.org/en/latest/docs/software/systemcore-info/systemcore-introduction.html). Visible page update: 2026-01-09. Used for the CM5/RP2350 and hardware-versus-software distinction, not production electrical guarantees.

<a id="r02"></a>
### R02: Official Systemcore testing guide

[Pinned README](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/README.md). Inspected tooling, compatibility, quick-start, and deployment sections through the GitHub connector. File blob: `e2710a939b7d8ebe09dfe7865c623fc06730f331`. Some example labels lag its compatibility matrix; use the matrix rather than copying an older launcher name.

<a id="r03"></a>
### R03: ROS 2 middleware interface design

[Official ROS design article](https://design.ros2.org/articles/ros_middleware_interface.html), Dirk Thomas. Written 2014-08; last modified 2017-09. Used for the client-library/RMW separation and design rationale. Its historical wording is not used as a complete list of present-day middleware implementations.

<a id="r04"></a>
### R04: NetworkTables introduction

[Current WPILib page](https://docs.wpilib.org/en/latest/docs/software/networktables/networktables-intro.html). Used for publish/subscribe, client/server organization, retained data, and topic semantics. The stable-version page was also inspected as historical context. No timestamp-unit value from the older page is promoted to a universal 2027 contract.

<a id="r05"></a>
### R05: WPILib desktop simulation

[Official introduction](https://docs.wpilib.org/en/latest/docs/software/wpilib-tools/robot-simulation/introduction.html). Visible page update: 2026-09-20. Used for existing simulation and dashboard capabilities and the explicit warning that vendor support varies. No launch command was executed.

<a id="r06"></a>
### R06: WPILib physics simulation

[Official physics guide](https://docs.wpilib.org/en/latest/docs/software/wpilib-tools/robot-simulation/physics-sim.html). Visible page update: 2026-08-31. Used for existing mechanism models and separation of robot logic from simulation updates. Older-looking code examples are not a tested alpha-7 project.

<a id="r07"></a>
### R07: WPILib 2027 change log

[Official changelog](https://docs.wpilib.org/en/latest/docs/yearly-overview/yearly-changelog.html). Mutable prerelease documentation. Used for the ongoing migration/maintenance context, not to select or install a new build during this follow-up.

<a id="r08"></a>
### R08: ROS 2 Zenoh RMW implementation

[Official ros2/rmw_zenoh README](https://github.com/ros2/rmw_zenoh/blob/rolling/README.md). Inspected opening, requirements, installation description, and initial test notes through the GitHub connector. File blob observed: `e96a75acafa2b3ce3a4197ca96cea458f714ff7b`. Establishes the existence of a Zenoh-based RMW implementation, not that Systemcore includes it. No commands from that README were executed.

Direct access to several `docs.ros.org` concept pages returned an access-challenge page, and one attempted documentation-repository file path returned 404. Those attempts supply no evidence. The accessible ROS design article and official RMW repository supply the ROS-specific evidence used here.
