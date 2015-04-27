// ----------------------------------------------------------------------------------------------------
// File Name: Form1.cs
// Project: Test MsiActions
// Copyright (c) Thales, 2010 - 2014. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Test_MsiActions
{
    using System;
    using System.Collections.Generic;
    using System.Configuration.Install;
    using System.Windows.Forms;
    using MsiActions;

    public partial class Form1 : Form
    {
        public Form1()
        {
            this.InitializeComponent();
        }

        private void Install_Click(object sender, EventArgs e)
        {
            MsiActions t = new MsiActions();
            Dictionary<object, object> d = new Dictionary<object, object>();
            t.Context = new InstallContext();
            t.Install(d);
        }

        private void Uninstall_Click(object sender, EventArgs e)
        {
            MsiActions t = new MsiActions();
            Dictionary<object, object> d = new Dictionary<object, object>();
            t.Uninstall(d);
        }
    }
}