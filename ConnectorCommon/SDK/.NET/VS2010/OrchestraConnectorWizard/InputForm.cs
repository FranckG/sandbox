using System;
using System.Windows.Forms;

namespace Orchestra.Connector.Wizard
{
    public partial class InputForm : Form
    {
        public InputForm()
        {
            InitializeComponent();
            this.rootType.TextChanged += new EventHandler(rootType_TextChanged);
            this.fileExt.TextChanged += new EventHandler(fileExt_TextChanged);
        }

        public string RootType
        {
            get
            {
                return rootType.Text;
            }
        }

        public string FileExt
        {
            get
            {
                return fileExt.Text;
            }
        }

        private void okBtn_Click(object sender, EventArgs e)
        {
            Hide();
        }

        private void rootType_KeyPress(object sender, KeyPressEventArgs e)
        {
            generatedProgId.Text = "Orchestra.Connector" + RootType + e.KeyChar;
        }

        private void rootType_TextChanged(object sender, EventArgs e)
        {
            this.okBtn.Enabled = (this.rootType.TextLength > 0 && this.fileExt.TextLength > 0);
        }

        private void fileExt_TextChanged(object sender, EventArgs e)
        {
            this.okBtn.Enabled = (this.rootType.TextLength > 0 && this.fileExt.TextLength > 0);
        }
    }
}