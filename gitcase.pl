use strict;
use warnings;

my $viewTag     = 'fgi_ProjectA_int';
my $customField = 'customfield_10010';
my $user        = 'jenkins';
my $password    = 'tcinteg';
my $integrationBranch = 'origin/develop'; # can be retrieve from Stash repo?
my $componentRootDir = 'Test_comp/Test_CCEnv';

print "SYNCHRONIZE WITH CLEARCASE UCM";

#use Env qw(GIT_COMMIT GIT_URL);
#########################################################################
# GET ENVIRONMENT
#my $commitId = "0c6f2cf570922152358d3359de839ad9d29c9561";
my $commitId = $ENV{'GIT_COMMIT'};
print "commit id: '${commitId}'\n";

#my $repoUrl = "http://150.2.38.125:7990/scm/ssp/wakeupclock.git";
my $repoUrl = $ENV{'GIT_URL'};
print "repository URL: '${repoUrl}'\n";

my $branch = $ENV{'GIT_BRANCH'};
print "Git branch: '${branch}'\n";

if ($branch ne $integrationBranch) {  # can be retrieve dynamically ?
  print "Not working on develop branch: nothing to Synch with ClearQuest";
  exit 0;
}

my ($projectKey, $repositorySlug) = $repoUrl =~ 'https{0,}://.+:\d+/scm/(.+)/(.+)\.git';
print "project key: '${projectKey}'\n";
print "repository slug: '${repositorySlug}'\n";


#########################################################################
# GET JIRA ISSUE
my $stashApi="http://vmo10113:7990/rest/api/1.0/projects/${projectKey}/repos/${repositorySlug}/commits/${commitId}";
my $curl=`curl -su  ${user}:${password} -H "Accept: application/json" -X GET $stashApi`;
#print  "Response from STASH:\n$curl\n";

#"attributes":{"jira-key":["SSP-39"]}}
#my $jiraId = "STOF-5";
my $jiraId;
if ($curl =~ /"attributes":{"jira-key":\["(.+-\d+)"\]}}/) {
  $jiraId = $1;
} else {
  die "Cannot find Jira issue\ncurl output:\n${curl}: $?";
}
print "Jira ID: '$jiraId'\n";

#########################################################################
# GET ClearQuest ID
my $jiraApi="http://vmo10113:8080/rest/api/2/issue/${jiraId}?fields=${customField}";
$curl=`curl -su  ${user}:${password} -H "Accept: application/json" -X GET $jiraApi`;
my $clearQuestId;
if ($curl =~ /"customfield_10010":"(.*)"}}/) {
  $clearQuestId = $1;
} else {
  die "Cannot find ClearQuest issue\ncurl output:\n${curl}: $?";
}
print "ClearQuest ID: '${clearQuestId}'\n";

my @args = {'cleartool', 'startview', $viewTag};
system(@args) == 0 or
  die "cannot startview $viewTag: $?";

@args = {'cleartool', 'setactivity', '-c', "link to Jira ${jiraId}", '-view', ${viewTag}, ${clearQuestId}}; 
system(@args) == 0 or
  die "cannot set activity '${clearQuestId}' in view '$viewTag': $?";

@args = {'git', 'clean', '-dfx'}; 
system(@args) == 0 or
  die "cannot git clean: $?";

# PREPARE SOURCE FOLDER
@args = {'git', 'reset', '--hard', 'HEAD'}; 
system(@args) == 0 or
  die "cannot git reset: $?";

exit;

# EXECUTE CLEARFSIMPORT
@args = {'clearfsimport', '-recurse', '-rmname', '-nsetevent', '.', "m:/${viewTag}/${componentRootDir}"}; 
system(@args) == 0 or
  die "cannot run clearfsimport: $?";

  
1;

__END__