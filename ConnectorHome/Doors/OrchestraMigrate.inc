#include <OrchestraConstantes.inc>
#include <OrchestraString.inc>
#include <OMIManagement/OrchestraManageOMI.inc>
#include <OrchestraStatus.inc>
#include <OrchestraUri.inc>

pragma encoding, "UTF-8"

void OrchestraMigrate(
	string UriFilePath, 
	string ResultFilePath, 
	string StatusFilePath, 
	string DxlFilePath, 
	bool blnExpandView, 
	bool blnLinkManagerExport) 
{
	string sUri
	string sOldUri
	string sType
	string sId
	string sModuleId
	string sObjectId
	string sViewName
	string sOrchestraModuleId
	string sNewUri
	string sRootName
	string sProjectId
	bool bUseIEPUIDAsIdentifier
	Skip skpListOfModuleToClose = create
	string sModuleName
		
	InitializeOrchestraUri()
    InitializeOrchestraStatus()
	if (ouri_GetODMVariable(CUseIEPUIDAsIdentifier)"" == "true") {
		bUseIEPUIDAsIdentifier = true
	} else {
		bUseIEPUIDAsIdentifier = false
	}
	
	if (ouri_UriFile_Split(UriFilePath)) {
		for sUri in ouri_skpListOfUri do {
			int i = (int key ouri_skpListOfUri)
			ouri_SetUri(sUri)
			sRootName = ouri_GetRootName()
			find(ouri_skpListOfRootNameProjectId, sRootName, sProjectId)
				
			sType = ouri_GetObjectType()
	    	sId= ouri_GetObjectID()
	    	sViewName = ouri_Parameter(CViewParameterName)
	    	sOldUri = ouri_Parameter(COldUriParameterName)
	    	ouri_ClearParameters()
	    	
			ouri_ExtractDoorsIdFromOrchestraId(sType, sId, sModuleId, sObjectId, sViewName)
			sOrchestraModuleId = omi_getOMIInCache(sModuleId, sProjectId)
			if (sOrchestraModuleId == sModuleId) {
				Item iModule = itemFromID(sModuleId)
				Project pParent = null
				string sProjectName
	
				if (!null iModule) {
					sModuleName = fullName(iModule)
					pParent = getParentProject(iModule)
				} else {
					sModuleName = sModuleId
				}
				if (!null pParent)
					sProjectName = fullName(pParent)
				else
					sProjectName = "containing " sModuleName
				
				string sMessage = "The module " sModuleName " is not found in the Orchestra Cache.\n" //-
          						  "Reasons:\n" //-
          						  "- The project " sProjectName " is not tagged for Orchestra Module Id or\n" //-
          						  "- The module /Orchestra Configuration/Orchestra Cache is not created or\n" //-
          						  "- The module " sModuleName " does not exist."
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, sOldUri, sMessage, 64061, false, orcs_StatusDefinition_Status())
				continue
			}
			if (!matches("\\.", sOrchestraModuleId)) {
				Item iModule = itemFromID(sOrchestraModuleId)
				if (!null iModule) {
					AttrDef atDef
					ModName_ modName = module(fullName(iModule))
					bool bCloseModule
					if (!open(modName)) {
						bCloseModule = true
					} else {
						bCloseModule = false
					}
					Module moduleRef = read(fullName(iModule), false, true)
					if (!null moduleRef) {
						atDef = find(moduleRef, CTrekTypeAttribute)
						if (!null atDef) {
							atDef = find(moduleRef, CIEPUID)
							if (!null atDef && atDef.object) {
								bool bIdIsIEPUID
								bIdIsIEPUID = !isValidInt(sObjectId)
								if (!bIdIsIEPUID && bUseIEPUIDAsIdentifier) {
									int intAbsoluteNumber = os_StringToInt(sObjectId)
									Object o = object(intAbsoluteNumber, moduleRef)
									if (length(o.CIEPUID"") > 0) {
										sObjectId = o.CIEPUID
									}
								} else if (bIdIsIEPUID && !bUseIEPUIDAsIdentifier) {
									Object oTmp
									for oTmp in entire(moduleRef) do {
										if (isDeleted(oTmp)) continue
										if ((oTmp.CIEPUID)"" == sObjectId) {
											sObjectId = oTmp.CAbsoluteNumber
											break
										}
									}
								}
							}
						}
					}
					if (bCloseModule && !find(skpListOfModuleToClose, fullName(moduleRef))) {
						put(skpListOfModuleToClose, fullName(moduleRef), moduleRef)
					}
				}
				sOrchestraModuleId = sModuleId
			}
			if (sType == CObject || sType == CRow || sType == CTable) {
				sId = sOrchestraModuleId..CIdentifierSeparatorString..sObjectId
				if (length(sViewName) > 0) {
					ouri_AddParameter(CViewParameterName, sViewName)	
				} 
			} else if (sType == CView) {
				sId = sOrchestraModuleId..CIdentifierSeparatorString..sViewName
			} else if (sType == COrchestraModuleType) {
				sId = sOrchestraModuleId
			} else {
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, sOldUri,
					"The Orchestra Module Identifier can only support the Doors objects, modules or views.",
					64062, false, orcs_StatusDefinition_Status())
				continue
			}
			ouri_SetObjectID(sId)
			sNewUri = ouri_GetUri()
			orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_OK, sOldUri, sNewUri, 0, false, orcs_StatusDefinition_Status())
		}
	}
	Module mod
	for mod in skpListOfModuleToClose do {
		close(mod)
	}
	if (length(StatusFilePath) > 0) {
		Stream statusFile
		statusFile = write StatusFilePath
		statusFile << orcs_StatusDefinition_ToString()
		close statusFile
	}
	TerminateOrchestraUri()
    TerminateOrchestraStatus()
}