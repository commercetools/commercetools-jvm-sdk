package sphere

import java.util.{Currency, UUID}

import sphere.testobjects.TestCustomer
import de.commercetools.sphere.client.shop.model.Customer
import de.commercetools.sphere.client.shop.Customers
import de.commercetools.sphere.client.{QueryRequest, CommandRequest, MockListenableFuture}
import de.commercetools.internal.ListenableFutureAdapter

import org.scalatest.{BeforeAndAfterEach, WordSpec}
import org.scalatest.matchers.MustMatchers
import org.scalamock.scalatest.MockFactory
import org.scalamock.ProxyMockFactory
import play.mvc.Http

abstract class CustomerServiceSpec
  extends WordSpec
  with MustMatchers
  with BeforeAndAfterEach
  with MockFactory
  with ProxyMockFactory {

  val testSession = new Session(new Http.Session(new java.util.HashMap()))
  val testId = UUID.randomUUID().toString
  val testCustomerId = UUID.randomUUID().toString
  val initialCustomer = TestCustomer(testCustomerId, 1)
  val resultCustomer = TestCustomer(testCustomerId, 2)

  val emptyMap = new java.util.HashMap[java.lang.String,java.lang.String]()
  val EUR = Currency.getInstance("EUR")

  def getCurrentSession() = new Session((Http.Context.current().session()))

  def customerServiceExpecting(expectedMethodCall: Symbol, methodArgs: List[Any], methodResult: Customer): Customers = {
    val mockedFuture = MockListenableFuture.completed(methodResult)
    val future = new ListenableFutureAdapter(mockedFuture)
    val commandRequest = mock[CommandRequest[Customer]]
    commandRequest expects 'executeAsync returning future
    val customerService = mock[Customers]
    customerService expects expectedMethodCall withArgs (methodArgs:_*) returning commandRequest
    customerService
  }

  def customerServiceQueryExpecting(expectedMethodCall: Symbol, methodArgs: List[Any], methodResult: Customer): Customers = {
    val mockedFuture = MockListenableFuture.completed(methodResult)
    val future = new ListenableFutureAdapter(mockedFuture)
    val queryRequest = mock[QueryRequest[Customer]]
    queryRequest expects 'fetchAsync returning future
    val customerService = mock[Customers]
    customerService expects expectedMethodCall withArgs (methodArgs:_*) returning queryRequest
    customerService
  }

}
