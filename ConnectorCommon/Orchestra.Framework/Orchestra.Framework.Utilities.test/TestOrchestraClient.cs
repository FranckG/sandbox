// ----------------------------------------------------------------------------------------------------
// File Name: TestOrchestraClient.cs
// Project: Orchestra.Framework.Utilities.test
// Copyright (c) Thales, 2010 - 2015. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities.test
{
    using Microsoft.VisualStudio.TestTools.UnitTesting;
    using Orchestra.Framework.Client;

    [TestClass]
    public class TestOrchestraClient
    {
        private static OrchestraClient _client;

        [TestMethod]
        public void CreateArtefactSetUri()
        {
            string[] uris = new[] { "Doors" };
            IUri uri = _client.CreateArtefactSetUri("/", ref uris);
            Assert.AreEqual(uri.ToString(), "orchestra://FrameworkCommands/ASU?LogicalFolderPath=%2F&RootTypes=Doors");
            uris = new[] { "Doors", "Word" };
            uri = _client.CreateArtefactSetUri("/Projects/Framework", ref uris);
            Assert.AreEqual(uri.ToString(), "orchestra://FrameworkCommands/ASU?LogicalFolderPath=%2FProjects%2FFramework&RootTypes=Doors%7C%24%7CWord");
            uris = null;
            uri = _client.CreateArtefactSetUri("/Projects/Framework", ref uris);
            Assert.AreEqual(uri.ToString(), "orchestra://FrameworkCommands/ASU?LogicalFolderPath=%2FProjects%2FFramework");
        }

        [ClassInitialize]
        public static void Initialize(TestContext context)
        {
            _client = new OrchestraClient();
        }

        [ClassCleanup]
        public static void CleanUp()
        {
            _client = null;
        }
    }
}