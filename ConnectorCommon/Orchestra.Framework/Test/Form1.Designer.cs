namespace Test
{
    partial class Form1
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            this.button1 = new System.Windows.Forms.Button();
            this.button2 = new System.Windows.Forms.Button();
            this.GetAllvariables = new System.Windows.Forms.Button();
            this.result = new System.Windows.Forms.TextBox();
            this.getCultureInfo = new System.Windows.Forms.Button();
            this.locale = new System.Windows.Forms.TextBox();
            this.GetVariable = new System.Windows.Forms.Button();
            this.variablePath = new System.Windows.Forms.TextBox();
            this.ReadServerConfigurationFile = new System.Windows.Forms.Button();
            this.toolname = new System.Windows.Forms.TextBox();
            this.objecttype = new System.Windows.Forms.TextBox();
            this.objectname = new System.Windows.Forms.TextBox();
            this.objectid = new System.Windows.Forms.TextBox();
            this.projectname = new System.Windows.Forms.TextBox();
            this.uri = new System.Windows.Forms.TextBox();
            this.GenerateURI = new System.Windows.Forms.Button();
            this.btnDecode = new System.Windows.Forms.Button();
            this.textToDecode = new System.Windows.Forms.TextBox();
            this.GetProjects = new System.Windows.Forms.Button();
            this.Edit = new System.Windows.Forms.Button();
            this.Export = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.label1 = new System.Windows.Forms.Label();
            this.button3 = new System.Windows.Forms.Button();
            this.ProjectPaths = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.URNs = new System.Windows.Forms.TextBox();
            this.MultiRequest = new System.Windows.Forms.GroupBox();
            this.Sort = new System.Windows.Forms.Button();
            this.PapeeteURI = new System.Windows.Forms.GroupBox();
            this.button4 = new System.Windows.Forms.Button();
            this.groupBox1.SuspendLayout();
            this.MultiRequest.SuspendLayout();
            this.PapeeteURI.SuspendLayout();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.AutoSize = true;
            this.button1.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.button1.Location = new System.Drawing.Point(12, 12);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(53, 23);
            this.button1.TabIndex = 0;
            this.button1.Text = "PDGEF";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // button2
            // 
            this.button2.AutoSize = true;
            this.button2.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.button2.Location = new System.Drawing.Point(71, 12);
            this.button2.Name = "button2";
            this.button2.Size = new System.Drawing.Size(66, 23);
            this.button2.TabIndex = 1;
            this.button2.Text = "ArtifactList";
            this.button2.UseVisualStyleBackColor = true;
            this.button2.Click += new System.EventHandler(this.button2_Click);
            // 
            // GetAllvariables
            // 
            this.GetAllvariables.AutoSize = true;
            this.GetAllvariables.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.GetAllvariables.Location = new System.Drawing.Point(12, 41);
            this.GetAllvariables.Name = "GetAllvariables";
            this.GetAllvariables.Size = new System.Drawing.Size(86, 23);
            this.GetAllvariables.TabIndex = 2;
            this.GetAllvariables.Text = "getAllVariables";
            this.GetAllvariables.UseVisualStyleBackColor = true;
            this.GetAllvariables.Click += new System.EventHandler(this.GetAllvariables_Click);
            // 
            // result
            // 
            this.result.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom)
                        | System.Windows.Forms.AnchorStyles.Left)
                        | System.Windows.Forms.AnchorStyles.Right)));
            this.result.Location = new System.Drawing.Point(12, 101);
            this.result.Multiline = true;
            this.result.Name = "result";
            this.result.ScrollBars = System.Windows.Forms.ScrollBars.Both;
            this.result.Size = new System.Drawing.Size(783, 319);
            this.result.TabIndex = 3;
            // 
            // getCultureInfo
            // 
            this.getCultureInfo.AutoSize = true;
            this.getCultureInfo.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.getCultureInfo.Location = new System.Drawing.Point(104, 41);
            this.getCultureInfo.Name = "getCultureInfo";
            this.getCultureInfo.Size = new System.Drawing.Size(85, 23);
            this.getCultureInfo.TabIndex = 4;
            this.getCultureInfo.Text = "GetCultureInfo";
            this.getCultureInfo.UseVisualStyleBackColor = true;
            this.getCultureInfo.Click += new System.EventHandler(this.getCultureInfo_Click);
            // 
            // locale
            // 
            this.locale.Location = new System.Drawing.Point(195, 43);
            this.locale.Name = "locale";
            this.locale.Size = new System.Drawing.Size(33, 20);
            this.locale.TabIndex = 5;
            // 
            // GetVariable
            // 
            this.GetVariable.AutoSize = true;
            this.GetVariable.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.GetVariable.Location = new System.Drawing.Point(143, 12);
            this.GetVariable.Name = "GetVariable";
            this.GetVariable.Size = new System.Drawing.Size(70, 23);
            this.GetVariable.TabIndex = 6;
            this.GetVariable.Text = "getVariable";
            this.GetVariable.UseVisualStyleBackColor = true;
            this.GetVariable.Click += new System.EventHandler(this.GetVariable_Click);
            // 
            // variablePath
            // 
            this.variablePath.Location = new System.Drawing.Point(219, 14);
            this.variablePath.Name = "variablePath";
            this.variablePath.Size = new System.Drawing.Size(539, 20);
            this.variablePath.TabIndex = 7;
            // 
            // ReadServerConfigurationFile
            // 
            this.ReadServerConfigurationFile.AutoSize = true;
            this.ReadServerConfigurationFile.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.ReadServerConfigurationFile.Location = new System.Drawing.Point(234, 40);
            this.ReadServerConfigurationFile.Name = "ReadServerConfigurationFile";
            this.ReadServerConfigurationFile.Size = new System.Drawing.Size(161, 23);
            this.ReadServerConfigurationFile.TabIndex = 8;
            this.ReadServerConfigurationFile.Text = "Read Server Configuration File";
            this.ReadServerConfigurationFile.UseVisualStyleBackColor = true;
            this.ReadServerConfigurationFile.Click += new System.EventHandler(this.ReadServerConfigurationFile_Click);
            // 
            // toolname
            // 
            this.toolname.Location = new System.Drawing.Point(11, 13);
            this.toolname.Name = "toolname";
            this.toolname.Size = new System.Drawing.Size(100, 20);
            this.toolname.TabIndex = 9;
            // 
            // objecttype
            // 
            this.objecttype.Location = new System.Drawing.Point(11, 39);
            this.objecttype.Name = "objecttype";
            this.objecttype.Size = new System.Drawing.Size(100, 20);
            this.objecttype.TabIndex = 10;
            // 
            // objectname
            // 
            this.objectname.Location = new System.Drawing.Point(11, 65);
            this.objectname.Name = "objectname";
            this.objectname.Size = new System.Drawing.Size(100, 20);
            this.objectname.TabIndex = 11;
            // 
            // objectid
            // 
            this.objectid.Location = new System.Drawing.Point(11, 91);
            this.objectid.Name = "objectid";
            this.objectid.Size = new System.Drawing.Size(100, 20);
            this.objectid.TabIndex = 12;
            // 
            // projectname
            // 
            this.projectname.Location = new System.Drawing.Point(123, 13);
            this.projectname.Name = "projectname";
            this.projectname.Size = new System.Drawing.Size(100, 20);
            this.projectname.TabIndex = 13;
            // 
            // uri
            // 
            this.uri.Location = new System.Drawing.Point(212, 41);
            this.uri.Name = "uri";
            this.uri.Size = new System.Drawing.Size(355, 20);
            this.uri.TabIndex = 14;
            // 
            // GenerateURI
            // 
            this.GenerateURI.AutoSize = true;
            this.GenerateURI.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.GenerateURI.Location = new System.Drawing.Point(123, 39);
            this.GenerateURI.Name = "GenerateURI";
            this.GenerateURI.Size = new System.Drawing.Size(83, 23);
            this.GenerateURI.TabIndex = 15;
            this.GenerateURI.Text = "Generate URI";
            this.GenerateURI.UseVisualStyleBackColor = true;
            this.GenerateURI.Click += new System.EventHandler(this.GenerateURI_Click);
            // 
            // btnDecode
            // 
            this.btnDecode.Location = new System.Drawing.Point(401, 40);
            this.btnDecode.Name = "btnDecode";
            this.btnDecode.Size = new System.Drawing.Size(75, 23);
            this.btnDecode.TabIndex = 16;
            this.btnDecode.Text = "Decode";
            this.btnDecode.UseVisualStyleBackColor = true;
            this.btnDecode.Click += new System.EventHandler(this.btnDecode_Click);
            // 
            // textToDecode
            // 
            this.textToDecode.Location = new System.Drawing.Point(487, 55);
            this.textToDecode.Name = "textToDecode";
            this.textToDecode.Size = new System.Drawing.Size(271, 20);
            this.textToDecode.TabIndex = 17;
            // 
            // GetProjects
            // 
            this.GetProjects.Location = new System.Drawing.Point(602, 438);
            this.GetProjects.Name = "GetProjects";
            this.GetProjects.Size = new System.Drawing.Size(71, 20);
            this.GetProjects.TabIndex = 18;
            this.GetProjects.Text = "GetProjects";
            this.GetProjects.UseVisualStyleBackColor = true;
            this.GetProjects.Click += new System.EventHandler(this.GetProjects_Click);
            // 
            // Edit
            // 
            this.Edit.Location = new System.Drawing.Point(6, 56);
            this.Edit.Name = "Edit";
            this.Edit.Size = new System.Drawing.Size(74, 20);
            this.Edit.TabIndex = 19;
            this.Edit.Text = "Edit";
            this.Edit.UseVisualStyleBackColor = true;
            this.Edit.Click += new System.EventHandler(this.Edit_Click);
            // 
            // Export
            // 
            this.Export.Location = new System.Drawing.Point(86, 56);
            this.Export.Name = "Export";
            this.Export.Size = new System.Drawing.Size(66, 20);
            this.Export.TabIndex = 20;
            this.Export.Text = "Export";
            this.Export.UseVisualStyleBackColor = true;
            this.Export.Click += new System.EventHandler(this.Export_Click);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.Export);
            this.groupBox1.Controls.Add(this.Edit);
            this.groupBox1.Location = new System.Drawing.Point(602, 474);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(163, 82);
            this.groupBox1.TabIndex = 21;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Tests with prerequisites";
            // 
            // label1
            // 
            this.label1.AutoEllipsis = true;
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(1, 21);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(104, 13);
            this.label1.TabIndex = 19;
            this.label1.Text = "List Of Physical Path";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // button3
            // 
            this.button3.AutoSize = true;
            this.button3.Location = new System.Drawing.Point(12, 70);
            this.button3.Name = "button3";
            this.button3.Size = new System.Drawing.Size(104, 23);
            this.button3.TabIndex = 18;
            this.button3.Text = "GetCurrentContext";
            this.button3.UseVisualStyleBackColor = true;
            this.button3.Click += new System.EventHandler(this.button3_Click);
            // 
            // ProjectPaths
            // 
            this.ProjectPaths.Location = new System.Drawing.Point(111, 18);
            this.ProjectPaths.Name = "ProjectPaths";
            this.ProjectPaths.Size = new System.Drawing.Size(672, 20);
            this.ProjectPaths.TabIndex = 20;
            this.ProjectPaths.Text = "D:\\PapeeteData\\Model\\GAB\\GAB.rpy|D:\\PapeeteData\\Model\\GAB\\GAB.rpy|D:\\PapeeteData\\" +
                "Model\\MlaProject.rpy";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(41, 47);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(64, 13);
            this.label2.TabIndex = 21;
            this.label2.Text = "List Of URN";
            // 
            // URNs
            // 
            this.URNs.Location = new System.Drawing.Point(111, 44);
            this.URNs.Multiline = true;
            this.URNs.Name = "URNs";
            this.URNs.Size = new System.Drawing.Size(672, 66);
            this.URNs.TabIndex = 22;
            this.URNs.Text = resources.GetString("URNs.Text");
            // 
            // MultiRequest
            // 
            this.MultiRequest.AutoSize = true;
            this.MultiRequest.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.MultiRequest.Controls.Add(this.Sort);
            this.MultiRequest.Controls.Add(this.URNs);
            this.MultiRequest.Controls.Add(this.label2);
            this.MultiRequest.Controls.Add(this.ProjectPaths);
            this.MultiRequest.Controls.Add(this.label1);
            this.MultiRequest.Location = new System.Drawing.Point(12, 562);
            this.MultiRequest.Name = "MultiRequest";
            this.MultiRequest.Size = new System.Drawing.Size(789, 129);
            this.MultiRequest.TabIndex = 23;
            this.MultiRequest.TabStop = false;
            this.MultiRequest.Text = "MultiRequest";
            // 
            // Sort
            // 
            this.Sort.Location = new System.Drawing.Point(17, 68);
            this.Sort.Name = "Sort";
            this.Sort.Size = new System.Drawing.Size(75, 23);
            this.Sort.TabIndex = 23;
            this.Sort.Text = "Sort";
            this.Sort.UseVisualStyleBackColor = true;
            this.Sort.Click += new System.EventHandler(this.Sort_Click);
            // 
            // PapeeteURI
            // 
            this.PapeeteURI.AutoSize = true;
            this.PapeeteURI.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.PapeeteURI.Controls.Add(this.GenerateURI);
            this.PapeeteURI.Controls.Add(this.uri);
            this.PapeeteURI.Controls.Add(this.projectname);
            this.PapeeteURI.Controls.Add(this.objectid);
            this.PapeeteURI.Controls.Add(this.objectname);
            this.PapeeteURI.Controls.Add(this.objecttype);
            this.PapeeteURI.Controls.Add(this.toolname);
            this.PapeeteURI.Location = new System.Drawing.Point(12, 426);
            this.PapeeteURI.Name = "PapeeteURI";
            this.PapeeteURI.Size = new System.Drawing.Size(573, 130);
            this.PapeeteURI.TabIndex = 24;
            this.PapeeteURI.TabStop = false;
            this.PapeeteURI.Text = "Uri";
            // 
            // button4
            // 
            this.button4.Location = new System.Drawing.Point(401, 69);
            this.button4.Name = "button4";
            this.button4.Size = new System.Drawing.Size(75, 23);
            this.button4.TabIndex = 25;
            this.button4.Text = "Encode";
            this.button4.UseVisualStyleBackColor = true;
            this.button4.Click += new System.EventHandler(this.button4_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(807, 684);
            this.Controls.Add(this.button4);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.GetProjects);
            this.Controls.Add(this.PapeeteURI);
            this.Controls.Add(this.MultiRequest);
            this.Controls.Add(this.button3);
            this.Controls.Add(this.textToDecode);
            this.Controls.Add(this.btnDecode);
            this.Controls.Add(this.ReadServerConfigurationFile);
            this.Controls.Add(this.variablePath);
            this.Controls.Add(this.GetVariable);
            this.Controls.Add(this.locale);
            this.Controls.Add(this.getCultureInfo);
            this.Controls.Add(this.result);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.GetAllvariables);
            this.Controls.Add(this.button2);
            this.Name = "Form1";
            this.Text = "Form1";
            this.groupBox1.ResumeLayout(false);
            this.MultiRequest.ResumeLayout(false);
            this.MultiRequest.PerformLayout();
            this.PapeeteURI.ResumeLayout(false);
            this.PapeeteURI.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button button2;
        private System.Windows.Forms.Button GetAllvariables;
        private System.Windows.Forms.TextBox result;
        private System.Windows.Forms.Button getCultureInfo;
        private System.Windows.Forms.TextBox locale;
        private System.Windows.Forms.Button GetVariable;
        private System.Windows.Forms.TextBox variablePath;
        private System.Windows.Forms.Button ReadServerConfigurationFile;
        private System.Windows.Forms.TextBox toolname;
        private System.Windows.Forms.TextBox objecttype;
        private System.Windows.Forms.TextBox objectname;
        private System.Windows.Forms.TextBox objectid;
        private System.Windows.Forms.TextBox projectname;
        private System.Windows.Forms.TextBox uri;
        private System.Windows.Forms.Button GenerateURI;
        private System.Windows.Forms.Button btnDecode;
        private System.Windows.Forms.TextBox textToDecode;
        private System.Windows.Forms.Button GetProjects;
        private System.Windows.Forms.Button Edit;
        private System.Windows.Forms.Button Export;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Button button3;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox ProjectPaths;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox URNs;
        private System.Windows.Forms.GroupBox MultiRequest;
        private System.Windows.Forms.Button Sort;
        private System.Windows.Forms.GroupBox PapeeteURI;
        private System.Windows.Forms.Button button4;
    }
}

