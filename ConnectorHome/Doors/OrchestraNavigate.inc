#include <OrchestraConstantes.inc>
#include <OrchestraString.inc>
#include <OMIManagement/OrchestraManageOMI.inc>
#include <OrchestraStatus.inc>
#include <OrchestraUri.inc>
//RCM
#include <RCMManagement/OrchestraRCM.inc>
///RCM

OleAutoObj onav_clsOrchestra

void InitializeWindowManagement () {
	onav_clsOrchestra = oleCreateAutoObject("Orchestra.IWindowManagement")
}

void TerminateWindowManagement () {
	oleCloseAutoObject(onav_clsOrchestra)
}

void onav_MakeWindowFront (
    string &strProjectName
){
	OleAutoArgs autoArgs = create
	put(autoArgs, "doors")
	put(autoArgs, strProjectName)
	oleMethod(onav_clsOrchestra, "MakeWindowFront", autoArgs)
	delete(autoArgs)
}

Module onav_ReadModule (string strFullName, bool blnIsVisible) {
	Module moduleRef = null
	int iOpenMode = 2
	string sOpenMode = getenv("DOORSDEFOPENMODE")
	if (!null sOpenMode) {
		if (sOpenMode == "r" || sOpenMode == "READ_ONLY") {
			iOpenMode = 1
		} else if (sOpenMode == "s" || sOpenMode == "READ_WRITE_SHARED") {
			iOpenMode = 3
		}
	}
		
	if (iOpenMode == 1) {
		moduleRef = read(strFullName, blnIsVisible)
	} else if (iOpenMode == 3) {
		moduleRef = share(strFullName, blnIsVisible, true)
	} else {
		if (!isLocked(module strFullName)) {
			moduleRef = edit(strFullName, blnIsVisible, true)
		}
		if (null moduleRef) {
			moduleRef = read(strFullName, blnIsVisible)
		}
	}
	return moduleRef
}

void DisplayLinkManagerStatus (string sLinkFile, Module mod)
{
	AttrDef atDef
	bool bIEPUIDExists
	atDef = find(mod, CTrekTypeAttribute)
	if (!null atDef) {
		atDef = find(mod, CIEPUID)
		if (!null atDef && atDef.object) {
			bIEPUIDExists = true
		}
	} else {
		bIEPUIDExists = false
	}

	string sSeparator = ouri_GetODMVariable(CDisplayLinkStatusDelimiter)""
	
	Skip mLMLinks = createString
	Stat s = create sLinkFile
	string sTmp = ""
	if (!null s) {
		Stream input = read sLinkFile
		string str
		while (true) {
			input >> str
			if (end of input) break
			if (length(str) > 0) {
				int offset
				int len
				if ( findPlainText(str, sSeparator, offset, len, false)) {
				    if ( ! find ( mLMLinks, str[0 : offset - 1], sTmp) ) 
					    put(mLMLinks, str[0 : offset - 1], str[offset + length(sSeparator ) :])
	                else {						
 					    if (  str[offset + length(sSeparator ) :] == sTmp  ) continue
						delete (mLMLinks, str[0 : offset - 1] )
						put (mLMLinks, str[0 : offset - 1], sTmp "\n" str[offset + length(sSeparator ) :] "")
					}
				}
			}
		}
		close input
		delete(s)
	}
	string sValue
	string sTemp
	Object o
	for o in mod do {
		sTemp = o.CAbsoluteNumber ""
		if (find(mLMLinks, sTemp, sValue)) {
			o."HasLinkManagerLinks" = sValue
		} else {
			if (bIEPUIDExists) {
				sTemp = o.CIEPUID ""
				if (find(mLMLinks, sTemp, sValue)) {
					o."HasLinkManagerLinks" = sValue
				} else {
					o."HasLinkManagerLinks" = ""
				}
			} else {
				o."HasLinkManagerLinks" = ""
			}
		}
	}
	delete(mLMLinks)
	deleteFile(sLinkFile)
	refresh(mod)
}

