# --- fichier  ModifyQueries.pm --- #
package ModifyQueries;
use CQPerlExt;
use strict;

sub ModifyCCBQueries{

		my($user, $pass, $db, $dbset,$queryUser,@dateRange) = @_;
		
		print "INFO :[Connection] \n";
		my $session= CQSession::Build();
		$session->UserLogon($user, $pass, $db, $dbset); 
		my $workspace = $session->GetWorkSpace();
		
		my @prg = ("Orchestra");
		my @okind = ("Customer");
		my @oldstate = ("Accepted");
		my @userName = ($queryUser);

		
		### Query  ####
		my $queryName = "Public Queries/Orchestra/CCB/Report/Result/0_Processed_During_CCB";
		my $Qdef = $workspace->GetQueryDef($queryName); 
		my $DBID = $workspace->GetQueryDbId($queryName); 	
		my $operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);

		############################################
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);		
		$operator->BuildFilter("Originator_Kind", $CQPerlExt::CQ_COMP_OP_EQ,\@okind);
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";
		
		
		#### Query  ####
		$queryName = "Public Queries/Orchestra/CCB/Report/Result/1_Accepted_PCR";
		
		$Qdef = $workspace->GetQueryDef($queryName); 
		$DBID = $workspace->GetQueryDbId($queryName); 

		
		$operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		my @old_state = ("Analysed"); my @new_state = ("Accepted");
		############################################
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_EQ,\@old_state);
		$operator->BuildFilter("history.new_state", $CQPerlExt::CQ_COMP_OP_EQ,\@new_state);
		$operator->BuildFilter("Originator_Kind", $CQPerlExt::CQ_COMP_OP_EQ,\@okind);		
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################
		
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";
		
	
		#### Query  ####
		$queryName = "Public Queries/Orchestra/CCB/Report/Result/2_Postponed_PCR";
		
		$Qdef = $workspace->GetQueryDef($queryName); 
		$DBID = $workspace->GetQueryDbId($queryName); 

		my @ipriority = ("Postponed");
		$operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		############################################
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_EQ,\@old_state);
		$operator->BuildFilter("history.new_state", $CQPerlExt::CQ_COMP_OP_EQ,\@old_state);
		$operator->BuildFilter("Originator_Kind", $CQPerlExt::CQ_COMP_OP_EQ,\@okind);
		$operator->BuildFilter("Internal_priority", $CQPerlExt::CQ_COMP_OP_EQ,\@ipriority);
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################
		
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";
	
		
		#### Query  ####
		$queryName = "Public Queries/Orchestra/CCB/Report/Result/3_More_Info_Required_PCR";
		
		$Qdef = $workspace->GetQueryDef($queryName); 
		$DBID = $workspace->GetQueryDbId($queryName); 

		
		my @ipriority = ("More Info Requiered");
		$operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		############################################
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_EQ,\@old_state);
		$operator->BuildFilter("history.new_state", $CQPerlExt::CQ_COMP_OP_EQ,\@old_state);
		$operator->BuildFilter("Originator_Kind", $CQPerlExt::CQ_COMP_OP_EQ,\@okind);		
		$operator->BuildFilter("Internal_priority", $CQPerlExt::CQ_COMP_OP_EQ,\@ipriority);
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################		

		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";

		
		#### Query  ####
		$queryName = "Public Queries/Orchestra/CCB/Report/Result/4_Rejected_PCR";
		
		$Qdef = $workspace->GetQueryDef($queryName); 
		$DBID = $workspace->GetQueryDbId($queryName); 
		
		my @state = ("Closed");		
		$operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		############################################		
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_EQ,\@old_state);
		$operator->BuildFilter("Originator_Kind", $CQPerlExt::CQ_COMP_OP_EQ,\@okind);
		$operator->BuildFilter("history.new_state", $CQPerlExt::CQ_COMP_OP_EQ,\@state);		
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################		
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";

		
		#### Query  ####
		$queryName = "Public Queries/Orchestra/CCB/Report/Result/5_Closed_PCR";
		
		$Qdef = $workspace->GetQueryDef($queryName); 
		$DBID = $workspace->GetQueryDbId($queryName); 
		
		my @state = ("Closed");		
		$operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		############################################		
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_NEQ,\@state);
		$operator->BuildFilter("Originator_Kind", $CQPerlExt::CQ_COMP_OP_EQ,\@okind);
		$operator->BuildFilter("history.new_state", $CQPerlExt::CQ_COMP_OP_EQ,\@state);		
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################		
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";
}

