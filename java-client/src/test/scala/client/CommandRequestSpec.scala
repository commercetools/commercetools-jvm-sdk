package io.sphere.client

import org.scalatest._
import scala.collection.JavaConverters._
import io.sphere.client.shop.JsonErrors._
import io.sphere.client.TestUtil._
import io.sphere.client.exceptions.{InvalidPasswordException, OutOfStockException}

class CommandRequestSpec extends WordSpec with MustMatchers {
  "Handle errors in async calls" in {
    val sphere = MockSphereClient.create(ordersResponse = FakeResponse(outOfStock, 400))
    val res = sphere.orders().createOrder(v1("cart-id")).executeAsync().get()
    res.isError must be (true)
    res.isSuccess must be (false)
    res.isError(classOf[OutOfStockException]) must be (true)
    res.getError(classOf[OutOfStockException]).getLineItemIds.asScala must be (List("l1", "l2"))
    res.isError(classOf[InvalidPasswordException]) must be (false)
    res.getError(classOf[InvalidPasswordException]) must be (null)
  }

  "Provide generic SphereBackendError" in {
    val sphere = MockSphereClient.create(ordersResponse = FakeResponse(priceChangedAndOutOfStock, 400))
    val res = sphere.orders().createOrder(v1("cart-id")).executeAsync().get()
    val sphereEx = res.getGenericError
    sphereEx.getStatusCode must be (400)
    sphereEx.getErrors.size must be (2)
    val outOfStockErr = sphereEx.getErrors.asScala.collect { case e: SphereError.OutOfStock => e }.head
    outOfStockErr.getLineItemIds.asScala must be (List("l1", "l2"))
    val priceChangedErr = sphereEx.getErrors.asScala.collect { case e: SphereError.PriceChanged => e }.head
    priceChangedErr.getLineItemIds.asScala must be (List("l3", "l4"))
  }
}
