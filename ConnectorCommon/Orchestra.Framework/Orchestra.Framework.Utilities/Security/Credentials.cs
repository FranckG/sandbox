// ----------------------------------------------------------------------------------------------------
// File Name: Credentials.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2014 - 2014. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities.Security
{
    using System.Runtime.InteropServices;

    ///<summary>
    /// This class manages the credentials data (login/password) for Orchestra application (ClearQuest, Doors, ...)
    ///</summary>
    [ComVisible(true)]
    [Guid("87161E8E-D1CC-4D1F-B402-4039757A4C06")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("OrchestraClientForTool.Credentials")]
    public class Credentials : ICredentials
    {
        ///<summary>
        /// login part of the Credentials
        ///</summary>
        private readonly string _login;

        ///<summary>
        /// password part of the Credentials
        ///</summary>
        private readonly string _password;

        ///<summary>
        /// credentials Contructor
        ///</summary>
        ///<param name="login">login/username</param>
        ///<param name="password">paswword</param>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// Credentials credentials = new Credentials("MyLogin", "MyPassword");
        /// string login = credentials.login;
        /// string password = credentials.password;
        /// </code>
        /// </example>
        public Credentials(string login, string password)
        {
            this._login = login;
            this._password = password;
        }

        #region ICredentials Members
        /// <summary>
        /// Returns a <see cref="T:System.String"/> that represents the login part of <see cref="Credentials"/> object.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"/> that represents the login part of <see cref="Credentials"/> object.
        /// </returns>
        public string Login()
        {
            return this._login;
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"/> that represents the password part of <see cref="Credentials"/> object.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"/> that represents the password part of <see cref="Credentials"/> object.
        /// </returns>
        public string Password()
        {
            return this._password;
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"/> that represents the current <see cref="Credentials"/> object.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"/> that represents the current <see cref="Credentials"/> object.
        /// </returns>
        public override string ToString()
        {
            return "Credentials: login='" + (this._login ?? "null") + "'; password='" + (this._password ?? "null") + "'";
        }
        #endregion
    }
}