void Navigate (
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
	int intIndex
	string sProjectId
	bool bNoError
	bool bBaselineOfModuleIsCurrent
	bool bUseIEPUIDAsIdentifierForObject

	noError()

	InitializeWindowManagement()
	InitializeOrchestraUri()
    InitializeOrchestraStatus ()

	if (ouri_UriFile_Split(UriFilePath)) {
		if (ouri_GetODMVariable(CUseIEPUIDAsIdentifier)"" == "true") {
			bUseIEPUIDAsIdentifierForObject = true
		} else {
			bUseIEPUIDAsIdentifierForObject = false
		}
		for strUri in ouri_skpListOfUri do {
			intIndex = (int key ouri_skpListOfUri)
			ouri_SetUri(strUri)
			find(ouri_skpListOfRootNameProjectId, ouri_GetRootName(), sProjectId)
	
			strObjectType = ouri_GetObjectType()
			strTitle = ""
			if (strObjectType == CObject || strObjectType == CView || strObjectType == COrchestraModuleType || strObjectType == CFolder || strObjectType == "") {
				strTemp = ouri_GetObjectID()
				omi_TransformIdOMIToIdDoors(strTemp, strObjectType, sProjectId, sOrchestraModuleId)
				strViewName = ouri_Parameter(CViewParameterName)
				ouri_ExtractDoorsIdFromOrchestraId(strObjectType, strTemp, strModuleID, strObjectID, strViewName)
				
				if (strObjectType == "") {
					itemRef = itemFromID(sProjectId)
					if (itemRef != null ) {
						activateURL(getURL(itemRef))
						strTitle = name(itemRef)
						bNoError = true
					} else {
						bNoError = false
						orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri, "Cannot open the Doors Project.", 64063, true, orcs_StatusDefinition_Status())
					}
				} else {
					itemRef = itemFromID(strModuleID)
					if (itemRef != null) {
						strTitle = name(itemRef)
						projectRef = getParentProject(itemRef)
						strResult = openProject(fullName(projectRef))
						if (null strResult) {
							bNoError = true
							if (strObjectType == CFolder) {
								activateURL(getURL(folder(itemRef)))
							} else {
								strBaseline = ouri_findBaselineForAModuleInEnvironment(sOrchestraModuleId)
								if (strBaseline != "current") {
									bBaselineOfModuleIsCurrent = false
									os_ExtractBaselinePartsFromBaselineName(strBaseline, intMajor, intMinor, strSuffix)
									moduleRef = read(fullName(itemRef), false)
									if (moduleRef == null) {
										bNoError = false
										orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri, "Cannot open the Doors Module \"" strTitle "\".", 64064, true, orcs_StatusDefinition_Status())
									} else {
										moduleRef = load(moduleRef, baseline(intMajor,intMinor,strSuffix), true)
										if (moduleRef == null) {
											bNoError = false
											orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri, "Cannot open the Doors Baseline \"" intMajor "." intMinor " (" strSuffix")\" for the Module \"" strTitle "\".", 64079, true, orcs_StatusDefinition_Status())
										}
									}
								} else {
									bBaselineOfModuleIsCurrent = true
									moduleRef = onav_ReadModule(fullName(itemRef), true)
									if (moduleRef == null) {
										bNoError = false
										orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri, "Cannot open the Doors Module \"" strTitle "\".", 64064, true, orcs_StatusDefinition_Status())
									}
								}
								if (moduleRef != null) {
									//RCM
									orcm_isRCMModule(moduleRef)
									///RCM
									if (strViewName != null && currentView(moduleRef) != strViewName) {
										bNoError = load view strViewName
									}
									if (bNoError) {
										if (strViewName != null) {
											string sFilePath = ouri_Parameter("filePath")
											if (sFilePath != null) {
												DisplayLinkManagerStatus(sFilePath, moduleRef)
											}
										}
										if (strObjectType == CObject) {
											Object oToNavigate
											bool bFound = false
											if (bUseIEPUIDAsIdentifierForObject) {
												Object oTmp
												for oTmp in entire(moduleRef) do {
													if ((oTmp.CIEPUID)"" == strObjectID) {
														oToNavigate = oTmp
														bFound = true
														break
													}
												}
											}
											if (!bFound) {
												if (isValidInt(strObjectID)) {
													intAbsoluteNumber = os_StringToInt(strObjectID)
													oToNavigate = object(intAbsoluteNumber, moduleRef)
												} else {
													bNoError = false
												}
											}
											if (bNoError) {
												//RCM
												oToNavigate = orcm_ObjectInReference(oToNavigate, bBaselineOfModuleIsCurrent)
												///RCM
												if (!null oToNavigate) {
													current = oToNavigate
													scroll to current Object
												} else {
													bNoError = false
												}
											}
											if (!bNoError) {
												orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri, "Cannot find the Doors object.", 64065, true, orcs_StatusDefinition_Status())
											}
										}
										bringToFront(moduleRef)
									} else {
										orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri, "Cannot open the Doors View: " strViewName, 64066, true, orcs_StatusDefinition_Status())
									}
								}
							}
						} else {
							bNoError = false
							orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri, "Cannot open the Doors Project.", 64067, true, orcs_StatusDefinition_Status())
						}
					} else {
						bNoError = false
						orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, strUri, "Cannot find the Doors Module.", 64068, true, orcs_StatusDefinition_Status())
					}
				}
				if (bNoError) {
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_OK, strUri, "", 0, false, orcs_StatusDefinition_Status())
				}
			}
			onav_MakeWindowFront(strTitle)
		}
	}
	if (length(StatusFilePath) > 0) {
		Stream statusFile
		statusFile = write StatusFilePath
		statusFile << orcs_StatusDefinition_ToString()
		close statusFile
	}
	
	TerminateWindowManagement()
	TerminateOrchestraUri()
    TerminateOrchestraStatus()
	deleteFile DxlFilePath
}
