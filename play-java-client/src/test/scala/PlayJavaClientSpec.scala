import com.google.common.base.Optional
import com.typesafe.config.ConfigFactory
import io.sphere.sdk.client._
import org.scalatest._

class PlayJavaClientSpec extends WordSpec with ShouldMatchers {

  val config = ConfigFactory.load()

  def withClient(client: PlayJavaClient)(body: PlayJavaClient => Unit) {
    try {
      body(client)
    } finally {
      client.close
    }
  }

  "SPHERE.IO Java client" must {
    "serve fetch requests providing JSON" in {
      withClient(new PlayJavaClientImpl(config, new HttpClientTestDouble {
        override def testDouble(requestable: Requestable) = HttpResponse.of(200, """{"id":1}""")
      })) { client =>
        val service = new XyzService
        val result = client.execute(service.fetchById("1"))
        result.get(1000).get() should be(new Xyz("1"))
      }
    }

    "serve fetch requests providing instance" in {
      withClient(new PlayJavaClientImpl(config, new SphereRequestExecutorTestDouble {
        override protected def result[T](fetch: ClientRequest[T]): T = Optional.of(new Xyz("1")).asInstanceOf[T]
      })) { client =>
        val service = new XyzService
        val result = client.execute(service.fetchById("1"))
        result.get(1000).get() should be(new Xyz("1"))
      }
    }
  }
}