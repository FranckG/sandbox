with SERVER_TYPES;
with Navdb_Types;
--
with lang.Primitifs;
with lang.Array_Iterator_Uint;
--
with Access_Provider_To_Datum;
with Perfos.Secondary.Controller.Serializable;

package SEC_DATA_MANAGER is

   --#-------------------------------------------------------------------------
   --#external type
   --#-------------------------------------------------------------------------

   K_Name_Sec_Data : constant lang.Primitifs.T_String;


private

   use lang.Primitifs;

   K_Name_Sec_Data         : constant lang.Primitifs.T_String := "FPLN.SEC";

end SEC_DATA_MANAGER;
