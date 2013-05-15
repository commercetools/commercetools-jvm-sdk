package io.sphere.client
package shop

import io.sphere.internal.command._
import io.sphere.internal.request._
import io.sphere.internal.request.QueryRequestImpl
import model._
import io.sphere.internal.command.ReviewCommands._


import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import io.sphere.internal.util.Util

class ReviewServiceSpec extends WordSpec with MustMatchers {

  import JsonTestObjects._

  val sphere = MockSphereClient.create(reviewsResponse = FakeResponse(reviewJson))

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(req: FetchRequest[Review]) = req.asInstanceOf[FetchRequestImpl[Review]]
  private def asImpl(req: QueryRequest[Review]) = req.asInstanceOf[QueryRequestImpl[Review]]
  private def asImpl(req: CommandRequest[Review]) = req.asInstanceOf[CommandRequestImpl[Review]]

  private def checkIdAndVersion(cmd: CommandBase) {
    cmd.getId must be (reviewId)
    cmd.getVersion must be (1)
  }

  "Get all reviews" in {
    val shopClient = MockSphereClient.create(reviewsResponse = FakeResponse("{}"))
    shopClient.reviews.all().fetch.getCount must be(0)
  }

  "Get review byId" in {
    val req = sphere.reviews.byId(reviewId)
    asImpl(req).getRequestHolder.getUrl must be ("/reviews/" + reviewId)
    val review = req.fetch()
    review.get.getId must be(reviewId)
  }

  "Get reviews by customerId" in {
    val req = MockSphereClient.create(reviewsResponse = FakeResponse("{}")).reviews().byCustomerId("custId")
    asImpl(req).getRequestHolder.getUrl must be ("/reviews?where=" + Util.urlEncode("customerId=\"custId\""))
    req.fetch().getCount must be (0)
  }

  "Get reviews by productId" in {
    val req = MockSphereClient.create(reviewsResponse = FakeResponse("{}")).reviews().byProductId("prodId")
    asImpl(req).getRequestHolder.getUrl must be ("/reviews?where=" + Util.urlEncode("productId=\"prodId\""))
    req.fetch().getCount must be (0)
  }

  "Get reviews by customerId and productId" in {
    val req = MockSphereClient.create(reviewsResponse = FakeResponse("{}")).reviews().byCustomerIdProductId("custId", "prodId")
    asImpl(req).getRequestHolder.getUrl must be ("/reviews?where=" + Util.urlEncode("customerId=\"custId\" and productId=\"prodId\""))
    req.fetch().getCount must be (0)
  }

  "Create review" in {
    val req = asImpl(sphere.reviews.createReview(productId, customerId, reviewAuthor, reviewTitle, reviewText, 0.5))
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
    val update = new ReviewUpdate()
    update.setAuthor("name")
    update.setTitle("title")
    update.setText("text")
    update.setScore(0.2)
    val req = asImpl(sphere.reviews().updateReview(v1(reviewId), update))
    req.getRequestHolder.getUrl must be("/reviews/" + reviewId)
    val cmd = req.getCommand.asInstanceOf[UpdateCommand[ReviewUpdateAction]]
    cmd.getVersion must be (1)
    val actions = scala.collection.JavaConversions.asScalaBuffer(cmd.getActions).toList
    actions.length must be (4)
    actions(0).asInstanceOf[SetAuthorName].getAuthorName must be ("name")
    actions(1).asInstanceOf[SetTitle].getTitle must be ("title")
    actions(2).asInstanceOf[SetText].getText must be ("text")
    actions(3).asInstanceOf[SetScore].getScore must be (0.2)
    val review: Review = req.execute()
    review.getId must be(reviewId)
  }
}
