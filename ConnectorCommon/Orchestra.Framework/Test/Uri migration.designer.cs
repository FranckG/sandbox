namespace Test
{
    partial class Uri_migration
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
            this.oldUri = new System.Windows.Forms.ComboBox();
            this.newUri = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // oldUri
            // 
            this.oldUri.FormattingEnabled = true;
            this.oldUri.Items.AddRange(new object[] {
            "papeete:///Doors//Orchestra+Database_b3cbf282%2FTest.Sylvain///",
            "papeete:///Doors//Orchestra+Database_b3cbf282%2FTest.Sylvain/View//000031e0%2110." +
                "+Document+view",
            "papeete:///Doors//Orchestra+Database_b3cbf282%2FTest.Sylvain/Object//000031e0%211" +
                "0.+Document+view%2118",
            "papeete:///Doors//Orchestra+Database_b3cbf282%2FTest.Sylvain/Object//000031e0%211" +
                "8",
            "papeete:///Doors//Orchestra+Database_b3cbf282%2FTest.Sylvain/Module//000031e0",
            "papeete:///Clearquest//prod/Query//Personal+Queries%2FMyPendingList",
            "papeete:///Clearquest//prod/Defect//Personal+Queries%2FMyPendingList%21prod000601" +
                "14",
            "papeete:///Clearquest//prod/Folder/Personal+Queries/",
            "papeete:///Clearquest//prod///",
            "papeete:///Clearquest//prod/Query//Personal+Queries%2F%3F+Find+FT+by+id/id%281_0%" +
                "29=prod00040630",
            "papeete:///Clearquest//prod/Query//Personal+Queries%2FFramework%2FCe+que+l%27on+a" +
                "ccept%E9+pour+la+V%3F/ProgProd_Deliv_Release%281_0%29=V5.0,+State%282_0%29=Accep" +
                "ted",
            "papeete:///Gimp//Images%2FMissing+You///"});
            this.oldUri.Location = new System.Drawing.Point(12, 12);
            this.oldUri.Name = "oldUri";
            this.oldUri.Size = new System.Drawing.Size(1094, 21);
            this.oldUri.TabIndex = 0;
            this.oldUri.SelectedIndexChanged += new System.EventHandler(this.oldUri_SelectedIndexChanged);
            // 
            // newUri
            // 
            this.newUri.Location = new System.Drawing.Point(12, 39);
            this.newUri.Name = "newUri";
            this.newUri.Size = new System.Drawing.Size(1094, 20);
            this.newUri.TabIndex = 1;
            // 
            // Uri_migration
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1118, 72);
            this.Controls.Add(this.newUri);
            this.Controls.Add(this.oldUri);
            this.Name = "Uri_migration";
            this.Text = "Uri_migration";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ComboBox oldUri;
        private System.Windows.Forms.TextBox newUri;
    }
}