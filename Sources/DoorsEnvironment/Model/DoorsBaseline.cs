// ----------------------------------------------------------------------------------------------------
// File Name: DoorsBaseline.cs
// Project: DoorsEnvironment
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Environment.Doors
{
    public class DoorsBaseline : DoorsItem
    {
        private const string StringFormat = "module" + IdentifierSeparator + "{0}" + IdentifierSeparator + "{1}";

        /// <summary>
        /// Initializes a new instance of the <see cref="DoorsBaseline"/> class.
        /// </summary>
        /// <param name="id">The id.</param>
        public DoorsBaseline(string id)
        {
            string[] ids = id.Split(';');
            if (ids.Length == 2)
            {
                this.ModuleId = ids[0];
                this.Baseline = ids[1];
                this.IsValid = true;
            }
            else
            {
                this.IsValid = false;
            }
        }

        /// <summary>
        /// Gets or sets the module id.
        /// </summary>
        /// <value>
        /// The module id.
        /// </value>
        public string ModuleId { get; set; }

        /// <summary>
        /// Gets or sets the baseline.
        /// </summary>
        /// <value>
        /// The baseline.
        /// </value>
        public string Baseline { get; set; }

        /// <summary>
        /// Returns a <see cref="System.String"/> that represents this instance.
        /// </summary>
        /// <returns>
        /// A <see cref="System.String"/> that represents this instance.
        /// </returns>
        public override string ToString()
        {
            return string.Format(StringFormat, this.ModuleId, this.Baseline);
        }

        #region Overrides of DoorsItem
        /// <summary>
        /// Gets the id.
        /// </summary>
        public override string Id
        {
            get
            {
                return this.ModuleId;
            }
            set
            {
            }
        }
        #endregion
    }
}