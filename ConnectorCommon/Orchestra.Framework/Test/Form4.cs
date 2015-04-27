using System;
using System.Windows.Forms;
using Orchestra.Framework.Client;
using Orchestra.Framework;
using Orchestra.Framework.Utilities;
using Orchestra.Framework.Utilities.Security;

namespace Test
{
    public partial class Form4 : Form
    {
        public Form4()
        {
            InitializeComponent();
        }

        private void btnResetCredentialsIdAPI_Click(object sender, EventArgs e)
        {
            try
            {
                IOrchestraClient o = new OrchestraClient();
                tbLogs.AppendText("Call 'ResetCredentials' API -> 'resetCredentials(" + tbCredentialsIdforGetAPI.Text + ");" + Environment.NewLine);
                IOrchestraResponse r = o.ResetCredentials(tbCredentialsIdforResetAPI.Text);

                tbLogs.AppendText("Result -> " + r.GetStatusDefinition().ToString() + Environment.NewLine + Environment.NewLine);
            }
            catch (Exception ee)
            {
                tbLogs.AppendText("Exception raised: " + ee.StackTrace + Environment.NewLine);
                System.Console.WriteLine(ee.StackTrace);
            }
        }

        private void btnGetCredentialsIdAPI_Click(object sender, EventArgs e)
        {
            try
            {
                bool uiPasswordFiledConfirmation;
                bool.TryParse(tbUIPasswordConfirmation.Text, out uiPasswordFiledConfirmation);
                tbLogs.AppendText("Call 'GetCredentials' API -> 'getCredentials(" + tbCredentialsIdforGetAPI.Text + ", " + uiPasswordFiledConfirmation + ", " + tbUITopMessage.Text + ");" + Environment.NewLine);
                OrchestraClient o = new OrchestraClient();
                ICredentialsResponse apiResult = o.GetCredentials(this.tbCredentialsIdforGetAPI.Text, credentialsUIPasswordConfirmation: uiPasswordFiledConfirmation, credentialsUIOnTopMessage: this.tbUITopMessage.Text, persistence: TODO);

                tbLogs.AppendText("Result -> " + apiResult.ToString() + Environment.NewLine + Environment.NewLine);
            }
            catch (Exception ee)
            {
                tbLogs.AppendText("Exception raised: " + ee.StackTrace + Environment.NewLine);                
                System.Console.WriteLine(ee.StackTrace);
            }
        }

    }
}
