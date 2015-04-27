using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Orchestra.Framework.Utilities;

namespace Test
{
    public partial class TestPassword : Form
    {
        public TestPassword()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            PasswordManagement pm = new PasswordManagement("toto", "prod@gest1", "jhgiuyrçè- rè-r",true);
            MessageBox.Show(pm.UserName);

        }

        private void button2_Click(object sender, EventArgs e)
        {
            PasswordManagement pm = new PasswordManagement("toto", "prod@gest1", "jhgiuyrçè- rè-r");
            MessageBox.Show(pm.UserName);
        }
    }
}
