// ----------------------------------------------------------------------------------------------------
// File Name: ContextManagement.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Core
{
    using System;
    using System.IO;
    using System.Runtime.InteropServices;
    using System.Xml.Serialization;

    [ComVisible(true)]
    [Guid("0D49ABDB-8DE9-4BDA-9F91-1B8BA56DD9FA")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("Orchestra.ContextDefinition")]
    public partial class ContextDefinition : IContextDefinition
    {
        #region IContextDefinition Members
        ///<summary>
        /// Deserializes the XML document contained by the specified <see cref="ContextDefinition"/>.
        ///</summary>
        ///<param name="contextString"></param>
        ///<returns><c>true</c> if no error, <c>false</c> otherwise.</returns>
        public bool Load(string contextString)
        {
            TextReader reader = null;
            try
            {
                Type t = typeof(ContextDefinition);
                XmlSerializer serializer = new XmlSerializer(t);
                reader = new StringReader(contextString);
                ContextDefinition tmp = (ContextDefinition)serializer.Deserialize(reader);
                this.context = tmp.context;
            }
            catch (Exception)
            {
                return false;
            }
            finally
            {
                if (reader != null)
                {
                    reader.Close();
                }
            }
            return true;
        }

        ///<summary>
        /// Serializes context definition
        ///</summary>
        ///<returns>A String that represents the current Object. </returns>
        public override string ToString()
        {
            TextWriter writer = new StringWriterUTF8();
            XmlSerializer serializer = new XmlSerializer(typeof(ContextDefinition));
            XmlSerializerNamespaces xsns = new XmlSerializerNamespaces();
            xsns.Add("context", "http://www.thalesgroup.com/orchestra/framework/4_0_24/Context");
            serializer.Serialize(writer, this, xsns);
            string returnedValue = writer.ToString();
            writer.Close();
            return returnedValue;
        }
        #endregion
    }

    [ComVisible(true)]
    [Guid("6B9B12C9-1527-45A9-A649-31AC15C6F233")]
    [ClassInterface(ClassInterfaceType.None)]
    [ProgId("Orchestra.Context")]
    public partial class Context : IContext
    {
        #region IContext Members
        ///<summary>
        /// Gets the <see cref="Artefact"/> at the specified index.
        ///</summary>
        ///<param name="index">The zero-based index of the element to get.</param>
        ///<returns><see cref="Artefact">Artefact</see>></returns>
        public Artefact artefacts(int index)
        {
            return this.artefact[index];
        }

        ///<summary>
        /// Gets the number of <see cref="Artefact"/> actually contained in the <see cref="Context"/>.
        ///</summary>
        public int artefactCount
        {
            get
            {
                return this.artefact.Length;
            }
        }
        #endregion
    }
}