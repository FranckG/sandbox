-------------------------------------------------------------------------------
--         COPYRIGHT BY THALES AVIONICS ALL RIGHTS RESTRICTED
-------------------------------------------------------------------------------

--  visibility on required modules :
with FIX;
with LEG;

with Wind.Wind_Set;

--  separate operation :
separate (LEGS)
procedure STRING_DOWNSTREAM
  (FIRST_LIST                    : in out SERVER_TYPES.T_LEGS;
   SECOND_LIST                   : in SERVER_TYPES.T_LEGS;
   SECOND_LIST_STOP_INDEX        : in SERVER_TYPES.T_LEG_INDEX;
   Wind_Constraint_Modified      : out Boolean;
   REQUEST_STATUS                : out SERVER_TYPES.T_REQUEST_STATUS;
   B_STRING_WITH_NO_MATCHING_FIX : in Boolean := True)
is
   L_B_MATCHING_FIX     : Boolean;
   L_MATCHING_FIX       : SERVER_TYPES.T_FIX;
   L_MATCHING_LEG_INDEX : SERVER_TYPES.T_LEG_INDEX;
   L_MATCHING_LEG       : SERVER_TYPES.T_LEG;
   L_LEG_TYPE           : SERVER_TYPES.T_LEG_TYPE;
   L_2ND_LIST           : SERVER_TYPES.T_LEGS;
   L_STRINGING_RULE     : T_STRINGING_RULE;

