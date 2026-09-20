$ErrorActionPreference = "Stop"

$ProjectRoot = Split-Path -Parent $PSScriptRoot
$LockFile = Join-Path $ProjectRoot "wpilib-reference.lock"
$ReferenceRoot = Join-Path $ProjectRoot ".reference"

if (-not (Get-Command git -ErrorAction SilentlyContinue)) {
    throw "git is required."
}

if (-not (Test-Path $LockFile)) {
    throw "Missing $LockFile"
}

$Config = @{}
Get-Content $LockFile | ForEach-Object {
    $Line = $_.Trim()
    if ($Line -and -not $Line.StartsWith("#")) {
        $Parts = $Line -split "=", 2
        if ($Parts.Count -eq 2) {
            $Config[$Parts[0]] = $Parts[1]
        }
    }
}

$Required = @("WPILIB_DOCS_REPO", "WPILIB_DOCS_REF", "ALLWPILIB_REPO", "ALLWPILIB_REF")
foreach ($Name in $Required) {
    if (-not $Config.ContainsKey($Name) -or -not $Config[$Name]) {
        throw "Missing $Name in lock file."
    }
}

function Sync-SparseRepository {
    param(
        [string]$Url,
        [string]$Ref,
        [string]$Destination,
        [string[]]$Paths
    )

    if (-not (Test-Path (Join-Path $Destination ".git"))) {
        git clone --filter=blob:none --no-checkout $Url $Destination
        if ($LASTEXITCODE -ne 0) { throw "Clone failed: $Url" }
    }

    git -C $Destination remote set-url origin $Url
    if ($LASTEXITCODE -ne 0) { throw "Unable to set remote: $Destination" }

    git -C $Destination fetch --depth 1 origin $Ref
    if ($LASTEXITCODE -ne 0) { throw "Fetch failed: $Ref" }

    git -C $Destination sparse-checkout init --cone
    if ($LASTEXITCODE -ne 0) { throw "Sparse checkout init failed." }

    git -C $Destination sparse-checkout set @Paths
    if ($LASTEXITCODE -ne 0) { throw "Sparse checkout paths failed." }

    git -C $Destination checkout --detach FETCH_HEAD
    if ($LASTEXITCODE -ne 0) { throw "Checkout failed: $Ref" }
}

New-Item -ItemType Directory -Force -Path $ReferenceRoot | Out-Null

$DocsArgs = @{
    Url = $Config["WPILIB_DOCS_REPO"]
    Ref = $Config["WPILIB_DOCS_REF"]
    Destination = Join-Path $ReferenceRoot "wpilib-docs"
    Paths = @(
        "source/docs/software/systemcore-info",
        "source/docs/software/wpilib-tools/robot-simulation",
        "source/docs/software/commandbased",
        "source/docs/software/dashboards",
        "source/docs/software/hardware-apis",
        "source/docs/software/networktables",
        "source/docs/networking",
        "source/docs/zero-to-robot/step-3",
        "source/docs/yearly-overview"
    )
}
Sync-SparseRepository @DocsArgs

$SourceArgs = @{
    Url = $Config["ALLWPILIB_REPO"]
    Ref = $Config["ALLWPILIB_REF"]
    Destination = Join-Path $ReferenceRoot "allwpilib"
    Paths = @(
        "wpilibj",
        "wpilibjExamples",
        "commandsv2",
        "ntcore",
        "wpimath",
        "hal",
        "simulation",
        "design-docs"
    )
}
Sync-SparseRepository @SourceArgs

Write-Host "WPILib reference material is ready in $ReferenceRoot"
