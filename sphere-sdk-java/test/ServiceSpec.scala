package sphere

import java.util.{Currency, UUID}

import sphere.testobjects.{TestOrder, TestCart, TestCustomer}
import de.commercetools.sphere.client.shop.model.Address
import de.commercetools.sphere.client.shop.{Orders, Carts, CustomerService, LoginResult}
import de.commercetools.sphere.client.{QueryRequest, CommandRequest, MockListenableFuture}
import de.commercetools.internal.ListenableFutureAdapter

import org.scalatest.{BeforeAndAfterEach, WordSpec}
import org.scalatest.matchers.MustMatchers
import org.scalamock.scalatest.MockFactory
import org.scalamock.ProxyMockFactory
import play.mvc.Http
import de.commercetools.sphere.client.model.QueryResult

abstract class ServiceSpec extends WordSpec
with MustMatchers
with BeforeAndAfterEach
with MockFactory
with ProxyMockFactory {

  val testId = UUID.randomUUID().toString
  val testCustomerId = UUID.randomUUID().toString
  val testCartId = UUID.randomUUID().toString
  lazy val initialCustomer = TestCustomer(testCustomerId, 1)
  lazy val resultCustomer = TestCustomer(testCustomerId, 2)
  val testAddress = new Address("Alexanderplatz")
  lazy val testCart = TestCart(testCartId, 1)
  val initialTestCart = TestCart(testCartId, 1)
  val resultTestCart = TestCart(testCartId, 2)
  lazy val loginResultNoCart = new LoginResult(resultCustomer, null)
  lazy val loginResultWithCart = new LoginResult(resultCustomer, resultTestCart)

  lazy val emptyMap = new java.util.HashMap[java.lang.String,java.lang.String]()
  lazy val EUR = Currency.getInstance("EUR")

  def customerServiceExpecting[A: Manifest](
    expectedMethodCall: Symbol,
    methodArgs: List[Any],
    methodResult: A = resultCustomer): CustomerService =
    serviceExpecting[CustomerService, A](expectedMethodCall, methodArgs, methodResult)

  def cartServiceExpecting[A: Manifest](
    expectedMethodCall: Symbol,
    methodArgs: List[Any],
    methodResult: A = resultTestCart): Carts =
    serviceExpecting[Carts, A](expectedMethodCall, methodArgs, methodResult)

  private def serviceExpecting[S: Manifest, A: Manifest](
    expectedMethodCall: Symbol,
    methodArgs: List[Any],
    methodResult: A): S = {
    val mockedFuture = MockListenableFuture.completed(methodResult)
    val future = new ListenableFutureAdapter(mockedFuture)
    val commandRequest = mock[CommandRequest[A]]
    commandRequest expects 'executeAsync returning future
    val service = mock[S]
    service expects expectedMethodCall withArgs (methodArgs:_*) returning commandRequest
    service
  }

  def orderServiceQueryExpecting[A: Manifest](
    expectedMethodCall: Symbol,
    methodArgs: List[Any],
    methodResult: A = TestOrder): Orders =
    serviceQueryExpecting[Orders, A](expectedMethodCall, methodArgs, methodResult)

  def cartServiceQueryExpecting[A: Manifest](
    expectedMethodCall: Symbol,
    methodArgs: List[Any],
    methodResult: A = resultTestCart): Carts =
    serviceQueryExpecting[Carts, A](expectedMethodCall, methodArgs, methodResult)

  def customerServiceQueryExpecting[A: Manifest](
    expectedMethodCall: Symbol,
    methodArgs: List[Any],
    methodResult: A = resultCustomer): CustomerService =
    serviceQueryExpecting[CustomerService, A](expectedMethodCall, methodArgs, methodResult)

  def queryResult[A](results: List[A]): QueryResult[A] ={
    import scala.collection.JavaConversions._
    new QueryResult(0, results.size, results.size, results)
  }

  private def serviceQueryExpecting[S: Manifest, A: Manifest](
    expectedMethodCall: Symbol,
    methodArgs: List[Any],
    methodResult: A): S = {
    val mockedFuture = MockListenableFuture.completed(methodResult)
    val future = new ListenableFutureAdapter(mockedFuture)
    val queryRequest = mock[QueryRequest[A]]
    queryRequest expects 'fetchAsync returning future
    val service = mock[S]
    service expects expectedMethodCall withArgs (methodArgs:_*) returning queryRequest
    service
  }

  override def beforeEach()  {
    Http.Context.current.set(new Http.Context(null, emptyMap, emptyMap))
  }
}
