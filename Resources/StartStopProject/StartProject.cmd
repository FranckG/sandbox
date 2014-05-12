echo off

REM Stop Orchestra framework
echo -- STOP Orchestra framework --
copy C:\Orchestra\Thales\Papeete\bin\stop.xml c:\Temp\stop.xml
start /w C:\Orchestra\Thales\Papeete\bin\papeeteUniversalClient.exe C:\Temp\stop.xml

echo Set artifacts folders
REM Change view mounted on Z drive
net use X: /DELETE
net use X: \\localhost\SimulatedViews\6WakeupSolution-BeforeAddSolarCapability
REM Change file system mounted on P drive
net use P: /DELETE
net use P: \\localhost\Fs3.3


REM Start Orchestra framework
echo -- START Orchestra framework --
start C:\Orchestra\Thales\Papeete\eclipse\Papeete.exe

REM Launch EngineeringDesk
C:\orchestra\thales\EngineeringDesk\orchestra.exe