OleAutoObj oGef

void InitializeGefFile () {
	oGef = oleCreateAutoObject("Orchestra.Gef")
}

void TerminateGefFile () {
	oleCloseAutoObject(oGef)
}

void Gef_setExtraFileDirectory (string &strExtraFileDirectory) {
	olePut(oGef, "ExtraFileDirectory", strExtraFileDirectory)
}

string Gef_getExtraFileDirectory () {
	string strExtraFileDirectory
	oleGet(oGef, "ExtraFileDirectory", strExtraFileDirectory)
	return strExtraFileDirectory
}

void Gef_InitOutputFile (
    string &strOutputFileName
) {
	OleAutoArgs autoArgs = create
	put(autoArgs, strOutputFileName)
	oleMethod(oGef, "InitOutputFile", autoArgs)
	delete(autoArgs)
}

void Gef_CloseOutputFile () {
	oleMethod(oGef, "CloseOutputFile")
}

string Gef_WriteStartArtefact (
    string uri,
    string name,
    string type,
    string label,
    string hash,
    string filePath,
    string sVersion,
    bool fullPath
){
	OleAutoArgs autoArgs = create
	put(autoArgs, uri)
	put(autoArgs, name)
	put(autoArgs, type)
	put(autoArgs, label)
	put(autoArgs, hash)
	put(autoArgs, filePath)
	put(autoArgs, sVersion)
	put(autoArgs, fullPath)
	string sRetour = ""
	
	sRetour = oleMethod(oGef, "WriteStartArtefact", autoArgs)
	delete(autoArgs)
	if (length(sRetour) > 13)
		return sRetour[13:]
	return sRetour
}

void Gef_WriteEndArtefact () {
	oleMethod(oGef, "WriteEndArtefact")
}

string Gef_WriteProperty (
    string name,
    string type,
    string content,
    string nature
){
	OleAutoArgs autoArgs = create
	put(autoArgs, name)
	put(autoArgs, type)
	put(autoArgs, content)
	put(autoArgs, nature)
	string sRetour = ""
	sRetour = oleMethod(oGef, "WriteProperty", autoArgs)
	delete(autoArgs)
	if (length(sRetour) > 13)
		return sRetour[13:]
	return sRetour
}

void Gef_WriteTextualDescription (
    string name,
    string type,
    string url,
    string nature,
    string content
){
	OleAutoArgs autoArgs = create
	put(autoArgs, name)
	put(autoArgs, type)
	put(autoArgs, url)
	put(autoArgs, nature)
	put(autoArgs, content)
	oleMethod(oGef, "WriteTextualDescription", autoArgs)
	delete(autoArgs)
}

void Gef_WriteReferenceForSurrogateModuleMigrationToLM (
	string uriTarget,
	string typeLink
){
	OleAutoArgs autoArgs = create
	put(autoArgs, uriTarget)
	put(autoArgs, typeLink)
	oleMethod(oGef, "WriteReferenceForSurrogateModuleMigrationToLM", autoArgs)
	delete(autoArgs)
}

string Gef_WriteInternalReference (
    string uriTarget,
    string typeLink,
    bool outLink,
    string name,
    string label
){
	OleAutoArgs autoArgs = create
	put(autoArgs, uriTarget)
	put(autoArgs, typeLink)
	put(autoArgs, outLink)
	put(autoArgs, name)
	put(autoArgs, label)
	string sRetour = ""
	sRetour = oleMethod(oGef, "WriteInternalReference", autoArgs)
	delete(autoArgs)
	if (length(sRetour) > 13)
		return sRetour[13:]
	return sRetour
}

string Gef_FormatFullNamePart (
	string &strFullNamePart
) {
	if (strFullNamePart == "") {
		return ""
	}
	string strRetour
	OleAutoArgs autoArgs = create
	put(autoArgs, strFullNamePart)
	oleMethod(oGef, "FormatFullNamePart", autoArgs, strRetour)
	delete(autoArgs)
	return strRetour
}

