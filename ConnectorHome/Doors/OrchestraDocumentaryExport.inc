#include <OrchestraConstantes.inc>
#include <OrchestraString.inc>
#include <OMIManagement/OrchestraManageOMI.inc>
#include <OrchestraStatus.inc>
#include <OrchestraUri.inc>
#include <OrchestraGef.inc>
#include <OrchestraArtifactsVersion.inc>
#include <OrchestraInstall.inc>
#include <Environment/OrchestraDoorsEnvironment.inc>
//RCM
#include <RCMManagement/OrchestraRCM.inc>
///RCM

pragma runLim, 0

Skip ode_mAddedProperties = createString
Skip ode_mLinkManagerProperties = createString
Skip ode_mLinkModule = createString
Skip ode_mMozartObjectFullName = create
int ode_mintMozartObjectFullNameCount
Skip ode_mLinkManagerLabelDefinition = create
int ode_mintLinkManagerLabelDefinitionCount
string ode_mstrPicturesDirectory
string ode_mstrPicturesDirectoryRelative
string ode_mstrOrchestraFullNameAttributeForThisModule
string ode_mstrConfDir_Env
Array ode_maProperties = create(1,4)
int ode_mintIndexProperties
Array ode_maPropertiesWithURI = create(1,4)
int ode_mintIndexPropertiesWithURI
Array ode_maLinks = create(1,3)
int ode_mintIndexLinks
Buffer ode_mbufStringToHash
string ode_mstrModuleFullname
string ode_msModuleName
string ode_mstrModuleVersion
string ode_sOrchestraModuleId
bool ode_bIsATrekModule = false
bool ode_bIEPUIDExists = false
bool ode_bLinkManagerExport = false
string ode_mstrRootNameOfExportedArtefact = ""
string ode_mstrProjectIdOfExportedArtefact = ""
Regexp ode_regLine = regexp "([^\";]+)|\"([^;]+)\""
OleAutoObj ode_parentStatus
OleAutoObj ode_rootStatus
string ode_sCurrentArtefactUri
bool ode_bWrittenStatusForCurrentArtefact = false
bool ode_bBaselineOfModuleIsCurrent
Skip ode_LinksToOthersObjects
bool ode_bUseIEPUIDAsIdentifierForObject = false

void InitializeArraysForObject() {
	ode_maProperties = create(1,4)
	ode_maPropertiesWithURI = create(1,4)
	ode_maLinks = create(1,3)
	ode_mintIndexProperties = 0
	ode_mintIndexPropertiesWithURI = 0
	ode_mintIndexLinks = 0
	setempty(ode_mbufStringToHash)
	ode_LinksToOthersObjects = createString
}

void DeleteArraysForObject() {
	if (!null ode_maProperties) {
		delete(ode_maProperties)
	}
	if (!null ode_maPropertiesWithURI) {
		delete(ode_maPropertiesWithURI)
	}
	if (!null ode_maLinks) {
		delete(ode_maLinks)
	}
	if (!null ode_LinksToOthersObjects) {
		delete(ode_LinksToOthersObjects)
	}
}

void IsATrekModule(Module moduleRef) {
	AttrDef atDef

	atDef = find(moduleRef, CTrekTypeAttribute)
	if (!null atDef) {
		ode_bIsATrekModule = true
		atDef = find(moduleRef, CIEPUID)
		if (!null atDef && atDef.object) {
			ode_bIEPUIDExists = true
		}
	} else {
		ode_bIsATrekModule = false
		ode_bIEPUIDExists = false
	}
}

void ReadAddedProperties() {
	string sAddedProperties = ouri_GetODMVariable(CAddedProperties)
	Regexp regProperty = regexp "[^;]+"
	Buffer bTmp = create
	while (regProperty sAddedProperties) {
		bTmp = sAddedProperties[match 0]
		if (length(bTmp) > 0) {
			put(ode_mAddedProperties, stringOf(bTmp), stringOf(bTmp))
		}
		sAddedProperties = sAddedProperties[end 0 + 1:]
	}
	delete(bTmp)
}

void ReadLinkManagerProperties() {
	string strFileName = ode_mstrConfDir_Env..CConnectorDoorsConfigRelativePath..CAnteSlash.."LinkManagerExportedAttributes.txt"

	Stat s = create strFileName
	if (!null s) {
		Stream input = read strFileName
		string str
		while (true) {
			input >> str
			if (end of input) break
			if (length(str) > 0) {
				put(ode_mLinkManagerProperties, str, str)
			}
		}
		close input
		delete(s)
	}
}

void ReadLinkModuleFile () {
	string strFileName = ode_mstrConfDir_Env..CConnectorDoorsConfigRelativePath..CAnteSlash..CLinkModuleFile

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
				put(ode_mLinkModule, str, strAttributeForLabel)
			}
		}
		close input
		delete(s)
	}
}

void VerifyIfAddedPropertiesExists (Module moduleRef) {
	AttrDef atDef

	string str

	for str in ode_mAddedProperties do {
		atDef = find(moduleRef, str)
		if (null atDef) {
			delete(ode_mAddedProperties, str)
		}
	}
}

void VerifyIfLinkManagerPropertiesExists (Module moduleRef) {
	AttrDef atDef

	string str

	for str in ode_mLinkManagerProperties do {
		atDef = find(moduleRef, str)
		if (null atDef) {
			delete(ode_mLinkManagerProperties, str)
		}
	}
}

void ReadLabelFile(Buffer bufFilePath, Module moduleRef, Skip &skpList, int &intCount) {
	int intOrder
	Stat s = create tempStringOf bufFilePath

	delete(skpList)
	skpList = create
	intCount = 0
	intOrder = 0
	if (!null s) {
		Stream input = read tempStringOf bufFilePath
		Buffer buf = create
		Buffer subbuf = create
		Buffer bufToAdd = create
		AttrDef ad
		while (true) {
			buf = create
			input >= buf
			if (end of input) break
			if (buf[0:1] == "//") continue
			setempty(bufToAdd)
			while (!null buf && ode_regLine buf) {
				subbuf = buf[match 0]
				if (tempStringOf(subbuf) != CAttributeDefinedInModuleProperties && subbuf[0] != '"') {
					ad = find(moduleRef, tempStringOf(subbuf))
					if (!null ad) {
						if (ad.object) {
							if (length(bufToAdd) > 0) {
								bufToAdd += ";"
							}
							bufToAdd += subbuf
						}
					}
				} else {
					if (length(bufToAdd) > 0) {
						bufToAdd += ";"
					}
					bufToAdd += subbuf
				}
				subbuf = buf[end 0 + 1:]
				buf = tempStringOf(subbuf)
			} 
			intOrder++
			if (length(bufToAdd) > 0)
				put(skpList, intOrder, stringOf(bufToAdd))
		}
		delete(buf)
		delete(subbuf)
		delete(bufToAdd)
		intCount = intOrder
		close input
		delete(s)
	}
}

