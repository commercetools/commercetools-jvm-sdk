resolvers += "Sonatype Repo" at "https://oss.sonatype.org/content/groups/public/"

resolvers += Resolver.sonatypeRepo("releases")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.7.1")

//http://www.scala-sbt.org/sbt-pgp/
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

//https://github.com/sbt/sbt-unidoc/
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.3.3")

addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.6")

addSbtPlugin("io.sphere" % "git-publisher" % "0.2")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

//required for sbt-idea, see https://github.com/sphereio/sphere-jvm-sdk/issues/160
libraryDependencies += "commons-io" % "commons-io" % "2.4"

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.5")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "4.0.0")