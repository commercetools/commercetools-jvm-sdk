package sphere

import IntegrationTest.Implicits._
import io.sphere.client.shop.model._
import io.sphere.client.shop.model.PaymentState._
import io.sphere.client.shop.model.ShipmentState._
import scala.collection.JavaConversions._
import sphere.TestData._
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

    "add tracking data" in {
      val order = newOrderWithShippingMethod
      val data = new TrackingData("xlf56b", "SPHERE parcels")
      val updatedOrder = client.orders.updateOrder(order.getIdAndVersion, new OrderUpdate().addTrackingData(data)).execute()
      val actual = updatedOrder.getShippingInfo.getTrackingData.get(0)
      actual must be(data)
    }

    "add tracking data with empty carrier" in {
      val order = newOrderWithShippingMethod
      val data = new TrackingData("xlf56b")
      val updatedOrder = client.orders.updateOrder(order.getIdAndVersion, new OrderUpdate().addTrackingData(data)).execute()
      val actual = updatedOrder.getShippingInfo.getTrackingData.get(0)
      actual must be(new TrackingData("xlf56b", "", false))
    }
  }
}
