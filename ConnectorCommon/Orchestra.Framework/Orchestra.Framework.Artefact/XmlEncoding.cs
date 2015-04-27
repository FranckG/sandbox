// ----------------------------------------------------------------------------------------------------
// File Name: XmlEncoding.cs
// Project: Orchestra.Framework.Artefact
// Copyright (c) Thales, 2014 - 2014. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Artefact
{
    using System;
    using System.Text;
    using System.Text.RegularExpressions;
    using System.Web;

    ///<summary>
    /// Uses to encode the GEF file
    ///</summary>
    internal static class XmlEncoding
    {
        private const string NullString = "null";
        private const string InvalidUnicodeCharDetected = "Invalid unicode char detected.";
        private const short InvalidUnicodeCharDetectedInt = 1;
        private const string InvalidXmlCharDetected = "Invalid Xml char detected.";
        private const short InvalidXmlCharDetectedInt = 2;

        private const string InvalidUnicodeCharDetectedAndInvalidXmlCharDetected =
            InvalidUnicodeCharDetected + "\r\n" + InvalidXmlCharDetected;

        private static readonly Encoding _ClassOfEncoding = Encoding.UTF8;
        private static readonly string _NameOfEncoding = _ClassOfEncoding.EncodingName;
        private static readonly Regex _LowerToUpper = new Regex("(%[0-9a-f][0-9a-f])", RegexOptions.Compiled);

        ///<summary>
        ///  Gets the encoding name
        ///</summary>
        internal static string Name
        {
            get
            {
                return _NameOfEncoding;
            }
        }

        ///<summary>
        ///  Gets the class of Encoding
        ///</summary>
        internal static Encoding Class
        {
            get
            {
                return _ClassOfEncoding;
            }
        }

        internal static string CheckString(string value, bool log)
        {
            if (string.IsNullOrEmpty(value))
            {
                return string.Empty;
            }
            StringBuilder s = new StringBuilder();
            foreach (char t in value)
            {
                if (t > 0xFFFD)
                {
                    if (log)
                    {
                        Gef.ErrorMessageCode = (short)(Gef.ErrorMessageCode | InvalidUnicodeCharDetectedInt);
                    }
                }
                else if (t < 0x20 && t != '\t' & t != '\n' & t != '\r')
                {
                    if (log)
                    {
                        Gef.ErrorMessageCode = (short)(Gef.ErrorMessageCode | InvalidXmlCharDetectedInt);
                    }
                }
                else
                {
                    s.Append(t);
                }
            }
            if (log)
            {
                switch (Gef.ErrorMessageCode)
                {
                    case 1:
                        Gef.ErrorMessage = InvalidUnicodeCharDetected;
                        break;
                    case 2:
                        Gef.ErrorMessage = InvalidXmlCharDetected;
                        break;
                    case 3:
                        Gef.ErrorMessage = InvalidUnicodeCharDetectedAndInvalidXmlCharDetected;
                        break;
                    default:
                        Gef.ErrorMessage = string.Empty;
                        break;
                }
            }
            return s.ToString();
        }

        ///<summary>
        ///  Decodes a String according Orchestra Xml Encoder (UTF8).
        ///</summary>
        ///<param name="s"> String to decode. </param>
        ///<returns> A String containing the results of decoding the parameter <paramref name="s" /> . </returns>
        internal static string Decode(string s)
        {
            return HttpUtility.UrlDecode(s);
        }

        ///<summary>
        ///  Encodes a String according Orchestra Xml Encoder (UTF8).
        ///</summary>
        ///<param name="s"> String to encode. </param>
        ///<returns> A String containing the results of encoding the parameter <paramref name="s" /> . </returns>
        internal static string Encode(string s)
        {
            string result = string.Empty;
            if (!string.IsNullOrEmpty(s) && !NullString.Equals(s, StringComparison.InvariantCultureIgnoreCase))
            {
                s = CheckString(s, true);
                s = HttpUtility.UrlEncode(s, _ClassOfEncoding);
                if (s != null)
                {
                    result = _LowerToUpper.Replace(s, UpperEncodedChar);
                    result = result.Replace("!", "%21");
                    result = result.Replace("'", "%27");
                    result = result.Replace("(", "%28");
                    result = result.Replace(")", "%29");
                }
            }
            return result;
        }

        private static string UpperEncodedChar(Match m)
        {
            string x = m.ToString();
            return x.ToUpper();
        }
    }
}