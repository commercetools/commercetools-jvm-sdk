package io.sphere.internal;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.CommentService;
import io.sphere.client.shop.model.Comment;
import io.sphere.internal.command.Command;
import io.sphere.internal.command.CommentCommands;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;

public class CommentServiceImpl extends ProjectScopedAPI implements CommentService {
    private final RequestFactory requestFactory;

    public CommentServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** {@inheritDoc}  */
    public FetchRequest<Comment> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.comments.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<Comment>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Comment> all() {
        return requestFactory.createQueryRequest(
                endpoints.comments.root(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Comment>>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Comment> byCustomerId(String customerId) {
        return requestFactory.createQueryRequest(
                endpoints.comments.queryByCustomerId(customerId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Comment>>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Comment> byProductId(String productId) {
        return requestFactory.createQueryRequest(
                endpoints.comments.queryByProductId(productId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Comment>>() {});
    }
    
    /** {@inheritDoc}  */
    public CommandRequest<Comment> createComment(String productId, String customerId, String authorName, String title, String text) {
        return createCommandRequest(
                endpoints.comments.root(),
                new CommentCommands.CreateComment(productId, customerId, authorName, title, text));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Comment> updateComment(String commentId, int commentVersion, String title, String text) {
        return createCommandRequest(
                endpoints.comments.byId(commentId),
                new CommentCommands.UpdateComment(commentVersion, title, text));
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<Comment> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<Comment>() {});
    }
}
