// ----------------------------------------------------------------------------------------------------
// File Name: IConnectorDoors.cs
// Project: DoorsConnector
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Connector.Doors
{
    using System.Runtime.InteropServices;

    [ComVisible(true)]
    [Guid(Connector.InterfaceId)]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IConnectorDoors
    {
        /// <summary>
        /// Creates a root artefact.
        /// </summary>
        /// <param name="context">The context string.</param>
        /// <returns>The status</returns>
        string create(string context);

        /// <summary>
        /// Generates a documentary export.
        /// </summary>
        /// <param name="context">The context string.</param>
        /// <returns>The status</returns>
        string documentaryExport(string context);

        /// <summary>
        /// Executes the specific command.
        /// </summary>
        /// <param name="context">The context string.</param>
        /// <returns>The status</returns>
        string executeSpecificCommand(string context);

        /// <summary>
        /// Expands artefacts.
        /// </summary>
        /// <param name="context">The context string.</param>
        /// <returns>The status</returns>
        string expand(string context);

        /// <summary>
        /// Generates an export for the link manager.
        /// </summary>
        /// <param name="context">The context string.</param>
        /// <returns>The status</returns>
        string lmExport(string context);

        /// <summary>
        /// Navigates to artefacts.
        /// </summary>
        /// <param name="context">The context string.</param>
        /// <returns>The status</returns>
        string navigate(string context);

        /// <summary>
        /// Searches the specified context string.
        /// </summary>
        /// <param name="context">The context string.</param>
        /// <returns>The status</returns>
        string search(string context);

        /// <summary>
        /// Reinitializes the doors login.
        /// </summary>
        /// <param name="doorsDatabase">The doors database address.</param>
        void ReinitializeDoorsLogin(string doorsDatabase);
    }
}