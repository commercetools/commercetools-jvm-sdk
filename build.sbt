import sbtunidoc.Plugin.UnidocKeys._

val writeVersion = taskKey[Unit]("Write the version into a file.")

writeVersion := {
  IO.write(target.value / "version.txt", version.value)
}

unidoc in Compile <<= (unidoc in Compile).dependsOn(writeVersion)