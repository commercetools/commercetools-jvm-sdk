package internal

import org.scalatest._
import io.sphere.internal.Version

class VersionSpec extends WordSpec with MustMatchers {
  "The class io.sphere.internal.Version" should {
    "return the version number of the SDK build" in {
      Version.version must fullyMatch regex """\d{1,2}\.\d{1,3}\.\d{1,2}((-\w{6,8})?-SNAPSHOT)?"""
    }
  }
}