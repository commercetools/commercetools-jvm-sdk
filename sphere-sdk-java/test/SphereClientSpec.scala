package sphere

import de.commercetools.sphere.client.shop.{CustomerService, ShopClient}

class SphereClientSpec extends CustomerServiceSpec {

  def sphereClient(customerService: CustomerService): SphereClient = {

    val config = mock[Config]
    config stubs 'shopCurrency returning EUR
    val shopClient = new ShopClient(null, null, null, null, null, customerService)
    new SphereClient(config, shopClient)
  }

  def sessionUpdated(): Unit = {
    val idVer = getCurrentSession().getCustomerId()
    idVer.id() must be (initialCustomer.id)
    idVer.version() must be (initialCustomer.version)
  }

  "signup()" must {
    "invoke customerService.signup() and put customer id and version into session" in {
      val customerService = customerServiceExpecting(
        'signup, List("em@ail.com", "secret", "hans", "wurst", "hungry", "the dude"),
        initialCustomer)
      sphereClient(customerService).signup("em@ail.com", "secret", "hans", "wurst", "hungry", "the dude")
      sessionUpdated()
    }
  }

  "login()" must {
    "invoke customerService.login() and put customer id and version into session" in {
      val customerService = customerServiceQueryExpecting(
        'login, List("em@ail.com", "secret"),
        initialCustomer)
      sphereClient(customerService).login("em@ail.com", "secret")
      sessionUpdated()
    }
  }

  "currentCustomer()" must {
    "return null on empty session" in {
      sphereClient(null).currentCustomer() must be (null)
    }
    "return CurrentCustomer when session contains customer id" in {
      getCurrentSession().putCustomer(initialCustomer)
      sphereClient(null).currentCustomer() != null must be (true)
    }
  }

  "logout()" must {
    "remove customer and cart data from the session" in {
      getCurrentSession().putCustomer(initialCustomer)
      getCurrentSession().putCart(testCart)
      val initialSession = getCurrentSession()
      initialSession.getCustomerId != null must be (true)
      initialSession.getCartId != null must be (true)

      sphereClient(null).logout()
      val updatedSession = getCurrentSession()
      updatedSession.getCustomerId must be (null)
      updatedSession.getCartId must be (null)
    }
  }
}
