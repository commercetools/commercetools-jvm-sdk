package sphere

import java.util.{UUID, Currency}

import de.commercetools.sphere.client.shop.model.{Order, PaymentState, LineItemContainer, Cart}
import de.commercetools.sphere.client.shop.Carts
import de.commercetools.sphere.client.{MockListenableFuture, CommandRequest}
import de.commercetools.internal.ListenableFutureAdapter
import testobjects.{TestOrder, TestCart}


import org.scalatest.{BeforeAndAfterEach, WordSpec}
import org.scalamock.scalatest.MockFactory
import org.scalamock.ProxyMockFactory
import org.scalatest.matchers.MustMatchers
import play.mvc.Http

class CurrentCartSpec
  extends WordSpec
  with MustMatchers
  with BeforeAndAfterEach
  with MockFactory
  with ProxyMockFactory  {

  val emptyMap = new java.util.HashMap[java.lang.String,java.lang.String]()
  val EUR = Currency.getInstance("EUR")

  val testCartId = UUID.randomUUID().toString
  val initialTestCart = TestCart(testCartId, 1)
  val resultTestCart = TestCart(testCartId, 2)


  def getCurrentSession() = new Session((Http.Context.current().session()))

  val testId = UUID.randomUUID().toString

  def currentCartWith(cartService: Carts): CurrentCart = new CurrentCart(cartService, EUR)

  def cartServiceExpecting(expectedMethodCall: Symbol, methodArgs: List[Any], methodResult: LineItemContainer = resultTestCart): Carts = {
    val mockedFuture = MockListenableFuture.completed(methodResult)
    val future = new ListenableFutureAdapter(mockedFuture)
    val commandRequest = if (methodResult.isInstanceOf[Cart]) mock[CommandRequest[Cart]] else mock[CommandRequest[Order]]
    commandRequest expects 'executeAsync returning future
    val cartService = mock[Carts]
    cartService expects expectedMethodCall withArgs (methodArgs:_*) returning commandRequest
    cartService
  }

  def checkCartServiceCall(currentCartMethod: CurrentCart => Cart, expectedCartServiceCall: Symbol, expectedServiceCallArgs: List[Any]): Cart = {
    val cartService = cartServiceExpecting(expectedCartServiceCall, expectedServiceCallArgs)
    val result = currentCartMethod(currentCartWith(cartService))
    getCurrentSession().getCartId.version() must be (resultTestCart.getVersion)
    result
  }

  override def beforeEach()  {
    Http.Context.current.set(new Http.Context(null, emptyMap, emptyMap))
    getCurrentSession().putCart(initialTestCart)
  }

  "addLineItem()" must {
    "invoke cartService.addLineItem() and update cart version in the session" in {
      checkCartServiceCall(
        _.addLineItem(testId),
        'addLineItem, List(initialTestCart.getId, initialTestCart.getVersion, testId, 1))
    }
  }

  "updateLineItemQuantity()" must {
    "invoke cartService.updateLineItemQuantity() and update cart version in the session" in {
      checkCartServiceCall(
        _.updateLineItemQuantity(testId, 5),
        'updateLineItemQuantity, List(initialTestCart.getId, initialTestCart.getVersion, testId, 5))
    }
  }

  "removeLineItem()" must {
    "invoke cartService.removeLineItem() and update cart version in the session" in {
      checkCartServiceCall(
        _.removeLineItem(testId),
        'removeLineItem, List(initialTestCart.getId, initialTestCart.getVersion, testId))
    }
  }

  "setShippingAddress()" must {
    "invoke cartService.setShippingAddress() and update cart version in the session" in {
      val address = "Alexanderplatz"
      checkCartServiceCall(
        _.setShippingAddress(address),
        'setShippingAddress, List(initialTestCart.getId, initialTestCart.getVersion, address))
    }
  }

  "order()" must {
    "invoke cartService.order and remove cart from session" in {
      val cartService = cartServiceExpecting(
        'order, List(initialTestCart.getId, initialTestCart.getVersion, PaymentState.Paid),
          TestOrder)
      currentCartWith(cartService).order(PaymentState.Paid)
      getCurrentSession().getCartId must be (null)
    }
  }

  //TODO add tests to check the behaviour when a cart is not in the session
}
