pragma Ada_95;
with System;
package ada_main is
   pragma Warnings (Off);

   gnat_argc : Integer;
   gnat_argv : System.Address;
   gnat_envp : System.Address;

   pragma Import (C, gnat_argc);
   pragma Import (C, gnat_argv);
   pragma Import (C, gnat_envp);

   gnat_exit_status : Integer;
   pragma Import (C, gnat_exit_status);

   GNAT_Version : constant String :=
                    "GNAT Version: Pro 6.3.2 (20100607-43)" & ASCII.NUL;
   pragma Export (C, GNAT_Version, "__gnat_version");

   Ada_Main_Program_Name : constant String := "_ada_zada" & ASCII.NUL;
   pragma Export (C, Ada_Main_Program_Name, "__gnat_ada_main_program_name");

   procedure adafinal;
   pragma Export (C, adafinal, "adafinal");

   procedure adainit;
   pragma Export (C, adainit, "adainit");

   procedure Break_Start;
   pragma Import (C, Break_Start, "__gnat_break_start");

   function main
     (argc : Integer;
      argv : System.Address;
      envp : System.Address)
      return Integer;
   pragma Export (C, main, "main");

   type Version_32 is mod 2 ** 32;
   u00001 : constant Version_32 := 16#2eb574b0#;
   pragma Export (C, u00001, "zadaB");
   u00002 : constant Version_32 := 16#6385d640#;
   pragma Export (C, u00002, "system__standard_libraryB");
   u00003 : constant Version_32 := 16#c332087d#;
   pragma Export (C, u00003, "system__standard_libraryS");
   u00004 : constant Version_32 := 16#0e6ea6c5#;
   pragma Export (C, u00004, "systemS");
   u00005 : constant Version_32 := 16#3ab61ed3#;
   pragma Export (C, u00005, "system__memoryB");
   u00006 : constant Version_32 := 16#c8eda076#;
   pragma Export (C, u00006, "system__memoryS");
   u00007 : constant Version_32 := 16#9c7dd3ea#;
   pragma Export (C, u00007, "adaS");
   u00008 : constant Version_32 := 16#370c0967#;
   pragma Export (C, u00008, "ada__exceptionsB");
   u00009 : constant Version_32 := 16#a187a4c1#;
   pragma Export (C, u00009, "ada__exceptionsS");
   u00010 : constant Version_32 := 16#ba011fb9#;
   pragma Export (C, u00010, "ada__exceptions__last_chance_handlerB");
   u00011 : constant Version_32 := 16#62eb6abe#;
   pragma Export (C, u00011, "ada__exceptions__last_chance_handlerS");
   u00012 : constant Version_32 := 16#fc53e595#;
   pragma Export (C, u00012, "system__soft_linksB");
   u00013 : constant Version_32 := 16#13ec91a3#;
   pragma Export (C, u00013, "system__soft_linksS");
   u00014 : constant Version_32 := 16#155c2ca5#;
   pragma Export (C, u00014, "system__parametersB");
   u00015 : constant Version_32 := 16#62b04be0#;
   pragma Export (C, u00015, "system__parametersS");
   u00016 : constant Version_32 := 16#873439f6#;
   pragma Export (C, u00016, "system__secondary_stackB");
   u00017 : constant Version_32 := 16#78788d18#;
   pragma Export (C, u00017, "system__secondary_stackS");
   u00018 : constant Version_32 := 16#b6947b9c#;
   pragma Export (C, u00018, "system__storage_elementsB");
   u00019 : constant Version_32 := 16#aabbdae3#;
   pragma Export (C, u00019, "system__storage_elementsS");
   u00020 : constant Version_32 := 16#892f4d5b#;
   pragma Export (C, u00020, "system__stack_checkingB");
   u00021 : constant Version_32 := 16#e2a4447e#;
   pragma Export (C, u00021, "system__stack_checkingS");
   u00022 : constant Version_32 := 16#2f60aa04#;
   pragma Export (C, u00022, "system__exception_tableB");
   u00023 : constant Version_32 := 16#d9ad4092#;
   pragma Export (C, u00023, "system__exception_tableS");
   u00024 : constant Version_32 := 16#e43c4f3d#;
   pragma Export (C, u00024, "system__htableB");
   u00025 : constant Version_32 := 16#56db7cfd#;
   pragma Export (C, u00025, "system__htableS");
   u00026 : constant Version_32 := 16#cc0e9903#;
   pragma Export (C, u00026, "system__string_hashB");
   u00027 : constant Version_32 := 16#668b0bd3#;
   pragma Export (C, u00027, "system__string_hashS");
   u00028 : constant Version_32 := 16#88c8686c#;
   pragma Export (C, u00028, "system__exceptionsB");
   u00029 : constant Version_32 := 16#4a31e901#;
   pragma Export (C, u00029, "system__exceptionsS");
   u00030 : constant Version_32 := 16#6997f8be#;
   pragma Export (C, u00030, "system__tracebackB");
   u00031 : constant Version_32 := 16#8d294e8f#;
   pragma Export (C, u00031, "system__tracebackS");
   u00032 : constant Version_32 := 16#ce975c20#;
   pragma Export (C, u00032, "system__unsigned_typesS");
   u00033 : constant Version_32 := 16#08a5f9f2#;
   pragma Export (C, u00033, "system__wch_conB");
   u00034 : constant Version_32 := 16#3eaa2b04#;
   pragma Export (C, u00034, "system__wch_conS");
   u00035 : constant Version_32 := 16#776b72d1#;
   pragma Export (C, u00035, "system__wch_stwB");
   u00036 : constant Version_32 := 16#badd64f5#;
   pragma Export (C, u00036, "system__wch_stwS");
   u00037 : constant Version_32 := 16#906233be#;
   pragma Export (C, u00037, "system__wch_cnvB");
   u00038 : constant Version_32 := 16#954a6cef#;
   pragma Export (C, u00038, "system__wch_cnvS");
   u00039 : constant Version_32 := 16#a69cad5c#;
   pragma Export (C, u00039, "interfacesS");
   u00040 : constant Version_32 := 16#093802d2#;
   pragma Export (C, u00040, "system__wch_jisB");
   u00041 : constant Version_32 := 16#1dd92a55#;
   pragma Export (C, u00041, "system__wch_jisS");
   u00042 : constant Version_32 := 16#fe5e1c6e#;
   pragma Export (C, u00042, "system__traceback_entriesB");
   u00043 : constant Version_32 := 16#a80054a5#;
   pragma Export (C, u00043, "system__traceback_entriesS");

   --  BEGIN ELABORATION ORDER
   --  ada%s
   --  interfaces%s
   --  system%s
   --  system.htable%s
   --  system.parameters%s
   --  system.parameters%b
   --  system.standard_library%s
   --  system.exceptions%s
   --  system.exceptions%b
   --  system.storage_elements%s
   --  system.storage_elements%b
   --  system.secondary_stack%s
   --  system.stack_checking%s
   --  system.stack_checking%b
   --  system.string_hash%s
   --  system.string_hash%b
   --  system.htable%b
   --  system.traceback%s
   --  system.traceback%b
   --  system.traceback_entries%s
   --  system.traceback_entries%b
   --  ada.exceptions%s
   --  ada.exceptions.last_chance_handler%s
   --  system.soft_links%s
   --  system.soft_links%b
   --  ada.exceptions.last_chance_handler%b
   --  system.secondary_stack%b
   --  system.exception_table%s
   --  system.exception_table%b
   --  system.memory%s
   --  system.memory%b
   --  system.standard_library%b
   --  system.unsigned_types%s
   --  system.wch_con%s
   --  system.wch_con%b
   --  system.wch_cnv%s
   --  system.wch_jis%s
   --  system.wch_jis%b
   --  system.wch_cnv%b
   --  system.wch_stw%s
   --  system.wch_stw%b
   --  ada.exceptions%b
   --  zada%b
   --  END ELABORATION ORDER

end ada_main;
