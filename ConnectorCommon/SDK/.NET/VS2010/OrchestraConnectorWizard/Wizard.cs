using System;
using System.Collections.Generic;
using EnvDTE;
using Microsoft.VisualStudio.TemplateWizard;
using System.IO;

namespace Orchestra.Connector.Wizard
{
    public class Solution : IWizard
    {
        /// <summary>
        /// Provide a means for sub-projects to have access to the solution name
        /// </summary>
        private static string solutionName;

        private static DTE dte;

        public static Dictionary<string, string> globalDictionary;
        

        #region Implementation of IWizard

        public void RunStarted(object automationObject, Dictionary<string, string> replacementsDictionary, WizardRunKind runKind, object[] customParams)
        {
            dte = automationObject as DTE;

            InputForm input = new InputForm();
            input.ShowDialog();
            string rootType = input.RootType;
            string fileExt = input.FileExt;
            replacementsDictionary.Add("$toolname$", rootType);
            replacementsDictionary.Add("$fileext$", fileExt);
            globalDictionary = new Dictionary<string, string>();
            globalDictionary.Add("$toolname$", rootType);
            globalDictionary.Add("$fileext$", fileExt);

            solutionName = replacementsDictionary["$safeprojectname$"];
            globalDictionary.Add("$solutionname$", solutionName);

        }

        public void ProjectFinishedGenerating(EnvDTE.Project project)
        {
            Object testDte = dte.Solution.Properties.Item("Path").Value;

            if (testDte != null)
            {
                // Recuperation du chemin de la solution a travers le dte.
                string solutionDirectory = Path.GetDirectoryName((string) dte.Solution.Properties.Item("Path").Value);

                if (solutionDirectory != null)
                {
                    // Creation de l'arborescence des ressources a la racine du projet

                    //Creation de Wrapper
                    string wrapper = Path.Combine(solutionDirectory, "Wrapper");
                    Directory.CreateDirectory(wrapper);

                    // Creation de ConfDir
                    string confDir = Path.Combine(solutionDirectory, "ConfDir");
                    Directory.CreateDirectory(confDir);

                    // Creation de Framework dans ConfDir
                    string framework = Path.Combine(confDir, "Framework");
                    Directory.CreateDirectory(framework);

                    // Creation de Association dans ConfDir\Framework
                    string association = Path.Combine(framework, "Association");
                    Directory.CreateDirectory(association);

                    // Creation de Config dans ConfDir\Framework
                    string config = Path.Combine(framework, "Config");
                    Directory.CreateDirectory(config);

                    // Creation de Mozart\Config dans ConfDir
                    string mozartConfig = Path.Combine(confDir, "Mozart\\Config");
                    Directory.CreateDirectory(mozartConfig);

                    // Creation de ConnectorHome\orcConfig
                    string connectorHomeOrcConfig = Path.Combine(solutionDirectory, "ConnectorHome\\orcConfig");
                    Directory.CreateDirectory(connectorHomeOrcConfig);

                    // Noms des fichiers a deplacer
                    string defaultAssociation = globalDictionary["$toolname$"] + "_DefaultAssociation.xml";
                    string artifactsDescription = "artifacts_description_" + globalDictionary["$toolname$"] + ".xml";
                    string publicationContextsDescriptionShort = "PC_desc_" + globalDictionary["$toolname$"] + ".xml";
                    string publicationContextsDescription = "PublicationContexts_description_" + globalDictionary["$toolname$"] + ".xml";
                    string typesDescription = "Types_description_" + globalDictionary["$toolname$"] + ".xml";
                    string connector = globalDictionary["$toolname$"] + ".connector";
                    string iWrapper = "IWrapper.cls";
                    string orchestraWrapper = "OrchestraWrapper.vbp";

                    // Pour chaque item de SolutionItemsContainer copier puis supprimer les fichiers sources du projet temporaire.
                    foreach (ProjectItem item in dte.Solution.Projects.Item(4).ProjectItems)
                    {
                        //System.Windows.Forms.MessageBox.Show("Nom de l'item: " + item.Name);

                        if (item.Name == defaultAssociation)
                        {
                            string associationDest = Path.Combine(association, item.Name);
                            File.Copy(item.FileNames[1], associationDest);
                            File.Delete(item.FileNames[1]);
                            continue;
                        }

                        if (item.Name == artifactsDescription)
                        {
                            string configDest = Path.Combine(config, item.Name);
                            File.Copy(item.FileNames[1], configDest);
                            File.Delete(item.FileNames[1]);
                            continue;
                        }

                        if (item.Name == publicationContextsDescriptionShort)
                        {
                            string mozartConfigDest = Path.Combine(mozartConfig, publicationContextsDescription);
                            File.Copy(item.FileNames[1], mozartConfigDest);
                            File.Delete(item.FileNames[1]);
                            continue;
                        }

                        if (item.Name == typesDescription)
                        {
                            string mozartConfigDest = Path.Combine(mozartConfig, item.Name);
                            File.Copy(item.FileNames[1], mozartConfigDest);
                            File.Delete(item.FileNames[1]);
                            continue;
                        }

                        if (item.Name == connector)
                        {
                            string connectorHomeOrcConfigDest = Path.Combine(connectorHomeOrcConfig, item.Name);
                            File.Copy(item.FileNames[1],connectorHomeOrcConfigDest);
                            File.Delete(item.FileNames[1]);
                            continue;
                        }

                        if (item.Name == iWrapper)
                        {
                            string wrapperDest = Path.Combine(wrapper, item.Name);
                            File.Copy(item.FileNames[1], wrapperDest);
                            File.Delete(item.FileNames[1]);
                            continue;
                        }

                        if (item.Name == orchestraWrapper)
                        {
                            string wrapperDest = Path.Combine(wrapper, item.Name);
                            File.Copy(item.FileNames[1], wrapperDest);
                            File.Delete(item.FileNames[1]);
                            continue;
                        }
                    }

                    // Sauvegarde du chemin du fichier projet avant suppression du projet dans la solution.
                    string solutionItemsContainerFullName = dte.Solution.Projects.Item(4).FullName;

                    //System.Windows.Forms.MessageBox.Show("Suppression physique des fichiers restants dans le projet temporaire");
                    Directory.Delete(Path.Combine(solutionDirectory, globalDictionary["$solutionname$"] + "\\SolutionItemsContainer\\bin"), true);
                    Directory.Delete(Path.Combine(solutionDirectory, globalDictionary["$solutionname$"] + "\\SolutionItemsContainer\\obj"), true);

                    //System.Windows.Forms.MessageBox.Show("Suppression du projet SolutionItemsContainer");
                    dte.Solution.Remove(dte.Solution.Projects.Item(4));

                    // Give the solution time to release the lock on the project file
                    System.Threading.Thread.Sleep(500);

                    // Suppression du fichier projet
                    File.Delete(solutionItemsContainerFullName);

                    // Suppression du dossier SolutionsItemsContainer
                    Directory.Delete(Path.Combine(solutionDirectory, globalDictionary["$solutionname$"] + "\\SolutionItemsContainer"), true);                    
                }
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

        #endregion
    }
}