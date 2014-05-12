#Options
use strict;
# Moduls
use CQPerlExt;
use Tk;
use ModifyQueries;

my $date = "";
my $startTime = "";
my $endTime = "";
my $type = "";

my $user = "";
my $pass = "";
my $queryUser = "";

###### PMT 16-12-2013 - Connection the CQDB
my $db = "IVVQ";
my $dbset = "IVV";

my $sep = "<>-<";

my $error = "";

open(DATES, "Last_CCB_Schedule.txt");
my @ligne=<DATES>;
my($tmpd, $date) = split(/: /, @ligne[3]);
my $tmpdate = chomp($date);
my($tmps, $startTime) = split(/: /, @ligne[5]);
my $tmpstartTime = chomp($startTime);
my($tmps, $endTime) = split(/: /, @ligne[7]);
my $tmpendTime = chomp($endTime);
close(DATES);

#### TopLevel Creation ####
my $mw = MainWindow->new(-title => 'ClearQuest Queries modifier');

#### Enter date frame ####
my $frm_date = $mw->Frame(-relief=>'groove', -borderwidth=>2);

my $lbl1 = $frm_date->Label(-text=>'Enter Date (yyyy-mm-dd in GMT)');
my $entry_date = $frm_date->Entry(-textvariable=>\$date);

$lbl1->pack(-side=>'top', -anchor=>'nw');
$entry_date->pack(-fill=>'x');

#### Enter hour frame ####
my $frm_time = $mw->Frame(-relief=>'groove', -borderwidth=>2);

my $lbl2 = $frm_time->Label(-text=>'Entrer Start hour (hh:mm:ss in GMT)');
my $entry_start = $frm_time->Entry(-textvariable=>\$startTime);
my $lbl3 = $frm_time->Label(-text=>'Entrer End hour (hh:mm:ss in GMT)');
my $entry_end = $frm_time->Entry(-textvariable=>\$endTime);


$lbl2->pack(-side=>'top', -anchor=>'nw');
$entry_start->pack(-fill=>'x');
$lbl3->pack(-side=>'top', -anchor=>'nw');
$entry_end->pack(-fill=>'x');

#### Enter user frame ####
my $frm_user = $mw->Frame(-relief=>'groove', -borderwidth=>2);

my $lbl4 = $frm_user->Label(-text=>'Enter user who modified the queries during meeting');
my $entry_user = $frm_user->Entry(-textvariable=>\$queryUser);

$lbl4->pack(-side=>'top', -anchor=>'nw');
$entry_user->pack(-fill=>'x');

#### Create log frame ####
#my $text_log = $mw->Text(qw/-width 100 -height 15/);
#tie *STDOUT, ref $text_log, $text_log;

my $frm_log = $mw->Frame(-relief=>'groove', -borderwidth=>2);

my $text_log = $frm_log->Text(qw/-width 125 -height 25/);
tie *STDOUT, ref $text_log, $text_log;
#tie *STDERR, ref $text_log, $text_log;

# liaison entre les widgets
my $scroll = $frm_log->Scrollbar(-command => ['yview', $text_log]);
$text_log->configure(-yscrollcommand => ['set', $scroll]);

# disposition des widgets
$text_log->pack(-expand=>1,-side => 'left', -fill => 'both', -expand => 1);
$scroll->pack(-side => 'right', -fill => 'y');

#### Create action frame ####
my $frm_action = $mw->Frame(-relief=>'groove', -borderwidth=>2);

my $bt_ok = $frm_action->Button(-text=>'Modify queries', -command => \&Modify);
my $bt_exit   = $frm_action->Button(-text=>'Exit', -command => \&Exit);

$bt_exit->pack(-side => 'right',-fill=>'both', -ipadx => 20,-padx => 5 , -pady => 5  );
$bt_ok->pack(-side => 'right',-fill=>'both', -ipadx => 20,-padx => 5 , -pady => 5  );



#### Pack all frame ####
$frm_date->pack(-side=>'top', -fill=>'both');
$frm_time->pack(-fill=>'both');
$frm_user->pack(-fill=>'both');
$frm_log->pack(-expand=>1,-fill=>'both');
$frm_action->pack(-side=>'bottom', -fill=>'both');


#### Checking args ####
if($#ARGV == 0){
	$type=$ARGV[0];
	if($type ne "CCB" && $type ne "SCCB"){
		$bt_ok->configure(-state=>'disabled');
		print "ERROR :[Invalid command argument, must be \"CCB\" or \"SCCB\".]\n";
	} else {
		print "INFO :[This program modify $type queries.]\n";
	}
} else {
	$bt_ok->configure(-state=>'disabled');
	print "ERROR :[Invalid number of command argument, must be \"CCB\" or \"SCCB\".]\n";
}

