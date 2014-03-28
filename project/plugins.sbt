resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype Repo" at "https://oss.sonatype.org/content/groups/public/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin"  % "2.2.2")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.7.1")

addSbtPlugin("com.typesafe.sbt"  % "sbt-pgp"     % "0.8")

addSbtPlugin("de.johoop"         % "jacoco4sbt"  % "2.1.4")

addSbtPlugin("com.typesafe.sbt"  % "sbt-osgi"    % "0.7.0")