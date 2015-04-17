// ----------------------------------------------------------------------------------------------------
// File Name: DoorsFile.cs
// Project: DoorsEnvironment
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Environment.Doors
{
    using System.Collections.Generic;
    using System.IO;

    public class DoorsFile
    {
        /// <summary>
        /// Gets the project.
        /// </summary>
        public DoorsProject Project { get; private set; }

        /// <summary>
        /// Reads the specified doors file path.
        /// </summary>
        /// <param name="doorsFilePath">The doors file path.</param>
        /// <param name="rootName">Name of the root.</param>
        public void Read(string doorsFilePath, string rootName)
        {
            StreamReader file = null;
            try
            {
                file = new StreamReader(doorsFilePath);
                string line = file.ReadLine();
                string database = null;
                string projectId = null;
                if (line != null)
                {
                    database = line.Replace(@"\", @"\\");
                }
                line = file.ReadLine();
                if (line != null)
                {
                    projectId = line;
                }
                if (database != null && projectId != null)
                {
                    this.Project = new DoorsProject(database, projectId, rootName);
                }
                while (!file.EndOfStream)
                {
                    line = file.ReadLine();
                    if (!string.IsNullOrEmpty(line) && line.Contains(";"))
                    {
                        if (this.Project.Items == null)
                        {
                            this.Project.Items = new Dictionary<string, DoorsItem>();
                        }
                        DoorsBaseline baseline = new DoorsBaseline(line);
                        if (baseline.IsValid && !this.Project.Items.ContainsKey(baseline.ModuleId))
                        {
                            this.Project.Items.Add(baseline.ModuleId, baseline);
                        }
                    }
                }
            }
            finally
            {
                if (file != null)
                {
                    file.Close();
                }
            }
        }
    }
}