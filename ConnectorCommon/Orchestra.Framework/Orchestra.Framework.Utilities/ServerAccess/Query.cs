using System;
using System.Collections.Generic;
using System.Text;
using System.Web;

namespace Orchestra.Framework.ServerAccess
{
    internal class Query : IQuery
    {
        private const int StringBufferCapacity = 100;
        private string _artefactParameters;
        private string _clientToolName;
        private string _cmpa;
        private string _command;
        private string _context;
        private string _format;
        private string _objectId;
        private string _objectName;
        private string _objectType;
        private string _projectName;
        private string _readonly;
        private string _serviceParameters;
        private string _targetToolName;
        private string _targetToolVersion;
        private readonly Dictionary<string, string> _data = new Dictionary<string, string>();
        
        public Query(string command, string targetToolName,
                     string targetToolVersion, string projectName, string objectName,
                     string objectType, string objectId, string clientToolName,
                     string readOnly, string format, string cmpa, string context,
                     Dictionary<string, string> artefactParametersMap,
                     Dictionary<string, string> serviceParametersMap)
        {
            string serviceParameters = BuildParameters(serviceParametersMap);
            string artefactParameters = BuildParameters(artefactParametersMap);

            SetValues(command, targetToolName, targetToolVersion, projectName,
                      objectName, objectType, objectId, clientToolName, readOnly,
                      format, cmpa, context, artefactParameters, serviceParameters);
        }

        private static string BuildParameters(Dictionary<string, string> parametersMap)
        {
            string result = null;

            if (parametersMap != null)
            {
                StringBuilder concatParameters = new StringBuilder();

                foreach (string key in parametersMap.Keys)
                {
                    concatParameters.Append(key);
                    concatParameters.Append('=');
                    concatParameters.Append(parametersMap[key]);
                    concatParameters.Append('&');
                }

                int cpLength = concatParameters.Length - 1;
                if (cpLength > 0)
                {
                    // remove ending '&'
                    concatParameters.Length = cpLength;
                }

                result = concatParameters.ToString();
            }

            return result;
        }

        private void SetValues(string command, string targetToolName,
                               string targetToolVersion, string projectName, string objectName,
                               string objectType, string objectId, string clientToolName,
                               string readOnly, string format, string cmpa, string context,
                               string artefactParameters, string serviceParameters)
        {
            _command = command;
            _targetToolName = targetToolName;
            _targetToolVersion = targetToolVersion;
            _projectName = projectName;
            _objectName = objectName;
            _objectType = objectType;
            _objectId = objectId;
            _clientToolName = clientToolName;
            _readonly = readOnly;
            _format = format;
            _cmpa = cmpa;
            _context = context;
            _artefactParameters = artefactParameters;
            _serviceParameters = serviceParameters;

            CheckData();

            _data.Add(PapeeteHttpConsts.CommandNameKey, _command);
            _data.Add(PapeeteHttpConsts.ProjectNameKey, _projectName);
            _data.Add(PapeeteHttpConsts.TargetToolNameKey, _targetToolName);
            _data.Add(PapeeteHttpConsts.ToolNameKey, _clientToolName);
            _data.Add(PapeeteHttpConsts.ReadonlyKey, _readonly);

            if (!String.IsNullOrEmpty(_targetToolVersion))
            {
                _data.Add(PapeeteHttpConsts.TargetToolVersion, _targetToolVersion);
            }

            if (!String.IsNullOrEmpty(_objectName))
            {
                _data.Add(PapeeteHttpConsts.ObjectNameKey, _objectName);
            }

            if (!String.IsNullOrEmpty(_objectType))
            {
                _data.Add(PapeeteHttpConsts.ObjectTypeKey, _objectType);
            }

            if (!String.IsNullOrEmpty(_objectId))
            {
                _data.Add(PapeeteHttpConsts.ObjectIdKey, _objectId);
            }

            if (!String.IsNullOrEmpty(_format))
            {
                _data.Add(PapeeteHttpConsts.FormatKey, _format);
            }

            if (!String.IsNullOrEmpty(_context))
            {
                _data.Add(PapeeteHttpConsts.ContextKey, _context);
            }

            if (!String.IsNullOrEmpty(_artefactParameters))
            {
                _data.Add(PapeeteHttpConsts.ArtefactParametersKey, _artefactParameters);
            }

            if (!String.IsNullOrEmpty(_serviceParameters))
            {
                _data.Add(PapeeteHttpConsts.ServiceParametersKey, _serviceParameters);
            }
        }

        public string CreatePostQuery()
        {
            StringBuilder postQuery = new StringBuilder(StringBufferCapacity);

            bool isFirst = true;
            foreach(string k in _data.Keys)
            {
                if (isFirst)
                {
                    isFirst = false;
                }
                else
                {
                    postQuery.Append("&");
                }
                postQuery.Append(HttpUtility.UrlEncode(k));
                postQuery.Append("=");
                postQuery.Append(HttpUtility.UrlEncode(_data[k]));
            }

            string parameters = postQuery.ToString();
            string request = parameters;
            return request;
        }

        private void CheckData()
        {
            if (String.IsNullOrEmpty(_targetToolName))
            {
                throw new PapeeteException(PapeeteHttpConsts.HttpClientInitializationError,
                                           PapeeteHttpConsts.TargetToolNameKey + " is empty!");
            }

            if (String.IsNullOrEmpty(_command))
            {
                throw new PapeeteException(PapeeteHttpConsts.HttpClientInitializationError,
                                           PapeeteHttpConsts.CommandNameKey + " is empty!");
            }

            if (String.IsNullOrEmpty(_projectName))
            {
                throw new PapeeteException(PapeeteHttpConsts.HttpClientInitializationError,
                                           PapeeteHttpConsts.ProjectNameKey + " is empty!");
            }
        }
    }
}