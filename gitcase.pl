my $url="http://vmo10113:8080/rest/api/2/issue/STOF-5?fields=customfield_10010";
my $curl=`curl -su  jenkins:tcinteg -H "Accept: application/json" -X GET $url`;

`git clean -dfx`;
`git reset --hard HEAD`;

#print "$curl\n";
$curl =~ /"customfield_10010":"(.*)"}}/;
my $id = $1;
print "id = $id\n";
my $viewTag=fgi_ProjectA_int;
`cleartool startview $viewTag`;
print "clearfsimport -recurse -rmname -nsetevent . m:\$viewTag\Test_comp\Test_CCEnv"