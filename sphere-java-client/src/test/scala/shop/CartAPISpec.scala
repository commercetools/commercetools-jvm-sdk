package de.commercetools.sphere.client
package shop

import de.commercetools.sphere.client.shop.model.Cart

import java.util.Currency


class CartAPISpec extends MockedShopClientSpec {

  lazy val EUR = Currency.getInstance("EUR")

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

  val cartShopClient = mockShopClient(cartJson)

  "Get all carts" in {
    val shopClient = mockShopClient("{}")
    shopClient.getCarts.all().fetch.getCount must be(0)
  }

  "Get cart byId" in {
    val cart: Cart = cartShopClient.getCarts.byId("764c4d25-5d04-4999-8a73-0cf8570f7599").fetch
    cart.getId() must be(cartId)
  }

  "Create cart" in {
    val cart: Cart = cartShopClient.getCarts.createCart(EUR, null)
    cart.getId() must be(cartId)
  }

  "Add line item" in {
    val cart: Cart = cartShopClient.getCarts.addLineItem(cartId, "1", "1234", 2)
    cart.getId() must be(cartId)
  }

  "Remove line item" in {
    val cart: Cart = cartShopClient.getCarts.removeLineItem(cartId, "1", "1234")
    cart.getId() must be(cartId)
  }

  "Update line item quantity" in {
    val cart: Cart = cartShopClient.getCarts.updateLineItemQuantity(cartId, "1", "1234", 3)
    cart.getId() must be(cartId)
  }

  "Set shipping address" in {
    val cart: Cart = cartShopClient.getCarts.setShippingAddress(cartId, "1", "Berlin")
    cart.getId() must be(cartId)
  }

  "Set customer" in {
    val cart: Cart = cartShopClient.getCarts.setCustomer(cartId, "1", "123")
    cart.getId() must be(cartId)
  }
}
