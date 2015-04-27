set WORKSPACE=d:\temp\ws
set BUILD_ID=213023151
set JOB_REVISION=4.0.26
set CLEARCASE_VIEWPATH=M:\PB188043_OrcFmk_5.0_int

call gradle -f -i -S -b OrchestraFrameworkSDKfeatures.gradle
call gradle -S -b OrchestraFrameworkSDKmerge.gradle