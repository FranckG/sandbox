
use CQPerlExt;
open(STDERR, ">&STDOUT");
my $session= CQSession::Build();
$session->UserLogon($ARGV[0], $ARGV[1], $ARGV[2], $ARGV[3]); 
CQSession::Unbuild($session);	
exit 0;