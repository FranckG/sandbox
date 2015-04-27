REM Get confdir location parameter.
set CONFDIR_LOCATION=%1

REM clean test dir.
rmdir /q /s %WORKSPACE%\tests\integ

REM Get a new baseLocation.
mkdir %WORKSPACE%\tests\integ

@REM get next available drive letter
for %%a in (G H J K L N O P Q R S T U V X Y Z) do (
  if exist %%a:\ (echo %%a drive exists) else (set driveLetter=%%a && goto NEXT_DRIVE_LETTER)
)
:NEXT_DRIVE_LETTER
@set driveLetter=%driveLetter:~0,1%:
@echo Next available drive letter: [%driveLetter%]

REM Substitution as a workaround for windows 254 characters limit.
subst %driveLetter% /D
subst %driveLetter% %WORKSPACE%\tests\integ

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
echo path=R:/extloc/platform-3.8/misc/eclipse > %LINKS_DIR%\platform-3.8-misc.link
echo path=R:/extloc/platform-3.8/swtbot-2.1.0.201302221524 > %LINKS_DIR%\swtbot-2.1.0.201302221524.link
echo path=%driveLetter%/OrchestraFramework/eclipse > %LINKS_DIR%\OrchestraFramework.link
echo path=%driveLetter%/frameworkTests/eclipse > %LINKS_DIR%\frameworkTests.link

REM Create directories neede by Orchestra Framework.
mkdir %driveLetter%\ORCHESTRA_DATA\Artifacts
mkdir %driveLetter%\ORCHESTRA_DATA\ConfDir

REM Install files needed by Orchestra Framework.
xcopy /I "%WORKSPACE%\Delivery\dtd" "%driveLetter%\OrchestraFramework\dtd"
mkdir %driveLetter%\OrchestraFramework\lib
copy "%WORKSPACE%\Delivery\lib\MimeTypes.properties" "%driveLetter%\OrchestraFramework\lib"
mkdir %driveLetter%\\TP\lib
copy "%WORKSPACE%\plugins\com.thalesgroup.orchestra.framework.integration.tests\lib\OrchestraDoctor.path" "%driveLetter%\TP\lib"
mkdir %driveLetter%\OrchestraFramework\orcConfig
copy "%WORKSPACE%\plugins\com.thalesgroup.orchestra.framework.connector.commands\connectors\FrameworkCommands.connector" "%driveLetter%\OrchestraFramework\orcConfig"
copy "%WORKSPACE%\plugins\com.thalesgroup.orchestra.framework.connector.migration\connectors\Migration.connector" "%driveLetter%\OrchestraFramework\orcConfig"

REM install ConfDir.
%CONFDIR_LOCATION% -o"%driveLetter%\ORCHESTRA_DATA\ConfDir" -y

REM Set test environment.
cd /d %driveLetter%\TP\eclipse

set TEMP=%WORKSPACE%
set TMP=%TEMP%
set JAVA=%JAVA_HOME%\bin\java.exe
set VM_ARGS=-Xms128m -Xmx512m -XX:MaxPermSize=256m

REM Launch UI auto tests.
set TEST_WORKSPACE=%driveLetter%\ws\integ_test1
mkdir %TEST_WORKSPACE%
%JAVA% %VM_ARGS% -Dorg.eclipse.equinox.http.jetty.customizer.class=com.thalesgroup.orchestra.framework.webservices.jettyConfigurator.DynamicPortJettyCustomizer -Dorg.eclipse.ecf.provider.remoteservice.addRegistrationRequestTimeout=500 -jar plugins\org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar -application org.eclipse.swtbot.eclipse.junit.headless.swtbottestapplication -testApplication com.thalesgroup.orchestra.framework.integration.tests.application -product com.thalesgroup.orchestra.framework.integration.tests.application.product -data %TEST_WORKSPACE% formatter=org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter,%driveLetter%/TEST-com.thalesgroup.orchestra.framework.ui.AllTests.xml -testPluginName com.thalesgroup.orchestra.framework.ui.tests -className com.thalesgroup.orchestra.framework.ui.tests.UITestSuite -os win32 -ws win32 -arch x86 -clean -consoleLog 

REM Launch integ auto tests.
set TEST_WORKSPACE=%driveLetter%\ws\integ_test2
mkdir %TEST_WORKSPACE%
%JAVA% %VM_ARGS% -Dorg.eclipse.equinox.http.jetty.customizer.class=com.thalesgroup.orchestra.framework.webservices.jettyConfigurator.DynamicPortJettyCustomizer -Dorg.eclipse.ecf.provider.remoteservice.addRegistrationRequestTimeout=500 -jar plugins\org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar -application org.eclipse.swtbot.eclipse.junit.headless.swtbottestapplication -testApplication com.thalesgroup.orchestra.framework.integration.tests.application -product com.thalesgroup.orchestra.framework.integration.tests.application.product -data %TEST_WORKSPACE% formatter=org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter,%driveLetter%/TEST-com.thalesgroup.orchestra.framework.integration.AllTests.xml -testPluginName com.thalesgroup.orchestra.framework.integration.tests -className com.thalesgroup.orchestra.framework.integration.tests.IntegrationTestSuite -os win32 -ws win32 -arch x86 -clean -consoleLog 

REM Launch integ auto tests.
set FMK_TEST_WORKSPACE=%driveLetter%\ws\integ_test3\fmk
mkdir %FMK_TEST_WORKSPACE%
REM Start the Orchestra Framework.
start %JAVA% %VM_ARGS% -Dorg.eclipse.equinox.http.jetty.customizer.class=com.thalesgroup.orchestra.framework.webservices.jettyConfigurator.DynamicPortJettyCustomizer -Dorg.eclipse.ecf.provider.remoteservice.addRegistrationRequestTimeout=500 -jar plugins\org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar -application com.thalesgroup.orchestra.framework.integration.tests.application -product com.thalesgroup.orchestra.framework.integration.tests.application.product -data %FMK_TEST_WORKSPACE% -clean
set TEST_WORKSPACE=%driveLetter%\ws\integ_test3\junit
mkdir %TEST_WORKSPACE%
REM Start tests (a Workbench will be started since we are doing SWTBot tests without a testapplication).
%JAVA% %VM_ARGS% -jar plugins\org.eclipse.equinox.launcher_1.3.0.v20120522-1813.jar -application org.eclipse.swtbot.eclipse.junit.headless.swtbottestapplication -data %TEST_WORKSPACE% formatter=org.apache.tools.ant.taskdefs.optional.junit.XMLJUnitResultFormatter,%driveLetter%\TEST-com.thalesgroup.orchestra.framework.integration.tests.api.AllTests.xml -os win32 -ws win32 -arch x86 -dev bin -testpluginname com.thalesgroup.orchestra.framework.integration.tests.api -classname com.thalesgroup.orchestra.framework.integration.tests.api.TestApi

:END
REM Delete substitution.
cd /d %WORKSPACE%
subst %driveLetter% /d
