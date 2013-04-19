package io.sphere.client
package shop

import java.util.Currency

import io.sphere.internal.command._
import io.sphere.internal.request._
import io.sphere.client.shop.model._
import io.sphere.internal.command.CartCommands._
import io.sphere.client.FakeResponse

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import com.neovisionaries.i18n.CountryCode

class CartServiceSpec extends WordSpec with MustMatchers  {

  import JsonTestObjects._

  lazy val EUR = Currency.getInstance("EUR")

  val cartShopClient = MockShopClient.create(cartsResponse = FakeResponse(cartJson))

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(reqBuilder: FetchRequest[Cart]) = reqBuilder.asInstanceOf[FetchRequestImpl[Cart]]
  private def asImpl(reqBuilder: CommandRequest[Cart]) = reqBuilder.asInstanceOf[CommandRequestImpl[Cart]]

  private def checkIdAndVersion(cmd: CommandBase) {
    cmd.getId must be (cartId)
    cmd.getVersion must be (1)
  }

  "Get all carts" in {
    val shopClient = MockShopClient.create(cartsResponse = FakeResponse("{}"))
    shopClient.carts.all().fetch.getCount must be(0)
  }

  "Get cart by customerId" in {
    val customerId = "764c4d25-5d04-4999-8a73-0cf8570f0000"
    val req = cartShopClient.carts.byCustomer(customerId)
    asImpl(req).getRequestHolder.getUrl must be ("/carts/by-customer?customerId=" + customerId)
    val cart = req.fetch()
    cart.get.getId must be(cartId)
  }

  "Get cart byId" in {
    val req = cartShopClient.carts.byId("764c4d25-5d04-4999-8a73-0cf8570f7599")
    asImpl(req).getRequestHolder.getUrl must be ("/carts/" + cartId)
    val cart = req.fetch()
    cart.get.getId must be(cartId)
  }

  "Create cart" in {
    val req = asImpl(cartShopClient.carts.createCart(EUR, Cart.InventoryMode.None))
    req.getRequestHolder.getUrl must be("/carts")
    val cmd = req.getCommand.asInstanceOf[CartCommands.CreateCart]
    cmd.getCurrency must be (EUR)
    val cart: Cart = req.execute()
    cart.getId must be(cartId)
  }

  "Update" in {
    val address = new Address(CountryCode.DE)
    val update = new CartUpdate()
    update.addLineItem(1, "product1", "1")
    update.addLineItem(2, "product2")
    update.decreaseLineItemQuantity("lineItem1", 3)
    update.recalculate()
    update.removeLineItem("lineItem2")
    update.setBillingAddress(address)
    update.setCountry(CountryCode.DE)
    update.setCustomerEmail("em@ail.com")
    update.setLineItemQuantity("lineItem3", 4)
    update.setShippingAddress(address)

    val req = asImpl(cartShopClient.carts.updateCart(cartId, 1, update))
    req.getRequestHolder.getUrl must be(s"/carts/$cartId")
    val cmd = req.getCommand.asInstanceOf[UpdateCommand[CartUpdateAction]]
    cmd.getVersion must be (1)
    val actions = scala.collection.JavaConversions.asScalaBuffer((cmd.getActions)).toList
    actions.length must be (10)
    val a0 = actions(0).asInstanceOf[AddLineItem]
    a0.getProductId must be ("product1")
    a0.getVariantId must be (1)
    a0.getQuantity must be (1)
    val a1 = actions(1).asInstanceOf[AddLineItemFromMasterVariant]
    a1.getProductId must be ("product2")
    a1.getQuantity must be (2)
    val a2 = actions(2).asInstanceOf[DecreaseLineItemQuantity]
    a2.getQuantity must be (3)
    a2.getLineItemId must be ("lineItem1")
    actions(3).asInstanceOf[RecalculateCartPrices]
    actions(4).asInstanceOf[RemoveLineItem].getLineItemId must be ("lineItem2")
    actions(5).asInstanceOf[SetBillingAddress].getAddress must be (address)
    actions(6).asInstanceOf[SetCountry].getCountry must be (CountryCode.DE)
    actions(7).asInstanceOf[SetCustomerEmail].getEmail must be ("em@ail.com")
    val a8 = actions(8).asInstanceOf[ChangeLineItemQuantity]
    a8.getLineItemId must be ("lineItem3")
    a8.getQuantity must be (4)
    actions(9).asInstanceOf[SetShippingAddress].getAddress must be (address)

    val cart: Cart = req.execute()
    cart.getId must be(cartId)
  }

  "Login with anonymous cart" in {
    val cartShopClient = MockShopClient.create(cartsResponse = FakeResponse(loginResultJson))
    val req = cartShopClient.carts.loginWithAnonymousCart(cartId, 1, "em@ail.com", "secret")
      .asInstanceOf[CommandRequestWithErrorHandling[AuthenticatedCustomerResult]]
    req.getRequestHolder.getUrl must be("/carts/login")
    val cmd = req.getCommand.asInstanceOf[CartCommands.LoginWithAnonymousCart]
    checkIdAndVersion(cmd)
    cmd.getEmail must be ("em@ail.com")
    cmd.getPassword must be ("secret")

    val result: AuthenticatedCustomerResult = req.execute().get
    result.getCart.getId must be(cartId)
    result.getCustomer.getId must be(customerId)
  }
}
