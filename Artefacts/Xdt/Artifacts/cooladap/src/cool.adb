with lang.Abstract_Type;
--
with FMS_TYPES;
with SERVER_TYPES;
with BASIC_TYPES;


with BASIC_TYPES;

package Hover_Algorithm is

   --  class type
   type T_Hover_Algorithm is abstract new lang.Abstract_Type.T_Abstract_Type
   with private;
   type T_Hover_Algorithm_Class_Access is access all T_Hover_Algorithm'Class;

   type T_Hover_Algorithm is abstract new lang.Abstract_Type.T_Abstract_Type
   with
      record
         Config_Data   : SERVER_TYPES.T_Config_Data_Type_Access := null;
         Hover_Config    : T_Hover_Config;
         Get_Zaff        : T_Get_Zaff;
         Get_Ac_Roll_Max : T_Get_Ac_Roll_Max;
         Basic_Param     : T_Hover_Basic_Param;
         Mrk_Offset      : FMS_TYPES.T_DISTANCE_NM;
         Mrk_Fix         : SERVER_TYPES.T_FIX;
         Inserted_Wind   : SERVER_TYPES.T_AC_WIND :=
           (STATUS => FMS_TYPES.UNDEFINED);
         Wind            : SERVER_TYPES.T_AC_WIND :=
           (STATUS => FMS_TYPES.UNDEFINED);
         Height          : FMS_TYPES.T_AC_ALT_FT  := K_Default_Height;
         Dtra            : FMS_TYPES.T_DISTANCE_NM;
         Tdn_Pos         : FMS_TYPES.T_POSITION;
         Int_Pos         : FMS_TYPES.T_POSITION;
         Ltrl_Offset     : FMS_TYPES.T_FPLN_OFFSET_DISTANCE_NM_C;
         Area            : SERVER_TYPES.T_HOVER_AREA;
      end record;
end Hover_Algorithm ;