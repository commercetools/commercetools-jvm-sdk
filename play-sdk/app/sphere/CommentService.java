package sphere;

import io.sphere.client.shop.model.Comment;

/** Sphere HTTP API for working with product comments in a given project.
 *
 * <p>For additional methods related to product reviews and the currently authenticated customer,
 * see {@link sphere.SphereClient#currentCustomer()}. */
public interface CommentService {
    /** Finds a comment by id. */
    FetchRequest<Comment> byId(String id);

    /** Queries all comments. */
    QueryRequest<Comment> all();

    /** Queries all comments for a specific product. */
    public QueryRequest<Comment> byProductId(String productId);

    /** Updates a comment. At least one of the two optional parameters (title, text) must be set. */
    public CommandRequest<Comment> updateComment(String commentId, int commentVersion, String title, String text);
}
