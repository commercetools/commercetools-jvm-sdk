package io.sphere.sdk.client

import org.scalatest.{Suite, BeforeAndAfterAll}


trait SdkIntegrationTest extends BeforeAndAfterAll {
  this: Suite =>

  var client: SphereJavaClient = _

  override protected def beforeAll() {
    client = new SphereJavaClientImpl(IntegrationTestUtils.defaultConfig)
  }


  override protected def afterAll() {
    client.close()
  }
}
