@echo off

::if %1.==. goto usage

::if exist %1\nul goto deldir
::goto newdir

::------------------------------------------------
:deldir

:: Determine if we are using Windows 2000 or XP
ver | find "XP" > nul
if %ERRORLEVEL% == 0 goto xp-and-2000

ver | find "2000" > nul
if %ERRORLEVEL% == 0 goto xp-and-2000

:goto nt-and-older

:xp-and-2000
rd /s/q %1
goto newdir

:nt-and-older
deltree /y %1

::------------------------------------------------
:newdir
::md %1
goto end

::------------------------------------------------
:usage
echo usage:
echo   %0 DIRNAME
echo.
echo Deletes the directory named DIRNAME and everything in it if it exists!
echo Then creates a new directory named DIRNAME
echo.

:end