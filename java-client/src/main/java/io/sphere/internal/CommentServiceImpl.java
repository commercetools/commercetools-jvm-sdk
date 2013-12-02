package io.sphere.internal;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.CommentService;
import io.sphere.client.shop.model.Comment;
import io.sphere.client.shop.model.CommentUpdate;
import io.sphere.internal.command.CommentCommands;
import io.sphere.internal.command.UpdateCommand;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;

public class CommentServiceImpl extends ProjectScopedAPI<Comment> implements CommentService {
    public CommentServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(requestFactory, endpoints, new TypeReference<Comment>() {}, new TypeReference<QueryResult<Comment>>() { } );
    }

    @Override public FetchRequest<Comment> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.comments.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<Comment>() {});
    }

    @Deprecated
    @Override public QueryRequest<Comment> all() {
        return query();
    }

    @Override public QueryRequest<Comment> query() {
        return queryImpl(endpoints.comments.root());
    }

    @Override public QueryRequest<Comment> forCustomer(String customerId) {
        return requestFactory.createQueryRequest(
                endpoints.comments.queryByCustomerId(customerId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Comment>>() {});
    }

    @Override public QueryRequest<Comment> forProduct(String productId) {
        return requestFactory.createQueryRequest(
                endpoints.comments.queryByProductId(productId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Comment>>() {});
    }
    
    @Override public CommandRequest<Comment> createComment(String productId, String customerId, String authorName, String title, String text) {
        return createCommandRequest(
                endpoints.comments.root(),
                new CommentCommands.CreateComment(productId, customerId, authorName, title, text));
    }

    @Override public CommandRequest<Comment> updateComment(VersionedId commentId, CommentUpdate update) {
        return createCommandRequest(
                endpoints.comments.byId(commentId.getId()),
                new UpdateCommand<CommentCommands.CommentUpdateAction>(commentId.getVersion(), update));
    }
}
