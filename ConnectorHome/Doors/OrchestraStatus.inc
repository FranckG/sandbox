OleAutoObj orcs_clsOrchestraStatus

int orcs_Status_Severity_OK = 0
int orcs_Status_Severity_INFO = 1
int orcs_Status_Severity_WARNING = 2
int orcs_Status_Severity_ERROR = 4

void InitializeOrchestraStatus() {
	orcs_clsOrchestraStatus = oleCreateAutoObject("Orchestra.StatusDefinition")
}

void TerminateOrchestraStatus() {
	oleCloseAutoObject(orcs_clsOrchestraStatus)
}

string orcs_StatusDefinition_ToString () {
	string strRetour
	oleGet(orcs_clsOrchestraStatus, "ToString", strRetour)
	return strRetour
}

OleAutoObj orcs_StatusDefinition_Status () {
	OleAutoObj oStatus
	oleGet(orcs_clsOrchestraStatus, "status", oStatus)
	return oStatus
}

OleAutoObj orcs_StatusDefinition_WriteStatus (int intSeverity, string strUri, string strMessage, int intCode, bool blnCodeSpecified, OleAutoObj oParentStatus) {
	OleAutoObj oStatus
	OleAutoArgs autoArgs = create
	put(autoArgs, intSeverity)
	put(autoArgs, strUri)
	put(autoArgs, strMessage)
	put(autoArgs, intCode)
	put(autoArgs, blnCodeSpecified)
	put(autoArgs, oParentStatus)
	oleMethod(orcs_clsOrchestraStatus, "WriteStatus", autoArgs, oStatus)
	return oStatus
}

void orcs_StatusDefinition_WriteStatus (int intSeverity, string strUri, string strMessage, int intCode, bool blnCodeSpecified) {
	orcs_StatusDefinition_WriteStatus(intSeverity, strUri, strMessage, intCode, blnCodeSpecified, null)
}

