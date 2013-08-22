###Versioning and Releasing

The single source of truth with regards of the current version is `version.sbt`, however you probably won't ever
have to touch that file yourself.

In order to release create a crendentials file at `~/.sbt/sontatype.sbt` with the following content

```scala
credentials += Credentials("Sonatype Nexus Repository Manager",
                           "oss.sonatype.org",
                           "<username>",
                           "<password>")
```

You will also need the [commercetools GPG key chain](http://pgp.mit.edu:11371/pks/lookup?op=vindex&search=0x151A6F0B123D66A0)
and have the files `pubring.asc` and `secring.asc` in `~/.sbt/gpg`.

With these two credentials run

    sbt release

This will use sensible defaults for the version number and will bump the number automatically.

The file `java-client/src/main/java/io/sphere/internal/Version.java` will also be automatically
generated with the appropriate version number.

###TODO

* The version bump task sadly doesn't run _during_ the release
* `sbt release` only calls the `publish` task but should `publish-signed` instead
