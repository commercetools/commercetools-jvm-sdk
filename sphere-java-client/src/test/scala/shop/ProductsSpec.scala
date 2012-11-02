package de.commercetools.sphere.client
package shop

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import collection.JavaConverters._

class ProductsSpec extends WordSpec with MustMatchers {

  private val productsJson = """{
    "offset" : 0,
    "count" :2,
    "total" : 2,
    "results" : [ %s, %s ]
  }""" format (productJson("prod111", List("cat-sports", "cat-Deleted", "cat-V6")), productJson("prod222", List()))

  private def productJson(id: String, categoryIds: List[String]) = {
    val categoriesJson =  categoryIds.map(catId => """{
      "typeId" : "category",
      "id" : "%s"
    }""" format catId).mkString(", ")
    """{
      "masterVariant" : {
        "id" : "03f1f3c3-5731-4f98-8db0-6e88cd8c6d5d",
        "sku" : "sku_BMW_116_Convertible_4_door",
        "price" : {
          "currencyCode" : "EUR",
          "centAmount" : 1700000
        },
        "imageURLs" : [ ],
        "attributes" : [ {
          "name" : "cost",
          "value" : {
            "currencyCode" : "EUR",
            "centAmount" : 1650000
          }
          }, {
            "name" : "tags",
            "value" : "convertible"
          } ]
      },
      "id" : "%s",
      "version" : 1,
      "productType" : {
        "typeId" : "productdef",
        "id" : "a850910e-83e6-4ae5-a606-be9c651104e6"
      },
      "name" : "BMW 116",
      "description" : "Great convertible car.",
      "categories" : [ %s ],
      "vendor" : {
        "typeId" : "vendor",
        "id" : "e2f25691-c1aa-4726-bc55-3888b1296214"
      },
      "slug" : "bmw_116_convertible_4_door",
      "variants" : [ ]
    }""" format(id, categoriesJson)
  }

  private val categoriesJson =
    """{
    "offset" : 0,
    "count" : 3,
    "total" : 3,
    "results" : [ {
        "id" : "cat-sports", "version" : 1, "name" : "Sports cars", "ancestors" : [ ]
      }, {
        "id" : "cat-convertibles", "version" : 5, "name" : "Convertibles", "ancestors" : [ ]
      }, {
        "id" : "cat-V6",
        "version" : 2,
        "name" : "V6",
        "ancestors" : [ { "typeId" : "category", "id" : "cat-sports"} ],
        "parent" : { "typeId" : "category", "id" : "cat-sports" }
      }
    ]
  }"""

  val shopClient = MockShopClient.create(
    productsResponse = FakeResponse(productsJson),
    categoriesResponse = FakeResponse(categoriesJson))

  "Parse zero products" in {
    val searchResult = MockShopClient.create(productsResponse = FakeResponse("{}")).products.all().fetch
    searchResult.getCount must be(0)
    searchResult.getOffset must be(0)
    searchResult.getResults.size must be(0)
  }

  "Parse two products and resolve categories" in {
    val searchResult = shopClient.products.all().fetch
    searchResult.getCount must be(2)
    searchResult.getOffset must be(0)
    searchResult.getResults.size must be(2)
    val prod1 = searchResult.getResults.get(0)
    prod1.getCategories.asScala.toList.map(_.getName) must be(List("Sports cars", "V6"))
    val prod2 = searchResult.getResults.get(1)
    prod2.getCategories.size must be(0)
  }
}
