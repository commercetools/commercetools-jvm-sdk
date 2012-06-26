package test

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import play.api.test._
import play.api.test.Helpers._
import sphere.Config

class ConfigSpec extends WordSpec with MustMatchers {

  "Read config" in {
    val config = Map(
      "sphere.auth" -> "http://localhost:7777",
      "sphere.core" -> "configDoesNotValidateURLs",
      "sphere.clientID" -> "client1",
      "sphere.project" -> "%7/",
      "unused" -> "unused")
    running(FakeApplication(additionalConfiguration = config)) {
      Config.authEndpoint must be ("http://localhost:7777")
      Config.coreEndpoint must be ("configDoesNotValidateURLs")
      Config.clientID must be ("client1")
      (evaluating { Config.projectID } must produce[Exception]).
        getMessage must be("Configuration error [Invalid project name '%7/'. Project name can only contain letters, numbers, dashes and underscores.]")
      (evaluating { Config.clientSecret } must produce[Exception]).
        getMessage must be("Configuration error [Path sphere.clientSecret not found in configuration.]")
    }
  }
}
