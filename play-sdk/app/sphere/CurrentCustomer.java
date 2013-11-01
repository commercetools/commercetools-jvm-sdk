package sphere;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.SphereClientException;
import io.sphere.client.SphereResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.CommentService;
import io.sphere.client.shop.CustomerService;
import io.sphere.client.shop.OrderService;
import io.sphere.client.shop.ReviewService;
import io.sphere.client.shop.model.*;
import io.sphere.internal.util.Log;
import io.sphere.client.exceptions.*;
import net.jcip.annotations.ThreadSafe;
import play.libs.F.Promise;
import sphere.util.Async;

import javax.annotation.Nullable;

/** Project customer service that automatically accesses the customer associated to the current HTTP session.
 *
 *  <p>After calling {@link Sphere#logout() Sphere.logout}, any existing CurrentCustomer instance is not valid any more
 *  and will throw {@code IllegalStateException}.
 *
 *  <p>Therefore, don't keep instances of this class around, but always use {@link Sphere#currentCustomer() Sphere.currentCustomer}
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
        throw new SphereClientException(
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
     *  @return Customer or null if no customer is authenticated. */
    public Customer fetch() {
        return Async.await(fetchAsync());
    }

    /** Fetches the currently authenticated {@link Customer} asynchronously.
     *  @return Customer or null if no customer is authenticated. */
    public Promise<Customer> fetchAsync() {
        final VersionedId customerId = getIdAndVersion();
        Log.trace(String.format("[customer] Fetching customer %s.", customerId.getId()));
        return Async.asPlayPromise(Futures.transform(customerService.byId(customerId.getId()).expand("customerGroup").
                fetchAsync(), new Function<Optional<Customer>, Customer>() {
            public Customer apply(@Nullable Optional<Customer> customer) {
                assert customer != null;
                if (!customer.isPresent()) {
                    session.clearCustomer();     // the customer was probably deleted, clear it from this old session
                    return new Customer("", 0);  // 'null object' to prevent NPEs on e.g. CurrentCustomer.fetch().getName()
                }
                return customer.get();
            }
        }));
    }

    /** Changes customer's password.
     *
     *  @throws InvalidPasswordException if the current password is invalid. */
    public void changePassword(String currentPassword, String newPassword) {
        Async.awaitResult(changePasswordAsync(currentPassword, newPassword));
    }

    /** Changes customer's password asynchronously.
     *
     *  @return A result which can fail with the following exceptions:
     *  <ul>
     *    <li>{@link InvalidPasswordException} if the current password is invalid.
     *  </ul>*/
    public Promise<SphereResult<Customer>> changePasswordAsync(String currentPassword, String newPassword){
        final VersionedId idV = getIdAndVersion();
        return Async.asPlayPromise(executeAsync(
                customerService.changePassword(idV, currentPassword, newPassword),
                String.format("[customer] Changing password for customer %s.", idV.getId())));
    }

    /** Updates the currently authenticated customer. */
    public Customer update(CustomerUpdate update) {
        return Async.awaitResult(updateAsync(update));
    }

    /** Updates the currently authenticated customer asynchronously. */
    public Promise<SphereResult<Customer>> updateAsync(CustomerUpdate update){
        final VersionedId idV = getIdAndVersion();
        return Async.asPlayPromise(executeAsync(
                customerService.update(idV, update),
                String.format("[customer] Updating customer %s.", idV.getId())));
    }

    /** Creates a token to verify customer's email. */
    public CustomerToken createEmailVerificationToken(int ttlMinutes) {
        return Async.awaitResult(createEmailVerificationTokenAsync(ttlMinutes));
    }

    /** Creates a token to verify customer's email asynchronously.
     *
     * @param ttlMinutes Validity of the token in minutes. */
    public Promise<SphereResult<CustomerToken>> createEmailVerificationTokenAsync(int ttlMinutes){
        final VersionedId idV = getIdAndVersion();
        Log.trace(String.format("[customer] Creating email verification token for customer %s.", idV.getId()));
        return Async.execute(customerService.createEmailVerificationToken(idV, ttlMinutes));
    }

    /** Sets {@link Customer#isEmailVerified} to true.
     *
     * Requires a token that was previously generated using the {@link #createEmailVerificationToken} method. */
    public Customer confirmEmail(String token) {
        return Async.awaitResult(confirmEmailAsync(token));
    }

    /** Sets {@link Customer#isEmailVerified} to true asynchronously.
     *
     * Requires a token that was previously generated using the {@link #createEmailVerificationToken} method. */
    public Promise<SphereResult<Customer>> confirmEmailAsync(String token){
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
        return Async.adapt(orderService.forCustomer(idV.getId()));
    }


    // --------------------------------------
    // Reviews
    // --------------------------------------

    /** Queries all reviews of the current. */
    public QueryRequest<Review> reviews() {
        final VersionedId idV = getIdAndVersion();
        Log.trace(String.format("[customer] Getting reviews of customer %s.", idV.getId()));
        return Async.adapt(reviewService.forCustomer(idV.getId()));
    }

    /** Queries all reviews of the current customer for a specific product. */
    public QueryRequest<Review> reviewsForProduct(String productId) {
       final VersionedId idV = getIdAndVersion();
        Log.trace(String.format("[customer] Getting reviews of customer %s on a product.", idV.getId(), productId));
        return Async.adapt(reviewService.forCustomerAndProduct(idV.getId(), productId));
    }

    /** Creates a review. At least one of the three optional parameters (title, text, score) must be set. */
    public Review createReview(String productId, String authorName, String title, String text, Double score) {
        return Async.awaitResult(createReviewAsync(productId, authorName, title, text, score));
    }

    /** Creates a review asynchronously. At least one of the three optional parameters (title, text, score) must be set. */
    public Promise<SphereResult<Review>> createReviewAsync(String productId, String authorName, String title, String text, Double score) {
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
        return Async.adapt(commentService.forCustomer(idV.getId()));
    }

    /** Creates a comment. At least one of the two optional parameters (title, text) must be set. */
    public Comment createComment(String productId, String authorName, String title, String text) {
        return Async.awaitResult(createCommentAsync(productId, authorName, title, text));
    }

    /** Creates a comment asynchronously. At least one of the two optional parameters (title, text) must be set. */
    public Promise<SphereResult<Comment>> createCommentAsync(String productId, String authorName, String title, String text) {
        final VersionedId idV = getIdAndVersion();
        Log.trace(String.format("[customer] Creating a comment for customer %s.", idV.getId()));
        return Async.execute(commentService.createComment(productId, idV.getId(), authorName, title, text));
    }

    // --------------------------------------
    // Command helpers
    // --------------------------------------

    private ListenableFuture<SphereResult<Customer>> executeAsync(io.sphere.client.CommandRequest<Customer> commandRequest, String logMessage) {
        Log.trace(logMessage);
        // update version in session
        return Session.withCustomerIdAndVersion(commandRequest.executeAsync(), session);
    }
}
