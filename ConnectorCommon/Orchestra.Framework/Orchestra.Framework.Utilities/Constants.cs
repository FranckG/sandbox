// ----------------------------------------------------------------------------------------------------
// File Name: Constants.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2013. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework
{
    using System.Collections.Generic;

    /// <summary>
    /// Constants used by the Framework
    /// </summary>
    public static class Constants
    {
        #region Nested type: Parameters
        /// <summary>
        /// Parameters of Artefact (Uri)
        /// </summary>
        public static class Parameters
        {
            /// <summary>
            /// physical of the container of artefact
            /// </summary>
            public const string PhysicalPath = "physicalPath";

            /// <summary>
            /// environment id of artefact
            /// </summary>
            public const string EnvironmentId = "environmentId";
        }
        #endregion

        #region Nested type: Services
        #region ServiceEnum enum
        ///<summary>
        /// Enumeration of defined services
        ///</summary>
        public enum ServiceEnum
        {
            ///<summary>
            /// Navigate service
            ///</summary>
            Navigate,

            ///<summary>
            /// Documentary Export service
            ///</summary>
            DocumentaryExport,

            ///<summary>
            /// Link Manager Export service
            ///</summary>
            LinkManagerExport,

            ///<summary>
            /// Expand service
            ///</summary>
            Expand,

            ///<summary>
            /// Create service
            ///</summary>
            Create,

            ///<summary>
            /// Search service
            ///</summary>
            Search
        }
        #endregion

        /// <summary>
        /// Class Services
        /// </summary>
        public static class Services
        {
            /// <summary>
            /// Correspondance between the <see cref="ServiceEnum"/> and the name of the service
            /// </summary>
            public static Dictionary<ServiceEnum, string> ServicesName = new Dictionary<ServiceEnum, string>
                {
                    { ServiceEnum.Navigate, "navigate" },
                    { ServiceEnum.Create, "create" },
                    { ServiceEnum.DocumentaryExport, "documentaryExport" },
                    { ServiceEnum.Expand, "expand" },
                    { ServiceEnum.LinkManagerExport, "lmExport" },
                    { ServiceEnum.Search, "search" }
                };
        }
        #endregion

        #region Nested type: Segment
        /// <summary>
        /// Segment name
        /// </summary>
        public static class Segment
        {
            /// <summary>
            /// Gold Segment name
            /// </summary>
            public const string Gold = "Gold";

            /// <summary>
            /// Silver Segment name
            /// </summary>
            public const string Silver = "Silver";

            /// <summary>
            /// Bronze Segment name
            /// </summary>
            public const string Bronze = "Bronze";
        }
        #endregion

    }
}