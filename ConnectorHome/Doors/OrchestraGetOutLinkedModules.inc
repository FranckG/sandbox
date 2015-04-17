#include <OrchestraConstantes.inc>
#include <OrchestraString.inc>
#include <OMIManagement/OrchestraManageOMI.inc>
#include <OrchestraStatus.inc>
#include <OrchestraUri.inc>
#include <OrchestraGef.inc>
#include <OrchestraInstall.inc>

Skip ogolm_mLinkModule = createString
string ogolm_mstrRootName
string ogolm_mstrConfDir_Env

void ogolm_ReadLinkModuleFile () {
	string strFileName = ogolm_mstrConfDir_Env..CConnectorDoorsConfigRelativePath..CAnteSlash..CLinkModuleFile
	Stat s = create strFileName
	if (!null s) {
		Stream input = read strFileName
		string str
		string strAttributeForLabel
		while (true) {
			input >> strAttributeForLabel
			if (end of input) break
			if (length(strAttributeForLabel) > 0) {
				str = os_StringUntilSeparator(strAttributeForLabel, ';', ' ')
				put(ogolm_mLinkModule, str, strAttributeForLabel)
			}
		}
		close input
		delete(s)
	}
}

void OrchestraGetOutLinkedModules (
	string UriFilePath,
	string ResultFilePath,
	string StatusFilePath,
	string DxlFilePath,
	bool blnExpandView,
	bool blnLinkManagerExport
) {
	Item itemRef
	Folder folderRef
	string strOldRootName = ""
	string strUri
	string strType
	string strModuleID
	string sOrchestraModuleId
	string sProjectId
	
	noError()
	
	InitializeGefFile()
	InitializeOrchestraUri()
	InitializeOrchestraStatus()
	
	if (ouri_UriFile_Split(UriFilePath)) {
		ogolm_mstrConfDir_Env = ouri_GetODMVariable(CConfigurationDirectory)
		ogolm_ReadLinkModuleFile()
		Gef_InitOutputFile(ResultFilePath)
		
		for strUri in ouri_skpListOfUri do {
			int i = (int key ouri_skpListOfUri)
	
			ouri_SetUri(strUri)
			strType = ouri_GetObjectType()
			ogolm_mstrRootName = ouri_GetRootName()
			if (strType == COrchestraModuleType || strType == CView) {
				Gef_WriteStartArtefact(strUri,"",strType,"","","","",false)
				strModuleID = ouri_GetObjectID()
				find(ouri_skpListOfRootNameProjectId, ogolm_mstrRootName, sProjectId)
				omi_TransformIdOMIToIdDoors(strModuleID, strType, sProjectId, sOrchestraModuleId)
				itemRef = itemFromID(strModuleID)
				if (!null itemRef) {
					Folder parentFolder = getParentFolder(itemRef)
					LinkModuleDescriptor lmd
					string fullNameTarget
					string fullNameSource = fullName(itemRef)
					string linkName
					string uriTarget
					string targetProjectName
					for lmd in parentFolder do {
						linkName = name(lmd)
						if (matches(".*/([^/]*)", linkName)) {
							linkName = linkName[match 1]
							if (length(linkName) > 0 && !find(ogolm_mLinkModule, linkName)) continue
							if (getSourceName(lmd) == fullNameSource) {
								fullNameTarget = getTargetName(lmd)
								ModName_ moduleTarget = module(fullNameTarget)
								targetProjectName = name(getParentProject(moduleTarget))
								if (matches(".*/([^/]*)", fullNameTarget)) {
									fullNameTarget = fullNameTarget[match 1]
									ouri_SetRootName(targetProjectName)
									ouri_SetObjectType(COrchestraModuleType)
									strModuleID = uniqueID(moduleTarget)
									omi_getOMIForAModule(itemFromID(strModuleID), strModuleID)
									if(strModuleID != null){
										ouri_SetObjectID(strModuleID)
										uriTarget = ouri_GetUri()
										
										Gef_WriteInternalReference(uriTarget,linkName,true,fullNameTarget,fullNameTarget)
									}
								}							
							}
						}
						
					}
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_OK, strUri, "", 0, false, orcs_StatusDefinition_Status())
				} else {
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, strUri, 
								"The module with the identifier " strModuleID " does not exist!", 64048, true, orcs_StatusDefinition_Status())				
				}
				Gef_WriteEndArtefact()
			} 
		}
		Gef_CloseOutputFile()
	}
	if (length(StatusFilePath) > 0) {
		Stream statusFile
		statusFile = write StatusFilePath
		statusFile << orcs_StatusDefinition_ToString()
		close statusFile
	}
	
	TerminateOrchestraStatus()
	TerminateGefFile()
	TerminateOrchestraUri()
}

