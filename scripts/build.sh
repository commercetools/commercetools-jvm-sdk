#!/bin/bash

sbt_command="./sbt -java-home /usr/lib/jvm/java-8-oracle/"
if [ -z "$JENKINS_URL" ]
then
	sbt_command="./sbt"
fi

$sbt_command test it:test::compile "show version"