package sphere

import org.scalatest._
import java.util.UUID
import io.sphere.client.shop.model.InventoryEntry

class InventoryIntegrationSpec extends WordSpec with MustMatchers {
  lazy val client = IntegrationTestClient()
  lazy val service = client.inventory
  val quantity1 = 500
  def randomSku() = "sku-" + UUID.randomUUID().toString
  def createEntry: InventoryEntry = service.createInventoryEntry(randomSku, quantity1).execute()

  "sphere client" must {
    "add an item to an inventory" in {
      val sku = randomSku
      val inventoryEntry = service.createInventoryEntry(sku, quantity1).execute()
      inventoryEntry.getSku  must be (sku)
      inventoryEntry.getQuantityOnStock must be (quantity1)
      inventoryEntry.getAvailableQuantity must be (quantity1)
    }

    "find an inventory entry by sku without having a supply channel" in {
      val createdEntry = createEntry
      val inventoryEntry = service.bySku(createdEntry.getSku).fetch.get
      inventoryEntry.getSku must be (createdEntry.getSku)
      inventoryEntry.getQuantityOnStock  must be (quantity1)
      inventoryEntry.getAvailableQuantity  must be (quantity1)
    }
  }

}
