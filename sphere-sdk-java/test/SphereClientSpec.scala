package sphere

import de.commercetools.sphere.client.shop.{Carts, CustomerService, ShopClient}
import de.commercetools.sphere.client.SphereException

class SphereClientSpec extends ServiceSpec {

  def sphereClient(customerService: CustomerService = null, cartService: Carts = null): SphereClient = {

    val config = mock[Config]
    config stubs 'shopCurrency returning EUR
    val shopClient = new ShopClient(null, null, null, cartService, null, customerService)
    new SphereClient(config, shopClient)
  }

  def sessionCustomerUpdated(): Unit = {
    val idVer = Session.current().getCustomerId()
    idVer.id() must be (initialCustomer.id)
    idVer.version() must be (initialCustomer.version)
  }

  def sessionCartUpdated(): Unit = {
    val idVer = Session.current().getCartId()
    idVer.id() must be (testCart.id)
    idVer.version() must be (testCart.version)
  }

  "signup()" must {
    "invoke customerService.signup() if no cart exists in the session and put customer id and version into session" in {
      val customerService = customerServiceExpecting(
        'signup, List("em@ail.com", "secret", "hans", "wurst", "hungry", "the dude"),
        initialCustomer)
      sphereClient(customerService).signup("em@ail.com", "secret", "hans", "wurst", "hungry", "the dude")
      sessionCustomerUpdated()
      Session.current().getCartId() must be (null)
    }
    "invoke customerService.signupWithCart() if a cart exists in the session and put customer and cart id and version into session" in {
      Session.current().putCart(testCart)
      val customerService = customerServiceExpecting(
        'signupWithCart, List("em@ail.com", "secret", "hans", "wurst", "hungry", "the dude", testCart.id, testCart.version),
        loginResultWithCart)
      sphereClient(customerService).signup("em@ail.com", "secret", "hans", "wurst", "hungry", "the dude")
      sessionCustomerUpdated()
      sessionCartUpdated()
    }
  }

  "login()" must {
    "invoke customerService.login() without anonymous cart in session and put customer id and version into session" in {
      val customerService = customerServiceQueryExpecting(
        'login, List("em@ail.com", "secret"),
        loginResultNoCart)
      sphereClient(customerService).login("em@ail.com", "secret")
      sessionCustomerUpdated()
      Session.current().getCartId() must be (null)
    }
    "invoke cartService.loginWithAnonymousCart() and put customer and cart id and version into session" in {
      Session.current().putCart(testCart)
      val cartService = cartServiceExpecting(
        'loginWithAnonymousCart, List(testCart.id, testCart.version, "em@ail.com", "secret"),
        loginResultWithCart)
      sphereClient(cartService = cartService).login("em@ail.com", "secret")
      sessionCustomerUpdated()
      sessionCartUpdated()
    }
  }

  "currentCustomer()" must {
    "return null on empty session" in {
      sphereClient(null).currentCustomer() must be (null)
    }
    "return CurrentCustomer when session contains customer id" in {
      Session.current().putCustomer(initialCustomer)
      sphereClient(null).currentCustomer() != null must be (true)
    }
  }

  "logout()" must {
    "remove customer and cart data from the session" in {
      Session.current().putCustomer(initialCustomer)
      Session.current().putCart(testCart)
      val initialSession = Session.current()
      initialSession.getCustomerId != null must be (true)
      initialSession.getCartId != null must be (true)

      sphereClient(null).logout()
      val updatedSession = Session.current()
      updatedSession.getCustomerId must be (null)
      updatedSession.getCartId must be (null)
    }
    "throw illegal state expection on CurrentCustomer methods if invoked after logout" in {
      Session.current().putCustomer(initialCustomer)
      val currentCustomer = sphereClient(null).currentCustomer()
      sphereClient(null).logout()
      try {
        currentCustomer.changePassword("","")
        fail("exception expected.")
      } catch {
        case e: SphereException => e.getCause.isInstanceOf[IllegalStateException] must be (true)
      }
    }
  }
}
