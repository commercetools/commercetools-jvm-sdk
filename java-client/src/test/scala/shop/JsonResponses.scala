package io.sphere.client
package shop

import io.sphere.client.model.{Reference}
import io.sphere.client.shop.model.{ShippingMethod, CustomerGroup, Catalog}

import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.`type`.TypeReference

object JsonResponses {

  def queryResult(elements: List[String]): String = """{
    "offset":0,
    "count":%s,
    "total":%s,
    "results": [%s]
  }""".format(elements.size, elements.size, elements.mkString(","))

  // CategoryTreeSpec
  val categoriesJson =
    """{
    "offset" : 0,
    "count" : 6,
    "total" : 6,
    "results" : [ {
      "id" : "id-sport", "version" : 1, "name" : { "en" : "Sports cars" }, "ancestors" : [ ]
    }, {
      "id" : "id-convert", "version" : 1, "name" : { "en" : "Convertibles" }, "ancestors" : [ ]
    }, {
      "id" : "id-v6",
      "version" : 2,
      "name" : { "en": "V6" },
      "ancestors" : [ { "typeId" : "category", "id" : "id-sport"} ],
      "parent" : { "typeId" : "category", "id" : "id-sport" }
    }, {
      "id" : "id-v8",
      "version" : 2,
      "name" : { "en" : "V8" },
      "ancestors" : [ { "typeId" : "category", "id" : "id-sport" } ],
      "parent" : { "typeId" : "category", "id" : "id-sport" }
    }, {
      "id" : "id-super",
      "version" : 2,
      "name" : { "en" : "Supercharger" },
      "ancestors" : [
         { "typeId" : "category", "id" : "id-sport" },
         { "typeId" : "category", "id" : "id-v8"} ],
      "parent" : { "typeId" : "category", "id" : "id-v8" }
    }, {
      "id" : "id-turbo",
      "version" : 2,
      "name" : { "en" : "Turbocharger" },
      "ancestors" : [
        { "typeId" : "category", "id" : "id-sport"},
        { "typeId" : "category", "id" : "id-v8" } ],
      "parent" : { "typeId" : "category", "id" : "id-v8" }
    } ]
  }"""

  // ProductServiceSpec
  def productJson(id: String, categoryIds: List[String]) = {
    val categoriesJson =  categoryIds.map(catId => """{
      "typeId" : "category",
      "id" : "%s"
    }""" format catId).mkString(", ")
    """{
      "masterVariant" : {
        "id" : 1,
        "sku" : "sku_BMW_116_Convertible_4_door",
        "prices" : [{
          "value" : {
            "currencyCode" : "EUR",
            "centAmount" : 1700000
          }
        }],
        "images" : [ {
            "url" : "http://a016.rackcdn.com/snowboard.jpeg",
            "label" : "Snowboard",
            "dimensions" : {
              "w" : 500,
              "h" : 321
            }
          }, {
            "url" : "http://a016.rackcdn.com/snowboard-bigair.jpeg",
            "dimensions" : {
              "w" : 700,
              "h" : 421
            }
        } ],
        "attributes" : [ {
            "name" : "cost", "value" : { "currencyCode" : "EUR", "centAmount" : 1650000 }
          }, {
            "name" : "tags", "value" : "convertible"
          }, {
            "name": "numberAttributeWhole", "value": 1
          }, {
            "name": "numberAttributeFractional", "value": 1.2
          }, {
            "name": "dateAttribute", "value": "2013-06-24"
          }, {
            "name": "timeAttribute", "value": "16:54:10.000"
          }, {
            "name": "dateTimeAttribute", "value": "2013-06-24T16:54:10.000Z"
          }]
      },
      "id" : "%s",
      "version" : 1,
      "productType" : {
        "typeId" : "productdef",
        "id" : "a850910e-83e6-4ae5-a606-be9c651104e6"
      },
      "name" : {"en" : "BMW 116" },
      "description" : {"en" : "Great convertible car." },
      "categories" : [ %s ],
      "slug" : {"en" : "bmw_116_convertible_4_door"},
      "variants" : [{
        "id" : 2,
        "sku" : "sku_BMW_116_Convertible_4_door_luxury",
        "prices" : [{
          "value" : {
            "currencyCode" : "EUR",
            "centAmount" : 1950000
          }
        }],
        "images" : [ {
          "url" : "http://a016.rackcdn.com/snowboard-jump.jpeg",
          "label" : "Snowboard - jump over the BMW",
          "dimensions" : {
            "w" : 45,
            "h" : 170
          }
        } ],
        "attributes" : [ {
            "name" : "cost", "value" : { "currencyCode" : "EUR", "centAmount" : 2350000 }
          }, {
            "name" : "tags", "value" : "convertible"
          }, {
            "name": "numberAttributeWhole", "value": -2
          }, {
            "name": "localizedString", "value": { "en" : "english string" }
          }, {
            "name": "numberAttributeFractional", "value": -2.2
          }, {
            "name": "dateAttribute", "value": "2014-06-24"
          }, {
            "name": "timeAttribute", "value": "17:54:10.000"
          }, {
            "name": "dateTimeAttribute", "value": "2014-06-24T16:54:10.000Z"
        }]
      }]
    }""" format(id, categoriesJson)
  }
  val productCategoriesJson =
    """{
    "offset" : 0,
    "count" : 3,
    "total" : 3,
    "results" : [ {
        "id" : "cat-sports", "version" : 1, "name" : { "en" : "Sports cars" }, "ancestors" : [ ]
      }, {
        "id" : "cat-convertibles", "version" : 5, "name" : { "en" : "Convertibles" }, "ancestors" : [ ]
      }, {
        "id" : "cat-V6",
        "version" : 2,
        "name" : {"en" : "V6" },
        "ancestors" : [ { "typeId" : "category", "id" : "cat-sports"} ],
        "parent" : { "typeId" : "category", "id" : "cat-sports" }
      }
    ]
  }"""

