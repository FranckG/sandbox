// ----------------------------------------------------------------------------------------------------
// File Name: IConnector.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Connector
{
    ///<summary>
    /// Example of interface of Connector
    /// Do not use directly (you must copy the code)
    ///</summary>
    public interface IConnector
    {
        ///<summary>
        ///</summary>
        ///<param name="contextString"> </param>
        ///<returns> status string </returns>
        string create(string contextString);

        ///<summary>
        ///</summary>
        ///<param name="contextString"> </param>
        ///<returns> status string </returns>
        string documentaryExport(string contextString);

        ///<summary>
        ///</summary>
        ///<param name="contextString"> </param>
        ///<returns> status string </returns>
        string executeSpecificCommand(string contextString);

        ///<summary>
        ///</summary>
        ///<param name="contextString"> </param>
        ///<returns> status string </returns>
        string expand(string contextString);

        ///<summary>
        ///</summary>
        ///<param name="contextString"> </param>
        ///<returns> status string </returns>
        string lmExport(string contextString);

        ///<summary>
        ///</summary>
        ///<param name="contextString"> </param>
        ///<returns> status string </returns>
        string navigate(string contextString);
    }
}