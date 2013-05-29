package io.sphere.client
package shop

import collection.JavaConverters._

import org.scalatest._

class CategoryTreeSpec extends WordSpec with MustMatchers {
  private val sphere = MockSphereClient.create(categoriesResponse = FakeResponse(JsonResponses.categoriesJson))

  "CategoryTree.getRoots" in {
    val categoryTree = sphere.categories()
    categoryTree.getRoots.asScala.toList.map(_.getName).sorted must be(List("Convertibles", "Sports cars"))
    val sportsCars = categoryTree.getById("id-sport")
    sportsCars.getId must be("id-sport")
    sportsCars.getIdAndVersion.getVersion must be(1)
    sportsCars.getName must be("Sports cars")
    sportsCars.getParent must be(null)
    sportsCars.getPathInTree.asScala.toList must be(List(sportsCars))
  }

  "CategoryTree.getChildren" in {
    val categoryTree = sphere.categories()
    val sportsCars = categoryTree.getById("id-sport")

    val v6v8 = sportsCars.getChildren.asScala.toList
    v6v8.map(_.getName).sorted must be(List("V6", "V8"))
    v6v8.foreach { c =>
      c.getIdAndVersion.getVersion must be (2)
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
    val categoryTree = sphere.categories()
    categoryTree.getById("id-convert").getId must be("id-convert")
    categoryTree.getById("id-convert").getName must be("Convertibles")
    categoryTree.getById("id-super").getId must be("id-super")
    categoryTree.getById("id-super").getName must be("Supercharger")
    categoryTree.getById("bogus") must be(null)
  }

  "CategoryTree.getBySlug" in {
    val categoryTree = sphere.categories()
    categoryTree.getBySlug("v8").getName must be("V8")
    categoryTree.getBySlug("v8").getParent.getSlug must be("sports-cars")
    categoryTree.getBySlug("sports-cars").getName must be("Sports cars")
    categoryTree.getBySlug("bogus_slug") must be(null)
  }

  "CategoryTree.getAsFlatList" in {
    val categoryTree = sphere.categories()
    // must be sorted by name
    categoryTree.getAsFlatList.asScala.toList.sortBy(_.getName) must be(categoryTree.getAsFlatList.asScala.toList)
    categoryTree.getAsFlatList.asScala.length must be(6)
  }

  "Category.getId, getVersion, getName, getSlug, getDescription" in {
    val sportsCars = sphere.categories().getBySlug("sports-cars")
    sportsCars.getId must be("id-sport")
    sportsCars.getIdAndVersion.getVersion must be(1)
    sportsCars.getName must be("Sports cars")
    sportsCars.getSlug must be("sports-cars")
    sportsCars.getDescription must be("")
  }

  "Category.getParent, isRoot" in {
    val convertibles = sphere.categories().getBySlug("convertibles")
    convertibles.getParent must be(null)
    convertibles.isRoot must be(true)
    val v8 = sphere.categories().getBySlug("v8")
    v8.getParent.getName must be ("Sports cars")
    v8.isRoot must be (false)
  }

  "Category.getChildren" in {
    val v8 = sphere.categories().getBySlug("v8")
    v8.getChildren.asScala.map(_.getName).sorted.toList must be(List("Supercharger", "Turbocharger"))
  }

  "Category.getPathInTree, getLevel" in {
    val supercharger = sphere.categories().getBySlug("supercharger")
    supercharger.getPathInTree.asScala.map(_.getName).toList must be(List("Sports cars", "V8", "Supercharger"))
    supercharger.getLevel must be(3)
    val convertibles = sphere.categories().getBySlug("convertibles")
    convertibles.getPathInTree.asScala.map(_.getName).toList must be(List("Convertibles"))
    convertibles.getLevel must be(1)
  }
}
