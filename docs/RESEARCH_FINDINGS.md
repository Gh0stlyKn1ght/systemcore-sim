# Public Repository Findings

“Hidden” in this note means easy to miss in public repositories, not confidential information.

## Verified

- The official SystemCore web-dashboard page is still incomplete, so this project must not claim to clone a finished vendor specification.
- WPILib supports desktop robot simulation and dashboard connections over localhost.
- The current public Java source contains simulation models such as `DifferentialDrivetrainSim`, `PWMSim`, and `SimDeviceSim`.
- Newer public SystemCore material describes five primary CAN buses named `can_s0` through `can_s4`.
- Public SystemCore material describes as many as four USB vision instances named `limelightsc0` through `limelightsc3`.
- Public testing material uses vision result states including `OK`, `NO_DATA`, `STALE`, and `DECODE_ERROR`.
- The 2027 WPILib and SystemCore surface is still changing.

## Important discrepancy

Older WPILib networking documentation and newer SystemCore test/OS material have shown different USB subnets. Network addresses must be treated as image-version-specific and verified against the installed controller image.

## Engineering conclusion

A useful behavioral simulator is feasible using public WPILib APIs. An exact SystemCore emulator is not justified by the public material because the full image build, RP2350 firmware, device models, and timing behavior are not available as one reproducible specification.

## Evidence labels

Every future finding should be labeled:

- **Verified:** supported directly by an official public source.
- **Observed:** reproduced in our test environment.
- **Inferred:** an engineering conclusion, not an official specification.
- **Unknown:** requires hardware or official clarification.
