package io.sphere.client
package shop
package model

import TestUtil.eur
import JsonResponses._

import io.sphere.client.model.{Reference, EmptyReference}

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import com.neovisionaries.i18n.CountryCode._

class PriceSpec extends WordSpec with MustMatchers  {

  val money = eur(100)
  val emptyGroup: Reference[CustomerGroup] = EmptyReference.create("customerGroup")

  "matches()" must {
    "match a price with value" in {
      val p = new Price(money, null, null)
      p.matches("EUR", null, null) must be (true)
      p.matches("USD", null, null) must be (false)
      p.matches("EUR", DE, null) must be (false)
      p.matches("EUR", null, emptyGroup) must be (true)
      p.matches("EUR", null, customerGroup) must be (false)
    }
    "match a price with value and empty group reference" in {
      val p = new Price(money, null, emptyGroup)
      p.matches("EUR", null, null) must be (true)
      p.matches("USD", null, null) must be (false)
      p.matches("EUR", DE, null) must be (false)
      p.matches("EUR", null, emptyGroup) must be (true)
      p.matches("EUR", null, customerGroup) must be (false)
    }
    "match a price with value and country" in {
      val p = new Price(money, DE, null)
      p.matches("EUR", null, null) must be (false)
      p.matches("EUR", DE, null) must be (true)
      p.matches("EUR", DE, emptyGroup) must be (true)
      p.matches("EUR", DE, customerGroup) must be (false)
      p.matches("EUR", FR, customerGroup) must be (false)
      p.matches("EUR", FR, null) must be (false)
      p.matches("EUR", FR, emptyGroup) must be (false)
      p.matches("USD", DE, emptyGroup) must be (false)
    }
    "match a price with value, country and group" in {
      val p = new Price(money, DE, customerGroup)
      p.matches("EUR", DE, customerGroup) must be (true)
      p.matches("EUR", DE, null) must be (false)
      p.matches("EUR", FR, customerGroup) must be (false)
      p.matches("EUR", DE, customerGroup2) must be (false)
    }
    "match a price with value and group" in {
      val p = new Price(money, null, customerGroup)
      p.matches("EUR", null, customerGroup) must be (true)
      p.matches("EUR", null, customerGroup2) must be (false)
    }
  }
}
