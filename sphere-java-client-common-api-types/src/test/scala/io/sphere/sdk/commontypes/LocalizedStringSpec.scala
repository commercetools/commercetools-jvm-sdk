package io.sphere.sdk.commontypes

import org.scalatest._
import java.util.Locale
import com.google.common.base.Optional
import com.google.common.collect.Lists._
import com.google.common.collect.Sets._

class LocalizedStringSpec extends WordSpec with ShouldMatchers {

  val GermanLocale = Locale.forLanguageTag("de")
  val EnglishLocale = Locale.forLanguageTag("en")
  val FrenchLocale = Locale.forLanguageTag("fr")
  val DefaultString1 = "foo"
  val DefaultString2 = "bar"
  val DefaultString3 = "baz"
  def absent = Optional.absent
  val localizedString = new LocalizedString(GermanLocale, DefaultString1, EnglishLocale, DefaultString2)
  
  "LocalizedString" must {
    "be created from one value" in {
      val locale = new LocalizedString(GermanLocale, DefaultString1)
      locale.get(GermanLocale).get should be(DefaultString1)
      locale.get(EnglishLocale) should be(absent)
    }


    "be created from two values" in {
      val locale = localizedString
      locale.get(GermanLocale).get should be(DefaultString1)
      locale.get(EnglishLocale).get should be(DefaultString2)
    }

    "construct an object by adding a translation" in {
      val locale = new LocalizedString(GermanLocale, DefaultString1).plus(EnglishLocale, DefaultString2)
      locale.get(GermanLocale).get should be(DefaultString1)
      locale.get(EnglishLocale).get should be(DefaultString2)
    }

    "find the first best Translation" in {
      val locale = localizedString
      locale.get(newArrayList(FrenchLocale, EnglishLocale, GermanLocale)).get should be(DefaultString2)
    }

    "return present locales" in {
      localizedString.getLocales should be(newHashSet(GermanLocale, EnglishLocale))
    }

    "implement toString" in {
      localizedString.toString should be(s"LocalizedString(de -> $DefaultString1, en -> $DefaultString2)")
    }

    "implement equals" in {
      new LocalizedString(GermanLocale, DefaultString1).plus(EnglishLocale, DefaultString2) should be(localizedString)
      new LocalizedString(GermanLocale, DefaultString1) should not be(localizedString)
    }
  }
}
