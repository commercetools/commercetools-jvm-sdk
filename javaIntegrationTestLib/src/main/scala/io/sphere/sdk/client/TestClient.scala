package io.sphere.sdk.client

import io.sphere.sdk.http.ClientRequest

class TestClient(underlying: JavaClient) {
  def execute[T](clientRequest: ClientRequest[T]): T = underlying.execute(clientRequest).get()

  def close() = underlying.close()
}
