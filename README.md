#Mob Recipes
Adds Recipes for Hostile Mobs

####For Linux based systems or Mac.
Setup the workspace for IntelliJ.
* _setup-idea.command

Setup the workspace for Eclipse.
* _setup-eclipse.command

Compile the source code of the mod.
* _build.command

To run the test client.
* _runClient.command

To run the test server.
* _runServer.command

To upload a new update of the mod to CurseForge use this. (Make sure you have filled out the build.properties file)
* _curseforgeupload.command

####For Windows systems.

Setup the workspace for IntelliJ.
* _setup-idea.bat

Setup the workspace for Eclipse.
* _setup-eclipse.bat

Compile the source code of the mod.
* _build.bat

To run the test client.
* _runClient.bat

To run the test server.
* _runServer.bat

To upload a new update of the mod to CurseForge use this. (Make sure you have filled out the build.properties and private_EXAMPLE.properties file)
* _curseforgeupload.bat

####Mod properties.
The following file is used to change the mod name, mod version, Minecraft version, Forge version, MCP mappings version and your group info.
* build.properties

This file is for your CurseForge API key. Read the instructions in the file and edit it to work for you. (NEVER share the "private.properties" file with anyone unless you trust them and make sure you don't upload it to github)
* private_EXAMPLE.properties

(This should be renamed to "private.properties" once you have finished setting it up or you will not be able to run any gradle task without it)

####.gitignore
This file is used to only upload the things that you want to GitHub, BitBucket or any other relevant hosting service.

Anything starting with a "!" is uploaded anything else is usually ignored because of the "*" but sometimes things get through like ".DS_STORE" files found on Mac or "desktop.ini" files from Windows for example so we add them to the .gitignore file without the "!" so that they are not uploaded like so.

```
*
!src/*
!LICENSE.MD
.DS_STORE
```
 By default I only upload the following things but you may want to edit the ".gitignore" file to suit your needs.
```
*
!src/*
!LICENSE.md
!README.md
!.gitignore
!build.gradle
!gradle/*
!gradlew
!gradlew.bat
!build.properties
!private_EXAMPLE.properties
!_runServer.command
!_runServer.bat
!_runClient.command
!_runClient.bat
!_curseforgeupload.command
!_curseforgeupload.bat
!_build.command
!_build.bat
!_setup-idea.command
!_setup-idea.bat
!_setup-eclipse.command
!_setup-eclipse.bat
```
