--#----------------------------------------------------------------------------
--#         COPYRIGHT BY THALES AVIONICS ALL RIGHTS RESTRICTED
--#----------------------------------------------------------------------------

-- visibility on required modules :
--  visibility on implementing modules :
-- package Ada gdhjdhjqjd
----------------------------------------------
with FPLNS_MANAGER;
with FPLNS_MANAGER.MISSION_CONTROLLER;
with FPLNS_MANAGER.Wind_Profile_Controller;
with Fplns_Manager.Dto_Abeam_Controller;
with Fplns_Manager.Ato_Controller;
with Fplns_Manager.Lat_Lon_X_Controller;
with FPLNS_CTRL;

package body FPLNS is

   procedure CANCEL(B_SAVE_SEC : in Boolean);

   procedure INITIALIZE_ACTV_BY_ROUTE
     (RTE_IDENT      : in NAVDB_TYPES.T_ROUTE_IDENT;
      RTE_DB_TYPE    : in NAVDB_TYPES.T_ROUTE_DB_TYPE;
      B_REVERSE      : in BOOLEAN;
      REQUEST_STATUS : out SERVER_TYPES.T_REQUEST_STATUS) renames
     FPLNS_MANAGER.INITIALIZE_ACTV_BY_ROUTE;

   procedure INITIALIZE_ACTV_BY_FROM_TO
     (FROM_ARPT      : in NAVDB_TYPES.T_FIX_POINT;
      To_Arpt        : in Navdb_Types.T_Fix_Point;
      Flight_Nbr     : in Fms_Types.T_Ident;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INITIALIZE_ACTV_BY_FROM_TO;

   procedure INITIALIZE
     (B_COLD_START             : in Boolean;
      Fplns_Resources          : in
	Fplns_Resources_Factory.T_Resources_Factory_Class_Access;
      Champ_Magnetique_Factory : in CHAMP_MAGNETIQUE.Factory.T_Factory_Access)
      renames FPLNS_MANAGER.INITIALIZE;

   procedure CANCEL_ACTV (New_Active_Mission : in Boolean := False) renames
     FPLNS_MANAGER.CANCEL_ACTV;

   procedure INITIALIZE_TMPY_BY_ROUTE
     (RTE_IDENT      : in NAVDB_TYPES.T_ROUTE_IDENT;
      RTE_DB_TYPE    : in NAVDB_TYPES.T_ROUTE_DB_TYPE;
      B_REVERSE      : in Boolean;

      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INITIALIZE_TMPY_BY_ROUTE;

   procedure INITIALIZE_TMPY_BY_FROM_TO
     (FROM_ARPT      : in NAVDB_TYPES.T_FIX_POINT;
      To_Arpt        : in Navdb_Types.T_Fix_Point;
      FLIGHT_NBR     : in FMS_TYPES.T_IDENT;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INITIALIZE_TMPY_BY_FROM_TO;

   procedure CANCEL_TMPY renames FPLNS_MANAGER.CANCEL_TMPY;

   procedure ACTIVATE_TMPY
     (AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.ACTIVATE_TMPY;


   procedure INITIALIZE_SEC_BY_ROUTE
     (RTE_IDENT      : in NAVDB_TYPES.T_ROUTE_IDENT;
      RTE_DB_TYPE    : in NAVDB_TYPES.T_ROUTE_DB_TYPE;
      B_REVERSE      : in Boolean;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INITIALIZE_SEC_BY_ROUTE;

   procedure INITIALIZE_SEC_BY_FROM_TO
     (FROM_ARPT      : in NAVDB_TYPES.T_FIX_POINT;
      TO_ARPT        : in NAVDB_TYPES.T_FIX_POINT;
      FLIGHT_NBR     : in Fms_Types.T_Ident;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INITIALIZE_SEC_BY_FROM_TO;

   procedure INITIALIZE_SEC_BY_COPY
     (REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INITIALIZE_SEC_BY_COPY;

   procedure CANCEL_SEC renames FPLNS_MANAGER.CANCEL_SEC;

   procedure ACTIVATE_SEC
     (REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.ACTIVATE_SEC;

   procedure SWAP_SEC_ACTV
     (REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.SWAP_SEC_ACTV;

   procedure UNDO_FPLN (REQUEST_STATUS : out Server_Types.T_Request_Status)
			renames FPLNS_MANAGER.UNDO_FPLN;

   function SEC_HAS_SAME_DEST_ACTV
     (FPLN1 : in Server_Types.T_Primary_Fpln;
      FPLN2 : in Server_Types.T_Primary_Fpln)
      return  Boolean renames FPLNS_MANAGER.SEC_HAS_SAME_DEST_ACTV;

   procedure PUT_STEP
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      ALT                 : in Fms_Types.T_Ac_Alt_Ft_C;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      AC_PARAMETERS       : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.PUT_STEP;

   procedure CLEAR_STEP
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.CLEAR_STEP;

   procedure MODIFY_STEP_MODE
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      MODE                : in TYPES_PERFO.T_CRZ_MODE_TYPE;
      IAS_CONS            : in TYPES_APPLI.VITESSE;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_STEP_MODE;

   procedure PUT_ALT_CSTR
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      ALT_CSTR            : in Fms_Types.T_Ac_Alt_Ft_C;
      CSTR_TYPE           : in Server_Types.T_Alt_Type;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.PUT_ALT_CSTR;

   procedure PUT_FPA_CSTR
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      FPA_CSTR            : in Fms_Types.T_Ang_Rel_Deg;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.PUT_FPA_CSTR;

   procedure PUT_SPD_CSTR
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      SPD_CSTR            : in Fms_Types.T_Ac_Speed_Kt;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.PUT_SPD_CSTR;

   procedure PUT_RTA_CSTR
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      RTA_TIME            : in Fms_Types.T_Day_Duration_S;
      RTA_TYPE            : in Server_Types.T_Rta_Type;
      AC_PARAMETERS       : in Server_Types.T_Ac_Parameters;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      B_DEL_OTHER_RTA     : in Boolean;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.PUT_RTA_CSTR;

   procedure PUT_OVERFLY
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.PUT_OVERFLY;

   procedure CLEAR_ALT_CSTR
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.CLEAR_ALT_CSTR;

   procedure CLEAR_FPA_CSTR
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.CLEAR_FPA_CSTR;

   procedure CLEAR_SPD_CSTR
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.CLEAR_SPD_CSTR;

   procedure CLEAR_RTA_CSTR
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.CLEAR_RTA_CSTR;

   procedure CLEAR_OVERFLY
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.CLEAR_OVERFLY;

   procedure INSERT_LATLON_WPT
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      LAT                 : in Fms_Types.T_Latitude;
      LONG                : in Fms_Types.T_Longitude;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      MARKER_TYPE         : in Server_Types.T_Marker_Type;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_LATLON_WPT;

   procedure Insert_Ato_Wpt
     (Fpln_Type           : in SERVER_TYPES.T_FPLN_TYPE;
      Distance            : in Fms_Types.T_Elem_Distance_Nm;
      Selected_Leg_Marker : in SERVER_TYPES.T_MARKER;
      Marker_Type         : in SERVER_TYPES.T_MARKER_TYPE;
      Request_Status      : out SERVER_TYPES.T_REQUEST_STATUS) renames
     Fplns_Manager.Ato_Controller.Insert_Ato_Wpt;

   procedure Insert_Lat_Lon_X_Wpt
     (Fpln_Type      : in Server_Types.T_Fpln_Type;
      Lat            : in Fms_Types.T_Latitude;
      B_Lat_Def      : in Boolean;
      Lon            : in Fms_Types.T_Longitude;
      B_Lon_Def      : in Boolean;
      Marker         : in Server_Types.T_Marker;
      Marker_Type    : in Server_Types.T_Marker_Type;
      Request_Status : out Server_Types.T_Request_Status) renames
     Fplns_Manager.Lat_Lon_X_Controller.Insert_Lat_Lon_X_Wpt;

   procedure INSERT_PBD_WPT
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      PLACE               : in NAVDB_TYPES.T_FIX_POINT;
      BRG                 : in Fms_Types.T_Ang_Abs_Deg;
      NORD                : in Boolean;
      DATE                : in Server_Types.T_Ac_Clock;
      DIST                : in Fms_Types.T_Elem_Distance_Nm;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      MARKER_TYPE         : in Server_Types.T_Marker_Type;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_PBD_WPT;

   procedure INSERT_PBPB_WPT
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      PLACE1              : in NAVDB_TYPES.T_FIX_POINT;
      BRG1                : in Fms_Types.T_Ang_Abs_Deg;
      PLACE2              : in NAVDB_TYPES.T_FIX_POINT;
      BRG2                : in Fms_Types.T_Ang_Abs_Deg;
      NORD                : in Boolean;
      DATE                : in Server_Types.T_Ac_Clock;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      MARKER_TYPE         : in Server_Types.T_Marker_Type;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_PBPB_WPT;

   procedure INSERT_FIX_WPT
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      NEW_WPT             : in NAVDB_TYPES.T_FIX_POINT;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      MARKER_TYPE         : in Server_Types.T_Marker_Type;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_FIX_WPT;

   procedure INSERT_AIRWAY
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      AIRWAY_IDENT        : in NAVDB_TYPES.T_AIRWAY_IDENT;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      FROM_FIX_POINT      : in NAVDB_TYPES.T_FIX_POINT;
      TO_FIX_POINT        : in NAVDB_TYPES.T_FIX_POINT;
      TO_MARKER           : out Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_AIRWAY;

   procedure Active_Mission
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      HOUR           : in Fms_Types.T_Day_Duration_S;
      DATE           : in Fms_Types.T_Date;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MISSION_CONTROLLER.Active;

   procedure Desactive_Mission
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MISSION_CONTROLLER.Desactive;

   procedure Insert_Mission
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      MARKER         : in Server_Types.T_Marker;
      DRAG_INDEX     : in TYPES_PERFO.T_INTEGER_VALID;
      CRZ_ALT        : in Fms_Types.T_Ac_Alt_Ft_C;
      CRZ_MODE       : in TYPES_PERFO.T_CRZ_MODE_TYPE;
      ENGINE         : in TYPES_PERFO.T_INTEGER_VALID;
      Ias            : in Fms_Types.T_Ac_Speed_Kt_C;
      Fuel_Flow      : in Fms_Types.T_Weight_Kg_Per_S_C;
      DURATION       : in Fms_Types.T_Day_Duration_S_C;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MISSION_CONTROLLER.Insert_Mission;

   procedure Clear_Mission
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MISSION_CONTROLLER.Clear_Mission;

   procedure Insert_Mission_Crz_Alt
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      CRZ_ALT        : in Fms_Types.T_Ac_Alt_Ft_C;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MISSION_CONTROLLER.Insert_Crz_Alt;

   procedure Insert_Mission_Ias
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      Ias            : in Fms_Types.T_Ac_Speed_Kt_C;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MISSION_CONTROLLER.Insert_Ias;

   procedure Insert_Mission_Fuel_Flow
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      Fuel_Flow      : in Fms_Types.T_Weight_Kg_Per_S_C;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MISSION_CONTROLLER.Insert_Fuel_Flow;

   procedure Insert_Mission_Duration
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      DURATION       : in Fms_Types.T_Day_Duration_S_C;
      HOUR           : in Fms_Types.T_Day_Duration_S;
      DATE           : in Fms_Types.T_Date;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MISSION_CONTROLLER.Insert_Duration;

   procedure Insert_Dep_Transalt
     (Fpln_Type      : in Server_Types.T_Fpln_Type;
      Trans_Alt      : in Fms_Types.T_Distance_Ft;
      Request_Status : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.Insert_Dep_Transalt;

   procedure Clear_Dep_Transalt
     (Fpln_Type      : in Server_Types.T_Fpln_Type;
      Request_Status : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.Clear_Dep_Transalt;

   procedure Insert_Dest_Transalt
     (Fpln_Type      : in Server_Types.T_Fpln_Type;
      Trans_Alt      : in Fms_Types.T_Distance_Ft;
      Request_Status : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.Insert_Dest_Transalt;

   procedure Clear_Dest_Transalt
     (Fpln_Type      : in Server_Types.T_Fpln_Type;
      Request_Status : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.Clear_Dest_Transalt;

   procedure CLEAR_LEG
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.CLEAR_LEG;

   procedure CLEAR_DISCON
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      AC_PARAMETERS       : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.CLEAR_DISCON;

   procedure INSERT_DISCON
     (FPLN_TYPE           : in Server_Types.T_Fpln_Type;
      SELECTED_LEG_MARKER : in Server_Types.T_Marker;
      MARKER_TYPE         : in Server_Types.T_Marker_Type;
      REQUEST_STATUS      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_DISCON;

   procedure RESTRING_MISSED_APPR renames FPLNS_MANAGER.RESTRING_MISSED_APPR;

   procedure MODIFY_STAR_TRANS
     (FPLN_TYPE        : in Server_Types.T_Fpln_Type;
      STAR_TRANS_IDENT : in Server_Types.T_Proc_Ident;
      REQUEST_STATUS   : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_STAR_TRANS;

   procedure MODIFY_STAR
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      STAR_IDENT     : in Server_Types.T_Proc_Ident;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_STAR;

   procedure MODIFY_APPR_VIA
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      APPR_VIA_IDENT : in Server_Types.T_Proc_Ident;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_APPR_VIA;

   procedure MODIFY_APPR
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      APPR_IDENT     : in Server_Types.T_Proc_Ident;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_APPR;

   procedure SELECT_NEW_DEST
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      DEST_ARPT      : in NAVDB_TYPES.T_FIX_POINT;
      LEG_MARKER     : in Server_Types.T_Marker;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.SELECT_NEW_DEST;

   procedure MODIFY_DEP_RWY
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      DEP_RWY_IDENT  : in Server_Types.T_Proc_Ident;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_DEP_RWY;

   procedure MODIFY_SID
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      SID_IDENT      : in Server_Types.T_Proc_Ident;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_SID;

   procedure MODIFY_SID_TRANS
     (FPLN_TYPE       : in Server_Types.T_Fpln_Type;
      SID_TRANS_IDENT : in Server_Types.T_Proc_Ident;
      REQUEST_STATUS  : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_SID_TRANS;

   procedure INSERT_HOLD
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      LEG_MARKER     : in Server_Types.T_Marker;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_HOLD;

   procedure MODIFY_HOLD_TIME
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      HOLD_TIME      : in Fms_Types.T_Day_Duration_S;
      LEG_MARKER     : in Server_Types.T_Marker;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_HOLD_TIME;

   procedure MODIFY_HOLD_DIST
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      HOLD_DIST      : in Fms_Types.T_Elem_Distance_Nm;
      LEG_MARKER     : in Server_Types.T_Marker;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_HOLD_DIST;

   procedure MODIFY_HOLD_CRS
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      HOLD_CRS       : in Fms_Types.T_Ang_Abs_Deg;
      LEG_MARKER     : in Server_Types.T_Marker;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_HOLD_CRS;

   procedure MODIFY_HOLD_TURN_DIR
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      HOLD_TURN_DIR  : in Fms_Types.T_Turn_Dir;
      LEG_MARKER     : in Server_Types.T_Marker;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_HOLD_TURN_DIR;

   procedure MODIFY_HOLD_SPD
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      HOLD_SPEED     : in Fms_Types.T_Ac_Speed_Kt;
      LEG_MARKER     : in Server_Types.T_Marker;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_HOLD_SPD;

   procedure INSERT_DTO_LATLON
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      LAT            : in Fms_Types.T_Latitude;
      LONG           : in Fms_Types.T_Longitude;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_DTO_LATLON;

   procedure INSERT_DTO_PBD
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      PLACE          : in NAVDB_TYPES.T_FIX_POINT;
      BRG            : in Fms_Types.T_Ang_Abs_Deg;
      NORD           : in Boolean;
      DIST           : in Fms_Types.T_Elem_Distance_Nm;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_DTO_PBD;

   procedure INSERT_DTO_PBPB
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      PLACE1         : in NAVDB_TYPES.T_FIX_POINT;
      BRG1           : in Fms_Types.T_Ang_Abs_Deg;
      PLACE2         : in NAVDB_TYPES.T_FIX_POINT;
      BRG2           : in Fms_Types.T_Ang_Abs_Deg;
      NORD           : in Boolean;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_DTO_PBPB;

   procedure INSERT_DTO_FPLN_FIX
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      LEG_MARKER     : in Server_Types.T_Marker;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_DTO_FPLN_FIX;

   procedure INSERT_DTO_DB_FIX
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      WPT_FIX        : in NAVDB_TYPES.T_FIX_POINT;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_DTO_DB_FIX;

   procedure INSERT_DTO_ALT_CSTR
     (ALT_CSTR       : in Fms_Types.T_Ac_Alt_Ft_C;
      CSTR_TYPE      : in Server_Types.T_Alt_Type;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_DTO_ALT_CSTR;

   procedure INSERT_DTO_RADIAL
     (RADIAL         : in Fms_Types.T_Ang_Abs_Deg;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_DTO_RADIAL;

   procedure INSERT_DTO_INTCP_DIST
     (INTCP_DIST     : in Fms_Types.T_Elem_Distance_Nm;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_DTO_INTCP_DIST;

   procedure CLEAR_DTO_ALT_CSTR
     (REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.CLEAR_DTO_ALT_CSTR;

   procedure CLEAR_DTO_RADIAL
     (AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.CLEAR_DTO_RADIAL;

   procedure CLEAR_DTO_INTCP_DIST
     (AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.CLEAR_DTO_INTCP_DIST;

   procedure INSERT_OFFSET
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      OFFSET_DIST    : in Fms_Types.T_Fpln_Offset_Distance_Nm;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_OFFSET;

   procedure CLEAR_OFFSET renames FPLNS_MANAGER.CLEAR_OFFSET;

   procedure CANCEL_ACTV_OFFSET renames FPLNS_MANAGER.CANCEL_ACTV_OFFSET;

   procedure CANCEL_ACTV_OFFSET2 renames FPLNS_MANAGER.CANCEL_ACTV_OFFSET2;

   procedure CANCEL_TMPY_OFFSET renames FPLNS_MANAGER.CANCEL_TMPY_OFFSET;

   procedure CANCEL_SEC_OFFSET renames FPLNS_MANAGER.CANCEL_SEC_OFFSET;

   procedure SEQUENCE
     (TO_LEG_MARKER : in Server_Types.T_Marker;
      HOUR          : in Fms_Types.T_Day_Duration_S_C;
      DATE          : in Fms_Types.T_Date_C;
      LAST_LEG_SQC  : in Boolean) renames FPLNS_MANAGER.SEQUENCE;

   function IS_FAF_SEQ return Boolean renames FPLNS_MANAGER.IS_FAF_SEQ;

   procedure INSERT_HOVER_WPT
     (FPLN_TYPE            : in Server_Types.T_Fpln_Type;
      AC_PARAMETERS        : in Server_Types.T_Ac_Parameters;
      B_FLAG_IMMEDIATE_ENG : in Boolean;
      MRK_WPT              : in NAVDB_TYPES.T_FIX_POINT;
      REQUEST_STATUS       : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_HOVER_WPT;

   procedure INSERT_HOVER_LATLON
     (FPLN_TYPE            : in Server_Types.T_Fpln_Type;
      AC_PARAMETERS        : in Server_Types.T_Ac_Parameters;
      B_FLAG_IMMEDIATE_ENG : in Boolean;
      LAT                  : in Fms_Types.T_Latitude;
      LONG                 : in Fms_Types.T_Longitude;
      IDENT_TYPE           : in Server_Types.T_Hover_Ident_Type;
      REQUEST_STATUS       : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INSERT_HOVER_LATLON;

   procedure MODIFY_HOVER_WIND
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      WIND           : in Server_Types.T_Ac_Wind;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_HOVER_WIND;

   procedure MODIFY_HOVER_HEIGHT
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      HEIGHT         : in Fms_Types.T_Ac_Alt_Ft;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_HOVER_HEIGHT;

   procedure MODIFY_HOVER_OFFSET
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      AC_PARAMETERS  : in Server_Types.T_Ac_Parameters;
      MRK_OFFSET     : in Fms_Types.T_Fpln_Offset_Distance_Nm;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.MODIFY_HOVER_OFFSET;

   procedure GET_DEFAULT_HOVER_PARAMS
     (WIND       : out Server_Types.T_Ac_Wind;
      HEIGHT     : out Fms_Types.T_Ac_Alt_Ft;
      MRK_OFFSET : out Fms_Types.T_Fpln_Offset_Distance_Nm) renames
     FPLNS_MANAGER.GET_DEFAULT_HOVER_PARAMS;

   function HAVE_SAME_ACTIVE_LEGS return Boolean renames
     FPLNS_MANAGER.HAVE_SAME_ACTIVE_LEGS;

   function FIND_FIX (FIX : in NAVDB_TYPES.T_FIX_POINT) return Boolean renames
     FPLNS_MANAGER.FIND_FIX;

   function HAVE_SAME_ACTIVE_LEGS_WITH_SEC return Boolean renames
     FPLNS_MANAGER.HAVE_SAME_ACTIVE_LEGS_WITH_SEC;

   procedure UPDATE_SEC_FPLN (B_UPDATED : out Boolean) renames
     FPLNS_MANAGER.UPDATE_SEC_FPLN;

   procedure UPDATE_TMPY_FPLN (B_UPDATED : out Boolean) renames
     FPLNS_MANAGER.UPDATE_TMPY_FPLN;

   procedure UPDATE_ACTIVE_FPLN (B_UPDATED : out Boolean) renames
     FPLNS_MANAGER.UPDATE_ACTIVE_FPLN;



   procedure Put_Active_Fpln_Xtalk_Data( Actv_Fpln : in Server_Types.T_Primary_Fpln)
					renames Fplns_Manager.Put_Active_Fpln_Xtalk_Data ;

   procedure Put_Tmpy_Fpln_Xtalk_Data( Tmpy_Fpln : in Server_Types.T_Primary_Fpln)
				      renames Fplns_Manager.Put_Tmpy_Fpln_Xtalk_Data ;




   procedure PUT_SEC_XTALK_DATA
     (SEC_FPLN   : in Server_Types.T_Primary_Fpln;
      SEC_LINKED : in Server_Types.T_Fpln_Link;
      B_SAVE_SEC : in Boolean) renames FPLNS_MANAGER.PUT_SEC_XTALK_DATA;

   procedure GET_XTALK_DATA
     (ACTV_FPLN : out Server_Types.T_Primary_Fpln;
      TMPY_FPLN : out Server_Types.T_Primary_Fpln) renames
     FPLNS_MANAGER.GET_XTALK_DATA;

   procedure GET_SEC_XTALK_DATA
     (SEC_FPLN   : out Server_Types.T_Primary_Fpln;
      SEC_LINKED : out Server_Types.T_Fpln_Link) renames
     FPLNS_MANAGER.GET_SEC_XTALK_DATA;

   procedure GET_ACTV_FPLN
     (FPLN     : out Server_Types.T_Primary_Fpln;
      FPLN_CHG : out Server_Types.T_Fpln_Modif_Origin) renames
     FPLNS_CTRL.GET_ACTV_FPLN;

   procedure GET_ACTV_FPLN (FPLN : out Server_Types.T_Primary_Fpln) renames
     FPLNS_CTRL.GET_ACTV_FPLN;

   procedure GET_TMPY_FPLN
     (FPLN     : out Server_Types.T_Primary_Fpln;
      FPLN_CHG : out Server_Types.T_Fpln_Modif_Origin) renames
     FPLNS_CTRL.GET_TMPY_FPLN;

   procedure GET_TMPY_FPLN (FPLN : out Server_Types.T_Primary_Fpln) renames
     FPLNS_CTRL.GET_TMPY_FPLN;

   procedure GET_SEC_FPLN (FPLN : out Server_Types.T_Primary_Fpln) renames
     FPLNS_CTRL.GET_SEC_FPLN;

   procedure GET_SEC_FPLN
     (FPLN      : out Server_Types.T_Primary_Fpln;
      FPLN_CHG  : out Server_Types.T_Fpln_Modif_Origin;
      FPLN_LINK : out Server_Types.T_Fpln_Link) renames
     FPLNS_CTRL.GET_SEC_FPLN;


   procedure Set_Wind_Set
     (Fpln_Type           : in Server_Types.T_Fpln_Type;
      Marker              : in Server_Types.T_Marker;
      Index_Wind_Modified : in Server_Types.T_Wind_Group_Index;
      Wind_Group       	  : in Wind.Wind_Group.T_Wind_Group;
      Request_Status      : out Server_Types.T_Request_Status)renames
     FPLNS_MANAGER.Wind_Profile_Controller.Set_Wind_Set;

   procedure Clear_Wind_Set
     (Fpln_Type           : in Server_Types.T_Fpln_Type;
      Marker              : in Server_Types.T_Marker;
      Index_Wind_Modified : in Server_Types.T_Wind_Group_Index;
      Request_Status      : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.Wind_Profile_Controller.Clear_Wind_Set;


   function IS_TMPY_FPLN_DEFINED return Boolean renames
     FPLNS_CTRL.IS_TMPY_FPLN_DEFINED;

   function GET_TMPY_DIR_TO return Server_Types.T_Dir_To renames
     FPLNS_CTRL.GET_TMPY_DIR_TO;

   function GET_TMPY_ARRIVAL return Server_Types.T_Arrival renames
     FPLNS_CTRL.GET_TMPY_ARRIVAL;

   function GET_TMPY_DEPARTURE return Server_Types.T_Departure renames
     FPLNS_CTRL.GET_TMPY_DEPARTURE;

   function GET_ACTV_ARRIVAL return Server_Types.T_Arrival renames
     FPLNS_CTRL.GET_ACTV_ARRIVAL;

   function GET_ACTV_DEPARTURE return Server_Types.T_Departure renames
     FPLNS_CTRL.GET_ACTV_DEPARTURE;

   function GET_ACTV_STEPS return Server_Types.T_Steps renames
     FPLNS_CTRL.GET_ACTV_STEPS;

   function GET_TMPY_STEPS return Server_Types.T_Steps renames
     FPLNS_CTRL.GET_TMPY_STEPS;

   function IS_SEC_FPLN_LINKED return Boolean renames
     FPLNS_CTRL.IS_SEC_FPLN_LINKED;

   function GET_SEC_ARRIVAL return Server_Types.T_Arrival renames
     FPLNS_CTRL.GET_SEC_ARRIVAL;

   function GET_SEC_DEPARTURE return Server_Types.T_Departure renames
     FPLNS_CTRL.GET_SEC_DEPARTURE;

   function GET_SEC_STEPS return Server_Types.T_Steps renames
     FPLNS_CTRL.GET_SEC_STEPS;

   function IS_SAME_SEC_DEP_ACTV_DEST_ARPT return Boolean renames
     FPLNS_CTRL.IS_SAME_SEC_DEP_ACTV_DEST_ARPT;

   function IS_ACTIVE_RTA_DEFINED return Boolean renames
     FPLNS_CTRL.IS_ACTIVE_RTA_DEFINED;

   function HAVE_SAME_FIXES
     (LEG1 : in Server_Types.T_Leg;
      LEG2 : in Server_Types.T_Leg)
      return Boolean renames FPLNS_MANAGER.HAVE_SAME_FIXES;

   procedure IS_MISSION_DEFINED
     (FPLN_TYPE              : in Server_Types.T_Fpln_Type;
      MISSION_IS_DEFINED     : out Boolean;
      Num_Leg_Before_Mission : out Server_Types.T_Leg_Index) renames
     FPLNS_CTRL.IS_MISSION_DEFINED;

   procedure IS_MISSION_ACTIVE
     (FPLN_TYPE         : in Server_Types.T_Fpln_Type;
      MISSION_IS_ACTIVE : out Boolean) renames FPLNS_CTRL.IS_MISSION_ACTIVE;

   procedure PUT_RECOV_FPLN (FPLN : in Server_Types.T_Primary_Fpln) renames
     FPLNS_CTRL.PUT_RECOV_FPLN;

   procedure GET_RECOV_FPLN (FPLN : out Server_Types.T_Primary_Fpln) renames
     FPLNS_CTRL.GET_RECOV_FPLN;

   function IS_RECOV_FPLN_DEFINED return Boolean renames
     FPLNS_CTRL.IS_RECOV_FPLN_DEFINED;

   procedure RESET_RECOVERY renames FPLNS_MANAGER.RESET_RECOVERY;


   procedure Insert_Dto_Abeam
     (FPLN_TYPE      : in Server_Types.T_Fpln_Type;
      LEG_MARKER     : in Server_Types.T_Marker;
      Ac_Roll_Max    : in Fms_Types.T_Ang_Rel_Deg_C;
      Ac_Parameters  : in Server_Types.T_Ac_Parameters;
      REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     Fplns_Manager.Dto_Abeam_Controller.Insert;

end FPLNS;
