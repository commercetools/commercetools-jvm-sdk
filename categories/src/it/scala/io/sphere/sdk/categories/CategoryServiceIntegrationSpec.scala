package io.sphere.sdk.categories

import io.sphere.sdk.client.SdkIntegrationTest
import org.scalatest._

class CategoryServiceIntegrationSpec extends WordSpec with ShouldMatchers with SdkIntegrationTest {

  classOf[CategoryServiceImpl].getName must {
    "query categories" in {
      val pagedQueryResult = client.execute(new CategoryQuery).get
      pagedQueryResult.getCount should be >(3)
      pagedQueryResult.getTotal should be >(3)
      pagedQueryResult.getOffset should be (0)
      pagedQueryResult.getResults.get(0).getId.length should be >(5)
    }
  }
}
