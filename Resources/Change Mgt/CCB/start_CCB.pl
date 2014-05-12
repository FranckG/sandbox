#Options
use strict;
#Moduls
use Tk;

my $date = todaydate();
my $startTime = todayhour();
my $error_msg = "";
my $title_msg = "";
my $endTime = "";
my $queryUser = "";

if (-e "start_data_CCB.txt")
{
	&error("A meeting has already begun. Plz, wait until the end of this one before start another one.", "Error");
}
unlink("Last_CCB_Schedule.txt"); # suppression du fichier de fin de réunion précédente car ouverture d'une nouvelle réunion
open(DATES, ">start_data_CCB.txt");
print DATES $date, "\n", $startTime;
close(DATES);
&error("The meeting has now begun.", "Information");
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