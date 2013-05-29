package io.sphere.client
package shop

import org.scalatest._
import io.sphere.client.model.SearchResult

class SearchResultSpec extends WordSpec with MustMatchers {
  def searchRes(offset: Int, count: Int, total: Int, pageSize: Int) =
    new SearchResult(offset, count, total, null, null, pageSize)

  "SearchResult.getTotalPages" in {
    searchRes(offset = 0, count = 10, total = 100, pageSize = 10).getTotalPages must be (10)
    searchRes(offset = 0, count = 10, total = 105, pageSize = 10).getTotalPages must be (11)
    searchRes(offset = 0, count = 5, total = 105, pageSize = 10).getTotalPages must be (11)
    searchRes(offset = 70, count = 10, total = 105, pageSize = 10).getTotalPages must be (11)
    searchRes(offset = 100, count = 5, total = 105, pageSize = 20).getTotalPages must be (6)
  }

  "SearchResult.getCurrentPage" in {
    searchRes(offset = 0, count = 10, total = 100, pageSize = 10).getCurrentPage must be (0)
    searchRes(offset = 9, count = 10, total = 100, pageSize = 10).getCurrentPage must be (0)
    searchRes(offset = 10, count = 10, total = 100, pageSize = 10).getCurrentPage must be (1)
    searchRes(offset = 10, count = 10, total = 105, pageSize = 10).getCurrentPage must be (1)
    searchRes(offset = 100, count = 5, total = 105, pageSize = 10).getCurrentPage must be (10)
    searchRes(offset = 100, count = 5, total = 105, pageSize = 20).getCurrentPage must be (5)
  }
}
