JQL filter to create:
---------------------

- Name (for example: 'metrics_pcr')

- JQL Query: issuetype = "Problem Report" AND project = "<project_name>" AND component = "<component_name>"

- JQL Columns to retrieve, be carefull display columns order is important :
1 - Key
2 - Created
3 - Severity
4 - Status

- Important:
This filter must be setted as a 'Favorite' in Jira for allowing Orchestra framework to see/use it.