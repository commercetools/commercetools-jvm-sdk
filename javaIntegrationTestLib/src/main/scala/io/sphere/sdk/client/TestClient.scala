package io.sphere.sdk.client

class TestClient(underlying: JavaClient) {
  def execute[T](clientRequest: ClientRequest[T]): T = underlying.execute(clientRequest).get()

  def close() = underlying.close()
}
