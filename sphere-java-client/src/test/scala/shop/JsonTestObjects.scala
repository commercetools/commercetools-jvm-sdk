package de.commercetools.sphere.client
package shop

import de.commercetools.sphere.client.model.Reference
import de.commercetools.sphere.client.shop.model.Catalog

import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.`type`.TypeReference

object JsonTestObjects {

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
            "id":"5da57705-0028-4844-980c-1a69e1ad1c3f",
            "price":{"currencyCode":"EUR","centAmount":1000},
            "imageURLs":[],
            "attributes":[]},
         "quantity":2},
      { "id":"c286fc14-aed2-41cf-913b-e09aaaf3c646",
        "productId":"db671ce6-c94e-4b5c-847c-e33940ee7333",
        "name":"name",
        "variant":{
            "id":"ff2403de-3026-43b2-900c-2b44c1a50c00",
            "price":{"currencyCode":"EUR","centAmount":700},
            "imageURLs":[],
            "attributes":[]},
        "quantity":3}],
    "amountTotal":{"currencyCode":"EUR","centAmount":4100},
    "cartState":"Active",
    "currency":"EUR"}""".format(cartId)

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
          "id":"a5076ca3-9049-40ed-8dcb-438940053149",
          "price":{"currencyCode":"EUR","centAmount":1000},
          "imageURLs":[],
          "attributes":[]},
          "quantity":2},
      { "id":"3f5cf0f2-89da-4c9c-97f4-c67a618934f2",
         "productId":"0ac52313-1c6d-49fd-8e3c-fbd6fce5d0c9",
         "name":"name",
         "variant":{
           "id":"4519fe7e-4923-4884-bd34-be19fb08f72c",
           "price":{"currencyCode":"EUR","centAmount":700},
           "imageURLs":[],
           "attributes":[]},
         "quantity":3}],
    "amountTotal":{"currencyCode":"EUR","centAmount":4100}}""".format(orderId)

  val customerId = "764c4d25-5d04-4999-8a73-0cf8570f7601"
  val customerJson =
    """{
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
  val tokenJson =
    """{
          "id":"10b0a46c-27ed-4d19-a2b0-1497b86fac39",
          "customerId":"c8a2e4f6-f22d-4826-b3bb-48561089fc93",
          "createdAt":"2012-10-29T15:13:23.669Z",
          "expiresAt":"2012-10-29T15:18:23.669Z",
          "value":"%s"
       }""".format(tokenValue)

  val loginResultJson = """{"customer":%s, "cart":%s}""".format(customerJson, cartJson)

  val catalogJson = """{"typeId":"catalog","id":"20a11651-a4b5-4032-9a2f-6222ce1465ec"}"""
  val catalog: Reference[Catalog] =  (new ObjectMapper()).readValue(catalogJson, new TypeReference[Reference[Catalog]] {})


}
