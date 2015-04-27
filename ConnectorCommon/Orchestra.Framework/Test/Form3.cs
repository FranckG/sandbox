using System;
using System.Windows.Forms;
using Orchestra.Framework.Client;
using Orchestra.Framework;
using Orchestra.Framework.Utilities;

namespace Test
{
    public partial class Form3 : Form
    {
        public Form3()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            OrchestraClient o = new OrchestraClient();
            IOrchestraResponse r = o.getRootArtifacts();
            StatusDefinition sd = r.GetStatusDefinition();
            //textBox1.Text = sd.status.status[0].exportFilePath;
            textBox1.Text = sd.status.GetChildStatus(0).exportFilePath;


            //textBox1.Text = r.Value("responseFilePath");
            //StatusDefinition s  = new StatusDefinition();
            //s.SetStatusDefinition(File.ReadAllText(textBox1.Text));
            //textBox1.Text = s.status.status[0].exportFilePath;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            EnvironmentMetadata environmentMetadata = new EnvironmentMetadata("orchestra://Doors/TestJYL/Module/4534bbf27f891120%2E0000282e");
        }

        private void button3_Click(object sender, EventArgs e)
        {
            OrchestraClient client = new OrchestraClient();
            textBox1.Text = client.getLogicalNameSingleResult(@"S:\Orchestra_Data\Artifacts\Models\GAB\GAB.rpy");
        }
    }
}
