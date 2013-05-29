package io.sphere.internal.util

import org.scalatest._

class UtilSpec extends WordSpec with MustMatchers {
  "urlEncode()" in {
     Util.urlEncode("slug=\"snowboard\"") must be ("slug%3D%22snowboard%22")
  }

  "Url.combine()" in {
    Url.combine("http://foo", "bar") must be ("http://foo/bar")
    Url.combine("http://foo/", "bar") must be ("http://foo/bar")
    Url.combine("http://foo", "/bar") must be ("http://foo/bar")
    Url.combine("http://foo/", "/bar") must be ("http://foo/bar")
  }

  "Url.stripExtension()" in {
    Url.stripExtension("http://foo/bar.jpg") must be ("http://foo/bar")
    Url.stripExtension("http://foo/bar") must be ("http://foo/bar")
  }

  "Url.getExtension()" in {
    Url.getExtension("http://foo/bar.jpg") must be (".jpg")
    Url.getExtension("http://foo/bar.") must be (".")
    Url.getExtension("http://foo/bar") must be ("")
  }
}
