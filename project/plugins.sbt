resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype Repo" at "https://oss.sonatype.org/content/groups/public/"

resolvers += Resolver.sonatypeRepo("releases")

addSbtPlugin("com.typesafe.play" % "sbt-plugin"  % "2.3.1")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.7.1")

//http://www.scala-sbt.org/sbt-pgp/
addSbtPlugin("com.typesafe.sbt"  % "sbt-pgp"     % "0.8.3")

//https://github.com/sbt/sbt-unidoc/
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.3.1")

addSbtPlugin("com.typesafe.sbt"  % "sbt-osgi"    % "0.7.0")

//temporary resolver until jacoco4sbt is compatible to Java 8
resolvers += "Schleichardts GitHub" at "http://schleichardt.github.io/jvmrepo/"

addSbtPlugin("io.sphere.de.johoop" % "jacoco4sbt" % "2.1.5-fork-1.0.0")

addSbtPlugin("io.sphere" % "git-publisher" % "0.2")