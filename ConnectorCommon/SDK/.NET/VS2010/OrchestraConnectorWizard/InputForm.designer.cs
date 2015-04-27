namespace Orchestra.Connector.Wizard
{
    partial class InputForm
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
            this.label1 = new System.Windows.Forms.Label();
            this.rootType = new System.Windows.Forms.TextBox();
            this.okBtn = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.generatedProgId = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.fileExt = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(47, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(56, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Root type:";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // rootType
            // 
            this.rootType.Location = new System.Drawing.Point(109, 6);
            this.rootType.MaxLength = 19;
            this.rootType.Name = "rootType";
            this.rootType.Size = new System.Drawing.Size(100, 20);
            this.rootType.TabIndex = 1;
            this.rootType.KeyPress += new System.Windows.Forms.KeyPressEventHandler(this.rootType_KeyPress);
            // 
            // okBtn
            // 
            this.okBtn.Enabled = false;
            this.okBtn.Location = new System.Drawing.Point(12, 101);
            this.okBtn.Name = "okBtn";
            this.okBtn.Size = new System.Drawing.Size(75, 23);
            this.okBtn.TabIndex = 3;
            this.okBtn.Text = "Ok";
            this.okBtn.UseVisualStyleBackColor = true;
            this.okBtn.Click += new System.EventHandler(this.okBtn_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(9, 38);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(94, 13);
            this.label2.TabIndex = 3;
            this.label2.Text = "Generated ProgId:";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // generatedProgId
            // 
            this.generatedProgId.AutoSize = true;
            this.generatedProgId.Location = new System.Drawing.Point(109, 38);
            this.generatedProgId.Name = "generatedProgId";
            this.generatedProgId.Size = new System.Drawing.Size(105, 13);
            this.generatedProgId.TabIndex = 4;
            this.generatedProgId.Text = "Orchestra.Connector";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(29, 70);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(74, 13);
            this.label3.TabIndex = 5;
            this.label3.Text = "File extension:";
            // 
            // fileExt
            // 
            this.fileExt.Location = new System.Drawing.Point(109, 70);
            this.fileExt.MaxLength = 10;
            this.fileExt.Name = "fileExt";
            this.fileExt.Size = new System.Drawing.Size(100, 20);
            this.fileExt.TabIndex = 2;
            // 
            // InputForm
            // 
            this.AcceptButton = this.okBtn;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(326, 133);
            this.ControlBox = false;
            this.Controls.Add(this.fileExt);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.generatedProgId);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.okBtn);
            this.Controls.Add(this.rootType);
            this.Controls.Add(this.label1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Name = "InputForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Orchestra Connector";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox rootType;
        private System.Windows.Forms.Button okBtn;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label generatedProgId;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox fileExt;
    }
}