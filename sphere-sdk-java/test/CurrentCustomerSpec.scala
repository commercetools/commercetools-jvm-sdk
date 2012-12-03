package sphere

import de.commercetools.sphere.client.shop.model.{CustomerUpdate, Customer}
import de.commercetools.sphere.client.shop.{ReviewService, CommentService, OrderService, CustomerService}
import sphere.testobjects.{TestOrder, TestCustomerToken}
import com.google.common.base.Optional

class CurrentCustomerSpec extends ServiceSpec {

  val testToken = TestCustomerToken("tokken")

  def currentCustomerWith(
    customerService: CustomerService = null,
    orderService: OrderService = null,
    commentService: CommentService = null,
    reviewService: ReviewService = null) =
    CurrentCustomer.createFromSession(customerService, orderService, commentService, reviewService)

  def checkCustomerServiceCall[A: Manifest](
    currentCustomerMethod: CurrentCustomer => A,
    expectedCustomerServiceCall: Symbol,
    expectedServiceCallArgs: List[Any],
    expectedResultCustomerVersion: Int = resultCustomer.getVersion,
    customerServiceReturnValue: A = resultCustomer): A = {

    val customerService = customerServiceExpectingCommand(expectedCustomerServiceCall, expectedServiceCallArgs, customerServiceReturnValue)
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

  "getOrders()" must {
    "invoke orderService.byCustomerId" in {
      val orderService = orderServiceExpectingQuery('byCustomerId, List(testCustomerId), queryResult(List(TestOrder)))
      val result = currentCustomerWith(orderService = orderService).getOrders
      result.getCount must be (1)
      result.getResults().get(0) must be (TestOrder)
    }
  }

  "fetch()" must {
    "invoke customerService.byId and update customer version in the session" in {
      val customerService = customerServiceExpectingFetch('byId, List(initialCustomer.id), Optional.of(resultCustomer))
      val currentCustomer = currentCustomerWith(customerService)
      val result: Customer = currentCustomer.fetch()
      Session.current().getCustomerId.version() must be (resultCustomer.version)
      result.getVersion must be (resultCustomer.version)
    }
  }

  "getReviews" must {
    "invoke reviewService.byCustomerId" in {
      val reviewService = reviewServiceExpectingQuery('byCustomerId, List(testCustomerId), queryResult(List(testReview)))
      val result = currentCustomerWith(reviewService = reviewService).getReviews()
      result.getCount must be (1)
      result.getResults().get(0) must be (testReview)
    }
  }

  "getComments" must {
    "invoke commentService.byCustomerId" in {
      val commentService = commentServiceExpectingQuery('byCustomerId, List(testCustomerId), queryResult(List(testComment)))
      val result = currentCustomerWith(commentService = commentService).getComments()
      result.getCount must be (1)
      result.getResults().get(0) must be (testComment)
    }
  }

  "createReview()" must {
    "invoke reviewService.createReview with the customerId from the session" in {
      val service = reviewServiceExpectingCommand('createReview, List(productId, testCustomerId, "fritz", "title", "text", 0.5))
      val result = currentCustomerWith(reviewService = service).createReview(productId, "fritz", "title", "text", 0.5)
      result must be (testReview)
    }
  }

  "createComment()" must {
    "invoke commentService.createComment with the customerId from the session" in {
      val service = commentServiceExpectingCommand('createComment, List(productId, testCustomerId, "fritz", "title", "text"))
      val result = currentCustomerWith(commentService = service).createComment(productId, "fritz", "title", "text")
      result must be (testComment)
    }
  }
}
