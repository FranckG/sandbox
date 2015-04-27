using Orchestra.Framework.ServerAccess;

namespace Orchestra.Framework.Utilities.ServerAccess
{
    class RawProtocolQuery : IQuery
    {
        private readonly string _query;

        public RawProtocolQuery(string queryP)
        {
            _query = queryP;
        }

        public string CreatePostQuery()
        {
            return _query;
        }
    }
}
