# Community field notes

Research lead: @Gh0stlyKn1ght. Reviewed: 2026-09-21. These are annotated public discussions, not a representative survey or independently reproduced test results.

## Firsthand field evidence: Team 604

Eugene Fang's Team 604 thread contains updates from 2025-08-02, 2025-11-16, and 2026-07-28. The early comparison reported median loop time falling from roughly 14 ms on roboRIO 2 to 1.5 ms on Systemcore, using alpha-era software and a changed garbage collector. That is workload-specific, not proof that every robot becomes ten times faster. Their IMU comparison lacked an absolute ground-truth instrument. Later reports explored 200 Hz operation and described USB logging/mount problems. The 2026 update retained CANivore and linked startup logging and NetworkTables-connectivity issues. [S27](SOURCES.md#s27)

**Our takeaway:** collect loop distributions, sensor validity, actual mount state, free space, and startup milestones. Do not select a faster control loop from one team's median. Reproduce a workload and document the garbage collector, logging load, warmup, and firmware first.

## What discussions are telling us

| Discussion | What was actually discussed | Evidence class | How it changes our research |
|---|---|---|---|
| [CANivore and 2027 control system?](https://www.chiefdelphi.com/t/canivore-and-2027-control-system/503391), 2025-06-19 onward | Whether native buses make CANivore obsolete; vendor features, licensing, mixed-device concerns | Mostly forecasts and purchasing opinions | Track protocol support, vendor features, and event rules separately |
| [FRC/FTC rollout questions, page 4](https://www.chiefdelphi.com/t/systemcore-motioncore-rollout-questions-frc-ftc/508694?page=4), 2025-12-01/02 | Distribution timing, KoP assumptions, software sharing, and driver hardware | Mixed maintainer statements and explicit guesses | Never convert an RFP target or analogy into a guaranteed team shipment |
| [Systemcore Question, r/FRC](https://www.reddit.com/r/FRC/comments/1qgmr5c/systemcore_question/) | Dashboard retirement, Telemetry changes, price uncertainty, migration scope | User questions and community advice | Create a before/after software worksheet rather than just an electrical diagram |
| [Python Friendly, r/FRC](https://www.reddit.com/r/FRC/comments/1sr6weq/lets_hope_that_systemcore_is_python_friendly/) | Student language familiarity, expected performance, adoption hopes | Opinions, anecdotes, unsourced speed claims | Verify RobotPy support and measure representative code; do not repeat fixed slowdown ratios |
| [Teardown Pictures](https://www.chiefdelphi.com/t/systemcore-teardown-pictures/503555) | Public hardware photographs | Useful visual lead, not a complete component audit here | Use official specifications for numerical claims and identify the revision before comparing photos |
| [When will FIRST state we can still use the roboRIO for 2027 season?](https://www.chiefdelphi.com/t/when-will-first-state-we-can-still-use-the-roborio-for-2027-season/524319), started 2026-09-20 | Availability anxiety, fallback questions, vendor readiness, offseason-event observations | Hearsay, informed community statements, and explicitly unofficial claims | Record team-planning risk without converting discussion into a verified shortage, delivery promise, or final legality rule |

Sources: [S27](SOURCES.md#s27), [S28](SOURCES.md#s28), [S29](SOURCES.md#s29), [S30](SOURCES.md#s30), [S31](SOURCES.md#s31), [S32](SOURCES.md#s32), [S43](SOURCES.md#s43).

The Reddit pages inspected in the earlier review rendered relative ages rather than reliable exact timestamps. This notebook records that limitation instead of assigning fabricated publication dates. We did not use related-post recommendations as evidence for the thread being studied.

A targeted recent-window Reddit check on 2026-09-21 did not produce a new firsthand Systemcore test report strong enough to promote into the evidence ledger. This is a search limitation, not proof that no new post exists.

## 2026-09-20 availability and fallback thread

The new Chief Delphi thread is useful only if its claims remain separated:

- One participant relays availability information from another venue and explicitly says they are unsure it is accurate. That is **hearsay**.
- A WPILib-connected community participant says the software project has moved away from roboRIO support for 2027 and warns that there is no concrete evidence of a reversal. That is an **informed community statement**, not the final Game Manual.
- Another participant points to long-running vendor access, active 2027 vendordeps, and production/near-production units planned for an offseason event. Vendor software activity is independently visible, but the event/distribution statement remains **community-reported** unless backed by the organizer/vendor.
- Other posts explicitly call their distribution information unofficial. That wording should be preserved rather than cleaned up into a factual supply timeline.

**Research conclusion:** the discussion establishes that teams are actively planning around uncertainty. It does not establish that Systemcore supply is insufficient, that every team will receive a controller by a certain date, or that roboRIO will be legal as a fallback. [S43](SOURCES.md#s43)

The correct decision source remains FIRST's final 2027 Game Manual and Team Updates. The current FIRST material in this dossier is still a rule preview. [S08](SOURCES.md#s08)

## Claims that require correction or restraint

### Native CAN-FD makes every external adapter obsolete

That is too strong. FIRST's 2026-08-31 preview specifically permits CANivore-added buses for 2027 as a transition measure and describes a different 2028 policy. Current CTRE integration also has its own version and firmware requirements. The older discussion is useful context, but it is not the current decision source. [S08](SOURCES.md#s08) [S19](SOURCES.md#s19)

### A Raspberry Pi foundation guarantees any Linux package will work

No such guarantee follows. The published toolchain targets aarch64 with a specific sysroot. Package ABI, kernel interfaces, hardware drivers, permissions, and resources still matter. Our conclusion is to distinguish generic Linux knowledge from validated controller integration. [S17](SOURCES.md#s17)

### The onboard IMU's exact part number is already known

Not established here. A Reddit model-identification lead could not be retrieved; guesses are not evidence. We will document the interface and measured behavior before relying on a silicon identifier. [S33](SOURCES.md#s33)

### Everyone will receive a unit on a particular date

The rollout threads contain assumptions, hearsay, and historical procurement targets. FIRST's July 2026 update said public-sale details were still being finalized, and the 2026-09-20 community thread does not replace an official distribution announcement. This review did not establish a final price, guaranteed shipment date, or exact team allocation. Do not budget from the development listing's placeholder-looking price. [S29](SOURCES.md#s29) [S43](SOURCES.md#s43) [S07](SOURCES.md#s07) [S01](SOURCES.md#s01)

### A forum problem is necessarily still present

Version the observation. Limelight's OS notes describe fixes for CAN-load behavior, camera handling, boot timing, and other subsystems. Published Alpha 15/Beta 15 prereleases add a newer version boundary. A release-note fix is stronger evidence of vendor action than an old complaint, but it is not our regression-test result. [S39](SOURCES.md#s39) [S40](SOURCES.md#s40)

### A community statement settles 2027 legality

No. Even statements from technically informed participants are not substitutes for the final Game Manual and Team Updates. FIRST's 2026-08-31 document is explicitly a preview. [S08](SOURCES.md#s08) [S43](SOURCES.md#s43)

## Proposed experiments derived from the discussions

| Question | Future measurement | Confounders to record |
|---|---|---|
| Is the control loop reliably within budget? | Median, high percentiles, maximum, and missed deadlines | JVM warmup, collector, logging, camera count, vendor update rates |
| Are logs really external? | Mount identity, write path, free-space trend, power-cycle behavior | Image, hardware revision, USB device, startup timing, mount failures |
| Is the onboard heading useful? | Drift against an independent reference and repeatable motion | Temperature, orientation, impacts, calibration, time |
| Does multi-bus design improve fault isolation? | Behavior when one modeled or bench bus fails | Shared services, device dependencies, recovery policy |
| Is migration complete? | Compile, replay, mode transitions, stale-data tests | Importer version, manual edits, renamed types and units |
| Does image 15 actually resolve an older reported issue for our stack? | Reproduce the original symptom before/after under fixed conditions | Hardware revision, exact image, vendor library/firmware, test load |

These experiments are proposed. None were run during the public-source review.

## Classroom implication

Community discussion is useful for generating scenarios, not for teaching specifications. A classroom exercise can ask students to classify a post as hearsay, firsthand observation, maintainer statement, vendor release note, or final rule. That is especially valuable during the 2027 transition because the same thread may contain all five evidence qualities mixed together.

## Collection boundary

We read accessible public pages. We did not join private Discord channels, contact forum users, access beta-only material, scrape user profiles, probe services, or infer community consensus from vote counts. Video leads were not treated as watched technical evidence. Reddit search coverage was incomplete. A broader future review should add specific presentation timestamps or transcripts before using video claims.
