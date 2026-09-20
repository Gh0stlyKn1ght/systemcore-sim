# Research roadmap and build gates

Research lead: @Gh0stlyKn1ght. Snapshot: 2026-09-20. This is a sequence of deliverables, not a promised calendar or background task.

## Gate 0: public-source baseline

Delivered in this update: hardware overview, revision distinctions, software/vendor worksheet, community notes, contradiction ledger, Linux design, source register, and OSINT playbook.

Still open: inaccessible source leads, final production details, final rules, and all local/hardware validation. Documentation volume does not close those questions.

## Gate 1: explain the platform accurately

Before writing simulator code, the research lead should be able to explain the CM5/RP2350 split, bus identity, SmartIO allocation, robot state versus dashboard state, vision validity, version compatibility, and host-versus-controller distinction without conflating draft and final behavior.

Produce a one-page architecture walkthrough and answer the P0 questions in [the evidence ledger](EVIDENCE.md). A correct answer may be that a detail remains unknown, provided the simulator does not depend on inventing it.

## Gate 2: freeze one research profile

Record hardware-generation assumptions, OS reference, WPILib release, Java runtime, vendor subset, Linux host, and simulation input path. Select only the dependencies needed for the first slice. Keep the existing upstream reading pins separate from a future tested build lock.

Exit criterion: no unresolved compatibility dependency required for the minimal upstream example. Do not add every vendor merely because its package exists.

## Gate 3: validate the environment

After explicit approval to start implementation, install the official matched tools on one supported host and run a minimal upstream simulation. Save version output, build output, known-good launch procedure, and a screenshot labeled simulation. Repeat on a clean environment before claiming classroom readiness.

Exit criterion: another person can reproduce the environment from the notes without guessing versions or disabling security controls globally.

## Gate 4: implement one small lesson

Only then implement the first drivetrain/sensor/fault slice defined in [the Linux plan](LINUX_SIMULATION.md). Prefer a readable, working lesson over a polished imitation of the vendor dashboard.

Exit criterion: a student can modify one behavior, observe telemetry, introduce a fault, explain the result, and restore the baseline within a 42-minute class structure. Time this in practice rather than assuming it fits.

## Gate 5: compare with hardware

When an owned or explicitly authorized controller is available, identify the revision and image before connecting anything. Establish a safe bench with no unexpected actuator movement. Measure the relevant adapter contracts, power-cycle behavior, sensor validity, and logging paths. Record differences between the model and the hardware.

Hardware inspection, traffic capture, and fault testing require a separate scope. They are not passive internet research.

## Research priorities

**First:** compatibility, state handling, SmartIO semantics, timestamp/validity contracts, and reproducibility. These can invalidate the entire lesson if wrong.

**Second:** multi-bus architecture, logging reliability, and vision integration. These create valuable diagnostic exercises once the basic model works.

**Later:** advanced rendering, full OS emulation, Hailo performance, custom kernel work, and a large web console. They are interesting but not prerequisites for teaching robot programming.

## Maintenance procedure

On each deliberate update, review FIRST announcements, the testing matrix, OS release status, WPILib changes, and only the vendors we actually use. Add dated community reports when they contribute a new measurement or failure mode. Review issue discussions before labeling an old problem resolved.

No recurring monitor was created by this update. Research updates must be initiated deliberately unless a separate scheduled workflow is authorized.
