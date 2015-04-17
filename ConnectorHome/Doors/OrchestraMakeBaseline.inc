pragma encoding, "UTF-8"

#include <OrchestraConstantes.inc>
#include <OrchestraGef.inc>
#include <OrchestraStatus.inc>
#include <OMIManagement/OrchestraManageOMI.inc>
#include <OrchestraString.inc>
#include <OrchestraUri.inc>

void OrchestraMakeBaseline (
	string UriFilePath,
	string ResultFilePath,
	string StatusFilePath,
	string DxlFilePath,
	bool blnExpandView,
	bool blnLinkManagerExport)
{
	string sUri
	string sTemp
	string sBaselineSuffix
	string sBaselineComment
	string sFolder
	string sBaselineSetDefinition
	string sBaselineSet
	string sProjectId
	string sOkMessage
	bool bBaselineMajor
	Folder f
	Item itemRef
	BaselineSetDefinition bsd
	BaselineSet bs
	bool bFound
	
	InitializeOrchestraUri()
    InitializeOrchestraStatus()
	
	if (ouri_UriFile_Split(UriFilePath)) {
		for sUri in ouri_skpListOfUri do {
			ouri_SetUri(sUri)
			
			sProjectId = ouri_Parameter("projectID")
	    	sTemp = ouri_GetObjectID()
	    	if (ouri_ExistParameter("major") && ouri_ExistParameter("suffix") && ouri_ExistParameter("comment")){
	    		if (ouri_Parameter("major") == "true") {
	    			bBaselineMajor = true
	    		} else {
	    			bBaselineMajor = false
	    		}
	    		sBaselineSuffix = ouri_Parameter("suffix")
	    		sBaselineComment = ouri_Parameter("comment")
				if (sBaselineSuffix == null) {
					sBaselineSuffix = ""
				}
				if (sBaselineComment == null) {
					sBaselineComment = ""
				}
	    		sFolder = os_StringUntilSeparator(sTemp, CIdentifierSeparatorChar, ' ')
	    		sBaselineSetDefinition = os_StringUntilSeparator(sTemp, CIdentifierSeparatorChar, ' ')
	    		sBaselineSet = sTemp
	    		if (sFolder[0] == '/') {
	    			f = folder(sFolder)
	    		} else {
					itemRef = itemFromID(sFolder)
					if (!null itemRef) {
						f = folder(itemRef)
					}
	    		}
	    		if (!null f) {
	    			bFound = false
	    			for bsd in f do {
	    				if (name(bsd) == sBaselineSetDefinition) {
	    					bFound = true
	    					break
	    				}	
	    			}
	    			if (bFound) {
	    				if (sBaselineSet == "current" ) {
	    					sTemp = create(bsd, bBaselineMajor, sBaselineSuffix, sBaselineComment, bs)
	    					if (sTemp != null) {
								orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "",
										"An error occurred during the creation of the Baseline Set.\nError :\n" sTemp, 64054, 
										true, orcs_StatusDefinition_Status())
								break
	    					}
	    					sBaselineSet = versionID(bs)
							sOkMessage = "BaselineSet " sBaselineSet " in BaselineSet Definition " name(bsd) " from folder " fullName(f) " is created"
	    				} else {
							sOkMessage = "BaselineSet " sBaselineSet " in BaselineSet Definition " name(bsd) " from folder " fullName(f) " is already set"
	    					bFound = false
	    					for bs in bsd do {
	    						if (versionID(bs) == sBaselineSet) {
	    							bFound = true
	    							break
	    						}
	    					}
	    					if (!bFound) {
	    						orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "",
										"The Baseline Set defined by " sBaselineSet " does not exist.", 64055, 
										true, orcs_StatusDefinition_Status())
								break
	    					}
	    					if (!isOpen(bs)) {
	    						orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, "",
										"The Baseline Set " sBaselineSet " is closed.", 64056, 
										true, orcs_StatusDefinition_Status())
								bFound = false
	    					}
	    				}
						if (bFound) {
							ModName_ modRef
							Skip modList = create
							int skipIndex = 0
							Buffer subMessage = create
							for modRef in bsd do {
								if (!isBaselinePresent(bs, modRef)) {
									put(modList, skipIndex++, modRef)
								}
							}
							if (skipIndex > 0) {
								sTemp = addBaselines(modList, bs)
								if (sTemp != null) {
									orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "",
											"An error occurred while adding the modules in the Baseline Set.\nError :\n" sTemp, 64057, 
											true, orcs_StatusDefinition_Status())
									break
								}
								ModuleVersion mv
								for mv in bs do {
									for modRef in modList do {
										string smodRef = fullName(modRef)
										string smv = fullName(mv)
										if (smodRef == smv) {
											if (length(subMessage) == 0) {
												subMessage = name(mv) " (" versionString(mv) ")"
											} else {
											subMessage += ", " name(mv) " (" versionString(mv) ")"
											}
										break
										}
									}
								}
								sOkMessage = sOkMessage " and the following modules " 
								sOkMessage = sOkMessage stringOf (subMessage) 
								sOkMessage = sOkMessage " are added to the baselineSet" 
							}
							delete(subMessage)
						}
	    			} else {
						orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "",
								"The Baseline Set Definition defined by " sBaselineSetDefinition " does not exist.", 64058, 
								true, orcs_StatusDefinition_Status())
						break
					}
	    		} else {
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "",
							"The folder defined by " sFolder " does not exist.", 64059,
							true, orcs_StatusDefinition_Status())
					break
	    		}
				ouri_ClearParameters()
				ouri_AddParameter("projectID", sProjectId)
				sTemp = sFolder CIdentifierSeparatorString sBaselineSetDefinition CIdentifierSeparatorString sBaselineSet
				ouri_SetObjectID(sTemp)
				sUri = ouri_GetUri()
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_OK, sUri, sOkMessage, 0, false, orcs_StatusDefinition_Status())
	    	} else {
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "",
						"Missing parameter(s)", 64060, 
						true, orcs_StatusDefinition_Status())
				break
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

