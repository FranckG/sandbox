string mRootName = null
Skip mListeOfUriTarget = createString()
Skip mListeOfModule = createString()

pragma runLim, 0

// OrchestraVariableManager
OleAutoObj ovm_mOrchestraVariableManager
Regexp ovm_LastChar = regexp ".*(.)$"
int ovm_iInit = 0

void InitializeOrchestraEnv () {
	if (ovm_iInit == 0) {
		ovm_mOrchestraVariableManager = oleCreateAutoObject("Orchestra.VariableManager")
	}
	ovm_iInit++
}

void TerminateOrchestraEnv () {
	if (ovm_iInit == 1){
		oleCloseAutoObject(ovm_mOrchestraVariableManager)
	}
	ovm_iInit--
}

string ovm_getSharedDir () {
	string strDir = null
	oleGet(ovm_mOrchestraVariableManager, "GetVariableSharedDirectory", strDir)
	if (strDir == null) {
		warningBox("There is no Shared Directory in current Context.")
		halt
	}
	if (ovm_LastChar strDir)
		if (strDir[match 1] != "\\")
			strDir = strDir "\\"
	return strDir
}
// OrchestraVariableManager


bool findtarget(Object o) {
    Link l
    ModName_ otherMod = null
    ModuleVersion otherVersion = null
    Object othero
    string disp = null
    string s = null
    string temp
    int count
	string strID = null
	string strUriModule = null
	Module m = null
	string strModuleLabel = null
	string strUriTarget = ""
	bool bReturn = false
	string strLinkName = ""
	
	ouri_SetRootType("Doors")
	ouri_SetRootName(mRootName)
	
	for l in all(o->"*") do {
		m = module(l)
		strLinkName = name(m) 
		otherVersion = targetVersion l
		otherMod = module(otherVersion)
		if (null otherMod || isDeleted otherMod) continue
		othero = target l
		if (null othero) {
			m = load(otherVersion, false)
		} else {
			m = module(othero)
		}
		othero = target l
		if (null othero || isDeleted othero) continue
		ouri_SetObjectType(CObject)
		strID = uniqueID(otherMod)
		omi_getOMIForAModule(itemFromID(strID), strID)
		temp = othero.CAbsoluteNumberAttribute
		strID = strID..CIdentifierSeparatorString..temp
		ouri_SetObjectID(strID)
		strUriTarget = ouri_GetUri()
		put(mListeOfUriTarget, strUriTarget, strLinkName)
		
		ouri_SetObjectType(COrchestraModuleType)
		strID = uniqueID(otherMod)
		omi_getOMIForAModule(itemFromID(strID), strID)
		ouri_SetObjectID(strID)
		strUriModule = ouri_GetUri()
		if (!find(mListeOfModule, strUriModule)) {
			strModuleLabel = name(m)
			put(mListeOfModule, strUriModule, strModuleLabel)
		}
		bReturn = true
	}
    return bReturn
}

bool VerifyFile(string filePath) {
	if (canOpenFile(filePath, true)) {
		return true
	}
	return false
}

string createDirectories(string projectName) {
	InitializeOrchestraEnv()
	string strSharedDir = ovm_getSharedDir()
	TerminateOrchestraEnv()
	string retour = null
	
	retour = strSharedDir CAnteSlash hostname() "_" username()
	Stat s = create retour
	if (null(s)) {
		mkdir(retour)
	} else if(! directory(s)){
		delete s
	}
	retour = strSharedDir CAnteSlash hostname() "_" username() "\\MigrateToLM"
	s = create retour
	if (null(s)) {
		mkdir(retour)
	} else if(! directory(s)) {
		delete s
	}
	if (! null(s)){
		delete s
	}
	retour = retour CAnteSlash projectName ".xml"
	deleteFile retour
	return retour
}

