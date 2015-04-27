using System;
using System.Globalization;
using System.Windows.Forms;
using Orchestra.Framework;
using Orchestra.Framework.Artefact;
using Orchestra.Framework.VariableManager;
using Environment = System.Environment;
using Orchestra.Framework.Utilities;

namespace Test
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Gef monExport = new Gef();
            TestExport(monExport, @"D:\PDGEF.xml");
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Gef monExport = new Gef();
            TestExport(monExport, @"D:\ExportList.xml");
        }

        private static void TestExport(Gef e, String file)
        {
            String result = e.InitOutputFile(file);
             e.WriteStartArtefact("", "", "Personal Queries", "Folder", "Personal Queries", "", "",  true);
            e.WriteStartArtefact("", "", "My Defect (PCR Life Responsible)", "Folder",
                                 "My Defect (PCR Life Responsible)", "", "", true);
            e.WriteStartArtefact("FT modifiés par moi entre deux dates","", "FT modifiés par moi entre deux dates",
                                 "Query", "FT modifiés par moi entre deux dates",
                                 "89a2c91a",
                                 "Personal Queries/My Defect (PCR Life Responsible)/FT modifiés par moi entre deux dates",
                                  true);
            e.WriteTextualDescription("Object Text", "rtf", "temp/00001fab!24.rtf", "","");
            e.WriteProperty("Parameter", "string", "action timestamp(1_0)… ]]>", "");
            e.WriteEndArtefact();
            e.WriteEndArtefact();
            e.WriteEndArtefact();
            e.CloseOutputFile();
        }

        private void GetAllvariables_Click(object sender, EventArgs e)
        {
            Client o = new Client();
            result.Text = "";
            int i = 0;
            foreach (String variable in o.GetAllVariables())
                result.AppendText(++i + " " + variable + Environment.NewLine);
        }

        private void getCultureInfo_Click(object sender, EventArgs e)
        {
            CultureInfo cultureInfo = CultureInfo.GetCultureInfo(locale.Text);
            result.Text = "";
            result.AppendText(cultureInfo.DisplayName);
        }

        private void GetVariable_Click(object sender, EventArgs e)
        {
            Client o = new Client();
            String[] var = o.GetVariable(variablePath.Text);
            result.Text = "";
            result.AppendText(variablePath.Text + Environment.NewLine);
            result.AppendText("Values :" + Environment.NewLine);
            foreach (String s in var)
                result.AppendText(s + Environment.NewLine);
        }

        private void ReadServerConfigurationFile_Click(object sender, EventArgs e)
        {
            ServerConfigurationFile o = new ServerConfigurationFile();
            result.Text = "";
            result.AppendText("Port Serveur :" + o.ServerPort + Environment.NewLine);
            result.AppendText("Mode Serveur :" + o.ServerMode + Environment.NewLine);
        }

        private void GenerateURI_Click(object sender, EventArgs e)
        {
            //Artefact artefact = new Artefact(toolname.Text, projectname.Text,objecttype.Text,objectid.Text);
            //uri.Text = artefact.ToString();
        }

        private void btnDecode_Click(object sender, EventArgs e)
        {
           // result.Text = XmlEncoding.Decode(textToDecode.Text);
        }

        private void GetProjects_Click(object sender, EventArgs e)
        {
            //result.Text = "";
            //PapeeteClient client = new PapeeteClient();
            //String[] response = client.GetProjects();
            //for(int i = 0; i < 3; i++)
            //{
            //    result.AppendText("[" + i + "]=> " + response[i] + Environment.NewLine);
            //}
        }

        private void Edit_Click(object sender, EventArgs e)
        {
            //result.Text = "";
            //PapeeteClient client = new PapeeteClient();
            //if (client.NavigateTo("Notepad", "Word", "Test", "", "", "", "false", "a", ""))
            //{
            //    result.AppendText("OK");
            //}
            //else
            //{
            //    result.AppendText("KO!");
            //}

        }

        private void Export_Click(object sender, EventArgs e)
        {
            //result.Text = "";
            //PapeeteClient client = new PapeeteClient();
            //String[] response = client.Import("Notepad", "ArtfactExplorer", "Test", "", "", "", "XML_DOC", "a", "");
            //for (int i = 0; i < 3; i++)
            //{
            //    result.AppendText("[" + i + "]=> " + response[i] + Environment.NewLine);
            //}

        }

        private void button3_Click(object sender, EventArgs e)
        {
            Client o = new Client();
            result.Text = o.GetCurrentContextName;
        }

        private void Sort_Click(object sender, EventArgs e)
        {
            //string[] projectsPath;
            //string[] Urns;

            //projectsPath = ProjectPaths.Text.Split('|');
            //Urns = URNs.Text.Split(' ');

            //MultiRequest multiRequest = new MultiRequest();

            //multiRequest.Populate(projectsPath, Urns);
        }

        private void button4_Click(object sender, EventArgs e)
        {
           // result.Text = XmlEncoding.Encode(textToDecode.Text);
        }


    }
}