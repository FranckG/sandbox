/**
 * Created by: T0008293
 * Creation date: 18 avr. 2012
 *
 * DXL Editor is provided by SODIUS (www.dxleditor.com)
 */
pragma encoding, "UTF-8"

#include <OrchestraInstall.inc>
#include <OrchestraConstantes.inc>

void orclm_LinkManagerExecuteCommand(string sCommand, bool bWithLabel) {
	Object o = current
	string sObjectHeading = o.CObjectHeading
	string sObjectText = o.CObjectText
	string sId = ""
	Module mCurrent = current
	string sModuleId = uniqueID(mCurrent)
	
	string sLinkManagerId = ""
	string sLinkManagerLabel = ""
	
	AttrDef ad = find(mCurrent, CIEPUID)
	if (! null ad && ad.object) {
		sId = o.CIEPUID
		sLinkManagerId = sId
	}
	if (length(sId) == 0) {
		sId = o.CAbsoluteNumber
		sLinkManagerId = sModuleId "." sId
	}
	if (bWithLabel) {
		if (length(sObjectText) > 0) {
			sLinkManagerLabel = sObjectText
		}
		if (length(sObjectHeading) > 0) {
			sLinkManagerLabel = sObjectHeading
		}
		if (length(sId) > 0) {
			sLinkManagerLabel = sId
		}
		system COrchestraLMFullPath " " sCommand " \"" sLinkManagerId "\" \"" sLinkManagerLabel "\""
	} else {
		system COrchestraLMFullPath " " sCommand " \"" sLinkManagerId "\""
	}
}

void orclm_LinkManagerExecuteCommandOnMultipleObject(string sCommand) {
	string lmid = ""
	string objId = ""
	string res = ""
	Module m = current
	
	string modUniqueId = uniqueID(m)
	
	AttrDef ad = find(m, CIEPUID)
	
	Object oStart
	Object oEnd
	
	getSelection(m, oStart, oEnd)
	if (oStart == null || oEnd == null)  {
	  oStart = current
	  oEnd = current
	}
	
	bool bStart = false
	Object o
	for o  in m do {
		if (o == oStart) {
			bStart = true
		}
		if (bStart) {
			objId = ""
			if (! null ad && ad.object) {
				objId = o.CIEPUID
				lmid = objId
			}
			if (length(objId) == 0) {
				objId = o.CAbsoluteNumber
				lmid = modUniqueId "." objId
			}
			if (length(res) == 0) {
				res = lmid
			} else {
				res = res  ";"  lmid
			}
		}
		if (o == oEnd) {
			break
		}
	}
	
	system COrchestraLMFullPath " " sCommand " \"" res "\" \"" res "\""
}
