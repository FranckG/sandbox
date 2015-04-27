al /link:"publisher 1.3.Utilities.config" /out:policy.1.3.Orchestra.Framework.Utilities.dll /keyfile:Orchestra.snk /v:1.3.9.0
al /link:"publisher 1.2.VariableManager.config" /out:policy.1.2.Orchestra.Framework.VariableManager.dll /keyfile:Orchestra.snk /v:1.3.1.0
al /link:"publisher 1.3.VariableManager.config" /out:policy.1.3.Orchestra.Framework.VariableManager.dll /keyfile:Orchestra.snk /v:1.3.1.0
al /link:"publisher 1.3.Artefact.config" /out:policy.1.3.Orchestra.Framework.Artefact.dll /keyfile:Orchestra.snk /v:1.4.0.0
al /link:"publisher 1.4.Artefact.config" /out:policy.1.4.Orchestra.Framework.Artefact.dll /keyfile:Orchestra.snk /v:1.4.0.0
al /link:"publisher 1.0.Connector.Common.config" /out:policy.1.0.Orchestra.Framework.Connector.Common.dll /keyfile:Orchestra.snk /v:1.0.0.0
sgen /compiler:/keyfile:Orchestra.snk Orchestra.Framework.VariableManager.dll /force
