/**
 * Created by: T0008293
 * Creation date: 30 août 2011
 *
 * DXL Editor is provided by SODIUS (www.dxleditor.com)
 */
pragma encoding, "UTF-8"

#include <OrchestraConstantes.inc>
#include <OrchestraGef.inc>
#include <OrchestraStatus.inc>
#include <OMIManagement/OrchestraManageOMI.inc>
#include <OrchestraString.inc>
#include <OrchestraUri.inc>

pragma runLim, 0

string ogp_sID

void ogp_ExploreFolder (Folder f, string sUriProject, string sSegment) {
    OleAutoObj oStatus = null
    string sFolderId
    
    ouri_SetUri(sUriProject)
    if ((type(f))"" == CProject) {
		ouri_SetObjectType(CProject)
		sFolderId = uniqueID(f)
		ouri_SetObjectID(sFolderId)
		Gef_WriteStartArtefact(ouri_GetUri(), name(f), CProject, "", "", "", "", true)
		ouri_ClearParameters()
		ouri_AddParameter("projectID", uniqueID(f))
		sUriProject = ouri_GetUri()
    } else if ((type(f))"" == CFolder) {
		ouri_SetObjectType(CFolder)
		sFolderId = fullName(f)
		ouri_SetObjectID(sFolderId)
		Gef_WriteStartArtefact(ouri_GetUri(), name(f), CFolder, "", "", "", "", true)
	}
	if (sSegment != CSilver) {
		BaselineSet bs
		ModuleVersion mv
		BaselineSetDefinition baseSetDef
		string errmess
		Buffer sId = create
		ModName_ mod
		for baseSetDef in f do {
			errmess = read(baseSetDef)
			if (length(errmess) > 0) {

			} else if (isEmpty(baseSetDef)) {

			} else {
				Buffer sDescription = create
				ouri_SetObjectType("BaselineSetDefinition")
				sId = sFolderId CIdentifierSeparatorString name(baseSetDef)
				ouri_SetObjectID(stringOf(sId))
				Gef_WriteStartArtefact(ouri_GetUri(), name(baseSetDef), "BaselineSetDefinition", "", "", "", "", true)
				sDescription = "Description: " description(baseSetDef) "\nModule list:"
				for mod in baseSetDef do {
					sDescription += "\n\t" fullName(mod)
				}
				Gef_WriteProperty("description", CTypeString, tempStringOf(sDescription), "")
				for bs in baseSetDef do {
					ouri_SetObjectType("BaselineSet")
					sId = sFolderId CIdentifierSeparatorString name(baseSetDef) CIdentifierSeparatorString versionID(bs)
					ouri_SetObjectID(stringOf(sId))
					Gef_WriteStartArtefact(ouri_GetUri(), versionID(bs), "BaselineSet", "", "", "", "", true)
					setempty(sDescription)
					sDescription = "Version: " versionID(bs) "\nAnnotation: " annotation(bs) "\n"
					sDescription += "Created by " user(bs) " on " dateOf(bs) "\n"
					for mv in bs do {
						sDescription += "    " (fullName mv) " [" (versionString mv) "]\n"
					}
					Gef_WriteProperty("description", CTypeString, tempStringOf(sDescription), "")
					Gef_WriteEndArtefact()
				}
				ouri_SetObjectType("BaselineSet")
				sId = sFolderId CIdentifierSeparatorString name(baseSetDef) CIdentifierSeparatorString "current"
				ouri_SetObjectID(stringOf(sId))
				Gef_WriteStartArtefact(ouri_GetUri(), "current", "BaselineSet", "", "", "", "", true)
				Gef_WriteEndArtefact()
				delete(sDescription)
				Gef_WriteEndArtefact()
			}
		}
		delete(sId)
		Item itemRef
		for itemRef in f do {
			if ((type(itemRef))"" == CFolder && name(itemRef) != CRepertoireConfigName && name(itemRef) != COrchestraInstallationFolderName) {
				Folder subFolder = folder(itemRef)
				ogp_ExploreFolder(subFolder, sUriProject, sSegment)
			}
		}
   	}
	if ((type(f))"" == CProject || (type(f))"" == CFolder) {
		Gef_WriteEndArtefact()
   	}
}

void OrchestraGetProjects (
	string UriFilePath,
	string ResultFilePath,
	string StatusFilePath,
	string DxlFilePath,
	bool blnExpandView,
	bool blnLinkManagerExport)
{
	Project projectRef
	string sUri
	string sSegment
		
	noError()

	InitializeGefFile()
	InitializeOrchestraUri()
	InitializeOrchestraStatus()

	Gef_InitOutputFile(ResultFilePath)

	ouri_SetRootType("Doors")
	ouri_SetRootName("Environment")
	ouri_AddParameter("database", getenv("DOORSDATA"))
	sUri = ouri_GetUri()
	sSegment = ouri_GetODMVariable(CSegmentName)
	Gef_WriteStartArtefact(sUri, "", "Database", "", "", "", "", true)
	string sCacheModule
	string sPartialName = "/Orchestra Configuration/Orchestra Cache " getDatabaseIdentifier() "."
	for projectRef in database do {
		sCacheModule = fullName(projectRef) sPartialName uniqueID(projectRef)
		if (exists module sCacheModule) {
			ogp_ExploreFolder(folder(itemFromID(uniqueID(projectRef))), sUri, sSegment)
		} else if (sSegment != CSilver) {
			orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_WARNING, "",
				"The project " fullName(projectRef) " is not tagged for Orchestra.", 64030, 
				true, orcs_StatusDefinition_Status())	
		}
	}
	Gef_WriteEndArtefact()
	Gef_CloseOutputFile()
	orcs_StatusDefinition_WriteStatus(orcs_Status_Severity_OK, sUri, "", 0, false)
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
