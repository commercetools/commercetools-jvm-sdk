package io.sphere.sdk.client

import java.util.concurrent.CompletableFuture

import io.sphere.sdk.http.{HttpClientTestDouble, Requestable, HttpResponse, ClientRequest}
import org.scalatest._
import java.util.Optional
import com.typesafe.config.ConfigFactory

class JavaClientSpec extends WordSpec with ShouldMatchers {

  val config = ConfigFactory.load()

  def withClient(client: JavaClient)(body: JavaClient => Unit) {
    try {
      body(client)
    } finally {
      client.close
    }
  }

  "SPHERE.IO Java client" must {
    "serve fetch requests providing JSON" in {
      withClient(new JavaClientImpl(config, new HttpClientTestDouble {
        override def testDouble(requestable: Requestable) = HttpResponse.of(200, """{"id":1}""")
      })) { client =>
        val service = new XyzService
        val result: CompletableFuture[Optional[Xyz]] = client.execute(service.fetchById("1"))
        result.get().get() should be(new Xyz("1"))
      }
    }

    "serve fetch requests providing instance" in {
      withClient(new JavaClientImpl(config, new SphereRequestExecutorTestDouble {
        override protected def result[T](fetch: ClientRequest[T]): T = Optional.of(new Xyz("1")).asInstanceOf[T]
      })) { client =>
        val service = new XyzService
        val result: CompletableFuture[Optional[Xyz]] = client.execute(service.fetchById("1"))
        result.get().get() should be(new Xyz("1"))
      }
    }
  }
}