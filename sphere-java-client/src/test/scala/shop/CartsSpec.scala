package de.commercetools.sphere.client
package shop

import de.commercetools.internal.CommandBase
import de.commercetools.internal.CartCommands
import de.commercetools.internal.RequestBuilderImpl
import de.commercetools.internal.CommandRequestBuilderImpl
import de.commercetools.sphere.client.shop.model._
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
  private def asImpl(reqBuilder: RequestBuilder[Cart]) = reqBuilder.asInstanceOf[RequestBuilderImpl[Cart]]
  private def asImpl(reqBuilder: CommandRequestBuilder[Cart]) = reqBuilder.asInstanceOf[CommandRequestBuilderImpl[Cart]]

  private def checkIdAndVersion(cmd: CommandBase): Unit = {
    cmd.getId() must be (cartId)
    cmd.getVersion() must be (1)
  }

  "Get all carts" in {
    val shopClient = Mocks.mockShopClient("{}")
    shopClient.carts.all().fetch.getCount must be(0)
  }

  "Get cart byId" in {
    val reqBuilder = cartShopClient.carts.byId("764c4d25-5d04-4999-8a73-0cf8570f7599")
    asImpl(reqBuilder).getRawUrl must be ("/carts/" + cartId)
    val cart = reqBuilder.fetch()
    cart.getId() must be(cartId)
  }

  "Create cart" in {
    val reqBuilder = asImpl(cartShopClient.carts.createCart(EUR))
    reqBuilder.getRawUrl must be("/carts")
    val cmd = reqBuilder.getCommand.asInstanceOf[CartCommands.CreateCart]
    cmd.getCurrency must be (EUR)
    val cart: Cart = reqBuilder.execute()
    cart.getId() must be(cartId)
  }

  "Add line item" in {
    val reqBuilder = asImpl(cartShopClient.carts.addLineItem(cartId, 1, "1234", 2))
    reqBuilder.getRawUrl must be("/carts/line-items")
    val cmd = reqBuilder.getCommand.asInstanceOf[CartCommands.AddLineItem]
    checkIdAndVersion(cmd)
    cmd.getProductId() must be ("1234")
    cmd.getQuantity() must be (2)
    val cart: Cart = reqBuilder.execute()
    cart.getId() must be(cartId)
  }

  "Remove line item" in {
    val reqBuilder = asImpl(cartShopClient.carts.removeLineItem(cartId, 1, "1234"))
    reqBuilder.getRawUrl must be("/carts/line-items/remove")
    val cmd = reqBuilder.getCommand.asInstanceOf[CartCommands.RemoveLineItem]
    checkIdAndVersion(cmd)
    cmd.getLineItemId() must be ("1234")
    val cart: Cart = reqBuilder.execute()
    cart.getId() must be(cartId)
  }

  "Update line item quantity" in {
    val reqBuilder = asImpl(cartShopClient.carts.updateLineItemQuantity(cartId, 1, "1234", 3))
    reqBuilder.getRawUrl must be("/carts/line-items/quantity")
    val cmd = reqBuilder.getCommand.asInstanceOf[CartCommands.UpdateLineItemQuantity]
    checkIdAndVersion(cmd)
    cmd.getLineItemId() must be ("1234")
    cmd.getQuantity() must be (3)
    val cart: Cart = reqBuilder.execute()
    cart.getId() must be(cartId)
  }

  "Set shipping address" in {
    val reqBuilder = asImpl(cartShopClient.carts.setShippingAddress(cartId, 1, "Berlin"))
    reqBuilder.getRawUrl must be("/carts/shipping-address")
    val cmd = reqBuilder.getCommand.asInstanceOf[CartCommands.SetShippingAddress]
    checkIdAndVersion(cmd)
    cmd.getAddress() must be ("Berlin")
    val cart: Cart = reqBuilder.execute()
    cart.getId() must be(cartId)
  }

  "Set customer" in {
    val reqBuilder = asImpl(cartShopClient.carts.setCustomer(cartId, 1, "123"))
    reqBuilder.getRawUrl must be("/carts/customer")
    val cmd = reqBuilder.getCommand.asInstanceOf[CartCommands.SetCustomer]
    checkIdAndVersion(cmd)
    cmd.getCustomerId() must be ("123")
    val cart: Cart = reqBuilder.execute()
    cart.getId() must be(cartId)
  }
}
