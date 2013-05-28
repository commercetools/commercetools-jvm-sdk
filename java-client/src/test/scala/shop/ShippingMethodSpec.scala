package io.sphere.client
package shop
package model

import java.util.Currency
import JsonTestObjects._
import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import com.neovisionaries.i18n.CountryCode

class ShippingMethodSpec extends WordSpec with MustMatchers  {

  "shippingRateForLocation()" must {
    "find a shipping rate for a country" in {
      val rate = shippingMethod.shippingRateForLocation(new Location(CountryCode.DE), Currency.getInstance("EUR"))
      rate == null must be (false)
      rate.getPrice.getAmount.intValue must be (10)
    }
    "find a country shipping rate for a country/state location" in {
      val rate = shippingMethod.shippingRateForLocation(new Location(CountryCode.DE, "Sylt"), Currency.getInstance("EUR"))
      rate == null must be (false)
      rate.getPrice.getAmount.intValue must be (10)
    }
    "return null for non-existing location" in {
      val rate = shippingMethod.shippingRateForLocation(
        new Location(CountryCode.CN), 
        Currency.getInstance("EUR"))
      rate == null must be (true)
    }
  }


}
