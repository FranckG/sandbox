@echo off
set WORKSPACE=%~dp0..

REM get 09 instead of _9
SET TIMESTAMP=%TIME: =0%

REM GET DATE IN YYYYMMDD
SET DATESTAMP=20150413

REM SET BUILD_ID=%DATESTAMP%%TIMESTAMP:~0,2%%TIMESTAMP:~3,2%
SET BUILD_ID=201504131519
SET JOB_REVISION=4.4.1

@echo Date stamp:   %DATESTAMP%
@echo Build ID:     %BUILD_ID%
@echo Job revision: %JOB_REVISION%
@echo Workspace:    %WORKSPACE%

REM call gradle -i -b %~dp0product.gradle
REM call gradle -i -b %~dp0features.gradle
REM call gradle -i -b %~dp0OdmVariablesResolverFeature.gradle
call gradle -i -S -b %~dp0merge.gradle

:END