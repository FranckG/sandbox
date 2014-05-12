
use strict;
use Tk;
use Tk::Dialog;

my ($login) = @ARGV;
my $pwd = "";
my $sep = "<>-<";

#### TopLevel Creation ####
my $mw = MainWindow->new(-title => 'ClearQuest parameters');

#### Create creator  frame ####
my $frm_account = $mw->Frame(-relief=>'groove', -borderwidth=>2);

my $lbl_c = $frm_account->Label(-text=>'Account parameters',-anchor => 'w' );
my $lbl_clogin = $frm_account->Label(-text=>'Login:',-anchor => 'w' );
my $ent_clogin = $frm_account->Entry(-textvariable=>\$login);
my $lbl_cpass = $frm_account->Label(-text=>'Password:',-anchor => 'w' );
my $ent_cpass = $frm_account->Entry(-textvariable=>\$pwd ,-show => '*');


$lbl_c->grid(-row=>0, -column=>0, -sticky =>'w');
$lbl_clogin->grid(-row=>1, -column=>0, -sticky =>'w');
$ent_clogin->grid(-row=>1, -column=>1, -sticky =>'w');
$lbl_cpass->grid(-row=>2, -column=>0, -sticky =>'w');
$ent_cpass->grid(-row=>2, -column=>1, -sticky =>'w');



#### Create action frame ####
my $frm_action = $mw->Frame(-relief=>'groove', -borderwidth=>2);

my $bt_ok = $frm_action->Button(-text=>'OK', -command => \&ok);
my $bt_cancel = $frm_action->Button(-text=>'Cancel', -command => \&cancel);

$bt_cancel->pack(-side => 'right',-fill=>'both', -ipadx => 20,-padx => 5 , -pady => 5  );
$bt_ok->pack(-side => 'right',-fill=>'both', -ipadx => 20,-padx => 5 , -pady => 5  );


#### Pack all frame ####
$frm_account->pack(-side=>'top', -fill=>'both');
$frm_action->pack(-side=>'bottom', -fill=>'both');

MainLoop();

#### Subs ####
sub ok{
	print "login".$sep.$login.$sep.
		  "pwd".$sep.$pwd;
	exit 0;
}
sub cancel{
	print "cancel";
	exit 0;
}

