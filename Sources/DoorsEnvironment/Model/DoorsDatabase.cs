// ----------------------------------------------------------------------------------------------------
// File Name: DoorsDatabase.cs
// Project: DoorsEnvironment
// Copyright (c) Thales, 2013 - 2013. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Environment.Doors
{
    using System.Collections.Generic;

    public class DoorsDatabase
    {
        /// <summary>
        /// Gets or sets the id of the database.
        /// </summary>
        /// <example>36677@192.168.1.0</example>
        /// <value>
        /// The id.
        /// </value>
        public string Id { get; set; }

        /// <summary>
        /// Gets or sets the projects.
        /// </summary>
        /// <value>
        /// The projects.
        /// </value>
        public Dictionary<string, DoorsProject> Projects { get; set; }

        /// <summary>
        /// Gets or sets the launch parameters.
        /// </summary>
        /// <value>
        /// The launch parameters.
        /// </value>
        public string LaunchParameters { get; set; }

        public bool AutoLogin { get; set; }
    }
}