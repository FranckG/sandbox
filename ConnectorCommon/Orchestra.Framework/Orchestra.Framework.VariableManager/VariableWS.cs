// ----------------------------------------------------------------------------------------------------
// File Name: VariableWS.cs
// Project: Orchestra.Framework.VariableManager
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.VariableManager.VariableManagerServer
{
    using System;
    using System.Text;

    public partial class VariableWS
    {
        /// <summary>
        /// Returns a <see cref="T:System.String"/> that represents the current <see cref="T:System.Object"/>.
        /// </summary>
        /// <returns>
        /// A <see cref="T:System.String"/> that represents the current <see cref="T:System.Object"/>.
        /// </returns>
        /// <filterpriority>2</filterpriority>
        public override string ToString()
        {
            const String variableFormat = "Variable {0}\n\tName {1}\n\tDescription: {2}\n\tMulti valued: {3}\n\t{4}";
            StringBuilder message = new StringBuilder();
            message.AppendFormat(
                variableFormat,
                this.path,
                this.name,
                this.description,
                this.multiValued,
                String.Join("\n\t", this.values));
            return message.ToString();
        }
    }
}