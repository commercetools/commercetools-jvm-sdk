## Test first
1. `mvn install` to install it locally and run tests/integration tests, runs about 15 min
1. use the new version as snapshot dependency for sunrise and run it: `sbt clean fullClasspath run`, the categories and the products should appear with prices

## Release without rerunning the tests
1. `./preapre-release.sh <RELEASE_TYPE> <RELEASE_BRANCH>`
    * <RELEASE_BRANCH> required for PATCH releases
    * <RELEASE_TYPE> is MAJOR, MINOR, PATCH
      * a MAJOR release creates a new branch (v<MAJOR+1>.0), increments the major version and pushes the changes to the branch
      * a MINOR release creates a new branch (v<MAJOR>.<MINOR+1>), increments the minor version and pushes the changes to the branch
      * a PATCH release increments the minor version and pushes changes to the current branch
    
1. `./perform-release.sh`
    * deploys the version from the current branch and publishes the javadoc to github pages
    
1. https://oss.sonatype.org/ click "release" for commercetools/sphere stuff in "Staging Repositories"
1. update GitHub release on https://github.com/commercetools/commercetools-jvm-sdk/releases
1. update version in the GitHub README


## Rollback on failures
1. `./mvnw release:rollback`
1.  may remove tag (example): `git tag -d v1.0.0-RC3 && git push origin :refs/tags/v1.0.0-RC3`
1. `./mvnw release:clean`

## location of artefacts
* https://oss.sonatype.org/content/repositories/snapshots/com/commercetools/sdk/jvm/core/commercetools-models/
* https://search.maven.org/#search%7Cga%7C1%7Ca%3A%22commercetools-models%22
