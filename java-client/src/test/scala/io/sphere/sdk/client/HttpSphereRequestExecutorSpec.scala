package io.sphere.sdk.client

import org.scalatest._
import com.typesafe.config.ConfigFactory
import io.sphere.client.exceptions.SphereBackendException
import io.sphere.internal.errors.SphereErrorResponse
import com.google.common.collect.Lists
import com.fasterxml.jackson.core.`type`.TypeReference

class HttpSphereRequestExecutorSpec extends WordSpec with ShouldMatchers with BeforeAndAfterAll {

  val config = ConfigFactory.load()
  val ClassName = classOf[SphereResultRaw[_]].getName

  val executor = new HttpSphereRequestExecutor(new HttpClientTestDouble {
    override def testDouble(requestable: Requestable): HttpResponse = null
  }, config)

  "HttpSphereRequestExecutor" must {
    s"convert valid fetch request into successful $ClassName" in {
      val result: SphereResultRaw[Xyz] = executor.requestToSphereResult(HttpResponse.of(200, """{"id":1}"""), new XyzService().fetchById("1"), Xyz.typeReference)
      result should be(SphereResultRaw.success(new Xyz("1")))
    }

    s"convert not found into error $ClassName" in {
      val result: SphereResultRaw[Xyz] = executor.requestToSphereResult(HttpResponse.of(404, """{"statusCode":404, "message":"not found"}"""), new XyzService().fetchById("1"), Xyz.typeReference)
      result should be(SphereResultRaw.error(new SphereBackendException("/", new SphereErrorResponse(404, "not found", Lists.newArrayList()))))
    }
  }

  override protected def afterAll {
    executor.close()
  }
}