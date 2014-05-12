--#----------------------------------------------------------------------------
--#         COPYRIGHT BY THALES AVIONICS ALL RIGHTS RESTRICTED
--#----------------------------------------------------------------------------
--# DESCRIPTION :
--#----------------------------------------------------------------------------
--  visibility on required modules :
with Fix;
with Leg;
with Fpln;
with Server;
with Legs;
with Arrival;
--  visibility on objects required by nested operation bodies :
with Basic_Types;             use Basic_Types;
with Fms_Types;
with Basic_Algo;
with Navdb_Types;
with Fpln.Mission_Controller;
with Mission;
with Lang.Primitifs;
with General_Fms_Types;
--
with Ctc.Factory_Singleton;
with Ctc.Ctc_Controller;
with Ctc.Factory;
with Ctc.Airport_Info;

package body Fplns_Ctrl is

   --
   G_Actv_Fpln         : Server_Types.T_Primary_Fpln;
   G_Actv_Fpln_Chg     : Server_Types.T_Fpln_Modif_Origin;
   G_Actv_Wind_Profile : Wind.Wind_Profile.T_Wind_Profile;
   --
   G_Tmpy_Fpln         : Server_Types.T_Primary_Fpln;
   G_Tmpy_Fpln_Chg     : Server_Types.T_Fpln_Modif_Origin;
   G_Tmpy_Wind_Profile : Wind.Wind_Profile.T_Wind_Profile;
   --
   G_Sec_Fpln         : Server_Types.T_Primary_Fpln;
   G_Sec_Fpln_Chg     : Server_Types.T_Fpln_Modif_Origin;
   G_Sec_Link         : Server_Types.T_Fpln_Link;
   G_Sec_Wind_Profile : Wind.Wind_Profile.T_Wind_Profile;
   --
   G_Recov_Fpln         : Server_Types.T_Primary_Fpln;
   G_Recov_Wind_Profile : Wind.Wind_Profile.T_Wind_Profile;
   --
   G_Trans_Alt_Sec_To_Be_Modified : Boolean;
   --
   procedure Update
     (Actv_Fpln : in Server_Types.T_Primary_Fpln;
      Tmpy_Fpln : in Server_Types.T_Primary_Fpln;
      Sec_Fpln  : in Server_Types.T_Primary_Fpln);

   procedure Send_Actv_Fpln_Events
     (New_Fpln : in Server_Types.T_Primary_Fpln;
      Old_Fpln : in Server_Types.T_Primary_Fpln;
      Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin);

   procedure Send_Tmpy_Fpln_Events
     (New_Fpln : in Server_Types.T_Primary_Fpln;
      Old_Fpln : in Server_Types.T_Primary_Fpln;
      Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin);

   procedure Send_Sec_Fpln_Events
     (Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin);

   procedure Send_Fplns_Events
     (New_Actv_Fpln : in Server_Types.T_Primary_Fpln;
      Old_Actv_Fpln : in Server_Types.T_Primary_Fpln);

   procedure Send_Offset_Events
     (New_Fpln : in Server_Types.T_Primary_Fpln;
      Old_Fpln : in Server_Types.T_Primary_Fpln);

   procedure Send_Actv_Airports_Events
     (New_Fpln : in Server_Types.T_Primary_Fpln;
      Old_Fpln : in Server_Types.T_Primary_Fpln);

   procedure Update_Sec_With_Actv
     (Old_Actv_Fpln : in Server_Types.T_Primary_Fpln;
      Actv_Fpln     : in Server_Types.T_Primary_Fpln;
      Actv_Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin);

   procedure Update_Sec_Proc_With_Actv
     (Old_Actv_Fpln      : in Server_Types.T_Primary_Fpln;
      New_Actv_Fpln      : in Server_Types.T_Primary_Fpln;
      Old_Sec_Fpln       : in Server_Types.T_Primary_Fpln;
      Matching_Leg_Index : in Server_Types.T_Leg_Index;
      Departure          : out Server_Types.T_Departure;
      Arrival            : out Server_Types.T_Arrival);

   procedure Update_Sec_Arrival_Transalt
     (Arrival_Actv : in Server_Types.T_Arrival);

   -----------------------------------------------
   -----------------------------------------------
   procedure Put_Trans_Alt_Sec_To_Be_Modified
     (Trans_Alt_Modified : in Boolean)
   is
   begin
      G_Trans_Alt_Sec_To_Be_Modified := Trans_Alt_Modified;
   end Put_Trans_Alt_Sec_To_Be_Modified;

   -----------------------------------------------
   -----------------------------------------------
   procedure Init
     (Fplns_Resources : in
        Fplns_Resources_Factory.T_Resources_Factory_Class_Access)
   is

      use Server_Types;
      use Os.Abstract_Semaphore;

      L_Actv_Fpln        : Server_Types.T_Primary_Fpln;
      L_Tmpy_Fpln        : Server_Types.T_Primary_Fpln;
      L_Sec_Fpln         : Server_Types.T_Primary_Fpln;
      L_Actv_Fpln_Chg    : Server_Types.T_Fpln_Modif_Origin;
      L_Tmpy_Fpln_Chg    : Server_Types.T_Fpln_Modif_Origin;
      L_Sec_Fpln_Chg     : Server_Types.T_Fpln_Modif_Origin;
      L_Request_Status   : Boolean;
      L_Sec_Wind_Profile : Wind.Wind_Profile.T_Wind_Profile;

   begin

      -- int semaphores
      G_Actv_Sem :=
         Fplns_Resources_Factory.Get_Actv_Semaphore (Fplns_Resources.all);
      G_Tmpy_Sem :=
         Fplns_Resources_Factory.Get_Tmpy_Semaphore (Fplns_Resources.all);
      G_Sec_Sem  :=
         Fplns_Resources_Factory.Get_Sec_Semaphore (Fplns_Resources.all);

      -- Initialize the last used marker to 0
      Leg.Reset;

      -- Initialize the configuration parameters of fpln.mission_controller
      Fpln.Mission_Controller.Initialize
        (A_Get_Perfo_Data  => Server.Get_Perfo_Data'Access,
         A_Get_Config_Data => Server.Get_Config_Data'Access);

      -- Initialize wind profiles
      Wind.Wind_Profile.Initialize (G_Actv_Wind_Profile);
      Wind.Wind_Profile.Initialize (G_Tmpy_Wind_Profile);
      Wind.Wind_Profile.Initialize (G_Sec_Wind_Profile);
      Wind.Wind_Profile.Initialize (G_Recov_Wind_Profile);

      -- initialize the secondary flight plan or recover saved secondary
      --flight plan
      Server.Get_Sec_Fpln_Saved (L_Sec_Fpln, L_Request_Status);

      -- Recover the wind profile structure from the secondary fpln
      Wind.Wind_Profile.Initialize (L_Sec_Wind_Profile);
      Get_Sec_Wind_Profile (L_Sec_Wind_Profile);
      Wind.Wind_Profile.Recover
        (This      => L_Sec_Wind_Profile,
         Legs_Fpln => L_Sec_Fpln.Legs);
      Put_Sec_Wind_Profile (L_Sec_Wind_Profile);

      if L_Request_Status then
         L_Sec_Fpln_Chg :=
           (Lateral_Modif  => Server_Types.Initialization,
            Vertical_Modif => Server_Types.Undef,
            Refresh_Modif  => Server_Types.Undef,
            Modified_Elt   => Fpln.Get_From_Leg_Marker (L_Sec_Fpln));
      else
         Fpln.Reset (L_Sec_Fpln, L_Sec_Fpln_Chg);
      end if;

      -- initialize the active flight plan
      Fpln.Reset (L_Actv_Fpln, L_Actv_Fpln_Chg);

      -- cancel the temporary flight plan
      Fpln.Cancel (L_Tmpy_Fpln, L_Tmpy_Fpln_Chg);

      -- Updates the temporary waypoints and last used marker
      Update (L_Actv_Fpln, L_Tmpy_Fpln, L_Sec_Fpln);

      V_Get_Token (This => G_Actv_Sem);

      G_Actv_Fpln     := L_Actv_Fpln;
      G_Actv_Fpln_Chg := L_Actv_Fpln_Chg;

      V_Free_Token (This => G_Actv_Sem);

      V_Get_Token (This => G_Tmpy_Sem);

      G_Tmpy_Fpln     := L_Tmpy_Fpln;
      G_Tmpy_Fpln_Chg := L_Tmpy_Fpln_Chg;

      V_Free_Token (This => G_Tmpy_Sem);

      V_Get_Token (This => G_Sec_Sem);

      G_Sec_Fpln     := L_Sec_Fpln;
      G_Sec_Fpln_Chg := L_Sec_Fpln_Chg;
      G_Sec_Link     := Server_Types.Not_Linked;

      V_Free_Token (This => G_Sec_Sem);

      Server.Update_Traj_Actv_Fpln (L_Actv_Fpln, L_Actv_Fpln_Chg);
      Server.Update_Traj_Tmpy_Fpln (L_Tmpy_Fpln, L_Tmpy_Fpln_Chg);
      Server.Update_Traj_Sec_Fpln (L_Sec_Fpln, L_Sec_Fpln_Chg);

      Put_Trans_Alt_Sec_To_Be_Modified (False);

      -- Warns IFR SERVER that the active and secondary flight plans have
      --changed
      Server.Send_Event
        (Event_Rqst_Descriptor => (Fm_Event => Server_Types.Active_Fpln_Update))
;
      Server.Send_Event
        (Event_Rqst_Descriptor => (Fm_Event => Server_Types.Sec_Fpln_Update));
   end Init;

   -----------------------------------------------
   -----------------------------------------------
   procedure Put_Fplns
     (Actv_Fpln     : in Server_Types.T_Primary_Fpln;
      Actv_Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin;
      Tmpy_Fpln     : in Server_Types.T_Primary_Fpln;
      Tmpy_Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin)
   is

      use Server_Types;
      use Fms_Types;
      use Os.Abstract_Semaphore;

      L_New_Actv_Fpln : Server_Types.T_Primary_Fpln;
      L_Old_Fpln      : Server_Types.T_Primary_Fpln;
      L_New_Tmpy_Fpln : Server_Types.T_Primary_Fpln;

      L_Actv_Wind_Profile : Wind.Wind_Profile.T_Wind_Profile;
      L_Tmpy_Wind_Profile : Wind.Wind_Profile.T_Wind_Profile;
   begin

      L_New_Actv_Fpln := Actv_Fpln;
      L_New_Tmpy_Fpln := Tmpy_Fpln;

      -- Updates the temporary waypoints and last used marker
      V_Get_Token (This => G_Sec_Sem);
      Update (L_New_Actv_Fpln, L_New_Tmpy_Fpln, G_Sec_Fpln);
      V_Free_Token (This => G_Sec_Sem);

      -- Updates the steps references
      Fpln.Update_Steps (L_New_Actv_Fpln);
      Fpln.Update_Steps (L_New_Tmpy_Fpln);


      -- Clears the arrival procedure if
      -- there are no more references in the legs list
      Fpln.Update_Arrival (L_New_Actv_Fpln, Server.Get_Config_Data'Access);
      Fpln.Update_Arrival (L_New_Tmpy_Fpln, Server.Get_Config_Data'Access);

      -- Clears the hover procedure if
      -- there are no more references in the legs list
      Fpln.Update_Hover_Exist (L_New_Actv_Fpln);
      Fpln.Update_Hover_Exist (L_New_Tmpy_Fpln);

      -- Clears the RTA informations if
      -- there are no more RTA in the designed leg
      Fpln.Update_Rta (L_New_Actv_Fpln);
      Fpln.Update_Rta (L_New_Tmpy_Fpln);

      V_Get_Token (This => G_Actv_Sem);
      -- Stores the new flight plans
      L_Old_Fpln  := G_Actv_Fpln;
      G_Actv_Fpln := L_New_Actv_Fpln;

      G_Actv_Fpln_Chg := Actv_Fpln_Chg;

      V_Free_Token (This => G_Actv_Sem);

      -- update the active wind profile
      Wind.Wind_Profile.Initialize (L_Actv_Wind_Profile);
      Fplns_Ctrl.Get_Actv_Wind_Profile (L_Actv_Wind_Profile);
      -- refresh using old fpln to keep the leg before the sequenced leg
      Wind.Wind_Profile.Refresh
        (L_Actv_Wind_Profile,
         L_New_Actv_Fpln.Legs,
         L_Old_Fpln.Legs,
         Actv_Fpln_Chg);
      Fplns_Ctrl.Put_Actv_Wind_Profile (L_Actv_Wind_Profile);

      Server.Update_Traj_Actv_Fpln (L_New_Actv_Fpln, Actv_Fpln_Chg);

      -- Sends the events related to the active flight plan revision
      Send_Fplns_Events
        (New_Actv_Fpln => L_New_Actv_Fpln,
         Old_Actv_Fpln => L_Old_Fpln);

      -- Update the secondary flight plan with active flight plan
      Update_Sec_With_Actv
        (Old_Actv_Fpln => L_Old_Fpln,
         Actv_Fpln     => L_New_Actv_Fpln,
         Actv_Fpln_Chg => Actv_Fpln_Chg);

      V_Get_Token (This => G_Tmpy_Sem);

      L_Old_Fpln      := G_Tmpy_Fpln;
      G_Tmpy_Fpln     := L_New_Tmpy_Fpln;
      G_Tmpy_Fpln_Chg := Tmpy_Fpln_Chg;

      V_Free_Token (This => G_Tmpy_Sem);

      -- update the temporary wind profile
      Wind.Wind_Profile.Initialize (L_Tmpy_Wind_Profile);
      Fplns_Ctrl.Get_Tmpy_Wind_Profile (L_Tmpy_Wind_Profile);
      -- refresh using old fpln to keep the leg before the sequenced leg
      Wind.Wind_Profile.Refresh
        (L_Tmpy_Wind_Profile,
         L_New_Tmpy_Fpln.Legs,
         L_Old_Fpln.Legs,
         Tmpy_Fpln_Chg);
      Fplns_Ctrl.Put_Tmpy_Wind_Profile (L_Tmpy_Wind_Profile);

      Server.Update_Traj_Tmpy_Fpln (L_New_Tmpy_Fpln, Tmpy_Fpln_Chg);
      Send_Tmpy_Fpln_Events
        (New_Fpln => L_New_Tmpy_Fpln,
         Old_Fpln => L_Old_Fpln,
         Fpln_Chg => Tmpy_Fpln_Chg);

   end Put_Fplns;

   -----------------------------------------------
   -----------------------------------------------
   procedure Put_Tmpy_Fpln
     (P_Fpln   : in out Server_Types.T_Primary_Fpln;
      Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin)
   is

      use Fms_Types;
      use Server_Types;
      use Os.Abstract_Semaphore;

      L_Old_Tmpy_Fpln     : Server_Types.T_Primary_Fpln;
      L_Tmpy_Wind_Profile : Wind.Wind_Profile.T_Wind_Profile;

   begin

      -- Init OLD_TMPY_FPLN
      V_Get_Token (This => G_Tmpy_Sem);
      L_Old_Tmpy_Fpln := G_Tmpy_Fpln;
      V_Free_Token (This => G_Tmpy_Sem);

      if L_Old_Tmpy_Fpln.Legs.Legs_Nbr = 0 then
         V_Get_Token (This => G_Actv_Sem);
         L_Old_Tmpy_Fpln := G_Actv_Fpln;
         V_Free_Token (This => G_Actv_Sem);
      end if;

      if ((P_Fpln.Params_Mission.Status = Defined) and
          (L_Old_Tmpy_Fpln.Params_Mission.Status = Defined))
      then

         -- Updates Mission (if necessary)
         Fpln.Mission_Controller.Set_Up
           (Old_Fpln => L_Old_Tmpy_Fpln,
            New_Fpln => P_Fpln,
            Fpln_Chg => Fpln_Chg);

      end if;

      -- Updates the temporary waypoints
      V_Get_Token (This => G_Sec_Sem);
      V_Get_Token (This => G_Actv_Sem);
      Update (G_Actv_Fpln, P_Fpln, G_Sec_Fpln);
      V_Free_Token (This => G_Sec_Sem);
      V_Free_Token (This => G_Actv_Sem);

      -- Updates the steps references
      Fpln.Update_Steps (P_Fpln);


      -- Clears the arrival procedure if
      -- there are no more references in the legs list
      Fpln.Update_Arrival (P_Fpln, Server.Get_Config_Data'Access);

      -- Clears the hover procedure if
      -- there are no more references in the legs list
      Fpln.Update_Hover_Exist (P_Fpln);

      -- Clears the RTA informations if
      -- there are no more RTA in the designed leg
      Fpln.Update_Rta (P_Fpln);

      V_Get_Token (This => G_Actv_Sem);
      V_Get_Token (This => G_Sec_Sem);

      -- Updates the temporary waypoints and last used marker
      Update (G_Actv_Fpln, P_Fpln, G_Sec_Fpln);

      V_Free_Token (This => G_Sec_Sem);
      V_Free_Token (This => G_Actv_Sem);

      Wind.Wind_Profile.Initialize (L_Tmpy_Wind_Profile);
      -- Update the temporary wind profile only if no tmpy fpln was existing
      -- else tmpy wind profile keeps the same value
      if not Is_Tmpy_Fpln_Defined then

         -- in case of init tmpy from recov, the wind has already been copied
         -- see ( undo_fpln ), in others cases, tmpy wind profile shall
         -- take the value of active wind profile
         if Fpln_Chg.Lateral_Modif /= Server_Types.Init_Tmpy_With_Recov and
            Fpln_Chg.Vertical_Modif /= Server_Types.Wind_Set_Modification
         then

            Fplns_Ctrl.Get_Actv_Wind_Profile (L_Tmpy_Wind_Profile);
            Fplns_Ctrl.Put_Tmpy_Wind_Profile (L_Tmpy_Wind_Profile);
         end if;
      end if;

      -- tmpy wind profile is defined
      Fplns_Ctrl.Get_Tmpy_Wind_Profile (L_Tmpy_Wind_Profile);

      -- refresh using old fpln to keep the leg before the sequenced leg
      Wind.Wind_Profile.Refresh
        (L_Tmpy_Wind_Profile,
         P_Fpln.Legs,
         L_Old_Tmpy_Fpln.Legs,
         Fpln_Chg);
      Fplns_Ctrl.Put_Tmpy_Wind_Profile (L_Tmpy_Wind_Profile);

      Wind.Wind_Profile.Update_Fpln
        (This      => L_Tmpy_Wind_Profile,
         Legs_Fpln => P_Fpln.Legs);


      V_Get_Token (This => G_Tmpy_Sem);
      -- Stores the new flight plan
      L_Old_Tmpy_Fpln := G_Tmpy_Fpln;
      G_Tmpy_Fpln     := P_Fpln;
      G_Tmpy_Fpln_Chg := Fpln_Chg;

      V_Free_Token (This => G_Tmpy_Sem);

      Server.Update_Traj_Tmpy_Fpln (P_Fpln, Fpln_Chg);

      -- Sends the events related to the temporary flight plan revision
      Send_Tmpy_Fpln_Events
        (New_Fpln => P_Fpln,
         Old_Fpln => L_Old_Tmpy_Fpln,
         Fpln_Chg => Fpln_Chg);
   end Put_Tmpy_Fpln;

   -----------------------------------------------
   -----------------------------------------------
   procedure Put_Actv_Fpln
     (P_Fpln   : in out Server_Types.T_Primary_Fpln;
      Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin)
   is

      use Server_Types;
      use Fms_Types;
      use Os.Abstract_Semaphore;

      L_Old_Actv_Fpln     : Server_Types.T_Primary_Fpln;
      L_Actv_Wind_Profile : Wind.Wind_Profile.T_Wind_Profile;
   begin

      -- Init OLD_FPLN
      V_Get_Token (This => G_Actv_Sem);
      L_Old_Actv_Fpln := G_Actv_Fpln;
      V_Free_Token (This => G_Actv_Sem);

      if ((P_Fpln.Params_Mission.Status = Defined) and
          (L_Old_Actv_Fpln.Params_Mission.Status = Defined))
      then

         -- Updates Mission (if necessary)
         Fpln.Mission_Controller.Set_Up
           (Old_Fpln => L_Old_Actv_Fpln,
            New_Fpln => P_Fpln,
            Fpln_Chg => Fpln_Chg);

      end if;

      -- Updates the steps references
      Fpln.Update_Steps (P_Fpln);

      -- Clears the arrival procedure if
      -- there are no more references in the legs list
      Fpln.Update_Arrival (P_Fpln, Server.Get_Config_Data'Access);

      -- Clears the hover procedure if
      -- there are no more references in the legs list
      Fpln.Update_Hover_Exist (P_Fpln);

      -- Clears the RTA informations if
      -- there are no more RTA in the designed leg
      Fpln.Update_Rta (P_Fpln);

      V_Get_Token (This => G_Tmpy_Sem);
      V_Get_Token (This => G_Sec_Sem);

      -- Updates the temporary waypoints and last used marker
      Update (P_Fpln, G_Tmpy_Fpln, G_Sec_Fpln);

      V_Free_Token (This => G_Sec_Sem);
      V_Free_Token (This => G_Tmpy_Sem);

      -- update the wind profile
      Wind.Wind_Profile.Initialize (L_Actv_Wind_Profile);
      Fplns_Ctrl.Get_Actv_Wind_Profile (L_Actv_Wind_Profile);
      -- refresh using old fpln to keep the leg before the sequenced leg
      Wind.Wind_Profile.Refresh
        (L_Actv_Wind_Profile,
         P_Fpln.Legs,
         L_Old_Actv_Fpln.Legs,
         Fpln_Chg);
      Fplns_Ctrl.Put_Actv_Wind_Profile (L_Actv_Wind_Profile);

      Wind.Wind_Profile.Update_Fpln
        (This      => L_Actv_Wind_Profile,
         Legs_Fpln => P_Fpln.Legs);

      V_Get_Token (This => G_Actv_Sem);
      -- Stores the new flight plan
      L_Old_Actv_Fpln := G_Actv_Fpln;
      G_Actv_Fpln     := P_Fpln;

      G_Actv_Fpln_Chg := Fpln_Chg;

      V_Free_Token (This => G_Actv_Sem);

      Server.Update_Traj_Actv_Fpln (P_Fpln, Fpln_Chg);

      -- Sends the events related to the active flight plan revision
      Send_Actv_Fpln_Events
        (New_Fpln => P_Fpln,
         Old_Fpln => L_Old_Actv_Fpln,
         Fpln_Chg => Fpln_Chg);

      if Fpln_Chg.Lateral_Modif /= Server_Types.Sequencement and
         Fpln_Chg.Lateral_Modif /= Server_Types.Sec_Activation and
         Fpln_Chg.Lateral_Modif /= Swap_Actv_Sec
      then
         if G_Trans_Alt_Sec_To_Be_Modified then

            Update_Sec_Arrival_Transalt (Arrival_Actv => P_Fpln.Arrival);
            Put_Trans_Alt_Sec_To_Be_Modified (False);

         else

            -- Update the secondary flight plan with active flight plan
            -- if it is not a sequencement
            Update_Sec_With_Actv
              (Old_Actv_Fpln => L_Old_Actv_Fpln,
               Actv_Fpln     => P_Fpln,
               Actv_Fpln_Chg => Fpln_Chg);

         end if;

      end if;

   end Put_Actv_Fpln;

   -----------------------------------------------
   -----------------------------------------------
   procedure Put_Sec_Fpln
     (P_Fpln     : in out Server_Types.T_Primary_Fpln;
      Fpln_Chg   : in Server_Types.T_Fpln_Modif_Origin;
      Fpln_Link  : in Server_Types.T_Fpln_Link;
      B_Save_Sec : in Boolean := True)
   is

      use Fms_Types;
      use Server_Types;
      use Os.Abstract_Semaphore;

      L_Old_Sec_Fpln     : Server_Types.T_Primary_Fpln;
      L_Sec_Wind_Profile : Wind.Wind_Profile.T_Wind_Profile;

   begin

      -- Init OLD_SEC_FPLN
      V_Get_Token (This => G_Sec_Sem);
      L_Old_Sec_Fpln := G_Sec_Fpln;
      V_Free_Token (This => G_Sec_Sem);

      if ((P_Fpln.Params_Mission.Status = Defined) and
          (L_Old_Sec_Fpln.Params_Mission.Status = Defined))
      then

         -- Updates Mission (if necessary)
         Fpln.Mission_Controller.Set_Up
           (Old_Fpln => L_Old_Sec_Fpln,
            New_Fpln => P_Fpln,
            Fpln_Chg => Fpln_Chg);

      end if;

      -- Updates the steps references
      Fpln.Update_Steps (P_Fpln);


      -- Clears the arrival procedure if
      -- there are no more references in the legs list
      Fpln.Update_Arrival (P_Fpln, Server.Get_Config_Data'Access);

      -- Clears the hover procedure if
      -- there are no more references in the legs list
      Fpln.Update_Hover_Exist (P_Fpln);

      -- Clears the RTA informations if
      -- there are no more RTA in the designed leg
      Fpln.Update_Rta (P_Fpln);

      V_Get_Token (This => G_Tmpy_Sem);
      V_Get_Token (This => G_Actv_Sem);

      -- Updates the temporary waypoints and last used marker
      Update (G_Actv_Fpln, G_Tmpy_Fpln, P_Fpln);

      V_Free_Token (This => G_Actv_Sem);
      V_Free_Token (This => G_Tmpy_Sem);

      -- update the wind profile
      Wind.Wind_Profile.Initialize (L_Sec_Wind_Profile);
      Fplns_Ctrl.Get_Sec_Wind_Profile (L_Sec_Wind_Profile);
      -- refresh using old fpln to keep the leg before the sequenced leg
      Wind.Wind_Profile.Refresh
        (L_Sec_Wind_Profile,
         P_Fpln.Legs,
         L_Old_Sec_Fpln.Legs,
         Fpln_Chg);
      Fplns_Ctrl.Put_Sec_Wind_Profile (L_Sec_Wind_Profile);

      Wind.Wind_Profile.Update_Fpln
        (This      => L_Sec_Wind_Profile,
         Legs_Fpln => P_Fpln.Legs);

      V_Get_Token (This => G_Sec_Sem);
      -- Stores the new flight plan
      G_Sec_Fpln := P_Fpln;

      G_Sec_Fpln_Chg := Fpln_Chg;
      G_Sec_Link     := Fpln_Link;

      V_Free_Token (This => G_Sec_Sem);

      if B_Save_Sec then
         -- Indicates that secondary flight plan can be saved
         Server.Put_Flag_Save_Fpln_Sec;
      end if;

      Server.Update_Traj_Sec_Fpln (P_Fpln, Fpln_Chg);

      -- Sends the events related to the secondary flight plan revision
      Send_Sec_Fpln_Events (Fpln_Chg => Fpln_Chg);
   end Put_Sec_Fpln;

   ---------------------------
   -- Put_Actv_Wind_Profile --
   ---------------------------
   procedure Put_Actv_Wind_Profile
     (Wind_Profile : in Wind.Wind_Profile.T_Wind_Profile)
   is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Actv_Sem);
      Wind.Wind_Profile.Copy (G_Actv_Wind_Profile, Wind_Profile);
      V_Free_Token (This => G_Actv_Sem);
   end Put_Actv_Wind_Profile;

   ---------------------------
   -- Put_Tmpy_Wind_Profile --
   ---------------------------
   procedure Put_Tmpy_Wind_Profile
     (Wind_Profile : in Wind.Wind_Profile.T_Wind_Profile)
   is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Tmpy_Sem);
      Wind.Wind_Profile.Copy (G_Tmpy_Wind_Profile, Wind_Profile);
      V_Free_Token (This => G_Tmpy_Sem);
   end Put_Tmpy_Wind_Profile;

   --------------------------
   -- Put_Sec_Wind_Profile --
   --------------------------
   procedure Put_Sec_Wind_Profile
     (Wind_Profile : in Wind.Wind_Profile.T_Wind_Profile)
   is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Sec_Sem);
      Wind.Wind_Profile.Copy (G_Sec_Wind_Profile, Wind_Profile);
      V_Free_Token (This => G_Sec_Sem);
   end Put_Sec_Wind_Profile;

   ----------------------------
   -- Put_Recov_Wind_Profile --
   ----------------------------
   procedure Put_Recov_Wind_Profile
     (Wind_Profile : in Wind.Wind_Profile.T_Wind_Profile)
   is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Actv_Sem);
      Wind.Wind_Profile.Copy (G_Recov_Wind_Profile, Wind_Profile);
      V_Free_Token (This => G_Actv_Sem);
   end Put_Recov_Wind_Profile;

   -----------------------------------------------
   -----------------------------------------------
   procedure Get_Actv_Fpln (Fpln : out Server_Types.T_Primary_Fpln) is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Actv_Sem);
      Fpln := G_Actv_Fpln;
      V_Free_Token (This => G_Actv_Sem);
   end Get_Actv_Fpln;

   -----------------------------------------------
   -----------------------------------------------
   procedure Get_Tmpy_Fpln (Fpln : out Server_Types.T_Primary_Fpln) is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Tmpy_Sem);
      Fpln := G_Tmpy_Fpln;
      V_Free_Token (This => G_Tmpy_Sem);
   end Get_Tmpy_Fpln;

   -----------------------------------------------
   -----------------------------------------------
   procedure Get_Sec_Fpln (Fpln : out Server_Types.T_Primary_Fpln) is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Sec_Sem);
      Fpln := G_Sec_Fpln;
      V_Free_Token (This => G_Sec_Sem);
   end Get_Sec_Fpln;

   -----------------------------------------------
   -----------------------------------------------
   procedure Get_Actv_Fpln
     (Fpln     : out Server_Types.T_Primary_Fpln;
      Fpln_Chg : out Server_Types.T_Fpln_Modif_Origin)
   is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Actv_Sem);
      Fpln     := G_Actv_Fpln;
      Fpln_Chg := G_Actv_Fpln_Chg;
      V_Free_Token (This => G_Actv_Sem);
   end Get_Actv_Fpln;

   -----------------------------------------------
   -----------------------------------------------
   procedure Get_Tmpy_Fpln
     (Fpln     : out Server_Types.T_Primary_Fpln;
      Fpln_Chg : out Server_Types.T_Fpln_Modif_Origin)
   is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Tmpy_Sem);
      Fpln     := G_Tmpy_Fpln;
      Fpln_Chg := G_Tmpy_Fpln_Chg;
      V_Free_Token (This => G_Tmpy_Sem);
   end Get_Tmpy_Fpln;

   -----------------------------------------------
   -----------------------------------------------
   procedure Get_Sec_Fpln
     (Fpln      : out Server_Types.T_Primary_Fpln;
      Fpln_Chg  : out Server_Types.T_Fpln_Modif_Origin;
      Fpln_Link : out Server_Types.T_Fpln_Link)
   is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Sec_Sem);
      Fpln      := G_Sec_Fpln;
      Fpln_Chg  := G_Sec_Fpln_Chg;
      Fpln_Link := G_Sec_Link;
      V_Free_Token (This => G_Sec_Sem);
   end Get_Sec_Fpln;

   -----------------------------------------------
   -----------------------------------------------
   procedure Put_Recov_Fpln (Fpln : in Server_Types.T_Primary_Fpln) is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Actv_Sem);
      G_Recov_Fpln := Fpln;
      V_Free_Token (This => G_Actv_Sem);
   end Put_Recov_Fpln;

   -----------------------------------------------
   -----------------------------------------------
   procedure Get_Recov_Fpln (Fpln : out Server_Types.T_Primary_Fpln) is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Actv_Sem);

      Wind.Wind_Profile.Update_Fpln
        (This      => G_Recov_Wind_Profile,
         Legs_Fpln => G_Recov_Fpln.Legs);
      Fpln := G_Recov_Fpln;

      V_Free_Token (This => G_Actv_Sem);
   end Get_Recov_Fpln;

   ---------------------------
   -- Get_Actv_Wind_Profile --
   ---------------------------
   procedure Get_Actv_Wind_Profile
     (Wind_Profile : out Wind.Wind_Profile.T_Wind_Profile)
   is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Actv_Sem);
      Wind.Wind_Profile.Copy (Wind_Profile, G_Actv_Wind_Profile);
      V_Free_Token (This => G_Actv_Sem);
   end Get_Actv_Wind_Profile;

   ---------------------------
   -- Get_Tmpy_Wind_Profile --
   ---------------------------
   procedure Get_Tmpy_Wind_Profile
     (Wind_Profile : out Wind.Wind_Profile.T_Wind_Profile)
   is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Tmpy_Sem);
      Wind.Wind_Profile.Copy (Wind_Profile, G_Tmpy_Wind_Profile);
      V_Free_Token (This => G_Tmpy_Sem);
   end Get_Tmpy_Wind_Profile;

   ---------------------------
   -- Get_Sec_Wind_Profile --
   ---------------------------
   procedure Get_Sec_Wind_Profile
     (Wind_Profile : out Wind.Wind_Profile.T_Wind_Profile)
   is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Sec_Sem);
      Wind.Wind_Profile.Copy (Wind_Profile, G_Sec_Wind_Profile);
      V_Free_Token (This => G_Sec_Sem);
   end Get_Sec_Wind_Profile;

   ----------------------------
   -- Get_Recov_Wind_Profile --
   ----------------------------
   procedure Get_Recov_Wind_Profile
     (Wind_Profile : out Wind.Wind_Profile.T_Wind_Profile)
   is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Actv_Sem);
      Wind.Wind_Profile.Copy (Wind_Profile, G_Recov_Wind_Profile);
      V_Free_Token (This => G_Actv_Sem);
   end Get_Recov_Wind_Profile;

   ------------------------------
   -- Cancel_Actv_Wind_Profile --
   ------------------------------
   procedure Cancel_Actv_Wind_Profile is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Actv_Sem);
      Wind.Wind_Profile.Clear (G_Actv_Wind_Profile);
      V_Free_Token (This => G_Actv_Sem);
   end Cancel_Actv_Wind_Profile;

   ------------------------------
   -- Cancel_Tmpy_Wind_Profile --
   ------------------------------
   procedure Cancel_Tmpy_Wind_Profile is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Tmpy_Sem);
      Wind.Wind_Profile.Clear (G_Tmpy_Wind_Profile);
      V_Free_Token (This => G_Tmpy_Sem);
   end Cancel_Tmpy_Wind_Profile;

   -----------------------------
   -- Cancel_Sec_Wind_Profile --
   -----------------------------
   procedure Cancel_Sec_Wind_Profile is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Sec_Sem);
      Wind.Wind_Profile.Clear (G_Sec_Wind_Profile);
      V_Free_Token (This => G_Sec_Sem);
   end Cancel_Sec_Wind_Profile;

   -------------------------------
   -- Cancel_Recov_Wind_Profile --
   -------------------------------
   procedure Cancel_Recov_Wind_Profile is
      use Os.Abstract_Semaphore;
   begin
      V_Get_Token (This => G_Actv_Sem);
      Wind.Wind_Profile.Clear (G_Recov_Wind_Profile);
      V_Free_Token (This => G_Actv_Sem);
   end Cancel_Recov_Wind_Profile;

   -----------------------------------------------
   -----------------------------------------------
   function Is_Recov_Fpln_Defined return Boolean is
      use Os.Abstract_Semaphore;
      B_Def : Boolean;
   begin
      V_Get_Token (This => G_Actv_Sem);
      B_Def := G_Recov_Fpln.B_Def;
      V_Free_Token (This => G_Actv_Sem);
      return B_Def;
   end Is_Recov_Fpln_Defined;

   -----------------------------------------------
   -----------------------------------------------
   function Is_Tmpy_Fpln_Defined return Boolean is
      use Os.Abstract_Semaphore;

      B_Def : Boolean;

   begin

      V_Get_Token (This => G_Tmpy_Sem);

      B_Def := G_Tmpy_Fpln.B_Def;

      V_Free_Token (This => G_Tmpy_Sem);

      return B_Def;
   end Is_Tmpy_Fpln_Defined;

   -----------------------------------------------
   -----------------------------------------------
   function Is_Sec_Fpln_Linked return Boolean is
      use Server_Types;

      use Os.Abstract_Semaphore;

      B_Linked : Boolean;

   begin

      V_Get_Token (This => G_Sec_Sem);

      B_Linked := G_Sec_Link = Server_Types.Linked;

      V_Free_Token (This => G_Sec_Sem);

      return B_Linked;
   end Is_Sec_Fpln_Linked;

   ---------------------
   -- Unlink_Sec_Fpln --
   ---------------------

   procedure Unlink_Sec_Fpln is

      use Os.Abstract_Semaphore;

   begin

      V_Get_Token (This => G_Sec_Sem);

      G_Sec_Link := Server_Types.Not_Linked;

      V_Free_Token (This => G_Sec_Sem);

   end Unlink_Sec_Fpln;

   -----------------------------------------------
   -----------------------------------------------
   function Get_Tmpy_Dir_To return  Server_Types.T_Dir_To is
      use Os.Abstract_Semaphore;

      L_Dir_To : Server_Types.T_Dir_To;

   begin

      V_Get_Token (This => G_Tmpy_Sem);

      L_Dir_To := G_Tmpy_Fpln.Dir_To;

      V_Free_Token (This => G_Tmpy_Sem);

      return L_Dir_To;
   end Get_Tmpy_Dir_To;

   -----------------------------------------------
   -----------------------------------------------
   function Get_Tmpy_Arrival return  Server_Types.T_Arrival is
      use Os.Abstract_Semaphore;

      L_Arrival : Server_Types.T_Arrival;

   begin

      V_Get_Token (This => G_Tmpy_Sem);

      L_Arrival := G_Tmpy_Fpln.Arrival;

      V_Free_Token (This => G_Tmpy_Sem);

      return L_Arrival;
   end Get_Tmpy_Arrival;

   -----------------------------------------------
   -----------------------------------------------
   function Get_Tmpy_Departure return  Server_Types.T_Departure is
      use Os.Abstract_Semaphore;

      L_Departure : Server_Types.T_Departure;

   begin

      V_Get_Token (This => G_Tmpy_Sem);

      L_Departure := G_Tmpy_Fpln.Departure;

      V_Free_Token (This => G_Tmpy_Sem);

      return L_Departure;
   end Get_Tmpy_Departure;

   -----------------------------------------------
   -----------------------------------------------
   function Get_Actv_Arrival return  Server_Types.T_Arrival is
      use Os.Abstract_Semaphore;

      L_Arrival : Server_Types.T_Arrival;

   begin

      V_Get_Token (This => G_Actv_Sem);

      L_Arrival := G_Actv_Fpln.Arrival;

      V_Free_Token (This => G_Actv_Sem);

      return L_Arrival;
   end Get_Actv_Arrival;

   -----------------------------------------------
   -----------------------------------------------
   function Get_Actv_Departure return  Server_Types.T_Departure is
      use Os.Abstract_Semaphore;

      L_Departure : Server_Types.T_Departure;

   begin

      V_Get_Token (This => G_Actv_Sem);

      L_Departure := G_Actv_Fpln.Departure;

      V_Free_Token (This => G_Actv_Sem);

      return L_Departure;
   end Get_Actv_Departure;

   -----------------------------------------------
   -----------------------------------------------
   function Get_Sec_Arrival return  Server_Types.T_Arrival is
      use Os.Abstract_Semaphore;

      L_Arrival : Server_Types.T_Arrival;

   begin

      V_Get_Token (This => G_Sec_Sem);

      L_Arrival := G_Sec_Fpln.Arrival;

      V_Free_Token (This => G_Sec_Sem);

      return L_Arrival;
   end Get_Sec_Arrival;

   -----------------------------------------------
   -----------------------------------------------
   function Get_Sec_Departure return  Server_Types.T_Departure is
      use Os.Abstract_Semaphore;

      L_Departure : Server_Types.T_Departure;

   begin

      V_Get_Token (This => G_Sec_Sem);

      L_Departure := G_Sec_Fpln.Departure;

      V_Free_Token (This => G_Sec_Sem);

      return L_Departure;
   end Get_Sec_Departure;

   -----------------------------------------------
   -----------------------------------------------
   function Get_Actv_Steps return  Server_Types.T_Steps is
      use Os.Abstract_Semaphore;

      L_Steps : Server_Types.T_Steps;

   begin

      V_Get_Token (This => G_Actv_Sem);

      L_Steps := G_Actv_Fpln.Steps;

      V_Free_Token (This => G_Actv_Sem);

      return L_Steps;
   end Get_Actv_Steps;

   -----------------------------------------------
   -----------------------------------------------
   function Get_Tmpy_Steps return  Server_Types.T_Steps is
      use Os.Abstract_Semaphore;

      L_Steps : Server_Types.T_Steps;

   begin

      V_Get_Token (This => G_Tmpy_Sem);

      L_Steps := G_Tmpy_Fpln.Steps;

      V_Free_Token (This => G_Tmpy_Sem);

      return L_Steps;
   end Get_Tmpy_Steps;

   -----------------------------------------------
   -----------------------------------------------
   function Get_Sec_Steps return  Server_Types.T_Steps is
      use Os.Abstract_Semaphore;

      L_Steps : Server_Types.T_Steps;

   begin

      V_Get_Token (This => G_Sec_Sem);

      L_Steps := G_Sec_Fpln.Steps;

      V_Free_Token (This => G_Sec_Sem);

      return L_Steps;
   end Get_Sec_Steps;

   -----------------------------------------------
   -----------------------------------------------
   function Is_Same_Sec_Dep_Actv_Dest_Arpt return Boolean is
      use Fms_Types;
      use Navdb_Types;
      L_Actv_Arrival  : Server_Types.T_Arrival;
      L_Sec_Departure : Server_Types.T_Departure;
      L_B_Same_Arpt   : Boolean;

      L_Sec_Departure_Ident   : Navdb_Types.T_Airport_Ident;
      L_Actv_Arrival_Ident    : Navdb_Types.T_Airport_Ident;
      L_Actv_Arrival_Lat      : Fms_Types.T_Latitude;
      L_Actv_Arrival_Long     : Fms_Types.T_Longitude;
      L_Actv_Arrival_Mag_Var  : Fms_Types.T_Ang_Rel_Deg_C;
      L_Sec_Departure_Lat     : Fms_Types.T_Latitude;
      L_Sec_Departure_Long    : Fms_Types.T_Longitude;
      L_Sec_Departure_Mag_Var : Fms_Types.T_Ang_Rel_Deg_C;

   begin

      L_Actv_Arrival  := Get_Actv_Arrival;
      L_Sec_Departure := Get_Sec_Departure;
      L_B_Same_Arpt   := False;

      if L_Actv_Arrival.B_Def and then L_Sec_Departure.B_Def then
         if L_Actv_Arrival.Airport.Database_Type = Navdb_Types.Std then
            L_Actv_Arrival_Ident   := L_Actv_Arrival.Airport.Std_Airport.Ident;
            L_Actv_Arrival_Lat     := L_Actv_Arrival.Airport.Std_Airport.Lat;
            L_Actv_Arrival_Long    := L_Actv_Arrival.Airport.Std_Airport.Long;
            L_Actv_Arrival_Mag_Var :=
                 L_Actv_Arrival.Airport.Std_Airport.Mag_Var;
         else
            L_Actv_Arrival_Ident   :=
                 L_Actv_Arrival.Airport.Pilot_Airport.Ident;
            L_Actv_Arrival_Lat     := L_Actv_Arrival.Airport.Pilot_Airport.Lat;
            L_Actv_Arrival_Long    :=
                 L_Actv_Arrival.Airport.Pilot_Airport.Long;
            L_Actv_Arrival_Mag_Var :=
                 L_Actv_Arrival.Airport.Pilot_Airport.Mag_Var;
         end if;

         if L_Sec_Departure.Airport.Database_Type = Navdb_Types.Std then
            L_Sec_Departure_Ident   :=
                 L_Sec_Departure.Airport.Std_Airport.Ident;
            L_Sec_Departure_Lat     := L_Sec_Departure.Airport.Std_Airport.Lat;
            L_Sec_Departure_Long    :=
                 L_Sec_Departure.Airport.Std_Airport.Long;
            L_Sec_Departure_Mag_Var :=
                 L_Sec_Departure.Airport.Std_Airport.Mag_Var;
         else
            L_Sec_Departure_Ident   :=
                 L_Sec_Departure.Airport.Pilot_Airport.Ident;
            L_Sec_Departure_Lat     :=
                 L_Sec_Departure.Airport.Pilot_Airport.Lat;
            L_Sec_Departure_Long    :=
                 L_Sec_Departure.Airport.Pilot_Airport.Long;
            L_Sec_Departure_Mag_Var :=
                 L_Sec_Departure.Airport.Pilot_Airport.Mag_Var;
         end if;

         if L_Actv_Arrival_Ident = L_Sec_Departure_Ident then
            Basic_Algo.Compare_Position
              ((Lat     => L_Actv_Arrival_Lat,
                Lon     => L_Actv_Arrival_Long,
                Mag_Dec => L_Actv_Arrival_Mag_Var),
               (Lat     => L_Sec_Departure_Lat,
                Lon     => L_Sec_Departure_Long,
                Mag_Dec => L_Sec_Departure_Mag_Var),
               0.00001,
               L_B_Same_Arpt);
         end if;

      end if;

      return L_B_Same_Arpt;
   end Is_Same_Sec_Dep_Actv_Dest_Arpt;

   -----------------------------------------------
   -----------------------------------------------
   function Is_Active_Rta_Defined return Boolean is
      use Fms_Types;
   begin
      return G_Actv_Fpln.Rta.Status = Fms_Types.Defined;
   end Is_Active_Rta_Defined;

   -----------------------------------------------
   -----------------------------------------------
   procedure Update
     (Actv_Fpln : in Server_Types.T_Primary_Fpln;
      Tmpy_Fpln : in Server_Types.T_Primary_Fpln;
      Sec_Fpln  : in Server_Types.T_Primary_Fpln)
   is

   begin

      Fix.Reset;

      for I in 0 .. Legs.Get_Tail_Index (Actv_Fpln.Legs) loop

         Fix.Update (Legs.Get_Fix (Legs => Actv_Fpln.Legs, Index => I));

      end loop;

      for I in 0 .. Legs.Get_Tail_Index (Tmpy_Fpln.Legs) loop

         Fix.Update (Legs.Get_Fix (Legs => Tmpy_Fpln.Legs, Index => I));

      end loop;

      for I in 0 .. Legs.Get_Tail_Index (Sec_Fpln.Legs) loop

         Fix.Update (Legs.Get_Fix (Legs => Sec_Fpln.Legs, Index => I));

      end loop;

      Leg.Update_Marker (Actv_Fpln.Legs, Tmpy_Fpln.Legs, Sec_Fpln.Legs);
   end Update;

   -----------------------------------------------
   -----------------------------------------------
   procedure Send_Actv_Fpln_Events
     (New_Fpln : in Server_Types.T_Primary_Fpln;
      Old_Fpln : in Server_Types.T_Primary_Fpln;
      Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin)
   is

      use Server_Types;

   begin

      -- Sends flight plan update event
      if Fpln_Chg.Refresh_Modif /= Server_Types.Undef then

         -- Warns IFR SERVER that the active flight plan has been updated
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event     =>
              Server_Types.Active_Fpln_Refresh,
                                      Is_Completed => True));

      elsif Fpln_Chg.Lateral_Modif = Server_Types.Sequencement then

         -- Warns IFR SERVER that the active flight have been sequenced
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event => Server_Types.Active_Fpln_Sqc))
;

      else

         -- Warns IFR SERVER that the active flight plan has changed
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event =>
              Server_Types.Active_Fpln_Update));

      end if;

      Send_Offset_Events (New_Fpln => New_Fpln, Old_Fpln => Old_Fpln);

      Send_Actv_Airports_Events (New_Fpln => New_Fpln, Old_Fpln => Old_Fpln);
   end Send_Actv_Fpln_Events;

   -----------------------------------------------
   -----------------------------------------------
   procedure Send_Tmpy_Fpln_Events
     (New_Fpln : in Server_Types.T_Primary_Fpln;
      Old_Fpln : in Server_Types.T_Primary_Fpln;
      Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin)
   is

      use Server_Types;

   begin

      -- Sends flight plan update events
      if Old_Fpln.B_Def and then not New_Fpln.B_Def then

         -- Warns IFR SERVER that the temporary flight plan has been cancelled
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event =>
              Server_Types.Tmpy_Fpln_Cancelled));

      elsif not Old_Fpln.B_Def and then New_Fpln.B_Def then

         -- Warns IFR SERVER that the temporary flight plan has been created
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event                =>
              Server_Types.Tmpy_Fpln_Created));

      elsif Fpln_Chg.Refresh_Modif /= Server_Types.Undef then

         -- Warns IFR SERVER that the temporary flight plan has been updated
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event     =>
              Server_Types.Tmpy_Fpln_Refresh,
                                      Is_Completed => True));

      elsif Fpln_Chg.Lateral_Modif = Server_Types.Sequencement then

         -- Warns IFR SERVER that the temporary flight have been sequenced
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event => Server_Types.Tmpy_Fpln_Sqc));

      else

         -- Warns IFR SERVER that the temporary flight plan has changed
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event                =>
              Server_Types.Tmpy_Fpln_Update));

      end if;

      -- for offset delayed message
      if Fpln_Chg.Lateral_Modif = Server_Types.Offset_Modification
        and then Fpln.Is_Offset_Delayed (New_Fpln)
      then

         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event =>
              Server_Types.Tmpy_Offset_Delayed));

      end if;
   end Send_Tmpy_Fpln_Events;

   -----------------------------------------------
   -----------------------------------------------
   procedure Send_Sec_Fpln_Events
     (Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin)
   is

      use Server_Types;

   begin

      -- Sends flight plan update event
      if Fpln_Chg.Refresh_Modif /= Server_Types.Undef then

         -- Warns IFR SERVER that the secondary flight plan has been updated
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event     =>
              Server_Types.Sec_Fpln_Refresh,
                                      Is_Completed => True));

      elsif Fpln_Chg.Lateral_Modif = Server_Types.Sequencement then

         -- Warns IFR SERVER that the secondary flight have been sequenced
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event => Server_Types.Sec_Fpln_Sqc));

      elsif Fpln_Chg.Lateral_Modif = Server_Types.Initialization then

         -- Warns IFR SERVER that the secondary flight have been initialized by
         -- a route
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event => Server_Types.Sec_Fpln_Init));

      else

         -- Warns IFR SERVER that the secondary flight plan has changed
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event => Server_Types.Sec_Fpln_Update))
;

      end if;

      if Fpln_Chg.Lateral_Modif /= Server_Types.Swap_Actv_Sec then
         -- Update secondary perfo parameters at destination
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event => Server_Types.Sec_Dest_Update))
;
      end if;

   end Send_Sec_Fpln_Events;

   -----------------------------------------------
   -----------------------------------------------
   procedure Send_Fplns_Events
     (New_Actv_Fpln : in Server_Types.T_Primary_Fpln;
      Old_Actv_Fpln : in Server_Types.T_Primary_Fpln)
   is

      use Server_Types;

   begin
      -- Warns IFR SERVER that the active flight plan has changed
      Server.Send_Event
        (Event_Rqst_Descriptor => (Fm_Event =>
                                     Server_Types.Active_Fpln_Update));
      Send_Offset_Events
        (New_Fpln => New_Actv_Fpln,
         Old_Fpln => Old_Actv_Fpln);

      Send_Actv_Airports_Events
        (New_Fpln => New_Actv_Fpln,
         Old_Fpln => Old_Actv_Fpln);
   end Send_Fplns_Events;

   -----------------------------------------------
   -----------------------------------------------
   procedure Send_Offset_Events
     (New_Fpln : in Server_Types.T_Primary_Fpln;
      Old_Fpln : in Server_Types.T_Primary_Fpln)
   is

   begin

      -- Sends offset update events
      if Fpln.Is_Offset_Active (Old_Fpln) then

         if Fpln.Is_Offset_Active (New_Fpln)
           and then (Fpln.Get_Offset_Dist (Old_Fpln) -
                     Fpln.Get_Offset_Dist (New_Fpln)) >
                    0.01
         then

            -- Warns IFR SERVER that the active flight offset has been inserted
            Server.Send_Event
              (Event_Rqst_Descriptor => (Fm_Event =>
                 Server_Types.Active_Offset_Inserted));

         else
            if not Fpln.Is_Offset_Active (New_Fpln) then

               -- Warns IFR SERVER that the active flight offset has been
               --cleared
               Server.Send_Event
                 (Event_Rqst_Descriptor => (Fm_Event =>
                    Server_Types.Active_Offset_Cleared));

            end if;

            if Fpln.Is_Offset_Delayed (New_Fpln) then

               -- Warns IFR SERVER that the active flight offset has been
               --delayed
               Server.Send_Event
                 (Event_Rqst_Descriptor => (Fm_Event =>
                    Server_Types.Active_Offset_Delayed));

            end if;

         end if;

      elsif Fpln.Is_Offset_Delayed (Old_Fpln) then

         if Fpln.Is_Offset_Active (New_Fpln) then

            -- Warns IFR SERVER that the active flight offset has been inserted
            Server.Send_Event
              (Event_Rqst_Descriptor => (Fm_Event =>
                 Server_Types.Active_Offset_Inserted));

         elsif not Fpln.Is_Offset_Delayed (New_Fpln) then

            -- Warns IFR SERVER that the active flight offset has been cleared
            Server.Send_Event
              (Event_Rqst_Descriptor => (Fm_Event =>
                 Server_Types.Active_Offset_Cleared));

         end if;

      else
         if Fpln.Is_Offset_Active (New_Fpln) then

            -- Warns IFR SERVER that the active flight offset has been inserted
            Server.Send_Event
              (Event_Rqst_Descriptor => (Fm_Event =>
                 Server_Types.Active_Offset_Inserted));

         elsif Fpln.Is_Offset_Delayed (New_Fpln) then

            -- Warns IFR SERVER that the active flight offset has been delayed
            Server.Send_Event
              (Event_Rqst_Descriptor => (Fm_Event =>
                 Server_Types.Active_Offset_Delayed));

         end if;

      end if;
   end Send_Offset_Events;

   ---------------------------------------------------------------
   -- This procedure is called for only Active Fpln Airport Events
   ---------------------------------------------------------------
   procedure Send_Actv_Airports_Events
     (New_Fpln : in Server_Types.T_Primary_Fpln;
      Old_Fpln : in Server_Types.T_Primary_Fpln)
   is

      use Server_Types;
      use Ctc.Factory_Singleton;
      use Ctc.Ctc_Controller;
      use Ctc.Factory;
      use Ctc.Airport_Info;

      L_Ctc_Factory           : constant Ctc.Factory.T_Ctc_Factory_Access :=
         Ctc.Factory_Singleton.Instance_Of;
      L_Act_Dest_Airport_Info : constant
           Ctc.Airport_Info.T_Airport_Info_Class_Access                   :=
         Get_Airport_Info (L_Ctc_Factory.all);

   begin

      -- Sends airport update events
      if not Fpln.Have_Same_Dest (Old_Fpln, New_Fpln) then

         -- Temperature Compensation is inactive
         -- when Destination is modified or canceled
         Set_Ctc_Status (Value => False);
         Ctc.Airport_Info.V_Update_Airport (L_Act_Dest_Airport_Info.all);

         -- Warns IFR SERVER that the active flight destination airport
         -- has changed
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event =>
              Server_Types.Active_Dest_Arpt_Update));

      end if;

      if not Fpln.Have_Same_Origin (Old_Fpln, New_Fpln) then

         -- Warns IFR SERVER that the active flight origin airport
         -- has changed
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event =>
              Server_Types.Active_Org_Arpt_Update));

      end if;

   end Send_Actv_Airports_Events;

   -----------------------------------------------
   -----------------------------------------------
   procedure Update_Sec_With_Actv
     (Old_Actv_Fpln : in Server_Types.T_Primary_Fpln;
      Actv_Fpln     : in Server_Types.T_Primary_Fpln;
      Actv_Fpln_Chg : in Server_Types.T_Fpln_Modif_Origin)
   is

      use Server_Types;
      use Fms_Types;
      use Mission;

      type T_Step_Loc is record
         Index  : Server_Types.T_Leg_Index;
         Step_R : Server_Types.T_Steps_Nbr;
      end record;

      type T_Step_Array is
           array (Fms_Types.T_Integer range
         1 .. 2 * Server_Types.C_Max_Nbr_Steps)
               of T_Step_Loc;

      L_Request_Status               : Server_Types.T_Request_Status;
      L_Matching_Leg                 : Server_Types.T_Leg;
      L_Sec_Fpln_Chg                 : Server_Types.T_Fpln_Modif_Origin;
      L_Sec_Fpln                     : Server_Types.T_Primary_Fpln;
      L_Result_Fpln                  : Server_Types.T_Primary_Fpln;
      L_Leg_Index                    : Server_Types.T_Leg_Index;
      L_Sec_End_Leg_Index            : Server_Types.T_Leg_Index;
      L_Matching_Leg_Index           : Server_Types.T_Leg_Index;
      L_Rta_Index                    : Server_Types.T_Leg_Index;
      L_Legs                         : Server_Types.T_Legs;
      L_Sec_Legs                     : Server_Types.T_Legs;
      L_Step_Array                   : T_Step_Array;
      L_Step_Nbr                     : Fms_Types.T_Integer;
      L_Step_Ref                     : Server_Types.T_Steps_Nbr;
      L_Rta_Nbr                      : Fms_Types.T_Integer;
      L_B_Sec_Full                   : Boolean     := False;
      L_B_Updated                    : Boolean     := False;
      L_B_Matching_Leg               : Boolean     := False;
      L_B_Disc_Ahead                 : Boolean     := False;
      L_Index_Mission_Sec_Fpln       : T_Leg_Index := T_Leg_Index'First;
      L_Index_Mission_Actv_Fpln      : T_Leg_Index := T_Leg_Index'First;
      L_Index_Mission_Old_Actv_Fpln  : Server_Types.T_Leg_Index;
      L_Marker_Mission_Sec_Fpln      : Server_Types.T_Marker;
      L_Marker_Mission_Actv_Fpln     : Server_Types.T_Marker;
      L_Marker_Mission_Old_Actv_Fpln : Server_Types.T_Marker;
      L_B_Found_In_Sec               : Boolean     := False;
      L_B_Found_In_Actv              : Boolean     := False;
      L_B_Found_In_Old_Actv          : Boolean     := False;

   begin

      L_Request_Status := Ok;
      L_Step_Nbr       := 0;
      for I in 1 .. 2 * Server_Types.C_Max_Nbr_Steps loop
         L_Step_Array (Fms_Types.T_Integer (I))  := (0, 0);
      end loop;
      L_Rta_Nbr   := 0;
      L_Rta_Index := 0;

      -- If the secondary flight plan is linked to active flight plan
      --   search for common part between two flight plans
      if G_Sec_Link = Server_Types.Linked then

         -- Retrieves active and secondary flight plans
         Get_Sec_Fpln (L_Sec_Fpln);

         -- Init parameters to treat mission
         if (Actv_Fpln.Params_Mission.Status = Defined) then
            L_Marker_Mission_Actv_Fpln :=
                 Server_Types.T_Marker (Mission.Get_Marker
                                           (This => Actv_Fpln.Params_Mission));
            Legs.Find
              (Legs    => Actv_Fpln.Legs,
               Marker  => L_Marker_Mission_Actv_Fpln,
               Index   => L_Index_Mission_Actv_Fpln,
               B_Found => L_B_Found_In_Actv);
         end if;
         if (Old_Actv_Fpln.Params_Mission.Status = Defined) then
            L_Marker_Mission_Old_Actv_Fpln :=
                 Server_Types.T_Marker (Mission.Get_Marker
                                           (This =>
                                                 Old_Actv_Fpln.Params_Mission));
            Legs.Find
              (Legs    => Old_Actv_Fpln.Legs,
               Marker  => L_Marker_Mission_Old_Actv_Fpln,
               Index   => L_Index_Mission_Old_Actv_Fpln,
               B_Found => L_B_Found_In_Old_Actv);
         end if;
         if (L_Sec_Fpln.Params_Mission.Status = Defined) then
            L_Marker_Mission_Sec_Fpln :=
                 Server_Types.T_Marker (Mission.Get_Marker
                                           (This => L_Sec_Fpln.Params_Mission));
            Legs.Find
              (Legs    => L_Sec_Fpln.Legs,
               Marker  => L_Marker_Mission_Sec_Fpln,
               Index   => L_Index_Mission_Sec_Fpln,
               B_Found => L_B_Found_In_Sec);
         end if;

         -- Search matching leg between old active and secondary flight plans
         -- such that from first leg to matching leg the two flight plans
         -- are identical.
         L_Sec_End_Leg_Index  := Legs.Get_Tail_Index (L_Sec_Fpln.Legs);
         L_Matching_Leg_Index := 0;

         while L_Matching_Leg_Index <= L_Sec_End_Leg_Index
           and then Leg.Are_Same_Legs_Bis
                       (Legs.Get (Old_Actv_Fpln.Legs, L_Matching_Leg_Index),
                        Legs.Get (L_Sec_Fpln.Legs, L_Matching_Leg_Index))
         loop
            L_Matching_Leg_Index := L_Matching_Leg_Index + 1;
         end loop;
         if L_Matching_Leg_Index /= 0 then
            L_B_Matching_Leg     := True;
            L_Matching_Leg_Index := L_Matching_Leg_Index - 1;
         end if;

         if L_B_Matching_Leg and
            (L_Matching_Leg_Index = L_Sec_End_Leg_Index) and
            L_B_Found_In_Old_Actv and
            L_B_Found_In_Sec and
            (L_Sec_Fpln.Params_Mission /= Old_Actv_Fpln.Params_Mission)
         then
            L_B_Matching_Leg     := True;
            L_Matching_Leg_Index := L_Index_Mission_Sec_Fpln;
         end if;

         -- If the matching leg exists,
         if L_B_Matching_Leg then
            -- And is the last leg of two flight plans
            -- Copy revised active flight plan into secondary flight plan
            if L_Matching_Leg_Index = L_Sec_End_Leg_Index then

               L_Result_Fpln := Actv_Fpln;
               L_B_Updated   := True;
               L_Leg_Index   := Legs.Find_Dest (L_Result_Fpln.Legs);

            -- Else if the matching leg exists
            --   Search this matching leg in revised active plan on beginning
            --   from the end of flight plan
            else
               L_Matching_Leg :=
                  Legs.Get (Old_Actv_Fpln.Legs, L_Matching_Leg_Index);
               L_Leg_Index    := Legs.Get_Tail_Index (Actv_Fpln.Legs);
               while L_Leg_Index > 0
                 and then not (Leg.Are_Same_Legs_Bis
                                  (L_Matching_Leg,
                                   Legs.Get (Actv_Fpln.Legs, L_Leg_Index)))
               loop
                  L_Leg_Index := L_Leg_Index - 1;
               end loop;

               if L_Leg_Index = 0
                 and then (not L_B_Found_In_Old_Actv and
                           L_B_Found_In_Actv and
                           L_B_Found_In_Sec)
                 and then (L_Index_Mission_Actv_Fpln /=
                           L_Index_Mission_Sec_Fpln)
               then
                  L_Leg_Index := L_Matching_Leg_Index;
               end if;

               -- Memorize the value of discontinuity of the last matching leg
               -- (concern the legs + 1)
               L_B_Disc_Ahead :=
                    L_Sec_Fpln.Legs.Legs_Array (L_Matching_Leg_Index).
                    B_Disc_Ahead;

               -- If the matching leg is found
               -- Concatenate the revised active flight plan and the secondary
               -- flight plan on taking the first part of revised active
               -- flight plan until the matching leg and the final part of the
               -- secondary flight plan from this matching leg
               if L_Leg_Index /= 0 then
                  L_Legs := Legs.Copy (Actv_Fpln.Legs, 0, L_Leg_Index);
                  -- first part of active
                  L_Sec_Legs :=
                     Legs.Copy
                       (L_Sec_Fpln.Legs,
                        L_Matching_Leg_Index + 1,
                        L_Sec_End_Leg_Index);
                  Legs.Append
                    (First_List     => L_Legs,
                     Second_List    => L_Sec_Legs,
                     Request_Status => L_Request_Status);

                  -- If the final flight plan exceeds the flight plan
                  -- limitations
                  --   (flight plan size, number of steps and number of RTA)
                  --   Send SEC_FULL event
                  if L_Request_Status = Fpln_Full then
                     L_B_Sec_Full := True;
                  else
                     -- Search steps number and RTA number in result flight
                     --plan
                     for I in
                           Server_Types.T_Leg_Index'First ..
                           Legs.Get_Tail_Index (L_Legs)
                     loop
                        L_Step_Ref := Legs.Get_Step_Ref (L_Legs, I);
                        if L_Step_Ref /= 0 then
                           L_Step_Nbr                       := L_Step_Nbr + 1;
                           L_Step_Array (L_Step_Nbr).Index  := I;
                           L_Step_Array (L_Step_Nbr).Step_R := L_Step_Ref;
                        end if;
                        if Legs.Is_Rta_Exist (L_Legs, I) then
                           L_Rta_Nbr   := L_Rta_Nbr + 1;
                           L_Rta_Index := I;
                        end if;
                     end loop;
                     if L_Step_Nbr <= Server_Types.C_Max_Nbr_Steps
                       and then L_Rta_Nbr <= 1
                     then
                        L_Result_Fpln.Legs := L_Legs;
                        -- Restore value of SEC FPLN discontinuity
                        L_Result_Fpln.Legs.Legs_Array (L_Leg_Index).
                             B_Disc_Ahead := L_B_Disc_Ahead;

                        -- Update departure and arrival procedures
                        Update_Sec_Proc_With_Actv
                          (Old_Actv_Fpln      => Old_Actv_Fpln,
                           New_Actv_Fpln      => Actv_Fpln,
                           Old_Sec_Fpln       => L_Sec_Fpln,
                           Matching_Leg_Index => L_Matching_Leg_Index,
                           Departure          => L_Result_Fpln.Departure,
                           Arrival            => L_Result_Fpln.Arrival);
                        -- Recovery of appropriate header, offset, dir_TO and
                        -- Hover procedures
                        L_Result_Fpln.B_Def     := True;
                        L_Result_Fpln.Pilot_Rte := Actv_Fpln.Pilot_Rte;
                        L_Result_Fpln.Flt_Nbr   := Actv_Fpln.Flt_Nbr;
                        L_Result_Fpln.Offset    := Actv_Fpln.Offset;
                        L_Result_Fpln.Dir_To    := Actv_Fpln.Dir_To;
                        L_Result_Fpln.Hover     := Actv_Fpln.Hover;

                        -- Recovery of appropriate mission
                        L_Result_Fpln.Params_Mission :=
                             L_Sec_Fpln.Params_Mission;

                        if L_B_Found_In_Actv and L_B_Found_In_Sec then
                           if (L_Index_Mission_Actv_Fpln <= L_Leg_Index) then
                              L_Result_Fpln.Params_Mission :=
                                   Actv_Fpln.Params_Mission;
                              if (L_Index_Mission_Actv_Fpln =
                                  L_Index_Mission_Sec_Fpln)
                                and then (Actv_Fpln.Params_Mission /=
                                          L_Sec_Fpln.Params_Mission)
                              then
                                 L_Result_Fpln.Params_Mission :=
                                      L_Sec_Fpln.Params_Mission;
                              end if;
                           end if;
                        elsif L_B_Found_In_Actv then
                           if (L_Index_Mission_Actv_Fpln <= L_Leg_Index) then
                              L_Result_Fpln.Params_Mission :=
                                   Actv_Fpln.Params_Mission;
                           end if;
                        end if;

                        -- Recovery of appropriate steps and RTA
                        if L_Rta_Nbr /= 0 then
                           if L_Rta_Index <= L_Leg_Index then
                              L_Result_Fpln.Rta := Actv_Fpln.Rta;
                           else
                              L_Result_Fpln.Rta := L_Sec_Fpln.Rta;
                           end if;
                        else
                           L_Result_Fpln.Rta := Server_Types.Default_Rta;
                        end if;
                        if L_Step_Nbr /= 0 then
                           L_Result_Fpln.Steps.Steps_Nbr :=
                                Server_Types.T_Steps_Nbr (L_Step_Nbr);
                           for I in 1 .. L_Step_Nbr loop
                              if L_Step_Array (I).Index <= L_Leg_Index then
                                 L_Result_Fpln.Steps.Steps_Array (
                                      Server_Types.T_Steps_Nbr (I)) :=
                                      Actv_Fpln.Steps.Steps_Array (
                                      L_Step_Array (I).Step_R);
                              else
                                 L_Result_Fpln.Steps.Steps_Array (
                                      Server_Types.T_Steps_Nbr (I)) :=
                                      L_Sec_Fpln.Steps.Steps_Array (
                                      L_Step_Array (I).Step_R);
                              end if;
                           end loop;
                        else
                           L_Result_Fpln.Steps := Server_Types.Default_Steps;
                        end if;
                        L_B_Updated := True;
                     else
                        L_B_Sec_Full := True;
                     end if;
                  end if;

               end if;
            end if;
         end if;

         -- Update the secondary flight plan or send SEC_FULL event
         --   and secondary perfo parameters at destination
         if L_B_Updated then

            L_Sec_Fpln_Chg :=
              (Lateral_Modif  => Server_Types.Rev_Propagation,
               Vertical_Modif => Actv_Fpln_Chg.Vertical_Modif,
               Refresh_Modif  => Actv_Fpln_Chg.Refresh_Modif,
               Modified_Elt   => Legs.Get_Marker
                                   (Legs  => L_Result_Fpln.Legs,
                                    Index => L_Leg_Index));
            Put_Sec_Fpln (L_Result_Fpln, L_Sec_Fpln_Chg, G_Sec_Link);
         else
            Server.Send_Event
              (Event_Rqst_Descriptor => (Fm_Event =>
                 Server_Types.Sec_Dest_Update));
         end if;

         if L_B_Sec_Full then
            Server.Send_Event
              (Event_Rqst_Descriptor => (Fm_Event => Server_Types.Sec_Full));
         end if;

      else
         -- Update secondary perfo parameters at destination
         Server.Send_Event
           (Event_Rqst_Descriptor => (Fm_Event => Server_Types.Sec_Dest_Update))
