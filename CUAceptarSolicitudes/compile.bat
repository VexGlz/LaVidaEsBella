@echo off
cd /d "c:\Users\Josel\OneDrive\Documentos\NetBeansProjects\LaVidaEsBella"

echo Intentando compilar con Maven...
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    mvn clean compile -f "CUAceptarSolicitudes\pom.xml"
) else (
    echo Maven no encontrado en PATH.
    echo Por favor, compila el proyecto desde NetBeans IDE:
    echo 1. Abre NetBeans
    echo 2. Abre el proyecto CUAceptarSolicitudes
    echo 3. Haz clic derecho en el proyecto y selecciona "Clean and Build"
)
pause
