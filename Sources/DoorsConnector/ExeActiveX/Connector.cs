// ----------------------------------------------------------------------------------------------------
// File Name: Connector.cs
// Project: RhapsodyConnector
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Connector.Doors
{
    using System;
    using System.ComponentModel;
    using System.Runtime.InteropServices;
    using CSExeComServer;

    public sealed partial class Connector : ReferenceCountedObject
    {
        [EditorBrowsable(EditorBrowsableState.Never)]
        [ComRegisterFunction]
        public static void Register(Type t)
        {
            try
            {
                ComHelper.RegasmRegisterLocalServer(t);
            }
            catch (Exception ex)
            {
                EventLogOrchestra.WriteError(61017, ex.Message, "ConnectorDoors");
                throw new ApplicationException("Error during register local server", ex); // Re-throw the exception
            }
        }

        [EditorBrowsable(EditorBrowsableState.Never)]
        [ComUnregisterFunction]
        public static void Unregister(Type t)
        {
            try
            {
                ComHelper.RegasmUnregisterLocalServer(t);
            }
            catch (Exception ex)
            {
                EventLogOrchestra.WriteError(61018, ex.Message, "ConnectorDoors");
                throw new ApplicationException("Error during unregister local server", ex); // Re-throw the exception
            }
        }
    }
}