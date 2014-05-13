package io.sphere.client
package shop
package model

import java.util.Locale
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

import io.sphere.client.model._
import org.scalatest._
import TestUtil._


class LocalizedStringSpec extends WordSpec with MustMatchers  {

  val EN = "beer"
  val DE = "Bier"
  val FR = "biere"

  "fallback logic for get(Locale...)" in {
    val s = new LocalizedString(Map(Locale.ENGLISH -> EN, Locale.FRENCH -> FR, Locale.GERMAN -> DE))
    s.get(Locale.ENGLISH, Locale.FRENCH) must be(EN)
    s.get(Locale.ITALIAN, Locale.ENGLISH) must be(EN)
    s.get(Locale.FRENCH, Locale.ENGLISH) must be(FR)
  }

}
