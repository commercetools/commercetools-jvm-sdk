package de.commercetools.internal;

import de.commercetools.internal.command.Command;
import de.commercetools.internal.command.CommentCommands;
import de.commercetools.internal.request.RequestFactory;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.CommentService;
import de.commercetools.sphere.client.shop.model.Comment;
import org.codehaus.jackson.type.TypeReference;

public class CommentServiceImpl extends ProjectScopedAPI implements CommentService {
    private final RequestFactory requestFactory;

    public CommentServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** {@inheritDoc}  */
    public FetchRequest<Comment> byId(String id) {
        return requestFactory.createFetchRequest(endpoints.comments.byId(id), new TypeReference<Comment>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Comment> all() {
        return requestFactory.createQueryRequest(endpoints.comments.root(), new TypeReference<QueryResult<Comment>>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Comment> byCustomerId(String customerId) {
        return requestFactory.createQueryRequest(
                endpoints.comments.queryByCustomerId(customerId),
                new TypeReference<QueryResult<Comment>>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Comment> createComment(String productId, String customerId, String title, String text) {
        return createCommandRequest(
                endpoints.comments.root(),
                new CommentCommands.CreateComment(productId, customerId, title, text));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Comment> updateComment(String commentId, int commentVersion, String title, String text) {
        return createCommandRequest(
                endpoints.comments.update(),
                new CommentCommands.UpdateComment(commentId, commentVersion, title, text));
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<Comment> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<Comment>() {});
    }
}
