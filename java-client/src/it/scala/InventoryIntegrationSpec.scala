package sphere

import org.scalatest._
import io.sphere.client.shop.model.{SupplyChannelUpdate, InventoryEntryUpdate, InventoryEntry}
import sphere.IntegrationTest._
import org.joda.time.DateTime
import com.google.common.base.Optional
import io.sphere.client.exceptions.{SphereBackendException, DuplicateSkuException}
import io.sphere.client.shop.model.SupplyChannelRoles.InventorySupply
import sphere.TestData._
import io.sphere.client.model.{Reference, EmptyReference}
import scala.collection.JavaConversions._

class InventoryIntegrationSpec extends WordSpec with MustMatchers {
  implicit lazy val client = IntegrationTestClient()
  val quantity1 = 500
  def randomSku() = "sku-" + randomString
  def createEntry: InventoryEntry = client.inventory.createInventoryEntry(randomSku, quantity1).execute()

  val data = new AnyRef {
    val quantityOnStock = 3
    val restockableInDays = 5
    val expectedDelivery = new DateTime().plusDays(4)
  }

  "sphere client" must {
    "Add an inventory entry" in {
      val sku = randomSku
      val inventoryEntry = client.inventory.createInventoryEntry(sku, quantity1).execute()
      inventoryEntry.getSku  must be (sku)
      inventoryEntry.getQuantityOnStock must be (quantity1)
      inventoryEntry.getAvailableQuantity must be (quantity1)
    }

    "Add an inventory entry with restockableInDays and expectedDelivery" in {
      val sku = randomSku
      val quantityOnStock = 3
      val restockableInDays = 5
      val expectedDelivery = new DateTime().plusDays(4)
      val inventoryEntry = client.inventory.createInventoryEntry(sku, quantityOnStock, restockableInDays, expectedDelivery).execute()
      inventoryEntry.getQuantityOnStock must be (quantityOnStock)
      inventoryEntry.getAvailableQuantity must be (quantityOnStock)
      inventoryEntry.getSku must be (sku)
      inventoryEntry.getRestockableInDays must be (Optional.of(5))
      inventoryEntry.getExpectedDelivery.getMillis must be (expectedDelivery.getMillis)
      inventoryEntry.getSupplyChannel must be (EmptyReference.create("supplyChannel"))
    }

    "Add an inventory entry with supply channel" in {
      val supplyChannel = newSupplyChannel
      val sku = randomSku
      import data._
      val inventoryEntry = client.inventory.createInventoryEntry(sku, quantityOnStock, restockableInDays, expectedDelivery, supplyChannel.getId).execute()
      inventoryEntry.getQuantityOnStock must be (quantityOnStock)
      inventoryEntry.getAvailableQuantity must be (quantityOnStock)
      inventoryEntry.getSku must be (sku)
      inventoryEntry.getRestockableInDays must be (Optional.of(restockableInDays))
      inventoryEntry.getExpectedDelivery.getMillis must be (expectedDelivery.getMillis)
      inventoryEntry.getSupplyChannel must not be (Reference.create("supplyChannel", supplyChannel.getId))
    }

    "Catch error on add an inventory entry to not existing supply channel" in {
      import data._
      val exception = intercept[SphereBackendException]{
        client.inventory.createInventoryEntry(randomSku(), quantityOnStock, restockableInDays, expectedDelivery, randomString()).execute()
      }
      pending //should throw a more specific exception than SphereBackendException
    }

    "Don't allow to insert inventory with already existing SKU" in {
      val sku = randomSku
      def executeCreateCommand {
        client.inventory.createInventoryEntry(sku, 1).execute()
      }
      executeCreateCommand
      intercept[DuplicateSkuException] {
        executeCreateCommand
      }
    }

    "Don't allow to insert inventory with already existing SKU in the same supply channel" in {
      val sku = randomSku
      val supplyChannel = newSupplyChannel
      import data._
      def executeCreateCommand {
        client.inventory.createInventoryEntry(sku, quantityOnStock, restockableInDays, expectedDelivery, supplyChannel.getId).execute()
      }
      executeCreateCommand
      intercept[DuplicateSkuException] {
        executeCreateCommand
      }
    }

    "Don't allow to insert inventory with a channel that does not have InventorySupply role" in (pending)

    "Add stock to inventory entry" in {
      val inventoryEntry = client.inventory.createInventoryEntry(randomSku, 1).execute()
      inventoryEntry.getAvailableQuantity must be(1)
      val updatedInventoryEntry = client.inventory.updateInventoryEntry(inventoryEntry.getIdAndVersion,
        new InventoryEntryUpdate().addQuantity(4)).execute()
      updatedInventoryEntry.getAvailableQuantity must be(5)
    }

    "Remove stock from inventory entry" in {
      val inventoryEntry = client.inventory.createInventoryEntry(randomSku, 5).execute()
      inventoryEntry.getAvailableQuantity must be(5)
      val updatedInventoryEntry = client.inventory.updateInventoryEntry(inventoryEntry.getIdAndVersion,
        new InventoryEntryUpdate().removeQuantity(3)).execute()
      updatedInventoryEntry.getAvailableQuantity must be(2)
    }

    "Set restockableInDays" in {
      val inventoryEntry = client.inventory.createInventoryEntry(randomSku, 5).execute()
      inventoryEntry.getRestockableInDays must be(Optional.absent())
      val updatedInventoryEntry = client.inventory.updateInventoryEntry(inventoryEntry.getIdAndVersion,
        new InventoryEntryUpdate().setRestockableInDays(3)).execute()
      updatedInventoryEntry.getRestockableInDays must be(Optional.of(3))
    }

    "Set expectedDelivery" in {
      val inventoryEntry = client.inventory.createInventoryEntry(randomSku, 5).execute()
      inventoryEntry.getExpectedDelivery must be(null)
      val date = new DateTime().plusDays(3)
      val updatedInventoryEntry = client.inventory.updateInventoryEntry(inventoryEntry.getIdAndVersion,
        new InventoryEntryUpdate().setExpectedDelivery(date)).execute()
      updatedInventoryEntry.getExpectedDelivery.getMillis must be(date.getMillis)
    }

    "find an inventory entry by sku without having a supply channel" in {
      val createdEntry = createEntry
      val inventoryEntry = client.inventory.bySku(createdEntry.getSku).fetch.get
      inventoryEntry must beSimilar[InventoryEntry](createdEntry, _.getSku, _.getAvailableQuantity, _.getQuantityOnStock)
    }

    "Add a channel" in {
      val key = "CHANNEL-" + randomString
      val supplyChannel = client.supplyChannels.create(key).execute()
      supplyChannel.getKey must be(key)
      supplyChannel.getIdAndVersion must not be(null)
      supplyChannel.getRoles.get(0) must be(InventorySupply)
    }

    "Set a new key" in {
      val supplyChannel = newSupplyChannel
      val newKey = "CHANNEL-" + randomString
      val updated = client.supplyChannels().updateSupplyChannel(supplyChannel.getIdAndVersion,
        new SupplyChannelUpdate().changeKey(newKey)).execute()
      updated.getKey must be(newKey)
    }

    "Add a role to channel" in (pending)

    "Remove a role" in (pending)

    "Set roles" in (pending)
  }

}
