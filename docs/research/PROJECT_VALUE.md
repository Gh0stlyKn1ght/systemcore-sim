# Is systemcore-sim worth building?

**Research lead: Gh0stly / @Gh0stlyKn1ght | Assessment date: 2026-09-20 | Status: scope recommendation, implementation still on hold**

## Verdict

**Continue the research and pursue a small teaching/validation project. Do not pursue a replacement operating system, middleware stack, or full hardware emulator as the first deliverable.**

The premise that Systemcore is just ROS 2 is not supported by the official FRC workflow. The [stack comparison](ROS2_COMPARISON.md) documents why. The more important challenge is that WPILib already provides simulation. Our work must add a clear learning or validation outcome rather than recreate that foundation. [R02](ROS2_COMPARISON.md#r02) [R05](ROS2_COMPARISON.md#r05) [R06](ROS2_COMPARISON.md#r06)

This is a judgment about the stated classroom and research goals, not market research, proof of technical novelty, or a prediction of employment outcomes.

## The baseline alternative must remain acceptable

The lowest-complexity option is to maintain this research notebook, pin a suitable upstream example, and teach directly with WPILib's existing tools. That is a valid outcome, not failure.

Only write additional simulator code when a concrete exercise needs behavior the selected upstream example does not express clearly enough. First configure and extend existing facilities. Contribute a generally useful fix upstream when that avoids maintaining a duplicate implementation.

| Option | What we own | Assessment |
|---|---|---|
| Research plus adapted upstream example | Source notes, compatibility profile, lesson and setup guide | Appropriate starting baseline |
| Focused Systemcore-oriented lab | Small adapters, repeatable fault scenarios, assertions, lesson workflow | Worth pursuing after the baseline exposes a real need |
| New physics engine or replacement HAL | Core mechanics and platform compatibility | Poor first use of effort when existing tools cover the lesson |
| Full controller emulator or custom OS | Board models, boot/runtime behavior, packaging, timing | Separate research problem with much broader evidence requirements |
| Custom dashboard before a working lesson | Interface design and presentation | Defer until existing tools demonstrably obstruct the learning task |

The last three assessments are engineering recommendations. They do not claim these projects are impossible.

## Where the benefit would come from

### Teaching the same application model students will use

A hardware-free WPILib exercise can let students work with the FRC application model before physical controller access. The published desktop workflow supports that foundation; our additional value would be a prepared, recoverable lesson rather than a claim to invent hardware-free programming. [R02](ROS2_COMPARISON.md#r02) [R05](ROS2_COMPARISON.md#r05)

The proposed student task is concrete: change one behavior, predict the output, inspect telemetry, diagnose one deliberately introduced fault, and restore a known-good state. Target a 42-minute period, but do not claim it fits until a classroom trial measures it.

### Making failure handling understandable

Consider a proposed synthetic vision exercise. The application receives a plausible target observation, then the source stops updating. NetworkTables can preserve and deliver an existing value; the presence of a number is therefore not itself a freshness guarantee. Retention is useful behavior, not a discovered vulnerability. [R04](ROS2_COMPARISON.md#r04)

Our exercise would ask the student to distinguish the measurement from its age and validity, define when aiming assistance should stop, and log the reason. The expected behavior is a project contract, not an invented vendor watchdog or a measured controller safety property.

Other later exercises could distinguish robot disabled from application failure, put a device on the wrong logical bus, or show an I/O ownership conflict. Start with one. Each scenario must separate a synthetic teaching fault from a known hardware issue and from an actual security vulnerability.

### Turning migration notes into reproducible evidence

The official guide has a version-compatibility matrix, and the WPILib changelog documents an evolving 2027 API. That creates a practical reason to record one compatible profile and its test results. It does not justify chasing every prerelease or integrating every vendor. [R02](ROS2_COMPARISON.md#r02) [R07](ROS2_COMPARISON.md#r07)

The useful output would be a statement another person can verify: this named example, source commit, host, toolchain, and dependency set passed these tests. A long list of downloaded packages is not equivalent evidence.

### Building systems expertise beyond one framework

For the stated robotics-security direction, this is a complementary case study to ROS 2/DDS work: application authority, sensor freshness, configuration ownership, version provenance, recovery, and separation of observation from control.

The career value is conditional on tangible work: a clear architecture decision, a threat-boundary diagram, a reproducible failure case, or a measured comparison. Merely collecting product specifications does not demonstrate advanced systems engineering. A simulated failure is not a CVE, and copying a known unsafe pattern into a toy model is not a new vulnerability discovery.

Keep private exploitation work outside this classroom repository. Keep ROS 2-specific research in its existing projects. Keep Robots-OS and Robots-security-OS separate; this project should not become another distribution effort.

## What could be original, and what is not yet established

An organized research summary is original synthesis when accurately attributed, but its underlying vendor facts are not our discoveries. A lesson can be valuable without being novel research.

A future transferable contribution might be a reusable fault-scenario specification, a tested compatibility profile, a documented mismatch between simulation and measured hardware, or an upstream regression test. None is delivered merely by writing this assessment. We have not performed an exhaustive comparison against every FRC simulator, training project, or research paper.

## A bounded first deliverable

After approval to leave documentation mode, target one supported Linux profile, one small Java/WPILib robot example, one mechanism, and one fault exercise. Use the existing simulator and a supported dashboard before considering new UI work.

| Proposed deliverable | Acceptance evidence |
|---|---|
| Versioned setup | A clean environment can build and launch the documented example |
| Small robot exercise | A student change produces the expected observable behavior |
| One synthetic fault | The incorrect behavior is reproducible and the intended correction is tested |
| State/validity assertions | Disabled output and stale-input behavior match explicit project contracts |
| Replay or repeatability record | Fixed input/scenario yields equivalent results under stated tolerances |
| Instructor/student notes | A trial demonstrates the exercise can be taught and reset within the class structure |

All conditions are untested. Later hardware testing must compare the same contracts against a named physical revision; simulated passing results do not certify hardware behavior.

## Evaluate the benefit, not the file count

Treat the following as hypotheses:

| Hypothesis | Comparison | What would weaken the case |
|---|---|---|
| The project reduces setup friction | Plain upstream instructions versus our pinned lesson package | Our layer introduces more failures or unexplained steps |
| Students diagnose faults better | Same task with and without the guided scenario | No meaningful improvement, or students only memorize button sequences |
| The project improves repeatability | Rebuild/reset attempts on clean supported environments | Results depend on undocumented machine state |
| The project builds research skill | Reproducible tests and reasoned architectural changes | Work remains a collection of screenshots and paraphrased announcements |

A small pilot is directional feedback, not a causal study proving a general educational effect. Record assistance, prior knowledge, and task differences rather than claiming precision the exercise cannot support.

## Stop, reduce, or defer when necessary

Reduce the project to notes and an upstream example if that already meets the teaching need. Defer additional features when no imminent lesson or test needs them. Pause hardware-specific claims when the public evidence is insufficient. Reassess the value when prerelease maintenance displaces the core lesson or the user's primary robotics-security work.

Do not postpone all useful work until every production detail is final. Freeze a sufficiently documented profile, keep uncertain behavior outside the first exercise, and label model assumptions. The goal is one reliable learning outcome, not an ever-growing prerequisite list.

## Decision statement

**Use WPILib as the foundation. Own the explanation, the selected compatibility profile, the fault scenarios, and the evidence. Treat ROS 2 integration and full emulation as optional separate questions, not prerequisites.**

See [research gates](ROADMAP.md), [simulation fidelity limits](LINUX_SIMULATION.md), and [the repository-first findings workflow](RESEARCH_WORKFLOW.md).
