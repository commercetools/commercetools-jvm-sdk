package sphere

import de.commercetools.sphere.client.shop.CartService
import de.commercetools.sphere.client.shop.model.{Address, PaymentState, Cart}
import testobjects.TestOrder

import play.mvc.Http

class CurrentCartSpec extends ServiceSpec {

  def currentCartWith(cartService: CartService): CurrentCart = new CurrentCart(cartService, EUR)

  def checkCartServiceCall(currentCartMethod: CurrentCart => Cart, expectedCartServiceCall: Symbol, expectedServiceCallArgs: List[Any]): Cart = {
    val cartService = cartServiceExpectingCommand(expectedCartServiceCall, expectedServiceCallArgs)
    val result = currentCartMethod(currentCartWith(cartService))
    Session.current().getCartId.version() must be (resultTestCart.getVersion)
    result
  }

  override def beforeEach()  {
    Http.Context.current.set(new Http.Context(null, emptyMap, emptyMap))
    Session.current().putCart(initialTestCart)
  }

  "addLineItem()" must {
    "invoke cartService.addLineItem() and update cart version in the session" in {
      checkCartServiceCall(
        _.addLineItem(testId),
        'addLineItem, List(initialTestCart.getId, initialTestCart.getVersion, testId, null, 1, null))
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
      val address = new Address("Alexanderplatz")
      checkCartServiceCall(
        _.setShippingAddress(address),
        'setShippingAddress, List(initialTestCart.getId, initialTestCart.getVersion, address))
    }
  }

  "order()" must {
    "invoke cartService.order and remove cart from session" in {
      val cartService = cartServiceExpectingCommand(
        'order, List(initialTestCart.getId, initialTestCart.getVersion, PaymentState.Paid),
          TestOrder)
      currentCartWith(cartService).order(PaymentState.Paid)
      Session.current().getCartId must be (null)
    }
  }

  //TODO add tests to check the behaviour when a cart is not in the session
}
