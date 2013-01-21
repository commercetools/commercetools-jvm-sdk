package de.commercetools.sphere.client
package shop
package model

import de.commercetools.sphere.client.model._
import java.util
import org.joda.time.LocalDate
import scala.collection.JavaConverters._
import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import TestUtil._

/** See also [de.commercetools.sphere.client.shop.ProductServiceSpec]. */
class ProductSpec extends WordSpec with MustMatchers  {
  def emptyList[A]= new util.ArrayList[A]
  val imageUrls = emptyList[String]

  def createAlienBlaster(withVariants: Boolean = true): Product = {
    val masterVariant = new Variant(1, "standard", new Money(250*100, "EUR"), imageUrls, lst(
      new Attribute("color", "silver"),
      new Attribute("damage", 25),
      new Attribute("weight", 2.7)))

    val sniperScopeVariant = new Variant(2, "sniper", new Money(290*100, "EUR"), imageUrls, lst(
      new Attribute("color", "translucent"),
      new Attribute("damage", 35),
      new Attribute("introduced", new LocalDate(2140, 8, 11))))

    val plasmaVariant = new Variant(3, "plasma", new Money(400*100, "EUR"), imageUrls, lst(
      new Attribute("color", "translucent"),
      new Attribute("damage", 60),
      new Attribute("introduced", new LocalDate(2140, 11, 8))))

    val masterHeavyVariant = new Variant(4, "standard-heavy", new Money(270*100, "EUR"), imageUrls, lst(
      new Attribute("color", "silver"),
      new Attribute("damage", 25),
      new Attribute("weight", 4.7)))

    val variants = if (withVariants) lst(masterHeavyVariant, sniperScopeVariant, plasmaVariant) else emptyList[Variant]

    new Product("id", 2, "Alien blaster", "Aliens are no more.", EmptyReference.create("no-one-sells-this-seriously?"),
      "alien-blaster", "meta1", "meta2", "meta3", 77, masterVariant, variants,
      emptyList, new util.HashSet[Reference[Catalog]](), EmptyReference.create("alien-catalog"), ReviewRating.empty())
  }

  // helper for asserts
  case class StringAttr(name: String, value: String)
  object StringAttr { def apply(a: Attribute): StringAttr = StringAttr(a.getName, a.getString) }

  "getAttribute for missing attribute" in {
    val name = "introduced"
    createAlienBlaster().getAttribute(name) must be (null)
    createAlienBlaster().getValue(name) must be (null)
    createAlienBlaster().getString(name) must be ("")
    createAlienBlaster().getInt(name) must be (0)
    createAlienBlaster().getDouble(name) must be (0.0)
    createAlienBlaster().getMoney(name) must be (null)
    createAlienBlaster().getDate(name) must be (null)
    createAlienBlaster().getTime(name) must be (null)
    createAlienBlaster().getDateTime(name) must be (null)
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
    a.getDate must be (null)
    a.getTime must be (null)
    a.getDateTime must be (null)
  }

  "getAttribute (delegation)" in {
    val p = createAlienBlaster()
    // Product
    p.getString("color") must be ("silver")
    p.getInt("color") must be (0)
    p.getDouble("color") must be (0.0)
    p.getMoney("color") must be (null)
    p.getDate("color") must be (null)
    p.getTime("color") must be (null)
    p.getDateTime("color") must be (null)
    // master Variant
    p.getMasterVariant.getString("color") must be ("silver")
    p.getMasterVariant.getInt("color") must be (0)
    p.getMasterVariant.getDouble("color") must be (0.0)
    p.getMasterVariant.getMoney("color") must be (null)
    p.getMasterVariant.getDate("color") must be (null)
    p.getMasterVariant.getTime("color") must be (null)
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
    a2.getDate must be (null)
    a2.getTime must be (null)
    a2.getDateTime must be (null)
  }

