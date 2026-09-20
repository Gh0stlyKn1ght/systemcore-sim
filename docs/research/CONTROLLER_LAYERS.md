# roboRIO and Systemcore: from hardware to robot behavior

**Research lead and original synthesis: Gh0stly / @Gh0stlyKn1ght**  
**Reviewed: 2026-09-20 | Documentation only | No controller or simulator tests performed**

## Answer first

The standard FRC text-based workflow does not require ROS 2 on either controller. Teams write a robot application using the appropriate WPILib release and vendor libraries. roboRIO and Systemcore are physical controllers, Linux is the underlying operating environment, and WPILib is robot-programming software. ROS 2 is a separate framework and middleware ecosystem, not a hidden prerequisite between those layers. [L01](#l01) [L02](#l02) [L03](#l03)

This describes the documented programming workflow. It does not claim a complete inventory of every vendor binary or prohibit optional ROS integration. Read [the ROS comparison](ROS2_COMPARISON.md) for that boundary.

## How to read the diagrams

These are original teaching models of responsibilities. They are not electrical schematics, exact boot traces, or a claim that every operation passes through every box. Libraries can execute inside the robot application rather than as separate services. Device paths differ: a USB camera, a CAN controller, and an FPGA-backed input need not use the same internal route.

The diagrams show the application at the top. The explanation then works upward from the hardware.

## 1. roboRIO layers

```text
                    TEAM-WRITTEN BEHAVIOR
         Drive, intake, arm, autonomous, diagnostics
                               |
                  WPILib + vendor libraries
         Robot modes, input APIs, controls, device APIs
                               |
                  Hardware abstraction layer
             Native HAL + NI low-level libraries
                               |
               Linux operating environment
          Processes, memory, files, networking, drivers
                               |
            Platform startup and configured I/O
               Boot support + FPGA configuration
                               |
                        PHYSICAL CONTROLLER
           ARM processor + FPGA + memory + I/O ports
                               |
              External sensors and motor controllers
```

Hardware: [L04](#l04). HAL/NI boundary: [L05](#l05). Linux and robot-process separation: [L06](#l06). Device paths: [L07](#l07), [L08](#l08), [L09](#l09).

### Layer 1: hardware does the computing and connects the wires

The roboRIO combines an ARM Cortex-A9 processor with Xilinx FPGA hardware and built-in robot interfaces. The processor runs software; the FPGA is configurable digital circuitry, not simply another CPU running a second copy of the team's program. [L04](#l04)

A useful analogy is a decision-maker working with a precise signal-handling specialist. It is an analogy, not a claim that the FPGA independently decides the robot's strategy.

The large drivetrain motors are powered through motor controllers and the robot's power distribution, not by a logic-level command wire. Keep two paths separate in a lesson:

```text
Command: robot application -> controller interface -> motor controller
Power:   battery -> protected distribution -> motor controller -> motor
```

A PWM control signal conveys a request. Switching electrical power into the motor is the motor controller's job. PWM signaling to a controller and PWM power switching inside it are related concepts but different signals. [L07](#l07)

### Layer 2: startup support and FPGA configuration

Boot support prepares the machine so its operating environment can run. An FPGA configuration defines hardware logic; the team's Java program is not that configuration.

For a concrete roboRIO example, WPILib documents PWM generation in the FPGA. It also documents FPGA decoding of directly attached quadrature encoders. These tasks do not require a Java loop to generate every signal edge or count every encoder transition itself. [L07](#l07) [L08](#l08)

This does not mean all I/O goes through the FPGA. In particular, an encoder attached to a smart motor controller has a different acquisition path from an encoder attached to roboRIO digital inputs. The encoder documentation explicitly distinguishes those cases. [L08](#l08)

### Layer 3: the operating system manages the computer

The roboRIO runs a Linux operating environment supplied in its controller image. WPILib documents separate administrative and robot-code accounts, including `lvuser` for team code. A robot application is therefore a process running under an operating system, not the operating system itself. [L06](#l06)

In general OS terms, this layer manages execution, memory, files, networking, and device access. Drivers connect software operations to particular hardware interfaces. Neither a real-time-oriented platform nor a dedicated I/O device makes arbitrary slow application code meet every deadline.

### Layer 4: HAL hides controller-specific details

HAL means **Hardware Abstraction Layer**. The roboRIO-era WPILib source overview describes its HAL calling NI libraries for low-level device control. That is the boundary between a robot-facing API and the platform-specific implementation. [L05](#l05)

The teaching analogy is a translator: student code asks for a measurement or output, while lower layers handle how that request is represented for this controller. A translator does not make incompatible hardware or old binaries magically interchangeable.

The Java runtime is not another robot framework. Java application code executes in a JVM with native support libraries; C++ uses compiled native code; RobotPy uses Python with bindings into the robotics libraries. These are different execution routes, not ROS 2 dependencies. WPILib's language overview explains the shared-library and Python-binding relationship. [L01](#l01)

### Layer 5: WPILib and vendor libraries provide robot tools

WPILib supplies robot-specific APIs and utilities. Vendor libraries add device-specific capabilities, particularly for CAN devices. A call into REVLib or Phoenix is not necessarily a call through a generic WPILib motor-controller class first. [L01](#l01) [L09](#l09)

Command-based programming is one organization framework within this ecosystem. It is not the Linux scheduler, a communication protocol, or the only possible application structure.

### Layer 6: the team decides what the robot should do

A hypothetical team requirement might be: when enabled in teleop, interpret the joystick; when autonomous starts, follow the selected routine; when a measurement becomes invalid, report it and apply a defined fallback.

For the roboRIO-era `TimedRobot` framework, WPILib calls periodic and mode-related application callbacks. The release API includes separate initialization, disabled, autonomous, teleop, and test callbacks. That explains why a disabled robot can still run code: disabled state is not synonymous with shutting down Linux or killing the application. [L10](#l10)

Do not assume every 2027 template uses identical names or organization. The transition includes API and framework changes. [L11](#l11)

## 2. What happens when a driver moves a joystick?

A simplified operating path is:

```text
Joystick attached to operator computer
  -> Driver Station reads operator input and participates in mode control
  -> robot network carries the relevant Driver Station traffic
  -> robot-side communications and WPILib expose input/state
  -> the team's application decides the requested behavior
  -> a device API and the appropriate interface issue a command
  -> a motor controller applies motor power
  -> sensor measurements inform subsequent decisions
```

The Driver Station runs on the operator side. Deployment places the team program on the roboRIO; routine robot decisions are not calculated by VS Code on the laptop and streamed as compiled code every cycle. [L02](#l02) [L12](#l12)

The radio/network is a communications path, not the team's robot program. Network availability, a running robot process, and permission to operate are separate conditions. The Driver Station exposes distinct communications, robot-code, and joystick status indicators for a reason. [L12](#l12)

For illustration, compare two sensor paths:

```text
Direct encoder -> roboRIO input/FPGA decoding -> HAL -> application
Motor-mounted encoder -> motor-controller firmware -> CAN/vendor API -> application
```

These are conceptual paths, not a promise about every vendor's implementation. [L08](#l08) [L09](#l09)

### NetworkTables is another path, not the entire control system

NetworkTables provides typed publish/subscribe data exchange using a client/server model. The usual arrangement makes the robot program the server and dashboards or coprocessors clients. It can share measurements and settings, but it is not automatically the transport for every Driver Station control message or CAN command. [L13](#l13)

Think of it as a shared information channel. Do not treat a visible dashboard number as proof that the robot is enabled, the sensor is fresh, or a motor actually moved.

## 3. Systemcore layers

```text
                    TEAM-WRITTEN BEHAVIOR
         Drive, intake, arm, autonomous, diagnostics
                               |
              Compatible WPILib + vendor libraries
             Selected 2027 APIs and mode framework
                               |
                Systemcore hardware abstraction
          Controller-specific HAL, drivers, I/O services
                               |
                Limelight Linux OS environment
          Robot process, packages, networking, web tools
                               |
           Platform startup and peripheral firmware
                  Including RP2350 I/O support
                               |
                        PHYSICAL CONTROLLER
          CM5 processor module + RP2350 + memory/ports
                               |
              External sensors and motor controllers
```

Hardware: [L14](#l14). Systemcore HAL and desktop alternative: [L15](#l15). OS/services/firmware: [L16](#l16). Official application workflow: [L03](#l03).

### The important hardware change

Systemcore uses a Raspberry Pi Compute Module 5 host and an RP2350 for reconfigurable I/O. Its published interfaces include CAN, I2C, USB, Ethernet, configurable PWM/digital/analog I/O, and an onboard IMU. [L14](#l14)

The RP2350 is a microcontroller. Do not call it an FPGA or assume it duplicates every roboRIO FPGA feature and timing guarantee. The analogy is that both architectures separate application computing from specialized I/O responsibilities, not that they have identical circuitry.

### The operating environment changes too

Limelight publishes controller images, service and firmware changes, package workflows, and vision integration. The official notes distinguish original alpha hardware from beta hardware and tie image releases to compatible WPILib versions. These are a separate platform environment, not a roboRIO disk image installed onto newer hardware. [L03](#l03) [L16](#l16)

Integrated vision and a browser interface are services, not evidence of a ROS node graph. A web page showing cameras or diagnostics need not be the process that runs the team's robot behavior.

### The programming ecosystem continues, with migration

The application still uses the relevant WPILib and vendor APIs. The current source overview explicitly describes separate Systemcore and simulation HAL implementations. The 2027 changelog documents required project migration and changes such as reorganized namespaces and additional frameworks. Same ecosystem does not mean unchanged source, firmware, package ABI, or deployment artifact. [L11](#l11) [L15](#l15)

We should preserve useful behavior-level interfaces where possible and revise hardware-specific adapters where necessary. That is our project design recommendation, not a claim that compatibility has already been tested.

## 4. Power-on, imaging, and deploying are different operations

This is a conceptual startup sequence for either architecture, not a measured vendor boot trace:

```text
Power becomes usable
  -> startup firmware prepares the platform
  -> operating system and drivers initialize
  -> required controller services and I/O support become available
  -> the installed robot application starts
  -> operator connectivity and robot state are established
  -> application logic runs under the applicable operating conditions
```

Several steps can overlap. Do not infer that the screen, networking, code, and I/O all become ready simultaneously. Limelight's release notes track several of these boot milestones separately. [L16](#l16)

**Imaging** prepares or replaces the controller's system software. **Deploying** builds/transfers the team's application using its development workflow. They are not synonyms. The roboRIO documentation and Systemcore guide document them as distinct procedures. [L02](#l02) [L03](#l03) [L04](#l04)

Neither action necessarily updates every external motor controller. Vendor-device firmware is another versioned dependency.

**Disabled is not de-energized.** For lab design, never treat a software state as isolation from battery energy, stored mechanical energy, or pneumatics. These diagrams are not a lockout procedure or safety certification. Keep physical setup and the applicable official safety procedures separate from a simulated enable/disable exercise.

## 5. Where ROS 2 would fit

A ROS 2 application has its own client libraries and middleware interface. In a Linux-based ROS deployment, those run above the OS rather than replacing its kernel. The ROS middleware design describes the client-library/RMW separation; it is a different API and communication model from the WPILib workflow. [L17](#l17)

For this project's first milestone, ROS 2 is unnecessary. The intended work is to run and test student WPILib behavior, not translate it into another framework first.

A future integration could be investigated explicitly:

```text
Separate computer running ROS 2
  perception / planning / other research application
                         |
                deliberately designed bridge
                         |
         WPILib application validates received information
                         |
              normal controller/device interfaces
```

This is a proposed architecture, not verified native support or competition approval. A first experiment should exchange observations rather than create a second independent actuator-control path. Define units, coordinate frames, timestamps, freshness, and failure policy before accepting data. Driver Station/FMS requirements remain separate.

Running Linux, writing Python, doing vision, or using topics is not enough to establish ROS 2 use. ROS 2 also should not be equated with one mandatory transport in every deployment; see the bounded, sourced discussion in [ROS2_COMPARISON.md](ROS2_COMPARISON.md).

## 6. What our simulator replaces

```text
                         Student behavior
                               |
                   WPILib + small I/O interfaces
                         /                 \
              Real implementation      Simulation implementation
              controller interfaces    desktop HAL + chosen models
                         |                 |
                 physical robot       virtual observations and outputs
```

WPILib already provides desktop simulation, and vendor support varies. We should reuse those facilities instead of emulating an entire controller to teach one behavior. [L18](#l18)

Our proposed contribution is the explanation, reproducible environment, and focused exercises. For example: supply the same application with valid observations, then deliberately supply a stale observation and inspect its decision. A passing desktop exercise does not validate actual FPGA/RP2350 timing, bus wiring, motor firmware, or power protection.

## 7. Check your understanding

Use these questions in a later lesson: Where does the team program execute? Which part decodes a directly attached roboRIO quadrature encoder? Is a CAN-connected motor-controller encoder the same path? Can code run while disabled? Does deploying replace the whole OS? Does a NetworkTables topic prove ROS 2 is present?

The answers should name responsibilities, not just product names. The layers are useful because they help locate a failure: wrong application decision, wrong device API, missing service, mismatched image, invalid measurement, or physical wiring.

## Sources and review boundaries

Access date for every entry: **2026-09-20**. L IDs are local to this document. Mutable pages are identified as such; source review is not bench verification. Earlier project sources remain in [SOURCES.md](SOURCES.md).

<a id="l01"></a>
**L01.** [WPILib: What is WPILib?](https://docs.wpilib.org/en/stable/docs/software/what-is-wpilib.html). Stable documentation, definition and supported text-language/binding sections. This document focuses on text-based WPILib, not a full LabVIEW architecture.

<a id="l02"></a>
**L02.** [Building and Deploying Robot Code](https://docs.wpilib.org/en/stable/docs/software/vscode-overview/deploying-robot-code.html). Stable roboRIO application-deployment workflow.

<a id="l03"></a>
**L03.** [SystemcoreTesting README](https://github.com/wpilibsuite/SystemcoreTesting/blob/2bb26c8ee952e06b566233bbc9c2dabf79a0b04d/README.md). Official test-program reference used for workflow, imaging, generation differences, and compatibility. Earlier project review inspected the setup; this follow-up also read deployment/telemetry and transition sections. This is a pinned research snapshot, not a new release audit.

<a id="l04"></a>
**L04.** [roboRIO Introduction](https://docs.wpilib.org/en/stable/docs/software/roborio-info/roborio-introduction.html). Stable official hardware overview and links to imaging procedures. Applies to the architectural family; numerical specs differ by generation.

<a id="l05"></a>
**L05.** [allwpilib README, v2026.2.2](https://github.com/wpilibsuite/allwpilib/blob/v2026.2.2/README.md#structure-and-organization). Inspected source organization/HAL explanation through GitHub. Blob observed: `87a41c682c59211611bc88eef678a3f2fedc56f3`.

<a id="l06"></a>
**L06.** [roboRIO User Accounts and SSH](https://docs.wpilib.org/en/stable/docs/software/roborio-info/roborio-ssh.html). Establishes Linux and separation of robot-code/admin accounts. No SSH connection or credential operation was performed.

<a id="l07"></a>
**L07.** [PWM Motor Controllers in Depth](https://docs.wpilib.org/en/stable/docs/software/hardware-apis/motors/pwm-controllers.html). Stable official explanation of input signaling, power switching, and FPGA PWM generation. No timing value is generalized to Systemcore.

<a id="l08"></a>
**L08.** [Encoders: Software](https://docs.wpilib.org/en/stable/docs/software/hardware-apis/sensors/encoders-software.html). Reviewed direct-attachment warning, quadrature/FPGA explanation, and measurement API examples. Does not establish Systemcore encoder parity.

<a id="l09"></a>
**L09.** [Third-Party CAN Devices](https://docs.wpilib.org/en/stable/docs/software/can-devices/third-party-devices.html). Used for the distinction between WPILib and vendor-maintained device libraries, not current business status or competition eligibility.

<a id="l10"></a>
**L10.** [TimedRobot API, 2026.2.2](https://github.wpilib.org/allwpilib/docs/release/java/edu/wpi/first/wpilibj/TimedRobot.html). Periodic callbacks and inherited mode hooks. Current release route is mutable; page identified itself as 2026.2.2 during review. No universal loop-rate guarantee is inferred.

<a id="l11"></a>
**L11.** [New for 2027](https://docs.wpilib.org/en/latest/docs/yearly-overview/yearly-changelog.html). Inspected transition, import, and major-change sections. Mutable prerelease documentation.

<a id="l12"></a>
**L12.** [FRC Driver Station Powered by NI LabVIEW](https://docs.wpilib.org/en/stable/docs/software/driverstation/driver-station.html). Reviewed input/state/status and robot-code-restart distinctions. Names and shortcuts belong to this Driver Station generation, not every future interface.

<a id="l13"></a>
**L13.** [What is NetworkTables](https://docs.wpilib.org/en/latest/docs/software/networktables/networktables-intro.html). Reviewed client/server and data-sharing semantics. Timestamp-unit wording on a mutable page is not used to override a versioned API contract.

<a id="l14"></a>
**L14.** [Systemcore Introduction](https://docs.wpilib.org/en/latest/docs/software/systemcore-info/systemcore-introduction.html). CM5/RP2350 and interface overview; visible update date 2026-01-09. Not a final production schematic.

<a id="l15"></a>
**L15.** [allwpilib main README](https://github.com/wpilibsuite/allwpilib/blob/main/README.md#structure-and-organization). Current Systemcore/simulation HAL organization inspected through GitHub. Blob observed: `57ac5db68f0aaefdeac3cc4a326ed3d3229b1255`. Main is mutable; no new build lock was created.

<a id="l16"></a>
**L16.** [Limelight OS README](https://github.com/LimelightVision/systemcore-os-public/blob/05eabac5f48b6d95032d80191b919c63c5fd94fa/README.md). Pinned project OS reference, with release-specific services, firmware, recovery, and boot milestones. This follow-up inspected opening/current release sections and selected historical sections. Unreleased changes are not presented as shipped behavior.

<a id="l17"></a>
**L17.** [ROS 2 middleware interface](https://design.ros2.org/articles/ros_middleware_interface.html), Dirk Thomas, written 2014-08 and modified 2017-09. Used only for client-library/middleware separation, not as a current complete middleware inventory.

<a id="l18"></a>
**L18.** [Introduction to Robot Simulation](https://docs.wpilib.org/en/latest/docs/software/wpilib-tools/robot-simulation/introduction.html). Existing desktop simulation and vendor-support limits. No simulator was run in this review.

### Retrieval and fidelity limits

Some public search results were irrelevant, and several guessed documentation URLs or legacy NI product links were inaccessible. They supplied no evidence. Accessible direct WPILib sources and GitHub repository reads underpin the comparison. An attempted `wpilibsuite/frc-nilrt/README.md` returned 404; no conclusion was drawn from it. A ROS marketing page returned an access challenge; the accessible ROS design article was used instead.

NI's linked specification redirected to roboRIO 2.0 HTML documentation; no numerical specification was needed for these diagrams. No PDF was analyzed, no firmware was unpacked, and no exact bootloader implementation, complete driver topology, scheduler guarantee, or electrical equivalence was established. Startup ordering is deliberately conceptual. No application code, dependencies, settings, or hardware were changed.
