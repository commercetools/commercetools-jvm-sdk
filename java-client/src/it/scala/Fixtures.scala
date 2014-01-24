package sphere

import com.neovisionaries.i18n.CountryCode._
import io.sphere.client.shop.model._
import io.sphere.client.shop.SphereClient
import java.util.{UUID, List}
import IntegrationTest._
import scala.collection.JavaConversions._
import sphere.IntegrationTest.Implicits._

object Fixtures {
  def allProducts(implicit client: SphereClient): List[Product] = client.products().all().fetch().getResults
  def oneProduct(implicit client: SphereClient) = allProducts(client)(0)
  def newCart(implicit client: SphereClient) = client.carts().createCart(EUR).execute()
  def newCartWithProduct(implicit client: SphereClient) = {
    val cart = newCart
    val update = new CartUpdate().addLineItem(1, oneProduct(client).getId).setShippingAddress(GermanAddress)
    client.carts().updateCart(cart.getIdAndVersion, update).execute()
  }
  def newOrderOf1Product(implicit client: SphereClient) = {
    client.orders().createOrder(newCartWithProduct.getIdAndVersion).execute()
  }
  def oneShippingMethod(implicit client: SphereClient) = client.shippingMethods.query.fetch.getResults.get(0)
  def newOrderWithShippingMethod(implicit client: SphereClient) = {
    val shippingMethodReference = ShippingMethod.reference(oneShippingMethod.getId)
    val cart = client.carts.updateCart(newCartWithProduct.getIdAndVersion, new CartUpdate().setShippingMethod(shippingMethodReference)).execute()
    client.orders().createOrder(cart.getIdAndVersion).execute()
  }
  val GermanAddress: Address = new Address(DE)
  val FranceAddress: Address = new Address(FR)
  val BelgianAddress: Address = new Address(BE)
  def newSupplyChannel(implicit client: SphereClient): Channel = {
    val key = "CHANNEL-" + randomString
    client.channels.create(key).execute()
  }
  def randomString() = UUID.randomUUID.toString
  def randomPassword() = randomString()
  def randomEmail() = "testmail-" + randomString() + "@test.commercetools.de"
  def customerName(): CustomerName = new CustomerName("Firstname", "Lastname")
  def newCustomer(implicit client: SphereClient): Customer = {
    val signupResult = client.customers().signUp(randomEmail(), randomPassword(), customerName).execute()
    signupResult.getCustomer
  }
}