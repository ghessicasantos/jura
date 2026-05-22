$ErrorActionPreference = "Stop"

$projectRoot = $PSScriptRoot
$sourceFiles = Get-ChildItem -Path "$projectRoot\src" -Recurse -Filter *.java | ForEach-Object { $_.FullName }

if (-not (Test-Path "$projectRoot\out\production\jura")) {
    New-Item -ItemType Directory -Path "$projectRoot\out\production\jura" | Out-Null
}

javac `
    -p "$projectRoot\lib\javafx-sdk-26.0.1\lib" `
    --add-modules javafx.controls,javafx.fxml `
    -cp "$projectRoot\lib\mysql-connector-j-9.7.0.jar" `
    -d "$projectRoot\out\production\jura" `
    $sourceFiles

jar --create --file "$projectRoot\out\jura.jar" --main-class Main -C "$projectRoot\out\production\jura" .

Write-Host "Build concluido: out\jura.jar"
