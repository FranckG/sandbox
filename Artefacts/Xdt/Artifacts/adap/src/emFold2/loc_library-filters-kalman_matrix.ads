-----------------------------------------------------------
--          copyright thales avionics all rights reserved
--          technical business unit navigation
-----------------------------------------------------------
-- package Ada : Kalman matrix

------------------------------------------------------------



function SEC_HAS_SAME_DEST_ACTV2
  (FPLN1 : in Server_Types.T_Primary_Fpln;
   FPLN2 : in Server_Types.T_Primary_Fpln)
   return  Boolean renames FPLNS_MANAGER.SEC_HAS_SAME_DEST_ACTV;
-- procedure Ada : gfhjfdd matrix

Procedure LOST (ABET : in ACCOUNT; FORTUNE : in out ACCOUNT);
-- function Ada : gfhjfdd matrix
function SEC_HAS_SAME_DEST_ACTV
  (FPLN1 : in Server_Types.T_Primary_Fpln;
   FPLN2 : in Server_Types.T_Primary_Fpln)
      return  Boolean renames FPLNS_MANAGER.SEC_HAS_SAME_DEST_ACTV;

procedure INITIALIZE_SEC_BY_COPY
     (REQUEST_STATUS : out Server_Types.T_Request_Status) renames
     FPLNS_MANAGER.INITIALIZE_SEC_BY_COPY;

with Lang.primitifs;
