#!/usr/bin/env bash

function getVersion() {
    VERSION=`./mvnw -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive org.codehaus.mojo:exec-maven-plugin:1.3.1:exec | tail -n 1`
    echo ${VERSION}
}

set +e
git diff --exit-code >/dev/null 2>&1
GIT_STATUS=$?
set -e

if [[ ! GIT_STATUS -eq 0 ]]
then
    echo "Aborting due to uncommited changes"
    exit 1
fi

RELEASE_VERSION=$(getVersion)
echo "Performing release ${RELEASE_VERSION}"

echo "Deploying release ${RELEASE_VERSION}"
./mvnw clean deploy -pl "!osgi-support, !osgi-support/mirror-subclass-plugin, !osgi-support/sdk-osgi-test-campaign, !osgi-support/commercetools-tests-fragment" -DskipTests -Prelease,javadoc-jdk-8u121

git tag -a v${RELEASE_VERSION} -m 'TASK Release v${RELEASE_VERSION}'
CURRENT_BRANCH=`git rev-parse --abbrev-ref HEAD`
git push origin ${CURRENT_BRANCH} --tags

echo "Publish release javadoc  ${RELEASE_VERSION}"
./mvnw javadoc:aggregate scm-publish:publish-scm -Ppublish-site,javadoc-jdk-8u121
