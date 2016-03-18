## Test first
1. `mvn install` to install it locally and run tests/integration tests, runs about 15 min
1. use the new version as snapshot dependency for sunrise and run it: `sbt clean fullClasspath run`, the categories and the products should appear with prices

## Release without rerunning the tests
1. `mvn release:prepare -Darguments="-DskipTests" -DskipTests`
    * ~ 2 min
1. `mvn release:perform -Darguments="-DskipTests" -DskipTests`
    * ~ 5 min
1. checkout the tag version and `git checkout $(git describe --abbrev=0 --tags) && mvn clean javadoc:aggregate scm-publish:publish-scm -P publish-site && git checkout master`
1. update GitHub release on https://github.com/sphereio/sphere-jvm-sdk/releases
1. add version badges to http://dev.commercetools.com/release-notes.html


## Rollback on failures
1. `mvn release:rollback`
1.  may remove tag (example): `git tag -d v1.0.0-RC3 && git push origin :refs/tags/v1.0.0-RC3`
1. `mvn release:clean`
