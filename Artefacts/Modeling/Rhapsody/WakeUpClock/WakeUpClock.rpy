I-Logix-RPY-Archive version 8.6.0 C++ 4012249
{ IProject 
	- _id = GUID 91be0163-9323-4355-b7a7-dd00416793b8;
	- _myState = 8192;
	- _properties = { IPropertyContainer 
		- Subjects = { IRPYRawContainer 
			- size = 2;
			- value = 
			{ IPropertySubject 
				- _Name = "Browser";
				- Metaclasses = { IRPYRawContainer 
					- size = 1;
					- value = 
					{ IPropertyMetaclass 
						- _Name = "Settings";
						- Properties = { IRPYRawContainer 
							- size = 1;
							- value = 
							{ IProperty 
								- _Name = "ShowPredefinedPackage";
								- _Value = "0";
								- _Type = Bool;
							}
						}
					}
				}
			}
			{ IPropertySubject 
				- _Name = "ConfigurationManagement";
				- Metaclasses = { IRPYRawContainer 
					- size = 1;
					- value = 
					{ IPropertyMetaclass 
						- _Name = "General";
						- Properties = { IRPYRawContainer 
							- size = 1;
							- value = 
							{ IProperty 
								- _Name = "UseSCCtool";
								- _Value = "No";
								- _Type = Enum;
								- _ExtraTypeInfo = "Yes,No";
							}
						}
					}
				}
			}
		}
	}
	- _name = "WakeUpClock";
	- Tags = { IRPYRawContainer 
		- size = 1;
		- value = 
		{ ITag 
			- _id = GUID d04508be-2a8e-4016-a701-0f04a86085c9;
			- _name = "Type";
			- ValueSpecifications = { IRPYRawContainer 
				- size = 1;
				- value = 
				{ ILiteralSpecification 
					- _id = GUID 44f485a8-5158-4392-87f5-f014b4ad0501;
					- _value = "System";
				}
			}
			- _typeOf = { IHandle 
				- _m2Class = "IType";
				- _filename = "PredefinedTypes.sbs";
				- _subsystem = "PredefinedTypes";
				- _class = "";
				- _name = "RhpString";
				- _id = GUID ae5e3720-4e3e-40f1-9346-9a8b4e501f35;
			}
			- _isOrdered = 0;
		}
	}
	- _UserColors = { IRPYRawContainer 
		- size = 16;
		- value = 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 
	}
	- _defaultSubsystem = { ISubsystemHandle 
		- _m2Class = "ISubsystem";
		- _filename = "Default\\Default.sbs";
		- _subsystem = "";
		- _class = "";
		- _name = "Default";
		- _id = GUID 30cf5173-cd4a-4520-b2c1-0c41a08a73f3;
	}
	- _component = { IHandle 
		- _m2Class = "IComponent";
		- _filename = "DefaultComponent.cmp";
		- _subsystem = "";
		- _class = "";
		- _name = "DefaultComponent";
		- _id = GUID e3185278-dfa6-4a41-9b57-eb9a018aee49;
	}
	- Multiplicities = { IRPYRawContainer 
		- size = 4;
		- value = 
		{ IMultiplicityItem 
			- _name = "1";
			- _count = -1;
		}
		{ IMultiplicityItem 
			- _name = "*";
			- _count = -1;
		}
		{ IMultiplicityItem 
			- _name = "0,1";
			- _count = -1;
		}
		{ IMultiplicityItem 
			- _name = "1..*";
			- _count = -1;
		}
	}
	- Subsystems = { IRPYRawContainer 
		- size = 11;
		- value = 
		{ ISubsystem 
			- fileName = "Default";
			- _persistAs = "Default";
			- _id = GUID 30cf5173-cd4a-4520-b2c1-0c41a08a73f3;
		}
		{ ISubsystem 
			- fileName = "System_Architecture";
			- _persistAs = "System_Architecture";
			- _id = GUID c8578bb9-da2b-49a7-bd01-c18c5cf20ed4;
		}
		{ IProfile 
			- fileName = "CGCompatibilityPre72Java";
			- _persistAs = "CGCompatibilityPre72Java";
			- _id = GUID 2aceedc8-18f0-4c99-99fe-020389805f7b;
		}
		{ IProfile 
			- fileName = "CGCompatibilityPre73Java";
			- _persistAs = "CGCompatibilityPre73Java";
			- _id = GUID cf199a76-9da5-4e90-9344-de4f494be2a3;
		}
		{ IProfile 
			- fileName = "Pre76GESkin";
			- _persistAs = "Pre76GESkin";
			- _id = GUID 74a6daed-8619-4275-8f6b-7d1d1d0220b1;
		}
		{ IProfile 
			- fileName = "CGCompatibilityPre73Cpp";
			- _persistAs = "CGCompatibilityPre73Cpp";
			- _id = GUID cd381dde-b3d4-47ff-8af3-32c5339a2b0a;
		}
		{ IProfile 
			- fileName = "CGCompatibilityPre75Cpp";
			- _persistAs = "CGCompatibilityPre75Cpp";
			- _id = GUID ca7332da-c15b-4929-9231-18e49a7edb64;
		}
		{ IProfile 
			- fileName = "CGCompatibilityPre751Cpp";
			- _persistAs = "CGCompatibilityPre751Cpp";
			- _id = GUID cb7b1c1a-b1ef-481f-9bc8-cb6d89a3848f;
		}
		{ IProfile 
			- fileName = "CGCompatibilityPre753Cpp";
			- _persistAs = "CGCompatibilityPre753Cpp";
			- _id = GUID 6d9087a8-4853-4759-a797-277df26481c2;
		}
		{ IProfile 
			- fileName = "CGCompatibilityPre76Cpp";
			- _persistAs = "CGCompatibilityPre76Cpp";
			- _id = GUID 38e0ad24-bc5e-45a0-9df2-1d0a857d19ad;
		}
		{ IProfile 
			- fileName = "CGCompatibilityPre761Cpp";
			- _id = GUID 0838347a-c933-40e3-9d7e-812acdb081ee;
		}
	}
	- Diagrams = { IRPYRawContainer 
		- size = 1;
		- value = 
		{ IDiagram 
			- _id = GUID 0054fe17-fc88-4275-9556-9137ec64b332;
			- _myState = 8192;
			- _name = "Model1";
			- _lastModifiedTime = "1.7.2009::10:19:30";
			- _graphicChart = { CGIClassChart 
				- _id = GUID f4575d66-f98f-480c-a663-9950d7e514eb;
				- m_type = 0;
				- m_pModelObject = { IHandle 
					- _m2Class = "IDiagram";
					- _id = GUID 0054fe17-fc88-4275-9556-9137ec64b332;
				}
				- m_pParent = ;
				- m_name = { CGIText 
					- m_str = "";
					- m_style = "Arial" 10 0 0 0 1 ;
					- m_color = { IColor 
						- m_fgColor = 0;
						- m_bgColor = 0;
						- m_bgFlag = 0;
					}
					- m_position = 1 0 0  ;
					- m_nIdent = 0;
					- m_bImplicitSetRectPoints = 0;
					- m_nOrientationCtrlPt = 8;
				}
				- m_drawBehavior = 0;
				- m_bIsPreferencesInitialized = 0;
				- elementList = 1;
				{ CGIClass 
					- _id = GUID f5abee0d-3df6-476b-b390-438c87abd5ea;
					- m_type = 78;
					- m_pModelObject = { IHandle 
						- _m2Class = "IClass";
						- _filename = "Default\\Default.sbs";
						- _subsystem = "Default";
						- _class = "";
						- _name = "TopLevel";
						- _id = GUID ada1d191-33e5-4f35-bdc1-8dc427c329f5;
					}
					- m_pParent = ;
					- m_name = { CGIText 
						- m_str = "TopLevel";
						- m_style = "Arial" 10 0 0 0 1 ;
						- m_color = { IColor 
							- m_fgColor = 0;
							- m_bgColor = 0;
							- m_bgFlag = 0;
						}
						- m_position = 1 0 0  ;
						- m_nIdent = 0;
						- m_bImplicitSetRectPoints = 0;
						- m_nOrientationCtrlPt = 5;
					}
					- m_drawBehavior = 0;
					- m_bIsPreferencesInitialized = 0;
					- m_AdditionalLabel = { CGIText 
						- m_str = "";
						- m_style = "Arial" 10 0 0 0 1 ;
						- m_color = { IColor 
							- m_fgColor = 0;
							- m_bgColor = 0;
							- m_bgFlag = 0;
						}
						- m_position = 1 0 0  ;
						- m_nIdent = 0;
						- m_bImplicitSetRectPoints = 0;
						- m_nOrientationCtrlPt = 1;
					}
					- m_polygon = 0 ;
					- m_nNameFormat = 0;
					- m_nIsNameFormat = 0;
					- Compartments = { IRPYRawContainer 
						- size = 0;
					}
					- Attrs = { IRPYRawContainer 
						- size = 0;
					}
					- Operations = { IRPYRawContainer 
						- size = 0;
					}
				}
				
				- m_access = 'Z';
				- m_modified = 'N';
				- m_fileVersion = "";
				- m_nModifyDate = 0;
				- m_nCreateDate = 0;
				- m_creator = "";
				- m_bScaleWithZoom = 1;
				- m_arrowStyle = 'S';
				- m_pRoot = GUID f5abee0d-3df6-476b-b390-438c87abd5ea;
				- m_currentLeftTop = 0 0 ;
				- m_currentRightBottom = 0 0 ;
			}
			- _defaultSubsystem = { IHandle 
				- _m2Class = "ISubsystem";
				- _filename = "Default\\Default.sbs";
				- _subsystem = "";
				- _class = "";
				- _name = "Default";
				- _id = GUID 30cf5173-cd4a-4520-b2c1-0c41a08a73f3;
			}
		}
	}
	- Components = { IRPYRawContainer 
		- size = 1;
		- value = 
		{ IComponent 
			- fileName = "DefaultComponent";
			- _id = GUID e3185278-dfa6-4a41-9b57-eb9a018aee49;
		}
	}
}

