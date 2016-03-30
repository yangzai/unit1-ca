@ECHO OFF
REM Pre-Condition: None
REM ********************************
REM Created by Navy Gao on 29/3/1016
REM ********************************
REM %1 = Compile switch, compile all and compile mini

REM *********************************
REM Get variables
REM *********************************
IF "%~1"=="" (
	SET CompileOpiton=CompileMini
) ELSE (
	SET CompileOpiton=%1
)

SETLOCAL ENABLEDELAYEDEXPANSION
REM //Setup enviorment
CALL setenv.bat

SET /A pstatus=0
SET ClassDir=%cd%\classes
SET SourcePath_Prefix=src\sg\edu\nus\iss\se24_2ft\unit1\ca
SET FullSourcePath_Prefix=%cd%\src\sg\edu\nus\iss\se24_2ft\unit1\ca
SET CLASSPATH=.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar;%cd%\classses;%cd%\src;%CLASSPATH%;

REM //Create classes path
IF EXIST "%ClassDir%" RMDIR /Q /S "%ClassDir%" 1>NUL 2>&1

MKDIR "%ClassDir%"

REM //Compiling
Echo Compiling... & Echo Compiling...>%~n0.log
IF "%CompileOpiton%"=="CompileMini" (
	CALL :CompileMini || SET /A pstatus=1
) ELSE (
	CALL :CompileAll || SET /A pstatus=2
)

GOTO :END

REM ****************** Functions **************************

:::::::::::::::::::::::CompileAll  :::::::::::::::::::::::::::
REM //Compile all java files under specified folder

:CompileAll
FOR /R "%FullSourcePath_Prefix%" %%s in (.) do (
	ECHO %%s
	javac -d "%cd%\classes" -cp classes -sourcepath src "%%s"\*.java>>%~n0.log
	IF !ERRORLEVEL! NEQ 0 SET /A status=1
) 

IF DEFINED status IF !status!==1 (
	EXIT /B 1
)
GOTO :EOF

:::::::::::::::::::::::CompileMini  :::::::::::::::::::::::::::
REM //Compile only related files which application running needs
:CompileMini

javac -d "%cd%\classes" -cp classes -sourcepath src %SourcePath_Prefix%\*.java 1>>%~n0.log 2>>&1
IF %ERRORLEVEL% EQU 0 (
	EXIT /B 0
) ELSE (
	EXIT /B 1
)
GOTO :EOF

:END
IF %pstatus% GTR 0 (
	ECHO Compile error^! & Echo Compile error^!>>%~n0.log
	IF EXIST "%ClassDir%" RMDIR /Q /S "%ClassDir%" 1>NUL 2>&1
) ELSE (
	ECHO Compile Succeed^! & Echo Compile Succeed^!>>%~n0.log
)
EXIT /B %pstatus%
ENDLOCAL
@ECHO ON
