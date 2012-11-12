package de.commercetools.sphere.client
package shop

object JsonTestObjects {

  val cartId = "764c4d25-5d04-4999-8a73-0cf8570f7599"
  val cartJson = """{
          "type":"Cart",
          "id":"%s",
          "version":1,
          "createdAt":"2012-09-18T08:54:42.208Z",
          "lastModifiedAt":"2012-09-18T08:54:42.208Z",
          "lineItems":[],
          "cartState":"Active",
          "currency":"EUR"
      }""".format(cartId)

  val orderId = "764c4d25-5d04-4999-8a73-0cf8570f7599"
  val orderJson = """{
    "type":"Order",
    "id":"%s",
    "version":3,
    "createdAt":"2012-09-19T13:09:16.031Z",
    "lastModifiedAt":"2012-09-19T13:09:16.031Z",
    "orderState":"Open",
    "lineItems":[{"id":"e05246f2-aca8-41b2-9897-5797845279c8",
    "productId":"03d8ff2c-cfb5-4969-b44f-2d76614d35c7",
    "sku":"sku_BMW_116_Convertible_4_door",
    "name":"BMW 116 Convertible 4 door",
    "quantity":2,
    "price":{"currencyCode":"EUR","centAmount":1700000}}],
    "amountTotal":{"currencyCode":"EUR","centAmount":3400000}
    }""".format(orderId)

  val customerId = "764c4d25-5d04-4999-8a73-0cf8570f7601"
  val customerJson =
    """{
          "type":"Customer",
          "id":"%s",
          "version":0,
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
}
