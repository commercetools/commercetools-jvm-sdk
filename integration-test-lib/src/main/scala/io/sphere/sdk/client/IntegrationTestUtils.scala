package io.sphere.sdk.client

import com.typesafe.config.ConfigFactory
import scala.util.Properties._
import scala.collection.JavaConversions._

object IntegrationTestUtils {
  lazy val defaultConfig = {
    for {
      core <- envOrNone("JVM_SDK_IT_SERVICE_URL")
      auth <- envOrNone("JVM_SDK_IT_AUTH_URL")
      key <- envOrNone("JVM_SDK_IT_PROJECT_KEY")
      id <- envOrNone("JVM_SDK_IT_CLIENT_ID")
      secret <- envOrNone("JVM_SDK_IT_CLIENT_SECRET")
    } yield {
      val map = Map(
        "sphere.core" -> core,
        "sphere.auth" -> auth,
        "sphere.project" -> key,
        "sphere.clientId" -> id,
        "sphere.clientSecret" -> secret
      )
      ConfigFactory.parseMap(map).withFallback(ConfigFactory.load())
    }
  }.get
}
