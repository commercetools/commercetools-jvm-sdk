package io.sphere.sdk.client

import com.google.common.base.Optional
import io.sphere.sdk.queries.{PagedQueryResult, Query}

class TestClient(underlying: JavaClient) {
  def execute[I, R](fetch: Fetch[I, R]): Optional[I] = underlying.execute(fetch).get()

  def execute[I, R](query: Query[I, R]): PagedQueryResult[I] = underlying.execute(query).get()

  def execute[T, V](command: Command[T, V]): T = underlying.execute(command).get()

  def close() = underlying.close()
}
