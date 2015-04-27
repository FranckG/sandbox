// ----------------------------------------------------------------------------------------------------
// File Name: Program.cs
// Project: OrchestraFrameworkCLI
// Copyright (c) Thales, 2010 - 2012. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace OrchestraFrameworkCLI
{
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using System.Reflection;

    internal class Program
    {
        private static void Main(string[] args)
        {
            if (args == null || !args.Any())
            {
                WaitForOrchestraFrameworkCommand();
            }
            else
            {
                List<string> arguments = new List<string>(args);
                string inputCommand = arguments[0];
                if (inputCommand.StartsWith("-"))
                {
                    inputCommand = inputCommand.Remove(0, 1);
                }
                arguments.RemoveAt(0);
                try
                {
                    Command command = (Command)
                                      Assembly.GetExecutingAssembly().CreateInstance("OrchestraFrameworkCLI." + inputCommand, true);
                    if (command == null)
                    {
                        Console.WriteLine("Command " + inputCommand + " not exists.");
                    }
                    else
                    {
                        command.Execute(arguments);
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine(ex.Message);
                }
            }
        }

        private static void WaitForOrchestraFrameworkCommand()
        {
            string inputCommand = "";

            while (!inputCommand.Equals("exit"))
            {
                Console.Write("OrchestraFramework> ");
                string inputLine = Console.ReadLine();
                if (inputLine != null && !string.IsNullOrEmpty(inputLine))
                {
                    List<string> arguments = new List<string>(inputLine.Trim().Split(' '));
                    inputCommand = arguments[0];
                    arguments.RemoveAt(0);
                    try
                    {
                        Command command = (Command)
                                          Assembly.GetExecutingAssembly().CreateInstance("OrchestraFrameworkCLI." + inputCommand, true);
                        if (command == null)
                        {
                            Console.WriteLine("Command " + inputCommand + " not exists.");
                        }
                        else
                        {
                            command.Execute(arguments);
                        }
                    }
                    catch (Exception ex)
                    {
                        Console.WriteLine(ex.Message);
                    }
                }

            }
        }
    }
}