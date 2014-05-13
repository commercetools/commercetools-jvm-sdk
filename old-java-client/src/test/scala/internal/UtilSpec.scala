package io.sphere.internal.util

import org.scalatest._
import java.util.Locale

class UtilSpec extends WordSpec with MustMatchers {

  "toLanguageTag()" in {
    Util.toLanguageTag(Locale.CANADA) must be("en-CA")
    Util.toLanguageTag(Locale.CANADA_FRENCH) must be("fr-CA")
    Util.toLanguageTag(Locale.GERMAN) must be("de")
  }

  "fromLanguageTag()" in {
    Util.fromLanguageTag("en") must be(Locale.ENGLISH)
    Util.fromLanguageTag("de") must be(Locale.GERMAN)
    Util.fromLanguageTag("de-DE") must be(Locale.GERMANY)
    Util.fromLanguageTag("fr-CA") must be(Locale.CANADA_FRENCH)
  }

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
