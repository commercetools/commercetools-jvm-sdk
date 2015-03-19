#!/bin/bash

if [ "$TRAVIS_PULL_REQUEST" = "false" ]; then sbt "jvm-sdk/gitPublish target/javaunidoc
  https://$GH_TOKEN:x-oauth-basic@github.com/$TRAVIS_REPO_SLUG.git \"javadoc/$TRAVIS_BRANCH\"
  sphere-oss automation@commercetools.de"
fi