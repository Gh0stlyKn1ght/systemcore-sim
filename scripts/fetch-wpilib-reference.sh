#!/usr/bin/env bash
set -euo pipefail

project_root="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
lock_file="$project_root/wpilib-reference.lock"
reference_root="$project_root/.reference"

if ! command -v git >/dev/null 2>&1; then
  echo "git is required." >&2
  exit 1
fi

if [[ ! -f "$lock_file" ]]; then
  echo "Missing $lock_file" >&2
  exit 1
fi

while IFS='=' read -r key value; do
  [[ -z "$key" || "$key" == #* ]] && continue
  case "$key" in
    WPILIB_DOCS_REPO|WPILIB_DOCS_REF|ALLWPILIB_REPO|ALLWPILIB_REF)
      printf -v "$key" '%s' "$value"
      ;;
  esac
done < "$lock_file"

required=(WPILIB_DOCS_REPO WPILIB_DOCS_REF ALLWPILIB_REPO ALLWPILIB_REF)
for name in "${required[@]}"; do
  if [[ -z "${!name:-}" ]]; then
    echo "Missing $name in lock file." >&2
    exit 1
  fi
done

sync_sparse_repo() {
  local url="$1"
  local ref="$2"
  local destination="$3"
  shift 3
  local paths=("$@")

  if [[ ! -d "$destination/.git" ]]; then
    git clone --filter=blob:none --no-checkout "$url" "$destination"
  fi

  git -C "$destination" remote set-url origin "$url"
  git -C "$destination" fetch --depth 1 origin "$ref"
  git -C "$destination" sparse-checkout init --cone
  git -C "$destination" sparse-checkout set "${paths[@]}"
  git -C "$destination" checkout --detach FETCH_HEAD
}

mkdir -p "$reference_root"

sync_sparse_repo   "$WPILIB_DOCS_REPO"   "$WPILIB_DOCS_REF"   "$reference_root/wpilib-docs"   source/docs/software/systemcore-info   source/docs/software/wpilib-tools/robot-simulation   source/docs/software/commandbased   source/docs/software/dashboards   source/docs/software/hardware-apis   source/docs/software/networktables   source/docs/networking   source/docs/zero-to-robot/step-3   source/docs/yearly-overview

sync_sparse_repo   "$ALLWPILIB_REPO"   "$ALLWPILIB_REF"   "$reference_root/allwpilib"   wpilibj   wpilibjExamples   commandsv2   ntcore   wpimath   hal   simulation   design-docs

echo "WPILib reference material is ready in $reference_root"