  // CartServiceSpec
  val cartId = "764c4d25-5d04-4999-8a73-0cf8570f7599"
  val cartJson = """{
    "type":"Cart",
    "id":"%s",
    "version":1,
    "customerId":"7063eeff-c1a5-4c05-acf0-4489a1d0e970",
    "createdAt":"2012-11-20T13:22:41.953Z",
    "lastModifiedAt":"2012-11-20T13:22:42.052Z",
    "lineItems":[
      { "id":"6cb661fa-7505-4b3d-b423-bf9e8c8d9eff",
        "productId":"e64f0b72-8d0b-4eaf-ba3b-76b58d2cc730",
        "name":"test cart item",
        "variant":{
            "id":1,
            "price":{"currencyCode":"EUR","centAmount":1000},
            "imageURLs":[],
            "attributes":[]},
         "quantity":2},
      { "id":"c286fc14-aed2-41cf-913b-e09aaaf3c646",
        "productId":"db671ce6-c94e-4b5c-847c-e33940ee7333",
        "name":"name",
        "variant":{
            "id":2,
            "price":{"currencyCode":"EUR","centAmount":700},
            "imageURLs":[],
            "attributes":[]},
        "quantity":3}],
    "amountTotal":{"currencyCode":"EUR","centAmount":4100},
    "cartState":"Active",
    "currency":"EUR"}""".format(cartId)

  // OrderServiceSpec
  val orderId = "764c4d25-5d04-4999-8a73-0cf8570f7599"
  val orderJson = """{
    "type":"Order",
    "id":"%s",
    "version":3,
    "customerId":"19e9a9c0-ad5f-4304-bccd-4fe4087978f2",
    "createdAt":"2012-11-20T13:31:19.170Z",
    "lastModifiedAt":"2012-11-20T13:31:19.170Z",
    "orderState":"Open",
    "lineItems":[
      { "id":"7939f09d-9e45-4511-8a09-81f4bf1cb46a",
        "productId":"934fe888-8024-4fdb-a905-5d2558db5635",
        "name":"test cart item",
        "variant":{
          "id":1,
          "price":{"currencyCode":"EUR","centAmount":1000},
          "imageURLs":[],
          "attributes":[]},
          "quantity":2},
      { "id":"3f5cf0f2-89da-4c9c-97f4-c67a618934f2",
         "productId":"0ac52313-1c6d-49fd-8e3c-fbd6fce5d0c9",
         "name":"name",
         "variant":{
           "id":1,
           "price":{"currencyCode":"EUR","centAmount":700},
           "imageURLs":[],
           "attributes":[]},
         "quantity":3}],
    "amountTotal":{"currencyCode":"EUR","centAmount":4100}}""".format(orderId)

  // CustomerServiceSpec
  val customerId = "764c4d25-5d04-4999-8a73-0cf8570f7601"
  val customerJson = """{
        "type":"Customer",
        "id":"%s",
        "version":1,
        "email":"em@ail.com",
        "firstName":"hans",
        "lastName":"wurst",
        "password":"p75aPGdoBK62KSHuWcoWrw==$LMnb/9st6JhKFS0gBMx/zOBV3MVY+cbC2qBFR7aeutg=",
        "middleName":"the horrible",
        "title":"sir",
        "shippingAddresses":[]
     }""".format(customerId)

