resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype Repo" at "https://oss.sonatype.org/content/groups/public/"

addSbtPlugin("play"              % "sbt-plugin"  % "2.1.1")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.7.1")

addSbtPlugin("com.typesafe.sbt"  % "sbt-pgp"     % "0.8")

addSbtPlugin("de.johoop"         % "jacoco4sbt"  % "1.2.4")

//https://github.com/sbt/sbt-unidoc/tree/v0.1.2
//https://github.com/sbt/sbt-unidoc/tree/v0.1.2#how-to-unify-javadoc
addSbtPlugin("com.eed3si9n" % "sbt-unidoc" % "0.1.2")
