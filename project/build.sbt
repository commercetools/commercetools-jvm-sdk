//thanks to http://stackoverflow.com/a/12508163/1575096
unmanagedJars in Compile ~= {uj =>
  Seq(Attributed.blank(file(System.getProperty("java.home").dropRight(3)+"lib/tools.jar"))) ++ uj
}

javacOptions ++= Seq("-deprecation", "-Xlint:unchecked", "-source", "1.6", "-target", "1.6", "-Xlint:-options")