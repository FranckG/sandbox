Skip omi_OMIbyId = createString

void omi_getOMIForAModuleVersion(
	ModuleVersion mv,
	string &sOMI
) {
	ModuleProperties mp = null
	getProperties (mv, mp)
	if (!null mp) {
		AttrDef ad = find(mp, COrchestraModuleIdAttribute)
		if (!null ad) {
			sOMI = mp.COrchestraModuleIdAttribute
		}
	}
}

void omi_getOMIForAModule(
	Item itemRef,
	string &sOMI
) {
	sOMI = null
	if (find(omi_OMIbyId, uniqueID(itemRef), sOMI)) {
		return
	}
	if (type(itemRef) == CModuleTypeFormal){
		if (open(module fullName itemRef)) {
			AttrDef ad = find(module itemRef, COrchestraModuleIdAttribute)
			if (!null ad) {
				sOMI = (module itemRef).COrchestraModuleIdAttribute
				put(omi_OMIbyId, uniqueID(itemRef), sOMI"")
			}
		} else {
			ModuleVersion mv = moduleVersion(module fullName itemRef)
			omi_getOMIForAModuleVersion(mv, sOMI)
			put(omi_OMIbyId, uniqueID(itemRef), sOMI"")
		}
	}
}

string omi_getIdDoorsInProjectbyIdOMI(
	Buffer sIdOMI,
	string &projectId
) {
	Item i
	Project p
	string sResult
	
	p = project(itemFromID(projectId))
	if (p == null) {
		return ""
	}
	for i in p do {
		omi_getOMIForAModule(i, sResult)
		if (type(i) == CModuleTypeFormal && projectId == uniqueID(getParentProject(i)) && tempStringOf(sIdOMI) == sResult){
			return uniqueID(i)
		}
	}
	return ""
}

string omi_getOMIInCache(
	string &sModuleId,
	string &sProjectId
) {
    string sOMI = sModuleId""
	bool bOMItoId
	if (matches("\\.", sOMI)) {
		bOMItoId = true
	} else {
		bOMItoId = false
	}
	if (!bOMItoId && find(omi_OMIbyId, sModuleId, sOMI)) {
		return
	}
	Module orcModule
	Module currentModule = current
	Item itemRef
	itemRef = itemFromID(sProjectId)
	if (null itemRef) {
		return sOMI
	}
	Buffer bufCacheModuleFullName = create
	bufCacheModuleFullName = fullName(itemRef)
	bufCacheModuleFullName += "/Orchestra Configuration/Orchestra Cache " getDatabaseIdentifier() "." sProjectId
	orcModule = read(tempStringOf(bufCacheModuleFullName), false, true)
	delete(bufCacheModuleFullName)
	if (null orcModule) {
		return sOMI
	}
	Buffer bufValue = create
	Buffer bufKey = create
	Buffer bufProjectIdInCache = create
	Object o
	bool bFoundInCache = false
	for o in orcModule do {
		if (bOMItoId) {
			bufKey = o."ModuleIdOrchestra"
			bufValue = o."ModuleIdDoors"
		} else {
			bufValue = o."ModuleIdOrchestra"
			bufKey = o."ModuleIdDoors"
		}
		if (tempStringOf(bufKey) == sModuleId"") {
			sOMI = stringOf(bufValue)
			put(omi_OMIbyId, sModuleId"", sOMI)
			bFoundInCache = true
			break
		}
	}
	close(orcModule)
	if (!bFoundInCache && !bOMItoId) {
		itemRef = itemFromID(sModuleId)
		if (itemRef != null) {
			omi_getOMIForAModule(itemRef, sOMI)
		}
	}
	if (currentModule != null) {
		current = currentModule
	}
	return sOMI
}

void omi_TransformIdOMIToIdDoors(
	string &sIdOMI,
	string &artefactType,
	string &projectId,
	string &sOrchestraModuleId
) {
	if (artefactType"" == CFolder"") {
		return
	}
	Regexp r = regexp "^([0-9a-z]*\\.[0-9a-z]*)(.*)"
    if ( r sIdOMI ) {
        Buffer buffRes = create
		Buffer optionalView = create
	    Buffer bTemp = create
        buffRes = sIdOMI[match 1]
        sOrchestraModuleId = stringOf(buffRes)
		optionalView = sIdOMI[match 2]
		bTemp = omi_getOMIInCache(sOrchestraModuleId, projectId)""
	    if (tempStringOf(bTemp) == tempStringOf(buffRes)){
	    	bTemp = omi_getIdDoorsInProjectbyIdOMI(buffRes, projectId)""
	    }
		bTemp += optionalView
	    if ( sIdOMI == tempStringOf(bTemp) ) {
			sIdOMI = stringOf(buffRes) stringOf(optionalView)
	    } else {
			sIdOMI = stringOf(bTemp)
	    }
	    delete(bTemp)
	    delete(optionalView)
	    delete(buffRes)
    } else {
        sIdOMI = ""
    }
}

