package sphere

import de.commercetools.sphere.client.shop.model.{CustomerUpdate, Customer}
import de.commercetools.sphere.client.shop.CustomerService
import sphere.testobjects.TestCustomerToken

class CurrentCustomerSpec extends ServiceSpec {

  val testToken = TestCustomerToken("tokken")

  def currentCustomerWith(customerService: CustomerService) =
    CurrentCustomer.getCurrentCustomer(customerService)

  def checkCustomerServiceCall[A: Manifest](
    currentCustomerMethod: CurrentCustomer => A,
    expectedCustomerServiceCall: Symbol,
    expectedServiceCallArgs: List[Any],
    expectedResultCustomerVersion: Int = resultCustomer.getVersion,
    customerServiceReturnValue: A = resultCustomer): A = {

    val customerService = customerServiceExpecting(expectedCustomerServiceCall, expectedServiceCallArgs, customerServiceReturnValue)
    val result = currentCustomerMethod(currentCustomerWith(customerService))
    Session.current().getCustomerId.version() must be (expectedResultCustomerVersion)
    result
  }

  override def beforeEach()  {
    super.beforeEach()
    Session.current().putCustomer(initialCustomer)
  }

  "changePassword()" must {
    "invoke customerService.changePassword and update customer version in the session" in {
      checkCustomerServiceCall(
        _.changePassword("old", "new"),
        'changePassword, List(initialCustomer.getId, initialCustomer.getVersion, "old", "new"))
    }
  }

  "changeShippingAddress()" must {
    "invoke customerService.changeShippingAddress and update customer version in the session" in {
      checkCustomerServiceCall(
        _.changeShippingAddress(5, testAddress),
        'changeShippingAddress, List(initialCustomer.getId, initialCustomer.getVersion, 5, testAddress))
    }
  }

  "removeShippingAddress()" must {
    "invoke customerService.removeShippingAddress and update customer version in the session" in {
      checkCustomerServiceCall(
        _.removeShippingAddress(5),
        'removeShippingAddress, List(initialCustomer.getId, initialCustomer.getVersion, 5))
    }
  }

  "setDefaultShippingAddress()" must {
    "invoke customerService.setDefaultShippingAddress and update customer version in the session" in {
      checkCustomerServiceCall(
        _.setDefaultShippingAddress(5),
        'setDefaultShippingAddress, List(initialCustomer.getId, initialCustomer.getVersion, 5))
    }
  }

  "updateCustomer()" must {
    val update = new CustomerUpdate()
    update.setEmail("em@ail.com")

    "invoke customerService.updateCustomer and update customer version in the session" in {
      checkCustomerServiceCall(
        _.updateCustomer(update),
        'updateCustomer, List(initialCustomer.getId, initialCustomer.getVersion, update))
    }
  }

  "resetPassword()" must {
    "invoke customerService.resetPassword and update customer version in the session" in {
      checkCustomerServiceCall(
        _.resetPassword("tokken", "new"),
        'resetPassword, List(initialCustomer.getId, initialCustomer.getVersion, "tokken", "new"))
    }
  }

  "createEmailVerificationToken()" must {
    "invoke customerService.createEmailVerificationToken and not update customer version in the session" in {
      checkCustomerServiceCall(
        _.createEmailVerificationToken(10),
        'createEmailVerificationToken, List(initialCustomer.getId, initialCustomer.getVersion, 10),
        initialCustomer.version,
        testToken)
    }
  }

  "verifyEmail()" must {
    "invoke customerService.verifyEmail and update customer version in the session" in {
      checkCustomerServiceCall(
        _.verifyEmail("tokken"),
        'verifyEmail, List(initialCustomer.getId, initialCustomer.getVersion, "tokken"))
    }
  }

  "fetch()" must {
    "invoke customerService.byId and update cusotmer version in the session" in {
      val customerService = customerServiceQueryExpecting('byId, List(initialCustomer.id))
      val currentCustomer = currentCustomerWith(customerService)
      val result: Customer = currentCustomer.fetch()
      Session.current().getCustomerId.version() must be (resultCustomer.version)
      result.getVersion must be (resultCustomer.version)
    }
  }
}
