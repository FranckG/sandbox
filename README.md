# Connector Doors

## Description

ConnectorDoors is the component responsible for integrating Doors within Orchestra.  
Not only does it provide a 'classical' integration by exposing artefacts (views, modules, requirements, objects, ...) and enabling services on top of them (navigation, exports, ...).  
But it also contributes an environment to Orchestra, that selects the database, the projects to use, along with their logical names.  
It also supports the use of Baseline Set Definitions and thus the Orchestra Baseline mechanism on all artefacts that can be baselined by Doors.  
Finally, it contributes various Orchestra menus to the Doors interfaces so as to be able to use Orchestra services within Doors, such as Link Manager ones.  
It is available for Doors 32 bits and 64 bits versions.

## Issues tracking

The Jira project is on [jiradev.orchestra] (http://jiradev.orchestra/browse/CODOORS/?selectedTab=com.atlassian.jira.jira-projects-plugin:summary-panel)

## Software Configuration Management hosting

The Git repository is on [vmo10113.orchestra] (http://vmo10113.orchestra:7990/projects/REM/repos/connectordoors)

## Builds

The environment (Java) is built on [Jenkins](http://tc21.orchestra.thales:8090/jenkins/view/ReqMgt)
The connector (C#) is built on developer machine and the binaries put in configuration...  

## Documentation

You can find more documentation on: 

* [Tropical team wiki](https://ecm.corp.thales/Livelink/livelink.exe?func=ll&objId=61210617&objAction=browse&viewType=1)
* [Orchestra SSDD](https://ecm.corp.thales/Livelink/livelink.exe?func=ll&objId=34370343&objAction=browse&viewType=1)

