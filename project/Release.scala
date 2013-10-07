import sbt._
import Keys._
import sbt.Extracted
import sbtrelease.ReleasePlugin._
import sbtrelease._
import ReleaseStateTransformations._
import sbtrelease.ReleasePlugin.ReleaseKeys._

object Release {
  private val pathToPgpPassphrase = System.getProperty("user.home") + "/.sbt/gpg/passphrase"

  private def task2ReleaseStep(task: sbt.TaskKey[scala.Unit]) = {
    val action = { st: State =>
      val extracted: Extracted = Project.extract(st)
      val ref = extracted.get(thisProjectRef)
      extracted.runAggregated(task in Global in ref, st)
    }
    ReleaseStep(action = action, enableCrossBuild = true)
  }

  private lazy val commitNextReadme = ReleaseStep(commitReadme)
  private def commitReadme = { st: State =>
    val readmeFile = file(".") / "README.md"
    val v = st.get(versions).getOrElse(sys.error("No versions are set! Was this release part executed before inquireVersions?"))._1
    val readmeContent = IO.read(readmeFile)

    def replace(content: String, before: String, versionPattern: String, after: String) = {
      import scala.util.matching.Regex
      val left = Regex.quoteReplacement(before)
      val right = Regex.quoteReplacement(after)
      val ReplacementPattern = new Regex(left + versionPattern + right, "left", "right")
      ReplacementPattern replaceAllIn (content,  Regex.quoteReplacement(left + v + right))
    }

    var newReadMeContent = replace(readmeContent, """sphere-play-sdk" % """", """[^"]+""", """" withSources""")
    newReadMeContent = replace(newReadMeContent, """<version>""", """[^<]+""", """</version>""")
    IO.write(readmeFile, newReadMeContent)

    import sbtrelease.Git
    Git.add("README.md")  ! st.log
    Git.commit("Update version in documentation to " + v + ".")  ! st.log
    st
  }

  lazy val publishSignedArtifactsStep = ReleaseStep(action = publishSignedArtifactsAction, enableCrossBuild = true)
  lazy val publishSignedArtifactsAction = { st: State =>
    val extracted: Extracted = Project.extract(st)
    val ref = extracted.get(thisProjectRef)
    extracted.runAggregated(com.typesafe.sbt.pgp.PgpKeys.publishSigned in Global in ref, st)
  }


  lazy val publishSettings = releaseSettings ++ Seq(
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    com.typesafe.sbt.pgp.PgpKeys.pgpPassphrase in Global := {
      val pgpPassphraseFile = file(pathToPgpPassphrase)
      if(pgpPassphraseFile.exists && pgpPassphraseFile.canRead) {
        Option(IO.read(pgpPassphraseFile).toCharArray)
      } else
        None
    },
    publishMavenStyle := true,
    publishArtifact in Test := false,
    pomExtra := (
      <scm>
        <url>git@github.com:commercetools/sphere-play-sdk.git</url>
        <connection>scm:git:git@github.com:commercetools/sphere-play-sdk.git</connection>
      </scm>
        <developers>
          <developer>
            <id>martin</id>
            <name>Martin Konicek</name>
            <url>https://github.com/mkonicek</url>
          </developer>
          <developer>
            <id>leonard</id>
            <name>Leonard Ehrenfried</name>
            <url>https://github.com/lenniboy</url>
          </developer>
          <developer>
            <id>gregor</id>
            <name>Gregor Goldmann</name>
            <url>https://github.com/gogregor</url>
          </developer>
          <developer>
            <id>michael</id>
            <name>Michael Schleichardt</name>
            <url>https://github.com/schleichardt</url>
          </developer>
        </developers>
      ),
    ReleaseKeys.releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      task2ReleaseStep(com.typesafe.sbt.pgp.PgpKeys.publishSigned),
      setNextVersion,
      commitNextVersion,
      commitNextReadme,
      pushChanges
    )
  )

}