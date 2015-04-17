/*
Orchestra URI structure

Project:
	orchestra://Doors/<root Name>
Folder
	orchestra://Doors/<root Name>/Folder/<folder id>
Module
	orchestra://Doors/<root name>/Module/<value of module attribute Orchestra Module Id>
View
	orchestra://Doors/<root name>/View/<value of module attribute Orchestra Module Id>!<name of view>
Object
	if (ODM variable "/Orchestra/Connectors/Doors/Use IE PUID as identifier" != true) {
		orchestra://Doors/<root name>/Object/<value of module attribute Orchestra Module Id>!<value of attribute Absolute Number>
	} else {
		orchestra://Doors/<root name>/Object/<value of module attribute Orchestra Module Id>!<value of attribute IE PUID>
	}
BaselineSetDefinition
	orchestra://Doors/Environment/BaselineSetDefinition/<folder path>!<BaselineSetDefinition name>
BaselineSet
	orchestra://Doors/Environment/BaselineSet/<folder path>!<BaselineSetDefinition name>!<BaselineSet name>
*/
OleAutoObj ouri_mclsUri
string ouri_OrchestraUriType = ""
Skip ouri_skpListOfUri = create
Skip ouri_skpListOfRootNameProjectId = createString
Skip ouri_skpListOfProjectIdRootName = createString
Skip ouri_skpListOfBaselineForModuleId = createString
Skip ouri_skpListOfBaselineSetDefinitionForFolderPath = createString
Skip ouri_skpListOfModuleIdBaselineSet = createString
Skip ouri_skpListOfOdmVariable = createString
int ouri_iInit = 0

// List of ODM variable readed by the C# Connector
const string CUseIEPUIDAsIdentifier = "UseIEPUIDAsIdentifier"
const string CConfigurationDirectory = "ConfigurationDirectory"
const string CAddedProperties = "AddedProperties"
const string CDisplayLinkStatusDelimiter = "DisplayLinkStatusDelimiter"
const string CSegmentName = "SegmentName"

string ouri_GetODMVariable(string sVariableName) {
	string sRetour
	if (!find(ouri_skpListOfOdmVariable, sVariableName, sRetour)) {
		sRetour = ""
	}
	return sRetour
}

void InitializeOrchestraUri() {
	if (ouri_iInit == 0) {
		ouri_mclsUri = oleCreateAutoObject("Orchestra.IUri")
	}
	ouri_iInit++
}

void TerminateOrchestraUri() {
	if (ouri_iInit == 1){
		oleCloseAutoObject(ouri_mclsUri)
	}
	ouri_iInit--
}

void ouri_SetUri (string &strUri) {
	olePut(ouri_mclsUri, "Uri", strUri)
}

string ouri_GetUri () {
	string strRetour
	oleGet(ouri_mclsUri, "Uri", strRetour)
	return strRetour
}

void ouri_SetRootType (string strRootType) {
	olePut(ouri_mclsUri, "RootType", strRootType)
}

string ouri_GetRootType () {
	string strRetour
	oleGet(ouri_mclsUri, "RootType", strRetour)
	return strRetour
}

void ouri_SetRootName (string strRootName) {
	olePut(ouri_mclsUri, "RootName", strRootName)
}

string ouri_GetRootName () {
	string strRetour
	oleGet(ouri_mclsUri, "RootName", strRetour)
	return strRetour
}

void ouri_SetObjectID (string strobjectID) {
	olePut(ouri_mclsUri, "Id", strobjectID)
}

string ouri_GetObjectID () {
    string strRetour
    oleGet(ouri_mclsUri, "Id", strRetour)
    return strRetour
}

void ouri_SetObjectType (string strobjectType) {
	olePut(ouri_mclsUri, "Type", strobjectType)
}

string ouri_GetObjectType () {
	oleGet(ouri_mclsUri, "Type", ouri_OrchestraUriType)
	return ouri_OrchestraUriType
}

string ouri_GetFormattedParameters() {
	string strRetour
	oleGet(ouri_mclsUri, "GetFormattedParameters", strRetour)
	return strRetour
}

void ouri_ClearParameters(){
	oleMethod(ouri_mclsUri, "ClearParameters")
}

void ouri_AddParameter(string strName, string strValue) {
	if (strName == "") {
		return
	}
	OleAutoArgs autoArgs = create
	put(autoArgs, strName)
	put(autoArgs, strValue)
	oleMethod(ouri_mclsUri, "AddParameter", autoArgs, blnRetour)
	delete(autoArgs)
}

bool ouri_ExistParameter(string strName) {
	if (strName == "") {
		return false
	}
	bool blnRetour
	OleAutoArgs autoArgs = create
	put(autoArgs, strName)
	oleMethod(ouri_mclsUri, "ExistParameter", autoArgs, blnRetour)
	delete(autoArgs)
	return blnRetour
}

