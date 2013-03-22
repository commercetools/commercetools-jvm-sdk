package io.sphere.internal.util

import org.scalatest._
import org.scalatest.matchers.MustMatchers

class SlugifySpec extends WordSpec with MustMatchers {
  "Slugify" in {
     Ext.slugify("Küchen") must be ("kuchen")
  }
}
