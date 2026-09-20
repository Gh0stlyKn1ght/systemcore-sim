# Passive OSINT and low-impact Linux research

Research lead: @Gh0stlyKn1ght. Snapshot: 2026-09-20. Commands below are optional instructions, not a record of commands executed against third parties.

## Scope

Investigate public information about Systemcore through FIRST, WPILib, Limelight, REV Robotics, AndyMark, CTRE, and public community discussions. No live robots, team networks, venue networks, private dashboards, vendor administrative systems, or unpublished endpoints are in scope.

The user-supplied CyCognito explainers are useful background, but terminology alone does not make a request passive. We distinguish methods by what they contact. [S34](SOURCES.md#s34) [S35](SOURCES.md#s35)

| Method | Contact with the vendor/target | Classification used here |
|---|---|---|
| Read a search-engine result or an already stored local file | No deliberate direct request to the target by this step | Passive relative to the target |
| Read an existing third-party archive capture | Archive service contacted | Passive relative to the target; capture freshness uncertain |
| Query public repository metadata on GitHub | GitHub contacted | Public-source collection, not robot probing |
| Download one published vendor manual/image | Vendor/CDN receives an HTTP request | Low-impact direct collection, not strictly passive |
| Open a vendor page in a browser | Page and possibly other resources fetched | Direct collection |
| Port scan, enumerate guessed URLs, fuzz APIs, try credentials | Target services actively exercised | Excluded |

No port scans, vulnerability scanners, login attempts, or robot-network connections were performed in this review. Repository reads and ordinary public-page retrievals were used. Search engines and browsers may make their own network requests; this is not a promise of invisibility.

## Search questions, not random infrastructure

Use exact product names and a focused question. Example queries to enter manually in a search engine:

```text
"Systemcore" site:community.firstinspires.org
"Systemcore" "2027" site:docs.wpilib.org
"Systemcore" site:github.com/wpilibsuite
"Systemcore" site:github.com/LimelightVision
"Systemcore" "REVLib" site:revrobotics.com
"AndyMark IPK for SystemCore"
"Systemcore" "CANivore" site:chiefdelphi.com
"Systemcore" "logging" site:chiefdelphi.com
"Systemcore" site:reddit.com/r/FRC
```

Queries are leads. Open the actual source, check dates and versions, and compare the claim against the relevant authority. Search snippets can be stale, truncated, or attached to the wrong page. Do not treat a lack of search hits as proof that a feature or announcement does not exist.

## Offline-first repository reading

Prefer the project's existing pinned reference workflow. Review the scripts before running them and keep downloaded upstream material in ignored `.reference/`. This update does not change or execute that workflow.

For repositories already present locally, these are read-only research commands:

```bash
# Run from this repository's root after deliberately obtaining references.
# These commands do not fetch updates or run upstream code.
for repo in .reference/*; do
  [ -d "$repo/.git" ] || continue
  printf '\nRepository: %s\n' "$repo"
  git -C "$repo" rev-parse HEAD
  git -C "$repo" log -n 12 --date=iso-strict \
    --format='%H %ad %s'
done

rg -n -i --glob '*.md' --glob '*.rst' --glob '*.java' \
  --glob '*.cpp' --glob '*.h' \
  'systemcore|smartio|CANPort|onboard.?imu|opmode|telemetry' \
  .reference/
```

Read context around matches. A class stub, open pull request, TODO, or unreleased changelog section is not a shipped feature. Record repository, commit, path, and relevant line range in a Markdown note. Do not search for or redistribute secrets; incidental private-looking data should be excluded and handled through the publisher's appropriate reporting channel.

## One-file public collection, only when needed

Use a known published URL, not an automatically generated list of guesses. The example intentionally does not follow redirects, retry failures, crawl links, or execute downloaded content. Review a redirect manually before making a separate request.

```bash
# Optional low-impact GET, not strictly passive.
# Bash; requires curl and sha256sum. Run as a normal user.
set -euo pipefail
umask 077
mkdir -p .research-cache
url='https://limelightvision.io/products/systemcore-development-unit'
out=".research-cache/systemcore-product-$(date -u +%Y%m%dT%H%M%SZ).html"
tmp="${out}.partial"
headers="${out}.headers"
trap 'rm -f "$tmp"' EXIT

code=$(curl --proto '=https' --tlsv1.2 --silent --show-error \
  --connect-timeout 10 --max-time 30 --max-filesize 5242880 \
  --user-agent 'Systemcore-docs-research/0.1 (single public-document request)' \
  --dump-header "$headers" --output "$tmp" \
  --write-out '%{http_code}' "$url")

if [ "$code" != 200 ]; then
  printf 'Stopped: HTTP %s. Review headers; do not bypass restrictions.\n' "$code" >&2
  exit 1
fi
mv "$tmp" "$out"
sha256sum "$out"
printf 'Source: %s\nRetrieved UTC: %s\nHTTP: %s\n' \
  "$url" "$(date -u +%FT%TZ)" "$code" > "${out}.provenance"
```

For another explicitly approved public document, change the URL and output name deliberately. Verify content type and inspect files as data in an updated, unprivileged environment. Never pipe a download into a shell. Respect publisher terms and robots guidance before any automation. A public URL is not permission for load testing.

Stop on 401, 403, 429, CAPTCHA, or an access restriction. Do not switch proxies or accounts to get around it. Honor `Retry-After` before any later permitted retry. Space deliberate requests out, avoid parallel collection, and reuse local copies. Redact unnecessary cookies or identifiers before sharing response headers.

## Existing archives and registries

A search engine's cached metadata or an existing web-archive capture can establish that a statement appeared earlier. Record the archive capture date separately from the source's publication date and today's access date. Do not trigger a new archive crawl and call that passive collection. No archive captures or public-registry results were relied on for this snapshot.

Certificate-transparency and registration records can suggest domain ownership history, but do not establish product architecture, exposed services, or an authorization scope. They are low priority for this task because published repositories and vendor documents answer the relevant questions more directly. We do not need a subdomain inventory to understand Systemcore.

## Highest-value public repository surfaces

Review release compatibility tables, dated changelogs, issue descriptions and maintainer replies, vendordep metadata, public example build files, package control files, and documentation changes. The Systemcore testing and OS repositories are linked in [the source register](SOURCES.md#s05).

Package metadata can reveal an architecture target, dependency, service name, or install path. It cannot prove that a service is reachable on a production controller. Release archives may contain executable installers: inspect metadata first, and do not execute unknown packages merely to obtain information.

## Evidence note template

```markdown
# Finding: short factual title

Researcher: @Gh0stlyKn1ght
Access date: YYYY-MM-DD
Source title and URL:
Publication or release date:
Repository commit / file / section:
Hardware revision:
OS / WPILib / vendor versions:
Method: search, archive, repository read, or public-document GET
Evidence class: Official / Reported / Observed / Proposed / Unknown

## Claim
One narrowly worded claim.

## Evidence
A concise paraphrase and exact source location.

## Contradictions and limits
What the source does not establish.

## Simulator implication
What should change, if anything, and why.

## Next verification
A documentation check or a future authorized local test.
```

## Keep research separate from bench work

Connecting to an owned controller, capturing its traffic, checking listening services, or testing recovery can be legitimate later laboratory work with an explicit scope. None of that is passive internet research. Define authorization, safety controls, and a test plan before it starts. This repository currently contains public-source documentation only.
