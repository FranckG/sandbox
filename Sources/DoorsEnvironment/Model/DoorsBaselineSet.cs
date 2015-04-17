// ----------------------------------------------------------------------------------------------------
// File Name: DoorsBaselineSet.cs
// Project: DoorsEnvironment
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Environment.Doors
{
    using System;

    public class DoorsBaselineSet : DoorsItem
    {
        private const string StringFormat =
            "baselineSet" + IdentifierSeparator + "{0}" + IdentifierSeparator + "{1}" + IdentifierSeparator + "{2}";

        /// <summary>
        /// Initializes a new instance of the <see cref="DoorsBaselineSet"/> class.
        /// </summary>
        /// <param name="id">The id.</param>
        public DoorsBaselineSet(string id)
        {
            string[] ids = id.Split(Convert.ToChar(IdentifierSeparator));
            if (ids.Length == 3)
            {
                this.FolderPath = ids[0];
                this.BaselineSetDefinition = ids[1];
                this.BaselineSet = ids[2];
                this.IsValid = true;
            }
            else
            {
                this.IsValid = false;
            }
        }

        /// <summary>
        /// Gets or sets the Doors folder path.
        /// </summary>
        /// <value>
        /// The Doors folder path.
        /// </value>
        public string FolderPath { get; set; }

        /// <summary>
        /// Gets or sets the baseline set definition.
        /// </summary>
        /// <value>
        /// The baseline set definition.
        /// </value>
        public string BaselineSetDefinition { get; set; }

        /// <summary>
        /// Gets or sets the baseline set.
        /// </summary>
        /// <value>
        /// The baseline set.
        /// </value>
        public string BaselineSet { get; set; }

        /// <summary>
        /// Returns a <see cref="System.String"/> that represents this instance.
        /// </summary>
        /// <returns>
        /// A <see cref="System.String"/> that represents this instance.
        /// </returns>
        public override string ToString()
        {
            return string.Format(StringFormat, this.FolderPath, this.BaselineSetDefinition, this.BaselineSet);
        }

        #region Overrides of DoorsItem
        /// <summary>
        /// Gets the id.
        /// </summary>
        public override string Id
        {
            get
            {
                return this.FolderPath + IdentifierSeparator + this.BaselineSetDefinition;
            }
            set
            {
            }
        }
        #endregion
    }
}