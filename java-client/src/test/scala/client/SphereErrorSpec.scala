package io.sphere.client.model

import scala.collection.JavaConverters._
import org.scalatest._
import io.sphere.client._
import io.sphere.client.FakeResponse
import io.sphere.client.exceptions.SphereBackendException

class SphereErrorSpec extends WordSpec with MustMatchers {
  "parse InvalidJsonInput" in {
    val message = "Request body does not contain valid JSON."
    val detailedMessage = """xyz went wrong"""
    val body = s"""{"statusCode":400,"message":"$message","errors":[{"code":"InvalidJsonInput","message":"$message","detailedErrorMessage":"$detailedMessage"}]}"""
    val sphere = MockSphereClient.create(customObjectResponse =
      FakeResponse(body, 400))
    val error = intercept[SphereBackendException] {
      sphere.customObjects().set("c", "k", "v").execute()//this is valid, just to trigger a response
    }.getErrors.asScala.collect { case e: SphereError.InvalidJsonInput => e }.head
    error.getCode must be ("InvalidJsonInput")
    error.getMessage must be (message)
    error.getDetailedErrorMessage must be (detailedMessage)
  }
}
