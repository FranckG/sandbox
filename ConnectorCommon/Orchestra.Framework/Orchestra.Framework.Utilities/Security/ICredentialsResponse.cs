using System;
using System.Runtime.InteropServices;

namespace Orchestra.Framework.Utilities.Security
{
    /// <summary>
    /// COM Interface for Orchestra Credentials APi response.
    /// See <see cref="CredentialsResponse"/> for more informations.
    /// </summary>
    [ComVisible(true)]
    [Guid("46098988-3819-4E12-A2C5-7833013CFDEE")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface ICredentialsResponse
    {
        /// <summary>
        /// Returns a <see cref="ICredentials"/> interface that represents <see cref="Credentials"/>.
        /// </summary>
        /// <returns>
        /// A <see cref="ICredentials"/> that represents <see cref="Credentials"/> object.
        /// </returns>
        ICredentials Credentials();

        /// <summary>
        /// Returns a <see cref="CredentialsUIStatus"/> that represents the credentials dialogbox status.
        /// </summary>
        /// <returns>
        /// A <see cref="CredentialsUIStatus"/> that represents the credentials dialogbox status.
        /// </returns>
        CredentialsUIStatus CredentialsUIStatus();

        /// <summary>
        /// Returns <c>true</c> if an error occurs, otherwise <c>false</c>.
        /// </summary>
        /// <returns></returns>
        bool Error();

        /// <summary>
        /// Returns the error message if an error occurs.
        /// </summary>
        /// <returns></returns>
        string ErrorMessage();
        
        /// <summary>
        /// Returns a <see cref="T:System.String"/> that represents the current <see cref="CredentialsResponse"/> object.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"/> that represents the current <see cref="CredentialsResponse"/> object.
        /// </returns>
        string ToString();
    }
}
