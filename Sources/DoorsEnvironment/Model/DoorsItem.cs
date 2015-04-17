// ----------------------------------------------------------------------------------------------------
// File Name: DoorsItem.cs
// Project: DoorsEnvironment
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Environment.Doors
{
    public abstract class DoorsItem
    {
        protected const string IdentifierSeparator = "!";

        /// <summary>
        /// Gets or sets a value indicating whether this instance is valid.
        /// </summary>
        /// <value>
        ///   <c>true</c> if this instance is valid; otherwise, <c>false</c>.
        /// </value>
        public bool IsValid { get; protected set; }

        /// <summary>
        /// Gets the id.
        /// </summary>
        public abstract string Id { get; set; }
    }
}