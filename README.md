# Wakeup clock

## Description

Orchestra Framework is a software providing the middleware for Orchestra applications to exchange data and services.  
It is made of three main parts:

* **Orchestra Framework Core (Framework)**, the applicative bus. The bus provides with the definition of Orchestra services and a way to use them from any Orchestra tool. It also provides with the capability to extend Orchestra scope with new services and applications,
* **Orchestra Data Management (ODM)**, the context handler. It provides with the capability to define contexts for Orchestra applications to use, and to refer to the required external configuration (configuration directories),
* **Orchestra Explorer (OE)**, which is the Orchestra artefacts explorer.

## Issues tracking

The Jira project is on [jiradev] (http://jiradev.orchestra/browse/ORCFMK/?selectedTab=com.atlassian.jira.jira-projects-plugin:summary-panel)

## Software Configuration Management hosting

The Git repository is on [vmo10113] (http://vmo10113.orchestra:7990/projects/DAM/repos/framework/browse)

## Builds

The Jenkins builds for this project are on [TC21](http://tc21.orchestra.thales:8090/jenkins/view/DataMgt/)

### Integration Tests

In order to run the integration tests, hereafter are the prerequisites:

* The computer is a Windows 64 bits (write into HKLM\Software\Wow6432Node)
* Install Orchestra Doctor in C:\Orchestra\Thales
* Set the User Account Control (UAC) to the lowest level (Integration tests need to write in HKLM registry)

## Installation 

An alternative eclipse.ini can be provided :  
`msiexec /qb /i OrchestraFramework-[VERSION].[TIMESTAMP].msi /l OrchestraFramework.log ECLIPSE_INI=path\to\alternative\framework.ini`

NB: if eclipse.ini does not exist, the embedded one is used instead and an error is displayed in the log file


## Documentation

You can find more documentation on: 

* [Tropical team wiki](https://ecm.corp.thales/Livelink/livelink.exe?func=ll&objId=61210617&objAction=browse&viewType=1)
* [IDD](https://ecm.corp.thales/Livelink/livelink.exe?func=ll&objId=31238475&objAction=browse&sort=name)

