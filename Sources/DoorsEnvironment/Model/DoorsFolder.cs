// ----------------------------------------------------------------------------------------------------
// File Name: DoorsFolder.cs
// Project: DoorsEnvironment
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Environment.Doors
{
    using System.Collections.Generic;
    using Orchestra.Framework.Core;

    internal class DoorsFolder : DoorsItem
    {
        public List<Artefact> Artefacts { get; set; }
    }
}