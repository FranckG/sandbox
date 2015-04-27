// ----------------------------------------------------------------------------------------------------
// File Name: MultiRequest.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities.MultiRequest
{
    using System.Collections.Generic;
    using Orchestra.Framework.Core;

    ///<summary>
    /// Class to manage a list of artefact requested to a connector.
    ///</summary>
    public class MultiRequest
    {
        ///<summary>
        /// Gets or sets a List of <see cref="Artefact"/> in the <see cref="MultiRequest"/>.
        ///</summary>
        public List<Artefact> Artefacts { get; set; }

        ///<summary>
        /// Gets the List of <see cref="Artefact"/> in the same project of <paramref name="artefact"/>
        ///</summary>
        ///<param name="artefact"><see cref="Artefact"/> for comparison.</param>
        ///<returns>A List containing all the <see cref="Artefact"/> in the same project of <paramref name="artefact"/>, if found; otherwise, an empty List. </returns>
        public List<Artefact> ArtefactsInTheSameProject(Artefact artefact)
        {
            return this.Artefacts.FindAll(artefact.UnderProject);
        }

        ///<summary>
        /// Populate the <see cref="MultiRequest"/> with <paramref name="artefacts"/>
        ///</summary>
        ///<param name="artefacts">Array of <see cref="Artefact"/></param>
        ///<param name="removingArtefactUnderProject"><value>true</value> to remove sub artefacts if root artefact is present; otherwise, <value>false</value></param>
        public void Populate(Artefact[] artefacts, bool removingArtefactUnderProject)
        {
            if (this.Artefacts == null)
            {
                this.Artefacts = new List<Artefact>();
            }
            else
            {
                this.Artefacts.Clear();
            }
            List<Artefact> projectList = null;
            if (removingArtefactUnderProject)
            {
                projectList = new List<Artefact>();
            }

            foreach (Artefact artefact in artefacts)
            {
                this.Artefacts.Add(artefact);
                if (removingArtefactUnderProject && artefact.IsProject())
                {
                    projectList.Add(artefact);
                }
            }
            this.Artefacts.Sort(Artefact.CompareProject);
            if (removingArtefactUnderProject)
            {
                foreach (Artefact project in projectList)
                {
                    this.Artefacts.RemoveAll(project.UnderProject);
                }
            }
        }

        ///<summary>
        /// Populate the <see cref="MultiRequest"/> with <paramref name="artefacts"/>        
        /// </summary>
        ///<param name="artefacts">Array of <see cref="Artefact"/></param>
        /// <remarks>
        /// Equivalent to <see cref="Populate(Orchestra.Framework.Core.Artefact[], bool)"/> with <c>removingArtefactUnderProject=</c><value>true</value>
        /// </remarks>
        public void Populate(Artefact[] artefacts)
        {
            this.Populate(artefacts, true);
        }
    }
}