package sphere;

import de.commercetools.internal.util.Log;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.shop.CustomerService;
import de.commercetools.sphere.client.shop.model.Address;
import de.commercetools.sphere.client.shop.model.Customer;
import de.commercetools.sphere.client.shop.model.CustomerToken;
import de.commercetools.sphere.client.shop.model.CustomerUpdate;
import sphere.util.IdWithVersion;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import play.mvc.Http;
import net.jcip.annotations.ThreadSafe;

import javax.annotation.Nullable;

/** Project customer that is automatically associated to the current HTTP session.
 *
 * After a logout() on {@link SphereClient}, the existing CurrentCustomer instance is not valid any more. Invoking
 * a method will throw an IllegalStateException.
 * */
@ThreadSafe
public class CurrentCustomer {
    private final Session session;
    private final CustomerService customerService;

    private CurrentCustomer(Session session, CustomerService customerService, IdWithVersion id) {
        this.session = session;
        this.customerService = customerService;
    }

    public IdWithVersion getIdWithVersion() {
        final IdWithVersion idV = session.getCustomerId();
        if (idV != null) return idV;
        else throw new IllegalStateException("CurrentCustomer should never exist without a customer id stored in a session.");
        //TODO how to handle the logout case (when customerid is removed from session) if a method is called on the CurrentCustomer instance
    }

    //TODO unify passing of the session on create between currentcustomer and currentcart
    public static CurrentCustomer getCurrentCustomer(CustomerService customerService) {
        final Session session = new Session(Http.Context.current().session());
        final IdWithVersion id = session.getCustomerId();
        if (id != null) return new CurrentCustomer(session, customerService, id);
        else return null;
    }

    /**
     * Fetches the customer from the server. The version number of the current customer is updated to the version
     * of the returned customer.
     */
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
        return withResultIdAndVersionStoredInSession(customerService.byId(idV.id()).fetchAsync(), session);
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
                String.format("[customer] Reseting password for customer %s.", idV.id()));
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

    // --------------------------------------
    // Command helpers
    // --------------------------------------

    private ListenableFuture<Customer> executeAsync(CommandRequest<Customer> commandRequest, String logMessage) {
        Log.trace(logMessage);
        return withResultIdAndVersionStoredInSession(commandRequest.executeAsync(), session);
    }

    static ListenableFuture<Customer> withResultIdAndVersionStoredInSession(ListenableFuture<Customer> future,
                                                                            final Session session) {
        return Futures.transform(future, new Function<Customer, Customer>() {
            @Override
            public Customer apply(@Nullable Customer customer) {
                session.putCustomer(customer);
                return customer;
            }
        });
    }

}
