# Telemetry contract check

Run from the repository root:

```bash
npm run test:telemetry
```

This hardware-free example tests the decision boundary for present, finite, valid, authorized, and fresh observations. It uses Java source-file mode and requires Java 17 or newer.

It does not test NetworkTables transport, cross-device clocks, WPILib integration, dashboards, Systemcore timing, or physical hardware.
