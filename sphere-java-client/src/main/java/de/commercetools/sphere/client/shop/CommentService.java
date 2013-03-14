package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.shop.model.*;

/** Sphere HTTP API for working with comments in a given project. */
public interface CommentService {
    /** Creates a request that finds a comment by given id. */
    FetchRequest<Comment> byId(String id);

    /** Creates a request that queries all comments. */
    QueryRequest<Comment> all();

    /** Creates a request builder that queries all comments of the given customer. */
    public QueryRequest<Comment> byCustomerId(String customerId);

    /** Creates a request builder that queries all comments of the given product. */
    public QueryRequest<Comment> byProductId(String productId);

    /** Creates a comment. At least one of the two optional parameters (title, text) must be set. */
    public CommandRequest<Comment> createComment(String productId, String customerId, String authorName, String title, String text);

    /** Updates a comment. At least one of the two optional parameters (title, text) must be set. */
    public CommandRequest<Comment> updateComment(String commentId, int commentVersion, String title, String text);
}