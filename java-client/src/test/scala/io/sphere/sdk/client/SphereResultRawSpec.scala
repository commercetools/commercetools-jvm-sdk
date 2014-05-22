package io.sphere.sdk.client

import org.scalatest._
import com.google.common.base.Function
import com.google.common.collect.Lists

class SphereResultRawSpec extends WordSpec with ShouldMatchers {

  val transformationFunction = new Function[String, Int] {
    override def apply(input: String): Int = input.length
  }

  classOf[SphereResultRaw[_]].getName must {
    "be mappable from successful to another successful result" in {
      SphereResultRaw.success("hi").transform(transformationFunction) should be(SphereResultRaw.success(2))
    }

    "be mappable from error" in {
      val error: SphereResultRaw[String] = SphereResultRaw.error(new SphereBackendException("/", new SphereErrorResponse(500, "foo", Lists.newArrayList())))
      error.transform(transformationFunction) should be(error)
    }
  }
}
