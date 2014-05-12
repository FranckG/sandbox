with TEXT_IO; use TEXT_IO;
with ONEARMEDBANDIT; use ONEARMEDBANDIT;

Procedure SlotMachine is
      FORTUNE : ACCOUNT := 100;
      ABET : ACCOUNT := 1;
Begin
   While FORTUNE > 0 and ABET > 0 loop
	BET(FORTUNE,ABET);
	if (ABET > 0) Then
	   PLAY(ABET,FORTUNE);
        end if;
   End loop;
   pragma Audit_Instrum;
   PUT ("Ctrl+C or close window to end game");
End;