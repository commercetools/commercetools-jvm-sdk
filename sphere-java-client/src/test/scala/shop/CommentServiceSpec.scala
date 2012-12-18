package de.commercetools.sphere.client
package shop

import de.commercetools.internal.command._
import de.commercetools.internal.request._
import de.commercetools.internal.request.QueryRequestImpl
import model._

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import de.commercetools.internal.util.Util

class CommentServiceSpec extends WordSpec with MustMatchers {

  import JsonTestObjects._

  val commentShopClient = MockShopClient.create(commentsResponse = FakeResponse(commentJson))

  // downcast to be able to test some request properties which are not public for shop developers
  private def asImpl(req: FetchRequest[Comment]) = req.asInstanceOf[FetchRequestImpl[Comment]]
  private def asImpl(req: QueryRequest[Comment]) = req.asInstanceOf[QueryRequestImpl[Comment]]
  private def asImpl(req: CommandRequest[Comment]) = req.asInstanceOf[CommandRequestImpl[Comment]]

  private def checkIdAndVersion(cmd: CommandBase) {
    cmd.getId must be (commentId)
    cmd.getVersion must be (1)
  }

  "Get all comments" in {
    val shopClient = MockShopClient.create(commentsResponse = FakeResponse("{}"))
    shopClient.comments.all().fetch.getCount must be(0)
  }

  "Get comment byId" in {
    val req = commentShopClient.comments.byId(commentId)
    asImpl(req).getUrl must be ("/comments/" + commentId)
    val comment = req.fetch()
    comment.get.getId must be(commentId)
  }

  "Get comments by customerId" in {
    val req = MockShopClient.create(commentsResponse = FakeResponse("{}")).comments().byCustomerId("custId")
    asImpl(req).getUrl must be ("/comments?where=" + Util.encodeUrl("customerId=\"custId\""))
    req.fetch().getCount must be (0)
  }
  
  "Get comments by productId" in {
    val req = MockShopClient.create(commentsResponse = FakeResponse("{}")).comments().byProductId("prodId")
    asImpl(req).getUrl must be ("/comments?where=" + Util.encodeUrl("productId=\"prodId\""))
    req.fetch().getCount must be (0)
  }
  
  "Create comment" in {
    val req = asImpl(commentShopClient.comments.createComment(productId, customerId, commentAuthor, commentTitle, commentText))
    req.getUrl must be("/comments")
    val cmd = req.getCommand.asInstanceOf[CommentCommands.CreateComment]
    cmd.getCustomerId must be (customerId)
    cmd.getProductId must be (productId)
    cmd.getAuthorName must be (commentAuthor)
    cmd.getTitle must be (commentTitle)
    cmd.getText must be (commentText)
    val comment: Comment = req.execute()
    comment.getId must be(commentId)
  }

  "Update Comment" in {
    val req = asImpl(commentShopClient.comments().updateComment(commentId, 1, commentTitle, commentText))
    req.getUrl must be("/comments/update")
    val cmd = req.getCommand.asInstanceOf[CommentCommands.UpdateComment]
    checkIdAndVersion(cmd)
    cmd.getTitle must be (commentTitle)
    cmd.getText must be (commentText)
    val comment: Comment = req.execute()
    comment.getId must be(commentId)
  }
}
