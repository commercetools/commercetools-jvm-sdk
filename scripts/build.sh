#!/bin/bash

sbt_command="./sbt"
if [ -n "$JENKINS_URL" ]
then
        export JAVA_HOME="/usr/lib/jvm/java-8-oracle/"
        export JDK_HOME="/usr/lib/jvm/java-8-oracle/"
	sbt_command="./sbt -java-home $JAVA_HOME"
	
fi

$sbt_command test it:test::compile "show version"
