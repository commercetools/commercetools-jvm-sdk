package sphere

import org.scalatest._
import java.util.UUID
import io.sphere.client.shop.model.InventoryEntry
import sphere.IntegrationTest._
import org.joda.time.DateTime
import com.google.common.base.Optional

class InventoryIntegrationSpec extends WordSpec with MustMatchers {
  lazy val client = IntegrationTestClient()
  lazy val service = client.inventory
  val quantity1 = 500
  def randomSku() = "sku-" + UUID.randomUUID().toString
  def createEntry: InventoryEntry = service.createInventoryEntry(randomSku, quantity1).execute()

  "sphere client" must {
    "Add an inventory entry" in {
      val sku = randomSku
      val inventoryEntry = service.createInventoryEntry(sku, quantity1).execute()
      inventoryEntry.getSku  must be (sku)
      inventoryEntry.getQuantityOnStock must be (quantity1)
      inventoryEntry.getAvailableQuantity must be (quantity1)
    }

    "Add an inventory entry with restockableInDays and expectedDelivery" in {
      val sku = randomSku
      val quantityOnStock = 3
      val restockableInDays = 5
      val expectedDelivery = new DateTime().plusDays(4)
      val inventoryEntry = service.createInventoryEntry(sku, quantityOnStock, restockableInDays, expectedDelivery).execute()
      inventoryEntry.getQuantityOnStock must be (quantityOnStock)
      inventoryEntry.getAvailableQuantity must be (quantityOnStock)
      inventoryEntry.getSku must be (sku)
      inventoryEntry.getRestockableInDays must be (Optional.of(5))
      inventoryEntry.getExpectedDelivery.getMillis must be (expectedDelivery.getMillis)
    }

    "find an inventory entry by sku without having a supply channel" in {
      val createdEntry = createEntry
      val inventoryEntry = service.bySku(createdEntry.getSku).fetch.get
      inventoryEntry must beSimilar[InventoryEntry](createdEntry, _.getSku, _.getAvailableQuantity, _.getQuantityOnStock)
    }
  }

}
