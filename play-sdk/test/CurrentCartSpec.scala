package sphere

import io.sphere.client.shop.CartService
import io.sphere.client.shop.model.{Address, PaymentState, Cart}
import testobjects.TestOrder
import io.sphere.client.SphereException

import play.mvc.Http
import com.neovisionaries.i18n.CountryCode._

// TODO migrate ScalaMock to Scala 2.10.1
//class CurrentCartSpec extends ServiceSpec {
//
//  def currentCartWith(cartService: CartService): CurrentCart = new CurrentCart(cartService, EUR, Cart.InventoryMode.None)
//
//  def checkCartServiceCall(currentCartMethod: CurrentCart => Cart, expectedCartServiceCall: Symbol, expectedServiceCallArgs: List[Any]): Cart = {
//    val cartService = cartServiceExpectingCommand(expectedCartServiceCall, expectedServiceCallArgs)
//    val result = currentCartMethod(currentCartWith(cartService))
//    Session.current().getCartId.version() must be (resultTestCart.version)
//    result
//  }
//
//  override def beforeEach()  {
//    Http.Context.current.set(newHttpContext)
//    Session.current().putCart(initialTestCart)
//  }
//
//  "addLineItem()" must {
//    "invoke cartService.addLineItem() and update cart version in the session" in {
//      checkCartServiceCall(
//        _.addLineItem(testId, 1),
//        'addLineItem, List(initialTestCart.getId, initialTestCart.version, testId, 1, 1, null))
//    }
//  }
//
//  "updateLineItemQuantity()" must {
//    "invoke cartService.updateLineItemQuantity() and update cart version in the session" in {
//      checkCartServiceCall(
//        _.updateLineItemQuantity(testId, 5),
//        'updateLineItemQuantity, List(initialTestCart.getId, initialTestCart.version, testId, 5))
//    }
//  }
//
//  "removeLineItem()" must {
//    "invoke cartService.removeLineItem() and update cart version in the session" in {
//      checkCartServiceCall(
//        _.removeLineItem(testId),
//        'removeLineItem, List(initialTestCart.getId, initialTestCart.version, testId))
//    }
//  }
//
//  "setShippingAddress()" must {
//    "invoke cartService.setShippingAddress() and update cart version in the session" in {
//      val address = new Address(DE)
//      checkCartServiceCall(
//        _.setShippingAddress(address),
//        'setShippingAddress, List(initialTestCart.getId, initialTestCart.version, address))
//    }
//  }
//
//  "orderCart()" must {
//    "invoke cartService.orderCart() and remove cart from session" in {
//      val cartService = cartServiceExpectingCommand(
//        'orderCart, List(initialTestCart.getId, initialTestCart.version, PaymentState.Paid),
//          TestOrder)
//      val checkoutId = currentCartWith(cartService).createCheckoutSummaryId()
//      // Simulate the checkoutId being sent to the client, and being sent back to create an order
//      Thread.sleep(800);
//      currentCartWith(cartService).isSafeToCreateOrder(checkoutId) must be (true)
//      currentCartWith(cartService).orderCart(checkoutId, PaymentState.Paid)
//      Session.current().getCartId must be (null)
//    }
//    "reject invalid checkoutId" in {
//      val cartService = mock[CartService]
//      // try to cheat the checkoutId verification by just creating a checkoutId and passing it back immediately
//      val e = intercept[SphereException] {
//        val currentCart = currentCartWith(cartService)
//        currentCart.orderCart(currentCart.createCheckoutSummaryId(), PaymentState.Paid)
//      }
//      intercept[SphereException] {
//        val currentCart = currentCartWith(cartService)
//        currentCart.orderCart("7_2a_157_fe", PaymentState.Pending)
//      }
//    }
//  }
//
//  "isSafeToCreateOrder()" must {
//    "reject invalid checkoutId" in {
//      val currentCart = currentCartWith(mock[CartService])
//      intercept[SphereException] {
//        currentCart.isSafeToCreateOrder(currentCart.createCheckoutSummaryId())
//      }
//      intercept[SphereException] {
//        currentCart.isSafeToCreateOrder("abc")
//      }
//      currentCart.isSafeToCreateOrder("7_2a_157_fe") must be (false)
//    }
//  }
//
//  // TODO add tests to check the behavior when a cart is not in the session
//}
