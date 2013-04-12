package io.sphere.client
package shop

import io.sphere.internal.command._
import io.sphere.internal.request._
import io.sphere.internal.request.QueryRequestImpl
import model._

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import io.sphere.internal.util.Util

class ReviewServiceSpec extends WordSpec with MustMatchers {

  import JsonTestObjects._

  val reviewShopClient = MockShopClient.create(reviewsResponse = FakeResponse(reviewJson))


  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(req: FetchRequest[Review]) = req.asInstanceOf[FetchRequestImpl[Review]]
  private def asImpl(req: QueryRequest[Review]) = req.asInstanceOf[QueryRequestImpl[Review]]
  private def asImpl(req: CommandRequest[Review]) = req.asInstanceOf[CommandRequestImpl[Review]]

  private def checkIdAndVersion(cmd: CommandBase) {
    cmd.getId must be (reviewId)
    cmd.getVersion must be (1)
  }

  "Get all reviews" in {
    val shopClient = MockShopClient.create(reviewsResponse = FakeResponse("{}"))
    shopClient.reviews.all().fetch.getCount must be(0)
  }

  "Get review byId" in {
    val req = reviewShopClient.reviews.byId(reviewId)
    asImpl(req).getRequestHolder.getUrl must be ("/reviews/" + reviewId)
    val review = req.fetch()
    review.get.getId must be(reviewId)
  }

  "Get reviews by customerId" in {
    val req = MockShopClient.create(reviewsResponse = FakeResponse("{}")).reviews().byCustomerId("custId")
    asImpl(req).getRequestHolder.getUrl must be ("/reviews?where=" + Util.urlEncode("customerId=\"custId\""))
    req.fetch().getCount must be (0)
  }

  "Get reviews by productId" in {
    val req = MockShopClient.create(reviewsResponse = FakeResponse("{}")).reviews().byProductId("prodId")
    asImpl(req).getRequestHolder.getUrl must be ("/reviews?where=" + Util.urlEncode("productId=\"prodId\""))
    req.fetch().getCount must be (0)
  }

  "Get reviews by customerId and productId" in {
    val req = MockShopClient.create(reviewsResponse = FakeResponse("{}")).reviews().byCustomerIdProductId("custId", "prodId")
    asImpl(req).getRequestHolder.getUrl must be ("/reviews?where=" + Util.urlEncode("customerId=\"custId\" and productId=\"prodId\""))
    req.fetch().getCount must be (0)
  }

  "Create review" in {
    val req = asImpl(reviewShopClient.reviews.createReview(productId, customerId, reviewAuthor, reviewTitle, reviewText, 0.5))
    req.getRequestHolder.getUrl must be("/reviews")
    val cmd = req.getCommand.asInstanceOf[ReviewCommands.CreateReview]
    cmd.getCustomerId must be (customerId)
    cmd.getProductId must be (productId)
    cmd.getAuthorName must be (reviewAuthor)
    cmd.getTitle must be (reviewTitle)
    cmd.getText must be (reviewText)
    cmd.getScore must be (0.5)
    val review: Review = req.execute()
    review.getId must be(reviewId)
  }

  "Update Review" in {
    val req = asImpl(reviewShopClient.reviews().updateReview(reviewId, 1, reviewTitle, reviewText, 0.5))
    req.getRequestHolder.getUrl must be("/reviews/" + reviewId)
    val cmd = req.getCommand.asInstanceOf[ReviewCommands.UpdateReview]
    cmd.getVersion must be (1)
    cmd.getTitle must be (reviewTitle)
    cmd.getText must be (reviewText)
    cmd.getScore must be (0.5)
    val review: Review = req.execute()
    review.getId must be(reviewId)
  }
}
