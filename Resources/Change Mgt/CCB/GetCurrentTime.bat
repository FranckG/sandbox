@echo off

if "%date%A" LSS "A" (set toks=1-3) else (set toks=2-4)
for /f "tokens=2-4 delims=(-)" %%a in ('echo:^|date') do (
for /f "tokens=%toks% delims=.-/ " %%i in ('date/t') do (
set '%%a'=%%i
set '%%b'=%%j
set '%%c'=%%k))

if %'yy'% LSS 100 set 'yy'=20%'yy'%
set Today=%'yy'%-%'mm'%-%'dd'%
ENDLOCAL & SET v_year=%'yy'%& SET v_month=%'mm'%& SET v_day=%'dd'%

rem echo Today is Year: [%v_year%] Month: [%v_month%] Day: [%v_day%]

for /f "tokens=1-2 delims=/:" %%a in ('time /t') do (set mytime=%%a:%%b)

echo/|set /p=%v_year%-%v_month%-%v_day% %mytime%
