package io.sphere.client
package shop

import java.util.{Comparator, Locale}

import collection.JavaConverters._
import org.scalatest._
import io.sphere.client.shop.model.Category
import com.google.common.collect.ComparisonChain

class CategoryTreeSpec extends WordSpec with MustMatchers {
  private val sphere = MockSphereClient.create(categoriesResponse = FakeResponse(JsonResponses.categoriesJson))

  val EN = Locale.ENGLISH

  "CategoryTree.getRoots(comparator)" in {
    val expectedListAsc = List("Convertibles", "Sports cars")
    sphere.categories.getRoots(AscCategoryNameComparator).asScala.map(_.getName(EN)) must be(expectedListAsc)
    sphere.categories.getRoots(DescCategoryNameComparator).asScala.map(_.getName(EN)) must be(expectedListAsc.reverse)
  }

  "CategoryTree.getRoots" in {
    val categoryTree = sphere.categories()
    categoryTree.getRoots.asScala.toList.map(_.getName(EN)).sorted must be(List("Convertibles", "Sports cars"))
    val sportsCars = categoryTree.getById("id-sport")
    sportsCars.getId must be("id-sport")
    sportsCars.getIdAndVersion.getVersion must be(1)
    sportsCars.getName(EN) must be("Sports cars")
    sportsCars.getParent must be(null)
    sportsCars.getPathInTree.asScala.toList must be(List(sportsCars))
  }

  "CategoryTree.getChildren" in {
    val categoryTree = sphere.categories()
    val sportsCars = categoryTree.getById("id-sport")

    val v6v8 = sportsCars.getChildren.asScala.toList
    v6v8.map(_.getName(EN)).sorted must be(List("V6", "V8"))
    v6v8.foreach { c =>
      c.getIdAndVersion.getVersion must be (2)
      c.getParent must be (sportsCars)
      c.getPathInTree.asScala.toList must be(List(sportsCars, c))
    }

    val v8 = v6v8.find(_.getName == "V8").get
    val v8Children = v8.getChildren.asScala.toList
    v8Children.map(_.getName(EN)).sorted must be(List("Supercharger", "Turbocharger"))
    v8Children.foreach { v8Child =>
      v8Child.getParent must be (v8)
      v8Child.getPathInTree.asScala.toList must be(List(sportsCars, v8, v8Child))
    }
  }

  "CategoryTree.getById" in {
    val categoryTree = sphere.categories()
    categoryTree.getById("id-convert").getId must be("id-convert")
    categoryTree.getById("id-convert").getName(EN) must be("Convertibles")
    categoryTree.getById("id-super").getId must be("id-super")
    categoryTree.getById("id-super").getName(EN) must be("Supercharger")
    categoryTree.getById("bogus") must be(null)
  }

  "CategoryTree.getBySlug" in {
    val categoryTree = sphere.categories()
    categoryTree.getBySlug("v8").getName(EN) must be("V8")
    categoryTree.getBySlug("v8").getParent.getSlug must be("sports-cars")
    categoryTree.getBySlug("sports-cars").getName(EN) must be("Sports cars")
    categoryTree.getBySlug("bogus_slug") must be(null)
  }

  "CategoryTree.getBySlug (localized)" in {
    val categoryTree = sphere.categories()
    val germanLocale = Locale.forLanguageTag("de")
    categoryTree.getBySlug("v8-de", germanLocale).getName(EN) must be("V8")
    categoryTree.getBySlug("v8-de", germanLocale).getParent.getSlug must be("sports-cars")
    categoryTree.getBySlug("sports-cars-de", germanLocale).getName(EN) must be("Sports cars")
    categoryTree.getBySlug("bogus_slug-de", germanLocale) must be(null)
  }

  "CategoryTree.getAsFlatList" in {
    val categoryTree = sphere.categories()
    // must be sorted by name
    categoryTree.getAsFlatList.asScala.toList.sortBy(_.getName(EN)) must be(categoryTree.getAsFlatList.asScala.toList)
    categoryTree.getAsFlatList.asScala.length must be(6)
  }

  "Category.getId, getVersion, getName, getSlug, getDescription" in {
    val sportsCars = sphere.categories().getBySlug("sports-cars")
    sportsCars.getId must be("id-sport")
    sportsCars.getIdAndVersion.getVersion must be(1)
    sportsCars.getName(EN) must be("Sports cars")
    sportsCars.getSlug must be("sports-cars")
    sportsCars.getDescription must be("")
  }

  "Category.getParent, isRoot" in {
    val convertibles = sphere.categories().getBySlug("convertibles")
    convertibles.getParent must be(null)
    convertibles.isRoot must be(true)
    val v8 = sphere.categories().getBySlug("v8")
    v8.getParent.getName(EN) must be ("Sports cars")
    v8.isRoot must be (false)
  }

  "Category.getChildren" in {
    val v8 = sphere.categories().getBySlug("v8")
    v8.getChildren.asScala.map(_.getName(EN)).sorted.toList must be(List("Supercharger", "Turbocharger"))
  }

  "Category.getChildren(comparator)" in {
    val v8 = sphere.categories().getBySlug("v8")
    val expectedListAsc = List("Supercharger", "Turbocharger")

    v8.getChildren(AscCategoryNameComparator).asScala.map(_.getName(EN)) must be(expectedListAsc)
    v8.getChildren(DescCategoryNameComparator).asScala.map(_.getName(EN)) must be(expectedListAsc.reverse)
  }

  "Category.getPathInTree, getLevel" in {
    val supercharger = sphere.categories().getBySlug("supercharger")
    supercharger.getPathInTree.asScala.map(_.getName(EN)).toList must be(List("Sports cars", "V8", "Supercharger"))
    supercharger.getLevel must be(3)
    val convertibles = sphere.categories().getBySlug("convertibles")
    convertibles.getPathInTree.asScala.map(_.getName(EN)).toList must be(List("Convertibles"))
    convertibles.getLevel must be(1)
  }

  "Category.getOrderHint" in {
    sphere.categories().getById("id-sport").getOrderHint must be("0.123456")
  }

  val AscCategoryNameComparator = new Comparator[Category]() {
    override def compare(cat1: Category, cat2: Category): Int =
      ComparisonChain.start.compare(cat1.getName, cat2.getName).result()
  }

  val DescCategoryNameComparator = new Comparator[Category]() {
    override def compare(cat1: Category, cat2: Category): Int =
      ComparisonChain.start.compare(cat2.getName, cat1.getName).result()
  }
}
