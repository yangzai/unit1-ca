@ECHO OFF

REM Created by Navy Gao on 29/3/2016
REM ********************************
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

REM //Create an empty class folder for subsequence use of compiling
MKDIR "%cd%\submission\classes"

REM zip
rem CD /D %~dp0
CALL :ZipFile "%cd%\submission\" "%cd%\%zipfile%" || SET /A pstatus=1

EXIT /B %pstatus%

:ZipFile <Source folder> <newzipfile>
rem SET vbsfile="temp.vbs"
rem IF EXIST %vbs% DEL /F /Q %vbsfile%

rem ECHO CreateObject("Scripting.FileSystemObject").CreateTextFile(%2, True).Write "PK" ^& Chr(5) ^& Chr(6) ^& String(18, vbNullChar)>%vbsf
rem ECHO Set objShell = CreateObject("Shell.Application")>>%vbsfile%
rem ECHO Set source = objShell.NameSpace(%1).Items>>%vbsfile%
rem ECHO objShell.NameSpace(%2).CopyHere(source)>>%vbsfile%
rem ECHO wScript.Sleep 2000>>%vbsfile%

echo.
echo Compressing files...

rem cscript //nologo %vbsfile%
.\tools\7z a %zipfile% .\submission\*>NULL

IF %ERRORLEVEL% EQU 0 (
	rem if exist %vbsfile% del /f /q %vbsfile%
	echo Compression Succeed^!
	EXIT /B 0
) ELSE (
	rem if exist %vbsfile% del /f /q %vbsfile%
	echo Compression error^!
	EXIT /B 1
)

GOTO :EOF