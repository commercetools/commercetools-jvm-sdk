package sphere;

import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Comment;
import io.sphere.client.shop.model.CommentUpdate;
import play.libs.F.Promise;

/** Sphere HTTP API for working with product comments in a given project.
 *
 * <p>For additional methods related to product reviews and the currently authenticated customer,
 * see {@link Sphere#currentCustomer()}. */
public interface CommentService {
    /** Finds a comment by id. */
    FetchRequest<Comment> byId(String id);

    /** Queries all comments. */
    QueryRequest<Comment> all();

    /** Queries all comments for a specific product. */
    public QueryRequest<Comment> byProductId(String productId);

    /** Updates a comment. At least one of (title, text) must be set. */
    public Comment updateComment(VersionedId commentId, CommentUpdate update);

    /** Updates a comment asynchronously. At least one of (title, text) must be set. */
    public Promise<SphereResult<Comment>> updateCommentAsync(VersionedId commentId, CommentUpdate update);
}
