package io.sphere.sdk.client

import org.scalatest._
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.base.Optional
import com.typesafe.config.ConfigFactory

class SphereJavaClientSpec extends WordSpec with ShouldMatchers {

  val config = ConfigFactory.load()

  def withClient(client: SphereJavaClient)(body: SphereJavaClient => Unit) {
    try {
      body(client)
    } finally {
      client.close
    }
  }

  "SPHERE.IO Java client" must {
    "serve fetch requests providing JSON" in {
      withClient(new SphereJavaClientImpl(config, new HttpClientTestDouble {
        override def testDouble(requestable: Requestable) = HttpResponse.of(200, """{"id":1}""")
      })) { client =>
        val service = new XyzService
        val result: ListenableFuture[Optional[Xyz]] = client.execute(service.fetchById("1"))
        result.get().get() should be(new Xyz("1"))
      }
    }

    "serve fetch requests providing instance" in {
      withClient(new SphereJavaClientImpl(config, new SphereRequestExecutorTestDouble {
        override protected def result[I, R](fetch: Fetch[I, R]): Optional[I] = Optional.of(new Xyz("1")).asInstanceOf[Optional[I]]
      })) { client =>
        val service = new XyzService
        val result: ListenableFuture[Optional[Xyz]] = client.execute(service.fetchById("1"))
        result.get().get() should be(new Xyz("1"))
      }
    }
  }
}