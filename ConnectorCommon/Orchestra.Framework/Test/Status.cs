using System;
using System.Windows.Forms;
using Orchestra.Framework;

namespace Test
{
    public partial class Status : Form
    {
        public Status()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            StatusDefinition sd = new StatusDefinition();
            sd.SetStatusDefinition(txtStatus.Text);
            sd.FindStatusesByUri(@"orchestra://Migration/Migration?UriToMigrate=papeete%3A%2F%2F%2FClearquest%2F%2Fprod%2F%2F%2F");
        }
    }
}
