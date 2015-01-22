my $jiraId = "STOF-5";
my $url="http://vmo10113:8080/rest/api/2/issue/$jiraId?fields=customfield_10010";
my $curl=`curl -su  jenkins:tcinteg -H "Accept: application/json" -X GET $url`;

`git clean -dfx`;
`git reset --hard HEAD`;

#print "$curl\n";
$curl =~ /"customfield_10010":"(.*)"}}/;
my $cqProd = $1
print "cqProd = $cqProd\n";
my $viewTag=fgi_ProjectA_int;
`cleartool startview $viewTag`;
print "clearfsimport -recurse -rmname -nsetevent . m:\$viewTag\Test_comp\Test_CCEnv"