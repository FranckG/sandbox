// ----------------------------------------------------------------------------------------------------
// File Name: StringManipulation.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities
{
    using System;
    using System.Runtime.InteropServices;

    ///<summary>
    /// Class for string extraction
    ///</summary>
    [ComVisible(false)]
    [Obsolete]
    public sealed class stringManipulation
    {
        ///<summary>
        /// Extracts a string until a separator.
        ///</summary>
        ///<param name="mystring">string (passed by reference) to extract.</param>
        ///<param name="separator">string separator.</param>
        ///<returns>string until <paramref name="separator"/>.</returns>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// string s = "one|two|three|four";
        /// while (!string.IsNullOrEmpty(s))
        /// {
        ///     string tmp = stringManipulation.stringUntilSeparator(ref s, "|");
        ///     Console.WriteLine(tmp);
        /// }
        /// // Console output:
        /// // one
        /// // two
        /// // three
        /// // four
        /// </code>
        /// </example>
        public static string stringUntilSeparator(ref string mystring, string separator)
        {
            return stringUntilSeparator(ref mystring, separator, null);
        }

        ///<summary>
        /// Extracts a string until a separator.
        ///</summary>
        ///<param name="mystring">string (passed by reference) to extract.</param>
        ///<param name="separator">string separator.</param>
        ///<param name="escape">Escape string.</param>
        ///<returns>string until <paramref name="separator"/>.</returns>
        /// <example>
        /// <code>
        /// // C#
        /// // ----------------------------------------------
        /// string s = "one|two|##three|four##";
        /// while (!string.IsNullOrEmpty(s))
        /// {
        ///     string tmp = stringManipulation.stringUntilSeparator(ref s, "|", "##");
        ///     Console.WriteLine(tmp);
        /// }
        /// // Console output:
        /// // one
        /// // two
        /// // three|four
        /// </code>
        /// </example>
        public static string stringUntilSeparator(ref string mystring, string separator, string escape)
        {
            int firstDelimiterPosition = -1;
            int secondDelimiterPosition = -1;
            int separatorLength = separator.Length;
            int escapeLength;
            string retour;

            if (escape == null)
            {
                escapeLength = 0;
            }
            else
            {
                escapeLength = escape.Length;
            }

            if (separatorLength == 0)
            {
                return "";
            }

            if (escapeLength > 0 && escape != null)
            {
                firstDelimiterPosition = mystring.IndexOf(escape, StringComparison.Ordinal);
                if (firstDelimiterPosition > -1)
                {
                    secondDelimiterPosition = mystring.IndexOf(
                        escape + separator, firstDelimiterPosition + escapeLength, StringComparison.Ordinal);
                    if (secondDelimiterPosition == -1)
                    {
                        secondDelimiterPosition = mystring.IndexOf(
                            escape, firstDelimiterPosition + escapeLength, StringComparison.Ordinal);
                    }
                }
            }
            int separatorPosition = mystring.IndexOf(separator, StringComparison.Ordinal);
            if (secondDelimiterPosition > firstDelimiterPosition && separatorPosition > firstDelimiterPosition
                && separatorPosition < secondDelimiterPosition)
            {
                separatorPosition = mystring.IndexOf(
                    separator, secondDelimiterPosition + escapeLength, StringComparison.Ordinal);
            }
            if (separatorPosition > -1)
            {
                if (firstDelimiterPosition < secondDelimiterPosition && secondDelimiterPosition < separatorPosition)
                {
                    retour = mystring.Substring(escapeLength, secondDelimiterPosition - escapeLength);
                }
                else
                {
                    retour = mystring.Substring(0, separatorPosition);
                }
                mystring = mystring.Substring(separatorPosition + separatorLength);
            }
            else
            {
                if (firstDelimiterPosition < secondDelimiterPosition)
                {
                    retour = mystring.Substring(escapeLength, secondDelimiterPosition - escapeLength);
                }
                else
                {
                    retour = mystring;
                }
                mystring = null;
            }
            return retour;
        }
    }
}