package de.commercetools.internal

import org.scalatest._
import org.scalatest.matchers.MustMatchers
import de.commercetools.internal.util.Ext

class SlugifySpec extends WordSpec with MustMatchers {
  "Slugify" in {
     Ext.slugify("Küchen") must be ("kuchen")
  }
}
