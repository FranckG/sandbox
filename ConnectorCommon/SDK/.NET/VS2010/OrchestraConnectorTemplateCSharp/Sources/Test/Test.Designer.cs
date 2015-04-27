namespace Test
{
    partial class Test
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
            this.expand = new System.Windows.Forms.Button();
            this.context = new System.Windows.Forms.TextBox();
            this.result = new System.Windows.Forms.TextBox();
            this.navigate = new System.Windows.Forms.Button();
            this.documentaryExport = new System.Windows.Forms.Button();
            this.button1 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // expand
            // 
            this.expand.Location = new System.Drawing.Point(12, 129);
            this.expand.Name = "expand";
            this.expand.Size = new System.Drawing.Size(94, 23);
            this.expand.TabIndex = 0;
            this.expand.Text = "expand";
            this.expand.UseVisualStyleBackColor = true;
            this.expand.Click += new System.EventHandler(this.button1_Click);
            // 
            // context
            // 
            this.context.Location = new System.Drawing.Point(12, 12);
            this.context.Multiline = true;
            this.context.Name = "context";
            this.context.Size = new System.Drawing.Size(897, 111);
            this.context.TabIndex = 1;
            // 
            // result
            // 
            this.result.Location = new System.Drawing.Point(12, 158);
            this.result.Multiline = true;
            this.result.Name = "result";
            this.result.Size = new System.Drawing.Size(897, 96);
            this.result.TabIndex = 2;
            // 
            // navigate
            // 
            this.navigate.Location = new System.Drawing.Point(112, 129);
            this.navigate.Name = "navigate";
            this.navigate.Size = new System.Drawing.Size(75, 23);
            this.navigate.TabIndex = 3;
            this.navigate.Text = "navigate";
            this.navigate.UseVisualStyleBackColor = true;
            this.navigate.Click += new System.EventHandler(this.navigate_Click);
            // 
            // documentaryExport
            // 
            this.documentaryExport.AutoSize = true;
            this.documentaryExport.Location = new System.Drawing.Point(193, 129);
            this.documentaryExport.Name = "documentaryExport";
            this.documentaryExport.Size = new System.Drawing.Size(110, 23);
            this.documentaryExport.TabIndex = 4;
            this.documentaryExport.Text = "documentary export";
            this.documentaryExport.UseVisualStyleBackColor = true;
            this.documentaryExport.Click += new System.EventHandler(this.documentaryExport_Click);
            // 
            // button1
            // 
            this.button1.AutoSize = true;
            this.button1.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.button1.Location = new System.Drawing.Point(309, 129);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(47, 23);
            this.button1.TabIndex = 5;
            this.button1.Text = "create";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click_1);
            // 
            // button3
            // 
            this.button3.AutoSize = true;
            this.button3.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.button3.Location = new System.Drawing.Point(362, 129);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(105, 23);
            this.button3.TabIndex = 7;
            this.button3.Text = "Specific Command";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(921, 266);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.documentaryExport);
            this.Controls.Add(this.navigate);
            this.Controls.Add(this.result);
            this.Controls.Add(this.context);
            this.Controls.Add(this.expand);
            this.Name = "Form1";
            this.Text = "Form1";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button expand;
        private System.Windows.Forms.TextBox context;
        private System.Windows.Forms.TextBox result;
        private System.Windows.Forms.Button navigate;
        private System.Windows.Forms.Button documentaryExport;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button3;
    }
}