  val tokenValue = "uJ58PwYmpuw0MU4bEEViJRhd6cvVHrhqs8vQKZVj"
  val tokenJson = """{
        "id":"10b0a46c-27ed-4d19-a2b0-1497b86fac39",
        "customerId":"c8a2e4f6-f22d-4826-b3bb-48561089fc93",
        "createdAt":"2012-10-29T15:13:23.669Z",
        "expiresAt":"2012-10-29T15:18:23.669Z",
        "value":"%s"
     }""".format(tokenValue)

  val loginResultJson = """{"customer":%s, "cart":%s}""".format(customerJson, cartJson)

  val catalogJson = """{"typeId":"catalog","id":"20a11651-a4b5-4032-9a2f-6222ce1465ec"}"""
  val catalog: Reference[Catalog] =  (new ObjectMapper()).readValue(catalogJson, new TypeReference[Reference[Catalog]] {})

  val productId = "764c4d25-5d04-4999-8a73-0cf8570f7222"

  val reviewId = "764c4d25-5d04-4999-8a73-0cf8570f7123"
  val reviewAuthor = "Hans"
  val reviewTitle = "Test Review"
  val reviewText = "Amazing product."
  val reviewJson = """{
        "id":"%s",
        "version":1,
        "productId":"%s",
        "customerId":"%s",
        "authorName":"%s",
        "title":"%s",
        "text":"%s",
        "score":0.5,
        "createdAt":"2012-11-20T14:22:41.953Z",
        "lastModifiedAt":"2012-11-20T14:22:42.052Z"
     }""".format(reviewId, customerId, productId, reviewAuthor, reviewTitle, reviewText)

  val commentId = "764c4d25-5d04-4999-8a73-0cf8570f7777"
  val commentAuthor = "Fritz"
  val commentTitle = "Test Comment"
  val commentText = "Crap product."
  val commentJson = """{
        "id":"%s",
        "version":1,
        "productId":"%s",
        "customerId":"%s",
        "authorName":"%s",
        "title":"%s",
        "text":"%s",
        "createdAt":"2012-11-20T15:22:41.953Z",
        "lastModifiedAt":"2012-11-20T15:22:42.052Z"
     }""".format(commentId, customerId, productId, commentAuthor, commentTitle, commentText)

  //InventoryServiceSpec
  val inventoryEntryId = "764c4d25-5d04-4999-8a73-0cf8570f8888"
  val inventoryJson = """{
        "id":"%s",
        "version":1,
        "productId":"%s",
        "variantId":3,
        "quantityOnStock":1,
        "availableQuantity":1,
        "reservations":[]}""".format(inventoryEntryId, productId)

  val customerGroupJson = """{"typeId":"customer-group","id":"20a11651-a4b5-4032-9a2f-622288888888"}"""
  val customerGroup: Reference[CustomerGroup] =
    (new ObjectMapper()).readValue(customerGroupJson, new TypeReference[Reference[CustomerGroup]] {})

  val customerGroup2Json = """{"typeId":"customer-group","id":"20a11651-a4b5-4032-9a2f-622288889999"}"""
  val customerGroup2: Reference[CustomerGroup] =
    (new ObjectMapper()).readValue(customerGroup2Json, new TypeReference[Reference[CustomerGroup]] {})

  val zoneId = "11111111-5d04-4999-8a73-0cf857011112"
  val zoneRef = s"""{"typeId":"zone", "id":"$zoneId", "obj":{
    "id":"$zoneId",
    "version":2,
    "name":"Europe",
    "locations":[{"country":"DE"}, {"country":"FR"}]
  }}"""

  val taxCategoryJsonRef = """{"typeId":"tax-category", "id":"20a11651-a4b5-4032-9a2f-622288888888"}"""
  val shippingMethodId = "764c4d25-5d04-4999-8a73-0cf857011111"
  val shippingMethodJson =
    s"""{
         "id":"${shippingMethodId}",
         "version":1,
         "name":"DHL",
         "taxCategory":${taxCategoryJsonRef},
         "zoneRates":[{
           "zone":$zoneRef,
           "shippingRates":[{
             "price": { "currencyCode" : "EUR", "centAmount" : 1000 }
           }]
         }],
         "isDefault":false
    }"""
  val shippingMethod: ShippingMethod =
    (new ObjectMapper()).readValue(shippingMethodJson, new TypeReference[ShippingMethod] {})

}
