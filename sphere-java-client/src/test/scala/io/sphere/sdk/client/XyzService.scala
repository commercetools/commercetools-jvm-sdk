package io.sphere.sdk.client

import com.fasterxml.jackson.core.`type`.TypeReference

class XyzService {
  def fetchById(id: String): Fetch[Xyz] = new Fetch[Xyz](new TypeReference[Xyz]() {}) {
    override def httpRequest(): HttpRequest = HttpRequest.of(HttpMethod.GET, "/")
  }
}