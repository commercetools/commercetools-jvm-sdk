package io.sphere.client
package shop

import io.sphere.internal.request._
import model._

import io.sphere.internal.util.Util

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class InventoryServiceSpec extends WordSpec with MustMatchers {

  import JsonTestObjects._

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(req: FetchRequest[InventoryEntry]) = req.asInstanceOf[FetchRequestImpl[InventoryEntry]]
  private def asQueryReqImpl(req: FetchRequest[InventoryEntry]) =
    req.asInstanceOf[FetchRequestBasedOnQuery[InventoryEntry]]
      .getUnderlyingQueryRequest().asInstanceOf[QueryRequestImpl[InventoryEntry]].getRequestHolder

  "Get inventory byId" in {
    val inventoryShopClient = MockShopClient.create(inventoryResponse = FakeResponse(inventoryJson))
    val req = inventoryShopClient.inventory().byId(inventoryEntryId)
    asImpl(req).getRequestHolder.getUrl must be ("/inventory/" + inventoryEntryId)
    val entry = req.fetch()
    entry.get.getId must be(inventoryEntryId)
  }

  "Retrieving inventory by product id, variant id" must {
    val inventoryShopClient = MockShopClient.create(inventoryResponse = FakeResponse(queryResult(List(inventoryJson))))

    "set 'catalog IS NOT DEFINED' in the predicate when using byProductVariant()" in {
      val req = inventoryShopClient.inventory().byProductVariant(productId, "3")
      val queryReq = asQueryReqImpl(req)
      val expectedQueryPredicate = "productId=\"%s\" and variantId=3 and catalog IS NOT DEFINED".format(productId)
      queryReq.getUrl() must be ("/inventory?where=" + Util.urlEncode(expectedQueryPredicate))
      val entry = req.fetch()
      entry.get.getId must be(inventoryEntryId)
    }
  }
}
