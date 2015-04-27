// ----------------------------------------------------------------------------------------------------
// File Name: IContext.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Core
{
    using System.Runtime.InteropServices;

    ///<summary>
    /// COM Interface for Orchestra Framework Context Definition Management
    /// </summary>
    [ComVisible(true)]
    [Guid("D1D4CB24-7406-4623-8982-90ABDCFBEE73")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IContextDefinition
    {
        ///<summary>
        /// Deserializes the XML document contained by the specified <see cref="ContextDefinition"/>.
        ///</summary>
        ///<param name="contextString"></param>
        ///<returns><c>true</c> if no error, <c>false</c> otherwise.</returns>
        bool Load(string contextString);

        ///<summary>
        /// Serializes context definition
        ///</summary>
        ///<returns>A String that represents the current Object. </returns>
        string ToString();

        ///<summary>
        /// Gets the <see cref="IContext"/> for the current object.
        ///</summary>
        Context context { get; }
    }

    ///<summary>
    /// COM Interface for Orchestra Framework Context Management
    ///</summary>
    [ComVisible(true)]
    [Guid("D645DDF4-7132-48A9-AB4C-4D3DC2D3AD46")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IContext
    {
        ///<summary>
        /// Gets the <see cref="Artefact"/> at the specified index.
        ///</summary>
        ///<param name="index">The zero-based index of the element to get.</param>
        ///<returns>The <see cref="Artefact"/> at the specified index.</returns>
        Artefact artefacts(int index);

        ///<summary>
        /// Gets the number of <see cref="Artefact"/> actually contained in the <see cref="Context"/>.
        ///</summary>
        int artefactCount { get; }

        ///<summary>
        /// Gets the command name.
        ///</summary>
        string type { get; }

        ///<summary>
        /// Gets the Generic Export Format (GEF) result model file absolute path.
        ///</summary>
        string exportFilePath { get; }
    }
}