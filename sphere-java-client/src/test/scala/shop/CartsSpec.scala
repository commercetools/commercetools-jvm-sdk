package de.commercetools.sphere.client
package shop

import de.commercetools.internal.RequestBuilderImpl
import de.commercetools.internal.CommandRequestBuilderImpl
import de.commercetools.sphere.client.shop.model.Cart
import de.commercetools.sphere.client.util.RequestBuilder
import de.commercetools.sphere.client.util.CommandRequestBuilder

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import java.util.Currency

class CartsSpec extends WordSpec with MustMatchers  {

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

  val cartShopClient = Mocks.mockShopClient(cartJson)

  // downcast to be able to test some request properties which are not public for shop developers
  private def cast(reqBuilder: RequestBuilder[Cart]) = reqBuilder.asInstanceOf[RequestBuilderImpl[Cart]]
  private def cast(reqBuilder: CommandRequestBuilder[Cart]) = reqBuilder.asInstanceOf[CommandRequestBuilderImpl[Cart]]

  "Get all carts" in {
    val shopClient = Mocks.mockShopClient("{}")
    shopClient.carts.all().fetch.getCount must be(0)
  }

  "Get cart byId" in {
    val builder = cartShopClient.carts.byId("764c4d25-5d04-4999-8a73-0cf8570f7599")
    cast(builder).getRawUrl must be ("/carts/" + cartId)
    val cart = builder.fetch()
    cart.getId() must be(cartId)
  }

  "Create cart" in {
    val builder = cartShopClient.carts.createCart(EUR)
    // use cast(builder).getBody for assertions
    // use cast(builder).getCommand for assertions
    val cart = builder.execute()
    cart.getId() must be(cartId)
  }

  "Add line item" in {
    val cart: Cart = cartShopClient.carts.addLineItem(cartId, "1", "1234", 2).execute()
    cart.getId() must be(cartId)
  }

  "Remove line item" in {
    val cart: Cart = cartShopClient.carts.removeLineItem(cartId, "1", "1234").execute()
    cart.getId() must be(cartId)
  }

  "Update line item quantity" in {
    val cart: Cart = cartShopClient.carts.updateLineItemQuantity(cartId, "1", "1234", 3).execute()
    cart.getId() must be(cartId)
  }

  "Set shipping address" in {
    val cart: Cart = cartShopClient.carts.setShippingAddress(cartId, "1", "Berlin").execute()
    cart.getId() must be(cartId)
  }

  "Set customer" in {
    val cart: Cart = cartShopClient.carts.setCustomer(cartId, "1", "123").execute()
    cart.getId() must be(cartId)
  }
}
