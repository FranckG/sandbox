
my $url="http://vmo10113:8080/rest/api/2/issue/STOF-5?fields=customfield_10010";
my $curl=`curl -su  jenkins:tcinteg -H "Accept: application/json" -X GET $url`;

print "$curl\n";
if ($curl =~ /"customfield_10010":"(.*)"}}/) {
  print "$1";
}