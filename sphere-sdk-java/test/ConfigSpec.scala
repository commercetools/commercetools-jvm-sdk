package test

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._
import sphere.Config

class ConfigSpec extends Specification {

  "ConfigTest" should {
    "Read config Test" in {
      val config = Map(
        "sphere.auth" -> "http://localhost:7777",
        "sphere.core" -> "configDoesNotValidateURLs",
        "sphere.clientID" -> "client1",
        "sphere.project" -> "%7/",
        "unused" -> "unused")
      running(FakeApplication(additionalConfiguration = config)) {
        Config.authEndpoint must be ("http://localhost:7777")
        Config.coreEndpoint must be ("configDoesNotValidateURLs")
        Config.projectID must throwAn[Exception].like { case e =>
          e.getMessage must equalTo("Configuration error [Invalid project name '%7/'. Project name can only contain letters, numbers, dashes and underscores.]")}
        Config.clientSecret must throwAn[Exception].like { case e =>
          e.getMessage must equalTo("Configuration error [Path sphere.clientSecret not found in configuration.]")}
        Config.clientID must be ("client1")
      }
    }
  }
}
