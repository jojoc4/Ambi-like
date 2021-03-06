; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Ambilike"
#define MyAppVersion "1.1"
#define MyAppPublisher "HE-Arc"
#define MyAppURL "https://forge.ing.he-arc.ch/gitlab/inf/1819/p2-java/Ambi-like"
#define MyAppExeName "Ambilike-RMI.exe"  
#define MyAppIcoName "logo.ico"
#define MyAppGroupName "Ambilike"
#define MyAppUninstaller "unins000.exe"
#define SourceDir "D:\users\teo\Documents\hes\2e\p2\Ambi-like\creation-installeur" ;change this path to your folder if you move the files.

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{AAEB3932-590E-4A63-8DE5-1A5D6F91080A}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf64}\{#MyAppName}
DefaultGroupName={#MyAppGroupName}
;AllowNoIcons=yes
OutputDir={#SourceDir}
OutputBaseFilename=ambilike-RMI-setup
SetupIconFile={#SourceDir}\{#MyAppIcoName}
Compression=lzma
SolidCompression=yes

[Languages]
Name: "french"; MessagesFile: "compiler:Languages\French.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked
Name: "startmenuicon"; Description: "Cr?er un raccourci dans le menu d?marrer"; GroupDescription: "{cm:AdditionalIcons}"; 
Name: "quicklaunchicon"; Description: "{cm:CreateQuickLaunchIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked; OnlyBelowVersion: 0,6.1
Name: "AutoRunRegistry"; Description: "D?marrer {#MyAppName} avec Windows"; GroupDescription: "{cm:AdditionalIcons}"

[Files]
Source: "{#SourceDir}\{#MyAppExeName}"; DestDir: "{app}"; Flags: ignoreversion
Source: "{#SourceDir}\{#MyAppIcoName}"; DestDir: "{app}"; Flags: ignoreversion
Source: "{#SourceDir}\config.amb"; DestDir: "{app}"; Flags: ignoreversion
Source: "{#SourceDir}\javadoc\*"; DestDir: "{app}\javadoc"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "{#SourceDir}\jdk-11.0.3\*"; DestDir: "{app}\jdk-11.0.3"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "{#SourceDir}\libs\*"; DestDir: "{app}\libs"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "{#SourceDir}\modes\*"; DestDir: "{app}\modes"; Flags: ignoreversion recursesubdirs createallsubdirs
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\{#MyAppIcoName}"; Tasks: desktopicon
Name: "{userappdata}\Microsoft\Internet Explorer\Quick Launch\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; Tasks: quicklaunchicon
Name: "{group}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"; IconFilename: "{app}\{#MyAppIcoName}"; Tasks: startmenuicon
Name: "{group}\{cm:ProgramOnTheWeb,{#MyAppName}}"; Filename: "{#MyAppURL}"; Tasks: startmenuicon
Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{app}\{#MyAppUninstaller}"; Tasks: startmenuicon

[Registry]
;any user
Root: HKLM; Subkey: "Software\Microsoft\Windows\CurrentVersion\Run"; ValueType: string; ValueName: {#MyAppName}; ValueData: "{app}\{#MyAppExeName}"; Tasks:AutoRunRegistry;

[Run]
Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: nowait postinstall skipifsilent


