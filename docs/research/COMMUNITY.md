# Community field notes

Research lead: @Gh0stlyKn1ght. Reviewed: 2026-09-20. These are annotated public discussions, not a representative survey or independently reproduced test results.

## Firsthand field evidence: Team 604

Eugene Fang's Team 604 thread contains updates from 2025-08-02, 2025-11-16, and 2026-07-28. The early comparison reported median loop time falling from roughly 14 ms on roboRIO 2 to 1.5 ms on Systemcore, using alpha-era software and a changed garbage collector. That is workload-specific, not proof that every robot becomes ten times faster. Their IMU comparison lacked an absolute ground-truth instrument. Later reports explored 200 Hz operation and described USB logging/mount problems. The 2026 update retained CANivore and linked startup logging and NetworkTables-connectivity issues. [S27](SOURCES.md#s27)

**Our takeaway:** collect loop distributions, sensor validity, actual mount state, free space, and startup milestones. Do not select a faster control loop from one team's median. Reproduce a workload and document the garbage collector, logging load, warmup, and firmware first.

## What other threads are telling us

| Discussion | What was actually discussed | Evidence class | How it changes our research |
|---|---|---|---|
| [CANivore and 2027 control system?](https://www.chiefdelphi.com/t/canivore-and-2027-control-system/503391), 2025-06-19 onward | Whether native buses make CANivore obsolete; vendor features, licensing, mixed-device concerns | Mostly forecasts and purchasing opinions | Track protocol support, vendor features, and event rules separately |
| [FRC/FTC rollout questions, page 4](https://www.chiefdelphi.com/t/systemcore-motioncore-rollout-questions-frc-ftc/508694?page=4), 2025-12-01/02 | Distribution timing, KoP assumptions, software sharing, and driver hardware | Mixed maintainer statements and explicit guesses | Never convert an RFP target or analogy into a guaranteed team shipment |
| [Systemcore Question, r/FRC](https://www.reddit.com/r/FRC/comments/1qgmr5c/systemcore_question/) | Dashboard retirement, Telemetry changes, price uncertainty, migration scope | User questions and community advice | Create a before/after software worksheet rather than just an electrical diagram |
| [Python Friendly, r/FRC](https://www.reddit.com/r/FRC/comments/1sr6weq/lets_hope_that_systemcore_is_python_friendly/) | Student language familiarity, expected performance, adoption hopes | Opinions, anecdotes, unsourced speed claims | Verify RobotPy support and measure representative code; do not repeat fixed slowdown ratios |
| [Teardown Pictures](https://www.chiefdelphi.com/t/systemcore-teardown-pictures/503555) | Public hardware photographs | Useful visual lead, not a complete component audit here | Use official specifications for numerical claims and identify the revision before comparing photos |

Sources: [S28](SOURCES.md#s28), [S29](SOURCES.md#s29), [S30](SOURCES.md#s30), [S31](SOURCES.md#s31), [S32](SOURCES.md#s32).

The Reddit pages rendered relative ages rather than reliable exact timestamps in the inspected view. This notebook records that limitation instead of assigning fabricated publication dates. We did not use related-post recommendations as evidence for the thread being studied.

## Claims that require correction or restraint

### Native CAN-FD makes every external adapter obsolete

That is too strong. FIRST's 2026-08-31 preview specifically permits CANivore-added buses for 2027 as a transition measure and describes a different 2028 policy. Current CTRE integration also has its own version and firmware requirements. The older discussion is useful context, but it is not the current decision source. [S08](SOURCES.md#s08) [S19](SOURCES.md#s19)

### A Raspberry Pi foundation guarantees any Linux package will work

No such guarantee follows. The published toolchain targets aarch64 with a specific sysroot. Package ABI, kernel interfaces, hardware drivers, permissions, and resources still matter. Our conclusion is to distinguish generic Linux knowledge from validated controller integration. [S17](SOURCES.md#s17)

### The onboard IMU's exact part number is already known

Not established here. A recent Reddit model-identification lead could not be retrieved; guesses are not evidence. We will document the interface and measured behavior before relying on a silicon identifier. [S33](SOURCES.md#s33)

### Everyone will receive a unit on a particular date

The inspected rollout thread contains assumptions and historical procurement targets. FIRST's July 2026 update said public-sale details were still being finalized. This review did not establish a final price, guaranteed shipment date, or exact team allocation. Do not budget from the development listing's placeholder-looking price. [S29](SOURCES.md#s29) [S07](SOURCES.md#s07) [S01](SOURCES.md#s01)

### A forum problem is necessarily still present

Version the observation. Limelight's OS notes describe fixes for CAN-load behavior, camera handling, boot timing, and other subsystems. A release-note fix is stronger evidence of vendor action than an old complaint, but it is not our regression-test result. [S06](SOURCES.md#s06)

## Proposed experiments derived from the discussions

| Question | Future measurement | Confounders to record |
|---|---|---|
| Is the control loop reliably within budget? | Median, high percentiles, maximum, and missed deadlines | JVM warmup, collector, logging, camera count, vendor update rates |
| Are logs really external? | Mount identity, write path, free-space trend, power-cycle behavior | Image, USB device, startup timing, mount failures |
| Is the onboard heading useful? | Drift against an independent reference and repeatable motion | Temperature, orientation, impacts, calibration, time |
| Does multi-bus design improve fault isolation? | Behavior when one modeled or bench bus fails | Shared services, device dependencies, recovery policy |
| Is migration complete? | Compile, replay, mode transitions, stale-data tests | Importer version, manual edits, renamed types and units |

These experiments are proposed. None were run during the public-source review.

## Collection boundary

We read accessible public pages. We did not join private Discord channels, contact forum users, access beta-only material, scrape user profiles, or infer community consensus from vote counts. Video leads were not treated as watched technical evidence. A broader future review should add specific presentation timestamps or transcripts before using video claims.
