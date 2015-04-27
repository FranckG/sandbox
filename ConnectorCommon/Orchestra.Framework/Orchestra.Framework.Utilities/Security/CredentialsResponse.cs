// ----------------------------------------------------------------------------------------------------
// File Name: CredentialsResponse.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2014 - 2014. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities.Security
{
    using System.Runtime.InteropServices;

    /// <summary>
    /// COM Interface for Orchestra Credentials APi response.
    /// See <see cref="CredentialsResponse"/> for more informations.
    /// </summary>
    [ComVisible(true)]
    [Guid("DF1A14C8-CE9E-4F9A-ABAE-DDD8E9AE58BF")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("OrchestraClientForTool.CredentialsResponse")]
    public class CredentialsResponse : ICredentialsResponse
    {
        ///<summary>
        /// <see cref="Credentials"/> part of  <see cref="CredentialsResponse"/>
        ///</summary>
        private readonly Credentials _credentials;

        ///<summary>
        /// <see cref="int"/> that represents the credentials dialogbox result status.
        ///</summary>
        private readonly CredentialsUIStatus _credentialsUIStatus;

        private bool _error;
        private string _message;

        /// <summary>
        ///  CredentialsResponse Contructor
        /// </summary>
        /// <param name="credentials"></param>
        /// <param name="credentialsUIStatus"></param>
        /// <example>
        ///  <code>
        ///  // C#
        ///  // ----------------------------------------------
        ///  Credentials credentials = new Credentials("MyLogin", "MyPassword");
        ///  string login = credentials.login;
        ///  string password = credentials.password;
        ///  </code>
        ///  </example>
        public CredentialsResponse(Credentials credentials, CredentialsUIStatus credentialsUIStatus)
        {
            this._credentials = credentials;
            this._credentialsUIStatus = credentialsUIStatus;
            this._error = false;
        }

        #region ICredentialsResponse Members
        /// <summary>
        /// Returns a <see cref="ICredentials"/> interface that represents <see cref="Credentials"/>.
        /// </summary>
        /// <returns>
        /// A <see cref="ICredentials"/> that represents <see cref="Credentials"/> object.
        /// </returns>
        public ICredentials Credentials()
        {
            return this._credentials;
        }

        /// <summary>
        /// Returns a <see cref="CredentialsUIStatus"/> that represents the credentials dialogbox status.
        /// </summary>
        /// <returns>
        /// A <see cref="CredentialsUIStatus"/> that represents the credentials dialogbox result status.
        /// </returns>
        public CredentialsUIStatus CredentialsUIStatus()
        {
            return this._credentialsUIStatus;
        }

        /// <summary>
        /// Returns <c>true</c> if an error occurs, otherwise <c>false</c>.
        /// </summary>
        /// <returns></returns>
        public bool Error()
        {
            return this._error;
        }

        /// <summary>
        /// Returns the error message if an error occurs.
        /// </summary>
        /// <returns></returns>
        public string ErrorMessage()
        {
            return this._message;
        }

        /// <summary>
        /// Returns a <see cref="T:System.String"/> that represents the current <see cref="CredentialsResponse"/> object.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"/> that represents the current <see cref="CredentialsResponse"/> object.
        /// </returns>
        public override string ToString()
        {
            return "CredentialsResponse: " + (this._credentials == null ? "null" : this._credentials.ToString())
                   + ", credentialsDialogBoxUIStatus: " + this._credentialsUIStatus;
        }
        #endregion

        internal void SetError(bool error, string message)
        {
            this._error = error;
            this._message = message;
        }
    }

    /// <summary>
    /// Credentials UI DialogBox status.
    /// </summary>
    [ComVisible(true)]
    public enum CredentialsUIStatus
    {
        ///<summary>
        ///Status undefined (error ?)
        ///</summary>
        UNDEFINED = -2,

        ///<summary>
        ///Dialog box for credentials data entry was not shown cause data identification informations for 'credentials id' provided has already been entered by the user.
        ///</summary>
        NOT_DISPLAYED = -1,

        ///<summary>
        ///Dialog box for credentials data entry was shown cause data identification informations for 'credentials id' provided has not already been entered by the user.
        ///The user chose to leave the dialog without entering anything.
        ///</summary>
        DISPLAYED_AND_CLOSE = 0,

        ///<summary>
        ///Dialog box for credentials data entry was shown cause data identification informations for 'credentials id' provided has not already been entered by the user.
        ///The user has entered the credentials data and click the OK button.
        ///</summary>
        DISPLAYED_AND_OK = 1,

        ///<summary>
        ///Dialog box for credentials data entry was shown cause data identification informations for 'credentials id' provided has not already been entered by the user.
        ///The user finally click the CANCEL button.
        ///</summary>
        DISPLAYED_AND_CANCEL = 2
    }
}