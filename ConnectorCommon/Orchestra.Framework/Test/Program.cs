using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace Test
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            //Application.Run(new Form1());
            //Application.Run(new Form2());
            //Application.Run(new Form3());
            Application.Run(new Form4());
            //Application.Run(new LoadContext());
            //Application.Run(new Status());
            //Application.Run(new TestPassword());
            //Application.Run(new Uri_migration());
        }
    }
}