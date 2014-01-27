package sphere

import io.sphere.client.shop.model.CartUpdate
import org.scalatest._
import IntegrationTest.Implicits._


class CustomLineItemIntegrationSpec extends WordSpec with MustMatchers {
  implicit val client = IntegrationTestClient()
  val Name = "cli 1"
  val Slug = "cli-slug"
  
  "sphere client" must {
    "add custom line items" in {
      val cart = Fixtures.createCartWithCustomLineItem(Name, Slug)
      cart.getTotalPrice must be(EUR("12.00"))
      cart.getCustomLineItems.size must be (1)
      val item = cart.getCustomLineItems().get(0)
      item.getId must not be (null)
      item.getMoney must be (EUR("12.00"))
      item.getName must be (string2localizedString(Name))
      item.getQuantity must be (1)
      item.getSlug must be (Slug)
      item.getTaxCategory.getId must be (Fixtures.taxCategory.getId)
    }

    "remove custom line items" in {
      val cart = Fixtures.createCartWithCustomLineItem(Name, Slug)
      val lineItemId = cart.getCustomLineItems.get(0).getId
      val update = new CartUpdate().removeCustomLineItem(lineItemId)
      val updatedCart = client.carts().updateCart(cart.getIdAndVersion, update).execute()
      updatedCart.getCustomLineItems.isEmpty must be (true)
    }
  }
}
