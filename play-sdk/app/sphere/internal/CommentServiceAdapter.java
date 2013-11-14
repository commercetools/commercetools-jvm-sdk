package sphere.internal;

import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Comment;
import io.sphere.client.shop.model.CommentUpdate;
import net.jcip.annotations.Immutable;
import play.libs.F.Promise;
import sphere.CommentService;
import sphere.FetchRequest;
import sphere.QueryRequest;
import sphere.util.Async;

import javax.annotation.Nonnull;

/** CommentService with Play-specific async methods.
 * Additional methods are exposed via {@link sphere.Sphere#currentCustomer()}. */
@Immutable
public class CommentServiceAdapter implements CommentService {
    private final io.sphere.client.shop.CommentService service;
    public CommentServiceAdapter(@Nonnull io.sphere.client.shop.CommentService service) {
        if (service == null) throw new NullPointerException("service");
        this.service = service;
    }

    @Override public FetchRequest<Comment> byId(String id) {
        return Async.adapt(service.byId(id));
    }

    @Deprecated
    @Override public QueryRequest<Comment> all() {
        return query();
    }

    @Override public QueryRequest<Comment> query() {
        return Async.adapt(service.query());
    }

    @Override public QueryRequest<Comment> byProductId(String productId) {
        return Async.adapt(service.forProduct(productId));
    }

    @Override public Comment updateComment(VersionedId commentId, CommentUpdate update) {
        return Async.awaitResult(updateCommentAsync(commentId, update));
    }

    @Override public Promise<SphereResult<Comment>> updateCommentAsync(VersionedId commentId, CommentUpdate update) {
        return Async.execute(service.updateComment(commentId, update));
    }
}
