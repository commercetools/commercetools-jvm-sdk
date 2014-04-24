resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype Repo" at "https://oss.sonatype.org/content/groups/public/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin"  % "2.2.2")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.7.1")

//http://www.scala-sbt.org/sbt-pgp/
addSbtPlugin("com.typesafe.sbt"  % "sbt-pgp"     % "0.8.3")

//https://github.com/sbt/sbt-unidoc/
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.3.1")

//version 2.1.4 is more recent but has a bug concerning integration testing: https://github.com/sbt/jacoco4sbt/issues/13
addSbtPlugin("de.johoop"         % "jacoco4sbt"  % "2.1.3")

addSbtPlugin("com.typesafe.sbt"  % "sbt-osgi"    % "0.7.0")