;
      end if;

   end Update_Sec_With_Actv;

   -----------------------------------------------
   -----------------------------------------------
   procedure Update_Sec_Proc_With_Actv
     (Old_Actv_Fpln      : in Server_Types.T_Primary_Fpln;
      New_Actv_Fpln      : in Server_Types.T_Primary_Fpln;
      Old_Sec_Fpln       : in Server_Types.T_Primary_Fpln;
      Matching_Leg_Index : in Server_Types.T_Leg_Index;
      Departure          : out Server_Types.T_Departure;
      Arrival            : out Server_Types.T_Arrival)
   is

      use Server_Types;
      use Fms_Types;
      L_Via_Type                                               :
           Server_Types.T_Via_Type;
      L_B_In_Dep, L_B_In_Arr                                   : Boolean;
      L_1st_Sid_Rwy_Ind, L_1st_Sid_Ind, L_1st_Sid_Trans_Ind    :
           Server_Types.T_Leg_Index_C;
      L_1st_Star_Trans_Ind, L_1st_Star_Ind, L_1st_Star_Rwy_Ind :
           Server_Types.T_Leg_Index_C;
      L_1st_Appr_Trans_Ind, L_1st_Appr_Ind                     :
           Server_Types.T_Leg_Index_C;

   begin
      L_1st_Sid_Rwy_Ind    := (Status => Fms_Types.Undefined);
      L_1st_Sid_Ind        := (Status => Fms_Types.Undefined);
      L_1st_Sid_Trans_Ind  := (Status => Fms_Types.Undefined);
      L_1st_Star_Trans_Ind := (Status => Fms_Types.Undefined);
      L_1st_Star_Ind       := (Status => Fms_Types.Undefined);
      L_1st_Star_Rwy_Ind   := (Status => Fms_Types.Undefined);
      L_1st_Appr_Trans_Ind := (Status => Fms_Types.Undefined);
      L_1st_Appr_Ind       := (Status => Fms_Types.Undefined);

      -- If via type of matching leg is not one of segment types of procedures
      --   set departure procedure to active departure
      --      and arrival procedure to secondary arrival
      L_Via_Type :=
         Legs.Get_Via_Type
           (Legs  => Old_Actv_Fpln.Legs,
            Index => Matching_Leg_Index);
      L_B_In_Dep :=
           (L_Via_Type = Server_Types.Sid
           or else L_Via_Type = Server_Types.Sid_Trans
           or else L_Via_Type = Server_Types.Sid_Rwy_Trans);
      L_B_In_Arr :=
           (L_Via_Type = Server_Types.Appr
           or else L_Via_Type = Server_Types.Appr_Trans
           or else L_Via_Type = Server_Types.Miss_Appr
           or else L_Via_Type = Server_Types.Act_Miss_Appr
           or else L_Via_Type = Server_Types.Star
           or else L_Via_Type = Server_Types.Star_Rte_Trans
           or else L_Via_Type = Server_Types.Star_Rwy_Trans);
      if not L_B_In_Dep and then not L_B_In_Arr then
         Departure := New_Actv_Fpln.Departure;
         Arrival   := Old_Sec_Fpln.Arrival;

      -- If matching leg belongs to departure procedure of old active fpln
      --   set arrival procedure to secondary arrival
      --   search first leg of every segment of departure procedure
      elsif L_B_In_Dep then
         Arrival := Old_Sec_Fpln.Arrival;
         Legs.Find_First
           (Old_Actv_Fpln.Legs,
            Server_Types.Sid_Rwy_Trans,
            L_1st_Sid_Rwy_Ind);
         Legs.Find_First
           (Old_Actv_Fpln.Legs,
            Server_Types.Sid_Trans,
            L_1st_Sid_Trans_Ind);
         Legs.Find_First (Old_Actv_Fpln.Legs, Server_Types.Sid, L_1st_Sid_Ind);

         if L_1st_Sid_Trans_Ind.Status = Fms_Types.Defined
           and then Matching_Leg_Index >= L_1st_Sid_Trans_Ind.Value
         then
            Departure := New_Actv_Fpln.Departure;
         elsif L_1st_Sid_Ind.Status = Fms_Types.Defined
           and then Matching_Leg_Index >= L_1st_Sid_Ind.Value
         then
            Departure :=
              (B_Def             => True,
               Airport           => New_Actv_Fpln.Departure.Airport,
               Trans_Alt         => New_Actv_Fpln.Departure.Trans_Alt,
               Rwy               => New_Actv_Fpln.Departure.Rwy,
               Sid_Ident         => New_Actv_Fpln.Departure.Sid_Ident,
               Sid_Max_Trans_Alt => New_Actv_Fpln.Departure.Sid_Max_Trans_Alt,
               Sid_Trans_Ident   => Old_Sec_Fpln.Departure.Sid_Trans_Ident);
         elsif L_1st_Sid_Rwy_Ind.Status = Fms_Types.Defined
           and then Matching_Leg_Index >= L_1st_Sid_Rwy_Ind.Value
         then
            Departure :=
              (B_Def             => True,
               Airport           => New_Actv_Fpln.Departure.Airport,
               Trans_Alt         => New_Actv_Fpln.Departure.Trans_Alt,
               Rwy               => New_Actv_Fpln.Departure.Rwy,
               Sid_Ident         => Old_Sec_Fpln.Departure.Sid_Ident,
               Sid_Max_Trans_Alt => Old_Sec_Fpln.Departure.Sid_Max_Trans_Alt,
               Sid_Trans_Ident   => Old_Sec_Fpln.Departure.Sid_Trans_Ident);
         end if;

      -- If matching leg belongs to arrival procedure of old active fpln
      --   set departure procedure to active departure
      --   search first leg of every segment of arrival procedure
      elsif L_B_In_Arr then
         Departure := New_Actv_Fpln.Departure;
         Legs.Find_First
           (Old_Actv_Fpln.Legs,
            Server_Types.Star_Rte_Trans,
            L_1st_Star_Trans_Ind);
         Legs.Find_First
           (Old_Actv_Fpln.Legs,
            Server_Types.Star,
            L_1st_Star_Ind);
         Legs.Find_First
           (Old_Actv_Fpln.Legs,
            Server_Types.Star_Rwy_Trans,
            L_1st_Star_Rwy_Ind);
         Legs.Find_First
           (Old_Actv_Fpln.Legs,
            Server_Types.Appr_Trans,
            L_1st_Appr_Trans_Ind);
         Legs.Find_First
           (Old_Actv_Fpln.Legs,
            Server_Types.Appr,
            L_1st_Appr_Ind);

         if L_1st_Appr_Ind.Status = Fms_Types.Defined
           and then Matching_Leg_Index >= L_1st_Appr_Ind.Value
         then
            Arrival := New_Actv_Fpln.Arrival;
         elsif L_1st_Appr_Trans_Ind.Status = Fms_Types.Defined
           and then Matching_Leg_Index >= L_1st_Appr_Trans_Ind.Value
         then
            Arrival :=
              (B_Def            => True,
               Airport          => Old_Sec_Fpln.Arrival.Airport,
               Trans_Alt        => Old_Sec_Fpln.Arrival.Trans_Alt,
               Rwy              => Old_Sec_Fpln.Arrival.Rwy,
               Navaid           => Server_Types.Default_Navaid,
               Star_Ident       => New_Actv_Fpln.Arrival.Star_Ident,
               Star_Trans_Ident => New_Actv_Fpln.Arrival.Star_Trans_Ident,
               Appr_Via_Ident   => New_Actv_Fpln.Arrival.Appr_Via_Ident,
               Appr             => Old_Sec_Fpln.Arrival.Appr,
               Mda              => Old_Sec_Fpln.Arrival.Mda,
               Map_Alt_Cstr     => Old_Sec_Fpln.Arrival.Map_Alt_Cstr);

         elsif (L_1st_Star_Rwy_Ind.Status = Fms_Types.Defined
               and then Matching_Leg_Index >= L_1st_Star_Rwy_Ind.Value)
           or else (L_1st_Star_Ind.Status = Fms_Types.Defined
                   and then Matching_Leg_Index >= L_1st_Star_Ind.Value)
         then
            Arrival :=
              (B_Def            => True,
               Airport          => Old_Sec_Fpln.Arrival.Airport,
               Trans_Alt        => Old_Sec_Fpln.Arrival.Trans_Alt,
               Rwy              => Old_Sec_Fpln.Arrival.Rwy,
               Navaid           => Server_Types.Default_Navaid,
               Star_Ident       => New_Actv_Fpln.Arrival.Star_Ident,
               Star_Trans_Ident => New_Actv_Fpln.Arrival.Star_Trans_Ident,
               Appr_Via_Ident   => Old_Sec_Fpln.Arrival.Appr_Via_Ident,
               Appr             => Old_Sec_Fpln.Arrival.Appr,
               Mda              => Old_Sec_Fpln.Arrival.Mda,
               Map_Alt_Cstr     => Old_Sec_Fpln.Arrival.Map_Alt_Cstr);

         elsif L_1st_Star_Trans_Ind.Status = Fms_Types.Defined
           and then Matching_Leg_Index >= L_1st_Star_Trans_Ind.Value
         then
            Arrival :=
              (B_Def            => True,
               Airport          => Old_Sec_Fpln.Arrival.Airport,
               Trans_Alt        => Old_Sec_Fpln.Arrival.Trans_Alt,
               Rwy              => Old_Sec_Fpln.Arrival.Rwy,
               Navaid           => Server_Types.Default_Navaid,
               Star_Ident       => Old_Sec_Fpln.Arrival.Star_Ident,
               Star_Trans_Ident => New_Actv_Fpln.Arrival.Star_Trans_Ident,
               Appr_Via_Ident   => Old_Sec_Fpln.Arrival.Appr_Via_Ident,
               Appr             => Old_Sec_Fpln.Arrival.Appr,
               Mda              => Old_Sec_Fpln.Arrival.Mda,
               Map_Alt_Cstr     => Old_Sec_Fpln.Arrival.Map_Alt_Cstr);
         end if;
      end if;

   end Update_Sec_Proc_With_Actv;

   -----------------------------------------------
   -----------------------------------------------
   procedure Is_Mission_Defined
     (Fpln_Type              : in Server_Types.T_Fpln_Type;
      Mission_Is_Defined     : out Boolean;
      Num_Leg_Before_Mission : out Server_Types.T_Leg_Index)
   is

      use Server_Types;
      use Fms_Types;
      use Os.Abstract_Semaphore;

      L_Params_Mission : Mission.T_Mission;
      L_Marker         : Lang.Primitifs.T_Nat;

      function Get_Leg_Index_From_Marker
        (Fpln   : in Server_Types.T_Primary_Fpln;
         Marker : in Lang.Primitifs.T_Nat)
         return   Server_Types.T_Leg_Index
      is
         Leg_Index_Pt : Server_Types.T_Leg_Index;
         L_Nb_Legs    : Server_Types.T_Leg_Nbr;
         L_Index      : Server_Types.T_Leg_Index;
         B_Found      : Boolean;

      begin

         -- find number of legs in fpln
         L_Nb_Legs := Fpln.Legs.Legs_Nbr;

         --init status and LEG_INDEX_PT
         Leg_Index_Pt := 0;
         L_Index      := 0;
         B_Found      := False;

         -- read FPLN until MARKER_PT corresponds to FPLN.MARK
         while L_Index < L_Nb_Legs and not B_Found loop

            if Fpln.Legs.Legs_Array (L_Index).Mark =
               Server_Types.T_Marker (Marker)
            then
               -- OK found
               Leg_Index_Pt := L_Index;
               --to exit from loop
               B_Found := True;
            else
               -- next leg
               L_Index := L_Index + 1;
            end if;

            exit when B_Found;

         end loop;

         return Leg_Index_Pt;

      end Get_Leg_Index_From_Marker;
   -------------------------------

   begin

      Mission_Is_Defined     := False;
      Num_Leg_Before_Mission := 0;

      if Fpln_Type = Server_Types.Actv then

            V_Get_Token (This => G_Actv_Sem);

            if G_Actv_Fpln.Params_Mission.Status = Fms_Types.Defined then
               Mission_Is_Defined     := True;
               L_Params_Mission       := G_Actv_Fpln.Params_Mission;
               L_Marker               := Mission.Get_Marker (L_Params_Mission);
               Num_Leg_Before_Mission :=
                  Get_Leg_Index_From_Marker (G_Actv_Fpln, L_Marker);
            end if;

            V_Free_Token (This => G_Actv_Sem);

      elsif Fpln_Type = Server_Types.Sec then

            V_Get_Token (This => G_Sec_Sem);

            if G_Sec_Fpln.Params_Mission.Status = Fms_Types.Defined then
               Mission_Is_Defined     := True;
               L_Params_Mission       := G_Sec_Fpln.Params_Mission;
               L_Marker               := Mission.Get_Marker (L_Params_Mission);
               Num_Leg_Before_Mission :=
                  Get_Leg_Index_From_Marker (G_Sec_Fpln, L_Marker);
            end if;

            V_Free_Token (This => G_Sec_Sem);

      end if;

   end Is_Mission_Defined;

   -----------------------------------------------
   -----------------------------------------------
   procedure Is_Mission_Active
     (Fpln_Type         : in Server_Types.T_Fpln_Type;
      Mission_Is_Active : out Boolean)
   is

      use Os.Abstract_Semaphore;
      use Server_Types;
      use Fms_Types;

      L_Params_Mission : Mission.T_Mission;

   begin

      Mission_Is_Active := False;

      if Fpln_Type = Server_Types.Actv then

            V_Get_Token (This => G_Actv_Sem);

            if G_Actv_Fpln.Params_Mission.Status = Fms_Types.Defined then
               L_Params_Mission  := G_Actv_Fpln.Params_Mission;
               Mission_Is_Active := Mission.Is_Active (L_Params_Mission);
            else
               Mission_Is_Active := False;
            end if;

            V_Free_Token (This => G_Actv_Sem);

      elsif Fpln_Type = Server_Types.Sec then

            V_Get_Token (This => G_Sec_Sem);

            if G_Sec_Fpln.Params_Mission.Status = Fms_Types.Defined then
               L_Params_Mission  := G_Sec_Fpln.Params_Mission;
               Mission_Is_Active := Mission.Is_Active (L_Params_Mission);
            else
               Mission_Is_Active := False;
            end if;

            V_Free_Token (This => G_Sec_Sem);

      end if;

   end Is_Mission_Active;

   -----------------------------------------------
   -----------------------------------------------
   procedure Update_Sec_Arrival_Transalt
     (Arrival_Actv : in Server_Types.T_Arrival)
   is

      use General_Fms_Types;

      L_Fpln_Sec : Server_Types.T_Primary_Fpln;
      L_Fpln_Chg : constant Server_Types.T_Fpln_Modif_Origin :=
        (Lateral_Modif  => Server_Types.Undef,
         Vertical_Modif => Server_Types.Undef,
         Refresh_Modif  => Server_Types.Undef,
         Modified_Elt   => 0);

   begin

      Fplns_Ctrl.Get_Sec_Fpln (L_Fpln_Sec);

      if Arrival.Have_Same_Arpt (Arrival_Actv, L_Fpln_Sec.Arrival) then

         if L_Fpln_Sec.Arrival.Trans_Alt.Status /=
            General_Fms_Types.Undefined
           and then L_Fpln_Sec.Arrival.Trans_Alt.Is_Default
         then

            L_Fpln_Sec.Arrival.Trans_Alt := Arrival_Actv.Trans_Alt;

            Fplns_Ctrl.Put_Sec_Fpln
              (P_Fpln     => L_Fpln_Sec,
               Fpln_Chg   => L_Fpln_Chg,
               Fpln_Link  => C_Conv_Link (Fplns_Ctrl.Is_Sec_Fpln_Linked),
               B_Save_Sec => not (Fplns_Ctrl.Is_Sec_Fpln_Linked));

         end if;

      end if;

   end Update_Sec_Arrival_Transalt;

end Fplns_Ctrl;
