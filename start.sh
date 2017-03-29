#!/bin/sh
# filename: start.sh

echo "build start"

JAR_PATH=lib
BIN_PATH=bin
SRC_PATH=src

# java文件列表目录  
SRC_FILE_LIST_PATH=src/sources.list

#生成所有的java文件列表  
rm -f $SRC_PATH/sources  
find $SRC_PATH/ -name *.java > $SRC_FILE_LIST_PATH

#删除旧的编译文件，生成bin目录  
rm -rf $BIN_PATH/  
mkdir $BIN_PATH/
cp $SRC_PATH/JMXConf.properties $BIN_PATH/

#生成依赖jar包列表
for file in  ${JAR_PATH}/*.jar;  
do  
jarfile=${jarfile}:${file}  
done  

#编译
javac -d $BIN_PATH/ -cp $jarfile @$SRC_FILE_LIST_PATH

echo "run start"

#运行  
nohup java -cp $BIN_PATH$jarfile main.TomcatMonitor &