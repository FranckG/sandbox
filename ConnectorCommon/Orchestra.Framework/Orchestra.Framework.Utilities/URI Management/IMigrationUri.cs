using System;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using System.Text;
using System.Text.RegularExpressions;
using System.Web;
using System.IO;
using Orchestra.Framework.Core;

namespace Orchestra.Framework.Utilities
{
    ///<summary>
    ///</summary>
    [ComVisible(true), Guid("ACB9FCE1-DA87-4d2c-A846-B8103735A507"), InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IMigrationUri
    {
        ///<summary>
        ///</summary>
        ///<param name="uriV4"></param>
        ///<returns></returns>
        string MigrateUriV4ToV5(string uriV4);
    }

    ///<summary>
    ///</summary>
    [ComVisible(true), Guid("3EB0EBA6-CC6E-4a31-8814-71C2EA6DE45E"), ClassInterface(ClassInterfaceType.None)]
    [ProgId("Orchestra.IMigrateUriV4ToV5")]
    public class MigrationUri : IMigrationUri
    {

        private static readonly List<String> ListOldImageTool = new List<string> { "Gimp", "Paint", "Photoeditor" };

        #region IMigrationUri Members

        ///<summary>
        ///</summary>
        ///<param name="uriV4"></param>
        ///<returns></returns>
        public string MigrateUriV4ToV5(string uriV4)
        {
            //UriV4 oldUri = new UriV4(uriV4);
            //Artefact newUri = new Artefact();
            //if (ListOldImageTool.Contains(oldUri.ToolName))
            //{
            //    string[] response = new Client.PapeeteClient().GetProjectPath(oldUri.ToolName,oldUri.ProjectName);
            //    if (response[0]=="200")
            //    {
            //        string fileExtension = Path.GetExtension(response[1]).ToLower().Substring(1);
            //        fileExtension = char.ToUpper(fileExtension[0]) + fileExtension.Substring(1);
            //        switch (fileExtension)
            //        {
            //            case "Jpg":
            //            case "Jpeg":
            //                newUri.RootType = "Jpg";
            //                break;
            //            case "Tif":
            //            case "Tiff":
            //                newUri.RootType = "Tiff";
            //                break;
            //            default:
            //                newUri.RootType = fileExtension;
            //                break;
            //        }
            //    }
            //    else
            //    {
            //        throw new ApplicationException(response[1]);
            //    }
            //}
            //else
            //    newUri.RootType = oldUri.ToolName;
            //newUri.RootName = oldUri.ProjectName;
            //newUri.Type = oldUri.ObjectType;

            //if (!String.IsNullOrEmpty(oldUri.Parameters))
            //{
            //    string[] parameters = oldUri.Parameters.Split(',');
            //    foreach (string s in parameters)
            //    {
            //        string[] values = s.Split('=');
            //        newUri.AddParameter(values[0].Trim(), values[1].Trim());
            //    }
            //}
            //string[] list;
            //switch (oldUri.ToolName)
            //{
            //    case "Doors":
            //        list = oldUri.ObjectId.Split('!');
            //        switch (list.Length)
            //        {
            //            case 1:
            //                newUri.Id = list[0];
            //                break;
            //            case 2:
            //                newUri.Id = oldUri.ObjectId;
            //                break;
            //            case 3:
            //                newUri.Id = list[0] + '!' + list[2];
            //                newUri.AddParameter("viewName", list[1]);
            //                break;
            //        }
            //        break;
            //    case "Clearquest":
            //        if (String.IsNullOrEmpty(oldUri.ObjectId))
            //            newUri.Id = oldUri.ObjectName;
            //        else
            //        {
            //            list = oldUri.ObjectId.Split('!');
            //            switch (list.Length)
            //            {
            //                case 1:
            //                    newUri.Id = list[0];
            //                    break;
            //                case 2:
            //                    newUri.Id = list[1];
            //                    newUri.AddParameter("queryName", list[0]);
            //                    break;
            //            }
            //        }
            //        break;
            //    default:
            //        if (String.IsNullOrEmpty(oldUri.ObjectId))
            //            newUri.Id = oldUri.ObjectName;
            //        else
            //            newUri.Id = oldUri.ObjectId;
            //        break;
            //}

            //return newUri.Uri;
            return "";
        }

        #endregion

        ///<summary>
        ///</summary>
        ///<param name="listOfUriV4"></param>
        ///<returns></returns>
        public Dictionary<string, string> MigrateUriV4ToV5(List<string> listOfUriV4)
        {
            Dictionary<string, string> urisV4UrisV5 = new Dictionary<string, string>();
            foreach (string uriV4 in listOfUriV4)
                urisV4UrisV5.Add(uriV4, MigrateUriV4ToV5(uriV4));
            return urisV4UrisV5;
        }



        #region Nested type: UriV4

        private class UriV4
        {
            public UriV4(string uri)
            {
                Regex validURI = new Regex(RegexURI);
                Encoding iso88591 = Encoding.GetEncoding(Iso88591);

                Match m = validURI.Match(uri);
                if (m.Success)
                {
                    ToolName = HttpUtility.UrlDecode(m.Groups[1].Value, iso88591);
                    ProjectName = HttpUtility.UrlDecode(m.Groups[3].Value, iso88591);
                    ObjectType = HttpUtility.UrlDecode(m.Groups[4].Value, iso88591);
                    ObjectName = HttpUtility.UrlDecode(m.Groups[5].Value, iso88591);
                    ObjectId = HttpUtility.UrlDecode(m.Groups[6].Value, iso88591);
                    Parameters = HttpUtility.UrlDecode(m.Groups[8].Value, iso88591);
                }
                else
                {
                    ToolName = "";
                    ProjectName = "";
                    ObjectType = "";
                    ObjectName = "";
                    ObjectId = "";
                    Parameters = "";
                }
            }

            #region Public Properties

            public string ToolName { get; private set; }

            public string ProjectName { get; private set; }

            public string ObjectType { get; private set; }

            public string ObjectName { get; private set; }

            public string ObjectId { get; private set; }

            public string Parameters { get; private set; }

            #endregion

            #region Private / Protected

            private const String Iso88591 = "iso-8859-1";
            private const String RegexURI = "^papeete:///([^/]*)/([^/]*)/([^/]*)/([^/]*)/([^/]*)/([^/]*)(/([^/]*))?";

            #endregion
        }

        #endregion
    }
}