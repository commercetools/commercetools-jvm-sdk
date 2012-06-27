package sphere

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import play.api.test._
import play.api.test.Helpers._

class ConfigSpec extends WordSpec with MustMatchers {

  val config = Map(
    "sphere.auth"     -> "http://localhost:7777",
    "sphere.core"     -> "configDoesNotValidateURLs",
    "sphere.clientID" -> "client1",
    "unused"          -> "unused")

  "Read config" in {
    running(FakeApplication(additionalConfiguration = config)) {
      val config = new Config(play.Configuration.root)
      config.authEndpoint must be ("http://localhost:7777")
      config.coreEndpoint must be ("configDoesNotValidateURLs")
      config.clientID must be ("client1")
    }
  }

  "Valiate project" in {
    running(FakeApplication(additionalConfiguration = Map("sphere.project" -> "%7/"))) {
      val config = new Config(play.Configuration.root)
      (evaluating { config.projectID } must produce[Exception]).
        getMessage must be("Configuration error [Invalid project name '%7/'. Project name can only contain letters, numbers, dashes and underscores.]")
    }
  }

  "Fail on missing keys" in {
    running(FakeApplication(additionalConfiguration = Map("sphere.project" -> "%7/"))) {
      val config = new Config(play.Configuration.root)
      (evaluating { config.clientSecret } must produce[Exception]).
        getMessage must be("Configuration error [Path sphere.clientSecret not found in configuration.]")
    }
  }
}
