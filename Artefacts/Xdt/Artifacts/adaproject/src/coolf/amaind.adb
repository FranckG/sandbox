with GNAT.IO;  use GNAT.IO;
procedure amaind is
begin
   Put_Line ("Hello World!");
end amaind;

--File: SUBUNIT


package P is




      procedure Proc1;
 end P;

package body P is



      S: String := "Global variable";
      package Inner is
         procedure Proc2;
      end Inner;

      procedure Proc1 is separate; --Body stub
      package body Inner is separate; --Body stub

        --Body of Inner is illegal here
 end P;


 with Ada.Text_IO; use Ada.Text_IO;
      --Additional context clauses

 separate(P)

 procedure Proc1 is
      begin
         Put_Line(S & " visible from Proc1");
         Inner.Proc2;
 end Proc1;

separate(P)

-- hskjddsqgdsq

package body Inner2 is

   procedure Proc3 is separate;
         procedure Proc2 is separate;
end Inner;

with Ada.Text_IO; use Ada.Text_IO;

separate(P.Inner)


procedure Proc2 is
      begin
         Put_Line(S & " visible from Proc2");
end Proc2;

with P;
procedure Subunit is
      begin
         P.Proc1;
end Subunit;


