using System;
using System.Windows.Forms;
using Orchestra.Framework.Connector.$toolname$;

namespace Test
{
    public partial class Test : Form
    {
        public Test()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Connector c = new Connector();
            result.Text = c.expand(context.Text);
        }

        private void navigate_Click(object sender, EventArgs e)
        {
            Connector c = new Connector();
            result.Text = c.navigate(context.Text);
        }

        private void documentaryExport_Click(object sender, EventArgs e)
        {
            Connector c = new Connector();
            result.Text = c.documentaryExport(context.Text);
        }

        private void button1_Click_1(object sender, EventArgs e)
        {
            Connector c = new Connector();
            result.Text = c.create(context.Text);
        }

        private void button3_Click(object sender, EventArgs e)
        {
            Connector c = new Connector();
            result.Text = c.executeSpecificCommand(context.Text);
        }
    }
}
