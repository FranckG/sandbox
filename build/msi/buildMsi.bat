@echo off
SET WORKSPACE=%~dp0..\..
SET VERSION=4.4.1
REM SET MSI_RESOURCES=M:\fgd_P_Packaging_int\Support_comp\Packaging\resources
SET MSI_RESOURCES=M:\fgi_P_Packaging\Support_comp\Packaging\resources
SET ORCHESTRA_VERSION=5.4.1
SET NAME=OrchestraFramework

REM get 09 instead of _9
set TIMESTAMP=%TIME: =0%

REM GET DATE IN YYYYMMDD
FOR /F "TOKENS=1* DELIMS= " %%A IN ('DATE/T') DO SET CDATE=%%B
FOR /F "TOKENS=1,2 eol=/ DELIMS=/ " %%A IN ('DATE/T') DO SET mm=%%B
FOR /F "TOKENS=1,2 DELIMS=/ eol=/" %%A IN ('echo %CDATE%') DO SET dd=%%B
FOR /F "TOKENS=2,3 DELIMS=/ " %%A IN ('echo %CDATE%') DO SET yyyy=%%B
SET DATESTAMP=%yyyy%%mm%%dd%

REM GET DATE IN YYYYMMDDmmss
set TIMESTAMP=%DATESTAMP%%TIMESTAMP:~0,2%%TIMESTAMP:~3,2%

msigen -w "%WORKSPACE%" -f "%MSI_RESOURCES%\scripts\MSI-pre.py" -f "%~dp0OrchestraFramework.py" "%~dp0OrchestraFramework.aip"
IF %ERRORLEVEL% NEQ 0 goto ERROR_01

copy "%WORKSPACE%\dist\%NAME%*.msi" "\\nas-ewd.orchestra\integ\Team Orchestra\Tropical Team\FGI"

:ERROR_01
exit /B 1

:END