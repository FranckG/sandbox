#include <OrchestraConstantes.inc>
#include <OrchestraString.inc>
#include <OMIManagement/OrchestraManageOMI.inc>
#include <OrchestraStatus.inc>
#include <OrchestraUri.inc>

pragma encoding, "UTF-8"

Skip osbfn_SplitRelativePath(string sString) {
	int i
	int iLen
	Buffer bufRetour = create
	Buffer bufString = create
	string strTemp
	Skip returnedList = create

	bufString = sString

	iLen = length(bufString)
	i = contains(bufString, '|')
	if (i > -1 && (i+2 > iLen || bufString[i+1] != '$' || bufString[i+2] != '|'))
		i = -1

	int intIndex = 0

	while (i > -1) {
		bufRetour = bufString[0:(i - 1)]
		bufString = bufString[(i + 3):]
		iLen = length(bufString)
		strTemp = stringOf bufRetour
		put(returnedList, intIndex++, strTemp)
		i = contains(bufString, '|')
		if (i > -1 && (i+2 > iLen || bufString[i+1] != '$' || bufString[i+2] != '|'))
			i = -1
	}
	if (iLen > 0) {
		strTemp = stringOf bufString
		put(returnedList, intIndex++, strTemp)
	}
	delete(bufString)
	delete(bufRetour)
	return returnedList
}

void OrchestraSearchByFullName(
	string UriFilePath, 
	string ResultFilePath, 
	string StatusFilePath, 
	string DxlFilePath, 
	bool blnExpandView, 
	bool blnLinkManagerExport)
{
	string sUri
	string sType
	string sId
	string sModuleId
	string sVoid
	string sObjectId
	string sViewName
	string sOrchestraModuleId
	string sNewUri
	string sRelativePath
	string sRootName
	string sProjectId
	
	InitializeOrchestraUri()
    InitializeOrchestraStatus()
	
	if (ouri_UriFile_Split(UriFilePath)) {	
		for sUri in ouri_skpListOfUri do {
			int i = (int key ouri_skpListOfUri)
				ouri_SetUri(sUri)
				sRootName = ouri_GetRootName()
				if (find(ouri_skpListOfRootNameProjectId, sRootName, sProjectId)) {
	
				sType = ouri_GetObjectType()
				if (sType == CObject || sType == CView) {
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, sUri, "Only the Doors folders, projects or modules can support the 'search' service.", 64069, true, orcs_StatusDefinition_Status())
					continue
				}
				
		    	sId = ouri_GetObjectID()
				omi_TransformIdOMIToIdDoors(sId, sType, sProjectId, sVoid)
		    	sViewName = null
		    	sRelativePath = ouri_Parameter(CRelativePathParameterName)
		    	
		    	Skip skpPath = osbfn_SplitRelativePath(sRelativePath)
		    	
				ouri_ExtractDoorsIdFromOrchestraId(sType, sId, sOrchestraModuleId, sObjectId, sViewName)
				sModuleId = omi_getOMIInCache(sOrchestraModuleId, sProjectId)
				if (sType != CFolder && sType != "" && sOrchestraModuleId == sModuleId) {
					string sMessage = "The module " sOrchestraModuleId " is not found in the Orchestra Cache.\n" //-
									  "Reasons:\n" //-
									  "- The project containing " sOrchestraModuleId " is not tagged for Orchestra Module Id or\n" //-
									  "- The module /Orchestra Configuration/Orchestra Cache is not created or\n" //-
									  "- The module " sOrchestraModuleId " doesn't exist."
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, sUri, sMessage, 64070, true, orcs_StatusDefinition_Status())
					continue
				}
				Item iItem
				if (sType == "") {
					iItem = itemFromID(sProjectId)
				} else {
					iItem = itemFromID(sModuleId)
				}
				
				string sName
				Item iSubItem
				bool bFound = false
				sViewName = null
				for sName in skpPath do {
					bFound = false
					if (type(iItem) == CFolder || type(iItem) == CProject) {
						for iSubItem in folder(iItem) do {
							if (name(iSubItem) == sName) {
								iItem = iSubItem
								bFound = true
								break;	
							}
						}
						if (!bFound)
							break;
					} else if (type(iItem) == CModuleTypeFormal) {
						Module mModule = module(iItem)
						bool bCloseModule = false
						if (null mModule) {
							mModule = read(fullName iItem, false, false)
							bCloseModule = true
						}
						if (null mModule)
							break;
						for sViewName in views mModule do {
							if (sViewName == sName) {
								bFound = true
								break;
							}
						}
						break;
					}
				}
				if (bFound) {
					sType = type(iItem)
					if (sType == CModuleTypeFormal) {
						if (sViewName == null)
							sType = COrchestraModuleType
						else
							sType = CView
					}
					sModuleId = uniqueID(iItem)
					if (sType == CFolder) {
						sId = sModuleId
					} else if (sType == CView) {
						sOrchestraModuleId = omi_getOMIInCache(sModuleId, sProjectId)
						sId = sOrchestraModuleId..CIdentifierSeparatorString..sViewName
					} else if (sType == COrchestraModuleType) {
						sId = omi_getOMIInCache(sModuleId, sProjectId)
					} else {
						orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, sUri, 
						"Only the Doors objects, modules or views are supported by the new Orchestra Module Identifier.", 64071, true, orcs_StatusDefinition_Status())
						continue
					}
					ouri_SetObjectType(sType)
					ouri_SetObjectID(sId)
					ouri_ClearParameters()
					sNewUri = ouri_GetUri()
					OleAutoObj oStatus = orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_OK, sUri, "", 0, false, orcs_StatusDefinition_Status())	
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_OK, sNewUri, "", 0, false, oStatus)
				} else {
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, sUri, "The view " sName " is not found.", 64072, true, orcs_StatusDefinition_Status())	
				}
			} else {
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, sUri, "the project is not found.", 64073, false, orcs_StatusDefinition_Status())	
			}
		}
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
