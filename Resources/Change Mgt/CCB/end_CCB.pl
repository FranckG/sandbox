#Options
use strict;
#Moduls
use Tk;

my $endDate = todaydate(); #date d'aujourd'hui au format yyyy-mm-dd
my $endTime = todayhour(); #horaire de l'instant présent au format hh:mm:ss
my $error_msg = "";
my $title_msg = "";

#Récupération des infos de début de réunion dans le fichier annexe temporaire pour la réunion en cours...
open(DATES, "start_data_CCB.txt") || &error("No meeting in progress, to finish a meeting, plz start one before.", "Error");
my @ligne=<DATES>;
my $startDate = @ligne[0];
my $tmpDate = chop($startDate);
my $startTime = @ligne[1];
close(DATES);
unlink("start_data_CCB.txt"); #et suppression du fichier annexe temporaire

#vérification du format des dates et horaires  pour les données de début et fin de réunion
unless ($startDate =~ /^\d\d\d\d-\d\d-\d\d/) {&error("ERROR :[Invalid start date, unable to continue.]\n", "Error");}
unless ($endDate =~ /^\d\d\d\d-\d\d-\d\d/) {&error("ERROR :[Invalid end date, unable to continue.]\n", "Error");}
unless ($startTime =~ /^\d\d:\d\d:\d\d/) {&error("ERROR :[Invalid start time, unable to continue.]\n", "Error");}
unless ($endTime =~ /^\d\d:\d\d:\d\d/) {&error("ERROR :[Invalid end time, unable to continue.]\n", "Error");}

#vérification de la date, si celle de fin est postérieure à celle de début, il y a erreur et arrêt du script
my ($sY, $sM, $sD) = split(/-/, $startDate);
my ($eY, $eM, $eD) = split(/-/, $endDate);
if(($sY < $eY) || (($sY == $eY) && ($sM < $eM)) || (($sY == $eY) && ($sM == $eM) && ($sD < $eD))){
	&error("ERROR :[Invalid answer date, unable to continue.]\n", "Error");
	}
my ($sH, $sMi, $sS) = split(/:/, $startTime);
if($sH > 23){&error("ERROR :[Invalid start time, check HOUR value, unable to continue.]\n", "Error");}
if($sMi > 59){&error("ERROR :[Invalid start time, check MINUTE value, unable to continue.]\n", "Error");}
if($sS > 59){&error("ERROR :[Invalid start time, check SECOND value, unable to continue.]\n", "Error");}
my ($eH, $eMi, $eS) = split(/:/, $endTime);
if($eH > 23){&error("ERROR :[Invalid end time, check HOUR value, unable to continue.]\n", "Error");}
if($eMi > 59){&error("ERROR :[Invalid end time, check MINUTE value, unable to continue.]\n", "Error");}
if($eS > 59){&error("ERROR :[Invalid end time, check SECOND value, unable to continue.]\n", "Error");}
if(($sH > $eH) || (($sH == $eH) && ($sMi > $eMi)) || (($sH == $eH) && ($sMi == $eMi) && ($sS > $eS))){
	&error("ERROR :[Invalid global date, unable to continue.]\n", "Information");}

open(DATA, ">Last_CCB_Schedule.txt");
print DATA "For the last CCB\n-----------------\n\nIt was on: ", $startDate,
	"\n\nIt started at: ", $startTime, "\n\nIt finish at: ", $endTime; 
close(DATA);
&error("The meeting is now complete.", "Information");
#### Subs ####
sub todaydate
{
    my $time = shift || time;           #$time par defaut vaut le time actuel
    my @tab_date =gmtime($time); 
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

sub todayhour
{
    my $time = shift || time;           #$time par defaut vaut le time actuel
    my @tab_date =gmtime($time); 
    #le mois renvoyé est compris entre 0 et 11 d'où la modification
    $tab_date[4] = $tab_date[4] + 1;    
    #L'annee renvoie est celle à partir de 1900 d'ou la modification
	$tab_date[5] = $tab_date[5] + 1900; 
    
    # On rajoute 0 si le chiffre est compris entre 1 et 9
    foreach  (@tab_date) {
      s/^(\d)$/0$1/;
    }

	my ($sec,$min,$hour,$day,$month,$year,$Wday,$Yday,$summer) = @tab_date;
         
	return "$hour:$min:$sec";
}

sub error
{
	my @args = @_;
	$error_msg = @args[0];
	$title_msg = @args[1];
	#### TopLevel Creation ####
	my $mw = MainWindow->new(-title => $title_msg);
	$mw->minsize(qw(500 42));
	#### Enter error frame ####
	my $frm_error_msg = $mw->Frame(-relief=>'groove', -borderwidth=>3)->pack('-side'=>'top', -fill=>'x');
	my $entry_error = $frm_error_msg->Entry(-textvariable=>\$error_msg);
	my $bt_ok = $frm_error_msg->Button(-text=>'Ok', -command => \&Exit)->pack(-side=>'right', -fill=>'both', -ipadx=>20, -padx=>5, -pady=>5);
	$entry_error->pack(-fill=>'x');
	MainLoop();
	exit(0);
}

sub Exit{
	exit(0);
}