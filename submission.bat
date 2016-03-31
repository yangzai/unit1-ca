@ECHO OFF
SETLOCAL

IF "%~1"=="" (
	SET zipfile=se24_F02.zip
) ELSE (
	SET zipfile=%~1
)

REM Cleanup directories
IF EXIST "%cd%\submission\classes" RMDIR /Q /S "%cd%\submission\classes" 
IF EXIST "%cd%\submission\src" RMDIR /Q /S "%cd%\submission\src" 
IF EXIST "%cd%\submission\test" RMDIR /Q /S "%cd%\submission\test" 
IF EXIST "%cd%\submission\data" RMDIR /Q /S "%cd%\submission\data" 

REM Populate directories
XCOPY "%cd%\src" "%cd%\submission\src" /S /R /I
XCOPY "%cd%\test" "%cd%\submission\test" /S /R /I
XCOPY "%cd%\data-sample" "%cd%\submission\data" /S /R /I

REM zip
rem CD /D %~dp0
CALL :ZipFile "%cd%\submission\" "%cd%\%zipfile%" || SET /A pstatus=1

EXIT /B %pstatus%

:ZipFile <Source folder> <newzipfile>
SET vbsfile="temp.vbs"
IF EXIST %vbs% DEL /F /Q %vbsfile%

ECHO CreateObject("Scripting.FileSystemObject").CreateTextFile(%2, True).Write "PK" ^& Chr(5) ^& Chr(6) ^& String(18, vbNullChar)>%vbsfile%
ECHO Set objShell = CreateObject("Shell.Application")>>%vbsfile%
ECHO Set source = objShell.NameSpace(%1).Items>>%vbsfile%
ECHO objShell.NameSpace(%2).CopyHere(source)>>%vbsfile%
ECHO wScript.Sleep 2000>>%vbsfile%

echo.
echo Compressing files...
cscript //nologo %vbsfile%
IF %ERRORLEVEL% EQU 0 (
	if exist %vbsfile% del /f /q %vbsfile%
	echo Compression Succeed^!
	EXIT /B 0
) ELSE (
	if exist %vbsfile% del /f /q %vbsfile%
	echo Compression error^!
	EXIT /B 1
)

GOTO :EOF