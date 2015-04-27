// ----------------------------------------------------------------------------------------------------
// File Name: IStatus.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework
{
    using System.Runtime.InteropServices;

    ///<summary>
    /// COM Interface for Status Definition Management
    ///</summary>
    [ComVisible(true)]
    [Guid("7DED4218-2955-4FC3-9E8E-8FF703180EAC")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IStatusDefinition
    {
        ///<summary>
        /// Serialize the Status Definition
        ///</summary>
        ///<returns>Status Definition serialized in XML.</returns>
        string ToString();

        ///<summary>
        /// Returns the Root Status (See <see cref="Status"/>)
        ///</summary>
        Status status { get; }

        ///<summary>
        /// Deserialize a Status Definition
        ///</summary>
        ///<param name="s"></param>
        void SetStatusDefinition(string s);

        ///<summary>
        /// Add a new <see cref="Status"/> in StatusDefinition
        ///</summary>
        ///<param name="statusSeverity">See <see cref="StatusSeverity"/></param>
        ///<param name="uri">Artefact Uri</param>
        ///<param name="message">Message</param>
        ///<param name="code">Code</param>
        ///<param name="codeSpecified"><c>true</c> if <paramref name="code"/> is specified otherwise <c>false</c></param>
        ///<param name="parentStatus">parent Status (see <see cref="Status"/>)</param>
        ///<returns>The added <see cref="Status"/></returns>
        Status WriteStatus(
            StatusSeverity statusSeverity, string uri, string message, int code, bool codeSpecified, Status parentStatus);

        ///<summary>
        /// Finds all statuses with uri attribute equals to <paramref name="uriToFound"/>.
        ///</summary>
        ///<param name="uriToFound">Uri to found.</param>
        ///<returns>Count of statuses founded.</returns>
        int FindStatusesByUri(string uriToFound);

        ///<summary>
        /// Gets Status at <paramref name="index"/>.
        /// <para>
        /// This method must be called after <see cref="FindStatusesByUri"/>
        /// </para>
        ///</summary>
        ///<param name="index">Index.</param>
        ///<returns>Status at <paramref name="index"/>, returns null if there is no status at <paramref name="index"/>.</returns>
        Status GetStatusByUriAtIndex(int index);
    }

    ///<summary>
    /// COM Interface for Orchestra Status
    ///</summary>
    [ComVisible(true)]
    [Guid("4069D17E-2821-4654-AB84-7E71FAE79728")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IStatus
    {
        ///<summary>
        /// Add a child status
        ///</summary>
        ///<param name="s">Status to add</param>
        void AddStatus(Status s);

        ///<summary>
        /// Property to get or set the <see cref="StatusSeverity"/>
        ///</summary>
        StatusSeverity severity { get; set; }

        ///<summary>
        /// Property to get or set message
        ///</summary>
        string message { get; set; }

        ///<summary>
        /// Property to get or set the Orchestra Uri
        ///</summary>
        string uri { get; set; }

        ///<summary>
        /// Property to get or set the code
        ///</summary>
        int code { get; set; }

        ///<summary>
        /// Property to get or set the full path of the .gef file
        ///</summary>
        string exportFilePath { get; set; }

        ///<summary>
        /// Property to get the count of children statuses
        ///</summary>
        int ChildCount { get; }

        ///<summary>
        /// Returns the child Status at <paramref name="index"/>
        ///</summary>
        ///<param name="index">index of child status</param>
        ///<returns>Status at <paramref name="index"/></returns>
        Status GetChildStatus(int index);
    }
}