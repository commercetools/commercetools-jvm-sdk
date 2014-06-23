package io.sphere.sdk.client

import com.fasterxml.jackson.core.`type`.TypeReference

class XyzService {
  def fetchById(id: String): Fetch[Xyz] = new FetchImpl[Xyz] {
    override protected def typeReference() = new TypeReference[Xyz]() {}

    override def httpRequest() = HttpRequest.of(HttpMethod.GET, "/")
  }
}