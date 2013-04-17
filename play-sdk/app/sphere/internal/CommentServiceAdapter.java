package sphere.internal;

import io.sphere.client.shop.model.Comment;
import net.jcip.annotations.Immutable;
import sphere.CommandRequest;
import sphere.CommentService;
import sphere.FetchRequest;
import sphere.QueryRequest;
import sphere.util.Async;

import javax.annotation.Nonnull;

/** CommentService with Play-specific async methods.
 * Additional methods are exposed via {@link sphere.SphereClient#currentCustomer()}. */
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

    @Override public QueryRequest<Comment> all() {
        return Async.adapt(service.all());
    }

    @Override public QueryRequest<Comment> byProductId(String productId) {
        return Async.adapt(service.byProductId(productId));
    }

    @Override public CommandRequest<Comment> updateComment(String commentId, int commentVersion, String title, String text) {
        return Async.adapt(service.updateComment(commentId, commentVersion, title, text));
    }
}