sub ModifySCCBQueries{

		my($user, $pass, $db, $dbset,$queryUser,@dateRange) = @_;
		
		print "INFO :[Connection] \n";
		my $session= CQSession::Build();

		$session->UserLogon($user, $pass, $db, $dbset);

		my $workspace = $session->GetWorkSpace();

		#my @prg = ("Outil Spécifique");
		my @oldstate = ("Analysed");
		my @userName = ($queryUser);
		
		#### Query  ####
		my $queryName = "Public Queries/Orchestra/SCCB/Report/STATUS_ANALYSE/0_Processed_During_SCCB";
		

		my $Qdef = $workspace->GetQueryDef($queryName); 
		my $DBID = $workspace->GetQueryDbId($queryName); 

		my $operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		############################################
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		#$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_EQ,\@oldstate);
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";	
		
		
		#### Query  ####
		$queryName = "Public Queries/Orchestra/SCCB/Report/STATUS_ANALYSE/1_Accepted_PCR";
		
		$Qdef = $workspace->GetQueryDef($queryName); 
		$DBID = $workspace->GetQueryDbId($queryName); 

		$operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		@oldstate = ("Analysed");
		my @state = ("Accepted");
		############################################
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		#$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);		
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_EQ,\@oldstate);
		$operator->BuildFilter("State", $CQPerlExt::CQ_COMP_OP_EQ,\@state);
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";	
		
		
		#### Query  ####
		$queryName = "Public Queries/Orchestra/SCCB/Report/STATUS_ANALYSE/2_Postponed_PCR";

		$Qdef = $workspace->GetQueryDef($queryName); 
		$DBID = $workspace->GetQueryDbId($queryName); 

		$operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		@oldstate = ("Analysed");
		@state = ("Analysed");
		my @priority = ("Postponed");
		############################################
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		#$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);		
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_EQ,\@oldstate);
		$operator->BuildFilter("State", $CQPerlExt::CQ_COMP_OP_EQ,\@state);		
		$operator->BuildFilter("Internal_priority", $CQPerlExt::CQ_COMP_OP_EQ,\@priority);
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";	
		

		#### Query  ####
		$queryName = "Public Queries/Orchestra/SCCB/Report/STATUS_ANALYSE/3_More_Info_Required_PCR";

		$Qdef = $workspace->GetQueryDef($queryName); 
		$DBID = $workspace->GetQueryDbId($queryName); 

		$operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		@oldstate = ("Analysed");
		@state = ("Analysed");
		@priority = ("More Info Required");		
		############################################
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		#$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_EQ,\@oldstate);
		$operator->BuildFilter("State", $CQPerlExt::CQ_COMP_OP_EQ,\@state);
		$operator->BuildFilter("Internal_priority", $CQPerlExt::CQ_COMP_OP_EQ,\@priority);
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";	
		

		#### Query  ####
		$queryName = "Public Queries/Orchestra/SCCB/Report/STATUS_ANALYSE/4_Closed_PCR";

		$Qdef = $workspace->GetQueryDef($queryName); 
		$DBID = $workspace->GetQueryDbId($queryName); 

		$operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		@oldstate = ("Analysed");
		@state = ("Closed");		
		############################################
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		#$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_EQ,\@oldstate);
		$operator->BuildFilter("State", $CQPerlExt::CQ_COMP_OP_EQ,\@state);
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";	
		


		#### Query  ####
		$queryName = "Public Queries/Orchestra/SCCB/Report/STATUS_VERIFIED/0_Processed_During_SCCB";

		$Qdef = $workspace->GetQueryDef($queryName); 
		$DBID = $workspace->GetQueryDbId($queryName); 

		$operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		@oldstate = ("Verified");
		############################################
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		#$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_EQ,\@oldstate);
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";	
		

		#### Query  ####
		$queryName = "Public Queries/Orchestra/SCCB/Report/STATUS_VERIFIED/1_Closed_PCR";

		$Qdef = $workspace->GetQueryDef($queryName); 
		$DBID = $workspace->GetQueryDbId($queryName); 

		$operator = $Qdef->BuildFilterOperator($CQPerlExt::CQ_BOOL_OP_AND);
		@oldstate = ("Verified");		
		@state = ("Closed");		
		############################################
		$operator->BuildFilter("history.action_timestamp", $CQPerlExt::CQ_COMP_OP_BETWEEN, \@dateRange);
		#$operator->BuildFilter("Program_product", $CQPerlExt::CQ_COMP_OP_EQ,\@prg);
		$operator->BuildFilter("history.old_state", $CQPerlExt::CQ_COMP_OP_EQ,\@oldstate);
		$operator->BuildFilter("State", $CQPerlExt::CQ_COMP_OP_EQ,\@state);
		$operator->BuildFilter("history.user_name", $CQPerlExt::CQ_COMP_OP_EQ,\@userName);
		############################################
		print "INFO :[Update the query: $queryName]\n";
		$workspace->UpdateQueryDef($DBID, $Qdef);		
		print "INFO :[Update done]\n";	
		
}


1;