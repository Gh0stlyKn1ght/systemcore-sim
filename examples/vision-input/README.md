# Vision observation check

Run from the repository root:

```bash
npm run test:vision
```

The example begins after a camera pipeline has produced a target result. It verifies that missing, non-finite, unauthorized, future-dated, stale, low-quality, and out-of-range observations cannot create an aiming correction. Accepted corrections are bounded.

This is not a pixel pipeline, camera calibration, WPILib pose estimator, or hardware accuracy test. Integrate a selected camera only after its clock, coordinate frame, calibration, vendor API, and controller compatibility are known.