void ReadMozartAndLinkManagerLabel (Module moduleRef) {
	Buffer bufFileName = create
	bufFileName = ode_mstrConfDir_Env..CConnectorDoorsConfigRelativePath..CAnteSlash..CMozartLabelFile
	ReadLabelFile(bufFileName, moduleRef, ode_mMozartObjectFullName, ode_mintMozartObjectFullNameCount)
	setempty(bufFileName)
	bufFileName = ode_mstrConfDir_Env..CConnectorDoorsConfigRelativePath..CAnteSlash..CLinkManagerLabelFile
	ReadLabelFile(bufFileName, moduleRef, ode_mLinkManagerLabelDefinition, ode_mintLinkManagerLabelDefinitionCount)
	delete(bufFileName)
}

void DetermineMozartFullNameAttributeForThisModule (Module moduleRef) {
	Module moduleCourant = current
	AttrDef ad
	string strAttributeName

	current = moduleRef

	ode_mstrOrchestraFullNameAttributeForThisModule = ""

	if (exists attribute CModuleAttributeNameForMozartFullName) {
		ad = find(moduleRef, CModuleAttributeNameForMozartFullName)
		if (ad.module) {
			strAttributeName = moduleRef.CModuleAttributeNameForMozartFullName
			if (exists attribute strAttributeName) {
				ad = find(moduleRef, strAttributeName)
				if (ad.object) {
					ode_mstrOrchestraFullNameAttributeForThisModule = strAttributeName
				}
			}
		}
	}
	current = moduleCourant
}

void ObjectFullName (Object o, string &sRetour) {
	if (ode_mintMozartObjectFullNameCount > 0) {
		Buffer bSubstr = create
		Buffer bFullName = create
		Buffer bTmp = create
		int intIndex
		bool blnAttributesNotEmpty = false
		for intIndex in 1:ode_mintMozartObjectFullNameCount do {
			sRetour = ""
			if (find(ode_mMozartObjectFullName, intIndex, sRetour)) {
				setempty(bFullName)
				blnAttributesNotEmpty = false
				while (!null sRetour && ode_regLine sRetour) {
					setempty(bSubstr)
					bSubstr = sRetour[match 0]
					if (bSubstr[0] == '"') {
						bFullName += sRetour[match 2]
					} else {
						if (tempStringOf(bSubstr) == CAttributeDefinedInModuleProperties) {
							setempty(bSubstr)
							bSubstr = ode_mstrOrchestraFullNameAttributeForThisModule
						}
						if (length(bSubstr) > 0) {
							setempty(bTmp)
							bTmp = o.(tempStringOf bSubstr)
							if (length(bTmp) > 0) {
								bFullName += bTmp
								blnAttributesNotEmpty = true
							}
						}
					}
					sRetour = sRetour[end 0 + 1:]
				} 
				if (blnAttributesNotEmpty) {
					sRetour = stringOf(bFullName)
					break
				}
			}
		}
		delete(bFullName)
		delete(bTmp)
		delete(bSubstr)
	}
}

void ObjectTypeTrek (Object o, string &sType) {
	if (ode_bIsATrekModule) {
		sType = o.CTrekTypeAttribute
		if (null sType || length(sType) == 0) {
			sType = CObject
		} else {
			sType = lower(sType)
		}
	} else {
		sType = CObject
	}
}

void ExportAddedProperties (Object o) {
	string str
	string strPropertyContent

	for str in ode_mAddedProperties do {
		strPropertyContent = o.str
		put(ode_maProperties, str, ode_mintIndexProperties, 0)
		put(ode_maProperties, CTypeString, ode_mintIndexProperties, 1)
		put(ode_maProperties, strPropertyContent, ode_mintIndexProperties, 2)
		put(ode_maProperties, CMozartInternal, ode_mintIndexProperties++, 3)
		ode_mbufStringToHash += str CTypeString strPropertyContent
	}
}

void ExportLinkManagerProperties (Object o) {
	string str
	string strPropertyContent

	for str in ode_mLinkManagerProperties do {
		strPropertyContent = o.str
		put(ode_maProperties, str, ode_mintIndexProperties, 0)
		put(ode_maProperties, CTypeString, ode_mintIndexProperties, 1)
		put(ode_maProperties, strPropertyContent, ode_mintIndexProperties, 2)
		put(ode_maProperties, "", ode_mintIndexProperties++, 3)
	}
}

void ReplaceModuleAlias (Object o, string &strRTF) {
	Module moduleRef = module(o)
	AttrDef ad
	int offset
	int len
	Buffer b = create
	b = strRTF
	
	if (contains(b ,"<<", 0) > -1) {
		string strName
		string strContent
		string strSearchString
		for ad in moduleRef do {
			if (ad.module) {
				strName = ad.name
				if (contains(b,strName,0) > -1) {
					strContent = moduleRef.strName
					strSearchString = "<<"..strName..">>"
					while (findRichText(strRTF, strSearchString, offset, len, true)) {
						strRTF = replaceRichText(strRTF, offset, len, strContent)
					}
				}
			}
		}
	}
	delete(b)
}

void ExportLMLabel(Object o) {
	Buffer bTmp = create
	Buffer bFullName = create
	int intIndex
	bool blnAttributesNotEmpty = false

	if (ode_mintLinkManagerLabelDefinitionCount > 0) {
		Buffer bSubstr = create
		string str
		for intIndex in 1:ode_mintLinkManagerLabelDefinitionCount do {
			str = ""
			if (find(ode_mLinkManagerLabelDefinition, intIndex, str)) {
				setempty(bFullName)
				blnAttributesNotEmpty = false
				while (!null str && ode_regLine str) {
					setempty(bSubstr)
					bSubstr = str[match 0]
					if (bSubstr[0] == '"') {
						bFullName += str[match 2]
					} else {
						bTmp = o.(tempStringOf bSubstr)
						if (length(bTmp) > 0) {
							bFullName += bTmp
							blnAttributesNotEmpty = true
						} else {
							blnAttributesNotEmpty = false
							break
						}
					}
					str = str[end 0 + 1:]
				} 
				if (blnAttributesNotEmpty)
					break
			}
		}
		delete(bSubstr)
	}	
	
	put(ode_maProperties, "LMLabel", ode_mintIndexProperties, 0)
	put(ode_maProperties, "String", ode_mintIndexProperties, 1)
	put(ode_maProperties, stringOf(bFullName), ode_mintIndexProperties, 2)
	put(ode_maProperties, CMozartInternal, ode_mintIndexProperties++, 3)
	if (ode_bLinkManagerExport) {
		setempty(bTmp)
		if (ode_bIEPUIDExists && length((o.CIEPUID)"") > 0) {
			bTmp = (o.CIEPUID)""
		} else {
			bTmp = uniqueID(module(o))
			bTmp += "."
			bTmp += (o.CAbsoluteNumber)""
		}
		put(ode_maProperties, "toolId", ode_mintIndexProperties, 0)
		put(ode_maProperties, "String", ode_mintIndexProperties, 1)
		put(ode_maProperties, stringOf(bTmp), ode_mintIndexProperties, 2)
		put(ode_maProperties, CMozartInternal, ode_mintIndexProperties++, 3)
	}
	delete(bTmp)
	delete(bFullName)
}

