using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using Orchestra.Framework.Core;

namespace Test
{
    public partial class LoadContext : Form
    {
        public LoadContext()
        {
            InitializeComponent();
        }

        private void btnLoad_Click(object sender, EventArgs e)
        {
            ContextDefinition c = new ContextDefinition();
            string t = contextString.Text;
            c.Load(t);
        }
    }
}
