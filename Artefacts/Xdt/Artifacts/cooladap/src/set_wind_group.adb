   --------------------
   -- Set_Wind_Group --
   --------------------
   procedure Set_Wind_Group
     (This       : in out T_Wind_Set;
      Wind_Group : in T_Wind_Group)
   is

      use Set_Of_Wind_Group;

      Wind_Set : Set_Of_Wind_Group.T_Sorted_Set (K_Nb_Of_Group);
      It       : Set_Of_Wind_Group.T_Iterator;
      It_End   : Set_Of_Wind_Group.T_Iterator;
      Index    : lang.Primitifs.T_Nat := 0;

   begin
      pragma Assert (This.Nb_Of_Group < K_Nb_Of_Group);

      -- init Wind set (no dynamic allocation is done)
      Initialize (Wind_Set);

      -- sorted set
      for I in 1 .. This.Nb_Of_Group loop
         V_Add (Wind_Set, This.Wind_Groups (I));
      end loop;
      V_Add (Wind_Set, Wind_Group);

      -- set up array
      It     := F_Begin (Wind_Set);
      It_End := F_End (Wind_Set);
      while It /= It_End loop
         Index                    := Index + 1;
         This.Wind_Groups (Index) := Ref (It).all;
         Inc (It);
      end loop;

      This.Nb_Of_Group := This.Nb_Of_Group + 1;

      pragma Assert (This.Nb_Of_Group <= K_Nb_Of_Group);
   end Set_Wind_Group;
