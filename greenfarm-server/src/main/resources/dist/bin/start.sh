#!/bin/bash

SERVER_NAME=GreenFarm

cd `dirname $0`
cd ..
DEPLOY_DIR=`pwd`


CLASS_PATH=.:${DEPLOY_DIR}/lib/*
CLASS_PATH=${DEPLOY_DIR}/conf:${CLASS_PATH}

JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "

JAVA_MEM_OPTS=" -server -Xmx2g -Xms2g -Xmn1g -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "

MAIN_CLASS=com.callforcode.greenfarm.GreenFarmApplication

echo "Starting the $SERVER_NAME ..."

echo "The classpath is ${CLASS_PATH}"

java ${JAVA_OPTS} ${JAVA_MEM_OPTS} -classpath ${CLASS_PATH} ${MAIN_CLASS}
