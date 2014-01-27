package sphere

import IntegrationTest.Implicits._
import io.sphere.client.shop.model._
import io.sphere.client.shop.model.PaymentState._
import io.sphere.client.shop.model.ShipmentState._
import scala.collection.JavaConversions._
import sphere.Fixtures._
import org.scalatest._
import io.sphere.client.shop.SphereClient
import com.google.common.collect.Lists
import io.sphere.client.exceptions.SphereBackendException
import com.google.common.base.Optional


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

    def createDelivery(order: Order, items: java.util.List[DeliveryItem])(implicit client: SphereClient) = {
      order.getShippingInfo must not be (null)
      order.getShippingInfo.getDeliveries must have size(0)
      val updatedOrder = client.orders.updateOrder(order.getIdAndVersion, new OrderUpdate().addDelivery(items)).execute()
      updatedOrder.getShippingInfo.getDeliveries must have size(1)
      val delivery = updatedOrder.getShippingInfo.getDeliveries.get(0)
      delivery.getItems must be(items)
      (delivery, updatedOrder)
    }

    "add deliveries for line items" in {
      val order = newOrderWithShippingMethod
      val lineItem = order.getLineItems()(0)
      val items = Lists.newArrayList(new DeliveryItem(lineItem))
      createDelivery(order, items)
    }

    "add deliveries for custom line items" in {
      val order = newOrderWithCustomLineItem
      val customLineItem = order.getCustomLineItems()(0)
      val items = Lists.newArrayList(new DeliveryItem(customLineItem))
      createDelivery(order, items)
    }

    "handle error no shipping method set" in {
      val order = newOrderOf1Product
      order.getShippingInfo must be (null)//a good order has always a shipping method
      intercept[SphereBackendException](client.orders.updateOrder(order.getIdAndVersion, new OrderUpdate().addDelivery(Lists.newArrayList())).execute())
    }

    "add parcels to a delivery" in {
      val order = newOrderWithShippingMethod
      val lineItem = order.getLineItems()(0)
      val items = Lists.newArrayList(new DeliveryItem(lineItem))

      val (delivery, orderWithDelivery) = createDelivery(order, items)
      val measurements = Optional.of(Fixtures.parcelMeasurementsExample)
      val trackingData = Optional.of(Fixtures.trackingDataExample)
      val updatedOrder = client.orders.updateOrder(orderWithDelivery.getIdAndVersion, new OrderUpdate().
        addParcelToDelivery(delivery.getId, measurements, trackingData)).execute()
      updatedOrder.getShippingInfo.getDeliveries()(0).getParcels must have size(1)
      val parcel = updatedOrder.getShippingInfo.getDeliveries()(0).getParcels.get(0)
      parcel.getMeasurements must be (measurements)
      parcel.getTrackingData must be (trackingData)
    }
  }
}
