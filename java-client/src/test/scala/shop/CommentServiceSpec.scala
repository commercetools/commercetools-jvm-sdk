package io.sphere.client
package shop

import io.sphere.internal.command._
import io.sphere.internal.request._
import io.sphere.internal.request.QueryRequestImpl
import model._
import io.sphere.internal.util.Util
import io.sphere.internal.command.CommentCommands._
import io.sphere.internal.command.ReviewCommands.ReviewUpdateAction

import org.scalatest.{Tag, WordSpec}
import org.scalatest.matchers.MustMatchers

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
    asImpl(req).getRequestHolder.getUrl must be ("/comments/" + commentId)
    val comment = req.fetch()
    comment.get.getId must be(commentId)
  }

  "Get comments by customerId" in {
    val req = MockShopClient.create(commentsResponse = FakeResponse("{}")).comments().byCustomerId("custId")
    asImpl(req).getRequestHolder.getUrl must be ("/comments?where=" + Util.urlEncode("customerId=\"custId\""))
    req.fetch().getCount must be (0)
  }
  
  "Get comments by productId" taggedAs(Tag("fff")) in {
    val req = MockShopClient.create(commentsResponse = FakeResponse("{}")).comments().byProductId("prodId")
    asImpl(req).getRequestHolder.getUrl must be ("/comments?where=" + Util.urlEncode("productId=\"prodId\""))
    req.fetch().getCount must be (0)
  }
  
  "Create comment" in {
    val req = asImpl(commentShopClient.comments.createComment(productId, customerId, commentAuthor, commentTitle, commentText))
    req.getRequestHolder.getUrl must be("/comments")
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
    val update = new CommentUpdate()
    update.setAuthorName("name")
    update.setTitle("title")
    update.setText("text")
    val req = asImpl(commentShopClient.comments().updateComment(commentId, 1, update))
    req.getRequestHolder.getUrl must be("/comments/" + commentId)
    val cmd = req.getCommand.asInstanceOf[UpdateCommand[ReviewUpdateAction]]
    cmd.getVersion must be (1)
    val actions = scala.collection.JavaConversions.asScalaBuffer((cmd.getActions)).toList
    actions.length must be (3)
    actions(0).asInstanceOf[SetAuthorName].getAuthorName must be ("name")
    actions(1).asInstanceOf[SetTitle].getTitle must be ("title")
    actions(2).asInstanceOf[SetText].getText must be ("text")
    val comment: Comment = req.execute()
    comment.getId must be(commentId)
  }
}
