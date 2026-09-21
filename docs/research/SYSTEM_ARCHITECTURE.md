# How Systemcore fits together

Research lead: @Gh0stlyKn1ght. Snapshot: 2026-09-21. Diagrams are explanatory models, not vendor schematics.

## The mental model

Think of Systemcore as a robot computer plus a dedicated timing-oriented I/O subsystem, not simply a Raspberry Pi with extra sockets. The CM5/RP2350 division is documented by WPILib; the official testing and OS repositories show a surrounding environment for robot deployment, configuration, diagnostics, packages, and vision. [S15](SOURCES.md#s15) [S05](SOURCES.md#s05) [S39](SOURCES.md#s39)

```mermaid
flowchart TB
    DS["Driver Station / operator controls"] <--> NET["Robot network"]
    NET <--> HOST["CM5 Linux host"]
    HOST --> APP["Robot program and WPILib"]
    APP <--> HAL["Hardware abstractions / vendor APIs"]
    HAL <--> IO["Real-time I/O subsystem"]
    IO <--> SMART["SmartIO and onboard sensing"]
    HAL <--> CAN["CAN buses and motor controllers"]
    HOST <--> CAM["USB cameras / vision processing"]
    HOST <--> WEB["Web configuration / diagnostics / packages"]
    APP --> TEL["Telemetry and logs"]
    CAM --> APP
    TEL --> DASH["Driver dashboard / analysis tools"]
```

This conceptual diagram intentionally does not specify every internal transport, driver, process boundary, or electrical connection. Do not infer an unpublished protocol from an arrow.

## Four paths to keep separate

**Control:** operator state and robot logic determine requested actuator behavior. The application reaches devices through selected WPILib and vendor interfaces. A connected dashboard is not, by itself, proof that the robot is enabled or safe to move.

**Observation:** sensor values, robot state, health, and logs are exposed for diagnosis. Treat a value's timestamp, validity, units, and source as part of the value. A plausible number can still be stale.

**Configuration:** team identity, image updates, packages, and device settings change the environment in which control runs. Configuration is not necessarily harmless while code is running. Current OS notes describe stopping robot code during operations such as Motioncore OTA and some hardware configuration changes. [S39](SOURCES.md#s39) [S40](SOURCES.md#s40)

**Recovery:** imaging and recovery use hardware-generation-specific procedures. A workflow valid for an early unit may be wrong for beta hardware. Recovery documentation needs an identified unit and known-good image, not a generic reset button diagram. [S05](SOURCES.md#s05)

These four categories are our architecture decomposition. They are useful for teaching ownership, failure isolation, and diagnosis; they are not a claim that the vendor uses those exact subsystem names.

## Boot and readiness

For research, distinguish power applied, Linux started, network reachable, real-time I/O healthy, robot code running, and Driver Station ready. A display turning on is not a complete readiness measurement. The public OS changelog reports improvements to several separate boot milestones, which reinforces the need to measure them independently on a later bench. [S39](SOURCES.md#s39) [S40](SOURCES.md#s40)

Image-15 notes add a revision-specific display wrinkle: Beta OLEDs remain on at reduced brightness/framerate after inactivity, while Alpha OLEDs continue to turn off. That means display behavior itself now needs a hardware-revision label before it is used as a diagnostic clue. This remains a vendor-documented behavior, not a project measurement. [S40](SOURCES.md#s40)

Our proposed readiness model will therefore allow partial states: network available without code, code present without valid sensors, or telemetry attached while disabled. This is a simulation design choice. Exact service ordering, watchdog timing, OTA timing, and recovery behavior remain hardware-validation questions.

## CAN is a set of separate buses

The testing guide names five primary interfaces `can_s0` through `can_s4`, distinct from Motioncore's `can_d0` through `can_d19`. Vendor APIs also distinguish the selected bus. [S05](SOURCES.md#s05) [S18](SOURCES.md#s18)

Our logical device key should include the bus and vendor/device identity, not just a small device number. Duplicate IDs on one relevant bus must not be confused with the same number intentionally used on another. Logical utilization, loss, and timeout demonstrations should be labeled models, not real CAN-FD timing predictions.

Native CAN-FD hardware does not prove that every motor-controller feature, timestamping mode, firmware release, or license is supported on every bus. FIRST's transition policy and current vendor documentation must be consulted separately. [S08](SOURCES.md#s08) [S19](SOURCES.md#s19) [S20](SOURCES.md#s20)

The current general test matrix accepts image 14+ with WPILib alpha 7+, while the checked CTRE release note specifically names image 14. That is an example of why bus capability, controller image, vendordep, and device firmware belong in one compatibility record instead of being inferred independently. [S05](SOURCES.md#s05) [S20](SOURCES.md#s20)

## Realtime internals and NetworkTables

A post-alpha7 `allwpilib` commit adds AOS as an internal dependency and an `aosnt` bridge. Selected AOS channels can be tagged for publication as normal NetworkTables topics, including protobuf and primitive types. The commit states that the first target is realtime `mrccomm` control data and packages the bridge for Systemcore. [S42](SOURCES.md#s42)

That source change suggests an architecture with a realtime internal communication path plus a separate NetworkTables-facing compatibility/telemetry surface. The bridge runs as its own process, which is explicitly intended to decouple realtime constraints from the NT translation. This is a source-level architecture development after alpha 7, not a tagged alpha-7 guarantee.

It is also not ROS 2. Nothing in this finding changes the project's documented FRC application model: team robot code remains WPILib-based, with NetworkTables still relevant to dashboards and telemetry. Do not introduce ROS 2 concepts into the Systemcore course unless a separate robotics project actually requires ROS 2.

## Integrated vision

Limelight's current test-program material describes up to four USB vision instances, named `limelightsc0` through `limelightsc3`. Results have a coherent frame envelope and explicit health states such as `OK`, `NO_DATA`, `STALE`, and `DECODE_ERROR`. Pose-processing examples distinguish coordinate frames and accepted/rejected measurements. [S21](SOURCES.md#s21)

For our simulator, this argues for a typed observation containing capture time, receive time, frame identity, coordinate convention, measurement, and validity. It does not require reproducing proprietary vision processing. Begin with synthetic observations and documented failure modes. Do not make Hailo hardware mandatory for a first classroom example.

Image-15 notes include vision-memory reductions and a camera capture-rate fix. Those release notes should become regression-test targets later, not synthetic “measurements” in the simulator. [S40](SOURCES.md#s40)

## Configuration and packages are part of the system

The official materials describe browser-accessible package workflows for telemetry tools and vendor utilities. AndyMark's public index independently describes a Systemcore IPK for browser-based setup and diagnostics; REV supplies a Systemcore Hardware Client package. [S05](SOURCES.md#s05) [S18](SOURCES.md#s18) [S23](SOURCES.md#s23)

That creates a useful future architecture exercise: distinguish who may observe from who may change device state. Our documentation will review privileges, update provenance, and recovery boundaries through published material. It does not characterize a public product as vulnerable merely because it has a web interface.

Motioncore OTA in the image-15 notes is another configuration/control boundary: robot code is described as stopping during the update and restarting after a successful update, and device communication is halted during the OTA process. That should be modeled as an explicit unavailable/configuration state in any future system simulator rather than as a transparent background update. [S40](SOURCES.md#s40)

## Networking without misleading defaults

| Connection | Current testing-guide value | Historical distinction |
|---|---|---|
| USB, Windows host | `172.26.0.1` | Images 9 and earlier used `172.28.0.1` |
| USB, Linux/macOS host | `172.27.0.1` | Images 9 and earlier used `172.29.0.1` |
| Built-in access point | `172.30.0.1` | Verify against the installed image |
| Ethernet | Read the display/current configuration | Do not assume one permanent address |

These addresses are published configuration information, not targets to probe. We made no connections to them. `robot.local` is documented for the controller's web interface, but name resolution and subnet reachability are separate failure domains. [S05](SOURCES.md#s05)

Built-in Wi-Fi is not automatic permission to replace the approved FRC event radio. Keep FRC and FTC competition rules separate, even where software is shared. [S03](SOURCES.md#s03)

## Architecture research boundary

Current evidence supports modeling Systemcore as a layered WPILib robot-control platform with revision-specific hardware, Linux services, realtime I/O, multiple CAN domains, vendor packages, vision, and telemetry. It does not support claims of exact process timing, complete internal protocol reconstruction, hardware-equivalent emulation, or ROS 2 dependency. Those require separate source evidence or measured tests.
