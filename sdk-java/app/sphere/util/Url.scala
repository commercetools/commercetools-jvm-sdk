package sphere.util

import java.net.{URLDecoder, URLEncoder}
import scala.collection.JavaConverters._

object Url {
  def buildQueryString(params: Map[String, Any]): String =
    params.map(p => p._1 + "=" + URLEncoder.encode(p._2.toString, "UTF-8")).mkString("&")

  def parseQueryString(s: String): Map[String, List[String]] = {
    s.split("&").map(_.split("=")).collect {
      case Array(k, v) => (k, URLDecoder.decode(v, "UTF-8"))
    }.groupBy(_._1).map {
      case (k, vs) => k -> vs.map(_._2).toList
    }
  }

  def buildQueryString(params: java.util.Map[String, Any]): String =
    buildQueryString(params.asScala.toMap)
}
