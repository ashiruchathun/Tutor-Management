$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$frontendDir = Join-Path $projectRoot "frontend"
$staticDir = Join-Path $projectRoot "src\main\resources\static"
$assetsDir = Join-Path $staticDir "assets"

Write-Host "Building React frontend..."
Push-Location $frontendDir
try {
    npm run build
}
finally {
    Pop-Location
}

Write-Host "Updating Spring Boot static files..."
if (Test-Path $assetsDir) {
    Remove-Item -LiteralPath $assetsDir -Recurse -Force
}

New-Item -ItemType Directory -Force -Path $staticDir | Out-Null
Copy-Item -Path (Join-Path $frontendDir "dist\*") -Destination $staticDir -Recurse -Force

Write-Host "Done. Restart Spring Boot and open http://localhost:8080/tutors"