string ouri_Parameter(string strName) {
	if (strName == "") {
		return ""
	}
	string strRetour
	OleAutoArgs autoArgs = create
	put(autoArgs, strName)
	oleMethod(ouri_mclsUri, "Parameter", autoArgs, strRetour)
	delete(autoArgs)
	return strRetour
}

bool ouri_ExploreFolder(Folder f, string sBaselineSetDefinition, string sBaselineSet) {
	BaselineSetDefinition baseSetDef
	string s
	ModName_ mod
	bool bBaselineSetDefinitionFound = false
	
	for baseSetDef in f do {
		if (name(baseSetDef) != sBaselineSetDefinition) {
			continue
		}
		bBaselineSetDefinitionFound = true
		s = read(baseSetDef)
		if (!null s) {

		} else if (isEmpty(baseSetDef)) {

		} else {
			bool bErrorInEnvironment = false
			for mod in baseSetDef do {
				if (find(ouri_skpListOfModuleIdBaselineSet, uniqueID(mod), s)) {
					s = "The Doors environment  is not valid!\nThe module " fullName(mod) " is present in more than one Baseline Set Definition  (" s " and " sBaselineSetDefinition ") in environments."
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", s, 64074, true, orcs_StatusDefinition_Status())
					bErrorInEnvironment = true
				} else {
					put(ouri_skpListOfModuleIdBaselineSet, uniqueID(mod), sBaselineSetDefinition)
				}	
			}
			if (bErrorInEnvironment) {
				return false
			}
			if (sBaselineSet != "current") {
				BaselineSet bs
				for bs in baseSetDef do {
					if (versionID(bs)"" != sBaselineSet) {
						continue
					}
					ModuleVersion mv
					for mv in bs do {
						s = ""
						mod = module(mv)
						omi_getOMIForAModule(itemFromID(uniqueID(mod)), s)
						if (length(s) > 0) {
							put(ouri_skpListOfBaselineForModuleId, s, versionString mv)
						}
					}
					return true
				}
			} else {
				return true
			}
		}
	}
	if (!bBaselineSetDefinitionFound) {
		s = "The DOORS environment is not valid!\nThe Baseline Set Definition  " sBaselineSetDefinition " does not exist."
	} else {
		s = "The DOORS environment is not valid!\nThe Baseline Set " sBaselineSet //-
      		" does not exist for the Baseline Set Definition " sBaselineSetDefinition "."
	}
	orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", s, 64075, true, orcs_StatusDefinition_Status())
	return false
}

