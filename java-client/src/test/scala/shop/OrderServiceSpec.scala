package io.sphere.client
package shop

import java.util.Currency

import io.sphere.client.shop.model._
import io.sphere.internal.util.Util
import io.sphere.internal.request._
import io.sphere.internal.command._

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import com.google.common.base.Optional

class OrderServiceSpec extends WordSpec with MustMatchers  {

  import JsonTestObjects._

  lazy val EUR = Currency.getInstance("EUR")

  val orderShopClient = MockShopClient.create(ordersResponse = FakeResponse(orderJson), cartsResponse = FakeResponse(orderJson))

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(req: FetchRequest[Order]) = req.asInstanceOf[FetchRequestImpl[Order]]
  private def asImpl(req: QueryRequest[Order]) = req.asInstanceOf[QueryRequestImpl[Order]]
  private def asImpl(req: CommandRequest[Order]) = req.asInstanceOf[CommandRequestImpl[Order]]

  private def checkIdAndVersion(cmd: CommandBase) {
    cmd.getId must be (orderId)
    cmd.getVersion must be (1)
  }

  "Get all orders" in {
    val shopClient = MockShopClient.create(ordersResponse = FakeResponse("{}"))
    shopClient.orders.all().fetch.getCount must be(0)
  }

  "Get order byId" in {
    val req = orderShopClient.orders.byId(orderId)
    asImpl(req).getRequestHolder.getUrl must be ("/orders/" + orderId)
    val order: Optional[Order] = req.fetch()
    order.get().getId must be(orderId)
  }

  "Get orders by customerId" in {
    val req = MockShopClient.create(ordersResponse = FakeResponse("{}")).orders.byCustomerId("custId")
    asImpl(req).getRequestHolder.getUrl must be ("/orders?where=" + Util.urlEncode("customerId=\"custId\""))
    req.fetch().getCount must be (0)
  }

  "Create order from cart" in {
    val req = asImpl(orderShopClient.carts.createOrder(orderId, 1))
    req.getRequestHolder.getUrl must be("/orders")
    val cmd = req.getCommand.asInstanceOf[CartCommands.OrderCart]
    checkIdAndVersion(cmd)
    val order: Order = req.execute()
    order.getId must be(orderId)
  }

  "Create order from cart with payment state" in {
    val req = asImpl(orderShopClient.carts.createOrder(orderId, 1, PaymentState.Paid))
    req.getRequestHolder.getUrl must be("/orders")
    val cmd = req.getCommand.asInstanceOf[CartCommands.OrderCart]
    checkIdAndVersion(cmd)
    cmd.getPaymentState must be (PaymentState.Paid)
    val order: Order = req.execute()
    order.getId must be(orderId)
  }

  "Set order shipment state" in {
    val req = asImpl(orderShopClient.orders.updateShipmentState(orderId, 1, ShipmentState.Shipped))
    req.getRequestHolder.getUrl must be("/orders/shipment-state")
    val cmd = req.getCommand.asInstanceOf[OrderCommands.UpdateShipmentState]
    checkIdAndVersion(cmd)
    val order: Order = req.execute()
    order.getId must be(orderId)
  }

  "Set order payment state" in {
    val req = asImpl(orderShopClient.orders().updatePaymentState(orderId, 1, PaymentState.Paid))
    req.getRequestHolder.getUrl must be("/orders/payment-state")
    val cmd = req.getCommand.asInstanceOf[OrderCommands.UpdatePaymentState]
    checkIdAndVersion(cmd)
    val order: Order = req.execute()
    order.getId must be(orderId)
  }
}