void ExportRTF (string strPropertyName, Object o, bool blnWriteDirectly) {
	string strRTFPropertyContent
	string strRTFFile
	string strRTFFilePath
	string strObjectID
	string strHashFile = ""
	string strTemp

	if (getPictFormat(o) != "") {
		if (!oleIsObject(o)) {
			string strPictureName = o.CAbsoluteNumberAttribute
			string strPictureFilePath
			strPictureFilePath = ode_mstrPicturesDirectory CAnteSlash strPictureName ".png"
			string s = exportPicture(o, strPictureFilePath, formatPNG)
			if (blnWriteDirectly) {
				strPictureFilePath = ode_mstrPicturesDirectoryRelative CAnteSlash strPictureName ".png"
				Gef_WriteProperty(CNativeDoc, CPng, strPictureFilePath, "")
			} else {
				oav_getFileHash(strPictureFilePath, strHashFile)
				ode_mbufStringToHash += CNativeDoc CPng strHashFile
				strPictureFilePath = ode_mstrPicturesDirectoryRelative CAnteSlash strPictureName ".png"
				put(ode_maProperties, CNativeDoc, ode_mintIndexProperties, 0)
				put(ode_maProperties, CPng, ode_mintIndexProperties, 1)
				put(ode_maProperties, strPictureFilePath, ode_mintIndexProperties, 2)
				put(ode_maProperties, "", ode_mintIndexProperties++, 3)
			}
		}
	}
	strTemp = o.CObjectText
	if (strTemp != "") {
		strRTFPropertyContent = richTextWithOle(o.CObjectText)
		if (strRTFPropertyContent[0:5] != "{\\rtf1") {
			strRTFPropertyContent = exportRTFString(strTemp)
			strRTFPropertyContent = "{\\rtf1{"..strRTFPropertyContent.."}}"
		}
		if (!oleIsObject(o)) {
			ReplaceModuleAlias(o, strRTFPropertyContent)
		}
		strObjectID = o.CAbsoluteNumber
		strRTFFile = ode_sOrchestraModuleId..CIdentifierSeparatorString..strObjectID..".rtf"
		strRTFFile = goodFileName(strRTFFile)
		strRTFFilePath = ode_mstrPicturesDirectory..CAnteSlash..strRTFFile
		Stream output = write binary strRTFFilePath
		output << strRTFPropertyContent
		flush output
		close output
		if (blnWriteDirectly) {
			strRTFFilePath = ode_mstrPicturesDirectoryRelative..CAnteSlash..strRTFFile
			Gef_WriteTextualDescription(strPropertyName, CRtf, strRTFFilePath, "", "")
		} else {
			oav_getFileHash(strRTFFilePath, strHashFile)
			strRTFFilePath = ode_mstrPicturesDirectoryRelative..CAnteSlash..strRTFFile
			put(ode_maProperties, strPropertyName, ode_mintIndexProperties, 0)
			put(ode_maProperties, CRtf, ode_mintIndexProperties, 1)
			put(ode_maProperties, "", ode_mintIndexProperties, 2)
			put(ode_maProperties, "Description", ode_mintIndexProperties++, 3)
			put(ode_maPropertiesWithURI, strPropertyName, ode_mintIndexPropertiesWithURI, 0)
			put(ode_maPropertiesWithURI, CRtf, ode_mintIndexPropertiesWithURI, 1)
			put(ode_maPropertiesWithURI, "", ode_mintIndexPropertiesWithURI, 2)
			put(ode_maPropertiesWithURI, strRTFFilePath, ode_mintIndexPropertiesWithURI++, 3)
			ode_mbufStringToHash += strPropertyName CRtf strHashFile
		}
	}
}

void ExportProperties (Object o, bool blnViewFilter){
	string strPropertyName
	string strPropertyType
	string strPropertyContent
	string strTemp

	ExportLMLabel(o)
	if (ode_bLinkManagerExport) {
		ExportLinkManagerProperties(o)
	} else {
		if (blnViewFilter) {
			Column c

			ExportAddedProperties(o)
			for c in current Module do {
				strPropertyName = (title c)
				if (!find(ode_mAddedProperties, strPropertyName)) {
					if (main c) {
						strTemp = o.CObjectHeading
						strPropertyContent = o.CObjectText
						if ((strTemp != "") && (strPropertyContent != "")) {
							strTemp = strTemp "\n"
							strPropertyContent = strTemp strPropertyContent
						}
						if (length(strPropertyContent) == 0) {
							strPropertyType = CTypeString
							put(ode_maProperties, strPropertyName, ode_mintIndexProperties, 0)
							put(ode_maProperties, strPropertyType, ode_mintIndexProperties, 1)
							put(ode_maProperties, strTemp, ode_mintIndexProperties, 2)
							put(ode_maProperties, "", ode_mintIndexProperties++, 3)
							ode_mbufStringToHash += strPropertyName strPropertyType strTemp
						} else {
							ExportRTF(strPropertyName, o, false)
						}
					} else {
						strPropertyContent = text(c, o)
						strPropertyType = CTypeString
						put(ode_maProperties, strPropertyName, ode_mintIndexProperties, 0)
						put(ode_maProperties, strPropertyType, ode_mintIndexProperties, 1)
						put(ode_maProperties, strPropertyContent, ode_mintIndexProperties, 2)
						put(ode_maProperties, "", ode_mintIndexProperties++, 3)
						ode_mbufStringToHash += strPropertyName strPropertyType strPropertyContent
					}
				}
			}
		} else {
			AttrDef ad
			for ad in current Module do {
				if (isVisibleAttribute(ad)) {
					strPropertyName = ad.name
					if (strPropertyName == CObjectText)
						ExportRTF(strPropertyName, o, false)
					else {
						strPropertyType = ad.typeName
						if (strPropertyType == CTypeText)
							strPropertyType = CTypeString
						strPropertyContent = o.strPropertyName
						put(ode_maProperties, strPropertyName, ode_mintIndexProperties, 0)
						put(ode_maProperties, strPropertyType, ode_mintIndexProperties, 1)
						put(ode_maProperties, strPropertyContent, ode_mintIndexProperties, 2)
						put(ode_maProperties, "", ode_mintIndexProperties++, 3)
						ode_mbufStringToHash += strPropertyName strPropertyType strPropertyContent
					}
				}
			}
		}
	}
}

