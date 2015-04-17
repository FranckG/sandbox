const string COrchestraTriggerOMIVersion = "2.2.4"
//Attention lorsqu'on inclus ce fichier il faut une Skip list
//tpfo_skpProjectList
//qui contient la liste des noms de projet à tagger (/projet)
Module tpfo_ModuleOfCache = null
string tpfo_nameOfCacheModule
int tpfo_nbItemToTagged = 0
string tpfo_nameProject
Buffer tpfo_CompletionMessage

bool tpfo_CreateCacheModule(Item projectItem) {
	if (projectItem == null || type(projectItem) != CProject) {
		return false
	}
	string sProjectName = fullName(projectItem)
	if (!canModify(projectItem)) {
		tpfo_CompletionMessage += "\tYou don't have modify access to the project.\n"
		return false
	}
	Module cacheModule
	string sNameOfCacheModule = sProjectName "/Orchestra Configuration/Orchestra Cache " getDatabaseIdentifier() "." uniqueID(projectItem)
	string sFolderName = sProjectName "/Orchestra Configuration"
	//Remove modules in sFolderName if don't match sNameOfCacheModule
	Item itemRef

	if (folder(sFolderName)) {
		Folder f = folder(sFolderName)
		if (isDeleted(f) && purge(f) != null) {
			tpfo_CompletionMessage += "\tImpossible to purge the folder " sFolderName ".\n"
			return false
		}
	}
	
	if (!folder(sFolderName)) {
		Folder f = create(sFolderName, "Configuration folder for Orchestra")
		if (null f) {
			tpfo_CompletionMessage += "\tImpossible to create the folder " sFolderName ".\n"
			return false
		}
		specific(f)
		set(f, read|modify|create|delete, "")
		tpfo_CompletionMessage += "\tFolder " sFolderName " created.\n"
	} else {
		tpfo_CompletionMessage += "\tStart to delete the old module of cache."
		for itemRef in folder(sFolderName) do {
			if (type(itemRef) == CModuleTypeFormal && !matches(sNameOfCacheModule, fullName(itemRef)) && matches("Orchestra Cache", fullName(itemRef))) {
				if (delete(itemRef) == null){
					tpfo_CompletionMessage += "."
					purge(itemRef)
				}	
			}
		}
		tpfo_CompletionMessage += " End\n"
	}
	
	if (module(sNameOfCacheModule)) {
		ModName_ modName = module(sNameOfCacheModule)
		if (isDeleted(modName) && purge(modName) != null) {
			tpfo_CompletionMessage += "\tImpossible to purge the cache module " sNameOfCacheModule ".\n"
			return false
		}
	}
	
	if (!module(sNameOfCacheModule)) {
		cacheModule = create(sNameOfCacheModule, "Orchestra cache. Please do not remove, don't change access rights", "", 1, false)
		if (null cacheModule) {
			tpfo_CompletionMessage += "\tImpossible to create the cache module " sNameOfCacheModule ".\n"
			return false
		}
		current = cacheModule
		create object type "String" attribute "ModuleIdDoors"
		create object type "String" attribute "ModuleIdOrchestra"
		specific(cacheModule)
		set(cacheModule, delete|create, "")
		save(cacheModule)
		tpfo_CompletionMessage += "\tCache module " sNameOfCacheModule " created.\n"
	}
	return true
}

bool tpfo_CreateTrigger(Item projectItem) {
	if (projectItem == null || type(projectItem) != CProject) {
		return false
	}
	bool createOrchestraTrigger = true
	Trigger t = null
	string triggerName = ""
	for t in project(fullName(projectItem)) do {
		triggerName = name(t)
	    if (!matches("Orchestra:Orchestra Module Id Feature", triggerName)) continue
		if (matches("[0-9]*\\.[0-9]*\\.[0-9]*", triggerName)) {
			if (COrchestraTriggerOMIVersion <= triggerName[match 0]) {
				createOrchestraTrigger = false
				break
			}
		}
		tpfo_CompletionMessage += "\tThe trigger " triggerName " is deleted.\n"
	    delete t
	}
	if (createOrchestraTrigger) {
		string sProjectName = name(projectItem)
		string sTriggerPath
		sTriggerPath = CInstallDirectory FileSep_ "OMIManagement" FileSep_ "trigger.dxl"
		Stat s = create(sTriggerPath)
		if (null s) {
			tpfo_CompletionMessage += "\tImpossible to load the trigger definition (" sTriggerPath ").\n"
			return false
		}
		delete(s)
		string sTriggerCode = readFile(sTriggerPath)
		trigger ("Orchestra:Orchestra Module Id Feature Post Open"..COrchestraTriggerOMIVersion, project->sProjectName->module->all->formal, post, open, 10, sTriggerCode)
		tpfo_CompletionMessage += "\tThe trigger Orchestra:Orchestra Module Id Feature Post Open" COrchestraTriggerOMIVersion " is created.\n"
	}
	return true
}

