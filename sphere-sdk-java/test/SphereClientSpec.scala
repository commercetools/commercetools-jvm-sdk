package sphere

import java.util.{Currency, UUID}

import de.commercetools.sphere.client.shop.{ShopClient, Customers}
import de.commercetools.sphere.client.shop.model.{Customer}
import de.commercetools.sphere.client.{QueryRequest, CommandRequest, MockListenableFuture}
import de.commercetools.internal.ListenableFutureAdapter
import sphere.testobjects.{TestCustomer}

import org.scalatest.{BeforeAndAfterEach, WordSpec}
import org.scalatest.matchers.MustMatchers
import org.scalamock.scalatest.MockFactory
import org.scalamock.ProxyMockFactory
import play.mvc.Http

class SphereClientSpec
  extends WordSpec
  with MustMatchers
  with BeforeAndAfterEach
  with MockFactory
  with ProxyMockFactory {


  val testSession = new Session(new Http.Session(new java.util.HashMap()))
  val testId = UUID.randomUUID().toString
  val testCustomerId = UUID.randomUUID().toString
  val testCustomer = TestCustomer(testCustomerId, 1)
  val EUR = Currency.getInstance("EUR")
  val emptyMap = new java.util.HashMap[java.lang.String,java.lang.String]()

  def currentSession() = new Session((Http.Context.current().session()))

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

  def sphereClient(customerService: Customers): SphereClient = {
    Http.Context.current.set(new Http.Context(null, testSession.getHttpSession, emptyMap))
    val config = mock[Config]
    config stubs 'shopCurrency returning EUR
    val shopClient = new ShopClient(null, null, null, null, null, customerService)
    new SphereClient(config, shopClient)
  }

  override def beforeEach()  {
    testSession.clearCustomer()
  }

  def sessionUpdated(): Unit = {
    val idVer = currentSession().getCustomerId()
    idVer.id() must be (testCustomer.id)
    idVer.version() must be (testCustomer.version)
  }

  "signup()" must {
    "invoke customerService.signup()" in {
      val customerService = customerServiceExpecting(
        'signup, List("em@ail.com", "secret", "hans", "wurst", "hungry", "the dude"),
        testCustomer)
      sphereClient(customerService).signup("em@ail.com", "secret", "hans", "wurst", "hungry", "the dude")
      sessionUpdated()
    }
  }

  "login()" must {
    "invoke customerService.login()" in {
      val customerService = customerServiceQueryExpecting(
        'login, List("em@ail.com", "secret"),
        testCustomer)
      sphereClient(customerService).login("em@ail.com", "secret")
      sessionUpdated()
    }
  }
}
