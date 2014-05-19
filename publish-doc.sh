#!/bin/bash

#or git@github.com:commercetools/sphere-play-sdk.git
git_path=${1:-https://${GH_TOKEN}@github.com/commercetools/sphere-play-sdk}

./sbt unidoc && \
version=$(cat target/version.txt) && \
cd target && \
docDir=gh-pages/javadoc/${version} && \
rm -rf gh-pages && \
git config user.name "TravisCI" && \
git config user.email "build@commercetools.com" && \
git clone --depth=1 $git_path -b gh-pages gh-pages && \
mkdir -p $docDir && \
cp -r javaunidoc/ $docDir && \
cd gh-pages && \
pwd && \
git add . && \
git commit -m "Deploy to Github Pages." && \
git push --quiet
