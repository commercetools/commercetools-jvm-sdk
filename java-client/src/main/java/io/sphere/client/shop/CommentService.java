package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.shop.model.Comment;
import io.sphere.client.shop.model.CommentUpdate;

/** Sphere HTTP API for working with product comments in a given project. */
public interface CommentService {
    /** Finds a comment by id. */
    FetchRequest<Comment> byId(String id);

    /** Queries all comments. */
    QueryRequest<Comment> all();

    /** Queries all comments by given customer. */
    public QueryRequest<Comment> byCustomerId(String customerId);

    /** Queries all comments for a specific product. */
    public QueryRequest<Comment> byProductId(String productId);

    /** Creates a comment. At least one of (title, text) must be set. */
    public CommandRequest<Comment> createComment(String productId, String customerId, String authorName, String title, String text);

    /** Updates a comment. At least one of (title, text) must be set. */
    public CommandRequest<Comment> updateComment(String commentId, int commentVersion, CommentUpdate update);
}