void tpfo_TagProjectList() {
	bool isInCache(string moduleID) {
		bool inCache = false
		string strKey
		Object o
		if (open module tpfo_nameOfCacheModule && !null tpfo_ModuleOfCache){
			for o in entire(tpfo_ModuleOfCache) do {
				if (isDeleted(o)) {
					continue
				}
				strKey = o."ModuleIdDoors"
				if (strKey == moduleID) {
					if (!inCache) {
						inCache = true
					} else {
						inCache = false
						break
					}
				}
			}
		}
		return inCache
	}
	
	bool isOMIInCache(string OMI) {
		bool inCache = false
		string strKey
		Object o
		if (open module tpfo_nameOfCacheModule && !null tpfo_ModuleOfCache){
			for o in entire(tpfo_ModuleOfCache) do {
				if (isDeleted(o)) {
					continue
				}
				strKey = o."ModuleIdOrchestra"
				if (strKey == OMI) {
					if (!inCache) {
						inCache = true
					} else {
						inCache = false
						break
					}
				}
			}
		}
		return inCache
	}
	
	bool tagProject(Folder f, bool blank){
		Item i
		bool blnCancel = false
		string sOMI
		for i in f do {
			if (type(i) == CModuleTypeFormal) {
				if (blank) {
					tpfo_nbItemToTagged++
				} else {
					progressMessage tpfo_nameProject "\nModule: " (name i)
			        if (progressCancelled) {
			            if (confirm("Exit?")) {
			                progressStop
							tpfo_CompletionMessage += "\nCancelled\n"
			                return true
			            }
			        }
			        if (!isInCache(uniqueID i)) {
			        	Module currentModule = current
			        	sOMI = ""
			        	omi_getOMIForAModule(i, sOMI)
			        	Module m
						bool needToEditModule = false
						if (length(sOMI) >  0) {
							if(isOMIInCache(sOMI)) {
								needToEditModule = true
							}
						}
			        	if (!needToEditModule) {
							tpfo_CompletionMessage += "\t|          |     X    |     X    |          | " (fullName i) "\n"
			        		m = read((fullName i), false, true)
							if (m != null) {
								close(m)
							}
							if (!blank && !(open module tpfo_nameOfCacheModule)) {
								tpfo_ModuleOfCache = edit(tpfo_nameOfCacheModule, false, true, true)
							}
			        	} else {
							Module m = edit((fullName i), false, true, true)
							if (m != null) {
								tpfo_CompletionMessage += "\t|          |          |     X    |     X    | " (fullName i) "\n"
								save(m)
								close(m)
							} else {
								tpfo_CompletionMessage += "\t|          |          |          |          | impossible to edit module " (fullName i) "\n"
							}
							if (!blank && !(open module tpfo_nameOfCacheModule)) {
								tpfo_ModuleOfCache = edit(tpfo_nameOfCacheModule, false, true, true)
							}
			        	}
						if (!null currentModule) {
							current = currentModule
						}
			        } else {
						tpfo_CompletionMessage += "\t|     X    |     X    |          |          | " (fullName i) "\n"
					}
					progressStep tpfo_nbItemToTagged++
				}
			} else if (type(i) == CFolder && name(i) != CRepertoireConfigName && name(i) != COrchestraInstallationFolderName) {
				blnCancel = tagProject(folder i, blank)
				if (blnCancel) {
					return true
				}
			}
		}
		return blnCancel
	}
	
	void TagList(bool blank) {
		string projName = ""
	    Item p
		for projName in tpfo_skpProjectList do {
			tpfo_CompletionMessage += "Start to tag project " projName "\n"
			p = item projName
			if (!blank) {
				tpfo_nameOfCacheModule = projName "/Orchestra Configuration/Orchestra Cache " getDatabaseIdentifier() "." uniqueID(p)
				tpfo_ModuleOfCache = edit(tpfo_nameOfCacheModule, false, true, true)
			}
			tpfo_nameProject = "Tag project " projName
			progressMessage tpfo_nameProject
			if (null p) {
				break
			} else {
				if (!blank && !tpfo_CreateCacheModule(p)) {
					tpfo_CompletionMessage += "End tag project " projName "\n"
					continue
				}
				if (!blank && !tpfo_CreateTrigger(p)) {
					tpfo_CompletionMessage += "End tag project " projName "\n"
					continue
				}
				tpfo_CompletionMessage += "\t---------------------------------------------------------------------------------------\n"
				tpfo_CompletionMessage += "\t|       Already       |    Put   |          |\n"
				tpfo_CompletionMessage += "\t| in cache |  tagged  | in cache |  Tagged  | Name of module\n"
				tpfo_CompletionMessage += "\t---------------------------------------------------------------------------------------\n"
				// open the project cache module
				if (tagProject(folder p, blank)) {
					break
				}
			}
			if (!blank && tpfo_ModuleOfCache != null && open module tpfo_nameOfCacheModule) {
				save(tpfo_ModuleOfCache)
				close(tpfo_ModuleOfCache)
			}
			tpfo_CompletionMessage += "\t---------------------------------------------------------------------------------------\n"
			tpfo_CompletionMessage += "End tag project " projName "\n"
		}
	}
	
	// traitement de la liste de retour
	// conversion au format Orchestra Module Id des fichiers dont
	// on a coché les projets ... 
	void tagProgress(DB x) {
		tpfo_CompletionMessage = create
		TagList(true)
		setempty(tpfo_CompletionMessage)
		hide x
		progressStart(x, "Tag project for Orchestra", "", tpfo_nbItemToTagged)
		tpfo_nbItemToTagged = 0
		TagList(false)
		// clean memory
		delete tpfo_skpProjectList
		progressStop
		destroy x
		if (length(tpfo_CompletionMessage) > 0) {
			string sPath
			sPath = tempFileName()..".log"
			Stream outLog = write sPath
			outLog << tpfo_CompletionMessage
			close(outLog)
			DB RIDbox = create "Log" 
			sPath = "Log file: "..sPath
			DBE response = text(RIDbox, sPath, stringOf(tpfo_CompletionMessage), 200, true) 
			show RIDbox
		}
		delete(tpfo_CompletionMessage)
	}
	
	DB progressDB = create ("Tag project for Orchestra", styleCentered|styleFixed)
	label(progressDB, "Selected DOORS Projects will be declared within Orchestra.")
	apply(progressDB, "Confirm", tagProgress)
	show progressDB 
}

