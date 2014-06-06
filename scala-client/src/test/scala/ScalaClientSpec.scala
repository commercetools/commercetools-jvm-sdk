import com.google.common.base.Optional
import com.typesafe.config.ConfigFactory
import io.sphere.sdk.client._
import org.scalatest._
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class ScalaClientSpec extends WordSpec with ShouldMatchers {

  val config = ConfigFactory.load()

  def withClient(client: SphereScalaClient)(body: SphereScalaClient => Unit) {
    try {
      body(client)
    } finally {
      client.close
    }
  }

  "SPHERE.IO Java client" must {
    "serve fetch requests providing JSON" in {
      withClient(new SphereScalaClientImpl(config, new HttpClientTestDouble {
        override def testDouble(requestable: Requestable) = HttpResponse.of(200, """{"id":1}""")
      })) { client =>
        val service = new XyzService
        val result: Future[Optional[Xyz]] = client.execute(service.fetchById("1"))
        Await.result(result, Duration("1 s")).get() should be(new Xyz("1"))
      }
    }

    "serve fetch requests providing instance" in {
      withClient(new SphereScalaClientImpl(config, new SphereRequestExecutorTestDouble {
        override protected def result[I, R](fetch: Fetch[I, R]): Optional[I] = Optional.of(new Xyz("1")).asInstanceOf[Optional[I]]
      })) { client =>
        val service = new XyzService
        val result: Future[Optional[Xyz]] = client.execute(service.fetchById("1"))
        Await.result(result, Duration("1 s")).get() should be(new Xyz("1"))
      }
    }
  }
}