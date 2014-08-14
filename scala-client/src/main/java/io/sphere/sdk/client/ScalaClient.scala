package io.sphere.sdk.client

import java.util.concurrent.CompletableFuture
import java.util.function.BiConsumer

import io.sphere.sdk.http.{HttpClient, ClientRequest}

import scala.concurrent.Future
import com.typesafe.config.Config
import com.google.common.util.concurrent.{FutureCallback, Futures, ListenableFuture}

trait ScalaClient extends AutoCloseable {
  def execute[T](clientRequest: ClientRequest[T]): Future[T]

  def close()
}

class ScalaClientImpl(config: Config, sphereRequestExecutor: SphereRequestExecutor) extends ScalaClient {

  import ScalaAsync._

  private val javaClient: JavaClient = new JavaClientImpl(config, sphereRequestExecutor)

  def this(config: Config, httpClient: HttpClient) = this(config, new HttpSphereRequestExecutor(httpClient, config))

  def this(config: Config) = this(config, new NingAsyncHttpClient(config))

  override def execute[T](clientRequest: ClientRequest[T]): Future[T] = javaClient.execute(clientRequest).asScala

  override def close(): Unit = javaClient.close()
}

private[client] object ScalaAsync {
  import scala.concurrent.{Promise => ScalaPromise, Future}

  implicit class RichCompletableFuture[T](future: CompletableFuture[T]) {
    def asScala = asFuture(future)
  }

  def asFuture[T](completableFuture: CompletableFuture[T]): Future[T] = {
    val promise: ScalaPromise[T] = ScalaPromise()
    completableFuture.whenComplete(new CompletableFutureMapper(promise));
    return promise.future
  }
}