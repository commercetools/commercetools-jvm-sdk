package io.sphere.client
package shop
package model

import TestUtil._
import io.sphere.client.model._
import scala.collection.JavaConverters._
import org.scalatest._
import org.joda.time.DateTime
import java.util
import util.Locale
import com.google.common.collect.{ImmutableMap, Maps}

/** See also [io.sphere.client.shop.ProductServiceSpec]. */
class ProductSpec extends WordSpec with MustMatchers  {

  val images = emptyList[Image]
  val DESC = "Aliens are no more."
  val NAME = "Alien blaster"

  def emptyList[A]= new util.ArrayList[A]
  def eur(amount: Double) = new Price(new Money(new java.math.BigDecimal(amount), "EUR"), null, null)
  def localized(s: String) = new LocalizedString(ImmutableMap.of(Locale.ENGLISH, s))

  def createAlienBlaster(withVariants: Boolean = true): Product = {
    val masterVariant = new Variant(1, "standard", lst(eur(250)), images, lst(
      new Attribute("color", "silver"),
      new Attribute("damage", 25),
      new Attribute("weight", 2.7)), null)

    val sniperScopeVariant = new Variant(2, "sniper", lst(eur(290)), images, lst(
      new Attribute("color", "translucent"),
      new Attribute("damage", 35),
      new Attribute("introduced", new DateTime(2140, 8, 11, 0, 0, 0))), null)

    val plasmaVariant = new Variant(3, "plasma", lst(eur(400)), images, lst(
      new Attribute("color", "translucent"),
      new Attribute("damage", 60),
      new Attribute("introduced", new DateTime(2140, 11, 8, 0, 0, 0))), null)

    val masterHeavyVariant = new Variant(4, "standard-heavy", lst(eur(270)), images, lst(
      new Attribute("color", "silver"),
      new Attribute("damage", 25),
      new Attribute("weight", 4.7)), null)

    val variants = if (withVariants) lst(masterHeavyVariant, sniperScopeVariant, plasmaVariant) else emptyList[Variant]

    new Product(VersionedId.create("id", 2), localized("Alien blaster"), localized(DESC), "alien-blaster",
      "meta1", "meta2", "meta3", masterVariant, variants,
      emptyList, new util.HashSet[Reference[Catalog]](), EmptyReference.create("alien-catalog"), ReviewRating.empty())
  }

  // helper for asserts
  case class StringAttr(name: String, value: String)
  object StringAttr { def apply(a: Attribute): StringAttr = StringAttr(a.getName, a.getString) }

  "getAttribute for missing attribute" in {
    val name = "introduced"
    createAlienBlaster().getAttribute(name) must be (null)
    createAlienBlaster().get(name) must be (null)
    createAlienBlaster().getString(name) must be ("")
    createAlienBlaster().getInt(name) must be (0)
    createAlienBlaster().getDouble(name) must be (0.0)
    createAlienBlaster().getMoney(name) must be (null)
    createAlienBlaster().getDateTime(name) must be (null)
  }

  "get I18n'd name" in {
    createAlienBlaster().getName.get(Locale.ENGLISH) must be(NAME)
    createAlienBlaster().getName.get(Locale.GERMAN) must be(NAME)
  }

  "getDescription" in {
    createAlienBlaster().getDescription.get(Locale.ENGLISH) must be(DESC)
    createAlienBlaster().getDescription.get(Locale.GERMAN) must be(DESC)
  }

  "getAttribute (string)" in {
    StringAttr(createAlienBlaster().getAttribute("color")) must be (StringAttr("color", "silver"))
    val a = createAlienBlaster().getAttribute("color")
    a.getName must be ("color")
    a.getValue must be ("silver")
    a.getString must be ("silver")
    a.getInt must be (0)
    a.getDouble must be (0.0)
    a.getMoney must be (null)
    a.getDateTime must be (null)
  }

  "getAttribute (delegation)" in {
    val p = createAlienBlaster()
    // Product
    p.getString("color") must be ("silver")
    p.getInt("color") must be (0)
    p.getDouble("color") must be (0.0)
    p.getMoney("color") must be (null)
    p.getDateTime("color") must be (null)
    // master Variant
    p.getMasterVariant.getString("color") must be ("silver")
    p.getMasterVariant.getInt("color") must be (0)
    p.getMasterVariant.getDouble("color") must be (0.0)
    p.getMasterVariant.getMoney("color") must be (null)
    p.getMasterVariant.getDateTime("color") must be (null)
  }

  "getAttribute (int)" in {
    val a2 = createAlienBlaster().getAttribute("damage")
    a2.getName must be ("damage")
    a2.getValue must be (25)
    a2.getString must be ("")
    a2.getInt must be (25)
    a2.getDouble must be (25.0)
    a2.getMoney must be (null)
    a2.getDateTime must be (null)
  }

