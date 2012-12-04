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

class ProductSpec extends WordSpec with MustMatchers  {
  def createProduct(withVariants: Boolean = true): Product = {
    def emptyList[A]= new util.ArrayList[A]
    val imageUrls = emptyList[String]

    val masterVariant = new Variant("standard", "sku", new Money(250*100, "EUR"), imageUrls, lst(
      new Attribute("color", "silver"), new Attribute("damage", 25), new Attribute("weight", 2.7)))

    val sniperScopeVariant = new Variant("sniper", "sku", new Money(290*100, "EUR"), imageUrls, lst(
      new Attribute("color", "translucent"), new Attribute("damage", 35), new Attribute("introduced", new LocalDate(2140, 8, 11))))

    val plasmaVariant = new Variant("plasma", "sku", new Money(400*100, "EUR"), imageUrls, lst(
      new Attribute("color", "translucent"), new Attribute("damage", 60), new Attribute("introduced", new LocalDate(2140, 11, 8))))

    val masterHeavyVariant = new Variant("standard-heavy", "sku", new Money(270*100, "EUR"), imageUrls, lst(
      new Attribute("color", "silver"), new Attribute("damage", 25), new Attribute("weight", 4.7)))

    val variants = if (withVariants) lst(masterHeavyVariant, sniperScopeVariant, plasmaVariant) else emptyList[Variant]

    new Product("id", 2, "Alien blaster", "Aliens are no more.", EmptyReference.create("no-one-sells-this-seriously?"),
      "alien-blaster", "meta1", "meta2", "meta3", 77, masterVariant, variants,
      emptyList, new util.HashSet[Reference[Catalog]](), EmptyReference.create("alien-catalog"), ReviewRating.empty())
  }

  "getAvailableAttributeValues" in {
    createProduct().getAvailableAttributeValues("color").asScala.toSet must be (Set("silver", "translucent"))
    createProduct().getAvailableAttributeValues("damage").asScala.toSet must be (Set(25, 35, 60))
    createProduct().getAvailableAttributeValues("introduced").asScala.toSet must be (Set(new LocalDate(2140, 8, 11), new LocalDate(2140, 11, 8)))

    createProduct(withVariants = false).getAvailableAttributeValues("color").asScala.toSet must be (Set("silver"))
    createProduct(withVariants = false).getAvailableAttributeValues("damage").asScala.toSet must be (Set(25))
    createProduct(withVariants = false).getAvailableAttributeValues("introduced").asScala.toSet must be (Set())
  }

  "getRelatedVariantClosest" in {
    val product = createProduct()
    val masterVariant = product.getMasterVariant
    val plasma = product.getRelatedVariantClosest(masterVariant, lst(new Attribute("damage", 60), new Attribute("color", "translucent")))
    plasma.getId must be("plasma")

    product.getRelatedVariantClosest(plasma, new Attribute("damage", 35)).getId must be("sniper")

    // Which variant is closer - the one with same color or the one with same damage? Damage wins, because it was asked for explicitly.
    product.getRelatedVariantClosest(plasma, lst(new Attribute("damage", 25), new Attribute("weight", 2.7))).getId must be("standard")

    product.getRelatedVariantClosest(masterVariant, lst(new Attribute("damage", 60), new Attribute("bogus-magazine", 720))).getId must be("plasma")
    product.getRelatedVariantClosest(masterVariant, lst(new Attribute("color", "translucent"), new Attribute("damage", 60))).getId must be("plasma")
    product.getRelatedVariantClosest(masterVariant, lst(new Attribute("color", "blue"), new Attribute("damage", 60))).getId must be("plasma")
    product.getRelatedVariantClosest(masterVariant, lst(new Attribute("color", "blue"), new Attribute("damage", 35))).getId must be("sniper")

    val product2 = createProduct(withVariants = false)
    val masterVariant2 = product2.getMasterVariant
    product2.getRelatedVariantClosest(masterVariant2, lst(new Attribute("damage", 60))).getId must be("standard")
    product2.getRelatedVariantClosest(masterVariant2, lst(new Attribute("damage", 60), new Attribute("bogus-magazine", 720))).getId must be("standard")
    product2.getRelatedVariantClosest(masterVariant2, lst(new Attribute("color", "translucent"), new Attribute("damage", 60))).getId must be("standard")
    product2.getRelatedVariantClosest(masterVariant2, lst(new Attribute("color", "blue"), new Attribute("damage", 60))).getId must be("standard")
    product2.getRelatedVariantClosest(masterVariant2, lst(new Attribute("color", "blue"), new Attribute("damage", 35))).getId must be("standard")
  }

  "getRelatedVariantExact" in {
    val product = createProduct()
    val masterVariant = product.getMasterVariant
    product.getRelatedVariantExact(masterVariant, new Attribute("weight", 4.7)).getId must be("standard-heavy")
    product.getRelatedVariantExact(masterVariant, lst(new Attribute("damage", 60))) must be (null)
    product.getRelatedVariantExact(masterVariant, lst(new Attribute("damage", 60), new Attribute("bogus-magazine", 720))) must be(null)
    product.getRelatedVariantExact(masterVariant, lst(new Attribute("color", "translucent"), new Attribute("damage", 60))) must be(null)
    product.getRelatedVariantExact(masterVariant, new Attribute("color", "blue")) must be(null)

    val product2 = createProduct(withVariants = false)
    val masterVariant2 = product2.getMasterVariant
    product.getRelatedVariantExact(masterVariant2, lst(new Attribute("damage", 60))) must be(null)
  }
}
