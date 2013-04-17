package sphere

import io.sphere.client.shop.model.{Order, CustomerUpdate, Customer}
import sphere.testobjects.{TestOrder, TestCustomerToken}
import com.google.common.base.Optional

// TODO migrate ScalaMock to Scala 2.10.1
//class CurrentCustomerSpec extends ServiceSpec {
//
//  val testToken = TestCustomerToken("tokken")
//
//  def currentCustomerWith(
//    customerService: CustomerService = null,
//    orderService: OrderService = null,
//    commentService: CommentService = null,
//    reviewService: ReviewService = null) =
//    CurrentCustomer.createFromSession(customerService, orderService, commentService, reviewService)
//
//  def checkCustomerServiceCall[A: Manifest](
//    currentCustomerMethod: CurrentCustomer => A,
//    expectedCustomerServiceCall: Symbol,
//    expectedServiceCallArgs: List[Any],
//    expectedResultCustomerVersion: Int = resultCustomer.getVersion,
//    customerServiceReturnValue: A = resultCustomer): A =
//  {
//    val customerService = customerServiceExpectingCommand(expectedCustomerServiceCall, expectedServiceCallArgs, customerServiceReturnValue)
//    val result = currentCustomerMethod(currentCustomerWith(customerService))
//    Session.current().getCustomerId.version() must be (expectedResultCustomerVersion)
//    result
//  }
//
//  override def beforeEach()  {
//    super.beforeEach()
//    Session.current().putCustomerIdAndVersion(initialCustomer)
//  }
//
//  "changePassword()" must {
//    "invoke customerService.changePassword and update customer version in the session" in {
//      checkCustomerServiceCall(
//        _.changePassword("old", "new"),
//        'changePassword,
//        List(initialCustomer.getId, initialCustomer.getVersion, "old", "new"),
//        customerServiceReturnValue = Optional.of(resultCustomer))
//    }
//  }
//
//  "changeAddress()" must {
//    "invoke customerService.changeAddress and update customer version in the session" in {
//      checkCustomerServiceCall(
//        _.changeAddress(5, testAddress),
//        'changeAddress, List(initialCustomer.getId, initialCustomer.getVersion, 5, testAddress))
//    }
//  }
//
//  "removeAddress()" must {
//    "invoke customerService.removeAddress and update customer version in the session" in {
//      checkCustomerServiceCall(
//        _.removeAddress(5),
//        'removeAddress, List(initialCustomer.getId, initialCustomer.getVersion, 5))
//    }
//  }
//
//  "setDefaultShippingAddress()" must {
//    "invoke customerService.setDefaultShippingAddress and update customer version in the session" in {
//      checkCustomerServiceCall(
//        _.setDefaultShippingAddress(5),
//        'setDefaultShippingAddress, List(initialCustomer.getId, initialCustomer.getVersion, 5))
//    }
//  }
//
//  "updateCustomer()" must {
//    val update = new CustomerUpdate()
//    update.setEmail("em@ail.com")
//
//    "invoke customerService.updateCustomer and update customer version in the session" in {
//      checkCustomerServiceCall(
//        _.updateCustomer(update),
//        'updateCustomer, List(initialCustomer.getId, initialCustomer.getVersion, update))
//    }
//  }
//
//  "resetPassword()" must {
//    "invoke customerService.resetPassword and update customer version in the session" in {
//      checkCustomerServiceCall(
//        _.resetPassword("tokken", "new"),
//        'resetPassword, List(initialCustomer.getId, initialCustomer.getVersion, "tokken", "new"))
//    }
//  }
//
//  "createEmailVerificationToken()" must {
//    "invoke customerService.createEmailVerificationToken and not update customer version in the session" in {
//      checkCustomerServiceCall(
//        _.createEmailVerificationToken(10),
//        'createEmailVerificationToken, List(initialCustomer.getId, initialCustomer.getVersion, 10),
//        initialCustomer.version,
//        testToken)
//    }
//  }
//
//  "confirmEmail()" must {
//    "invoke customerService.confirmEmail and update customer version in the session" in {
//      checkCustomerServiceCall(
//        _.confirmEmail("tokken"),
//        'confirmEmail, List(initialCustomer.getId, initialCustomer.getVersion, "tokken"))
//    }
//  }
//
//  "queryOrders()" must {
//    "invoke orderService.byCustomerId" in {
//      val orderService = mock[OrderService]
//      orderService expects 'byCustomerId withArgs (testCustomerId) returning null
//      val queryRequest = currentCustomerWith(orderService = orderService).queryOrders
//      queryRequest must be (null)
//    }
//  }
//
//  "fetch()" must {
//    "invoke customerService.byId and update customer version in the session" in {
//      val customerService = customerServiceExpectingFetch('byId, List(initialCustomer.id), Optional.of(resultCustomer))
//      val currentCustomer = currentCustomerWith(customerService)
//      val result: Customer = currentCustomer.fetch()
//      Session.current().getCustomerId.version() must be (resultCustomer.version)
//      result.getVersion must be (resultCustomer.version)
//    }
//
//    "clear customer from session if the customer no longer exists in the backend" in {
//      val customerService = customerServiceExpectingFetch('byId, List(initialCustomer.id), Optional.absent())
//      val currentCustomer = currentCustomerWith(customerService)
//      val result: Customer = currentCustomer.fetch()
//      Session.current().getCustomerId must be (null)
//      result.getName.getFirstName must be ("")       // null object returned to prevent NPEs
//    }
//  }
//
//  "queryReviews()" must {
//    "invoke reviewService.byCustomerId" in {
//      val reviewService = mock[ReviewService]
//      reviewService expects 'byCustomerId withArgs (testCustomerId) returning null
//      val queryRequest = currentCustomerWith(reviewService = reviewService).queryReviews
//      queryRequest must be (null)
//    }
//  }
//
//  "queryReviewsForProduct()" must {
//    "invoke reviewService.byCustomerIdProductId" in {
//      val reviewService = mock[ReviewService]
//      reviewService expects 'byCustomerIdProductId withArgs (testCustomerId, productId) returning null
//      val queryRequest = currentCustomerWith(reviewService = reviewService).queryReviewsForProduct(productId)
//      queryRequest must be (null)
//    }
//  }
//
//  "queryComments()" must {
//    "invoke commentService.byCustomerId" in {
//      val commentService = mock[CommentService]
//      commentService expects 'byCustomerId withArgs (testCustomerId) returning null
//      val queryRequest = currentCustomerWith(commentService = commentService).queryComments()
//      queryRequest must be (null)
//    }
//  }
//
//  "createReview()" must {
//    "invoke reviewService.createReview with the customerId from the session" in {
//      val service = reviewServiceExpectingCommand('createReview, List(productId, testCustomerId, "fritz", "title", "text", 0.5))
//      val result = currentCustomerWith(reviewService = service).createReview(productId, "fritz", "title", "text", 0.5)
//      result must be (testReview)
//    }
//  }
//
//  "createComment()" must {
//    "invoke commentService.createComment with the customerId from the session" in {
//      val service = commentServiceExpectingCommand('createComment, List(productId, testCustomerId, "fritz", "title", "text"))
//      val result = currentCustomerWith(commentService = service).createComment(productId, "fritz", "title", "text")
//      result must be (testComment)
//    }
//  }
//}
