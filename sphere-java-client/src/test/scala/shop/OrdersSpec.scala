package de.commercetools.sphere.client
package shop

import java.util.Currency

import de.commercetools.internal.CommandBase
import de.commercetools.internal.CartCommands
import de.commercetools.internal.OrderCommands
import de.commercetools.internal.QueryRequestImpl
import de.commercetools.internal.CommandRequestImpl
import de.commercetools.sphere.client.shop.model._
import de.commercetools.sphere.client.util.CommandRequest
import de.commercetools.sphere.client.model.QueryResult
import de.commercetools.internal.util.Util

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class OrdersSpec extends WordSpec with MustMatchers  {

  lazy val EUR = Currency.getInstance("EUR")

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

  val orderShopClient = Mocks.mockShopClient(orderJson)

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(req: Request[Order]) = req.asInstanceOf[RequestImpl[Order]]
  private def asImplQ(req: Request[QueryResult[Order]]) = req.asInstanceOf[RequestImpl[QueryResult[Order]]]
  private def asImpl(req: CommandRequest[Order]) = req.asInstanceOf[CommandRequestImpl[Order]]

  private def checkIdAndVersion(cmd: CommandBase): Unit = {
    cmd.getId() must be (orderId)
    cmd.getVersion() must be (1)
  }

  "Get all orders" in {
    val shopClient = Mocks.mockShopClient("{}")
    shopClient.orders.all().fetch.getCount must be(0)
  }

  "Get order byId" in {
    val req = orderShopClient.orders.byId(orderId)
    asImpl(req).getRawUrl must be ("/orders/" + orderId)
    val order: Order = req.fetch()
    order.getId() must be(orderId)
  }

  "Get orders by customerId" in {
    val req = Mocks.mockShopClient("{}").orders.byCustomerId("custId")
    asImplQ(req).getRawUrl must be ("/orders/?where=" + Util.encodeUrl("customerId=custId"))
    req.fetch().getCount must be (0)
  }

  "Create order from cart" in {
    val req = asImpl(orderShopClient.carts.order(orderId, 1))
    req.getRawUrl must be("/carts/order")
    val cmd = req.getCommand.asInstanceOf[CartCommands.OrderCart]
    checkIdAndVersion(cmd)
    val order: Order = req.execute()
    order.getId() must be(orderId)
  }

  "Create order from cart with payment state" in {
    val req = asImpl(orderShopClient.carts.order(orderId, 1, PaymentState.Paid))
    req.getRawUrl must be("/carts/order")
    val cmd = req.getCommand.asInstanceOf[CartCommands.OrderCart]
    checkIdAndVersion(cmd)
    cmd.getPaymentState() must be (PaymentState.Paid)
    val order: Order = req.execute()
    order.getId() must be(orderId)
  }

  "Set order shipment state" in {
    val req = asImpl(orderShopClient.orders().updateShipmentState(orderId, 1, ShipmentState.Shipped))
    req.getRawUrl must be("/orders/shipment-state")
    val cmd = req.getCommand.asInstanceOf[OrderCommands.UpdateShipmentState]
    checkIdAndVersion(cmd)
    val order: Order = req.execute()
    order.getId() must be(orderId)
  }

  "Set order payment state" in {
    val req = asImpl(orderShopClient.orders().updatePaymentState(orderId, 1, PaymentState.Paid))
    req.getRawUrl must be("/orders/payment-state")
    val cmd = req.getCommand.asInstanceOf[OrderCommands.UpdatePaymentState]
    checkIdAndVersion(cmd)
    val order: Order = req.execute()
    order.getId() must be(orderId)
  }
}
