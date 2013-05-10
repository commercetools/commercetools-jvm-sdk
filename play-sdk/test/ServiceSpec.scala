package sphere

import java.util.{Currency, UUID}

import sphere.testobjects._
import io.sphere.client.shop.model.Address
import io.sphere.client.shop._
import io.sphere.internal.ListenableFutureAdapter
import io.sphere.client.model.QueryResult
import sphere.testobjects.TestCart
import sphere.testobjects.TestCustomer

import org.scalatest.{BeforeAndAfterEach, WordSpec}
import play.mvc.Http
import com.neovisionaries.i18n.CountryCode._

// TODO migrate from ScalaMock to Mockito
//abstract class ServiceSpec extends WordSpec
//with MustMatchers
//with BeforeAndAfterEach
//with MockFactory {
//
//  val testId = UUID.randomUUID().toString
//  val testCustomerId = UUID.randomUUID().toString
//  val testCartId = UUID.randomUUID().toString
//  lazy val initialCustomer = TestCustomer(testCustomerId, 1)
//  lazy val resultCustomer = TestCustomer(testCustomerId, 2)
//  val testAddress = new Address(DE)
//  lazy val testCart = TestCart(testCartId, 1)
//  val initialTestCart = TestCart(testCartId, 1)
//  val resultTestCart = TestCart(testCartId, 2)
//  lazy val loginResultNoCart = new AuthenticatedCustomerResult(resultCustomer, null)
//  lazy val loginResultWithCart = new AuthenticatedCustomerResult(resultCustomer, resultTestCart)
//  val testReviewId = UUID.randomUUID().toString
//  val testCommentId = UUID.randomUUID().toString
//  val testReview = TestReview(testReviewId, 1)
//  val testComment = TestComment(testCommentId, 1)
//  val productId = UUID.randomUUID().toString
//
//  def newHttpContext: Http.Context = {
//    val emptyMap = new java.util.HashMap[java.lang.String,java.lang.String]()
//    new Http.Context(
//      null, null, null, emptyMap, emptyMap, new java.util.HashMap[java.lang.String,java.lang.Object]())
//  }
//  lazy val EUR = Currency.getInstance("EUR")
//
//  def customerServiceExpectingCommand[A: Manifest](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A = resultCustomer): CustomerService =
//    serviceExpectingCommand[CustomerService, A](expectedMethodCall, methodArgs, methodResult)
//
//  def cartServiceExpectingCommand[A: Manifest](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A = resultTestCart): CartService =
//    serviceExpectingCommand[CartService, A](expectedMethodCall, methodArgs, methodResult)
//
//  def reviewServiceExpectingCommand[A: Manifest](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A = testReview): ReviewService =
//    serviceExpectingCommand[ReviewService, A](expectedMethodCall, methodArgs, methodResult)
//
//  def commentServiceExpectingCommand[A: Manifest](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A = testComment): CommentService =
//    serviceExpectingCommand[CommentService, A](expectedMethodCall, methodArgs, methodResult)
//
//  private def serviceExpectingCommand[S: Manifest, A: Manifest](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A): S = {
//    val future = new ListenableFutureAdapter(MockListenableFuture.completed(methodResult))
//    val commandRequest = mock[CommandRequest[A]]
//    commandRequest expects 'executeAsync returning future
//    val service = mock[S]
//    service expects expectedMethodCall withArgs (methodArgs:_*) returning commandRequest
//    service
//  }
//
//  def orderServiceExpectingQuery[A: Manifest](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A = TestOrder): OrderService =
//    serviceExpectingQuery[OrderService, A](expectedMethodCall, methodArgs, methodResult)
//
//  def cartServiceQueryExpectingQuery[A: Manifest](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A = resultTestCart): CartService =
//    serviceExpectingQuery[CartService, A](expectedMethodCall, methodArgs, methodResult)
//
//  def customerServiceExpectingQuery[A: Manifest](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A = resultCustomer): CustomerService =
//    serviceExpectingQuery[CustomerService, A](expectedMethodCall, methodArgs, methodResult)
//
//  def reviewServiceExpectingQuery[A: Manifest](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A = testReview): ReviewService =
//    serviceExpectingQuery[ReviewService, A](expectedMethodCall, methodArgs, methodResult)
//
//
//  def commentServiceExpectingQuery[A: Manifest](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A = testComment): CommentService =
//    serviceExpectingQuery[CommentService, A](expectedMethodCall, methodArgs, methodResult)
//
//
//  def customerServiceExpectingFetch[A](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A): CustomerService =
//  {
//    val future = new ListenableFutureAdapter(MockListenableFuture.completed(methodResult))
//    val fetchRequest = mock[FetchRequest[A]]
//    fetchRequest expects 'fetchAsync returning future
//    val customerService = mock[CustomerService]
//    customerService expects expectedMethodCall withArgs (methodArgs:_*) returning fetchRequest
//    customerService
//  }
//
//  def queryResult[A](results: List[A]): QueryResult[A] ={
//    import scala.collection.JavaConversions._
//    new QueryResult(0, results.size, results.size, results)
//  }
//
//  private def serviceExpectingQuery[S: Manifest, A: Manifest](
//    expectedMethodCall: Symbol,
//    methodArgs: List[Any],
//    methodResult: A): S = {
//    val future = new ListenableFutureAdapter(MockListenableFuture.completed(methodResult))
//    val queryRequest = mock[QueryRequest[A]]
//    queryRequest expects 'fetchAsync returning future
//    val service = mock[S]
//    service expects expectedMethodCall withArgs (methodArgs:_*) returning queryRequest
//    service
//  }
//
//  override def beforeEach()  {
//    Http.Context.current.set(newHttpContext)
//  }
//}
