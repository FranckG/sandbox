// ----------------------------------------------------------------------------------------------------
// File Name: DoorsProject.cs
// Project: DoorsEnvironment
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Environment.Doors
{
    using System.Collections.Generic;

    /// <summary>
    /// 
    /// </summary>
    public class DoorsProject : DoorsItem
    {
        private const string StringFormat = "project" + IdentifierSeparator + "{0}" + IdentifierSeparator + "{1}";

        private string _id;

        /// <summary>
        /// Initializes a new instance of the <see cref="DoorsProject"/> class.
        /// </summary>
        public DoorsProject()
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="DoorsProject"/> class.
        /// </summary>
        /// <param name="databaseId">The database id.</param>
        /// <param name="projectId">The project id.</param>
        /// <param name="rootName">Name of the root.</param>
        public DoorsProject(string databaseId, string projectId, string rootName)
        {
            this.Database = databaseId;
            this._id = projectId;
            this.RootName = rootName;
        }

        /// <summary>
        /// Gets or sets the database.
        /// </summary>
        /// <example>36677@192.168.1.0</example>
        /// <value>
        /// The database.
        /// </value>
        public string Database { get; set; }

        /// <summary>
        /// Gets or sets the name of the root.
        /// </summary>
        /// <value>
        /// The name of the root.
        /// </value>
        public string RootName { get; set; }

        /// <summary>
        /// Gets the items.
        /// </summary>
        public Dictionary<string, DoorsItem> Items { get; internal set; }

        public override string ToString()
        {
            return string.Format(StringFormat, this.RootName, this.Id);
        }

        #region Overrides of DoorsItem
        public override string Id
        {
            get
            {
                return this._id;
            }
            set
            {
                this._id = value;
            }
        }
        #endregion
    }
}