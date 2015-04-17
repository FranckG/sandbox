OleAutoObj odenv_OrchestraDoorsEnvironment

void odenv_Initialize() {
	odenv_OrchestraDoorsEnvironment = oleCreateAutoObject("Orchestra.EnvironmentDoors")
}

void odenv_Terminate() {
	oleCloseAutoObject(odenv_OrchestraDoorsEnvironment)
}

void odenv_FindRootNameForAProjectId(
	string &sProjectId,
	string &sRetour
) {
	if (sProjectId == "") {
		return
	}
	OleAutoArgs autoArgs = create
	put(autoArgs, sProjectId)
	oleMethod(odenv_OrchestraDoorsEnvironment, "FindRootNameForAProjectId", autoArgs, sRetour)
	delete(autoArgs)
}