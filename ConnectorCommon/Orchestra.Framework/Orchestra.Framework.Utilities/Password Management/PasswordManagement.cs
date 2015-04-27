// ----------------------------------------------------------------------------------------------------
// File Name: PasswordManagement.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2013. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities
{
    using System;
    using System.IO;
    using System.Runtime.InteropServices;
    using System.Security;
    using System.Security.Cryptography;
    using System.Text;
    using Microsoft.Win32;

    ///<summary>
    /// This class manages the login/password for Orchestra application (ClearQuest, Doors, ...)
    ///</summary>
    [ComVisible(false)]
    [Obsolete("Use of this class for credentials management for Orchestra application is deprecated. Please use Credentials Orchestra API instead.")]    
    public class PasswordManagement
    {
        private readonly bool _confirmPassword;
        private readonly string _database;
        private readonly SecureString _privateKey;
        private readonly string _tool;
        private bool _decrypted;
        private LoginForm _myForm;
        private SecureString _passwordNotEncoded;
        private SecureString _userNameNotEncoded;

        ///<summary>
        /// PasswordManagement Contructor
        ///</summary>
        ///<param name="tool">tool name</param>
        ///<param name="database">database name</param>
        ///<param name="key">key to crypt/decrypt login and password</param>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// PasswordManagement oDoorsLogin = new PasswordManagement("Doors", "36777@gest1", "klsdqy_è-_è 43534dqsfte'atert");
        /// string userName = oDoorsLogin.UserName;
        /// string password = oDoorsLogin.Password;
        /// // Connect to Doors with userName/password
        /// </code>
        /// </example>
        public PasswordManagement(string tool, string database, string key)
            : this(tool, database, key, false)
        {
        }

        ///<summary>
        /// PasswordManagement Contructor
        ///</summary>
        ///<param name="tool">tool name</param>
        ///<param name="database">database name</param>
        ///<param name="key">key to crypt/decrypt login and password</param>
        ///<param name="confirmPassword">if true then user must confirm password</param>
        ///<example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// PasswordManagement oDoorsLogin = new PasswordManagement("Doors", "36777@gest1", "klsdqy_è-_è 43534dqsfte'atert", true);
        /// string userName = oDoorsLogin.UserName;
        /// string password = oDoorsLogin.Password;
        /// // Connect to Doors with userName/password
        /// </code>
        /// </example>
        public PasswordManagement(string tool, string database, string key, bool confirmPassword)
        {
            this._tool = tool;
            this._database = database;
            this._privateKey = new SecureString();
            foreach (char c in key)
            {
                this._privateKey.AppendChar(c);
            }
            this._privateKey.MakeReadOnly();
            this._userNameNotEncoded = new SecureString();
            this._passwordNotEncoded = new SecureString();
            this._confirmPassword = confirmPassword;
        }

        ///<summary>
        /// RootType UserName (decrypted)
        ///</summary>
        public string UserName
        {
            get
            {
                if (!this._decrypted)
                {
                    this.Decrypt();
                }
                return ConvertToUnsecureString(this._userNameNotEncoded);
            }
        }

        ///<summary>
        /// RootType Password (decrypted)
        ///</summary>
        public string Password
        {
            get
            {
                if (!this._decrypted)
                {
                    this.Decrypt();
                }
                return ConvertToUnsecureString(this._passwordNotEncoded);
            }
        }

        ///<summary>
        /// Method to reinitialize login
        ///</summary>
        /// <remarks>No verification provided</remarks>
        public void ReinitializeLogin()
        {
            this.DefineUserNameAndPassword(null);
        }

        ///<summary>
        /// Method to reinitialize login
        ///</summary>
        ///<param name="userName">User Name</param>
        ///<returns><c>true</c> if the user click "OK" button; otherwise, <c>false</c>.</returns>
        public bool ReinitializeLogin(string userName)
        {
            return this.DefineUserNameAndPassword(userName);
        }

        private static string ConvertToUnsecureString(SecureString securePassword)
        {
            if (securePassword == null)
            {
                throw new ArgumentNullException("securePassword");
            }

            IntPtr unmanagedString = IntPtr.Zero;
            try
            {
                unmanagedString = Marshal.SecureStringToGlobalAllocUnicode(securePassword);
                return Marshal.PtrToStringUni(unmanagedString);
            }
            finally
            {
                Marshal.ZeroFreeGlobalAllocUnicode(unmanagedString);
            }
        }

        private void Decrypt()
        {
            RegistryKey software = Registry.CurrentUser.OpenSubKey("Software");
            if (software != null)
            {
                using (
                    RegistryKey databaseKey =
                        software.OpenSubKey(@"Thales\Orchestra\" + this._tool + @"\" + this._database))
                {
                    if (ReferenceEquals(databaseKey, null))
                    {
                        this._userNameNotEncoded.Clear();
                        this._passwordNotEncoded.Clear();
                        this.DefineUserNameAndPassword(null);
                    }
                    else
                    {
                        Byte[] oldUserNameEncrypted = (Byte[])databaseKey.GetValue("UserName");
                        Byte[] oldPasswordEncrypted = (Byte[])databaseKey.GetValue("Password");
                        Byte[] userNameEncrypted = (Byte[])databaseKey.GetValue("bac");
                        Byte[] passwordEncrypted = (Byte[])databaseKey.GetValue("location");
                        bool needMigration = (oldPasswordEncrypted != null && oldUserNameEncrypted != null);
                        if ((userNameEncrypted == null || passwordEncrypted == null))
                        {
                            if (needMigration)
                            {
                                this.DecryptString(oldUserNameEncrypted, ref this._userNameNotEncoded, true);
                                this.DecryptString(oldPasswordEncrypted, ref this._passwordNotEncoded, true);
                                this.Encrypt();
                                using (
                                    RegistryKey databaseKeyForWriting =
                                        Registry.CurrentUser.CreateSubKey(
                                            @"Software\Thales\Orchestra\" + this._tool + @"\" + this._database))
                                {
                                    if (databaseKeyForWriting != null)
                                    {
                                        databaseKeyForWriting.DeleteValue("UserName");
                                        databaseKeyForWriting.DeleteValue("Password");
                                    }
                                }
                            }
                            else
                            {
                                this.DefineUserNameAndPassword(null);
                            }
                        }
                        else
                        {
                            this.DecryptString(userNameEncrypted, ref this._userNameNotEncoded);
                            this.DecryptString(passwordEncrypted, ref this._passwordNotEncoded);
                        }
                    }
                }
            }
            this._decrypted = true;
        }

        private void DecryptString(byte[] textToDecrypt, ref SecureString ss, bool oldMethod)
        {
            try
            {
                RijndaelManaged provider = new RijndaelManaged { Padding = PaddingMode.PKCS7 };
                Rfc2898DeriveBytes secretKey = Rfc2898DeriveBytesKeyGenerator(this._privateKey, oldMethod, this._database);
                ICryptoTransform decryptor = provider.CreateDecryptor(secretKey.GetBytes(32), secretKey.GetBytes(16));

                //Decrypt string
                MemoryStream memoryStream = new MemoryStream(textToDecrypt);
                CryptoStream cryptoStream = new CryptoStream(memoryStream, decryptor, CryptoStreamMode.Read);
                Byte[] plainText = new Byte[textToDecrypt.Length];
                int decryptedCount = cryptoStream.Read(plainText, 0, plainText.Length);
                memoryStream.Close();
                cryptoStream.Close();
                string decryptedString = Encoding.Unicode.GetString(plainText, 0, decryptedCount);
                foreach (char c in decryptedString)
                {
                    ss.AppendChar(c);
                }
            }
            catch (Exception)
            {
                throw new ApplicationException("Error during decryption. Please reset credentials.");
            }
        }

        private void DecryptString(Byte[] textToDecrypt, ref SecureString ss)
        {
            this.DecryptString(textToDecrypt, ref ss, false);
        }

        private bool DefineUserNameAndPassword(string userName)
        {
            bool ret = false;
            this._myForm = new LoginForm(this._confirmPassword)
                {
                    Text = "Login - " + this._tool,
                    lblDatabaseValue = { Text = this._database }
                };
            if (userName != null)
            {
                this._myForm.txtUserName.Text = userName;
                this._myForm.txtPassword.Select();
            }
            this._myForm.ShowDialog();
            if (this._myForm.ValidatedLoginForm)
            {
                this._userNameNotEncoded.Clear();
                foreach (char c in this._myForm.txtUserName.Text)
                {
                    this._userNameNotEncoded.AppendChar(c);
                }
                this._passwordNotEncoded.Clear();
                foreach (char c in this._myForm.txtPassword.Text)
                {
                    this._passwordNotEncoded.AppendChar(c);
                }
                this.Encrypt();
                ret = true;
            }
            this._myForm.Close();
            return ret;
        }

        private void Encrypt()
        {
            Byte[] userNameEncrypted = this.EncryptString(this._userNameNotEncoded);
            Byte[] passwordEncrypted = this.EncryptString(this._passwordNotEncoded);
            RegistryKey software = Registry.CurrentUser.OpenSubKey("Software", true);

            if (software != null)
            {
                RegistryKey databaseKey =
                    software.CreateSubKey(@"Thales\Orchestra\" + this._tool + @"\" + this._database);
                if (databaseKey != null)
                {
                    databaseKey.SetValue("bac", userNameEncrypted, RegistryValueKind.Binary);
                    databaseKey.SetValue("location", passwordEncrypted, RegistryValueKind.Binary);
                }
            }
        }

        private Byte[] EncryptString(SecureString textToEncrypt)
        {
            RijndaelManaged provider = new RijndaelManaged { Padding = PaddingMode.PKCS7 };
            Rfc2898DeriveBytes secretKey = Rfc2898DeriveBytesKeyGenerator(this._privateKey, false, this._database);
            ICryptoTransform encryptor = provider.CreateEncryptor(secretKey.GetBytes(32), secretKey.GetBytes(16));

            //Encrypt string
            MemoryStream memoryStream = new MemoryStream();
            CryptoStream cryptoStream = new CryptoStream(memoryStream, encryptor, CryptoStreamMode.Write);
            Byte[] textToEncryptBytes = Encoding.Unicode.GetBytes(ConvertToUnsecureString(textToEncrypt));
            cryptoStream.Write(textToEncryptBytes, 0, textToEncryptBytes.Length);
            cryptoStream.FlushFinalBlock();
            Byte[] cipherBytes = memoryStream.ToArray();
            memoryStream.Close();
            cryptoStream.Close();
            return cipherBytes;
        }

        private static Rfc2898DeriveBytes Rfc2898DeriveBytesKeyGenerator(
            SecureString password, bool oldMethod, string database)
        {
            string currentSid = Environment.UserName.ToLowerInvariant();
            database = database.ToLowerInvariant();
            byte[] salt =
                Encoding.ASCII.GetBytes(
                    oldMethod
                        ? ConvertToUnsecureString(password)
                        : ConvertToUnsecureString(password) + database + currentSid);
            const int MinimalSaltLength = 8;
            int nbByteToPad = MinimalSaltLength - salt.Length;
            if (nbByteToPad > 0)
            {
                byte[] paddedSalt = new byte[MinimalSaltLength];
                Buffer.BlockCopy(salt, 0, paddedSalt, 0, salt.Length);
                salt = paddedSalt;
            }
            Rfc2898DeriveBytes secretKey =
                new Rfc2898DeriveBytes(
                    oldMethod
                        ? ConvertToUnsecureString(password)
                        : ConvertToUnsecureString(password) + database + currentSid,
                    salt);
            return secretKey;
        }
    }
}