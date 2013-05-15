package sphere;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.SphereException;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.CommentService;
import io.sphere.client.shop.CustomerService;
import io.sphere.client.shop.OrderService;
import io.sphere.client.shop.ReviewService;
import io.sphere.client.shop.model.*;
import io.sphere.internal.util.Log;
import net.jcip.annotations.ThreadSafe;
import play.libs.F.Promise;
import sphere.util.Async;

import javax.annotation.Nullable;

/** Project customer service that automatically accesses the customer associated to the current HTTP session.
 *
 *  <p>After calling {@link sphere.SphereClient#logout()}, any existing CurrentCustomer instance is not valid any more
 *  and will throw {@link IllegalStateException}.
 *
 *  <p>Therefore, don't keep instances of this class around, but always use {@link sphere.SphereClient#currentCustomer()}
 *  to get an up-to-date instance or null, if noone is logged in.
 * */
@ThreadSafe
public class CurrentCustomer {
    private final Session session;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final CommentService commentService;
    private final ReviewService reviewService;

    private CurrentCustomer(Session session,
                            CustomerService customerService,
                            OrderService orderService,
                            CommentService commentService,
                            ReviewService reviewService) {
        this.session = session;
        this.customerService = customerService;
        this.orderService = orderService;
        this.commentService = commentService;
        this.reviewService = reviewService;
    }

    private VersionedId getIdAndVersion() {
        final VersionedId sessionCustomerId = session.getCustomerId();
        if (sessionCustomerId != null) {
            return sessionCustomerId;
        }
        throw new SphereException(
                "This CurrentCustomer instance is not valid anymore. Please don't hold references to CurrentCustomer instances " +
                "after calling logout(). Instead, always use SphereClient.currentCustomer() to get an up-to-date instance, or null.");
    }

    /** If a customer is logged in, returns a {@link CurrentCustomer} instance. If no customer is logged in, returns null. */
    public static CurrentCustomer createFromSession(CustomerService customerService,
                                                    OrderService orderService,
                                                    CommentService commentService,
                                                    ReviewService reviewService) {
        final Session session = Session.current();
        final VersionedId sessionCustomerId = session.getCustomerId();
        if (sessionCustomerId == null) {
            return null;
        }
        return new CurrentCustomer(session, customerService, orderService, commentService, reviewService);
    }

    /** Fetches the currently authenticated {@link Customer}.
     * @return Customer or null if no customer is authenticated. */
    public Customer fetch() {
        return Async.await(fetchAsync());
    }

    /** Fetches the currently authenticated {@link Customer} asynchronously.
     * @return Customer or null if no customer is authenticated. */
    public Promise<Customer> fetchAsync() {
        final VersionedId versionedId = getIdAndVersion();
        Log.trace(String.format("[customer] Fetching customer %s.", versionedId.getId()));
        ListenableFuture<Customer> customerFuture = Futures.transform(customerService.byId(versionedId.getId()).fetchAsync(), new Function<Optional<Customer>, Customer>() {
            public Customer apply(@Nullable Optional<Customer> customer) {
                assert customer != null;
                if (!customer.isPresent()) {
                    session.clearCustomer();     // the customer was probably deleted, clear it from this old session
                    return new Customer("", 0);  // 'null object' to prevent NPEs on e.g. CurrentCustomer.fetch().getName()
                }
                return customer.get();
            }
        });
        return Async.asPlayPromise(Session.withCustomerIdAndVersion(customerFuture, session));
    }

    /** Changes customer's password. */
    public boolean changePassword(String currentPassword, String newPassword) {
        return Async.await(changePasswordAsync(currentPassword, newPassword)).isPresent();
    }

    /** Changes customer's password asynchronously. */
    public Promise<Optional<Customer>> changePasswordAsync(String currentPassword, String newPassword){
        final VersionedId idV = getIdAndVersion();
        return Async.asPlayPromise(executeAsyncOptional(
                customerService.changePassword(idV, currentPassword, newPassword),
                String.format("[customer] Changing password for customer %s.", idV.getId())));
    }

    /** Updated the currently authenticated customer. */
    public Customer update(CustomerUpdate update) {
        return Async.await(updateAsync(update));
    }

    /** Updated the currently authenticated customer. */
    public Promise<Customer> updateAsync(CustomerUpdate update){
        final VersionedId idV = getIdAndVersion();
        return Async.asPlayPromise(executeAsync(
                customerService.update(idV, update),
                String.format("[customer] Updating customer %s.", idV.getId())));
    }

    /** Creates a token used to verify customer's email. */
    public CustomerToken createEmailVerificationToken(int ttlMinutes) {
        return Async.await(createEmailVerificationTokenAsync(ttlMinutes));
    }

