package sphere

import io.sphere.client.shop.model.{Cart, Order, Customer, Review, Comment}
import io.sphere.client.shop.model.CustomerToken

package testobjects {

object TestOrder extends Order
  case class TestCart(id: String, version: Int) extends Cart(id, version)
  case class TestCustomer(id: String, version: Int) extends Customer(id, version)
  case class TestCustomerToken(value: String) extends CustomerToken
  case class TestComment(id: String, version: Int) extends Comment
  case class TestReview(id: String, version: Int) extends Review
}
