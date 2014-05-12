Option Explicit

' This VBS (visual Basic Scripting) file allow to execute IVV Metrics computing by external call.
' Before using this script, by sure all "IVV collect data" sheet information are corretly setted (uri, automatic rows addition, full reload or not etc...)

' --------------------------< TECHNICAL PARAMETERS >--------------------------
'IVV metrics Excel file extension
Public Const IVV_METRICS_FILE_EXTENSION = ".xls"

' view Excel during execution
Public Const IVV_METRICS_EXCEL_VISIBLE = true

' VBA function to call on external execution
Public Const IVV_METRICS_EXTERNAL_CALL_FUNCTION = "ExternalCall.Run"
' ----------------------------------------------------------------------------



On Error Resume Next

' Call main function
Orchestra_IVVQ_Metrics_External_Call_Execution



Sub Orchestra_IVVQ_Metrics_External_Call_Execution()

	' for debug
	'msgbox "Debug informations:" & vbCrlf _
    '   & "    - Script filename: " & Wscript.ScriptName & vbCrlf _
	'	& "    - Script full name: " & Wscript.ScriptFullName & vbCrlf _
	'	& "    - Script engine: " & ScriptEngine  & vbCrlf _
	'	& "    - Script engine build number: " & ScriptEngineMajorVersion & "." & ScriptEngineMinorVersion & "." & ScriptEngineBuildVersion & vbCrlf _
	'	& "    - Script host version: " & Wscript.Version
    
    
	' get vbs file current path and filename without extension
	' ps: vbs cript should be located in the same folder than the ivv metrics Excel file and the same name except extension.
	Dim fso
	Set fso = CreateObject("Scripting.FileSystemObject")

	Dim vbsFileNameWithoutExtension
	vbsFileNameWithoutExtension = fso.GetBaseName(WScript.ScriptFullName)

	Dim vbsFilePath
	vbsFilePath = fso.GetParentFolderName(WScript.ScriptFullName)

	Set fso = Nothing
	
    ' create/instanciate excel application
	Dim excelApp
    Set excelApp = CreateObject("Excel.Application") ' create Excel application
    excelApp.Visible = IVV_METRICS_EXCEL_VISIBLE ' set Excel display or not
	excelApp.DisplayAlerts = False ' no display alerts
    excelApp.AskToUpdateLinks = False ' no asking for links updating

    ' load ivv metrics Excel file in excel instance created
	Dim excelWorkbook
    Set excelWorkbook = excelApp.Workbooks.Open(vbsFilePath & "\" & vbsFileNameWithoutExtension & IVV_METRICS_FILE_EXTENSION) ' open ivv metrics Excel file

	' run the Excel macro related to the external call use of ivv metrics.
	excelApp.Run IVV_METRICS_EXTERNAL_CALL_FUNCTION

    excelWorkbook.Save ' save workbook
	excelWorkbook.Close ' close workbook and save changes
    Set excelWorkbook = Nothing

	excelApp.Quit
    Set excelApp = Nothing

End Sub
