package io.sphere.sdk.client

import org.scalatest._
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.base.Optional
import scala.beans.BeanProperty
import com.typesafe.config.ConfigFactory
import java.util.concurrent.TimeUnit
import com.fasterxml.jackson.core.`type`.TypeReference

class SphereJavaClientSpec extends WordSpec with ShouldMatchers {

  case class Xyz(@BeanProperty id: String)

  class XyzService {
    def fetchById(id: String): Fetch[Xyz] = new Fetch[Xyz](new TypeReference[Xyz](){})
  }

  val config = ConfigFactory.load()

  "SPHERE.IO Java client" must {
    "serve fetch requests providing JSON" in {
      pending
      val client = new SphereJavaClientImpl(config, new HttpClientTestDouble {
        override def testDouble[T](requestable: Requestable[T]) = HttpResponse.of(200, """{"id":1}""")
      })
      val service = new XyzService
      val result: ListenableFuture[Optional[Xyz]] = client.execute(service.fetchById("1"))
      result.get().get() should be(Xyz("1"))
    }

    "serve fetch requests providing instance" in {
      val client = new SphereJavaClientImpl(config, new SphereRequestExecutorTestDouble {
        override def result[T](fetch: Fetch[T]): Optional[T] = Optional.of(Xyz("1")).asInstanceOf[Optional[T]]
      })
      val service = new XyzService
      val result: ListenableFuture[Optional[Xyz]] = client.execute(service.fetchById("1"))
      result.get().get() should be(Xyz("1"))
    }
  }
}