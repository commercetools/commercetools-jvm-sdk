package sphere

import io.sphere.client.shop.model.CartUpdate
import org.scalatest._
import IntegrationTest.Implicits._


class CustomLineItemIntegrationSpec extends WordSpec with MustMatchers {
  val client = IntegrationTestClient()

  "sphere client" must {
    "add custom line items" in {
      val cart = createCartWithCustumLineItem
      cart.getTotalPrice must be(EUR("12.00"))
      cart.getCustomLineItems.size must be (1)
      val item = cart.getCustomLineItems().get(0)
      item.getId must not be (null)
      item.getMoney must be (EUR("12.00"))
      item.getName must be (string2localizedString(name))
      item.getQuantity must be (1)
      item.getSlug must be (slug)
      item.getTaxCategory.getId must be (taxCategory.getId)
    }

    "remove custom line items" in {
      val cart = createCartWithCustumLineItem
      val lineItemId = cart.getCustomLineItems.get(0).getId
      val update = new CartUpdate().removeCustomLineItem(lineItemId)
      val updatedCart = client.carts().updateCart(cart.getIdAndVersion, update).execute()
      updatedCart.getCustomLineItems.isEmpty must be (true)
    }
  }

  val name = "cli 1"
  val slug = "cli-slug"

  def taxCategory = client.getTaxCategoryService.all().fetch().getResults.get(0) //random category is good enough

  def createCartWithCustumLineItem = {
    val cart = client.carts().createCart(EUR).execute()
    val update = new CartUpdate().addCustomLineItem(name, EUR("12.00"), slug, taxCategory.getReferenceId, 1)
    client.carts().updateCart(cart.getIdAndVersion, update).execute()
  }
}
