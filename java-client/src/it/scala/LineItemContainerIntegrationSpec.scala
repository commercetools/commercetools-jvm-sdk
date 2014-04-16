package sphere

import org.scalatest._
import IntegrationTest.Implicits._
import Fixtures._
import collection.JavaConversions._
import com.google.common.base.Optional


class LineItemContainerIntegrationSpec extends WordSpec with MustMatchers {
  implicit val client = IntegrationTestClient()

  "LineItemContainer" must {
    "provide getLineItemById(id)" in {
      val cart = newCartWithLineItems
      for (lineItem <- cart.getLineItems){
        val id = lineItem.getId
        cart.getLineItemById(id).get() must be (lineItem)
      }
      cart.getLineItemById("not-present") must be (Optional.absent())
      cart.getLineItems.size must be > 3
    }

    "provide getCustomLineItemById(id)" in {
      val cart = newCartWithCustomLineItems
      for (customLineItem <- cart.getCustomLineItems){
        val id = customLineItem.getId
        cart.getCustomLineItemById(id).get() must be (customLineItem)
      }
      cart.getCustomLineItemById("not-present") must be (Optional.absent())
      cart.getCustomLineItems.size must be > 3
    }
  }
}
