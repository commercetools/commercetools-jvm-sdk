package de.commercetools.sphere.client
package shop

import collection.JavaConverters._

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class CategoriesSpec extends WordSpec with MustMatchers {
  private val categoriesJson =
    """{
    "offset" : 0,
    "count" : 6,
    "total" : 6,
    "results" : [ {
      "id" : "ce28460f-bd22-4393-84f5-622e091a9f3e", "version" : 1, "name" : "Sports cars", "ancestors" : [ ]
    }, {
      "id" : "7c8e9332-22f2-4248-a2ed-5ee67b114c6f", "version" : 1, "name" : "Convertibles", "ancestors" : [ ]
    }, {
      "id" : "d70b4ab4-dcde-4eff-ab00-7a2eefd01fd3",
      "version" : 2,
      "name" : "V6",
      "ancestors" : [ { "typeId" : "category", "id" : "ce28460f-bd22-4393-84f5-622e091a9f3e"} ],
      "parent" : { "typeId" : "category", "id" : "ce28460f-bd22-4393-84f5-622e091a9f3e" }
    }, {
      "id" : "6701707d-0b5b-4c62-9ab2-5b8ca6938ab8",
      "version" : 2,
      "name" : "V8",
      "ancestors" : [ { "typeId" : "category", "id" : "ce28460f-bd22-4393-84f5-622e091a9f3e" } ],
      "parent" : { "typeId" : "category", "id" : "ce28460f-bd22-4393-84f5-622e091a9f3e" }
    }, {
      "id" : "19e4539b-356d-4ab5-882b-5e92fff27ba9",
      "version" : 2,
      "name" : "Supercharger",
      "ancestors" : [
         { "typeId" : "category", "id" : "ce28460f-bd22-4393-84f5-622e091a9f3e" },
         { "typeId" : "category", "id" : "6701707d-0b5b-4c62-9ab2-5b8ca6938ab8"} ],
      "parent" : { "typeId" : "category", "id" : "6701707d-0b5b-4c62-9ab2-5b8ca6938ab8" }
    }, {
      "id" : "20045803-b0c0-48c7-8913-cc40f0e1cd71",
      "version" : 2,
      "name" : "Turbocharger",
      "ancestors" : [
        { "typeId" : "category", "id" : "ce28460f-bd22-4393-84f5-622e091a9f3e"},
        { "typeId" : "category", "id" : "6701707d-0b5b-4c62-9ab2-5b8ca6938ab8" } ],
      "parent" : { "typeId" : "category", "id" : "6701707d-0b5b-4c62-9ab2-5b8ca6938ab8" }
    } ]
  }"""

  "CategoryTree.getRoots" in {
    val categoryTree = Mocks.mockShopClient(categoriesJson).categories()

    categoryTree.getRoots.asScala.toList.map(_.getName).sorted must be(List("Convertibles", "Sports cars"))
    val sportsCars = categoryTree.getById("ce28460f-bd22-4393-84f5-622e091a9f3e")
    sportsCars.getId must be("ce28460f-bd22-4393-84f5-622e091a9f3e")
    sportsCars.getVersion must be(1)
    sportsCars.getName must be("Sports cars")
    sportsCars.getParent must be(null)
    sportsCars.getPathInTree.asScala.toList must be(List(sportsCars))
  }

  "CategoryTree.getChildren" in {
    val categoryTree = Mocks.mockShopClient(categoriesJson).categories()
    val sportsCars = categoryTree.getById("ce28460f-bd22-4393-84f5-622e091a9f3e")

    val v6v8 = sportsCars.getChildren.asScala.toList
    v6v8.map(_.getName).sorted must be(List("V6", "V8"))
    v6v8.foreach { c =>
      c.getVersion must be (2)
      c.getParent must be (sportsCars)
      c.getPathInTree.asScala.toList must be(List(sportsCars, c))
    }

    val v8 = v6v8.find(_.getName == "V8").get
    val v8Children = v8.getChildren.asScala.toList
    v8Children.map(_.getName).sorted must be(List("Supercharger", "Turbocharger"))
    v8Children.foreach { v8Child =>
      v8Child.getParent must be (v8)
      v8Child.getPathInTree.asScala.toList must be(List(sportsCars, v8, v8Child))
    }
  }

  "CategoryTree.getById" in {
    val categoryTree = Mocks.mockShopClient(categoriesJson).categories()
    categoryTree.getById("ce28460f-bd22-4393-84f5-622e091a9f3e").getId must be("ce28460f-bd22-4393-84f5-622e091a9f3e")
    categoryTree.getById("ce28460f-bd22-4393-84f5-622e091a9f3e").getName must be("Sports cars")
    categoryTree.getById("19e4539b-356d-4ab5-882b-5e92fff27ba9").getId must be("19e4539b-356d-4ab5-882b-5e92fff27ba9")
    categoryTree.getById("19e4539b-356d-4ab5-882b-5e92fff27ba9").getName must be("Supercharger")
    categoryTree.getById("bogus") must be(null)
  }

  "CategoryTree.getBySlug" in {
    val categoryTree = Mocks.mockShopClient(categoriesJson).categories()
    categoryTree.getBySlug("v8").getName must be("V8")
    categoryTree.getBySlug("v8").getParent.getSlug must be("sports-cars")
    categoryTree.getBySlug("sports-cars").getName must be("Sports cars")
    categoryTree.getBySlug("bogus_slug") must be(null)
  }

  "CategoryTree.getAsFlatList" in {
    val categoryTree = Mocks.mockShopClient(categoriesJson).categories()
    // must be sorted by name
    categoryTree.getAsFlatList.asScala.toList.sortBy(_.getName) must be(categoryTree.getAsFlatList.asScala.toList)
    categoryTree.getAsFlatList.asScala.length must be(6)
  }
}
