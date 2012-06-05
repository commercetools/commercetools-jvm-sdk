package sphere.util

import scala.collection.JavaConverters._
import java.net.{URL, URLDecoder, URLEncoder}

object Url {
  def buildQueryString(params: Map[String, Any]): String =
    params.filter(_._2 != null).map(p => p._1 + "=" + URLEncoder.encode(p._2.toString, "UTF-8")).mkString("&")

  def parseQueryString(s: String): Map[String, List[String]] = {
    s.split("&").map(_.split("=")).collect {
      case Array(k, v) => (k, URLDecoder.decode(v, "UTF-8"))
    }.groupBy(_._1).map {
      case (k, vs) => k -> vs.map(_._2).toList
    }
  }

  def buildQueryString(params: java.util.Map[String, Any]): String =
    buildQueryString(params.asScala.toMap)
  
  def combine(baseUrl: String, relativeUrl: String): String = {
    return new URL(new URL(baseUrl), relativeUrl).toString();
  }
}
