package sphere

import java.util.Currency

import de.commercetools.sphere.client.shop.model.{Cart, Order, Customer}

package testobjects {
  object TestOrder extends Order
  case class TestCart(id: String, version: Int) extends Cart(id, version)
  case class TestCustomer(id: String, version: Int) extends Customer(id, version)
}
