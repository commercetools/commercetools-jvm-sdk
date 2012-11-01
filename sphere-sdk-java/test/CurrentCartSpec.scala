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

  val EUR = Currency.getInstance("EUR")
  val testCartId = UUID.randomUUID().toString
  val initialTestCart = TestCart(testCartId, 1)
  val resultTestCart = TestCart(testCartId, 2)

  val testSession = new Session(new Http.Session(new java.util.HashMap()))
  val testId = UUID.randomUUID().toString

  def currentCartWith(cartService: Carts, session: Session = testSession): CurrentCart = new CurrentCart(session, cartService, EUR)

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
    testSession.getCartId.version() must be (resultTestCart.getVersion)
    result
  }

  override def beforeEach()  {
    testSession.clearCart()
    testSession.putCart(initialTestCart)
  }

  "addLineItem()" must {
    "invoke cartService.addLineItem()" in {
      checkCartServiceCall(
        _.addLineItem(testId),
        'addLineItem, List(initialTestCart.getId, initialTestCart.getVersion, testId, 1))
    }
  }

  "updateLineItemQuantity()" must {
    "invoke cartService.updateLineItemQuantity()" in {
      checkCartServiceCall(
        _.updateLineItemQuantity(testId, 5),
        'updateLineItemQuantity, List(initialTestCart.getId, initialTestCart.getVersion, testId, 5))
    }
  }

  "removeLineItem()" must {
    "invoke cartService.removeLineItem()" in {
      checkCartServiceCall(
        _.removeLineItem(testId),
        'removeLineItem, List(initialTestCart.getId, initialTestCart.getVersion, testId))
    }
  }

  "setCustomer()" must {
    "invoke cartService.setCustomer()" in {
      checkCartServiceCall(
        _.setCustomer(testId),
        'setCustomer, List(initialTestCart.getId, initialTestCart.getVersion, testId))
    }
  }

  "setShippingAddress()" must {
    "invoke cartService.setShippingAddress()" in {
      val address = "Alexanderplatz"
      checkCartServiceCall(
        _.setShippingAddress(address),
        'setShippingAddress, List(initialTestCart.getId, initialTestCart.getVersion, address))
    }
  }

  "order()" must {
    "invoke cartService.order" in {
      val cartService = cartServiceExpecting(
        'order, List(initialTestCart.getId, initialTestCart.getVersion, PaymentState.Paid),
          TestOrder)
      currentCartWith(cartService).order(PaymentState.Paid)
      testSession.getCartId must be (null)
    }
  }

  //TODO add tests to check the behaviour when a cart is not in the session
}