void WriteArtefact(Object o, bool bRootLevel) {
	string strTemp
	string uriObject = null

	strTemp = o."PAPEETE ToolName"
	ouri_SetRootType(strTemp)
	strTemp = o."PAPEETE ProjectName"
	ouri_SetRootName(strTemp)
	if (bRootLevel) {
		ouri_SetObjectType("")
		ouri_SetObjectID("")
	} else {
		strTemp = o."PAPEETE ObjectType"
		ouri_SetObjectType(strTemp)
		strTemp = o."PAPEETE ObjectID"
		if (length(strTemp) == 0 || strTemp == null) {
			strTemp = o."PAPEETE ObjectName"
		}
		if (strTemp == null){
			strTemp = ""
		}
		ouri_SetObjectID(strTemp)
	}
	uriObject = ouri_GetUri()
	strTemp = o."Object Heading"
	if (strTemp[0] == '(')  {
		os_StringUntilSeparator(strTemp, ')', '"')
		if (strTemp[0] == ' ') {
			strTemp = strTemp[1:]
		}
	}
	Gef_WriteStartArtefact(uriObject, "", CObject, strTemp, "", "", "", false)
}

void MigrateToLM(string moduleFullName, string outputFullPath) {
	InitializeOrchestraEnv()
	InitializeGefFile()
	InitializeOrchestraUri()
	odenv_Initialize()
	
	Module surrogateModule
	surrogateModule = read(moduleFullName)

	if (surrogateModule != null) {
		string uriTarget = null
		string strLinkType = null
		Project projectRef = getParentProject(surrogateModule)
		string strProjetNom = name(projectRef)
		string strTempFileName = uniqueID(projectRef)
		odenv_FindRootNameForAProjectId(strTempFileName, mRootName)
		
		if (outputFullPath != null && length(outputFullPath) > 0) {
			if (VerifyFile(outputFullPath)) {
				strTempFileName = outputFullPath
			} else {
				warningBox "Cannot open file \""outputFullPath"\" for write"
				strTempFileName = createDirectories(strProjetNom)
			}
		} else {
			strTempFileName = createDirectories(strProjetNom)
		}
		ouri_SetRootType("Doors")
		ouri_SetRootName(mRootName)
		ouri_ClearParameters()
		
		Gef_InitOutputFile(strTempFileName)
		Gef_WriteStartArtefact(ouri_GetUri(), "", "", "", "", "", "", false)
		Object o
		bool writingArtefactLevel1 = false

		for o in (surrogateModule) do {
			if (!isDeleted(o)){
				if (level(o) == 1) {
					if (writingArtefactLevel1) {
						Gef_WriteEndArtefact()
						writingArtefactLevel1 = false
					}
					WriteArtefact(o, true)
					writingArtefactLevel1 = true
				}
				delete(mListeOfUriTarget)
				mListeOfUriTarget = createString()
			
				if (findtarget(o)) {
					if (level(o) != 1)
						WriteArtefact(o, false)
					for strLinkType in mListeOfUriTarget do {
						uriTarget = (string key mListeOfUriTarget)
						Gef_WriteReferenceForSurrogateModuleMigrationToLM(uriTarget, strLinkType)
					}
					if (level(o) != 1)
						Gef_WriteEndArtefact()
				}
			}
		}
		if (writingArtefactLevel1) {
			Gef_WriteEndArtefact()
		}
		string str
		for str in mListeOfModule do {
			uriTarget = (string key mListeOfModule)
			Gef_WriteStartArtefact(uriTarget, "", COrchestraModuleType, str, "", "", "", false)
			Gef_WriteEndArtefact()
		}
		Gef_WriteEndArtefact()
		Gef_CloseOutputFile()
		infoBox "Generate File : \"" strTempFileName "\""
	} else {
		errorBox "The module:\n\"" moduleFullName "\"\n doesn't exists!"
	}
	odenv_Terminate()
	TerminateOrchestraEnv()
	TerminateGefFile()
	TerminateOrchestraUri()
}
