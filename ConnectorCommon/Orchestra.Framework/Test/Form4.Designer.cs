namespace Test
{
    partial class Form4
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.btnGetCredentialsIdAPI = new System.Windows.Forms.Button();
            this.tbLogs = new System.Windows.Forms.TextBox();
            this.tbCredentialsIdforGetAPI = new System.Windows.Forms.TextBox();
            this.lblCredentialsIdforGetAPI = new System.Windows.Forms.Label();
            this.lblCredentialsIdforResetAPI = new System.Windows.Forms.Label();
            this.tbCredentialsIdforResetAPI = new System.Windows.Forms.TextBox();
            this.btnResetCredentialsIdAPI = new System.Windows.Forms.Button();
            this.lblUITopMessage = new System.Windows.Forms.Label();
            this.lblUIPasswordConfirmation = new System.Windows.Forms.Label();
            this.tbUITopMessage = new System.Windows.Forms.TextBox();
            this.tbUIPasswordConfirmation = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // btnGetCredentialsIdAPI
            // 
            this.btnGetCredentialsIdAPI.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.btnGetCredentialsIdAPI.Location = new System.Drawing.Point(574, 117);
            this.btnGetCredentialsIdAPI.Name = "btnGetCredentialsIdAPI";
            this.btnGetCredentialsIdAPI.Size = new System.Drawing.Size(139, 33);
            this.btnGetCredentialsIdAPI.TabIndex = 0;
            this.btnGetCredentialsIdAPI.Text = "Call GetCredentials API";
            this.btnGetCredentialsIdAPI.UseVisualStyleBackColor = true;
            this.btnGetCredentialsIdAPI.Click += new System.EventHandler(this.btnGetCredentialsIdAPI_Click);
            // 
            // tbLogs
            // 
            this.tbLogs.Location = new System.Drawing.Point(15, 224);
            this.tbLogs.Multiline = true;
            this.tbLogs.Name = "tbLogs";
            this.tbLogs.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.tbLogs.Size = new System.Drawing.Size(698, 116);
            this.tbLogs.TabIndex = 1;
            // 
            // tbCredentialsIdforGetAPI
            // 
            this.tbCredentialsIdforGetAPI.Location = new System.Drawing.Point(114, 17);
            this.tbCredentialsIdforGetAPI.Name = "tbCredentialsIdforGetAPI";
            this.tbCredentialsIdforGetAPI.Size = new System.Drawing.Size(436, 20);
            this.tbCredentialsIdforGetAPI.TabIndex = 4;
            // 
            // lblCredentialsIdforGetAPI
            // 
            this.lblCredentialsIdforGetAPI.AutoSize = true;
            this.lblCredentialsIdforGetAPI.Location = new System.Drawing.Point(12, 20);
            this.lblCredentialsIdforGetAPI.Name = "lblCredentialsIdforGetAPI";
            this.lblCredentialsIdforGetAPI.Size = new System.Drawing.Size(79, 13);
            this.lblCredentialsIdforGetAPI.TabIndex = 5;
            this.lblCredentialsIdforGetAPI.Text = "Credentials ID :";
            // 
            // lblCredentialsIdforResetAPI
            // 
            this.lblCredentialsIdforResetAPI.AutoSize = true;
            this.lblCredentialsIdforResetAPI.Location = new System.Drawing.Point(13, 191);
            this.lblCredentialsIdforResetAPI.Name = "lblCredentialsIdforResetAPI";
            this.lblCredentialsIdforResetAPI.Size = new System.Drawing.Size(79, 13);
            this.lblCredentialsIdforResetAPI.TabIndex = 8;
            this.lblCredentialsIdforResetAPI.Text = "Credentials ID :";
            // 
            // tbCredentialsIdforResetAPI
            // 
            this.tbCredentialsIdforResetAPI.Location = new System.Drawing.Point(114, 188);
            this.tbCredentialsIdforResetAPI.Name = "tbCredentialsIdforResetAPI";
            this.tbCredentialsIdforResetAPI.Size = new System.Drawing.Size(436, 20);
            this.tbCredentialsIdforResetAPI.TabIndex = 7;
            // 
            // btnResetCredentialsIdAPI
            // 
            this.btnResetCredentialsIdAPI.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.btnResetCredentialsIdAPI.Location = new System.Drawing.Point(574, 181);
            this.btnResetCredentialsIdAPI.Name = "btnResetCredentialsIdAPI";
            this.btnResetCredentialsIdAPI.Size = new System.Drawing.Size(139, 33);
            this.btnResetCredentialsIdAPI.TabIndex = 6;
            this.btnResetCredentialsIdAPI.Text = "Call ResetCredentials API";
            this.btnResetCredentialsIdAPI.UseVisualStyleBackColor = true;
            this.btnResetCredentialsIdAPI.Click += new System.EventHandler(this.btnResetCredentialsIdAPI_Click);
            // 
            // lblUITopMessage
            // 
            this.lblUITopMessage.AutoSize = true;
            this.lblUITopMessage.Location = new System.Drawing.Point(12, 40);
            this.lblUITopMessage.Name = "lblUITopMessage";
            this.lblUITopMessage.Size = new System.Drawing.Size(377, 13);
            this.lblUITopMessage.TabIndex = 9;
            this.lblUITopMessage.Text = "If UI appears, optional message (HTMl formatted or not) to display on form top :";
            // 
            // lblUIPasswordConfirmation
            // 
            this.lblUIPasswordConfirmation.AutoSize = true;
            this.lblUIPasswordConfirmation.Location = new System.Drawing.Point(13, 127);
            this.lblUIPasswordConfirmation.Name = "lblUIPasswordConfirmation";
            this.lblUIPasswordConfirmation.Size = new System.Drawing.Size(301, 13);
            this.lblUIPasswordConfirmation.TabIndex = 10;
            this.lblUIPasswordConfirmation.Text = "If UI appears, optional password confirmation field (true/false) :";
            // 
            // tbUITopMessage
            // 
            this.tbUITopMessage.Location = new System.Drawing.Point(114, 56);
            this.tbUITopMessage.Multiline = true;
            this.tbUITopMessage.Name = "tbUITopMessage";
            this.tbUITopMessage.Size = new System.Drawing.Size(436, 56);
            this.tbUITopMessage.TabIndex = 11;
            // 
            // tbUIPasswordConfirmation
            // 
            this.tbUIPasswordConfirmation.Location = new System.Drawing.Point(333, 124);
            this.tbUIPasswordConfirmation.Name = "tbUIPasswordConfirmation";
            this.tbUIPasswordConfirmation.Size = new System.Drawing.Size(217, 20);
            this.tbUIPasswordConfirmation.TabIndex = 12;
            this.tbUIPasswordConfirmation.Text = "True";
            // 
            // Form4
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(725, 359);
            this.Controls.Add(this.tbUIPasswordConfirmation);
            this.Controls.Add(this.tbUITopMessage);
            this.Controls.Add(this.lblUIPasswordConfirmation);
            this.Controls.Add(this.lblUITopMessage);
            this.Controls.Add(this.lblCredentialsIdforResetAPI);
            this.Controls.Add(this.tbCredentialsIdforResetAPI);
            this.Controls.Add(this.btnResetCredentialsIdAPI);
            this.Controls.Add(this.lblCredentialsIdforGetAPI);
            this.Controls.Add(this.tbCredentialsIdforGetAPI);
            this.Controls.Add(this.tbLogs);
            this.Controls.Add(this.btnGetCredentialsIdAPI);
            this.Name = "Form4";
            this.Text = "Form4";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnGetCredentialsIdAPI;
        private System.Windows.Forms.TextBox tbLogs;
        private System.Windows.Forms.TextBox tbCredentialsIdforGetAPI;
        private System.Windows.Forms.Label lblCredentialsIdforGetAPI;
        private System.Windows.Forms.Label lblCredentialsIdforResetAPI;
        private System.Windows.Forms.TextBox tbCredentialsIdforResetAPI;
        private System.Windows.Forms.Button btnResetCredentialsIdAPI;
        private System.Windows.Forms.Label lblUITopMessage;
        private System.Windows.Forms.Label lblUIPasswordConfirmation;
        private System.Windows.Forms.TextBox tbUITopMessage;
        private System.Windows.Forms.TextBox tbUIPasswordConfirmation;
    }
}