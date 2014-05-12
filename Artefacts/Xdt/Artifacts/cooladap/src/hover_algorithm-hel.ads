with Hover_Algorithm;
--
with SERVER_TYPES;

package Hover_Algorithm.Hel is

   --  class type
   type T_Hover_Algorithm_Hel is new Hover_Algorithm.T_Hover_Algorithm with
      private; 

   type T_Hover_Algorithm_Hel is new Hover_Algorithm.T_Hover_Algorithm with
      record
         Mrk_Prim_Fix  : Server_Types.T_FIX;
         Get_Dtra_Algo : T_Get_Dtra_Algo := null;
      end record;

end Hover_Algorithm.Hel;

