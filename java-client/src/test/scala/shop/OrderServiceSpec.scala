package io.sphere.client
package shop

import java.util.Currency

import io.sphere.client.shop.model._
import io.sphere.internal.util.Util
import io.sphere.internal.request._
import io.sphere.internal.command._
import io.sphere.client.TestUtil._

import org.scalatest._
import com.google.common.base.Optional
import io.sphere.client.exceptions.{InvalidPasswordException, PriceChangedException, OutOfStockException}

class OrderServiceSpec extends WordSpec with MustMatchers  {

  import JsonResponses._

  lazy val EUR = Currency.getInstance("EUR")

  val sphere = MockSphereClient.create(ordersResponse = FakeResponse(orderJson), cartsResponse = FakeResponse(orderJson))

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(req: FetchRequest[Order]) = req.asInstanceOf[FetchRequestImpl[Order]]
  private def asImpl(req: QueryRequest[Order]) = req.asInstanceOf[QueryRequestImpl[Order]]
  private def asImpl(req: CommandRequest[Order]) = req.asInstanceOf[CommandRequestImpl[Order]]

  private def checkIdAndVersion(cmd: CommandBase) {
    cmd.getId must be (orderId)
    cmd.getVersion must be (1)
  }

  "Get all orders" in {
    val shopClient = MockSphereClient.create(ordersResponse = FakeResponse("{}"))
    shopClient.orders.query.fetch.getCount must be(0)
  }

  "Get order byId" in {
    val req = sphere.orders.byId(orderId)
    asImpl(req).getRequestHolder.getUrl must be ("/orders/" + orderId)
    val order: Optional[Order] = req.fetch()
    order.get().getId must be(orderId)
  }

  "Get orders by customerId" in {
    val req = MockSphereClient.create(ordersResponse = FakeResponse("{}")).orders.forCustomer("custId")
    asImpl(req).getRequestHolder.getUrl must be ("/orders?where=" + Util.urlEncode("customerId=\"custId\""))
    req.fetch().getCount must be (0)
  }

  "Create order from cart" in {
    val req = asImpl(sphere.orders.createOrder(v1(orderId)))
    req.getRequestHolder.getUrl must be("/orders")
    val cmd = req.getCommand.asInstanceOf[CartCommands.OrderCart]
    checkIdAndVersion(cmd)
    val order: Order = req.execute()
    order.getId must be(orderId)
  }

  "Create order from cart with payment state" in {
    val req = asImpl(sphere.orders.createOrder(v1(orderId), PaymentState.Paid))
    req.getRequestHolder.getUrl must be("/orders")
    val cmd = req.getCommand.asInstanceOf[CartCommands.OrderCart]
    checkIdAndVersion(cmd)
    cmd.getPaymentState must be (PaymentState.Paid)
    val order: Order = req.execute()
    order.getId must be(orderId)
  }

  "Set order shipment state" in {
    val req = asImpl(sphere.orders.updateShipmentState(v1(orderId), ShipmentState.Shipped))
    req.getRequestHolder.getUrl must be("/orders/shipment-state")
    val cmd = req.getCommand.asInstanceOf[OrderCommands.UpdateShipmentState]
    checkIdAndVersion(cmd)
    val order: Order = req.execute()
    order.getId must be(orderId)
  }

  "Set order payment state" in {
    val req = asImpl(sphere.orders().updatePaymentState(v1(orderId), PaymentState.Paid))
    req.getRequestHolder.getUrl must be("/orders/payment-state")
    val cmd = req.getCommand.asInstanceOf[OrderCommands.UpdatePaymentState]
    checkIdAndVersion(cmd)
    val order: Order = req.execute()
    order.getId must be(orderId)
  }

  // -----------------------
  // Error handling
  // -----------------------

  import JsonErrors._
  import scala.collection.JavaConverters._

  "createOrder" must {
    "handle PriceChanged" in {
      val sphere = MockSphereClient.create(ordersResponse = FakeResponse(priceChanged, 400))
      val e = intercept[PriceChangedException] {
        sphere.orders().createOrder(v1("cart-id")).execute()
      }
      e.getLineItemIds.asScala must be (List("l3", "l4"))
    }

    "handle OutOfStock" in {
      val sphere = MockSphereClient.create(ordersResponse = FakeResponse(outOfStock, 400))
      val req = sphere.orders().createOrder(v1("cart-id"))
      // Sync
      val e = intercept[OutOfStockException] {
        req.execute()
      }
      e.getLineItemIds.asScala must be (List("l1", "l2"))
      // re-execute
      intercept[OutOfStockException] {
        req.execute()
      }
    }

    "handle PriceChanged and OutOfStock, OutOfStock taking precedence" in {
      val sphere = MockSphereClient.create(ordersResponse = FakeResponse(priceChangedAndOutOfStock, 400))
      val req = sphere.orders().createOrder(v1("cart-id"))
      // Sync
      // OutOfStock takes precedence over PriceChanged
      val e = intercept[OutOfStockException] {
        req.execute()
      }
      e.getLineItemIds.asScala must be (List("l1", "l2"))
      // Async
      val res = req.executeAsync().get()
      res.isError must be (true)
      res.isSuccess must be (false)
      res.isError(classOf[OutOfStockException]) must be (true)
      res.getError(classOf[OutOfStockException]).getLineItemIds.asScala must be (List("l1", "l2"))
    }
  }
}
