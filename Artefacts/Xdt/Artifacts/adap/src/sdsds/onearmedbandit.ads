
Procedure DISPLAY2 (RESULT : in BILLBOARD);

Package ONEARMEDBANDIT2 is
   MIN_INT : constant := 0;
   MAX_INT : constant := 100;

   Subtype WHEEL_NUMBER is INTEGER range 1 .. 4;
   Subtype WHEEL_NUMBER1 is INTEGER range 1 .. 4;
   Subtype ACCOUNT is INTEGER range MIN_INT .. MAX_INT;
   Type FIGURES is (WATER_MELON, BELL, SEVEN, FOUR_LEAVES_CLOVER,
                       HORSESHOE, COIN, THIRTEEN, DIE);
Type BILLBOARD Is Array (WHEEL_NUMBER) Of FIGURES;

   Package ALEA Is
      Function RANDOM (MIN : In INTEGER :=0; MAX : In INTEGER := 1000) return INTEGER;
   End ALEA;

   Procedure DISPLAY (RESULT : in BILLBOARD);
   Function THREE_OF_A_KIND return BOOLEAN;
   Function SQUARE return BOOLEAN;
   Procedure WON (ABET : in out ACCOUNT; FORTUNE : in out ACCOUNT);
   Procedure PLAY (ABET : in out ACCOUNT; FORTUNE : in out ACCOUNT);
   Procedure BET (FORTUNE: in out ACCOUNT; ABET : in out ACCOUNT);
   Procedure LOST (ABET : in ACCOUNT; FORTUNE : in out ACCOUNT);

End ONEARMEDBANDIT;