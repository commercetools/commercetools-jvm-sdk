//thanks to http://stackoverflow.com/a/12508163/1575096
unmanagedJars in Compile ~= {uj =>
  Seq(Attributed.blank(file(System.getProperty("java.home").dropRight(3)+"lib/tools.jar"))) ++ uj
}

javacOptions ++= Seq("-deprecation", "-Xlint:unchecked", "-source", "1.8", "-target", "1.8", "-Xlint:-options","-encoding", "UTF-8",
  "-proc:none"//remove warning: Supported source version 'RELEASE_6' from annotation processor 'org.eclipse.sisu.space.SisuIndexAPT6' less than -source '1.8'
)

scalacOptions ++= Seq("-deprecation", "-feature")

libraryDependencies += "net.sourceforge.plantuml" % "plantuml" % "8019"

libraryDependencies += "org.jsoup" % "jsoup" % "1.8.1"