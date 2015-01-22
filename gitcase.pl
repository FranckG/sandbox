my $url="http://vmo10113:8080/rest/api/2/issue/STOF-5?fields=customfield_10010";
my $curl=`curl -su  jenkins:tcinteg -H "Accept: application/json" -X GET $url`;

`git clean -dfx`;
`git reset --hard HEAD`;

#print "$curl\n";
my $id = $curl =~ /"customfield_10010":"(.*)"}}/;

my $viewTag=fgi_ProjectA_int;
`cleartool startview $viewTag`;
print "clearfsimport -recurse -rmname -nsetevent . m:\%VIEW_TAG%\Test_comp\Test_CCEnv"