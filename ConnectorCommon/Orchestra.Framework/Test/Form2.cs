using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Orchestra.Framework.Utilities;

namespace Test
{
    public partial class Form2 : Form
    {
        public Form2()
        {
            InitializeComponent();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Orchestra.Framework.Core.Artefact artefact = new Orchestra.Framework.Core.Artefact();
            artefact.Uri = cbURI.Text;
            RootType.Text = artefact.RootType;
            RootName.Text = artefact.RootName;
            Type.Text = artefact.Type;
            Id.Text = artefact.Id;
            
            result.Text = artefact.GetFormattedParameters;
        }
    }
}
