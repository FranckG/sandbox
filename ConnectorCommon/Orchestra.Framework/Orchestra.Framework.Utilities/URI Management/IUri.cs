// ----------------------------------------------------------------------------------------------------
// File Name: IUri.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities
{
    using System.Runtime.InteropServices;
    using Orchestra.Framework.Core;

    ///<summary>
    /// COM Interface for Uri Management
    ///</summary>
    [ComVisible(true)]
    [Guid("A6BBA6A2-4E20-4329-8E9D-D09FBBF08955")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IUri
    {
        ///<summary>
        /// Gets or sets the Uri for the current <see cref="Artefact"/> object. 
        ///</summary>
        string Uri { get; set; }

        ///<summary>
        /// Sets the Uri for the current <see cref="Artefact"/> object.
        ///</summary>
        ///<param name="uri"></param>
        void SetUri(string uri);

        ///<summary>
        /// Sets the Uri for the current <see cref="Artefact"/> object.
        ///</summary>
        ///<param name="rootType">The logical type for the current <see cref="Artefact"/> object.</param>
        ///<param name="rootName">The Logical name for the current <see cref="Artefact"/> object.</param>
        ///<param name="type">The type for the current <see cref="Artefact"/> object.</param>
        ///<param name="id">The identifier for the current <see cref="Artefact"/> object.</param>
        ///<param name="formattedParameters">Parameters for the current <see cref="Artefact"/> object.
        /// <para>
        /// Parameters string must be encoded according RFC 3986.
        /// </para>
        /// <example>
        /// <c>viewName=10%2E%20Document%20view</c>
        /// </example>
        /// </param>
        void SetUriValues(string rootType, string rootName, string type, string id, string formattedParameters);

        ///<summary>
        /// Gets or sets the command for the current <see cref="Artefact"/> object.
        ///</summary>
        string Command { get; set; }

        ///<summary>
        /// Gets or sets Root name (or logical name) for the current <see cref="Artefact"/> object.
        ///</summary>
        string RootName { get; set; }

        ///<summary>
        /// Gets or sets RootType for the current <see cref="Artefact"/> object.
        ///</summary>
        string RootType { get; set; }

        ///<summary>
        /// Gets or sets identifier for the current <see cref="Artefact"/> object.
        ///</summary>
        string Id { get; set; }

        ///<summary>
        /// Gets or sets type for the current <see cref="Artefact"/> object. 
        ///</summary>
        string Type { get; set; }

        ///<summary>
        /// Gets parameters (formatted and encoded) for the current <see cref="Artefact"/> object.
        ///</summary>
        string GetFormattedParameters { get; }

        ///<summary>
        /// Clear parameters for the current <see cref="Artefact"/> object.
        ///</summary>
        void ClearParameters();

        ///<summary>
        /// Adds new parameter for the current <see cref="Artefact"/> object.
        ///</summary>
        ///<param name="name"></param>
        ///<param name="value"></param>
        ///<returns><c>true</c> if the parameter is added to the current <see cref="Artefact"/> object; otherwise, <c>false</c>. </returns>
        bool AddParameter(string name, string value);

        ///<summary>
        /// Determines whether the current <see cref="Artefact"/> object contains the specified parameter. 
        ///</summary>
        ///<param name="name"></param>
        ///<returns><c>true</c> if the current <see cref="Artefact"/> object contains a parameter with the specified name; otherwise, <c>false</c>. </returns>
        bool ExistParameter(string name);

        ///<summary>
        /// Retrieve parameter <paramref name="name"/>
        ///</summary>
        ///<param name="name">parameter name to retrieve</param>
        ///<returns>value of parameter <paramref name="name"/>
        /// Raise en error if <paramref name="name"/> doesn't exist</returns>
        string Parameter(string name);

        ///<summary>
        /// Gets Artefact absolute Uri
        ///</summary>
        string AbsoluteUri { get; }

        ///<summary>
        /// Gets the physical path of the root artifact for the current <see cref="Artefact"/> object.
        ///</summary>
        string RootPhysicalPath { get; }

        ///<summary>
        ///  Gets parameters (not encoded) for the current <see cref="Artefact"/> object.
        ///</summary>
        ///<returns>
        /// parameters (not encoded) for the current <see cref="Artefact"/> object.
        /// <para>
        /// <example>
        /// viewName=10. Document view
        /// </example>
        /// </para>
        /// </returns>
        string GetParametersLabel();

        ///<summary>
        /// Indicates whether the current <see cref="Artefact"/> object is a project (a root artefact). 
        ///</summary>
        ///<returns><c>true</c> if the current <see cref="Artefact"/> object is a project; otherwise, <c>false</c>.</returns>
        bool IsProject();
    }
}