pragma runLim, 0

bool orcm_blnRCMModule = false

void orcm_isRCMModule(Module m) {
	orcm_blnRCMModule = false
}

bool orcm_isFirstObject (Object o) {
	if (null o) return false
	return true
}

Object orcm_ObjectInReference (Object o, bool bCurrent) {
	return o
}

Object orcm_FirstObject (Object o, Module m) {
	return o
}