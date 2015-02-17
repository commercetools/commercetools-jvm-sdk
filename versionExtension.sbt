import java.text.SimpleDateFormat
import java.util.Date

val Snapshot = "SNAPSHOT"

version in ThisBuild <<= (version in ThisBuild) { v =>
  //Jenkins is supposed to publish every snapshot artifact which can be distinguished per Git commit.
  if (scala.util.Properties.envOrNone("JENKINS_URL").isDefined) {
    val shortenedGitHash = Process("git rev-parse HEAD").lines.head.substring(0, 7)
    if(v.endsWith(Snapshot) && !v.contains(shortenedGitHash)){
      val dateFormat = "yyyy-MM-dd-HH-mm-ss"
      val dateFormatted = new SimpleDateFormat(dateFormat).format(new Date)
      v.replace(Snapshot, "") + dateFormatted + "-" + shortenedGitHash + "-" + Snapshot
    }else {
      v
    }
  } else {
    v
  }
}
