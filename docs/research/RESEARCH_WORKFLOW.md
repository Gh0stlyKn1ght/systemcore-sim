# Repository-first research workflow

**Research lead: Gh0stly / @Gh0stlyKn1ght | Authorized documentation workflow: 2026-09-20**

## Rule

Every substantive Systemcore finding from this project must be persisted in `Gh0stlyKn1ght/systemcore-sim`, not left only in chat. Public-source facts, corrections, community reports, open questions, and architectural recommendations must carry their evidence class and scope.

This authorizes documentation maintenance, not application development, dependency changes, deployment, hardware interaction, or publication of private security material. The project remains documentation-first.

## Scheduled workflow

The previously configured ChatGPT weekly task was updated on 2026-09-20 to save findings to this repository. It remains scheduled for Monday mornings around 08:00 America/New_York, beginning 2026-09-21.

This is an external scheduled task, not a GitHub Actions workflow installed in the repository. Configuration success is not evidence that a future run has executed or committed anything. Each run must report its actual persistence result.

The task covers FIRST, WPILib/SystemcoreTesting, Limelight, REV, CTRE, AndyMark, and relevant public community reports. This follow-up did not perform another exhaustive release audit or create a second schedule.

## Each research session

Read the current repository and recent update notes first. Check the applicable original publications and distinguish their publication/release dates from today's access date. Use the previous successful review as context, but do not treat a narrow scope-assessment note as proof that all upstream releases were checked.

Save a dated note under `docs/research/updates/YYYY-MM-DD.md`, using the America/New_York date. Append a clearly labeled section to an existing same-day note rather than overwriting prior work. Avoid repeating old findings as new developments.

Update the relevant topic document and source/evidence index when the finding changes an established conclusion. A local source appendix, such as the one in [the ROS 2 comparison](ROS2_COMPARISON.md#sources-and-access-notes), must be linked from the research index so its provenance is discoverable. Use stable source IDs and commit-specific links when actually available.

Do not invent findings to fill a weekly brief. A no-change record should state the scope checked, date range, sources inspected, retrieval limits, and that no substantive change was identified. It must not claim that nothing changed anywhere.

## Note template

```markdown
# Research update: YYYY-MM-DD

Research lead: Gh0stly / @Gh0stlyKn1ght
Review kind: release review / scope assessment / source correction
Access date:
Review window and prior baseline:
Repository base commit:

## Findings
For each finding: narrow claim, evidence class, source location,
publication date or version, and hardware/software scope.

## Implications
Separate documentation corrections from proposed future implementation.

## Unresolved questions and retrieval limits
State what was not checked and what the source cannot establish.

## Persistence and validation
Name documents changed. Record actual checks, not assumed success.
Keep hardware/software test results separate from document review.
```

## Write discipline

Read the latest branch before editing and preserve unrelated changes. Make a documentation-only commit, or a documentation pull request when direct writes are blocked. Never force-push, reset history, silently replace source pins, change repository visibility, or broaden permissions.

Before reporting success, read the remote ref or PR and verify the changed paths. If a write fails, explicitly say that the findings were not saved and deliver the unsaved Markdown in chat or an attachment. A tool's planned action is not a successful commit.

Scheduled documentation writes must not modify application code, scripts, dependency locks, release artifacts, repository settings, or an actual controller. A proposed implementation action remains a recommendation until separately authorized.

## Evidence discipline

Use the existing Official, Reported, Observed, Proposed, and Unknown classifications. A scheduled web review cannot create an Observed hardware result. Preserve superseded claims with dated corrections rather than erasing the history that explains the change.

In particular, retain these distinctions: Systemcore versus ROS 2; Linux host versus controller OS; hardware alpha/beta versus software release; released versus unreleased; preview versus final competition rules; simulation versus measured hardware; application fault versus security vulnerability.

The public-research boundary is defined in [PASSIVE_OSINT.md](PASSIVE_OSINT.md). No port scanning, guessed endpoint enumeration, authentication attempts, or robot-network interaction belongs in this scheduled workflow.

## Authorship

Credit original project synthesis and research direction to Gh0stly / @Gh0stlyKn1ght. Preserve attribution to FIRST, WPILib, ROS contributors, Limelight, vendors, and community authors for their own material. The project's existing AI-assistance and licensing disclosures remain in force.
