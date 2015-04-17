pragma encoding, "UTF-8"
#include <OrchestraConstantes.inc>
#include <OrchestraString.inc>
#include <OMIManagement/OrchestraManageOMI.inc>
#include <OrchestraStatus.inc>
#include <OrchestraUri.inc>
#include <OrchestraGef.inc>
//RCM
#include <RCMManagement/OrchestraRCM.inc>
///RCM

void OrchestraGetArtefactsMetadata (
	string UriFilePath,
	string ResultFilePath,
	string StatusFilePath,
	string DxlFilePath,
	bool blnExpandView,
	bool blnLinkManagerExport
) {
	string strViewName
	string strObjectType
	string strObjectID
	string strModuleID
	string sOrchestraModuleId
	string strTemp
	int intAbsoluteNumber
	Module moduleRef
	Item itemRef
	string strBaseline
	int intMajor
	int intMinor
	string strSuffix
	Project projectRef
	string strResult
	string strTitle
	string strUri
	string sProjectId
	bool bNoError
	string sVersionOfArtefact
	string sPathOfArtefact
	History historyOfModule
	Date dLastModificationDateTime

	noError()

	InitializeGefFile()
	InitializeOrchestraUri()
    InitializeOrchestraStatus ()

	if (ouri_UriFile_Split(UriFilePath)) {
		Gef_InitOutputFile(ResultFilePath)
		
		for strUri in ouri_skpListOfUri do {
			sVersionOfArtefact = ""
			sPathOfArtefact = ""
			ouri_SetUri(strUri)
			find(ouri_skpListOfRootNameProjectId, ouri_GetRootName(), sProjectId)
	
			strObjectType = ouri_GetObjectType()
			strTemp = ouri_GetObjectID()
			omi_TransformIdOMIToIdDoors(strTemp, strObjectType, sProjectId, sOrchestraModuleId)
			strViewName = ouri_Parameter(CViewParameterName)
			ouri_ExtractDoorsIdFromOrchestraId(strObjectType, strTemp, strModuleID, strObjectID, strViewName)
			
			if (strObjectType == "" || strObjectType == CFolder) {
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri,
						"Versions are not supported for projects and folders.", 64050, true, orcs_StatusDefinition_Status())
			} else {
				itemRef = itemFromID(strModuleID)
				sPathOfArtefact = fullName(itemRef)
				if (itemRef != null) {
					projectRef = getParentProject(itemRef)
					strResult = openProject(fullName(projectRef))
					if (null strResult) {
						strBaseline = ouri_findBaselineForAModuleInEnvironment(sOrchestraModuleId)
						if (strBaseline != "current") {
							sVersionOfArtefact = strBaseline
						} else {
							moduleRef = read(fullName(itemRef), false, true)
							if (moduleRef == null) {
								orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri,
										"Cannot open the Doors Module.", 64051, true, orcs_StatusDefinition_Status())
							} else {
								for historyOfModule in moduleRef do {
									dLastModificationDateTime = historyOfModule.date
								}
								sVersionOfArtefact = dLastModificationDateTime""
							}
						}
					} else {
						orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri,
								"Cannot open the Doors Project.", 64052, true, orcs_StatusDefinition_Status())
					}
				} else {
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri,
							"Cannot find the Doors Module.", 64053, true, orcs_StatusDefinition_Status())
				}
			}
			if (length(sVersionOfArtefact) > 0) {
				Gef_WriteStartArtefact(strUri, "", "", "", "", sPathOfArtefact, sVersionOfArtefact, false)
				Gef_WriteProperty("moduleId", "string", strModuleID, "")
				Gef_WriteEndArtefact()
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_OK, strUri, "", 0, false, orcs_StatusDefinition_Status())
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
	
	TerminateOrchestraUri()
    TerminateOrchestraStatus()
    TerminateGefFile()
	deleteFile DxlFilePath
}

