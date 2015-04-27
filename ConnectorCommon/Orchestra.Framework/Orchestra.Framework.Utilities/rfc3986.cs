// ----------------------------------------------------------------------------------------------------
// File Name: rfc3986.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities
{
    using System;
    using System.Collections.Specialized;
    using System.Runtime.InteropServices;
    using System.Text;

    ///<summary>
    /// Interface of <see cref="OrchestraEncoding"/> class
    ///</summary>
    [ComVisible(true)]
    [Guid("006AC1E7-D8C3-4E52-A16C-A75BC6794168")]
    [InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IOrchestraEncoding
    {
        ///<summary>
        /// Encodes a String according Orchestra Encoder (RFC 3986).
        ///</summary>
        ///<param name="s">String to encode.</param>
        ///<returns>A String containing the results of encoding the parameter <paramref name="s"/>. </returns>
        string EncodeString(string s);

        ///<summary>
        /// Decodes a String according Orchestra Encoder (RFC 3986).
        ///</summary>
        ///<param name="s">String to decode.</param>
        ///<returns>A String containing the results of decoding the parameter <paramref name="s"/>.</returns>
        string DecodeString(string s);
    }

    ///<summary>
    /// This class implements the coding for the artefact uri.
    /// Compliant to rfc 3986
    ///</summary>
    [ComVisible(true)]
    [Guid("76536F90-5B02-46C7-A5D9-90F1EBDC64A8")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("Orchestra.Encoding")]
    public class OrchestraEncoding : IOrchestraEncoding
    {
        #region IOrchestraEncoding Members
        ///<summary>
        /// Encodes a String according Orchestra Encoder (RFC 3986).
        ///</summary>
        ///<param name="s">String to encode.</param>
        ///<returns>A String containing the results of encoding the parameter <paramref name="s"/>. </returns>
        public string EncodeString(string s)
        {
            return Rfc3986.Encode(s);
        }

        ///<summary>
        /// Decodes a String according Orchestra Encoder (RFC 3986).
        ///</summary>
        ///<param name="s">String to decode.</param>
        ///<returns>A String containing the results of decoding the parameter <paramref name="s"/>.</returns>
        public string DecodeString(string s)
        {
            return Rfc3986.Decode(s);
        }
        #endregion

        ///<summary>
        /// Encodes a String according Orchestra Encoder (RFC 3986).
        ///</summary>
        ///<param name="s">String to encode.</param>
        ///<returns>A String containing the results of encoding the parameter <paramref name="s"/>. </returns>
        public static string SEncodeString(string s)
        {
            return Rfc3986.Encode(s);
        }

        ///<summary>
        /// Decodes a String according Orchestra Encoder (RFC 3986).
        ///</summary>
        ///<param name="s">String to decode.</param>
        ///<returns>A String containing the results of decoding the parameter <paramref name="s"/>.</returns>
        public static string SDecodeString(string s)
        {
            return Rfc3986.Decode(s);
        }
    }

    internal static class Rfc3986
    {
        private const char Escape = '%';
        private const byte PrefixEscape = 37;

        private static readonly long _DigitHi = HighBitmask('0', '9');
        private static readonly long _DigitLo = LowBitmask('0', '9');

        private static readonly long _HexHi = _DigitHi | HighBitmask('A', 'F') | HighBitmask('a', 'f');
        private static readonly long _HexLo = _DigitLo | LowBitmask('A', 'F') | LowBitmask('a', 'f');

        internal static string Decode(string value)
        {
            if (value == null)
            {
                return null;
            }
            int i = value.IndexOf('%');
            if (i < 0)
            {
                return value;
            }
            StringBuilder result = new StringBuilder(value.Substring(0, i));
            byte[] bytes = new byte[4];
            int receivedBytes = 0;
            int expectedBytes = 0;
// ReSharper disable ForControlVariableIsNeverModified
            for (int len = value.Length; i < len; i++)
// ReSharper restore ForControlVariableIsNeverModified
            {
                if (IsEscaped(value, i))
                {
                    char character = Unescape(value[i + 1], value[i + 2]);
                    i += 2;

                    if (expectedBytes > 0)
                    {
                        if ((character & 0xC0) == 0x80)
                        {
                            bytes[receivedBytes++] = (byte)character;
                        }
                        else
                        {
                            expectedBytes = 0;
                        }
                    }
                    else if (character >= 0x80)
                    {
                        if ((character & 0xE0) == 0xC0)
                        {
                            bytes[receivedBytes++] = (byte)character;
                            expectedBytes = 2;
                        }
                        else if ((character & 0xF0) == 0xE0)
                        {
                            bytes[receivedBytes++] = (byte)character;
                            expectedBytes = 3;
                        }
                        else if ((character & 0xF8) == 0xF0)
                        {
                            bytes[receivedBytes++] = (byte)character;
                            expectedBytes = 4;
                        }
                    }

                    if (expectedBytes > 0)
                    {
                        if (receivedBytes == expectedBytes)
                        {
                            switch (receivedBytes)
                            {
                                case 2:
                                    {
                                        result.Append((char)((bytes[0] & 0x1F) << 6 | bytes[1] & 0x3F));
                                        break;
                                    }
                                case 3:
                                    {
                                        result.Append(
                                            (char)((bytes[0] & 0xF) << 12 | (bytes[1] & 0X3F) << 6 | bytes[2] & 0x3F));
                                        break;
                                    }
                                case 4:
                                    {
                                        result.Append(
                                            ((bytes[0] & 0x7) << 18 | (bytes[1] & 0X3F) << 12 | (bytes[2] & 0X3F) << 6
                                             | bytes[3] & 0x3F));
                                        break;
                                    }
                            }
                            receivedBytes = 0;
                            expectedBytes = 0;
                        }
                    }
                    else
                    {
                        for (int j = 0; j < receivedBytes; ++j)
                        {
                            result.Append((char)bytes[j]);
                        }
                        receivedBytes = 0;
                        result.Append(character);
                    }
                }
                else
                {
                    for (int j = 0; j < receivedBytes; ++j)
                    {
                        result.Append((char)bytes[j]);
                    }
                    receivedBytes = 0;
                    result.Append(value[i]);
                }
            }
            return result.ToString();
        }

        internal static string Encode(string s)
        {
            return string.IsNullOrEmpty(s) ? string.Empty : Encoding.ASCII.GetString(EncodeToBytes(s));
        }

        internal static string GenerateQueryString(NameValueCollection parameters)
        {
            if (parameters == null)
            {
                return string.Empty;
            }

            StringBuilder returnedValue = new StringBuilder();

            bool addingParamSeparator = false;

            foreach (string key in parameters.AllKeys)
            {
                if (addingParamSeparator)
                {
                    returnedValue.Append("&");
                }
                else
                {
                    addingParamSeparator = true;
                }
                string encodedKey = Encode(key);
                string encodedValue = Encode(parameters.Get(key));
                returnedValue.Append(encodedKey).Append("=").Append(encodedValue);
            }
            return returnedValue.ToString();
        }

        internal static NameValueCollection ParseQueryString(string parameters)
        {
            NameValueCollection returnedCol = new NameValueCollection();

            if (string.IsNullOrEmpty(parameters))
            {
                return returnedCol;
            }
            string[] listParams = parameters.Split('&');

            foreach (string s in listParams)
            {
                string[] values = s.Split('=');

                if (values.Length != 2)
                {
                    throw new FormatException("Pair is not a key-value pair");
                }

                string key = Decode(values[0]);
                if (string.IsNullOrEmpty(key))
                {
                    throw new FormatException("Key cannot be null or empty");
                }

                string value = Decode(values[1]);

                returnedCol.Add(key, value);
            }
            return returnedCol;
        }

        private static byte[] EncodeToBytes(string s)
        {
            if (string.IsNullOrEmpty(s))
            {
                return new byte[0];
            }

            byte[] bytes = Encoding.UTF8.GetBytes(s);

            int unsafeChars = 0;
            foreach (byte b in bytes)
            {
                if (EscapeChar((char)b))
                {
                    unsafeChars++;
                }
            }

            if (unsafeChars == 0)
            {
                return bytes;
            }

            byte[] returnedBytes = new byte[bytes.Length + (unsafeChars * 2)];
            int position = 0;

            foreach (byte b in bytes)
            {
                if (EscapeChar((char)b))
                {
                    returnedBytes[position++] = PrefixEscape;
                    returnedBytes[position++] = (byte)IntegerToHexadecimal((b >> 4) & 0xf);
                    returnedBytes[position++] = (byte)IntegerToHexadecimal(b & 0x0f);
                }
                else
                {
                    returnedBytes[position++] = b;
                }
            }

            return returnedBytes;
        }

        private static bool EscapeChar(char c)
        {
            return !((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9'));
        }

        private static long HighBitmask(char from, char to)
        {
            return to < 64 ? 0 : LowBitmask((char)(from < 64 ? 0 : from - 64), (char)(to - 64));
        }

        private static char IntegerToHexadecimal(int i)
        {
            if (i > 9)
            {
                return (char)(i - 10 + 'A');
            }
            return (char)(i + '0');
        }

        private static bool IsEscaped(String s, int i)
        {
            return s[i] == Escape && s.Length > i + 2 && Matches(s[i + 1], _HexHi, _HexLo)
                   && Matches(s[i + 2], _HexHi, _HexLo);
        }

        private static long LowBitmask(char from, char to)
        {
            long result = 0L;
            if (from < 64 && from <= to)
            {
                to = (char)(to < 64 ? to : 63);
                for (char c = from; c <= to; c++)
                {
                    result |= (1L << c);
                }
            }
            return result;
        }

        private static bool Matches(char c, long highBitmask, long lowBitmask)
        {
            if (c >= 128)
            {
                return false;
            }
            return c < 64 ? ((1L << c) & lowBitmask) != 0 : ((1L << (c - 64)) & highBitmask) != 0;
        }

        private static char Unescape(char highHexDigit, char lowHexDigit)
        {
            return (char)((ValueOf(highHexDigit) << 4) | ValueOf(lowHexDigit));
        }

        private static int ValueOf(char hexDigit)
        {
            if (hexDigit >= 'A' && hexDigit <= 'F')
            {
                return hexDigit - 'A' + 10;
            }
            if (hexDigit >= 'a' && hexDigit <= 'f')
            {
                return hexDigit - 'a' + 10;
            }
            if (hexDigit >= '0' && hexDigit <= '9')
            {
                return hexDigit - '0';
            }
            return 0;
        }
    }
}