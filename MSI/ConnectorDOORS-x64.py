from msigenlib.objects import *

doc = r"\\nas-ewd.orchestra\integ\Deliveries\delivery_Documentation\ConnectorDOORS\${ORCHESTRA_VERSION}"

class SetFeature(AdvancedInstallerCommand):
  def __init__(self, feature_id):
    self.feature_id = feature_id

  def get_command_str(self):
    return "SetCurrentFeature %s" % self.feature_id 

if os.environ.has_key('DIST'):
    dist = '${DIST}'
else:
    dist = r'${WORKSPACE}\dist'

actions = [
  #--------------------#
  # PRE-BUILD COMMANDS #
  #--------------------#
  CleanFolder(dist),
	CleanFolder(r'${WORKSPACE}\package'),
	ExpandArchive(r'\\TC1\msideliver\ConnectorDoors\v${ORCHESTRA_VERSION}\ConnectorDoors_generation-*.zip', r'${WORKSPACE}\package\generation'),
	ExpandArchive(r'\\TC1\msideliver\ConnectorDoors\v${ORCHESTRA_VERSION}\ConnectorDoors_auto*.zip', r'${WORKSPACE}\package\auto_assembly'),
	ExpandArchive(r'\\TC1\msideliver\ConnectorDoors\v${ORCHESTRA_VERSION}\ConnectorDoors_context_contribution*.zip', r'${WORKSPACE}\package\context_contribution'),
	ExpandArchive(r'\\TC1\msideliver\ConnectorDoors\v${ORCHESTRA_VERSION}\ConnectorDoors_environment*.zip', r'${WORKSPACE}\package\environment'),
	ExpandArchive(r'\\TC1\msideliver\ConnectorDoors\v${ORCHESTRA_VERSION}\javadoc.tar.gz', r'${WORKSPACE}\package\doc\APIs'),
	
  #----------------------#
  # MANDATORY PROPERTIES #
  #----------------------#
  SetVersion("${VERSION}"),
  SetProperty("ProductName", "ConnectorDOORS"),
  SetProperty("BuildNumber", "${TIMESTAMP}"),
	SetProperty("Category", "Requirement Management"),

  #-----------------#
  # FILES & FOLDERS #
  #-----------------#
  CopyFolder(r"${WORKSPACE}\ConnectorHome\Doors", r"${WORKSPACE}\package\ConnectorHome\Doors"),
  CopyFolder(r"${WORKSPACE}\ConnectorHome\orcConfig", r"${WORKSPACE}\package\ConnectorHome\orcConfig"),
  CopyFolder(r"${WORKSPACE}\ConnectorHome\resources", r"${WORKSPACE}\package\ConnectorHome\resources"),

  AddFolder(r"${WORKSPACE}\package\ConnectorHome\Doors", "APPDIR"),
  AddFolder(r"${WORKSPACE}\package\ConnectorHome\orcConfig", "APPDIR"),
  AddFolder(r"${WORKSPACE}\package\ConnectorHome\resources", "APPDIR"),
	AddFolder(r"${WORKSPACE}\package\doc\APIs\Mozart","APPDIR\doc\APIs"),
	AddFolder(r'${WORKSPACE}\package\generation\eclipse', "SHAREDDIRECTORY\Mozart\Generation"),
	AddFolder(r'${WORKSPACE}\package\context_contribution\eclipse', "ORC_FMK_INSTALL_DIR\shared\context.contributions"),
	AddFolder(r'${WORKSPACE}\package\environment\eclipse', "ORC_FMK_INSTALL_DIR\shared\environment.contributions"),
	AddFolder(r'${WORKSPACE}\package\auto_assembly\eclipse', "SHAREDDIRECTORY\Mozart\AutomaticAssembly"),
  AddFile(doc + r'\SUM_Doors Custom.pdf', r"APPDIR\doc"),

  #-----------#
  # SHORTCUTS #
  #-----------#
  NewShortcut(name="ConnectorDOORS SUM", 
              dir="SHORTCUTDIR", 
              target=r"APPDIR\doc\SUM_Doors Custom.pdf",
              icon=r"${MSI_RESOURCES}\icons\PDFFile.ico"),

  #----------#
  # REGISTRY #
  #----------#
  NewRegValue(r"HKLM\Software\[Company]\[Division]\[Manufacturer]\Products\[ProductName]\iconPath", "[APPDIR]resources\images"),
	NewRegValue(r"HKLM\Software\[Company]\[Division]\[Manufacturer]\Products\[ProductName]\configConnector", "[APPDIR]orcConfig\Doors.connector"),
    
  #-------#
  # DOORS #
  #-------#
  SetFeature("Doors96"),
  AddFolder(r"${WORKSPACE}\DoorsHome\lib", "DOORS_HOME_96"),
  AddFile(r"${WORKSPACE}\DoorsHome\lib96\OrchestraDontShowLastOpenModules.dxl", r'DOORS_HOME_96\lib\dxl\startupFiles')
]
