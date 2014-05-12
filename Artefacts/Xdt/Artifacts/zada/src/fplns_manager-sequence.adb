--#----------------------------------------------------------------------------
---
--#
--#         COPYRIGHT BY THALES AVIONICS ALL RIGHTS RESTRICTED
--#
--#----------------------------------------------------------------------------
---
--#
--# MODULE NAME  :  fplns_manager-sequence.adb
--#
--# AUTHOR :
--#
--# CREATION DATE : 23-SEP-97
--#
--#----------------------------------------------------------------------------
---
--#
--# MODIFICATION :
--#
--#
--#----------------------------------------------------------------------------
---

--  visibility on required modules :
with FPLNS_CTRL;
with LEGS;
with LEG;
with FPLN;
with SERVER;
--  separate operation :
separate (FPLNS_MANAGER)
procedure SEQUENCE
  (TO_LEG_MARKER : in SERVER_TYPES.T_MARKER;
   HOUR          : in FMS_TYPES.T_DAY_DURATION_S_C;
   DATE          : in FMS_TYPES.T_DATE_C;
   LAST_LEG_SQC  : in Boolean)
is

   use SERVER_TYPES;
   use FMS_TYPES;

   L_ACTV_FPLN          : SERVER_TYPES.T_PRIMARY_FPLN;
   L_ACTV_FPLN_CHG      : SERVER_TYPES.T_FPLN_MODIF_ORIGIN;
   L_ACTV_LEG_INDEX     : SERVER_TYPES.T_LEG_INDEX;

   L_TMPY_FPLN          : SERVER_TYPES.T_PRIMARY_FPLN;
   L_TMPY_FPLN_CHG      : SERVER_TYPES.T_FPLN_MODIF_ORIGIN;
   L_TMPY_LEG_INDEX     : SERVER_TYPES.T_LEG_INDEX;
   L_TMPY_END_LEG_INDEX : SERVER_TYPES.T_LEG_INDEX;

   L_SEC_FPLN           : SERVER_TYPES.T_PRIMARY_FPLN;
   L_SEC_FPLN_CHG       : SERVER_TYPES.T_FPLN_MODIF_ORIGIN;
   L_SEC_LEG_INDEX      : SERVER_TYPES.T_LEG_INDEX;
   L_SEC_END_LEG_INDEX  : SERVER_TYPES.T_LEG_INDEX;

   L_RECOV_FPLN          : SERVER_TYPES.T_PRIMARY_FPLN;
   L_RECOV_FPLN_CHG      : SERVER_TYPES.T_FPLN_MODIF_ORIGIN;
   L_RECOV_LEG_INDEX     : SERVER_TYPES.T_LEG_INDEX;
   L_RECOV_END_LEG_INDEX : SERVER_TYPES.T_LEG_INDEX;

   L_B_FOUND            : Boolean;
   L_UNUSED_FPLN_CHG    : SERVER_TYPES.T_FPLN_MODIF_ORIGIN;
   L_AC_PARAMETERS      : SERVER_TYPES.T_AC_PARAMETERS;