  "getAvailableVariantAttributes" in {
    implicit val dateOrdering = new Ordering[LocalDate] {
     override def compare(d1: LocalDate, d2: LocalDate): Int = d1.compareTo(d2)
    }

    createAlienBlaster().getAvailableVariantAttributes("color").asScala.map(StringAttr(_)).toList.sortBy(_.value) must be (List(
      StringAttr("color", "silver"), StringAttr("color", "translucent")))

    createAlienBlaster().getAvailableVariantAttributes("damage").asScala.map(_.getInt).toList.sorted must be (List(25, 35, 60))
    createAlienBlaster().getAvailableVariantAttributes("introduced").asScala.map(_.getDate).toList.sorted must be (List(new LocalDate(2140, 8, 11), new LocalDate(2140, 11, 8)))
    createAlienBlaster().getAvailableVariantAttributes("bogus").asScala.toList must be (List())

    createAlienBlaster(withVariants = false).getAvailableVariantAttributes("color").asScala.map(_.getString).toList.sorted must be (List("silver"))
    createAlienBlaster(withVariants = false).getAvailableVariantAttributes("damage").asScala.map(_.getInt).toList.sorted must be (List(25))
  }

  def createKelaBin(): Product = {
    val white28 = new Variant(1, "white-28", new Money(20, "EUR"), imageUrls, lst(
      new Attribute("material", "Metall"),
      new Attribute("size", "28 cm"),
      new Attribute("taric", "73269098000"),
      new Attribute("weight", "0,581"),
      new Attribute("color", "weiß"),
      new Attribute("tax-type", 19),    // additional matching attributes to try to influence the selection in a wrong way
      new Attribute("cost-center", "Berlin"),
      new Attribute("restock", "no")
    ));
    val gray32 = new Variant(2, "gray-32", new Money(20, "EUR"), imageUrls, lst(
      new Attribute("material", "Metall"),
      new Attribute("size", "32 cm"),
      new Attribute("taric", "73269098000"),
      new Attribute("weight", "0,581"),
      new Attribute("color", "grau"),
      new Attribute("filtercolor", "grau")
    ))
    val black32 = new Variant(3, "black-32", new Money(20, "EUR"), imageUrls, lst(
      new Attribute("material", "Metall"),
      new Attribute("size", "32 cm"),
      new Attribute("taric", "73269098000"),
      new Attribute("weight", "0,581"),
      new Attribute("color", "schwarz"),
      new Attribute("filter-color", "schwarz"),
      new Attribute("tax-type", 19),
      new Attribute("cost-center", "Berlin"),
      new Attribute("restock", "no")
    ))
    val black28 = new Variant(4, "black-28", new Money(20, "EUR"), imageUrls, lst(
      new Attribute("material", "Metall"),
      new Attribute("size", "28 cm"),
      new Attribute("taric", "73269098000"),
      new Attribute("weight", "0,429"),
      new Attribute("color", "schwarz"),
      new Attribute("surface", "pulverbeschichtet")
    ))
    new Product("id", 3, "One bin to rule them all", "Kela", EmptyReference.create("get-this-at-your-local-kela"),
      "kela-kela", "meta1", "meta2", "meta3", 77, black28, lst(gray32, black32, white28),
      emptyList, new util.HashSet[Reference[Catalog]](), EmptyReference.create("kela-stuff"), ReviewRating.empty())
  }

  // white 28 32, gray 28 32, black 28 32

  "get variant" in {
    createKelaBin.getVariant(new Attribute("surface", "pulverbeschichtet")).getSKU must be ("black-28")
    createKelaBin.getVariant(new Attribute("surface", "flat")) must be (null)
    createKelaBin.getVariant(new Attribute("cost-center", "Berlin"), new Attribute("color", "schwarz")).getSKU must be ("black-32")
    createKelaBin.getVariant(new Attribute("cost-center", "Berlin"), new Attribute("color", "grau")) must be (null)
  }

  "get attribute" in {
    val black32 = createKelaBin.getVariants.get(2)
    black32.getAttribute("color").getName must be ("color")
    black32.getAttribute("color").getValue must be ("schwarz")
    black32.hasAttribute("color") must be (true)
    black32.getAttribute("smoothness") must be (null)
    black32.hasAttribute("smoothness") must be (false)
  }

  "get related variant" in {
    val prod = createKelaBin
    val black32 = prod.getVariants.get(2)
    black32.getSKU must be ("black-32")
    prod.getVariant(black32.getAttribute("color"), new Attribute("size", "32 cm")).getSKU must be ("black-32")
    prod.getVariant(black32.getAttribute("color"), new Attribute("size", "28 cm")).getSKU must be ("black-28")
  }
}
