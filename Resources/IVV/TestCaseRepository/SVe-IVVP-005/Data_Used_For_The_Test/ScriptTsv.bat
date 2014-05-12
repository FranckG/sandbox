@ECHO off
echo Running script for %1 ( @ %2 / Res : %4 )...
echo Going to execute:  %3\Orchestra.IVVQ.bat setresult %2 %2\Data_Used_For_The_Test\%4
pause
%3\Orchestra.IVVQ.bat setresult %2 %2\Data_Used_For_The_Test\%4

PAUSE
