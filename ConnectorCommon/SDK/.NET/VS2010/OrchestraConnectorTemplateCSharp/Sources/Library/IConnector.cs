using System.Runtime.InteropServices;

namespace Orchestra.Framework.Connector.$toolname$
{
    [ComVisible(true), Guid("$guid2$"), InterfaceType(ComInterfaceType.InterfaceIsDual)]
    public interface IConnector
    {
        ///<summary>
        ///</summary>
        ///<param name="context"></param>
        ///<returns></returns>
        string create(string context);

        ///<summary>
        ///</summary>
        ///<param name="context"></param>
        ///<returns></returns>
        string documentaryExport(string context);

        ///<summary>
        ///</summary>
        ///<param name="context"></param>
        ///<returns></returns>
        string executeSpecificCommand(string context);

        ///<summary>
        ///</summary>
        ///<param name="context"></param>
        ///<returns></returns>
        string expand(string context);

        ///<summary>
        ///</summary>
        ///<param name="context"></param>
        ///<returns></returns>
        string lmExport(string context);

        ///<summary>
        ///</summary>
        ///<param name="context"></param>
        ///<returns></returns>
        string navigate(string context);

        ///<summary>
        ///</summary>
        ///<param name="context"></param>
        ///<returns></returns>
        string search(string context);
    }
}