namespace Test
{
    partial class Form2
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
            this.cbURI = new System.Windows.Forms.ComboBox();
            this.RootType = new System.Windows.Forms.TextBox();
            this.RootName = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.Type = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.Id = new System.Windows.Forms.TextBox();
            this.result = new System.Windows.Forms.TextBox();
            this.parametres = new System.Windows.Forms.ListBox();
            this.button1 = new System.Windows.Forms.Button();
            this.isOk = new System.Windows.Forms.CheckBox();
            this.button2 = new System.Windows.Forms.Button();
            this.button3 = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // cbURI
            // 
            this.cbURI.FormattingEnabled = true;
            this.cbURI.Items.AddRange(new object[] {
            "orchestra://Ccucm/DoorsConnector%20for%20ODM/Baseline/Virtual_Latest_Baseline_Doo" +
                "rsConnector?Promotion%20level=RELEASED",
            "orchestra://Excel/Excel%2FClasseur7",
            "orchestra://Powerpoint/A380%2FBS%2FSlides%2FA380/Slide/590",
            "orchestra://Clearquest/prod",
            "orchestra://Clearquest/prod/Query/Personal%20Queries%2FMyPendingList",
            "orchestra://Clearquest/prod/Defect/prod00059629?Query=Personal%20Queries%2FMyPend" +
                "ingList&toto=tata&viewName=10.%20Document%20view",
            "orchestra:EDIT://Clearquest/prod/Defect/prod00059629",
            "orchestra://Doors/Orchestra%20Database_b3cbf282%2FOrchestra/Module/000007eb",
            "orchestra://Doors/Orchestra%20Database_b3cbf282%2FOrchestra/View/000007eb%2110.%2" +
                "0Document%20view",
            "orchestra://Doors/Orchestra%20Database_b3cbf282%2FOrchestra/Object/000007eb%21157" +
                "?viewName=10.%20Document%20view",
            "orchestra://Reqtify/LM%2FTest%2F55553/LinkSet/Bookmarks%2CRequirements",
            "orchestra://Rhapsody/Model%2FGAB%2FGAB/Attribute/GUID%20173dae23-ddec-4acf-83d5-5" +
                "c767d13cc9c",
            "orchestra://Word/CRAL%2FManagement%2FCRAL_source/Bookmark/Principales_conclusions" +
                "/",
            "orchestra://Clearquest/prod/Defect/prod00059629?Query=Personal%20Queries%2FMyPend" +
                "ingList&toto=tata&viewName=10.%20Document%20view&toto=titi",
            "orchestra://Type%20de%20mon%20root%20%C3%A0%20moi/Root%20Name%2FName%20du%20fichi" +
                "er/Vue%20Doors/ObjectId?test%20d%27es%3Fpace=c%3A%2Fpath%2Fd%3De%2Ffichier&test%" +
                "26decaract%C3%A8resmoisis=%3Den%26lk"});
            this.cbURI.Location = new System.Drawing.Point(12, 12);
            this.cbURI.Name = "cbURI";
            this.cbURI.Size = new System.Drawing.Size(956, 21);
            this.cbURI.TabIndex = 0;
            // 
            // RootType
            // 
            this.RootType.Location = new System.Drawing.Point(66, 43);
            this.RootType.Name = "RootType";
            this.RootType.Size = new System.Drawing.Size(754, 20);
            this.RootType.TabIndex = 2;
            // 
            // RootName
            // 
            this.RootName.Location = new System.Drawing.Point(66, 69);
            this.RootName.Name = "RootName";
            this.RootName.Size = new System.Drawing.Size(754, 20);
            this.RootName.TabIndex = 3;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(6, 72);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(54, 13);
            this.label1.TabIndex = 4;
            this.label1.Text = "root name";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(12, 46);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(48, 13);
            this.label2.TabIndex = 5;
            this.label2.Text = "root type";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(33, 98);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(27, 13);
            this.label3.TabIndex = 7;
            this.label3.Text = "type";
            // 
            // Type
            // 
            this.Type.Location = new System.Drawing.Point(66, 95);
            this.Type.Name = "Type";
            this.Type.Size = new System.Drawing.Size(754, 20);
            this.Type.TabIndex = 6;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(45, 124);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(15, 13);
            this.label4.TabIndex = 9;
            this.label4.Text = "id";
            // 
            // Id
            // 
            this.Id.Location = new System.Drawing.Point(66, 121);
            this.Id.Name = "Id";
            this.Id.Size = new System.Drawing.Size(754, 20);
            this.Id.TabIndex = 8;
            // 
            // result
            // 
            this.result.Location = new System.Drawing.Point(66, 315);
            this.result.Name = "result";
            this.result.Size = new System.Drawing.Size(754, 20);
            this.result.TabIndex = 11;
            // 
            // parametres
            // 
            this.parametres.FormattingEnabled = true;
            this.parametres.Location = new System.Drawing.Point(66, 150);
            this.parametres.Name = "parametres";
            this.parametres.Size = new System.Drawing.Size(753, 147);
            this.parametres.TabIndex = 12;
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(66, 387);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(75, 23);
            this.button1.TabIndex = 13;
            this.button1.Text = "button1";
            this.button1.UseVisualStyleBackColor = true;
            // 
            // isOk
            // 
            this.isOk.AutoSize = true;
            this.isOk.Location = new System.Drawing.Point(67, 349);
            this.isOk.Name = "isOk";
            this.isOk.Size = new System.Drawing.Size(52, 17);
            this.isOk.TabIndex = 14;
            this.isOk.Text = "Is OK";
            this.isOk.UseVisualStyleBackColor = true;
            // 
            // button2
            // 
            this.button2.Location = new System.Drawing.Point(156, 387);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(75, 23);
            this.button2.TabIndex = 15;
            this.button2.Text = "button2";
            this.button2.UseVisualStyleBackColor = true;
            // 
            // button3
            // 
            this.button3.Location = new System.Drawing.Point(826, 40);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(75, 23);
            this.button3.TabIndex = 16;
            this.button3.Text = "button3";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // Form2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(980, 541);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.button2);
            this.Controls.Add(this.isOk);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.parametres);
            this.Controls.Add(this.result);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.Id);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.Type);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.RootName);
            this.Controls.Add(this.RootType);
            this.Controls.Add(this.cbURI);
            this.Name = "Form2";
            this.Text = "Form2";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.ComboBox cbURI;
        private System.Windows.Forms.TextBox RootType;
        private System.Windows.Forms.TextBox RootName;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox Type;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox Id;
        private System.Windows.Forms.TextBox result;
        private System.Windows.Forms.ListBox parametres;
        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.CheckBox isOk;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button button3;
    }
}