# Deterministic desktop simulation

Run from the repository root:

```bash
npm run test:simulation
```

The example advances a differential drivetrain model in fixed 20 ms steps. It verifies straight motion, rotation, repeatability, request bounds, neutral deceleration, a reversed right encoder, and a frozen heading observation.

This is a hardware-free teaching model. It does not use WPILib, emulate Systemcore, reproduce motor electrical behavior, or validate real sensors. Its scenarios become the baseline for the version-matched WPILib simulation layer.
