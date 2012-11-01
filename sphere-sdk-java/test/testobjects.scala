package sphere

import de.commercetools.sphere.client.shop.model.{Cart, Order}

package testobjects {
  object TestOrder extends Order
  case class TestCart(id: String, version: Int) extends Cart(id, version)
}