void ExportInternalsLinks (Object o) {
	Module moduleCourant = current
	Buffer bufObjectName = create
	Buffer bufObjectID = create
	Link l
	Object otherObject
	string strAttributeForLabel
	string strModuleID
	ModName_ targetMod
	string strTargetProjectID
	Project targetProjectRef
	string strTargetProjectRootName
	AttrDef ad
	Module moduleRef
	Item itemRef
	ModuleVersion moduleVer
	Module mm
	bool bIEPUIDExistsForTargetModule

	string strSaveParameter = ""
	bool blnExistParameterViewName = ouri_ExistParameter(CViewParameterName)
	if (blnExistParameterViewName) {
		strSaveParameter = ouri_Parameter(CViewParameterName)
		ouri_ClearParameters()
	}
	
	for l in all (o) -> "*" do {
		mm = module(l)
		if (!find(ode_mLinkModule, name(mm), strAttributeForLabel)) continue
		moduleVer = targetVersion(l)
		targetMod = module(moduleVer)
		if (targetMod == null) continue
		if (module fullName(moduleVer)) {
			if (!open(targetMod)) {
				moduleRef = load(moduleVer, false)
				otherObject = target(l)
				if (null otherObject) continue
			} else {
				otherObject = target(l)
				if (null otherObject) continue
				moduleRef = module(otherObject)
			}
		}
		if (isDeleted otherObject) continue
		ad = find(moduleRef, strAttributeForLabel)
		if (null ad || !ad.object) {
			strAttributeForLabel = CObjectText
		}
		ad = find(moduleRef, CIEPUID)
		if (!null ad && ad.object) {
			bIEPUIDExistsForTargetModule = true
		} else {
			bIEPUIDExistsForTargetModule = false
		}
		targetProjectRef = getParentProject(targetMod)
		strTargetProjectID = uniqueID(targetProjectRef)
		if (strTargetProjectID != ode_mstrProjectIdOfExportedArtefact) {
			if (!find(ouri_skpListOfProjectIdRootName, strTargetProjectID, strTargetProjectRootName)) {
				odenv_FindRootNameForAProjectId(strTargetProjectID, strTargetProjectRootName)
				if (length(strTargetProjectRootName) > 0) {
					put(ouri_skpListOfProjectIdRootName, strTargetProjectID, strTargetProjectRootName)
					put(ouri_skpListOfRootNameProjectId, strTargetProjectRootName, strTargetProjectID)
				}
			}
			if (length(strTargetProjectRootName) < 1) continue
			ouri_SetRootName(strTargetProjectRootName)
		} else {
			ouri_SetRootName(ode_mstrRootNameOfExportedArtefact)
		}
		strModuleID = uniqueID(targetMod)
		omi_getOMIForAModule(itemFromID(strModuleID), strModuleID)
		if (strModuleID != null) {
			//RCM
			Object oTmp = orcm_FirstObject(otherObject, moduleRef)
			///RCM
			bufObjectID = strModuleID..CIdentifierSeparatorString
			if (bIEPUIDExistsForTargetModule && ode_bUseIEPUIDAsIdentifierForObject && length((oTmp.CIEPUID)"") > 0) {
				bufObjectID += (oTmp.CIEPUID)""
			} else {
				bufObjectID += (oTmp.CAbsoluteNumberAttribute)""
			}
			bufObjectName = (otherObject.strAttributeForLabel)""
			ouri_SetObjectID(stringOf(bufObjectID))
			ouri_SetObjectType(CObject)
			if (put(ode_LinksToOthersObjects, ouri_GetUri() name(mm), "")) {
				put(ode_maLinks, ouri_GetUri(), ode_mintIndexLinks, 0)
				put(ode_maLinks, name(mm), ode_mintIndexLinks, 1)
				put(ode_maLinks, stringOf(bufObjectName), ode_mintIndexLinks++, 2)
				ode_mbufStringToHash += bufObjectID
				ode_mbufStringToHash += name(mm)
				ode_mbufStringToHash += bufObjectName
			}
		}
	}
	delete(bufObjectName)
	delete(bufObjectID)
	ouri_SetRootName(ode_mstrRootNameOfExportedArtefact)
	if (blnExistParameterViewName)
		ouri_AddParameter(CViewParameterName, strSaveParameter)
	current = moduleCourant
}

void WriteAllPropertiesAndLinks () {
	int intIndex
	string strA = ""
	string strB = ""
	string strC = ""
	string strD = ""
	string strTemp

	if (ode_mintIndexPropertiesWithURI > 0) {
		ode_mintIndexPropertiesWithURI--
		for intIndex in 0:ode_mintIndexPropertiesWithURI do {
			strA = (string get(ode_maPropertiesWithURI, intIndex, 0))//name
			strB = (string get(ode_maPropertiesWithURI, intIndex, 1))//type
			strC = (string get(ode_maPropertiesWithURI, intIndex, 2))//content
			strD = (string get(ode_maPropertiesWithURI, intIndex, 3))//url
			if (null strA)
				strA = ""
			if (null strB)
				strB = ""
			if (null strC)
				strC = ""
			if (null strD)
				strD = ""
			Gef_WriteTextualDescription(strA, strB, strD, "", strC)
		}
	}
	if (ode_mintIndexProperties > 0) {
		ode_mintIndexProperties--
		for intIndex in 0:ode_mintIndexProperties do {
			strA = (string get(ode_maProperties, intIndex, 0))
			strB = (string get(ode_maProperties, intIndex, 1))
			strC = (string get(ode_maProperties, intIndex, 2))
			strD = (string get(ode_maProperties, intIndex, 3))
			if (null strA)
				strA = ""
			if (null strB)
				strB = ""
			if (null strC)
				strC = ""
			if (ode_bLinkManagerExport)
				strD = ""
			strTemp = Gef_WriteProperty(strA, strB, strC, strD)
			if (length(strTemp) > 0) {
				if (!ode_bWrittenStatusForCurrentArtefact) {
					ode_parentStatus = orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, ode_sCurrentArtefactUri, "", 0, false, ode_rootStatus)
				}
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, "", strTemp "On property: " strA, 64031, true, ode_parentStatus)
			}
		}
	}
	if (ode_mintIndexLinks > 0) {
		ode_mintIndexLinks--
		for intIndex in 0:ode_mintIndexLinks do {
			strA = (string get(ode_maLinks, intIndex, 0))//ID
			strB = (string get(ode_maLinks, intIndex, 1))//type
			strC = (string get(ode_maLinks, intIndex, 2))//name
			if (null strA)
				strA = ""
			if (null strB)
				strB = ""
			if (null strC)
				strC = ""
			strTemp = Gef_WriteInternalReference(strA, strB, true, strC, strC)
			if (length(strTemp) > 0) {
				if (!ode_bWrittenStatusForCurrentArtefact) {
					ode_parentStatus = orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, ode_sCurrentArtefactUri, "", 0, false, ode_rootStatus)
				}
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, "", strTemp, 64032, true, ode_parentStatus)
			}
		}
	}
	DeleteArraysForObject()
}

