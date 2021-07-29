@echo off & setlocal enabledelayedexpansion

cd %~dp0

set SERVER_NAME=GreenFarm

set CLASS_PATH="..;..\lib\*;../conf"

set PORT=%1

set CONFIG=%2

if "%PORT%"=="-h" (
    goto print_usage
)
if "%PORT%"=="--help" (
    goto print_usage
)

if "%PORT%"=="" (
set MAIN_CLASS=com.callforcode.greenfarm.GreenFarmApplication
) else ( if "%CONFIG%"=="" (
    set MAIN_CLASS=com.callforcode.greenfarm.GreenFarmApplication %PORT%
    echo The port is configured as %PORT%
    set CLASS_PATH=../conf;%CLASS_PATH%
    ) else (
    set MAIN_CLASS=com.callforcode.greenfarm.GreenFarmApplication %PORT% %CONFIG%
    echo The port is configured as %PORT%
    echo The configuration path is %CONFIG%
    set CLASS_PATH=../%CONFIG%;%CLASS_PATH%
    )
    echo The classpath is %CLASS_PATH%
)

echo Starting the %SERVER_NAME% ...

java -server -Xmx2g -Xms2g -Xmn1g -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -Dfile.encoding=UTF-8 -classpath %CLASS_PATH% %MAIN_CLASS%

goto exit

:print_usage
 echo "usage: start.bat [port] [config_dir]"
 echo "  port: proxy listen port, default is 8081"
 echo "  config_dir: proxy config directory, default is conf"
 pause

:exit
 pause
