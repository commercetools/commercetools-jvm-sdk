#!/bin/bash

if [ "$TRAVIS_BRANCH"  = "master" -a "$TRAVIS_PULL_REQUEST" = "false" ]; then sbt
  "jvm-sdk/gitPublish target/javaunidoc https://$GH_TOKEN:x-oauth-basic@github.com/$TRAVIS_REPO_SLUG.git
  \"javadoc/<version>\" sphere-oss automation@commercetools.de"
fi