void ExportTable (Object o, bool blnViewFilter) {
	Object oRow
	Object oCell
	string strObjectName
	string strObjectID
	string sTableId
	string strTemp
	string strLabel
	string strPropertyContent
	int intRow = 0
	int intCol
	int intColMax
	int intColWidth
	string strColWidth
	string strConvert
	bool blnVisible

	if (o == null) {
		return
	}
	if (ode_bIEPUIDExists && ode_bUseIEPUIDAsIdentifierForObject && length((o.CIEPUID)"") > 0) {
		sTableId = (o.CIEPUID)""
	} else {
		sTableId = o.CAbsoluteNumber
	}
	ObjectFullName(o, strLabel)
	strTemp = Gef_FormatFullNamePart(strLabel)
	strObjectName = ode_msModuleName.."."..strTemp

	sTableId = ode_sOrchestraModuleId..CIdentifierSeparatorString..sTableId
	ouri_SetObjectType(CTable)
	ouri_SetObjectID(sTableId)
	ode_sCurrentArtefactUri = ouri_GetUri()

	strTemp = Gef_WriteStartArtefact(ode_sCurrentArtefactUri, strObjectName, CTable, strLabel, "", ode_mstrModuleFullname, ode_mstrModuleVersion, false)
	if (length(strTemp) > 0) {
		ode_bWrittenStatusForCurrentArtefact = true
		ode_parentStatus = orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, ode_sCurrentArtefactUri, strTemp, 64033, true, ode_rootStatus)
	} else {
		ode_bWrittenStatusForCurrentArtefact = false
		ode_parentStatus = null
	}
	
	intColMax = -1
	for oRow in table o do {
		if (isDeleted(oRow)) continue
		intCol = 0
		for oCell in row oRow do {
			if (isDeleted(oCell)) continue
			if (intCol > intColMax) {
				strConvert = "Col" intCol""
				intColWidth = getCellWidth(oCell)
				strColWidth = intColWidth ""
				Gef_WriteProperty(strConvert, "Column", strColWidth, "")
				intColMax = intCol
			}
			intCol++
		}

	}
	Gef_WriteProperty("LMLabel", "String", "Table", "")
	Buffer bTmp = create
	if (ode_bIEPUIDExists && length((o.CIEPUID)"") > 0) {
		bTmp = (o.CIEPUID)""
	} else {
		bTmp = uniqueID(module(o))
		bTmp += "."
		bTmp += (o.CAbsoluteNumber)""
	}
	Gef_WriteProperty("toolId", "String", stringOf(bTmp), "")
	delete(bTmp)
	for oRow in table o do {
		if (isDeleted(oRow)) continue
		strObjectID = ode_sOrchestraModuleId CIdentifierSeparatorString sTableId CIdentifierSeparatorString intRow ""
		ouri_SetObjectType(CRow)
		ouri_SetObjectID(strObjectID)
		ode_sCurrentArtefactUri = ouri_GetUri()
	
		Gef_WriteStartArtefact(ode_sCurrentArtefactUri, "", CRow, "", "", ode_mstrModuleFullname, ode_mstrModuleVersion, false)
		ode_bWrittenStatusForCurrentArtefact = false
		intRow++
		intCol = 0
		for oCell in row oRow do {
			if (isDeleted(oCell)) continue
			if (blnViewFilter)
				blnVisible = isVisible(oCell)
			else
				blnVisible = true
			strConvert = "Col" intCol""
			if (!ode_bLinkManagerExport && blnVisible)
				ExportRTF(strConvert, oCell, true)
			intCol++
		}
		intCol = 0
		for oCell in row oRow do {
			if (isDeleted(oCell)) continue
			if (blnViewFilter)
				blnVisible = isVisible(oCell)
			else
				blnVisible = true
			strPropertyContent = ""
			strTemp = oCell.CObjectHeading
			strPropertyContent = oCell.CObjectText
			if ((strTemp != "") && (strPropertyContent != "")) {
				strTemp = strTemp "\n"
				strPropertyContent = strTemp strPropertyContent
			}
			strConvert = "Col" intCol""
			if (length(strPropertyContent) == 0 || !blnVisible)
				strTemp = Gef_WriteProperty(strConvert, CTypeString, "", "")
			else
				strTemp = Gef_WriteProperty(strConvert, CRtf, "", "Description")
			if (length(strTemp) > 0) {
				if (!ode_bWrittenStatusForCurrentArtefact) {
					ode_parentStatus = orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, ode_sCurrentArtefactUri, "", 0, false, ode_rootStatus)
				}
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, "", strTemp, 64034, true, ode_parentStatus)
			}
			
			intCol++
		}
		Gef_WriteEndArtefact()
	}
	Gef_WriteEndArtefact()
}

