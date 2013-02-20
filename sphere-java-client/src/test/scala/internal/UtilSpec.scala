package de.commercetools.internal.util

import org.scalatest._
import org.scalatest.matchers.MustMatchers

class UtilSpec extends WordSpec with MustMatchers {
  "urlEncode()" in {
     Util.urlEncode("slug=\"snowboard\"") must be ("slug%3D%22snowboard%22")
  }
}
