$ErrorActionPreference = "Stop"

$projectRoot = $PSScriptRoot

if (-not $env:DB_PASSWORD) {
    $env:DB_PASSWORD = Read-Host "Digite a senha do MySQL para o usuario root"
}

if (-not (Test-Path "$projectRoot\out\jura.jar")) {
    & "$projectRoot\build.ps1"
}

java `
    --enable-native-access=javafx.graphics `
    -p "$projectRoot\lib\javafx-sdk-26.0.1\lib" `
    --add-modules javafx.controls,javafx.fxml `
    -cp "$projectRoot\out\jura.jar;$projectRoot\lib\mysql-connector-j-9.7.0.jar" `
    Main
