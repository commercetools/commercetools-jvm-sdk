package sphere

import de.commercetools.sphere.client.shop.Customers
import de.commercetools.sphere.client.shop.model.Customer

import play.mvc.Http

class CurrentCustomerSpec extends CustomerServiceSpec {

  def currentCustomerWith(customerService: Customers, session: Session = testSession) = 
    CurrentCustomer.getCurrentCustomer(session.getHttpSession, customerService)

  def checkCustomerServiceCall(
    currentCustomerMethod: CurrentCustomer => Customer,
    expectedCustomerServiceCall: Symbol,
    expectedServiceCallArgs: List[Any]): Customer = {

    val customerService = customerServiceExpecting(expectedCustomerServiceCall, expectedServiceCallArgs, resultCustomer)
    val result = currentCustomerMethod(currentCustomerWith(customerService))
    getCurrentSession().getCustomerId.version() must be (resultCustomer.getVersion)
    result
  }

  override def beforeEach()  {
    testSession.clearCustomer()
    testSession.putCustomer(initialCustomer)
    Http.Context.current.set(new Http.Context(null, testSession.getHttpSession, emptyMap))
  }

  "changePassword()" must {
    "invoke customerService.changePassword and update customer version in the session" in {
      checkCustomerServiceCall(
        _.changePassword("old", "new"),
        'changePassword, List(initialCustomer.getId, initialCustomer.getVersion, "old", "new"))
    }
  }
}
