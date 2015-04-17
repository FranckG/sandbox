pragma runLim, 0

bool orcm_blnRCMModule = false

void orcm_isRCMModule(Module m) {
	if (!null checkDXL("DEFS_SYSIE_Version")) {
		orcm_blnRCMModule = false
		return
	}
	orcm_blnRCMModule = RCM_isModuleControlled(m)
}

bool orcm_isFirstObject (Object o) {
	if (null o) {
		return false
	}
	if (orcm_blnRCMModule) {
		return RCM_isFirstObject o
	} else {
		return true
	}
}

Object orcm_ObjectInReference (Object o, bool bCurrent) {
	if (null o || !orcm_blnRCMModule) {
		return o
	}
	if (!RCM_isObjectControlled(o)) {
		return o
	}
	Object tmp
	if (!bCurrent) then {
		while (!RCM_isLastObject(o)) {
	   		tmp = RCM_getNextObject o 
			if (RCM_isObjectInWorking(tmp)) {
				return o
			}
			o = tmp
		}
	} else {
		while (!RCM_isLastObject(o)) {
			tmp = RCM_getNextObject o
			o = tmp
		}	
	}
	return o	
}

Object orcm_FirstObject (Object o, Module m) {
	if (null o || !null checkDXL("DEFS_SYSIE_Version")) {
		return o
	}
	if (!RCM_isModuleControlled(m)) {
		return o
	}
	if (!RCM_isObjectControlled(o)) {
		return o
	}
	return RCM_getFirstObject o
}
