With Text_IO; Use Text_IO;
With ONEARMEDBANDIT; Use ONEARMEDBANDIT;
dddddddd
Package body ONEARMEDBANDIT is

   RESULT : BILLBOARD := (COIN, BELL, COIN, HORSESHOE);                  --**
   HAS_JUST_WON : BOOLEAN := false;                                      --**
   JACK_POT : BILLBOARD := (SEVEN, HORSESHOE, THIRTEEN,                  --**
                               FOUR_LEAVES_CLOVER);                      --**
   NUMBER : WHEEL_NUMBER := 1;                                           --**
   COMMAND : array (WHEEL_NUMBER) of WHEEL_NUMBER := (1,2,3,4);          --**
   Package ACCOUNT_IO is new INTEGER_IO (NUM => ACCOUNT);                --**
   Package WHEEL_IO is new INTEGER_IO (NUM => WHEEL_NUMBER);             --**
   Package FIGURES_IO is new ENUMERATION_IO (ENUM => FIGURES);

   Package body ALEA is

   -- ***************************************************************
   -- Implementation of the random draw.
   -- The "PERMANENT" task is activated at once
   -- in parallel with the procedure that imports the
   -- current "ALEA" package.
   -- The "PERMANENT" task simply increases simplement a counter
   -- and suggests the use of a "STOP" switch.
   -- When using the "STOP" switch thanks to the "RANDOM" function
   -- it displays the current value of the counter.
   -- ***************************************************************

     Task PERMANENT is
          Entry STOP (RETVAL : out NATURAL);
     End PERMANENT;

     Task body PERMANENT is
        COUNTER : NATURAL := NATURAL'first;
     Begin
       Loop
           If COUNTER = NATURAL'last
           then COUNTER := NATURAL'first;
           else COUNTER := COUNTER + 1;
           End if;
           Select
                 Accept STOP (RETVAL : out NATURAL) do
                        RETVAL := COUNTER;
                 End STOP;
                 Else
                        Null;
           End select;
       End loop;
     End PERMANENT;

     Function RANDOM (MIN : in INTEGER := 0;
                      MAX : in INTEGER := 1000) return INTEGER is
          DYNAMIC : INTEGER := MAX - MIN + 1;
          RANDOM : INTEGER;
     Begin
          PERMANENT.STOP (RANDOM);
          Return MIN + (RANDOM mod DYNAMIC);
     End RANDOM;
   End ALEA;


   Task type WHEEL is                                                    --**
        Entry THROW(NUM : in WHEEL_NUMBER ; FIGURE :  out FIGURES);      --**
   End WHEEL;                                                            --**
   Task body WHEEL is                                                    --**
      Begin                                                              --**
          Accept THROW (NUM : in WHEEL_NUMBER ;                          --**
                         FIGURE :  out FIGURES) do                       --**
                 FIGURE := FIGURES'val (ALEA.RANDOM(0,7));                              --**
          End THROW;                                                     --**
   End WHEEL;                                                            --**


   Procedure DISPLAY (RESULT : in BILLBOARD) is

   -- ************************************************************
   -- This procedure displays the four pictures contained
   -- in the global array variable "RESULT"
   -- ************************************************************

   Begin
    For WHEEL in WHEEL_NUMBER loop
        FIGURES_IO.PUT (RESULT(WHEEL));
         PUT ("    ");
    End loop;
     NEW_LINE;
   End DISPLAY;

   Function THREE_OF_A_KIND return BOOLEAN is

   -- ************************************************************
   -- This function returns the value TRUE if the result
   -- of the game is a trhee of a kind
   -- otherwise it reurn FALSE
   -- If A,B,C et D are the 4 obtained pictures
   -- we have a three of a kind  if (with a exclusive or#)
   -- ((A = B) and (A = C or# A = D)) or ((C = D) and (C = A or# C = B))
   -- ************************************************************

   Begin
    Return
    (((RESULT(1) = RESULT(2))  and
     ((RESULT(1) = RESULT(4)) xor (RESULT(1) = RESULT(3))))
     or
     ((RESULT(3) = RESULT(4))  and
     ((RESULT(3) = RESULT(1)) xor (RESULT(3) = RESULT(2)))));
   End THREE_OF_A_KIND;

   Function SQUARE return BOOLEAN is

   -- ************************************************************
   -- This function returns TRUE if the result of the game is
   -- a square (4 pictures out of 4 alike)
   -- otherwise it returns FALSE
   -- ************************************************************

   RETVAL : BOOLEAN := TRUE;
   Begin
     For WHEEL in 2 .. WHEEL_NUMBER'last
         loop
         RETVAL := RETVAL and (RESULT(WHEEL) = RESULT(1));
         End loop;
     Return RETVAL;
   End SQUARE;

   Procedure WON (ABET : in out ACCOUNT; FORTUNE : in out ACCOUNT) is

   -- ************************************************************
   -- This procedure manges and edits the money the player wins
   -- by using the following rule:
   --    the JACK_POT brings 10 times the bet
   --    the SQUARE brings 5 times the bet
   --    the THREE_OF_A_KIND brings 2 times the bet
   -- It then activates the 4 wheels
   -- the player starts again with a random result
   -- (This draw enables to win money but not to lose
   -- ************************************************************

   GAIN : ACCOUNT := 0;
   Begin
      HAS_JUST_WON := TRUE;
      If RESULT = JACK_POT then GAIN := 10 * ABET;
      Elsif SQUARE then GAIN := 5 * ABET;
      Else GAIN := 2 * ABET;
      End if;

      FORTUNE := FORTUNE + GAIN;

       PUT ("You have won   ");
      ACCOUNT_IO.PUT (GAIN);
       NEW_LINE;

      PUT ("Your fortune comes to a total of   ");
      ACCOUNT_IO.PUT (FORTUNE);
      NEW_LINE;

      PUT ("Automatic draw   ");
      NEW_LINE;
      NUMBER := 4;           -- automatic
      COMMAND := (1,2,3,4);  -- draw
      PLAY(ABET, FORTUNE);   -- of the 4 wheels

   End WON;

   Procedure PLAY (ABET : in out ACCOUNT; FORTUNE: in out ACCOUNT) is

   -- ************************************************************
   -- This procedure activates the n wheels required
   -- (n is given in the gloabl variable "NUMBER",
   -- The n wheels chosen by the player are in
   -- the global array variable "COMMAND".
   -- It then displays the final result,
   -- analyzes this result and manages the gains and the loses.
   -- ************************************************************

    Type RANG is access WHEEL;
    POINTED : array (WHEEL_NUMBER) of RANG := (Others => Null);
    CUR_FIGURE : FIGURES := BELL;

   Begin
    For NO_WHEEL in 1 .. NUMBER loop     --*
       POINTED(NO_WHEEL) := new WHEEL;  --* Elaboration de n tasks
    End loop;                            --* (by pointer)
                                         --*
    For NO_WHEEL in 1 .. NUMBER loop   -- activation of n tasks in parallel
       POINTED(NO_WHEEL).THROW(COMMAND(NO_WHEEL),CUR_FIGURE);
       RESULT(COMMAND(NO_WHEEL)) := CUR_FIGURE;
    End loop;

    DISPLAY(RESULT);

    If RESULT = JACK_POT or SQUARE or THREE_OF_A_KIND
    then WON (ABET,FORTUNE);
    else LOST (ABET,FORTUNE);
    End if;
    NEW_LINE;
   End PLAY;

   Procedure BET(FORTUNE : in out ACCOUNT; ABET : in out ACCOUNT) is

   -- ********************************************
   -- This procedure enables the player to enter
   -- how much money he wants to bet on the draw
   -- and  how many wheels (and which ones)
   -- this draw will be used
   -- ********************************************

   Begin
    PUT ("Your fortune comes to a total of : ");
    ACCOUNT_IO.PUT(FORTUNE);
    NEW_LINE;
    PUT("How much do you want to bet ? ");
    NEW_LINE;
    ACCOUNT_IO.GET (ABET);
    NEW_LINE;

    If ABET > FORTUNE then ABET := FORTUNE;
      PUT ("Bet depending on your credit, this is to say   ");
      ACCOUNT_IO.PUT (ABET);
      NEW_LINE;
    End if;

    If ABET > 0 then PUT ("How many wheels do you wish to use ? (only 1 to 4 is allowed)");
	NEW_LINE;
	WHEEL_IO.GET (NUMBER);
	NEW_LINE;

	If NUMBER = 4
	then COMMAND := (1,2,3,4);
	    NEW_LINE;
	else
            For INDEX in 1 .. NUMBER loop
                PUT ("Which wheel do you wish to use ? (only 1 to 4 is allowed)");
                NEW_LINE;
                WHEEL_IO.GET (COMMAND (INDEX));
            End loop;
        End if;
    End if;
   End BET;

   Procedure LOST (ABET : in ACCOUNT; FORTUNE : in out ACCOUNT) is

   -- **********************************************************
   -- This procedure manages and edits the losses of the player
   -- **********************************************************

   Begin
     If HAS_JUST_WON then HAS_JUST_WON := false;
        PUT ("You have lost   ");
	NEW_LINE;
     Else
            FORTUNE := FORTUNE - ABET;
            PUT ("You have lost   ");
            ACCOUNT_IO.PUT (ABET);
            NEW_LINE;
     End if;
   End LOST;

End ONEARMEDBANDIT;