  "VariantList.getAvailableVariantAttributes()" in {
    implicit val dateOrdering = new Ordering[DateTime] {
     override def compare(d1: DateTime, d2: DateTime): Int = d1.compareTo(d2)
    }

    createAlienBlaster().getVariants().getAvailableAttributes("color").asScala.map(StringAttr(_)).toList.sortBy(_.value) must be (List(
      StringAttr("color", "silver"), StringAttr("color", "translucent")))

    createAlienBlaster().getVariants().getAvailableAttributes("damage").asScala.map(_.getInt).toList.sorted must be (List(25, 35, 60))
    createAlienBlaster().getVariants().getAvailableAttributes("introduced").asScala.map(_.getDateTime).toList.sorted must be (List(
      new DateTime(2140, 8, 11, 0, 0, 0), new DateTime(2140, 11, 8, 0, 0, 0)))
    createAlienBlaster().getVariants().getAvailableAttributes("bogus").asScala.toList must be (List())

    createAlienBlaster(withVariants = false).getVariants().getAvailableAttributes("color").asScala.map(_.getString).toList.sorted must be (List("silver"))
    createAlienBlaster(withVariants = false).getVariants().getAvailableAttributes("damage").asScala.map(_.getInt).toList.sorted must be (List(25))
  }

  def createKelaBin(): Product = {
    val white28 = new Variant(1, "white-28", lst(eur(20)), images, lst(
      new Attribute("material", "Metall"),
      new Attribute("size", "28 cm"),
      new Attribute("taric", "73269098000"),
      new Attribute("weight", "0,581"),
      new Attribute("color", "weiß"),
      new Attribute("tax-type", 19),    // additional matching attributes to try to influence the selection in a wrong way
      new Attribute("cost-center", "Berlin"),
      new Attribute("restock", "no")
    ), null)
    val gray32 = new Variant(2, "gray-32", lst(eur(20)), images, lst(
      new Attribute("material", "Metall"),
      new Attribute("size", "32 cm"),
      new Attribute("taric", "73269098000"),
      new Attribute("weight", "0,581"),
      new Attribute("color", "grau"),
      new Attribute("filtercolor", "grau")
    ), null)
    val black32 = new Variant(3, "black-32", lst(eur(20)), images, lst(
      new Attribute("material", "Metall"),
      new Attribute("size", "32 cm"),
      new Attribute("taric", "73269098000"),
      new Attribute("weight", "0,581"),
      new Attribute("color", "schwarz"),
      new Attribute("filter-color", "schwarz"),
      new Attribute("tax-type", 19),
      new Attribute("cost-center", "Berlin"),
      new Attribute("restock", "no")
    ), null)
    val black28 = new Variant(4, "black-28", lst(eur(20)), images, lst(
      new Attribute("material", "Metall"),
      new Attribute("size", "28 cm"),
      new Attribute("taric", "73269098000"),
      new Attribute("weight", "0,429"),
      new Attribute("color", "schwarz"),
      new Attribute("surface", "pulverbeschichtet")
    ), null)
    new Product(VersionedId.create("id", 3), localized("One bin to rule them all"), localized("Kela"), "kela-kela",
      "meta1", "meta2", "meta3", black28, lst(gray32, black32, white28),
      emptyList, new util.HashSet[Reference[Catalog]](), EmptyReference.create("kela-stuff"), ReviewRating.empty())
  }

  // white 28 32, gray 28 32, black 28 32

  "VariantList.findByAttributes()" in {
    createKelaBin.getVariants().byAttributes(new Attribute("surface", "pulverbeschichtet")).first.get.getSKU must be ("black-28")
    createKelaBin.getVariants().byAttributes(new Attribute("surface", "flat")).size must be (0)
    createKelaBin.getVariants().byAttributes(new Attribute("surface", "flat")).first.isPresent must be (false)
    createKelaBin.getVariants().byAttributes(new Attribute("cost-center", "Berlin")).asList.asScala.map(_.getSKU).toSet must be (Set("white-28", "black-32"))
    createKelaBin.getVariants().byAttributes(new Attribute("cost-center", "Berlin")).size() must be (2)
    createKelaBin.getVariants().byAttributes(new Attribute("cost-center", "Berlin"), new Attribute("color", "schwarz")).first.get.getSKU must be ("black-32")
    createKelaBin.getVariants().byAttributes(new Attribute("cost-center", "Berlin"), new Attribute("color", "grau")).first.orNull must be (null)
    createKelaBin.getVariants().byAttributes(new Attribute("cost-center", "Berlin"), new Attribute("color", "grau")).size() must be (0)
  }

  "Variant.getAttribute()" in {
    val black32 = createKelaBin.getVariants.asList.get(2)
    black32.getAttribute("color").getName must be ("color")
    black32.getAttribute("color").getValue must be ("schwarz")
    black32.hasAttribute("color") must be (true)
    black32.getAttribute("smoothness") must be (null)
    black32.hasAttribute("smoothness") must be (false)
  }

  "Get related variant" in {
    val prod = createKelaBin
    val black32 = prod.getVariants.asList.get(2)
    black32.getSKU must be ("black-32")
    prod.getVariants().byAttributes(black32.getAttribute("color"), new Attribute("size", "32 cm")).first.get.getSKU must be ("black-32")
    prod.getVariants().byAttributes(black32.getAttribute("color"), new Attribute("size", "28 cm")).first.get.getSKU must be ("black-28")
  }
}
