set -e

cmd="docker run --rm \
-v $PWD:/wd \
-w /wd \
-e JVM_SDK_IT_SERVICE_URL=$JVM_SDK_IT_SERVICE_URL \
-e JVM_SDK_IT_AUTH_URL=$JVM_SDK_IT_AUTH_URL \
-e JVM_SDK_IT_PROJECT_KEY=$JVM_SDK_IT_PROJECT_KEY \
-e JVM_SDK_IT_CLIENT_ID=$JVM_SDK_IT_CLIENT_ID \
-e JVM_SDK_IT_CLIENT_SECRET=$JVM_SDK_IT_CLIENT_SECRET \
-e TRAVIS_PULL_REQUEST=$TRAVIS_PULL_REQUEST \
-e TRAVIS_BRANCH=$TRAVIS_BRANCH \
-e GH_TOKEN=$GH_TOKEN \
-e TRAVIS_REPO_SLUG=$TRAVIS_REPO_SLUG \
msct/jvmsdktest"

eval "$cmd sh -c './sbt genDoc test it:test'"
test_result=$?
eval "$cmd sh -c 'bash scripts/publish-javadoc-branch-folder.sh > /dev/null 2> /dev/null'"
eval "$cmd sh -c './sbt \"commercetools-models/it:test::runMain io.sphere.sdk.client.MainMethodThreadLeakTest\"'"
eval "$cmd sh -c './sbt \"test:runMain introspection.rules.RulesMain\"'"
if [ $test_result -eq 0 ]; then
  eval "$cmd sh -c 'bash scripts/publish-javadoc-version-folder.sh > /dev/null 2> /dev/null'"
fi