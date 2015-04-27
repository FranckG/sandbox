regasm Orchestra.Framework.Artefact.dll /tlb
gacutil /i Orchestra.Framework.Artefact.dll
gacutil /i policy.1.3.Orchestra.Framework.Artefact.dll
gacutil /i policy.1.4.Orchestra.Framework.Artefact.dll

regasm Orchestra.Framework.Utilities.dll /tlb
gacutil /i Orchestra.Framework.Utilities.dll
gacutil /i policy.1.3.Orchestra.Framework.Utilities.dll

regasm Orchestra.Framework.VariableManager.dll /tlb
gacutil /i Orchestra.Framework.VariableManager.dll
gacutil /i policy.1.2.Orchestra.Framework.VariableManager.dll
gacutil /i policy.1.3.Orchestra.Framework.VariableManager.dll

gacutil /i Orchestra.Framework.VariableManager.XmlSerializers.dll

gacutil /i Orchestra.Framework.Connector.Common.dll
gacutil /i policy.1.0.Orchestra.Framework.Connector.Common.dll