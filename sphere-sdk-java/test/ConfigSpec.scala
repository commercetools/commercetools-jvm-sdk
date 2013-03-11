package sphere

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import play.api.test._
import play.api.test.Helpers._

class ConfigSpec extends WordSpec with MustMatchers {

  val config = Map(
    "sphere.auth"         -> "http://localhost:7777",
    "sphere.core"         -> "configDoesNotValidateURLs",
    "sphere.clientId"     -> "client1",
    "sphere.clientSecret" -> "secret1",
    "sphere.project"      -> "project1",
    "unused"              -> "unused")

  "Read config" in {
    running(FakeApplication(additionalConfiguration = config)) {
      val config = new ConfigImpl(play.Configuration.root)
      config.authEndpoint must be ("http://localhost:7777")
      config.coreEndpoint must be ("configDoesNotValidateURLs")
      config.clientId must be ("client1")
    }
  }

  "Validate project" in {
    running(FakeApplication(additionalConfiguration = Map("sphere.project" -> "%7/"))) {
      (evaluating {
        val config = new ConfigImpl(play.Configuration.root)
        config.projectID
      } must produce[Exception]).
        getMessage must be("Configuration error [Invalid project name '%7/'. Project name can only contain letters, numbers, dashes and underscores.]")
    }
  }

  "Fail on missing keys" in {
    running(FakeApplication(additionalConfiguration = config - "sphere.clientSecret")) {
      (evaluating {
        val config = new ConfigImpl(play.Configuration.root)
        config.clientSecret
      } must produce[Exception]).
        getMessage must be("Configuration error [Path sphere.clientSecret not found in configuration.]")
    }
  }
}
