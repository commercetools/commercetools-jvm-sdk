package sphere


import de.commercetools.sphere.client.shop.{CustomerService, ShopClient}

import play.mvc.Http

class SphereClientSpec extends CustomerServiceSpec {

  def sphereClient(customerService: CustomerService): SphereClient = {
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
      testSession.putCustomer(initialCustomer)
      sphereClient(null).currentCustomer() != null must be (true)
    }
  }
}
