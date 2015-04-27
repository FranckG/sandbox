using System;
using System.Collections.Generic;
using EnvDTE;
using Microsoft.VisualStudio.TemplateWizard;

namespace Orchestra.Connector.Wizard
{
    public class Project : IWizard
    {
        #region Implementation of IWizard

        public void RunStarted(object automationObject, Dictionary<string, string> replacementsDictionary, WizardRunKind runKind, object[] customParams)
        {
            try
            {
                replacementsDictionary.Add("$toolname$", Solution.globalDictionary["$toolname$"]);
                replacementsDictionary.Add("$fileext$", Solution.globalDictionary["$fileext$"]);
            }
            catch (Exception)
            {
                //DO NOTHING
            }
        }

        public void ProjectItemFinishedGenerating(ProjectItem projectItem)
        {

        }

        public bool ShouldAddProjectItem(string filePath)
        {
            return true;
        }

        public void BeforeOpeningFile(ProjectItem projectItem)
        {

        }

        public void RunFinished()
        {

        }

        public void ProjectFinishedGenerating(EnvDTE.Project project)
        {
            
        }

        #endregion
    }
}