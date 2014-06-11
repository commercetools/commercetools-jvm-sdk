package io.sphere.sdk.client

import com.google.common.base.Optional
import scala.concurrent.Future
import com.typesafe.config.Config
import com.google.common.util.concurrent.{FutureCallback, Futures, ListenableFuture}

trait SphereScalaClient {
  def execute[I, R](fetch: Fetch[I, R]): Future[Optional[I]]

  def execute[I, R](query: Query[I, R]): Future[PagedQueryResult[I]]

  def execute[T, V](command: Command[T, V]): Future[T]

  def close()
}

class SphereScalaClientImpl(config: Config, sphereRequestExecutor: SphereRequestExecutor) extends SphereScalaClient {

  import ScalaAsync._

  private val javaClient: SphereJavaClient = new SphereJavaClientImpl(config, sphereRequestExecutor)

  def this(config: Config, httpClient: HttpClient) = this(config, new HttpSphereRequestExecutor(httpClient, config))

  def this(config: Config) = this(config, new NingAsyncHttpClient(config))

  override def execute[I, R](fetch: Fetch[I, R]): Future[Optional[I]] = javaClient.execute(fetch).asScala

  override def execute[I, R](query: Query[I, R]): Future[PagedQueryResult[I]] = javaClient.execute(query).asScala

  override def execute[I, R](query: AtMostOneResultQuery[I, R]): Future[Optional[I]] = javaClient.execute(query).asScala

  override def execute[T, V](command: Command[T, V]): Future[T] = javaClient.execute(command).asScala

  override def close(): Unit = javaClient.close()
}

private[client] object ScalaAsync {
  import scala.concurrent.{Promise => ScalaPromise, Future}

  implicit class RichGuavaFuture[T](future: ListenableFuture[T]) {
    def asScala = asFuture(future)
  }

  def asFuture[T](future: ListenableFuture[T]): Future[T] = {
    val promise: ScalaPromise[T] = ScalaPromise.apply[T]
    Futures.addCallback(future, new FutureCallback[T] {
      def onSuccess(result: T) {
        promise.success(result)
      }

      def onFailure(t: Throwable) {
        promise.failure(t)
      }
    })
    return promise.future
  }
}