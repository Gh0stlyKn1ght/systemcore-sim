# Research snapshot and coverage

Research lead: @Gh0stlyKn1ght. Snapshot date: **2026-09-20**.

## Repository and upstream references

| Repository/reference | Recorded identifier | Meaning |
|---|---|---|
| systemcore-sim before this update | `2910f901c5eef9e3cbccc4ca1affe8ddacdd7ca2` | Existing project commit inspected |
| SystemcoreTesting main | `2bb26c8ee952e06b566233bbc9c2dabf79a0b04d` | Public reference head observed during review |
| systemcore-os-public main | `05eabac5f48b6d95032d80191b919c63c5fd94fa` | Public reference head observed during review |
| Existing wpilib-docs reading pin | `1897febd8ae910a6e0de78388f62e7ccd6d8a085` | Preserved from existing lock; not a new build validation |
| Existing allwpilib reading pin | `8e42a614691fdf6c1cf89d68f0fb98b2e26fbc03` | Preserved from existing lock; not a new build validation |
| WPILib release linked by testing guide | `v2027.0.0-alpha-7` | Research candidate, not locally installed |
| Limelight alpha image reference | `limelightosr-2027.0.0-alpha14-380` | Hardware-specific image reference |
| Limelight beta image reference | `limelightosr-2027.0.0-beta14-210` | Hardware-specific image reference |

Upstream files were read through the public repository connector and current heads recorded during the same review. Mutable websites and forum posts are not byte-for-byte archived here. No claim is made that upstream main cannot change after access.

## What was inspected

Official FIRST announcements and team blasts; WPILib installation, migration, removed-feature, simulation, and Systemcore pages; SystemcoreTesting's main guide and REV, CTRE, RobotPy, and selected LimelightLib sections; Limelight OS release notes and repository layout; a cross-compilation configuration; official CTRE and AndyMark pages; visible Reddit and Chief Delphi discussions; and the draft alpha specification, including page images for technical tables and diagrams.

This is a targeted deep review, not an exhaustive crawl of every repository file or issue. Existing private project files were inspected only to integrate the documentation without replacing unrelated work.

## Retrieval limitations

- The supplied image is embedded remotely. Local download attempts failed, so no local image file or checksum was produced.
- The separate cable PDF link was found but not retrieved successfully.
- AndyMark's configuration index established the IPK workflow, but linked download/installation pages and `llms.txt` were inaccessible in this session.
- The recent Reddit IMU-identification lead failed direct retrieval and is not evidence of a chip model.
- Long repository files were reviewed selectively; trailing uninspected sections are not treated as audited.
- Public video leads were not watched or transcribed for technical claims.
- No existing archive captures, certificate-transparency results, private channels, interviews, or beta-only sources were used.

## What was not done

No application source was generated. No dependencies were installed. No firmware or controller OS image was downloaded or executed. No controller was flashed. No simulation, benchmark, classroom trial, hardware test, vendor-service scan, or robot-network connection was performed.

The reference scripts, original lock, and existing version record are not silently converted into tested results. Future measurements must be recorded separately.

## Changes made by this documentation update

The README now credits the research lead and embeds the supplied vendor image with attribution. The expanded dossier introduces a source register, hardware and software notes, architecture model, community notebook, OSINT procedure, fidelity contract, evidence ledger, glossary, and gated roadmap. Earlier immediate-build directions in the project notes are replaced with documentation-first gates.

All new repository content in this update is Markdown. The existing non-Markdown reference files are retained unchanged.
