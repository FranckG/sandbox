using System;
using System.Windows.Forms;
using Orchestra.Framework.Utilities;

namespace Test
{
    public partial class Uri_migration : Form
    {
        public Uri_migration()
        {
            InitializeComponent();
        }

        private void oldUri_SelectedIndexChanged(object sender, EventArgs e)
        {
            //MigrationUri o = new MigrationUri();

            //newUri.Text= o.MigrateUriV4ToV5(oldUri.Text);
        }
    }
}
