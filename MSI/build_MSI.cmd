set WORKSPACE=D:\ConnectorDOORS
set TIMESTAMP=0000000000000
set VERSION=0.0.0
set CLEARCASE_VIEWPATH=M:\cm_P_ConnectorDoors_V2.3_dev

msigen -w %WORKSPACE% -o dist\ConnectorDOORS-%VERSION%.msi -f %MSI_RESOURCES%\scripts\MSI-pre.py -f %CLEARCASE_VIEWPATH%\ReqMgt_comp\ConnectorDoors\MSI\ConnectorDOORS.py %CLEARCASE_VIEWPATH%\ReqMgt_comp\ConnectorDoors\MSI\ConnectorDOORS.aip