void ExportObject (Object o, bool blnRecurse, bool blnViewFilter) {
	string strObjectName
	string strObjectType
	string strObjectID
	string strObjectVersion = ""
	string strLabel
	Object objetFils
	bool blnVisible
	string strTemp
	bool blnWritedObject = false
	Object oObjectInReference

	if (o == null) {
		return
	}
	if (blnViewFilter) {
		blnVisible = isVisible(o)
	} else {
		blnVisible = true
	}
	//RCM
	oObjectInReference = orcm_ObjectInReference(o, ode_bBaselineOfModuleIsCurrent)
	if (oObjectInReference == null) {
		return
	}
	///RCM
	
	//Initialisation des tableaux de proprietes, de liens de l'objet
	InitializeArraysForObject()

	if (blnVisible) {
		if (table(o)) {
			ExportTable(o, blnViewFilter)
		} else {
			if (ode_bIEPUIDExists && ode_bUseIEPUIDAsIdentifierForObject && length((o.CIEPUID)"") > 0) {
				strObjectID = (o.CIEPUID)""
			} else {
				strObjectID = o.CAbsoluteNumber
			}
			ObjectTypeTrek(o, strObjectType)
			//Remplissage des tableaux de proprietes, de liens de l'objet
			ExportProperties(oObjectInReference, blnViewFilter)
			ExportInternalsLinks(oObjectInReference)
			if (!ode_bLinkManagerExport) {
				ObjectFullName(oObjectInReference, strLabel)
				strTemp = Gef_FormatFullNamePart(strLabel)
				strObjectName = ode_msModuleName.."."..strTemp
				oav_getHash(ode_mbufStringToHash, strObjectVersion)
			} else {
				strObjectName = ""
				strLabel = ""
				strObjectVersion = ""
			}
			strObjectID = ode_sOrchestraModuleId..CIdentifierSeparatorString..strObjectID
			ouri_SetObjectType(CObject)
			ouri_SetObjectID(strObjectID)
			ode_sCurrentArtefactUri = ouri_GetUri()
			blnWritedObject = true
			strTemp = Gef_WriteStartArtefact(ode_sCurrentArtefactUri, strObjectName, strObjectType, strLabel, strObjectVersion,
							   ode_mstrModuleFullname, ode_mstrModuleVersion, false)
			if (length(strTemp) > 0) {
				ode_bWrittenStatusForCurrentArtefact = true
				ode_parentStatus = orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, ode_sCurrentArtefactUri, strTemp, 64035, true, ode_rootStatus)
			} else {
				ode_bWrittenStatusForCurrentArtefact = false
				ode_parentStatus = null
			}
			WriteAllPropertiesAndLinks()
		}
	} else {
		if (table(o))
			ExportTable(o, blnViewFilter)
	}
	if (blnRecurse && !table(o)) {
		for objetFils in oObjectInReference do {
			//RCM
			if (!orcm_isFirstObject(objetFils)) continue
			///RCM
			ExportObject(objetFils, blnRecurse, blnViewFilter)
		}
	}
	if (blnWritedObject)
		Gef_WriteEndArtefact()
}

void ExtractView (Module moduleRef) {
	Object o

	if (sorting(moduleRef)) {
		for o in moduleRef  do {
			//RCM
			if (!orcm_isFirstObject(o)) continue
			///RCM
			ExportObject(o, false, true)
		}
	} else {
		for o in top moduleRef  do {
			//RCM
			if (!orcm_isFirstObject(o)) continue
			///RCM
			ExportObject(o, true, true)
		}
	}
}

void DescribeView (Module moduleRef) {
	string strPropertyName
	string strPropertyContent
	Column c
	string strTemp

	for c in moduleRef do {
		strPropertyName = (title c)
		strPropertyContent = (width c) " "
		strTemp = Gef_WriteProperty(strPropertyName, CColumn, strPropertyContent, "")
		if (length(strTemp) > 0) {
			if (!ode_bWrittenStatusForCurrentArtefact) {
				ode_parentStatus = orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, ode_sCurrentArtefactUri, "", 0, false, ode_rootStatus)
			}
			orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, "", strTemp, 64036, true, ode_parentStatus)
		}
	}
}

string HashView (Module moduleRef) {
	if (ode_bLinkManagerExport)
		return ""
	string strPropertyName
	string strPropertyContent
	Column c
	Buffer bufStringToHash = create

	for c in moduleRef do {
		strPropertyName = (title c)
		strPropertyContent = (width c) " "
		bufStringToHash += strPropertyName CColumn strPropertyContent
	}
	oav_getHash(bufStringToHash, strPropertyContent)
	delete(bufStringToHash)
	return strPropertyContent
}

void ManageFullName (string &sRetour, bool blnEncode) {
	Regexp namePart = regexp "/([^/]*)"
	string strTemp
	bool blnRemoveProjectName = false
	
	Buffer buf = create
	buf = ""
	Buffer input = create
	input = ode_mstrModuleFullname
	while (!null input && namePart input) {
		if (blnRemoveProjectName) {
			strTemp = input[match 1]
			if (length(buf) == 0) {
				if (blnEncode && length(strTemp) > 0) {
					strTemp = Gef_FormatFullNamePart(strTemp)""
				}
				buf = strTemp
			} else {
				if (blnEncode && length(strTemp) > 0) {
					strTemp = Gef_FormatFullNamePart(strTemp)""
				}
				buf += "."..strTemp
			}
		} else {
			blnRemoveProjectName = true
		}
		input = input[end 0 + 1:]
	}
	sRetour = stringOf(buf)
	delete(buf)
}

void ExportModuleProperties (Module moduleRef) {
	AttrDef ad
	string strPropertyName
	string strPropertyType
	string strPropertyContent

	InitializeArraysForObject()

	for ad in moduleRef do {
		if (ad.module) {
			strPropertyName = ad.name
			strPropertyType = ad.typeName
			if (strPropertyType == CTypeText)
				strPropertyType = CTypeString
			strPropertyContent = moduleRef.strPropertyName
			put(ode_maProperties, strPropertyName, ode_mintIndexProperties, 0)
			put(ode_maProperties, strPropertyType, ode_mintIndexProperties, 1)
			put(ode_maProperties, strPropertyContent, ode_mintIndexProperties, 2)
			put(ode_maProperties, CMozartInternal, ode_mintIndexProperties++, 3)
			ode_mbufStringToHash += strPropertyName strPropertyType strPropertyContent
		}
	}
	WriteAllPropertiesAndLinks()
}

