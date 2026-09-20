# Hardware specifications and revision boundaries

Research lead: @Gh0stlyKn1ght. Snapshot: 2026-09-20.

## Identify the unit before applying a specification

The published development-unit image and original PDF describe early hardware. The official testing guide distinguishes original FRC alpha units from later beta hardware distributed in the FTC alpha test. Hardware generation and software maturity are separate labels. A beta enclosure running an alpha WPILib build is not a contradiction. [S01](SOURCES.md#s01) [S05](SOURCES.md#s05)

## Compute and interfaces

WPILib describes a Linux-capable Raspberry Pi Compute Module 5 host paired with an RP2350 real-time subsystem. FIRST's hardware overview identifies five CAN-FD ports, six SmartIO ports, two I2C connections, four USB 3 ports, USB-C, Ethernet, an RSL connection, and the Motioncore bridge. It also describes onboard inertial sensing and an M.2 A+E expansion option compatible with Hailo acceleration. Those are different interfaces, not interchangeable ways to attach arbitrary hardware. [S03](SOURCES.md#s03) [S15](SOURCES.md#s15)

### Historical alpha numerical reference

All values below are from the draft alpha PDF; pending qualifications and later revisions matter. [S02](SOURCES.md#s02)

| Item | Published alpha value |
|---|---|
| CPU | BCM2712, four Cortex-A76 cores, 2.4 GHz |
| Memory/storage | 4 GB LPDDR4X-4267; 16 GB eMMC |
| Real-time processor | RP2350, dual Cortex-M33, 150 MHz; 520 KB SRAM |
| Dimensions/mass | 135.5 × 71.5 × 28.13 mm; 215 g |
| Operating input | 5–26 V |
| Survivability envelope | 4.5–35 V, not an operating recommendation |
| Power | 6 W idle; 40 W maximum |
| CAN-FD ceiling | Up to 8 Mbit/s; built-in 120-ohm termination per bus |
| ADC | 12-bit nominal; 8.75–9 effective bits; 0–3.3 V |
| Shared I/O/I2C supply | 3.3 V; 1 A shared |
| USB | 5 Gbit/s shared upstream; 2 A shared supply |
| Ethernet | 1 Gbit/s |
| USB-C link | Data only, USB 2.0 |
| IMU output | 400 Hz quaternion/yaw/acceleration/gyro |
| Display | 128 × 64 monochrome OLED |

The draft's power table and brownout section disagree: 6 V nominal versus 6.3 V, with 7.5 V recovery in the latter. Some protections were disabled in specified alpha firmware. Do not encode a universal brownout threshold from this document.

## Alpha versus beta: practical differences

| Concern | Alpha hardware | Beta hardware | Source |
|---|---|---|---|
| Power connector | Dedicated input and bridge exist; never power both simultaneously | Power through MicroFit Pwr/Bridge | [S05](SOURCES.md#s05) |
| Recovery entry | USB-C attached before applying power | Config button held with USB connected during power-up | [S05](SOURCES.md#s05) |
| Digital input bias | Pulldown arrangement | Pull-up arrangement | [S05](SOURCES.md#s05) |
| Identification | Early port-label limitations | Later enclosure/configuration-button distinction | [S05](SOURCES.md#s05) |

The testing guide also warns against using a regulator such as the VRM to supply the controller under load. Follow the revision-specific power instructions, not a photograph. Its I2C migration note says the roboRIO cable order differs: SDA and SCL need attention. A mechanically fitting connector does not prove correct wiring. [S05](SOURCES.md#s05)

## How to reason about the specifications

A listed bus speed is a link capability, not a guaranteed application update rate. Shared supplies and shared bandwidth require an aggregate budget. A storage capacity is not the same as free log space. A sensor's nominal converter resolution is not its effective measurement precision. These distinctions should appear in classroom examples and later test plans.

Likewise, six configurable ports do not mean six independent ports of every possible type simultaneously. Our future simulator should make allocation and ownership visible. The exact combinations supported by a particular image/API need validation before becoming teaching rules.

Do not select cables, mounting holes, current limits, or external power arrangements from this abbreviated table. Consult the full drawing and current vendor instructions for the actual unit. The cable PDF was located but could not be inspected in this session. [S38](SOURCES.md#s38)

## Manual reading map

Use S02 for the physical drawing and processor details near the beginning, power/CAN/SmartIO tables in the middle, and USB, Ethernet, expansion, IMU, status indicators, and mating connectors later. The filename date alone is insufficient: inspect the internal revision history. FIRST's early announcement also gives a slightly different length, so production mounting must wait for revision-matched CAD. [S02](SOURCES.md#s02) [S04](SOURCES.md#s04)

## Still unknown for our purposes

Final production dimensional approval, exact onboard IMU silicon, complete temperature-qualified operating limits, final accessory bundle, production retail price, and measured thermal/ESD behavior of our future unit are not established by this dossier. We have not measured any controller.
