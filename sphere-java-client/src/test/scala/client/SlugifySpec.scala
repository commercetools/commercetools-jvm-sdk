package de.commercetools.sphere.client

import org.scalatest._
import org.scalatest.matchers.MustMatchers
import de.commercetools.internal.util.Ext

class SlugifySpec extends WordSpec with MustMatchers {
  "Slugify" in {
     // TODO might be better to use java.text.Normalizer, with a pre-pass for German (ä->ae)
     // https://github.com/mkonicek/GoodStock/blob/master/Android/src/eu/logio/goods/Diacritics.java#L35
     Ext.slugify("küchen") must be ("kuchen")
  }
}