begin

   REQUEST_STATUS := SERVER_TYPES.OK;
   Wind_Constraint_Modified := False;

   -- Copy the second list.
   L_2ND_LIST := SECOND_LIST;

   if not IS_EMPTY (FIRST_LIST) then
      if not IS_EMPTY (L_2ND_LIST) then

         -- The matching fix if it exists is the termination fix of the last
         -- leg of the first list.
         L_B_MATCHING_FIX     := False;
         L_MATCHING_LEG_INDEX := SERVER_TYPES.T_LEG_INDEX'FIRST;

         L_MATCHING_FIX := LEG.GET_TERM_FIX (FIRST_LIST.LEGS_ARRAY (GET_TAIL_INDEX (FIRST_LIST)));

         -- The matching fix is  searched into the second list if there
         -- is no discontinuity at the end of the first list.
         if not (FIX.IS_DEFAULT_FIX (L_MATCHING_FIX)
                or else LEG.IS_DISCON_AHEAD (FIRST_LIST.LEGS_ARRAY (GET_TAIL_INDEX (FIRST_LIST))))
         then
            FIND_FIRST (L_2ND_LIST, L_MATCHING_FIX, L_MATCHING_LEG_INDEX, L_B_MATCHING_FIX);

            L_B_MATCHING_FIX := L_B_MATCHING_FIX and then not (L_MATCHING_LEG_INDEX > SECOND_LIST_STOP_INDEX);
         end if;

         if L_B_MATCHING_FIX then
            -- Treat specific cases of leg stringing with a matching fix.
            L_STRINGING_RULE :=
              C_STRINGING_RULES (GET_LEG_TYPE (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST)), GET_LEG_TYPE
                                                                                           (L_2ND_LIST,
                                                                                            L_MATCHING_LEG_INDEX));
            case L_STRINGING_RULE is
               when SOLVE_AF_PI | SOLVE_HX_PI =>
                  -- Check whether the CF fix is the same as the PI fix.
                  if LEG.USE_SAME_FIX (L_2ND_LIST.LEGS_ARRAY (L_MATCHING_LEG_INDEX + 1), L_MATCHING_FIX) then
                     -- The PI is removed.
                     L_B_MATCHING_FIX := False;
                     REMOVE_HEAD (L_2ND_LIST);
                  else
                     -- The PI and CF leg are removed.
                     L_MATCHING_LEG_INDEX := L_MATCHING_LEG_INDEX + 2;
                     -- Get the characteristics of the matching leg.
                     L_MATCHING_LEG := GET (L_2ND_LIST, L_MATCHING_LEG_INDEX - 1);
                     -- Set the characteristics of the removed fix to the kept one.
                     SET_CHARACTERISTICS (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST), L_MATCHING_LEG);
                  end if;

               when SOLVE_HX_HX =>
                  -- The Hx of the second list is removed.
                  L_MATCHING_LEG_INDEX := L_MATCHING_LEG_INDEX + 1;
                  -- Get the characteristics of the matching leg.
                  L_MATCHING_LEG := GET (L_2ND_LIST, L_MATCHING_LEG_INDEX - 1);
                  -- Set the characteristics of the removed fix to the kept one.
                  SET_CHARACTERISTICS (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST), L_MATCHING_LEG);

               when others =>
                  case GET_LEG_TYPE (L_2ND_LIST, L_MATCHING_LEG_INDEX) is
                     when LIF | AF | CF | DF | RF | TF =>

                        if LEGS.IS_DISCON_AHEAD (L_2ND_LIST, L_MATCHING_LEG_INDEX) then
                           SET_DISCON_AHEAD (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST), True);
                        end if;
                        L_MATCHING_LEG_INDEX := L_MATCHING_LEG_INDEX + 1;
                        -- Get the characteristics of the matching leg.
                        L_MATCHING_LEG := GET (L_2ND_LIST, L_MATCHING_LEG_INDEX - 1);

                        -- If last leg type of first list is not AF, CF, DF,IF, RF and TF
                        -- and it exists a RTA on matching leg, don't keep this constraint
                        L_LEG_TYPE := GET_LEG_TYPE (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST));
                        if L_LEG_TYPE /= AF and then L_LEG_TYPE /= CF and then L_LEG_TYPE /= DF and then
                          L_LEG_TYPE /= LIF and then L_LEG_TYPE /= RF and then L_LEG_TYPE /= TF then
                           LEG.SET_RTA_CSTR (L_MATCHING_LEG, False);
                        end if;

                        -- Set the characteristics of the removed fix to the kept one.
                        SET_CHARACTERISTICS (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST), L_MATCHING_LEG);

                        -- If a Hx or PI-CF follows the matching fix.
                        case GET_LEG_TYPE (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST)) is
                           when HA | HF | HM =>
                              L_STRINGING_RULE :=
                                C_STRINGING_RULES (GET_LEG_TYPE (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST)), GET_LEG_TYPE
                                                                                                             (L_2ND_LIST,
                                                                                                              L_MATCHING_LEG_INDEX)
);
                              case L_STRINGING_RULE is
                                 when SOLVE_HX_PI =>
                                    -- Check whether the CF fix is the same as the PI fix.
                                    if LEG.USE_SAME_FIX (L_2ND_LIST.LEGS_ARRAY (L_MATCHING_LEG_INDEX + 1), L_MATCHING_FIX) then
                                       -- The PI is removed.
                                       L_B_MATCHING_FIX := False;
                                       REMOVE_HEAD (L_2ND_LIST);
                                    else
                                       -- The PI and CF leg are removed.
                                       L_MATCHING_LEG_INDEX := L_MATCHING_LEG_INDEX + 2;
                                       -- Get the characteristics of the matching leg.
                                       L_MATCHING_LEG := GET (L_2ND_LIST, L_MATCHING_LEG_INDEX - 1);
                                       -- Set the characteristics of the removed fix to the kept one.
                                       SET_CHARACTERISTICS (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST), L_MATCHING_LEG);
                                    end if;
                                 when SOLVE_HX_HX =>
                                    -- The Hx of the second list is removed.
                                    L_MATCHING_LEG_INDEX := L_MATCHING_LEG_INDEX + 1;
                                    -- Get the characteristics of the matching leg.
                                    L_MATCHING_LEG := GET (L_2ND_LIST, L_MATCHING_LEG_INDEX - 1);
                                    -- Set the characteristics of the removed fix to the kept one.
                                    SET_CHARACTERISTICS (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST), L_MATCHING_LEG);
                                 when others =>
                                    null;
                              end case;
                           when others =>
                              null;
                        end case;
                     when others =>
                        null;
                  end case;

            end case;

            -- Check AF-AF sequence.
            L_B_MATCHING_FIX := L_B_MATCHING_FIX
              and then LEG.ARE_MATCHING_AFS
                (GET (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST)),
                 GET (L_2ND_LIST, L_MATCHING_LEG_INDEX));

            if L_B_Matching_Fix then

               if Wind.Wind_Set.Is_Valid(L_2nd_List.Legs_Array(L_Matching_Leg_Index).Wind_Set) and then
                 not Wind.Wind_Set.Is_Crew_Wind_Set(L_2nd_List.Legs_Array(L_Matching_Leg_Index).Wind_Set)
               then

                  Legs.Delay_Wind_Constraint
                    (Legs                     => L_2nd_List,
                     Index1                   => L_Matching_Leg_Index,
                     Wind_Constraint_Modified => Wind_Constraint_Modified);

               end if;
            end if;

            L_2ND_LIST := COPY (L_2ND_LIST, L_MATCHING_LEG_INDEX, GET_TAIL_INDEX (L_2ND_LIST));

            -- The two lists are directly strung at the matching fix.
            if L_B_MATCHING_FIX then

               -- Append the two leg lists.
               APPEND (FIRST_LIST, L_2ND_LIST, REQUEST_STATUS);
            end if;

         end if;

         -- No matching fix has been found.
         if not L_B_MATCHING_FIX and then REQUEST_STATUS = SERVER_TYPES.OK then
            -- If the stringing with no matching fix is authorized
            if B_STRING_WITH_NO_MATCHING_FIX then
               STRING_WITH_NO_MATCHING_FIX (FIRST_LIST, L_2ND_LIST, REQUEST_STATUS);
            else
               SET_DISCON_AHEAD (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST), True);
            end if;
         end if;

      else
         SET_DISCON_AHEAD (FIRST_LIST, GET_TAIL_INDEX (FIRST_LIST), True);
      end if;
   else
      FIRST_LIST := L_2ND_LIST;
   end if;
end STRING_DOWNSTREAM;