bool ouri_UriFile_Split(
	string &UriFilePath
) {
	delete(ouri_skpListOfUri)
	delete(ouri_skpListOfRootNameProjectId)
	delete(ouri_skpListOfProjectIdRootName)
	delete(ouri_skpListOfBaselineForModuleId)
	delete(ouri_skpListOfBaselineSetDefinitionForFolderPath)
	ouri_skpListOfUri = create
	ouri_skpListOfRootNameProjectId = createString
	ouri_skpListOfProjectIdRootName = createString
	ouri_skpListOfBaselineForModuleId = createString
	ouri_skpListOfBaselineSetDefinitionForFolderPath = createString
	noError()
	Stat s = create UriFilePath
	int intSeparatorPosition
	Buffer bufString = create
	Buffer bufRootName = create
	Buffer bufBaseline = create
	Buffer bufId = create
	Buffer bufFolderPath = create
	Buffer bufBaselineSetDefinition = create
	Buffer bufBaselineSet = create
	bool bNoError = true
	Item itemRef

	if (!null s) {
		if (regular s) {		
			string sErrorMessage
			delete s
			Stream file = read(UriFilePath, CP_UTF8)
			int iIndexUri = 0
			while (! end file) {
				file >= bufString
				if (bufString[0:7]"" == "variable") { //Variable ODM
					Regexp line = regexp "variable!([^=]*)=(.*)"
					if (line bufString) {
						put(ouri_skpListOfOdmVariable, stringOf(bufString[match 1]), stringOf(bufString[match 2])) "\n"
					}
				} else if (bufString[0:8]"" == "orchestra") { //Uri Orchestra
					put(ouri_skpListOfUri, iIndexUri++, stringOf bufString)
				} else if (bufString[0:6]"" == "project") { //Info Projet
					intSeparatorPosition = contains(bufString, CIdentifierSeparatorChar)
					if (intSeparatorPosition == -1) continue
					bufString = bufString[(intSeparatorPosition + 1):]
					intSeparatorPosition = contains(bufString, CIdentifierSeparatorChar)
					bufRootName = bufString[0:(intSeparatorPosition - 1)]
					bufId = bufString[(intSeparatorPosition + 1):]
					itemRef = itemFromID(tempStringOf(bufId))
					if (null itemRef) {
						sErrorMessage = "The project defined by " tempStringOf(bufId) " does not exist."
						orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", sErrorMessage, 64076, true, orcs_StatusDefinition_Status())
						bNoError = false
					} else {
						put(ouri_skpListOfRootNameProjectId, stringOf bufRootName, stringOf bufId)
						put(ouri_skpListOfProjectIdRootName, stringOf bufId, stringOf bufRootName)
					}
				} else if (bufString[0:10]"" == "baselineSet") {//Info BaselineSet
					intSeparatorPosition = contains(bufString, CIdentifierSeparatorChar)
					if (intSeparatorPosition == -1) continue
					bufString = bufString[(intSeparatorPosition + 1):]
					intSeparatorPosition = contains(bufString, CIdentifierSeparatorChar)
					bufFolderPath = bufString[0:(intSeparatorPosition - 1)]
					bufString = bufString[(intSeparatorPosition + 1):]
					intSeparatorPosition = contains(bufString, CIdentifierSeparatorChar)
					bufBaselineSetDefinition = bufString[0:(intSeparatorPosition - 1)]
					bufBaselineSet = bufString[(intSeparatorPosition + 1):]
					put(ouri_skpListOfBaselineSetDefinitionForFolderPath, stringOf bufFolderPath, stringOf bufBaselineSetDefinition)
					Folder f = null
					if (bufFolderPath[0] == '/') {
						f = folder(tempStringOf(bufFolderPath))
					} else {
						itemRef = itemFromID(tempStringOf(bufFolderPath))
						if (!null itemRef) {
							f = folder(itemRef)
						}
					}
					if (null f) {
						sErrorMessage = "The folder defined by " tempStringOf(bufFolderPath) " does not exist."
						orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", sErrorMessage, 64077, true, orcs_StatusDefinition_Status())
						bNoError = false
					}else if (!ouri_ExploreFolder(f, stringOf bufBaselineSetDefinition, stringOf bufBaselineSet)) {
						bNoError = false
					}
				} else if (bufString[0:5]"" == "module") { // Info Module (Compatibilité avec les .doors, l'env ne passe jamais ici)
					intSeparatorPosition = contains(bufString, CIdentifierSeparatorChar)
					if (intSeparatorPosition == -1) continue
					bufString = bufString[(intSeparatorPosition + 1):]
					intSeparatorPosition = contains(bufString, CIdentifierSeparatorChar)
					bufId = bufString[0:(intSeparatorPosition - 1)]
					bufBaseline = bufString[(intSeparatorPosition + 1):]
					put(ouri_skpListOfBaselineForModuleId, stringOf bufId, stringOf bufBaseline)
				}
				if (end file)
					break
			}
			close file
			string sSegment
			sSegment = ouri_GetODMVariable(CSegmentName)
			if (sSegment == CSilver) {
				string sCacheModule
				string sPartialName = "/Orchestra Configuration/Orchestra Cache " getDatabaseIdentifier() "."
				Project projectRef
				string sProjectID
				for projectRef in database do {
					sProjectID = uniqueID(projectRef)
					sCacheModule = fullName(projectRef) sPartialName sProjectID
					if (exists module sCacheModule) {
						put(ouri_skpListOfRootNameProjectId, name(projectRef), sProjectID)
						put(ouri_skpListOfProjectIdRootName, sProjectID, name(projectRef))
					}
				}
			}
		} else {
			delete s
		}
	}
	delete(bufRootName)
	delete(bufBaseline)
	delete(bufId)
	delete(bufString)
	delete(bufFolderPath)
	delete(bufBaselineSetDefinition)
	delete(bufBaselineSet)
	deleteFile UriFilePath
	return bNoError
}

void ouri_ExtractDoorsIdFromOrchestraId (
	string sObjectType,
	string sTemp,
	string &sModuleID,
	string &sObjectID,
	string &sViewName
) {
	sModuleID = null
	sObjectID = null

	if (sObjectType == COrchestraModuleType || sObjectType == CFolder) {
		sModuleID = sTemp
	} else if (sObjectType == CView) {
		sViewName = sTemp
		sModuleID = os_StringUntilSeparator(sViewName, CIdentifierSeparatorChar, ' ')
	} else if (sObjectType == CObject || sObjectType == CTable || sObjectType == CRow) {
		sObjectID = sTemp
		sModuleID = os_StringUntilSeparator(sObjectID, CIdentifierSeparatorChar, ' ')
	} 
}

string ouri_findBaselineForAModuleInEnvironment (
	string sModuleID
) {
	string sBaseline

	if (find(ouri_skpListOfBaselineForModuleId, sModuleID, sBaseline)) {
		return sBaseline
	}
	return "current"
}
