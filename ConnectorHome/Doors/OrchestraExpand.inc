#include <OrchestraConstantes.inc>
#include <OrchestraString.inc>
#include <OMIManagement/OrchestraManageOMI.inc>
#include <OrchestraStatus.inc>
#include <OrchestraUri.inc>
#include <OrchestraGef.inc>
#include <OrchestraInstall.inc>

string oex_mstrRootName

void oex_ExploreModule(Item itemRef, string strParentName, bool blnExpandView) {
	string sModuleLabel
	string strModuleName
	string strObjectNameEncoded
	string strObjectID
	string strBaselineName
	string strObjectVersion
	string strTemp
	string strViewName
	string strViewNameEncoded

// prise en compte des OMI ... 
	omi_getOMIForAModule(itemRef, strObjectID)
	if (strObjectID == null) {
		orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, "", 
			"The module \"" fullName(itemRef) "\" is not tagged for Orchestra.", 
			64047, false, orcs_StatusDefinition_Status)
		return
	}
	strModuleName = name(itemRef)
	strObjectNameEncoded = Gef_FormatFullNamePart(strModuleName)
	strBaselineName = ouri_findBaselineForAModuleInEnvironment(strObjectID)
	strObjectVersion = strBaselineName
	if (length(strParentName) != 0)
		strObjectNameEncoded = strParentName.."."..strObjectNameEncoded
	if (strBaselineName != "current") {
		strTemp = Gef_FormatFullNamePart(strBaselineName)
		strObjectNameEncoded = strObjectNameEncoded " "
		strObjectNameEncoded = strObjectNameEncoded..strTemp
		sModuleLabel = strModuleName " - " strBaselineName
	} else {
		sModuleLabel = strModuleName
	}

	ouri_SetObjectID(strObjectID)
	ouri_SetObjectType(COrchestraModuleType)
	Gef_WriteStartArtefact(ouri_GetUri(), strObjectNameEncoded, COrchestraModuleType, sModuleLabel,
							strObjectVersion, "", "", false)
	Gef_WriteProperty(CDropLabel, CTypeString, "(" oex_mstrRootName " " COrchestraModuleType ") " strModuleName, "")
	if (blnExpandView) {
		moduleRef = read(fullName(itemRef), false)
		for strViewName in views moduleRef do {
			strViewNameEncoded = Gef_FormatFullNamePart(strViewName)
			ouri_SetObjectID(strObjectID..CIdentifierSeparatorString..strViewName)
			ouri_SetObjectType(CView)
			Gef_WriteStartArtefact(ouri_GetUri(), strViewNameEncoded, CView, strViewName,
							   "", "", "", false)
			Gef_WriteProperty(CDropLabel, CTypeString, "(" oex_mstrRootName " " strModuleName " " CView ") " strViewName, "")
			Gef_WriteEndArtefact()
		}
		close(moduleRef)
	}
	Gef_WriteEndArtefact()
}

void oex_ExploreFolder (Folder f, string strParentName, bool blnExpandView, int iLevel) {
	string strFolderName
	string strFolderNameEncoded
	Item itemRef
	int nbFolder = 0
	int nbModule = 0
    
	iLevel++
	
	strFolderName = name(f)
	strFolderNameEncoded = Gef_FormatFullNamePart(strFolderName)
	if (length(strParentName) != 0)
		strFolderNameEncoded = strParentName.."."..strFolderNameEncoded
	if (type(f)"" != CProject) {
		ouri_SetObjectID(uniqueID(f)"")
		ouri_SetObjectType(type(f)"")
		Gef_WriteStartArtefact(ouri_GetUri(), strFolderNameEncoded, type(f)"", strFolderName, "", "", "", false)
	}
	if (type(f)"" == CProject) {
		strFolderNameEncoded = ""
	}
	for itemRef in f do {
		if (type(itemRef)"" == CModuleTypeFormal) {
			nbModule++ 
		} else {
			if (type(itemRef)"" == CFolder && name(itemRef) != CRepertoireConfigName && name(itemRef) != COrchestraInstallationFolderName) {
				nbFolder++
			}
		}
	}
	string lstOfFolder[nbFolder]
	string lstOfModule[nbModule]
	int indexModule = 0
	int indexFolder = 0
	for itemRef in f do {
		if (type(itemRef)"" == CModuleTypeFormal) {
			lstOfModule[indexModule++] = fullName(itemRef)
		} else {
			if (type(itemRef)"" == CFolder && name(itemRef) != CRepertoireConfigName && name(itemRef) != COrchestraInstallationFolderName) {
				lstOfFolder[indexFolder++] = fullName(itemRef)
			}
		}
	}
	sort(lstOfFolder)
	sort(lstOfModule)
	for (indexFolder = 0; indexFolder < nbFolder; indexFolder++) {
		Folder subFolder = folder(lstOfFolder[indexFolder])
		oex_ExploreFolder(subFolder, strFolderNameEncoded, blnExpandView, iLevel)
	}
	for (indexModule = 0; indexModule < nbModule; indexModule++) {
		oex_ExploreModule(item(lstOfModule[indexModule]), strFolderNameEncoded, blnExpandView)
	}
	
	if (type(f)"" != CProject) 
		Gef_WriteEndArtefact()
}

void OrchestraExpand (
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
		Gef_InitOutputFile(ResultFilePath)
		
		for strUri in ouri_skpListOfUri do {
			int i = (int key ouri_skpListOfUri)
	
			ouri_SetUri(strUri)
			strType = ouri_GetObjectType()
			oex_mstrRootName = ouri_GetRootName()
			if (strOldRootName != oex_mstrRootName) {
				find(ouri_skpListOfRootNameProjectId, oex_mstrRootName, sProjectId)
				if (strType != COrchestraModuleType)
					Gef_WriteStartArtefact(strUri, "", "", "", "", "", "", false)
			}
			if (strType == COrchestraModuleType) {
				strModuleID = ouri_GetObjectID()
				omi_TransformIdOMIToIdDoors(strModuleID, strType, sProjectId, sOrchestraModuleId)
				itemRef = itemFromID(strModuleID)
				if (!null itemRef) {
					oex_ExploreModule(itemRef, "", true)
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_OK, strUri, "", 0, false, orcs_StatusDefinition_Status())
				} else {
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, strUri, 
								"The module with the identifier " strModuleID " does not exist!", 64048, true, orcs_StatusDefinition_Status())				
				}	
			} else {
				itemRef = itemFromID(sProjectId)
				if (!null itemRef) {
					folderRef = folder(fullName(itemRef))
					oex_ExploreFolder(folderRef, "", blnExpandView, 0)
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_OK, strUri, "", 0, false, orcs_StatusDefinition_Status())
				} else {
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, strUri, 
								"The project with identifier: " sProjectId " does not exists!", 64049, true, orcs_StatusDefinition_Status())
				}
			}
			if (strOldRootName != oex_mstrRootName) {
				if (strType != COrchestraModuleType)
					Gef_WriteEndArtefact()
				strOldRootName = oex_mstrRootName
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

