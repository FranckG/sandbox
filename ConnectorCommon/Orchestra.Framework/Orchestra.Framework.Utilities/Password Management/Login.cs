// ----------------------------------------------------------------------------------------------------
// File Name: Login.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities
{
    using System;
    using System.Drawing;
    using System.Runtime.InteropServices;
    using System.Windows.Forms;

    [ComVisible(false)]
    internal partial class LoginForm : Form
    {
        public LoginForm()
            : this(false)
        {
        }

        public LoginForm(bool confirmPassword)
        {
            this.InitializeComponent();
            if (!confirmPassword)
            {
                this.txtConfirmPassword.Visible = false;
                this.label3.Visible = false;
                this.ClientSize = new Size(336, 133);
                this.Controls.Remove(this.txtConfirmPassword);
                this.Controls.Remove(this.label3);
                this.txtPassword.Location = new Point(81, 61);
                this.label2.Location = new Point(19, 64);
                this.txtUserName.Location = new Point(81, 35);
                this.label1.Location = new Point(12, 38);
                this.btnCancel.Location = new Point(260, 93);
                this.btnOk.Location = new Point(190, 93);
                this.btnOk.Enabled = true;
                this.txtConfirmPassword.TabIndex = 0;
                this.btnOk.TabIndex = 3;
                this.btnCancel.TabIndex = 4;
            }
            this.ValidatedLoginForm = false;
        }

        public Boolean ValidatedLoginForm { get; private set; }

        private void LoginForm_Load(object sender, EventArgs e)
        {
            this.Activate();
        }

        private void btnOk_Click(object sender, EventArgs e)
        {
            this.ValidatedLoginForm = true;
            this.Hide();
        }

        private void txtConfirmPassword_KeyPress(object sender, KeyPressEventArgs e)
        {
            string password = this.txtConfirmPassword.Text + e.KeyChar;
            if (password.Equals(this.txtPassword.Text))
            {
                this.btnOk.Enabled = true;
                this.txtConfirmPassword.ForeColor = Color.Black;
            }
            else
            {
                this.btnOk.Enabled = false;
                this.txtConfirmPassword.ForeColor = Color.Red;
            }
        }
    }
}