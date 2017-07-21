## Test first
1. `mvn install` to install it locally and run tests/integration tests, runs about 15 min
1. use the new version as snapshot dependency for sunrise and run it: `sbt clean fullClasspath run`, the categories and the products should appear with prices

## Release without rerunning the tests
1. `./mvnw release:prepare -Darguments="-DskipTests" -DskipTests`
    * ~ 2 min
1. `./mvnw release:perform -Darguments="-DskipTests" -DskipTests`
    * ~ 10 min
1. https://oss.sonatype.org/ click "release" for commercetools/sphere stuff in "Staging Repositories"
1. publish the Javadoc to GitHub pages: `git checkout $(git describe --abbrev=0 --tags) && ./mvnw clean compile javadoc:aggregate scm-publish:publish-scm -P publish-site && git checkout master`
1. update GitHub release on https://github.com/commercetools/commercetools-jvm-sdk/releases
1. add version badges to http://dev.commercetools.com/release-notes.html
1. update version in the GitHub README


## Rollback on failures
1. `./mvnw release:rollback`
1.  may remove tag (example): `git tag -d v1.0.0-RC3 && git push origin :refs/tags/v1.0.0-RC3`
1. `./mvnw release:clean`

## location of artefacts
* https://oss.sonatype.org/content/repositories/snapshots/com/commercetools/sdk/jvm/core/commercetools-models/
* http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22commercetools-models%22
