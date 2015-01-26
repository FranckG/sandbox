import os
import requests, json
import re
import subprocess 


viewTag     = 'fgi_ProjectA_int';
customField = 'customfield_10010';
user        = 'jenkins';
password    = 'tcinteg';
integrationBranch = 'origin/develop'; # can be retrieve from Stash repo?
componentRootDir = 'Test_comp/Test_CCEnv';


#########################################################################
# GET ENVIRONMENT
gitCommit = "8f5f090ea5d977d5d3097818b3980715d9553a70";
#gitCommit = os.environ['GIT_COMMIT']
print ("git commit: '"+gitCommit+"'")

repoUrl = "http://150.2.38.125:7990/scm/ssp/wakeupclock.git";
#repoUrl = os.environ['GIT_URL']
print ("repository URL: '"+repoUrl+"'")

#branch = os.environ['GIT_BRANCH'];
branch = integrationBranch
print ("Git branch: '"+branch+"'")

import sys
if branch != integrationBranch:   # can be retrieve dynamically ?
  print ("Not working on develop branch: nothing to sync with ClearQuest")
  sys.exit(0)


matchObj=re.match('https{0,}://.+:\d+/scm/(.+)/(.+)\.git', repoUrl)
if matchObj:
  projectKey = matchObj.group(1)
  repositorySlug =  matchObj.group(2)
else:
  print ('No match')

print ("project key:     '"+projectKey+"'")
print ("repository slug: '"+repositorySlug+"'")


#########################################################################
# GET JIRA ISSUE
url = "http://vmo10113:7990/rest/api/1.0/projects/"+projectKey+"/repos/"+repositorySlug+"/commits/"+gitCommit
r = requests.get(url, auth=(user, password))
jsonResponse = r.json()
#print json.dumps(jsonResponse, indent=4, sort_keys=True)
jiraId = jsonResponse['attributes']['jira-key'][0]
print ("Jira ID: '"+jiraId+"'")

#########################################################################
# GET ClearQuest ID
url="http://vmo10113:8080/rest/api/2/issue/"+jiraId+"?fields="+customField
r = requests.get(url, auth=(user, password))
jsonResponse = r.json()
#print json.dumps(jsonResponse, indent=4, sort_keys=True)
clearQuestId = jsonResponse['fields']['customfield_10010']
print ("ClearQuest ID: '"+clearQuestId+"'")

return_code = subprocess.call(['cleartool', 'startview', viewTag], shell=True)
return_code = subprocess.call(['cleartool', 'setactivity', '-c', "link to Jira '"+jiraId+"'", '-view', viewTag, clearQuestId], shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)

# PREPARE SOURCE FOLDER
return_code = subprocess.call(['git', 'clean', '-dfx'], shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)


sys.exit(0)

return_code = subprocess.call(['git', 'reset', '--hard', 'HEAD'], shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)

# EXECUTE CLEARFSIMPORT
return_code = subprocess.call(['clearfsimport', '-recurse', '-rmname', '-nsetevent', '.', os.path.join('M:', viewTag, componentRootDir)], shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
