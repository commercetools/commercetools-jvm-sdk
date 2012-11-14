package sphere;

import de.commercetools.internal.util.Log;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.CustomerService;
import de.commercetools.sphere.client.shop.Orders;
import de.commercetools.sphere.client.shop.model.*;
import sphere.util.IdWithVersion;

import com.google.common.util.concurrent.ListenableFuture;
import play.mvc.Http;
import net.jcip.annotations.ThreadSafe;

/** Project customer that is automatically associated to the current HTTP session.
 *
 *  After a logout() on {@link SphereClient}, the existing CurrentCustomer instance is not valid any more.
 *  Invoking any method will throw an IllegalStateException.
 *
 *  Therefore, don't keep instances of this class around, but always use {@link sphere.SphereClient#currentCustomer()}
 *  to get an up-to-date instance, or null if no one is logged in.
 * */
@ThreadSafe
public class CurrentCustomer {
    private final Session session;
    private final CustomerService customerService;
    private final Orders orderService;

    private CurrentCustomer(Session session, CustomerService customerService, Orders orderService) {
        this.session = session;
        this.customerService = customerService;
        this.orderService = orderService;
    }

    private IdWithVersion getIdWithVersion() {
        final IdWithVersion idV = session.getCustomerId();
        if (idV != null) return idV;
        else throw new IllegalStateException("CurrentCustomer should never exist without a customer id stored in a session.");
    }

    /** If a customer is logged in, returns a {@link CurrentCustomer} instance. If no customer is logged in, returns null. */
    public static CurrentCustomer getCurrentCustomer(CustomerService customerService, Orders orderService) {
        final Session session = Session.current();
        final IdWithVersion sessionCustomerId = session.getCustomerId();
        if (sessionCustomerId == null) {
            return null;
        }
        return new CurrentCustomer(session, customerService, orderService);
    }

    /** Fetches the {@link Customer} from the server. The version number of the current customer is updated to the version
     *  of the returned customer. */
    public Customer fetch() {
        try {
            return fetchAsync().get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Customer> fetchAsync() {
        final IdWithVersion idV = getIdWithVersion();
        Log.trace(String.format("[customer] Fetching customer %s.", idV.id()));
        return Session.withCustomerId(customerService.byId(idV.id()).fetchAsync(), session);
    }

    // Change password

    public Customer changePassword(String currentPassword, String newPassword) {
        try {
            return changePasswordAsync(currentPassword, newPassword).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Customer> changePasswordAsync(String currentPassword, String newPassword){
        final IdWithVersion idV = getIdWithVersion();
        return executeAsync(
                customerService.changePassword(idV.id(), idV.version(), currentPassword, newPassword),
                String.format("[customer] Changing password for customer %s.", idV.id()));
    }

    // Change shipping address

    public Customer changeShippingAddress(int addressIndex, Address address) {
        try {
            return changeShippingAddressAsync(addressIndex, address).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Customer> changeShippingAddressAsync(int addressIndex, Address address){
        final IdWithVersion idV = getIdWithVersion();
        return executeAsync(
                customerService.changeShippingAddress(idV.id(), idV.version(), addressIndex, address),
                String.format("[customer] Changing shipping address for customer %s.", idV.id()));
    }

    // Remove shipping address

    public Customer removeShippingAddress(int addressIndex) {
        try {
            return removeShippingAddressAsync(addressIndex).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Customer> removeShippingAddressAsync(int addressIndex){
        final IdWithVersion idV = getIdWithVersion();
        return executeAsync(
                customerService.removeShippingAddress(idV.id(), idV.version(), addressIndex),
                String.format("[customer] Changing shipping address with index %s for customer %s.", addressIndex, idV.id()));
    }


    // Set default shipping address

    public Customer setDefaultShippingAddress(int addressIndex) {
        try {
            return setDefaultShippingAddressAsync(addressIndex).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Customer> setDefaultShippingAddressAsync(int addressIndex){
        final IdWithVersion idV = getIdWithVersion();
        return executeAsync(
                customerService.setDefaultShippingAddress(idV.id(), idV.version(), addressIndex),
                String.format("[customer] Setting default shipping address with index %s for customer %s.", addressIndex, idV.id()));
    }

    // Update customer

    public Customer updateCustomer(CustomerUpdate update) {
        try {
            return updateCustomerAsync(update).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Customer> updateCustomerAsync(CustomerUpdate update){
        final IdWithVersion idV = getIdWithVersion();
        return executeAsync(
                customerService.updateCustomer(idV.id(), idV.version(), update),
                String.format("[customer] Updating customer %s.", idV.id()));
    }

    // Reset password
   
    public Customer resetPassword(String tokenValue, String newPassword) {
        try {
            return resetPasswordAsync(tokenValue, newPassword).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Customer> resetPasswordAsync(String tokenValue, String newPassword){
        final IdWithVersion idV = getIdWithVersion();
        return executeAsync(
                customerService.resetPassword(idV.id(), idV.version(), tokenValue, newPassword),
                String.format("[customer] Resetting password for customer %s.", idV.id()));
    }

    // Create email verification token

    public CustomerToken createEmailVerificationToken(int ttlMinutes) {
        try {
            return createEmailVerificationTokenAsync(ttlMinutes).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<CustomerToken> createEmailVerificationTokenAsync(int ttlMinutes){
        final IdWithVersion idV = getIdWithVersion();
        Log.trace(String.format("[customer] Creating email verification token for customer %s.", idV.id()));
        return customerService.createEmailVerificationToken(idV.id(), idV.version(), ttlMinutes).executeAsync();
    }

    // Verify email

    public Customer verifyEmail(String tokenValue) {
        try {
            return verifyEmailAsync(tokenValue).get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<Customer> verifyEmailAsync(String tokenValue){
        final IdWithVersion idV = getIdWithVersion();
        return executeAsync(
                customerService.verifyEmail(idV.id(), idV.version(), tokenValue),
                String.format("[customer] Verifying email for customer %s.", idV.id()));
    }

    // Get orders of the customer
    public QueryResult<Order> getOrders() {
        try {
            return getOrdersAsync().get();
        } catch(Exception e) {
            throw new SphereException(e);
        }
    }

    public ListenableFuture<QueryResult<Order>> getOrdersAsync() {
        final IdWithVersion idV = getIdWithVersion();
        Log.trace(String.format("[customer] Getting orders of customer %s.", idV.id()));
        return orderService.byCustomerId(idV.id()).fetchAsync();
    }

    // --------------------------------------
    // Command helpers
    // --------------------------------------

    private ListenableFuture<Customer> executeAsync(CommandRequest<Customer> commandRequest, String logMessage) {
        Log.trace(logMessage);
        return Session.withCustomerId(commandRequest.executeAsync(), session);
    }

}
