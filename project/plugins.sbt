resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype Repo" at "https://oss.sonatype.org/content/groups/public/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin"  % "2.2.2")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.7.1")

//http://www.scala-sbt.org/sbt-pgp/
addSbtPlugin("com.typesafe.sbt"  % "sbt-pgp"     % "0.8.3")

//https://github.com/sbt/sbt-unidoc/tree/v0.3.0
//https://github.com/sbt/sbt-unidoc/tree/v0.3.0#how-to-unify-javadoc
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.3.0")

addSbtPlugin("de.johoop"         % "jacoco4sbt"  % "2.1.4")

addSbtPlugin("com.typesafe.sbt"  % "sbt-osgi"    % "0.7.0")
