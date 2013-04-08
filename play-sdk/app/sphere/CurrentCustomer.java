package sphere;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import io.sphere.client.CommandRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.SphereException;
import io.sphere.client.shop.CommentService;
import io.sphere.client.shop.CustomerService;
import io.sphere.client.shop.OrderService;
import io.sphere.client.shop.ReviewService;
import io.sphere.client.shop.model.*;
import io.sphere.internal.util.Log;
import io.sphere.internal.util.Util;
import net.jcip.annotations.ThreadSafe;
import sphere.util.IdWithVersion;

import javax.annotation.Nullable;

/** Project customer that is automatically associated to the current HTTP session.
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

    private IdWithVersion getIdWithVersion() {
        final IdWithVersion sessionCustomerId = session.getCustomerId();
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
        final IdWithVersion sessionCustomerId = session.getCustomerId();
        if (sessionCustomerId == null) {
            return null;
        }
        return new CurrentCustomer(session, customerService, orderService, commentService, reviewService);
    }

    /** Fetches the currently authenticated {@link Customer}.
     * @return Customer or null if no customer is authenticated. */
    public Customer fetch() {
        return Util.sync(fetchAsync());
    }

    /** Fetches the currently authenticated {@link Customer} asynchronously.
     * @return Customer or null if no customer is authenticated. */
    public ListenableFuture<Customer> fetchAsync() {
        final IdWithVersion idWithVersion = getIdWithVersion();
        Log.trace(String.format("[customer] Fetching customer %s.", idWithVersion.getId()));
        ListenableFuture<Customer> customerFuture = Futures.transform(customerService.byId(idWithVersion.getId()).fetchAsync(), new Function<Optional<Customer>, Customer>() {
            public Customer apply(@Nullable Optional<Customer> customer) {
                assert customer != null;
                if (!customer.isPresent()) {
                    session.clearCustomer();     // the customer was probably deleted, clear it from this old session
                    return new Customer("", 0);  // 'null object' to prevent NPEs on e.g. CurrentCustomer.fetch().getName()
                }
                return customer.get();
            }
        });
        return Session.withCustomerIdAndVersion(customerFuture, session);
    }

    /** Changes customer's password. */
    public boolean changePassword(String currentPassword, String newPassword) {
        return Util.sync(changePasswordAsync(currentPassword, newPassword)).isPresent();
    }

    /** Changes customer's password asynchronously. */
    public ListenableFuture<Optional<Customer>> changePasswordAsync(String currentPassword, String newPassword){
        final IdWithVersion idV = getIdWithVersion();
        return executeAsyncOptional(
                customerService.changePassword(idV.getId(), idV.getVersion(), currentPassword, newPassword),
                String.format("[customer] Changing password for customer %s.", idV.getId()));
    }

    /**
     * A helper method for {@link CustomerService#update}
     *
     * @throws SphereException
     */
    public Customer updateCustomer(CustomerUpdate update) {
        return Util.sync(updateCustomerAsync(update));
    }

    /**
     * A helper method for {@link CustomerService#update}
     */
    public ListenableFuture<Customer> updateCustomerAsync(CustomerUpdate update){
        final IdWithVersion idV = getIdWithVersion();
        return executeAsync(
                customerService.update(idV.getId(), idV.getVersion(), update),
                String.format("[customer] Updating customer %s.", idV.getId()));
    }

    /** Sets a new password for the current customer.
     *
     * Requires a token that was previously generated using the {@link CustomerService#createPasswordResetToken(String)} method.*/
    public Customer resetPassword(String tokenValue, String newPassword) {
        return Util.sync(resetPasswordAsync(tokenValue, newPassword));
    }

   /** Sets a new password for the current customer asynchronously.
    *
    * Requires a token that was previously generated using the {@link CustomerService#createPasswordResetToken(String)} method.*/
    public ListenableFuture<Customer> resetPasswordAsync(String tokenValue, String newPassword){
        final IdWithVersion idV = getIdWithVersion();
        return executeAsync(
                customerService.resetPassword(idV.getId(), idV.getVersion(), tokenValue, newPassword),
                String.format("[customer] Resetting password for customer %s.", idV.getId()));
    }

    /** Creates a token used to verify customer's email. */
    public CustomerToken createEmailVerificationToken(int ttlMinutes) {
        return Util.sync(createEmailVerificationTokenAsync(ttlMinutes));
    }

    /** Creates a token used to verify customer's email asynchronously.
     *
     * @param ttlMinutes Validity of the token in minutes. */
    public ListenableFuture<CustomerToken> createEmailVerificationTokenAsync(int ttlMinutes){
        final IdWithVersion idV = getIdWithVersion();
        Log.trace(String.format("[customer] Creating email verification token for customer %s.", idV.getId()));
        return customerService.createEmailVerificationToken(idV.getId(), idV.getVersion(), ttlMinutes).executeAsync();
    }

    /** Sets {@link Customer#isEmailVerified} to true.
     *
     * Requires a token that was previously generated using the {@link #createEmailVerificationToken} method. */
    public Customer confirmEmail(String tokenValue) {
        return Util.sync(confirmEmailAsync(tokenValue));
    }

    /** Sets {@link Customer#isEmailVerified} to true asynchronously.
     *
     * Requires a token that was previously generated using the {@link #createEmailVerificationToken} method. */
    public ListenableFuture<Customer> confirmEmailAsync(String tokenValue){
        final IdWithVersion idV = getIdWithVersion();
        return executeAsync(
                customerService.confirmEmail(idV.getId(), idV.getVersion(), tokenValue),
                String.format("[customer] Confirming email for customer %s.", idV.getId()));
    }

    /** Queries all orders of given customer. */
    public QueryRequest<Order> queryOrders() {
        final IdWithVersion idV = getIdWithVersion();
        Log.trace(String.format("[customer] Getting orders of customer %s.", idV.getId()));
        return orderService.byCustomerId(idV.getId());
    }


    // --------------------------------------
    // Reviews
    // --------------------------------------

    /** Queries all reviews of the current. */
    public QueryRequest<Review> queryReviews() {
        final IdWithVersion idV = getIdWithVersion();
        Log.trace(String.format("[customer] Getting reviews of customer %s.", idV.getId()));
        return reviewService.byCustomerId(idV.getId());
    }

    /** Queries all reviews of the current customer for a specific product. */
    public QueryRequest<Review> queryReviewsForProduct(String productId) {
       final IdWithVersion idV = getIdWithVersion();
        Log.trace(String.format("[customer] Getting reviews of customer %s on a product.", idV.getId(), productId));
        return reviewService.byCustomerIdProductId(idV.getId(), productId);
    }

    /** Creates a review. At least one of the three optional parameters (title, text, score) must be set. */
    public Review createReview(String productId, String authorName, String title, String text, Double score) {
        return Util.sync(createReviewAsync(productId, authorName, title, text, score));
    }

    /** Creates a review asynchronously. At least one of the three optional parameters (title, text, score) must be set. */
    public ListenableFuture<Review> createReviewAsync(String productId, String authorName, String title, String text, Double score) {
        final IdWithVersion idV = getIdWithVersion();
        Log.trace(String.format("[customer] Creating a review for customer %s.", idV.getId()));
        return reviewService.createReview(productId, idV.getId(), authorName, title, text, score).executeAsync();
    }

    // --------------------------------------
    // Comments
    // --------------------------------------

    /** Queries all comments that the current customer created. */
    public QueryRequest<Comment> queryComments() {
        final IdWithVersion idV = getIdWithVersion();
        Log.trace(String.format("[customer] Getting comments of customer %s.", idV.getId()));
        return commentService.byCustomerId(idV.getId());
    }

    /** Creates a comment. At least one of the two optional parameters (title, text) must be set. */
    public Comment createComment(String productId, String authorName, String title, String text) {
        return Util.sync(createCommentAsync(productId, authorName, title, text));
    }

    /** Creates a comment asynchronously. At least one of the two optional parameters (title, text) must be set. */
    public ListenableFuture<Comment> createCommentAsync(String productId, String authorName, String title, String text) {
        final IdWithVersion idV = getIdWithVersion();
        Log.trace(String.format("[customer] Creating a comment for customer %s.", idV.getId()));
        return commentService.createComment(productId, idV.getId(), authorName, title, text).executeAsync();
    }

    // --------------------------------------
    // Command helpers
    // --------------------------------------

    private ListenableFuture<Customer> executeAsync(CommandRequest<Customer> commandRequest, String logMessage) {
        Log.trace(logMessage);
        return Session.withCustomerIdAndVersion(commandRequest.executeAsync(), session);
    }

    private ListenableFuture<Optional<Customer>> executeAsyncOptional(CommandRequest<Optional<Customer>> commandRequest, String logMessage) {
        Log.trace(logMessage);
        return Session.withCustomerIdAndVersionOptional(commandRequest.executeAsync(), session);
    }
}
