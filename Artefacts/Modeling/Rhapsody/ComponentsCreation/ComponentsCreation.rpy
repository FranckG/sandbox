I-Logix-RPY-Archive version 8.8.0 C++ 6107223
{ IProject 
	- _id = GUID 102da27a-25f0-472f-ad81-7b8d66f1a23a;
	- _myState = 8192;
	- _name = "ComponentsCreation";
	- codeUpdateCGTime = 10.18.2013::13:50:21;
	- Dependencies = { IRPYRawContainer 
		- size = 1;
		- value = 
		{ IDependency 
			- _id = GUID 3fdfd62e-1a67-4950-9dc3-6d6c17bd4263;
			- _myState = 2048;
			- _name = "CodeCentricCpp";
			- codeUpdateCGTime = 10.18.2013::13:50:21;
			- Stereotypes = { IRPYRawContainer 
				- size = 1;
				- value = 
				{ IHandle 
					- _m2Class = "IStereotype";
					- _filename = "PredefinedTypes.sbs";
					- _subsystem = "PredefinedTypes";
					- _class = "";
					- _name = "AppliedProfile";
					- _id = GUID d2eca2c1-e5a5-4296-9453-29283c4ed8bc;
				}
			}
			- _modifiedTimeWeak = 1.1.1970::1:0:0;
			- _dependsOn = { INObjectHandle 
				- _m2Class = "IProfile";
				- _filename = "$OMROOT\\Settings\\CodeCentric\\\\CodeCentric752Cpp.sbs";
				- _subsystem = "";
				- _class = "";
				- _name = "CodeCentricCpp";
				- _id = GUID 5bc6cd73-d83c-4d02-b530-2cc953376a5f;
			}
		}
	}
	- _modifiedTimeWeak = 1.1.1970::1:0:0;
	- _lastID = 1;
	- _UserColors = { IRPYRawContainer 
		- size = 16;
		- value = 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 16777215; 
	}
	- _defaultSubsystem = { ISubsystemHandle 
		- _m2Class = "ISubsystem";
		- _filename = "Default.sbs";
		- _subsystem = "";
		- _class = "";
		- _name = "Default";
		- _id = GUID 18e11ee6-67da-4afe-8619-f4b3325cd97b;
	}
	- _component = { IHandle 
		- _m2Class = "IComponent";
		- _filename = "DefaultComponent.cmp";
		- _subsystem = "";
		- _class = "";
		- _name = "DefaultComponent";
		- _id = GUID 699aa6ca-f798-4841-9cd0-bf5f52901362;
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
		- size = 7;
		- value = 
		{ ISubsystem 
			- fileName = "Default";
			- _id = GUID 18e11ee6-67da-4afe-8619-f4b3325cd97b;
		}
		{ IProfile 
			- fileName = "SoftwareArchitect752Cpp";
			- _persistAs = "D:\\Orchestra\\IBM\\Rational\\Rhapsody\\7.6\\Share\\Settings\\SoftwareArchitect";
			- _id = GUID c4330c76-1cd6-4dbd-bdff-600355b9f34d;
			- _name = "SoftwareArchitectCpp";
			- _isReference = 1;
		}
		{ IProfile 
			- fileName = "CodeCentric752Cpp";
			- _persistAs = "$OMROOT\\Settings\\CodeCentric\\";
			- _id = GUID 5bc6cd73-d83c-4d02-b530-2cc953376a5f;
			- _name = "CodeCentricCpp";
			- _isReference = 1;
		}
		{ ISubsystem 
			- fileName = "package_0";
			- _id = GUID c12c7641-bdfb-4ca1-8a34-08428828610f;
		}
		{ IProfile 
			- fileName = "CGCompatibilityPre761Cpp";
			- _id = GUID 0838347a-c933-40e3-9d7e-812acdb081ee;
		}
		{ IProfile 
			- fileName = "CGCompatibilityPre80Cpp";
			- _id = GUID cdc61ea8-5d48-43f9-9f97-af672c36c918;
		}
		{ IProfile 
			- fileName = "CGCompatibilityPre803Cpp";
			- _id = GUID 2c6673a1-7843-43a4-92d5-93795ef7e229;
		}
	}
	- Diagrams = { IRPYRawContainer 
		- size = 1;
		- value = 
		{ IDiagram 
			- _id = GUID 8d3d8509-a270-47e1-8494-08094f621851;
			- _myState = 8192;
			- _properties = { IPropertyContainer 
				- Subjects = { IRPYRawContainer 
					- size = 1;
					- value = 
					{ IPropertySubject 
						- _Name = "Format";
						- Metaclasses = { IRPYRawContainer 
							- size = 1;
							- value = 
							{ IPropertyMetaclass 
								- _Name = "Package";
								- Properties = { IRPYRawContainer 
									- size = 7;
									- value = 
									{ IProperty 
										- _Name = "DefaultSize";
										- _Value = "0,0,216,151";
										- _Type = String;
									}
									{ IProperty 
										- _Name = "Fill.FillColor";
										- _Value = "255,255,255";
										- _Type = Color;
									}
									{ IProperty 
										- _Name = "Font.Font";
										- _Value = "Tahoma";
										- _Type = String;
									}
									{ IProperty 
										- _Name = "Font.Size";
										- _Value = "8";
										- _Type = Int;
									}
									{ IProperty 
										- _Name = "Font.Weight@Child.NameCompartment@Name";
										- _Value = "700";
										- _Type = Int;
									}
									{ IProperty 
										- _Name = "Line.LineColor";
										- _Value = "109,163,217";
										- _Type = Color;
									}
									{ IProperty 
										- _Name = "Line.LineWidth";
										- _Value = "1";
										- _Type = Int;
									}
								}
							}
						}
					}
				}
			}
			- _name = "Model1";
			- _modifiedTimeWeak = 1.1.1970::1:0:0;
			- _lastModifiedTime = "10.18.2013::13:50:52";
			- _graphicChart = { CGIClassChart 
				- _id = GUID a65b201b-20b3-4367-aced-e09b76bae93b;
				- m_type = 0;
				- m_pModelObject = { IHandle 
					- _m2Class = "IDiagram";
					- _id = GUID 8d3d8509-a270-47e1-8494-08094f621851;
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
					- _id = GUID 9c3c26be-e697-4db7-85be-314c083f9cdc;
					- m_type = 78;
					- m_pModelObject = { IHandle 
						- _m2Class = "IClass";
						- _filename = "Default.sbs";
						- _subsystem = "Default";
						- _class = "";
						- _name = "TopLevel";
						- _id = GUID 66f94e5a-6a3e-4ed8-99cd-97be403278d6;
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
						- size = 2;
						- value = 
						{ CGICompartment 
							- _id = GUID 49be13ef-6298-400c-94b7-5d8352cc11d2;
							- m_name = "Attribute";
							- m_displayOption = Explicit;
							- m_bShowInherited = 0;
							- m_bOrdered = 0;
							- Items = { IRPYRawContainer 
								- size = 0;
							}
						}
						{ CGICompartment 
							- _id = GUID 765fa27f-02d5-44fd-ad94-4eea3e86d54e;
							- m_name = "Operation";
							- m_displayOption = Explicit;
							- m_bShowInherited = 0;
							- m_bOrdered = 0;
							- Items = { IRPYRawContainer 
								- size = 0;
							}
						}
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
				- m_pRoot = GUID 9c3c26be-e697-4db7-85be-314c083f9cdc;
				- m_currentLeftTop = 0 0 ;
				- m_currentRightBottom = 0 0 ;
			}
			- _defaultSubsystem = { IHandle 
				- _m2Class = "ISubsystem";
				- _filename = "Default.sbs";
				- _subsystem = "";
				- _class = "";
				- _name = "Default";
				- _id = GUID 18e11ee6-67da-4afe-8619-f4b3325cd97b;
			}
		}
	}
	- Components = { IRPYRawContainer 
		- size = 1;
		- value = 
		{ IComponent 
			- fileName = "DefaultComponent";
			- _id = GUID 699aa6ca-f798-4841-9cd0-bf5f52901362;
		}
	}
}

