resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Typesafe Repo" at "https://oss.sonatype.org/content/groups/public/"

addSbtPlugin("play"              % "sbt-plugin"  % "2.1.1")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.7.1")

addSbtPlugin("com.typesafe.sbt"  % "sbt-pgp"     % "0.8")

addSbtPlugin("de.johoop"         % "jacoco4sbt"  % "1.2.4")
