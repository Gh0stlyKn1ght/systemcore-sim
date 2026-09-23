# Bounded autonomous routine

Run:

```bash
npm run test:autonomous
```

The framework-neutral state machine verifies drive-to-turn sequencing, normal completion, cancellation from both moving states, timeout, stale input, non-finite input, terminal persistence, and neutral output.

Wrap this behavior with the selected WPILib 2027 OpMode or command framework only after the matching generated example builds.
