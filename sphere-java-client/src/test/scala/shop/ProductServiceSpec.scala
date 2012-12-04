package de.commercetools.sphere.client
package shop

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import JsonTestObjects._
import collection.JavaConverters._

class ProductServiceSpec extends WordSpec with MustMatchers {

  val noProductsClient = MockShopClient.create(productsResponse = FakeResponse("{}"))

  val oneProductClient: ShopClient = {
    val productsJson = """{
      "offset" : 0,
      "count" : 1,
      "total" : 1,
      "results" : [ %s ]
    }""" format (
      productJson("prod1", List()))
    MockShopClient.create(
      productsResponse = FakeResponse(productsJson),
      categoriesResponse = FakeResponse(productCategoriesJson))
  }

  val twoProductsClient: ShopClient = {
    val productsJson = """{
      "offset" : 0,
      "count" :2,
      "total" : 2,
      "results" : [ %s, %s ]
    }""" format (
      productJson("prod111", List("cat-sports", "cat-Deleted", "cat-V6")),
      productJson("prod222", List()))
    MockShopClient.create(
      productsResponse = FakeResponse(productsJson),
      categoriesResponse = FakeResponse(productCategoriesJson))
  }


  "Parse zero products" in {
    val searchResult = noProductsClient.products.all.fetch
    searchResult.getCount must be(0)
    searchResult.getOffset must be(0)
    searchResult.getResults.size must be(0)
  }

  "Parse two products and resolve categories" in {
    val searchResult = twoProductsClient.products.all.fetch
    searchResult.getCount must be (2)
    searchResult.getOffset must be (0)
    searchResult.getResults.size must be (2)
    val prod1 = searchResult.getResults.get (0)
    prod1.getCategories.asScala.toList.map(_.getName) must be (List("Sports cars", "V6"))
    val prod2 = searchResult.getResults.get (1)
    prod2.getCategories.size must be (0)
  }

  "Get product by slug" in {
    val optionalProduct = oneProductClient.products.bySlug("bmw_116_convertible_4_door").fetch
    optionalProduct.isPresent must be (true)
    optionalProduct.get.getSlug must be ("bmw_116_convertible_4_door")
  }

  "Get product by wrong slug" in {
    val optionalProduct = noProductsClient.products.bySlug("bmw_116").fetch
    optionalProduct.isPresent must be (false)
  }

  "Get multiple products by slug" in {
    val optionalProduct = twoProductsClient.products.bySlug("bmw_116_convertible_4_door").fetch
    // if there are multiple products with the same slug (should never happen!), return the first one
    optionalProduct.isPresent must be (true)
    optionalProduct.get.getSlug must be ("bmw_116_convertible_4_door")
  }
}
