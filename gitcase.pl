use strict;
use warnings;

#use Env qw(GIT_COMMIT GIT_URL);
#########################################################################
# GET ENVIRONMENT
my $commitId = "0c6f2cf570922152358d3359de839ad9d29c9561";
#my $commitId = $ENV{'GIT_COMMIT'};
print "commit id: '${commitId}'\n";

my $repoUrl = "http://150.2.38.125:7990/scm/ssp/wakeupclock.git";
#my $repoUrl = $ENV{'GIT_URL'};
print "repository URL: '${repoUrl}'\n";


my ($projectKey, $repositorySlug) = $repoUrl =~ 'https{0,}://.+:\d+/scm/(.+)/(.+)\.git';
print "project key: '${projectKey}'\n";
print "repository slug: '${repositorySlug}'\n";

#########################################################################
# GET JIRA ISSUE
my $stashApi="http://vmo10113:7990/rest/api/1.0/projects/${projectKey}/repos/${repositorySlug}/commits/${commitId}";
my $curl=`curl -su  jenkins:tcinteg -H "Accept: application/json" -X GET $stashApi`;
#print  "Response from STASH:\n$curl\n";

#"attributes":{"jira-key":["SSP-39"]}}
#my $jiraId = "STOF-5";
my $jiraId;
if ($curl =~ /"attributes":{"jira-key":\["(.+-\d+)"\]}}/) {
  $jiraId = $1;
} else {
  print "Cannot find Jira issue\n";
  print "curl output:\n${curl}\n";
  exit 1;
}

print "Jira ID: '$jiraId'\n";

#########################################################################
# GET ClearQuest ID
my $jiraApi="http://vmo10113:8080/rest/api/2/issue/${jiraId}?fields=customfield_10010";
$curl=`curl -su  jenkins:tcinteg -H "Accept: application/json" -X GET $jiraApi`;
my $clearQuestId;
if ($curl =~ /"customfield_10010":"(.*)"}}/) {
  $clearQuestId = $1;
} else {
  print "Cannot find ClearQuest issue\n";
  print "curl output:\n${curl}\n";
  exit 1;
}
print "ClearQuest ID: '${clearQuestId}'\n";

my $viewTag = 'fgi_ProjectA_int';
`cleartool startview $viewTag`;
`cleartool setactivity -c "link to Jira ${jiraId}" -view ${viewTag} ${clearQuestId}`;

`git clean -dfx`;

exit;

# PREPARE SOURCE FOLDER
`git reset --hard HEAD`;

# EXECUTE CLEARFSIMPORT
print "clearfsimport -recurse -rmname -nsetevent . m:/${viewTag}/Test_comp/Test_CCEnv";

1;

__END__