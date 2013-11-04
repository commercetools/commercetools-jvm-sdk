### Versioning and Releasing

* Click "Schedule Release Build" in [Jenkins][3]
* Publish release at [oss.sonatype.org][1] (more detailed instructions can be found in the [official documentation][2])
    * Go to [https://oss.sonatype.org][1] and login
    * Open "Staging Repositories" and find repository you would like to release
    * "Close" it. After you have done this, it would be verified and prepared for the release
    * If everything went good - "Release" it

[1]: https://oss.sonatype.org
[2]: https://docs.sonatype.org/display/Repository/Sonatype+OSS+Maven+Repository+Usage+Guide#SonatypeOSSMavenRepositoryUsageGuide-8a.ReleaseIt
[3]: http://hub.ci.cloud.commercetools.de/view/sphere/job/sphere-play-sdk/release
