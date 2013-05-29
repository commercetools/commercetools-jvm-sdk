package io.sphere.internal.util

import org.scalatest._

class SlugifySpec extends WordSpec with MustMatchers {
  "Slugify" in {
     Ext.slugify("Küchen") must be ("kuchen")
  }
}
