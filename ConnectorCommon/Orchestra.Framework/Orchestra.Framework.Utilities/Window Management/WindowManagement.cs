// ----------------------------------------------------------------------------------------------------
// File Name: WindowManagement.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities
{
    using System;
    using System.Runtime.InteropServices;
    using System.Windows.Forms;
    using ManagedWinapi.Windows;

    ///<summary>
    /// COM Interface for window management
    ///</summary>
    [ComVisible(true)]
    [Guid("347BDC9D-C336-441a-B4C4-A31A3AF33815")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IWindowManagement
    {
        ///<summary>
        /// Try to make a window front with the name of the process and a part of this window's title.
        ///</summary>
        ///<param name="nameOfExecutable">Name of the executable to make front</param>
        ///<param name="titleContains">String to find in title of window</param>
        [DispId(1)]
        void MakeWindowFront(
            [In] String nameOfExecutable, [In] [Optional] [DefaultParameterValue("")] String titleContains);
    }

    ///<summary>
    /// This class implements the Window management
    ///</summary>
    [ComVisible(true)]
    [Guid("A77D5F19-DB71-4bf5-9BA1-1FE872E39CC6")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("Orchestra.IWindowManagement")]
    public class WindowManagement : IWindowManagement
    {
        private readonly FrmWaitForNavigation _waitWindow;
        private String _nameOfExecutable;
        private String _titleContains;

        ///<summary>
        /// Constructor
        /// Display the "Wait" window
        ///</summary>
        public WindowManagement()
        {
            this._waitWindow = new FrmWaitForNavigation { LblMessage = { Text = "Wait for navigation." } };
            this._waitWindow.Show();
            this._waitWindow.Refresh();
        }

        #region IWindowManagement Members
        ///<summary>
        /// Try to make a window front with the name of the process and a part of this window's title.
        ///</summary>
        ///<param name="nameOfExecutable">Name of the executable to make front</param>
        ///<param name="titleContains">String to find in title of window</param>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// WindowManagement o = new WindowManagement(); //A top window appears with the wait message
        /// 
        /// // code for displaying the rhapsody artifact
        /// // ...
        /// 
        /// // When the artifact display is ready
        /// o.MakeWindowFront("rhapsody", strTitle);
        /// </code>
        /// </example>
        public void MakeWindowFront(String nameOfExecutable, String titleContains)
        {
            try
            {
                this._nameOfExecutable = nameOfExecutable.ToLower();
                this._titleContains = titleContains.ToLower();
                SystemWindow[] desktopWindows = SystemWindow.FilterToplevelWindows(this.IsRightProcess);
                foreach (SystemWindow window in desktopWindows)
                {
                    if (window.WindowState == FormWindowState.Minimized)
                    {
                        window.WindowState = FormWindowState.Normal;
                    }
#if DEBUG
                    EventLogOrchestra.WriteInfo(63000, "Mise en avant de la fenetre " + titleContains + " de " + nameOfExecutable, "Window Management");
#endif
                    SystemWindow.ForegroundWindow = window;
                }
            }
            catch (Exception)
            {
            }
            try
            {
                this._waitWindow.Hide();
                this._waitWindow.Close();
            }
            catch (Exception)
            {
            }
        }
        #endregion

        ///<summary>
        /// Try to make a window front with the name of the process.
        ///</summary>
        ///<param name="nameOfExecutable">Name of the executable to make front</param>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// WindowManagement o = new WindowManagement(); //A top window appears with the wait message
        /// 
        /// // code for displaying the rhapsody artifact
        /// // ...
        /// 
        /// // When the artifact display is ready
        /// o.MakeWindowFront("rhapsody"); // put the first visible window of the process rhapsody front.
        /// </code>
        /// </example>
        public void MakeWindowFront(String nameOfExecutable)
        {
            this.MakeWindowFront(nameOfExecutable, "");
        }

        private bool IsRightProcess(SystemWindow window)
        {
            try
            {
                return window.Process.ProcessName.ToLower().Equals(this._nameOfExecutable)
                       && window.Title.ToLower().Contains(this._titleContains) && window.Visible;
            }
            catch (Exception)
            {
                return false;
            }
        }
    }
}