MainLoop();

#### Subs ####
sub today{
    my $time = shift || time;           #$time par defaut vaut le time actuel
    my @tab_date =localtime($time); 
    #le mois renvoyé est compris entre 0 et 11 d'où la modification
    $tab_date[4] = $tab_date[4] + 1;    
    #L'annee renvoie est celle à partir de 1900 d'ou la modification
	$tab_date[5] = $tab_date[5] + 1900; 
    
    # On rajoute 0 si le chiffre est compris entre 1 et 9
    foreach  (@tab_date) {
      s/^(\d)$/0$1/;
    }

	my ($sec,$min,$hour,$day,$month,$year,$Wday,$Yday,$summer) = @tab_date;
         
    return "$year-$month-$day";
}

sub Modify{
	print ("INFO :[Checking parameters]\n");
	my $checked = 1;	
				
	#### Check date & time format ####
	if(length($date) != 10){ $checked = 0; print "ERROR :[Invalid date, unable to continue.]\n";}
	unless ($date =~ /^\d\d\d\d-\d\d-\d\d/) { $checked = 0; print "ERROR :[Invalid date, unable to continue.]\n";}
	unless ($startTime =~ /^\d\d:\d\d:\d\d/) { $checked = 0; print "ERROR :[Invalid start time, unable to continue.]\n";}
	unless ($endTime =~ /^\d\d:\d\d:\d\d/) { $checked = 0; print "ERROR :[Invalid end time, unable to continue.]\n";}
	
	#### Check if start is before end ####
	if($checked){
		my($sH,$sM,$sS) = split( /:/, $startTime );
		if($sH > 23){$checked = 0; print "ERROR :[Invalid start time, check HOUR value, unable to continue.]\n";}
		if($sM > 59){$checked = 0; print "ERROR :[Invalid start time, check MINUTE value, unable to continue.]\n";}
		if($sS > 59){$checked = 0; print "ERROR :[Invalid start time, check SECOND value, unable to continue.]\n";}
		
		my($eH,$eM,$eS) = split( /:/, $endTime );
		if($eH > 23){$checked = 0; print "ERROR :[Invalid end time, check HOUR value, unable to continue.]\n";}
		if($eM > 59){$checked = 0; print "ERROR :[Invalid end time, check MINUTE value, unable to continue.]\n";}
		if($eS > 59){$checked = 0; print "ERROR :[Invalid end time, check SECOND value, unable to continue.]\n";}	

		if( ($sH > $eH)||(($sH == $eH)&&($sM > $eM))||(($sH == $eH)&&($sM == $eM)&&($sS > $eS)) ){
			$checked = 0; print "ERROR :[Invalid times, start time must be ealier than end time, unable to continue.]\n";
		}	
	}
	#### Check if user name is empty ####
	if($queryUser eq ""){$checked = 0; print "ERROR :[Invalid user, user name must not be null, unable to continue.]\n";}
		
	#### Update view ####	
	$mw->update;
	
	#### get CQ params & check it ####
	if($checked){
		my $rep = `cqperl AccountLogin.pl $queryUser`;
		if($rep eq "cancel" || $rep eq "") {
			$checked = 0; print "ERROR :[You have to enter parameters for connection, unable to continue.]\n";
		} else {
			my @tabrep = split(/$sep/,$rep);
			if((@tabrep % 2) == 1){$tabrep[@tabrep] = "";}

			my %params = @tabrep;
			
			$user = $params{login};
			$pass = $params{pwd};

			#### Check params ####
			my $ret = "";
			$ret = `cqperl CheckConnection.pl $user $pass $db $dbset`;
			if($ret ne "")
			{
				chomp($ret);
				print "ERROR :[$ret]\n";
				return 1;
			}
			#my $session= CQSession::Build();
			#$session->UserLogon($user, $pass, $db, $dbset); 
			### CQ API exits programm if parameters are not valid.
			#CQSession::Unbuild($session);		
		}
	}
	$mw->update;	
	
	if($checked){	
		if($type eq "CCB"){
			ModifyQueries::ModifyCCBQueries($user, $pass, $db, $dbset,$queryUser,"$date $startTime", "$date $endTime");
		} elsif($type eq "SCCB"){
			ModifyQueries::ModifySCCBQueries($user, $pass, $db, $dbset,$queryUser,"$date $startTime", "$date $endTime");
		} else {
			print "ERROR :[Unable to dermine type of queries to modify, unable to continue.\n";
		}
	}
	
	print "INFO :[End of program.]\n";
}


sub Exit{
	exit(0);
}

