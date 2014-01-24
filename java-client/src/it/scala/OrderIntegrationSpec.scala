package sphere

import IntegrationTest.Implicits._
import io.sphere.client.shop.model._
import io.sphere.client.shop.model.PaymentState._
import io.sphere.client.shop.model.ShipmentState._
import scala.collection.JavaConversions._
import sphere.Fixtures._
import org.scalatest._
import io.sphere.client.shop.SphereClient


class OrderIntegrationSpec extends WordSpec with MustMatchers {
  implicit lazy val client: SphereClient = IntegrationTestClient()

  "sphere client" must {
    "change the payment state of an order" in {
      val order = newOrderOf1Product
      order.getPaymentState must be(null)
      val updatedOrder = client.orders.updateOrder(order.getIdAndVersion, new OrderUpdate().setPaymentState(CreditOwed)).execute()
      updatedOrder.getPaymentState must be(CreditOwed)
    }

    "change the shipment state of an order" in {
      val order = newOrderOf1Product
      order.getShipmentState must be (null)
      val updatedOrder = client.orders.updateOrder(order.getIdAndVersion, new OrderUpdate().setShipmentState(Shipped)).execute()
      updatedOrder.getShipmentState must be (Shipped)
    }
  }
}