    /** Creates a token used to verify customer's email asynchronously.
     *
     * @param ttlMinutes Validity of the token in minutes. */
    public Promise<CustomerToken> createEmailVerificationTokenAsync(int ttlMinutes){
        final VersionedId idV = getIdAndVersion();
        Log.trace(String.format("[customer] Creating email verification token for customer %s.", idV.getId()));
        return Async.execute(customerService.createEmailVerificationToken(idV, ttlMinutes));
    }

    /** Sets {@link Customer#isEmailVerified} to true.
     *
     * Requires a token that was previously generated using the {@link #createEmailVerificationToken} method. */
    public Customer confirmEmail(String token) {
        return Async.await(confirmEmailAsync(token));
    }

    /** Sets {@link Customer#isEmailVerified} to true asynchronously.
     *
     * Requires a token that was previously generated using the {@link #createEmailVerificationToken} method. */
    public Promise<Customer> confirmEmailAsync(String token){
        final VersionedId idV = getIdAndVersion();
        return Async.asPlayPromise(executeAsync(
                customerService.confirmEmail(idV, token),
                String.format("[customer] Confirming email for customer %s.", idV.getId())));
    }

    // --------------------------------------
    // Orders
    // --------------------------------------

    /** Queries all orders of given customer. */
    public QueryRequest<Order> orders() {
        final VersionedId idV = getIdAndVersion();
        Log.trace(String.format("[customer] Getting orders of customer %s.", idV.getId()));
        return Async.adapt(orderService.byCustomerId(idV.getId()));
    }


    // --------------------------------------
    // Reviews
    // --------------------------------------

    /** Queries all reviews of the current. */
    public QueryRequest<Review> reviews() {
        final VersionedId idV = getIdAndVersion();
        Log.trace(String.format("[customer] Getting reviews of customer %s.", idV.getId()));
        return Async.adapt(reviewService.byCustomerId(idV.getId()));
    }

    /** Queries all reviews of the current customer for a specific product. */
    public QueryRequest<Review> reviewsForProduct(String productId) {
       final VersionedId idV = getIdAndVersion();
        Log.trace(String.format("[customer] Getting reviews of customer %s on a product.", idV.getId(), productId));
        return Async.adapt(reviewService.byCustomerIdProductId(idV.getId(), productId));
    }

    /** Creates a review. At least one of the three optional parameters (title, text, score) must be set. */
    public Review createReview(String productId, String authorName, String title, String text, Double score) {
        return Async.await(createReviewAsync(productId, authorName, title, text, score));
    }

    /** Creates a review asynchronously. At least one of the three optional parameters (title, text, score) must be set. */
    public Promise<Review> createReviewAsync(String productId, String authorName, String title, String text, Double score) {
        final VersionedId idV = getIdAndVersion();
        Log.trace(String.format("[customer] Creating a review for customer %s.", idV.getId()));
        return Async.execute(reviewService.createReview(productId, idV.getId(), authorName, title, text, score));
    }

    // --------------------------------------
    // Comments
    // --------------------------------------

    /** Queries all comments that the current customer created. */
    public QueryRequest<Comment> comments() {
        final VersionedId idV = getIdAndVersion();
        Log.trace(String.format("[customer] Getting comments of customer %s.", idV.getId()));
        return Async.adapt(commentService.byCustomerId(idV.getId()));
    }

    /** Creates a comment. At least one of the two optional parameters (title, text) must be set. */
    public Comment createComment(String productId, String authorName, String title, String text) {
        return Async.await(createCommentAsync(productId, authorName, title, text));
    }

    /** Creates a comment asynchronously. At least one of the two optional parameters (title, text) must be set. */
    public Promise<Comment> createCommentAsync(String productId, String authorName, String title, String text) {
        final VersionedId idV = getIdAndVersion();
        Log.trace(String.format("[customer] Creating a comment for customer %s.", idV.getId()));
        return Async.execute(commentService.createComment(productId, idV.getId(), authorName, title, text));
    }

    // --------------------------------------
    // Command helpers
    // --------------------------------------

    private ListenableFuture<Customer> executeAsync(io.sphere.client.CommandRequest<Customer> commandRequest, String logMessage) {
        Log.trace(logMessage);
        return Session.withCustomerIdAndVersion(commandRequest.executeAsync(), session);
    }

    private ListenableFuture<Optional<Customer>> executeAsyncOptional(io.sphere.client.CommandRequest<Optional<Customer>> commandRequest, String logMessage) {
        Log.trace(logMessage);
        return Session.withCustomerIdAndVersionOptional(commandRequest.executeAsync(), session);
    }
}
