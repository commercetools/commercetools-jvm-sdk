package de.commercetools.sphere.client
package shop

import java.util.Currency

import de.commercetools.internal.util.Util
import de.commercetools.sphere.client.model.QueryResult
import de.commercetools.internal.command._
import de.commercetools.internal.request._
import de.commercetools.sphere.client.shop.model._

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class CartsSpec extends WordSpec with MustMatchers  {

  import JsonTestObjects._

  lazy val EUR = Currency.getInstance("EUR")

  val cartShopClient = MockShopClient.create(cartsResponse = FakeResponse(cartJson))

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(reqBuilder: QueryRequest[Cart]) = reqBuilder.asInstanceOf[QueryRequestImpl[Cart]]
  private def asImplQ(reqBuilder: QueryRequest[QueryResult[Cart]]) = reqBuilder.asInstanceOf[QueryRequestImpl[QueryResult[Cart]]]
  private def asImpl(reqBuilder: CommandRequest[Cart]) = reqBuilder.asInstanceOf[CommandRequestImpl[Cart]]

  private def checkIdAndVersion(cmd: CommandBase): Unit = {
    cmd.getId() must be (cartId)
    cmd.getVersion() must be (1)
  }

  "Get all carts" in {
    val shopClient = MockShopClient.create(cartsResponse = FakeResponse("{}"))
    shopClient.carts.all().fetch.getCount must be(0)
  }

  "Get cart by customerId" in {
    val customerId = "764c4d25-5d04-4999-8a73-0cf8570f0000"
    val req = cartShopClient.carts.byCustomer(customerId)
    asImpl(req).getRawUrl must be ("/carts/by-customer?customerId=" + customerId)
    val cart = req.fetch()
    cart.getId() must be(cartId)
  }

  "Get cart byId" in {
    val req = cartShopClient.carts.byId("764c4d25-5d04-4999-8a73-0cf8570f7599")
    asImpl(req).getRawUrl must be ("/carts/" + cartId)
    val cart = req.fetch()
    cart.getId() must be(cartId)
  }

  "Create cart" in {
    val req = asImpl(cartShopClient.carts.createCart(EUR))
    req.getRawUrl must be("/carts")
    val cmd = req.getCommand.asInstanceOf[CartCommands.CreateCart]
    cmd.getCurrency must be (EUR)
    val cart: Cart = req.execute()
    cart.getId() must be(cartId)
  }

  "Add line item" in {
    val req = asImpl(cartShopClient.carts.addLineItem(cartId, 1, "1234", "5678", 2, catalog))
    req.getRawUrl must be("/carts/line-items")
    val cmd = req.getCommand.asInstanceOf[CartCommands.AddLineItem]
    checkIdAndVersion(cmd)
    cmd.getProductId() must be ("1234")
    cmd.getQuantity() must be (2)
    val cart: Cart = req.execute()
    cart.getId() must be(cartId)
  }

  "Remove line item" in {
    val req = asImpl(cartShopClient.carts.removeLineItem(cartId, 1, "1234"))
    req.getRawUrl must be("/carts/line-items/remove")
    val cmd = req.getCommand.asInstanceOf[CartCommands.RemoveLineItem]
    checkIdAndVersion(cmd)
    cmd.getLineItemId() must be ("1234")
    val cart: Cart = req.execute()
    cart.getId() must be(cartId)
  }

  "Update line item quantity" in {
    val req = asImpl(cartShopClient.carts.updateLineItemQuantity(cartId, 1, "1234", 3))
    req.getRawUrl must be("/carts/line-items/quantity")
    val cmd = req.getCommand.asInstanceOf[CartCommands.UpdateLineItemQuantity]
    checkIdAndVersion(cmd)
    cmd.getLineItemId() must be ("1234")
    cmd.getQuantity() must be (3)
    val cart: Cart = req.execute()
    cart.getId() must be(cartId)
  }

  "Increase line item quantity" in {
    val req = asImpl(cartShopClient.carts.increaseLineItemQuantity(cartId, 1, "1234", 3))
    req.getRawUrl must be("/carts/line-items/increase-quantity")
    val cmd = req.getCommand.asInstanceOf[CartCommands.IncreaseLineItemQuantity]
    checkIdAndVersion(cmd)
    cmd.getLineItemId() must be ("1234")
    cmd.getQuantityAdded() must be (3)
    val cart: Cart = req.execute()
    cart.getId() must be(cartId)
  }

  "Decrease line item quantity" in {
    val req = asImpl(cartShopClient.carts.decreaseLineItemQuantity(cartId, 1, "1234", 3))
    req.getRawUrl must be("/carts/line-items/decrease-quantity")
    val cmd = req.getCommand.asInstanceOf[CartCommands.DecreaseLineItemQuantity]
    checkIdAndVersion(cmd)
    cmd.getLineItemId() must be ("1234")
    cmd.getQuantityRemoved() must be (3)
    val cart: Cart = req.execute()
    cart.getId() must be(cartId)
  }

  "Set shipping address" in {
    val req = asImpl(cartShopClient.carts.setShippingAddress(cartId, 1, "Berlin"))
    req.getRawUrl must be("/carts/shipping-address")
    val cmd = req.getCommand.asInstanceOf[CartCommands.SetShippingAddress]
    checkIdAndVersion(cmd)
    cmd.getAddress() must be ("Berlin")
    val cart: Cart = req.execute()
    cart.getId() must be(cartId)
  }

  "Login with anonymous cart" in {
    val cartShopClient = MockShopClient.create(cartsResponse = FakeResponse(loginResultJson))
    val req = cartShopClient.carts.loginWithAnonymousCart(cartId, 1, "em@ail.com", "secret")
      .asInstanceOf[CommandRequestImpl[LoginResult]]
    req.getRawUrl must be("/carts/login")
    val cmd = req.getCommand.asInstanceOf[CartCommands.LoginWithAnonymousCart]
    checkIdAndVersion(cmd)
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")

    val result: LoginResult = req.execute()
    result.getCart.getId() must be(cartId)
    result.getCustomer.getId() must be(customerId)
  }
}
