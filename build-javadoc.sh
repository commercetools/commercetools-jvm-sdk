#!/bin/bash

set -e

DOC_DIR=target/doc
LIB_DIR=target/lib

sbt clean update
mkdir -p $DOC_DIR $LIB_DIR
find ~/.ivy2/cache -name *.jar -exec cp {} $LIB_DIR \;

javadoc -d $DOC_DIR -classpath "$LIB_DIR/*" -use -sourcepath sphere-java-client/src/main/java:sphere-sdk-java/app -subpackages de.commercetools.sphere -subpackages sphere