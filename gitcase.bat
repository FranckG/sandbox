REM 
REM CLEAN GIT REPO
REM
git clean -dfx
git reset --hard HEAD
REM .git and .git * are not the same
del /F /Q /Q .git .git* 

REM
REM CLEARFSIMPORT
REM
set VIEW_TAG=fgi_ProjectA_int
cleartool startview %VIEW_TAG%
REM clearfsimport -recurse -rmname -nsetevent . m:\%VIEW_TAG%\Test_comp\Test_CCEnv