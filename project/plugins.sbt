resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("play"              % "sbt-plugin"  % "2.1.1")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.7.1")

resolvers += Resolver.url("sbt-plugin-releases", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases")) (Resolver.ivyStylePatterns)

addSbtPlugin("com.jsuereth" % "xsbt-gpg-plugin" % "0.6")

