@REM clean test dir
rmdir /q /s %WORKSPACE%\tests\unit

@REM Get a new baseLocation.
mkdir %WORKSPACE%\tests\unit

subst

@REM get next available drive letter
for %%a in (G H J K L N O P Q R S T U V X Y Z) do (
  if exist %%a:\ (echo %%a drive exists) else (set driveLetter=%%a && goto NEXT_DRIVE_LETTER)
)
:NEXT_DRIVE_LETTER
@set driveLetter=%driveLetter:~0,1%:
@echo Next available drive letter: [%driveLetter%]

@REM mandatory to delete lost substituted drive
subst %driveLetter% /D

@REM Substitution as a workaround for windows 254 characters limit.
subst %driveLetter% %WORKSPACE%\tests\unit

:GET_TARGET_PLATFORM
@REM GET Target Platform
@echo GET TARGET PLATFORM
svn export http://svn.orchestra.ors.services.thales/plugins_repo/trunk/extloc/platform-3.8/eclipse-sdk-3.8.2 %driveLetter%\TP
if ERRORLEVEL 1 GOTO END

REM Get the product result.
mkdir %driveLetter%\OrchestraFramework
unzip -q %WORKSPACE%\work\%BUILD_ID%\OrchestraFramework-%JOB_REVISION%.%BUILD_ID%-win32.win32.x86.zip -d %driveLetter%\OrchestraFramework

REM Get the test feature result.
mkdir %driveLetter%\frameworkTests
unzip -q %WORKSPACE%\work\%BUILD_ID%\com.thalesgroup.orchestra.framework.tests.feature-%BUILD_ID%.zip -d %driveLetter%\frameworkTests

REM Creating links.
set LINKS_DIR=%driveLetter%\TP\eclipse\dropins
mkdir %LINKS_DIR%
echo path=R:/extloc/platform-3.8/eclipse-test-framework-3.8.2/eclipse > %LINKS_DIR%\eclipse-test-framework-3.8.2.link
echo path=%driveLetter%/OrchestraFramework/eclipse > %LINKS_DIR%\OrchestraFramework.link
echo path=%driveLetter%/frameworkTests/eclipse > %LINKS_DIR%\frameworkTests.link

REM Install files needed by Orchestra Framework.
mkdir %driveLetter%\TP\eclipse\lib
copy "%WORKSPACE%\plugins\com.thalesgroup.orchestra.framework.integration.tests\lib\OrchestraDoctor.path" "%driveLetter%\TP\eclipse\lib"

REM Launching tests.
cd /d %driveLetter%\TP\eclipse

set JAVA=%JAVA_HOME%\bin\java.exe
set VM_ARGS=-Xms128m -Xmx512m -XX:MaxPermSize=256m

set TEST_WORKSPACE=%driveLetter%\ws\test1
mkdir %TEST_WORKSPACE%
%JAVA% %VM_ARGS% -jar plugins\org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar -application org.eclipse.test.coretestapplication -data %TEST_WORKSPACE% -os win32 -ws win32 -arch x86 -dev bin -testpluginname com.thalesgroup.orchestra.framework.common.test -classname com.thalesgroup.orchestra.framework.common.AllTests formatter=org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter,%driveLetter%\TEST-com.thalesgroup.orchestra.framework.common.AllTests.xml

set TEST_WORKSPACE=%driveLetter%\ws\test3
mkdir %TEST_WORKSPACE%
%JAVA% %VM_ARGS% -jar plugins\org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar -application org.eclipse.test.coretestapplication -data %TEST_WORKSPACE% -os win32 -ws win32 -arch x86 -dev bin -testpluginname com.thalesgroup.orchestra.framework.model.migration.test -classname com.thalesgroup.orchestra.framework.model.migration.AllTests formatter=org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter,%driveLetter%\TEST-com.thalesgroup.orchestra.framework.model.migration.AllTests.xml

set TEST_WORKSPACE=%driveLetter%\ws\test4
mkdir %TEST_WORKSPACE%
%JAVA% %VM_ARGS% -jar plugins\org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar -application org.eclipse.test.coretestapplication -data %TEST_WORKSPACE% -os win32 -ws win32 -arch x86 -dev bin -testpluginname com.thalesgroup.orchestra.framework.project.test -classname com.thalesgroup.orchestra.framework.project.AllTests formatter=org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter,%driveLetter%\TEST-com.thalesgroup.orchestra.framework.project.AllTests.xml

set TEST_WORKSPACE=%driveLetter%\ws\test5
mkdir %TEST_WORKSPACE%
%JAVA% %VM_ARGS% -jar plugins\org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar -application org.eclipse.test.coretestapplication -data %TEST_WORKSPACE% -os win32 -ws win32 -arch x86 -dev bin -testpluginname com.thalesgroup.orchestra.framework.test -classname com.thalesgroup.orchestra.framework.test.AllTests formatter=org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter,%driveLetter%\TEST-com.thalesgroup.orchestra.framework.test.AllTests.xml

set TEST_WORKSPACE=%driveLetter%\ws\test6
mkdir %TEST_WORKSPACE%
%JAVA% %VM_ARGS% -jar plugins\org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar -application org.eclipse.test.coretestapplication -data %TEST_WORKSPACE% -os win32 -ws win32 -arch x86 -dev bin -testpluginname com.thalesgroup.orchestra.framework.tests -classname com.thalesgroup.orchestra.framework.tests.AllTests formatter=org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter,%driveLetter%\TEST-com.thalesgroup.orchestra.framework.tests.AllTests.xml

:END
REM Delete substitution.
cd /d %WORKSPACE%
subst %driveLetter% /d

