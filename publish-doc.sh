#!/bin/bash

./sbt unidoc && \
version=$(cat target/version.txt) && \
mkdir -p target/javadoc/${version} && \
cp -r target/javaunidoc/ target/javadoc/${version} && \
git init && \
git config user.name "TravisCI" && \
git config user.email "build@commercetools.com" && \
git add . && \
git commit -m "Deploy to Github Pages." && \
git push --force --quiet git@github.com:commercetools/sphere-play-sdk.git master:gh-pages 


#http://commercetools.github.io/sphere-play-sdk/