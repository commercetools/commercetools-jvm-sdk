package sphere

import com.neovisionaries.i18n.CountryCode._
import io.sphere.client.shop.model._
import io.sphere.client.shop.SphereClient
import java.util.{UUID, List}
import IntegrationTest._
import scala.collection.JavaConversions._
import sphere.IntegrationTest.Implicits._
import io.sphere.client.SortDirection
import io.sphere.client.model.ReferenceId
import org.joda.time.DateTime

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
    val cart = client.carts.updateCart(newCartWithProduct.getIdAndVersion, new CartUpdate().setShippingMethod(shippingMethodReference)).execute()
    client.orders().createOrder(cart.getIdAndVersion).execute()
  }

  def shippingMethodReference(implicit client: SphereClient): ReferenceId[ShippingMethod] = {
    ShippingMethod.reference(oneShippingMethod.getId)
  }

  val GermanAddress: Address = new Address(DE)
  val FrenchAddress: Address = new Address(FR)
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

  def createCartWithCustomLineItem(name: String, slug: String)(implicit client: SphereClient) = {
    val cart = client.carts().createCart(EUR).execute()
    val update = new CartUpdate().addCustomLineItem(name, EUR("12.00"), slug, taxCategory.getReferenceId, 1).
      setShippingAddress(GermanAddress)
    client.carts().updateCart(cart.getIdAndVersion, update).execute()
  }

  def createCartWithCustomLineItemAndShippingMethod(name: String, slug: String)(implicit client: SphereClient) = {
    val cart = client.carts().createCart(EUR).execute()
    val update = new CartUpdate().addCustomLineItem(name, EUR("12.00"), slug, taxCategory.getReferenceId, 1).
      setShippingAddress(GermanAddress).setShippingMethod(shippingMethodReference)
    client.carts().updateCart(cart.getIdAndVersion, update).execute()
  }
  
  def newOrderWithCustomLineItem(implicit client: SphereClient) = {
    val cart = createCartWithCustomLineItemAndShippingMethod("custom line item name", "custom line item slug")
    client.orders().createOrder(cart.getIdAndVersion).execute()
  }

  def taxCategory(implicit client: SphereClient) = client.getTaxCategoryService.query.sort("name", SortDirection.ASC).fetch.getResults.get(0) //random category is good enough

  val parcelMeasurementsExample = new ParcelMeasurements(100, 100, 100, 100)

  val TrackingId = "Eff389"
  val Carrier = "UPS"
  val Provider = "shipcloud.io"
  val ProviderTransaction = "4864534784444"

  val trackingDataExample = new TrackingData.Builder().setTrackingId(TrackingId).
    setCarrier(Carrier).
    setProvider(Provider).
    setProviderTransaction(ProviderTransaction).
    build()

  def newChannel(key: String = randomString())(implicit client: SphereClient) = client.channels.create(key).execute()
  def newChannel(key: String, roles: java.util.Set[ChannelRoles])(implicit client: SphereClient) =
    client.channels.create(key, roles).execute()
  def newChannel(roles: java.util.Set[ChannelRoles])(implicit client: SphereClient): Channel = newChannel(randomString(), roles)
  def newInventoryEntry(sku: String, quantity: Int)(implicit client: SphereClient): InventoryEntry = client.inventory.createInventoryEntry(sku, quantity).execute()
  def newInventoryEntry(sku: String, quantity: Int, channel: Channel)(implicit client: SphereClient): InventoryEntry =
    client.inventory.createInventoryEntry(sku, quantity, 0, DateTime.now().plusDays(4), channel.getId).execute()
}