void OrchestraDocumentaryExport (
	string UriFilePath,
	string ResultFilePath,
	string StatusFilePath,
	string DxlFilePath,
	bool blnExpandView,
	bool blnLinkManagerExport
) {
	string strResult
	string strToolName
	string strProjectName
	string sDoorsModuleId
	string strViewName
	string strViewID
	string strObjectType
	string strObjectID
	string strTemp
	string strModuleLabel
	int intAbsoluteNumber
	Module moduleRef
	Item itemRef
	Project projectRef
	string strBaseline
	int intMajor
	int intMinor
	string strSuffix
	ModName_ modName
	bool blnCloseModule
	string strFullLabel
	string strUri
	int intStatusFinal = orcs_Status_Severity_OK
	bool blnUriOk
	string strOldRootName = ""
	bool blnExplorerVisible
	Date dLastModificationDateTime
	
	noError()

	InitializeGefFile()
	InitializeArtifactsVersion()
	InitializeOrchestraUri()
	InitializeOrchestraStatus()
	odenv_Initialize()

	ode_bLinkManagerExport = blnLinkManagerExport
	
	if (ouri_UriFile_Split(UriFilePath)) {
		ode_mstrConfDir_Env = ouri_GetODMVariable(CConfigurationDirectory)
		if (ouri_GetODMVariable(CUseIEPUIDAsIdentifier)"" == "true") {
			ode_bUseIEPUIDAsIdentifierForObject = true
		} else {
			ode_bUseIEPUIDAsIdentifierForObject = false
		}
		Gef_InitOutputFile(ResultFilePath)
		
		ode_mbufStringToHash = create
	
		for strUri in ouri_skpListOfUri do {
			int i = (int key ouri_skpListOfUri)
			ode_sCurrentArtefactUri = strUri
			blnUriOk = true
			strViewName = null
			sDoorsModuleId = null
			strObjectID = null
			
			ouri_SetUri(strUri)
			ode_mstrRootNameOfExportedArtefact = ouri_GetRootName()
	
			ode_rootStatus = orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_OK, ode_sCurrentArtefactUri, "", 0, false, orcs_StatusDefinition_Status())
			
			strObjectType = ouri_GetObjectType()
			if (strObjectType"" == CFolder"") {
				intStatusFinal = orcs_Status_Severity_ERROR
				strResult = "The folder type does not support the Export feature."
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", strResult, 64037, true, ode_rootStatus)
				blnUriOk = false
			}
			
			if (blnUriOk && !find(ouri_skpListOfRootNameProjectId, ode_mstrRootNameOfExportedArtefact, ode_mstrProjectIdOfExportedArtefact)) {
				intStatusFinal = orcs_Status_Severity_ERROR
				strResult = "Cannot find the project with RootName: " ode_mstrRootNameOfExportedArtefact
				orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", strResult, 64038, true, ode_rootStatus)
				blnUriOk = false
			}
			
			if (blnUriOk) {
				strToolName = ouri_GetRootType()
				strTemp = ouri_GetObjectID()
				omi_TransformIdOMIToIdDoors(strTemp, strObjectType, ode_mstrProjectIdOfExportedArtefact, ode_sOrchestraModuleId)
				if (strTemp == "") {
					intStatusFinal = orcs_Status_Severity_ERROR
					strResult = "The Doors Connector is not configured to use the Orchestra Module Id.\nPlease refer to the SUM_DoorsCustom to configure it."
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", strResult, 64039, true, ode_rootStatus)
					blnUriOk = false
				}
			}
			
			if (blnUriOk) {
				strViewName = ouri_Parameter(CViewParameterName)
				ouri_ClearParameters()
				
				ouri_ExtractDoorsIdFromOrchestraId(strObjectType, strTemp, sDoorsModuleId, strObjectID, strViewName)
				
				itemRef = itemFromID(sDoorsModuleId)
	
				if (itemRef != null) {
					if (isDeleted(itemRef)) {
						intStatusFinal = orcs_Status_Severity_ERROR
						strResult = "The module " name(itemRef) " was removed."
						orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", strResult, 64087, true, ode_rootStatus)
						blnUriOk = false
						continue
					}
					if (strOldRootName != ode_mstrRootNameOfExportedArtefact) {
						strOldRootName = ode_mstrRootNameOfExportedArtefact
		                Gef_setExtraFileDirectory(ode_mstrRootNameOfExportedArtefact)
		                ode_mstrPicturesDirectoryRelative = Gef_getExtraFileDirectory()
		                os_extractPath(ResultFilePath, ode_mstrPicturesDirectoryRelative, ode_mstrPicturesDirectory)
					}
					projectRef = getParentProject(itemRef)
					strProjectName = fullName(projectRef)
	
					strResult = openProject(strProjectName)
	
					strModuleLabel = name(itemRef)
	
					if (!null strResult) {
						intStatusFinal = orcs_Status_Severity_ERROR
						strResult = "Cannot open the Doors Project: " strProjectName
						orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", strResult, 64040, true, ode_rootStatus)
						blnUriOk = false
					} else {
						strBaseline = ouri_findBaselineForAModuleInEnvironment(ode_sOrchestraModuleId)
						//Calcul du label en fonction de la baseline et ouverture du module
						if (strBaseline != "current") {
							ode_bBaselineOfModuleIsCurrent = false
							os_ExtractBaselinePartsFromBaselineName(strBaseline, intMajor, intMinor, strSuffix)
							modName = module(fullName(itemRef))
							if (!open(modName)) {
								blnCloseModule = true
							} else {
								blnCloseModule = false
							}
							moduleRef = read(fullName(itemRef), false)
							if (isBatch() || strObjectType == COrchestraModuleType) {
								moduleRef = load(moduleRef, baseline(intMajor,intMinor,strSuffix), false)
							} else {
								moduleRef = load(moduleRef, baseline(intMajor,intMinor,strSuffix), true)
							}
							if (moduleRef == null) {
								intStatusFinal = orcs_Status_Severity_ERROR
								strResult = "Cannot open the Doors Baseline: \"" strModuleLabel " " intMajor "." intMinor " (" strSuffix ")\"."
								orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", strResult, 64080, true, ode_rootStatus)
								blnUriOk = false
							} else {
								ode_mstrModuleVersion = version(moduleRef)
								ode_mstrModuleFullname = fullName(moduleRef)
								ManageFullName(ode_msModuleName, true)
								strModuleLabel = strModuleLabel " " intMajor "%2E" intMinor " (" strSuffix ")"
								ode_msModuleName = ode_msModuleName " " intMajor "%2E" intMinor " (" strSuffix ")"
							}
						} else {
							ode_bBaselineOfModuleIsCurrent = true
							modName = module(fullName(itemRef))
							if (!open(modName)) {
								blnCloseModule = true
							} else {
									blnCloseModule = false
							}
							if (isBatch() || strObjectType == COrchestraModuleType) {
								moduleRef =  read(fullName(itemRef), false)
							} else {
								moduleRef =  read(fullName(itemRef), true)
							}
							dLastModificationDateTime = lastModifiedTime(moduleRef)
							ode_mstrModuleVersion = dLastModificationDateTime""
							ode_mstrModuleFullname = fullName(moduleRef)
							ManageFullName(ode_msModuleName, true)
						}
						if (blnUriOk) {
							//Chargement des attributs complémentaire en fonction du type d'export
							if (blnLinkManagerExport) {
								ReadLinkManagerProperties()
								VerifyIfLinkManagerPropertiesExists(moduleRef)
							} else {
								ReadAddedProperties()
								VerifyIfAddedPropertiesExists(moduleRef)
							}
		
							//On regarde si le module est un module Trek
							IsATrekModule(moduleRef)
		
							//RCM
							orcm_isRCMModule(moduleRef)
							///RCM
		
							//Lecture de la composition du fullName
							ReadMozartAndLinkManagerLabel(moduleRef)
		
							//Lecture des types de liens que l'on doit suivre
							ReadLinkModuleFile()
		
							//Initialisation de l'Uri
							ouri_SetRootType(strToolName)
							ouri_SetRootName(ode_mstrRootNameOfExportedArtefact)
							
							DetermineMozartFullNameAttributeForThisModule(moduleRef)
		
							ManageFullName(strModuleLabel, false)
							
							blnExplorerVisible = showingExplorer(moduleRef)
							
							if (strObjectType == COrchestraModuleType) { //Export du module sans tenir compte d'une vue
								if (blnExplorerVisible) {
									hideExplorer(moduleRef)
								}
								ouri_SetObjectID(ode_sOrchestraModuleId)
								ouri_SetObjectType(COrchestraModuleType)
								ode_sCurrentArtefactUri = ouri_GetUri()
								strTemp = Gef_WriteStartArtefact(ode_sCurrentArtefactUri, ode_msModuleName, COrchestraModuleType, strModuleLabel, ode_mstrModuleVersion,
												   ode_mstrModuleFullname, ode_mstrModuleVersion, true)
								if (length(strTemp) > 0) {
									ode_bWrittenStatusForCurrentArtefact = true
									ode_parentStatus = orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, ode_sCurrentArtefactUri, strTemp, 64041, true, ode_rootStatus)
								} else {
									ode_bWrittenStatusForCurrentArtefact = false
								}
		
								ExportModuleProperties(moduleRef)
								Object objectRef
								for objectRef in top moduleRef do {
									//RCM
									if (!orcm_isFirstObject(objectRef)) continue
									///RCM
									ExportObject(objectRef, true, false)
								}
								Gef_WriteEndArtefact()
							} else if (strObjectType == CView) { //Export du module en tenant compte de strViewName
								strViewID = ode_sOrchestraModuleId..CIdentifierSeparatorString..strViewName
								if (!load(moduleRef,view strViewName)) {
									intStatusFinal = orcs_Status_Severity_ERROR
									strResult = "Cannot open the Doors View: " strViewName
									ode_parentStatus = orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", strResult, 64042, true, ode_rootStatus)
									blnUriOk = false
								} else {
									if (blnExplorerVisible) {
										hideExplorer(moduleRef)
									}
									ouri_SetObjectID(strViewID)
									ouri_SetObjectType(CView)
									ode_sCurrentArtefactUri = ouri_GetUri()
									strFullLabel = "("..ode_mstrRootNameOfExportedArtefact.." "..strModuleLabel.." "..CView..") "..strViewName
									strTemp = Gef_WriteStartArtefact(ode_sCurrentArtefactUri, Gef_FormatFullNamePart(strViewName), CView, strFullLabel,
													   HashView(moduleRef), ode_mstrModuleFullname, ode_mstrModuleVersion, false)
									if (length(strTemp) > 0) {
										ode_bWrittenStatusForCurrentArtefact = true
										orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, ode_sCurrentArtefactUri, strTemp, 64043, true, ode_rootStatus)
									} else {
										ode_bWrittenStatusForCurrentArtefact = false
									}
									if (!blnLinkManagerExport)
										DescribeView(moduleRef)
									ouri_AddParameter(CViewParameterName, strViewName)
									ExtractView(moduleRef)
									Gef_WriteEndArtefact()
								}
							} else if (strObjectType == CObject) { //Export d'un objet particulier
								Object objectRef
								bool bFound = false
								if (ode_bUseIEPUIDAsIdentifierForObject) {
									Object oTmp
									for oTmp in entire(moduleRef) do {
										if ((oTmp.CIEPUID)"" == strObjectID) {
											objectRef = oTmp
											bFound = true
											break
										}
									}
								}
								if (!bFound) {
									if (isValidInt(strObjectID)) {
										intAbsoluteNumber = os_StringToInt(strObjectID)
										bool blnFilteringModule
										blnFilteringModule = filtering(moduleRef)
										if (blnFilteringModule)
											filtering off
										objectRef = object(intAbsoluteNumber, moduleRef)
										if (blnFilteringModule)
											filtering on
									}
								}
								if (objectRef != null) {
									//RCM
									if (orcm_isFirstObject(objectRef)) {
									///RCM
										if (strViewName != null) { //Export de l'objet à travers une vue
											strViewID = ode_sOrchestraModuleId..CIdentifierSeparatorString..strViewName
											if (!load(moduleRef,view strViewName)) {
												intStatusFinal = orcs_Status_Severity_ERROR
												strResult = "Cannot open the Doors View: " strViewName
												orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", strResult, 64044, true, ode_rootStatus)
												blnUriOk = false
											} else {
												if (blnExplorerVisible) {
													hideExplorer(moduleRef)
												}
												ouri_AddParameter(CViewParameterName, strViewName)
												ExportObject(objectRef, true, true)
											}
										} else { //Export de l'objet sans tenir compte de la vue
											if (blnExplorerVisible) {
												hideExplorer(moduleRef)
											}
											ExportObject(objectRef, true, false)
										}
									//RCM
									}
									///RCM
								} else {
										intStatusFinal = orcs_Status_Severity_ERROR
										strResult = "Cannot found the object: " intAbsoluteNumber " in the module: " strModuleLabel
										orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", strResult, 64045, true, ode_rootStatus)
										blnUriOk = false						
								}
							}
							if (blnExplorerVisible) {
								showExplorer(moduleRef)
							}
							if (blnCloseModule && open(modName)) {
								close(moduleRef)
							}
						}
					}
				} else {
					intStatusFinal = orcs_Status_Severity_ERROR
					strResult = "Cannot open the Doors Module."
					orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_ERROR, "", strResult, 64046, true, ode_rootStatus)
					blnUriOk = false
				}
			}
		}
		delete(ode_mbufStringToHash)
		Gef_CloseOutputFile()
		delete(ode_mAddedProperties)
		delete(ode_mMozartObjectFullName)
	} else {
		intStatusFinal = orcs_Status_Severity_ERROR
	}
	orcs_StatusDefinition_WriteStatus(intStatusFinal, "", "", 0, false)
	if (length(StatusFilePath) > 0) {
		Stream statusFile
		statusFile = write StatusFilePath
		statusFile << orcs_StatusDefinition_ToString()
		close statusFile
	}
	TerminateOrchestraStatus()
	TerminateGefFile()
	TerminateArtifactsVersion()
	TerminateOrchestraUri()
	odenv_Terminate()
}
