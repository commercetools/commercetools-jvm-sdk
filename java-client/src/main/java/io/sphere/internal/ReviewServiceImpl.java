package io.sphere.internal;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.ReviewService;
import io.sphere.client.shop.model.Review;
import io.sphere.client.shop.model.ReviewUpdate;
import io.sphere.internal.command.Command;
import io.sphere.internal.command.ReviewCommands;
import io.sphere.internal.command.UpdateCommand;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;

public class ReviewServiceImpl extends ProjectScopedAPI implements ReviewService {
    private final RequestFactory requestFactory;

    public ReviewServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** {@inheritDoc}  */
    public FetchRequest<Review> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.reviews.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<Review>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Review> all() {
        return requestFactory.createQueryRequest(
                endpoints.reviews.root(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Review>>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Review> byCustomerId(String customerId) {
        return requestFactory.createQueryRequest(
                endpoints.reviews.queryByCustomerId(customerId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Review>>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Review> byCustomerIdProductId(String customerId, String productId) {
        return requestFactory.createQueryRequest(
                endpoints.reviews.queryByCustomerIdProductId(customerId, productId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Review>>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Review> byProductId(String productId) {
        return requestFactory.createQueryRequest(
                endpoints.reviews.queryByProductId(productId),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Review>>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Review> createReview(
            String productId,
            String customerId,
            String authorName,
            String title,
            String text,
            Double score) {
        return createCommandRequest(
                endpoints.reviews.root(),
                new ReviewCommands.CreateReview(productId, customerId, authorName, title, text, score));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Review> updateReview(String reviewId, int reviewVersion, ReviewUpdate update) {
        return createCommandRequest(
                endpoints.reviews.byId(reviewId),
                new UpdateCommand<ReviewCommands.ReviewUpdateAction>(reviewVersion, update));
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<Review> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<Review>() {});
    }
}
