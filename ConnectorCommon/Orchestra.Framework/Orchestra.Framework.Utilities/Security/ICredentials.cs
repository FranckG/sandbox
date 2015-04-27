using System;
using System.Runtime.InteropServices;
namespace Orchestra.Framework.Utilities.Security
{
    /// <summary>
    /// COM Interface for Orchestra Credentials object.
    /// See <see cref="Credentials"/> for more informations.
    /// </summary>
    [ComVisible(true)]
    [Guid("70D6AA39-7FDF-4F4D-8CDF-C13A152602DA")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface ICredentials
    {
        /// <summary>
        /// Returns a <see cref="T:System.String"/> that represents the login part of <see cref="Credentials"/> object.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"/> that represents the login part of <see cref="Credentials"/> object.
        /// </returns>
        string Login();

        /// <summary>
        /// Returns a <see cref="T:System.String"/> that represents the password part of <see cref="Credentials"/> object.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"/> that represents the password part of <see cref="Credentials"/> object.
        /// </returns>
        string Password();

        /// <summary>
        /// Returns a <see cref="T:System.String"/> that represents the current <see cref="Credentials"/> object.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"/> that represents the current <see cref="Credentials"/> object.
        /// </returns>
        string ToString();
    }
}