begin

   L_RECOV_LEG_INDEX := 1;

   L_TMPY_LEG_INDEX := 1;
   L_SEC_LEG_INDEX  := 1;
   L_B_FOUND        := True;

   FPLNS_CTRL.GET_ACTV_FPLN (L_ACTV_FPLN);

   if not LAST_LEG_SQC then

      -- Retreives the index of the leg to sequence
      LEGS.FIND
        (LEGS    => L_ACTV_FPLN.LEGS,
         MARKER  => TO_LEG_MARKER,
         INDEX   => L_ACTV_LEG_INDEX,
         B_FOUND => L_B_FOUND);

   else
      L_ACTV_LEG_INDEX := LEGS.FIND_DEST (L_ACTV_FPLN.LEGS) + 1;

   end if;

   if L_B_FOUND and then L_ACTV_LEG_INDEX > 1 then

      if FPLNS_CTRL.IS_TMPY_FPLN_DEFINED then

         FPLNS_CTRL.GET_TMPY_FPLN (L_TMPY_FPLN);

         L_TMPY_END_LEG_INDEX := LEGS.FIND_DEST (L_TMPY_FPLN.LEGS);

         -- Finds the index of the first different legs between
         -- the two flight plans (until the index of the leg to sequence)
         while L_TMPY_LEG_INDEX < L_ACTV_LEG_INDEX
           and then L_TMPY_LEG_INDEX <= L_TMPY_END_LEG_INDEX
           and then LEG.ARE_SAME_LEGS
                       (LEGS.GET (L_ACTV_FPLN.LEGS, L_TMPY_LEG_INDEX),
                        LEGS.GET (L_TMPY_FPLN.LEGS, L_TMPY_LEG_INDEX))
         loop

            L_TMPY_LEG_INDEX := L_TMPY_LEG_INDEX + 1;

         end loop;

      end if;

      -- if at least the first different leg is after the active leg
      -- of the temporary flight plan
      if L_TMPY_LEG_INDEX > 1 then

         -- Sequences the temporary flight plan
         FPLN.SEQUENCE
           (FPLN         => L_TMPY_FPLN,
            TO_LEG_INDEX => L_TMPY_LEG_INDEX,
            HOUR         => HOUR,
            DATE         => DATE,
            FPLN_CHG     => L_TMPY_FPLN_CHG);

         if FPLN.IS_OFFSET_ACTIVE (L_TMPY_FPLN)
           and then (not FPLN.IS_OFFSET_ACTIVE (L_ACTV_FPLN)
                    or else (FPLN.IS_OFFSET_ACTIVE (L_ACTV_FPLN)
                            and then (FPLN.GET_OFFSET_DIST (L_TMPY_FPLN) /=
                                      FPLN.GET_OFFSET_DIST (L_ACTV_FPLN))))
         then

            -- check the offset is again applicable.
            if FPLN.IS_OFFSETABLE_LEG (L_TMPY_FPLN, 1)
              and then FPLN.IS_OFFSETABLE_LEG (L_TMPY_FPLN, 2)
              and then FPLN.IS_OFFSETABLE_TRANS (L_TMPY_FPLN, 1)
            then

               SERVER.GET_AC_PARAMETERS (L_AC_PARAMETERS);

               -- If the offset have to be delayed
               if L_AC_PARAMETERS.AC_POS.STATUS = FMS_TYPES.DEFINED
                 and then SERVER.IS_TMPY_OFFSET_DELAYED
                             (False,
                              L_TMPY_FPLN.LEGS,
                              FPLN.GET_OFFSET_DIST (L_TMPY_FPLN),
                              L_AC_PARAMETERS.AC_POS.POSITION)
               then

                  -- If the offset can be delayed
                  if FPLN.IS_OFFSETABLE_LEG (L_TMPY_FPLN, 3)
                    and then FPLN.IS_OFFSETABLE_TRANS (L_TMPY_FPLN, 2)
                    and then not SERVER.IS_TMPY_OFFSET_DELAYED
                                   (True,
                                    L_TMPY_FPLN.LEGS,
                                    FPLN.GET_OFFSET_DIST (L_TMPY_FPLN),
                                    L_AC_PARAMETERS.AC_POS.POSITION)
                  then

                     FPLN.DELAY_OFFSET (L_TMPY_FPLN);

                  else
                     FPLN.CLEAR_OFFSET
                       (FPLN     => L_TMPY_FPLN,
                        FPLN_CHG => L_UNUSED_FPLN_CHG);

                  end if;

               end if;

            else
               FPLN.CLEAR_OFFSET
                 (FPLN     => L_TMPY_FPLN,
                  FPLN_CHG => L_UNUSED_FPLN_CHG);

            end if;

         end if;

         if L_TMPY_FPLN_CHG.LATERAL_MODIF /= SERVER_TYPES.UNDEF then

            FPLNS_CTRL.PUT_TMPY_FPLN (L_TMPY_FPLN, L_TMPY_FPLN_CHG);

         end if;

      end if;

      -- treatment of secondary flight plan if it is linked with active one
      if FPLNS_CTRL.IS_SEC_FPLN_LINKED then

         L_SEC_FPLN_CHG :=
           (LATERAL_MODIF  => UNDEF,
            VERTICAL_MODIF => UNDEF,
            REFRESH_MODIF  => UNDEF,
            MODIFIED_ELT   => 0);

         FPLNS_CTRL.GET_SEC_FPLN (L_SEC_FPLN);
         L_SEC_END_LEG_INDEX := LEGS.FIND_DEST (L_SEC_FPLN.LEGS);

         -- Finds the index of the first different legs between the active
         --  and secondary flight plans (until the index of the leg to
         --sequence)
         while L_SEC_LEG_INDEX < L_ACTV_LEG_INDEX
           and then L_SEC_LEG_INDEX <= L_SEC_END_LEG_INDEX
           and then LEG.ARE_SAME_LEGS_BIS
                       (LEGS.GET (L_ACTV_FPLN.LEGS, L_SEC_LEG_INDEX),
                        LEGS.GET (L_SEC_FPLN.LEGS, L_SEC_LEG_INDEX))
         loop
            L_SEC_LEG_INDEX := L_SEC_LEG_INDEX + 1;
         end loop;

         if L_SEC_LEG_INDEX > 1 then
            -- Sequences the secondary flight plan if common leg index is
            --greater than FROM leg index
            FPLN.SEQUENCE
              (FPLN         => L_SEC_FPLN,
               HOUR         => HOUR,
               DATE         => DATE,
               TO_LEG_INDEX => L_SEC_LEG_INDEX,
               FPLN_CHG     => L_SEC_FPLN_CHG);
         end if;

         -- Update secondary flight plan if it has been modified
         if L_SEC_FPLN_CHG.LATERAL_MODIF /= SERVER_TYPES.UNDEF then
            FPLNS_CTRL.PUT_SEC_FPLN
              (L_SEC_FPLN,
               L_SEC_FPLN_CHG,
               SERVER_TYPES.LINKED);
         end if;
      end if;

      --===============================================================
      if FPLNS_CTRL.IS_RECOV_FPLN_DEFINED then

         FPLNS_CTRL.GET_RECOV_FPLN (L_RECOV_FPLN);

         L_RECOV_END_LEG_INDEX := LEGS.FIND_DEST (L_RECOV_FPLN.LEGS);

         -- Finds the index of the first different legs between
         -- the two flight plans (until the index of the leg to sequence)
         while L_RECOV_LEG_INDEX < L_ACTV_LEG_INDEX
           and then L_RECOV_LEG_INDEX <= L_RECOV_END_LEG_INDEX
           and then LEG.ARE_SAME_LEGS
                       (LEGS.GET (L_ACTV_FPLN.LEGS, L_RECOV_LEG_INDEX),
                        LEGS.GET (L_RECOV_FPLN.LEGS, L_RECOV_LEG_INDEX))
         loop

            L_RECOV_LEG_INDEX := L_RECOV_LEG_INDEX + 1;

         end loop;

      end if;

      -- if at least the first different leg is after the active leg
      -- of the temporary flight plan
      if L_RECOV_LEG_INDEX > 1 then

         -- Sequences the temporary flight plan
         FPLN.SEQUENCE
           (FPLN         => L_RECOV_FPLN,
            TO_LEG_INDEX => L_RECOV_LEG_INDEX,
            HOUR         => HOUR,
            DATE         => DATE,
            FPLN_CHG     => L_RECOV_FPLN_CHG);

         if FPLN.IS_OFFSET_ACTIVE (L_RECOV_FPLN)
           and then (not FPLN.IS_OFFSET_ACTIVE (L_ACTV_FPLN)
                    or else (FPLN.IS_OFFSET_ACTIVE (L_ACTV_FPLN)
                            and then (FPLN.GET_OFFSET_DIST (L_RECOV_FPLN) /=
                                      FPLN.GET_OFFSET_DIST (L_ACTV_FPLN))))
         then

            -- check the offset is again applicable.
            if FPLN.IS_OFFSETABLE_LEG (L_RECOV_FPLN, 1)
              and then FPLN.IS_OFFSETABLE_LEG (L_RECOV_FPLN, 2)
              and then FPLN.IS_OFFSETABLE_TRANS (L_RECOV_FPLN, 1)
            then

               SERVER.GET_AC_PARAMETERS (L_AC_PARAMETERS);

               -- If the offset have to be delayed
               if L_AC_PARAMETERS.AC_POS.STATUS = FMS_TYPES.DEFINED
                 and then SERVER.IS_RECOV_OFFSET_DELAYED
                             (False,
                              L_RECOV_FPLN.LEGS,
                              FPLN.GET_OFFSET_DIST (L_RECOV_FPLN),
                              L_AC_PARAMETERS.AC_POS.POSITION)
               then

                  -- If the offset can be delayed
                  if FPLN.IS_OFFSETABLE_LEG (L_TMPY_FPLN, 3)
                    and then FPLN.IS_OFFSETABLE_TRANS (L_RECOV_FPLN, 2)
                    and then not SERVER.IS_RECOV_OFFSET_DELAYED
                                   (True,
                                    L_RECOV_FPLN.LEGS,
                                    FPLN.GET_OFFSET_DIST (L_RECOV_FPLN),
                                    L_AC_PARAMETERS.AC_POS.POSITION)
                  then

                     FPLN.DELAY_OFFSET (L_RECOV_FPLN);

                  else
                     FPLN.CLEAR_OFFSET
                       (FPLN     => L_RECOV_FPLN,
                        FPLN_CHG => L_UNUSED_FPLN_CHG);

                  end if;

               end if;

            else
               FPLN.CLEAR_OFFSET
                 (FPLN     => L_RECOV_FPLN,
                  FPLN_CHG => L_UNUSED_FPLN_CHG);

            end if;

         end if;

         if L_RECOV_FPLN_CHG.LATERAL_MODIF /= SERVER_TYPES.UNDEF then

            --FPLNS_CTRL.PUT_RECOV_FPLN (L_RECOV_FPLN, L_RECOV_FPLN_CHG);
            FPLNS_CTRL.PUT_RECOV_FPLN (L_RECOV_FPLN);

         end if;

      end if;
      --===============================================================

      -- Sequences the active flight plan
      FPLN.SEQUENCE
        (FPLN         => L_ACTV_FPLN,
         TO_LEG_INDEX => L_ACTV_LEG_INDEX,
         HOUR         => HOUR,
         DATE         => DATE,
         FPLN_CHG     => L_ACTV_FPLN_CHG);

      if L_ACTV_FPLN_CHG.LATERAL_MODIF /= SERVER_TYPES.UNDEF then

         FPLNS_CTRL.PUT_ACTV_FPLN (L_ACTV_FPLN, L_ACTV_FPLN_CHG);

      end if;

   end if;
end SEQUENCE;
