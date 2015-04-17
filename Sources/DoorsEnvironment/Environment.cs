// ----------------------------------------------------------------------------------------------------
// File Name: Environment.cs
// Project: DoorsEnvironment
// Copyright (c) Thales, 2013 - 2013. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Environment.Doors
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Runtime.InteropServices;
    using System.Xml.Linq;
    using Orchestra.Framework.Client;
    using Orchestra.Framework.Core;
    using Orchestra.Framework.Utilities;

    [ComVisible(true)]
    [Guid("F7FA2506-FE64-48E5-BB09-C6D2A975C5EA")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IEnvironment
    {
        /// <summary>
        /// Finds the root name for a project id.
        /// </summary>
        /// <param name="projectId">The project id.</param>
        /// <returns>the root name.</returns>
        [DispId(1)]
        string FindRootNameForAProjectId(string projectId);
    }

    [ComVisible(true)]
    [Guid("50276606-B330-4646-84A1-7A04ED10B256")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("Orchestra.EnvironmentDoors")]
    public class Environment : IEnvironment
    {
        private const string DoorsEnvironmentPort = "doors_environment_port";
        private const string DoorsEnvironmentDbName = "doors_environment_db_name";
        private const string DoorsProjects = "doors_projects";
        private const string DoorsBaselineset = "doors_baselineset_";
        private const string DoorsParameters = "doors_environment_parameters";
        private const string DoorsEnvironmentAutologinValue = "doors_environment_autologin_value";

        public readonly Dictionary<string, Dictionary<string, DoorsDatabase>> List;
        private bool _populated;

        /// <summary>
        /// Initializes a new instance of the <see cref="Environment"/> class.
        /// </summary>
        public Environment()
        {
            this.List = new Dictionary<string, Dictionary<string, DoorsDatabase>>();
        }

        #region IEnvironment Members
        /// <summary>
        /// Finds the root name for a project id.
        /// </summary>
        /// <param name="projectId">The project id.</param>
        /// <returns>the root name.</returns>
        public string FindRootNameForAProjectId(string projectId)
        {
            bool projectFound = false;
            string rootName = "";
            this.Populate();

            foreach (KeyValuePair<string, Dictionary<string, DoorsDatabase>> keyValuePair in this.List)
            {
                foreach (KeyValuePair<string, DoorsDatabase> doorsDatabase in keyValuePair.Value)
                {
                    foreach (KeyValuePair<string, DoorsProject> doorsProject in doorsDatabase.Value.Projects)
                    {
                        if (doorsProject.Value.Id.Equals(projectId))
                        {
                            rootName = doorsProject.Value.RootName;
                            projectFound = true;
                            break;
                        }
                    }
                    if (projectFound)
                    {
                        break;
                    }
                }
                if (projectFound)
                {
                    break;
                }
            }
            return rootName;
        }
        #endregion

        /// <summary>
        /// Adds the specified environment describes in the Gef file.
        /// </summary>
        /// <param name="environmentId">The environment id.</param>
        /// <param name="gef">The gef file.</param>
        public void Add(string environmentId, GEF gef)
        {
            DoorsDatabase doorsDatabase = new DoorsDatabase();
            foreach (Element project in gef.GENERIC_EXPORT_FORMAT)
            {
                Artefact rootArtefact = new Artefact { Uri = project.Uri };
                DoorsProject doorsProject = new DoorsProject { RootName = rootArtefact.RootName };
                foreach (object item in project.Items)
                {
                    Properties properties = item as Properties;
                    if (properties != null)
                    {
                        foreach (Property property in properties.Property.OfType<Property>())
                        {
                            switch (property.Name)
                            {
                                case "database":
                                    doorsProject.Database = property.Text[0];
                                    doorsDatabase.Id = doorsProject.Database;
                                    break;
                                case "projectId":
                                    doorsProject.Id = property.Text[0];
                                    break;
                            }
                        }
                    }
                    else
                    {
                        var element = item as Element;
                        if (element != null)
                        {
                            Artefact baselineSet = new Artefact { Uri = element.Uri };
                            DoorsBaselineSet doorsBaselineSet = new DoorsBaselineSet(baselineSet.Id);
                            if (doorsProject.Items == null)
                            {
                                doorsProject.Items = new Dictionary<string, DoorsItem>();
                            }
                            if (doorsBaselineSet.IsValid && !doorsProject.Items.ContainsKey(doorsBaselineSet.Id))
                            {
                                doorsProject.Items.Add(doorsBaselineSet.Id, doorsBaselineSet);
                            }
                        }
                    }
                }
                if (doorsDatabase.Projects == null)
                {
                    doorsDatabase.Projects = new Dictionary<string, DoorsProject>();
                }
                if (!string.IsNullOrEmpty(doorsProject.RootName)
                    && !doorsDatabase.Projects.ContainsKey(doorsProject.RootName))
                {
                    doorsDatabase.Projects.Add(doorsProject.RootName, doorsProject);
                }
            }
            if (!this.IsExists(environmentId))
            {
                this.List.Add(environmentId, new Dictionary<string, DoorsDatabase>());
            }
            if (doorsDatabase.Id != null)
            {
                if (this.List[environmentId].ContainsKey(doorsDatabase.Id))
                {
                    foreach (KeyValuePair<string, DoorsProject> project in doorsDatabase.Projects)
                    {
                        if (this.List[environmentId][doorsDatabase.Id].Projects.ContainsKey(project.Key))
                        {
                            this.List[environmentId][doorsDatabase.Id].Projects[project.Key] = project.Value;
                        }
                        else
                        {
                            this.List[environmentId][doorsDatabase.Id].Projects.Add(project.Key, project.Value);
                        }
                    }
                }
                else
                {
                    this.List[environmentId].Add(doorsDatabase.Id, doorsDatabase);
                }
            }
        }

        /// <summary>
        /// Adds the specified environment id.
        /// </summary>
        /// <param name="environmentId">The environment id.</param>
        /// <param name="environmentProperties">The environment properties.</param>
        public void Add(string environmentId, EnvironmentProperty[] environmentProperties)
        {
            Dictionary<string, string> listOfProperties = environmentProperties.ToDictionary(
                property => property.name, property => property.value);
            bool autologin = false;
            string autologinValue;
            if (listOfProperties.TryGetValue(DoorsEnvironmentAutologinValue, out autologinValue))
            {
                if (autologinValue != null)
                {
                    autologin = autologinValue.Equals("true", StringComparison.InvariantCultureIgnoreCase);
                }
            }

            DoorsDatabase doorsDatabase = new DoorsDatabase
                {
                    Id =
                        OrchestraEncoding.SDecodeString(
                            listOfProperties[DoorsEnvironmentPort] + "@" + listOfProperties[DoorsEnvironmentDbName]),
                    LaunchParameters = OrchestraEncoding.SDecodeString(listOfProperties[DoorsParameters]),
                    AutoLogin = autologin
                };
            listOfProperties.Remove(DoorsEnvironmentPort);
            listOfProperties.Remove(DoorsEnvironmentDbName);
            listOfProperties.Remove(DoorsEnvironmentAutologinValue);

            if(!string.IsNullOrEmpty(listOfProperties[DoorsProjects])) {
                string[] projects = listOfProperties[DoorsProjects].Split(',');           
                listOfProperties.Remove(DoorsProjects);
            
                foreach (string project in projects)
                {
                    string[] s = project.Split(';');
                    DoorsProject doorsProject = new DoorsProject(
                        doorsDatabase.Id, OrchestraEncoding.SDecodeString(s[0]), OrchestraEncoding.SDecodeString(s[2]));
                    string propertyNameForBaselineSet = DoorsBaselineset + doorsProject.Id;
                    if (listOfProperties.ContainsKey(propertyNameForBaselineSet))
                    {
                        string[] baselinesSet = listOfProperties[propertyNameForBaselineSet].Split(',');
                        foreach (string oneBaselineSet in baselinesSet)
                        {
                            string uri = oneBaselineSet.Substring(0, oneBaselineSet.LastIndexOf(';'));
                            Artefact baselineSet = new Artefact { Uri = OrchestraEncoding.SDecodeString(uri) };
                            DoorsBaselineSet doorsBaselineSet = new DoorsBaselineSet(baselineSet.Id);
                            if (doorsProject.Items == null)
                            {
                                doorsProject.Items = new Dictionary<string, DoorsItem>();
                            }
                            if (doorsBaselineSet.IsValid && !doorsProject.Items.ContainsKey(doorsBaselineSet.Id))
                            {
                                doorsProject.Items.Add(doorsBaselineSet.Id, doorsBaselineSet);
                            }
                        }
                    }
                    if (doorsDatabase.Projects == null)
                    {
                        doorsDatabase.Projects = new Dictionary<string, DoorsProject>();
                    }
                    if (!string.IsNullOrEmpty(doorsProject.RootName)
                        && !doorsDatabase.Projects.ContainsKey(doorsProject.RootName))
                    {
                        doorsDatabase.Projects.Add(doorsProject.RootName, doorsProject);
                    }
                }
            }

            if (!this.IsExists(environmentId))
            {
                this.List.Add(environmentId, new Dictionary<string, DoorsDatabase>());
            }
            if (doorsDatabase.Id != null)
            {
                if (this.List[environmentId].ContainsKey(doorsDatabase.Id))
                {
                    foreach (KeyValuePair<string, DoorsProject> project in doorsDatabase.Projects)
                    {
                        if (this.List[environmentId][doorsDatabase.Id].Projects.ContainsKey(project.Key))
                        {
                            this.List[environmentId][doorsDatabase.Id].Projects[project.Key] = project.Value;
                        }
                        else
                        {
                            this.List[environmentId][doorsDatabase.Id].Projects.Add(project.Key, project.Value);
                        }
                    }
                }
                else
                {
                    this.List[environmentId].Add(doorsDatabase.Id, doorsDatabase);
                }
            }
        }

        /// <summary>
        /// Determines whether the specified environment id is exists.
        /// </summary>
        /// <param name="environmentId">The environment id.</param>
        /// <returns>
        ///   <c>true</c> if the specified environment id is exists; otherwise, <c>false</c>.
        /// </returns>
        public bool IsExists(string environmentId)
        {
            return this.List.ContainsKey(environmentId);
        }

        /// <summary>
        /// Determines whether the specified environment id is exists.
        /// </summary>
        /// <param name="environmentId">The environment id.</param>
        /// <param name="rootName">Name of the root.</param>
        /// <returns>
        ///   <c>true</c> if the specified environment id is exists; otherwise, <c>false</c>.
        /// </returns>
        public bool IsExists(string environmentId, string rootName)
        {
            if (this.List.ContainsKey(environmentId))
            {
                return
                    this.List[environmentId].Any(
                        doorsDatabase =>
                        doorsDatabase.Value.Projects.Any(doorsProject => doorsProject.Value.RootName.Equals(rootName)));
            }
            return false;
        }

        /// <summary>
        /// Prepares the artefact for DXL.
        /// </summary>
        /// <param name="artefact">The artefact.</param>
        /// <param name="artefacts">The artefacts.</param>
        public void PrepareArtefactForDxl(Artefact artefact, ref Dictionary<string, List<Artefact>> artefacts)
        {
            Dictionary<string, DoorsDatabase> doorsDatabases = this.List[artefact.environmentId];
            foreach (KeyValuePair<string, DoorsDatabase> doorsDatabase in doorsDatabases)
            {
                if (!artefacts.ContainsKey(doorsDatabase.Value.Id))
                {
                    artefacts.Add(doorsDatabase.Value.Id, new List<Artefact>());
                }
                artefacts[doorsDatabase.Value.Id].Add(artefact);
                break;
            }
        }

        /// <summary>
        /// Populates this instance.
        /// </summary>
        private void Populate()
        {
            if (this._populated)
            {
                return;
            }
            this._populated = true;
            OrchestraClient client = new OrchestraClient();
            IOrchestraResponse response = client.getRootArtifacts();
            if (response != null)
            {
                string gefFilePath = response.GetGefFileFullPath();
                if (!string.IsNullOrEmpty(gefFilePath))
                {
                    XElement gef = XElement.Load(gefFilePath);
                    IEnumerable<XElement> rootArtefactsDoors = from el in gef.Descendants("ELEMENT")
                                                               where
                                                                   ((string)el.Attribute("uri")).Contains(
                                                                       "orchestra://Doors/")
                                                               select el;
                    List<string> artefactsInEnvironmentDoors = new List<string>();
                    foreach (XElement element in rootArtefactsDoors)
                    {
                        XAttribute xAttribute = element.Attribute("uri");
                        if (xAttribute == null)
                        {
                            continue;
                        }
                        string uri = xAttribute.Value;
                        artefactsInEnvironmentDoors.Add(uri);
                    }
                    if (artefactsInEnvironmentDoors.Count > 0)
                    {
                        string[] uris = artefactsInEnvironmentDoors.ToArray();
                        EnvironmentMetadata environmentMetadata = new EnvironmentMetadata(uris);
                        if (environmentMetadata.Properties != null)
                        {
                            Add("Doors", environmentMetadata.Properties);
                        }
                    }
                }
            }
        }
    }
}