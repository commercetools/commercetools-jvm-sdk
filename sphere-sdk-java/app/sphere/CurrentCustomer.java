package sphere;

import de.commercetools.internal.util.Log;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.SphereException;
import de.commercetools.sphere.client.shop.Customers;
import de.commercetools.sphere.client.shop.model.Customer;
import sphere.util.IdWithVersion;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import play.mvc.Http;
import net.jcip.annotations.ThreadSafe;

import javax.annotation.Nullable;

/** Project customer that is automatically associated to the current HTTP session. */
@ThreadSafe
public class CurrentCustomer {
    private final Session session;
    private final Customers customerService;

    private CurrentCustomer(Session session, Customers customerService) {
        this.session = session;
        this.customerService = customerService;
    }

    //TODO unify passing of the session on create between currentcustomer and currentcart
    /** Ensures that the CurrentCustomer's session always contains the customer id. */
    public static CurrentCustomer getCurrentCustomer(Http.Session httpSession, Customers customerService) {
        final Session session = new Session(Http.Context.current().session());
        if (session.getCustomerId() != null) return new CurrentCustomer(session, customerService);
        else return null;
    }

    public IdWithVersion getId() {
        IdWithVersion id = session.getCustomerId();
        if (id != null) return id;
        else throw new IllegalStateException("Current customers id should never be null.");
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
        IdWithVersion customerId = getId();
        return executeAsync(
                customerService.changePassword(customerId.id(), customerId.version(), currentPassword, newPassword),
                String.format("[customer] Changing password for customer %s.", customerId.id()));
    }


    // --------------------------------------
    // Command helpers
    // --------------------------------------

    private ListenableFuture<Customer> executeAsync(CommandRequest<Customer> commandRequest, String logMessage) {
        Log.trace(logMessage);
        return Futures.transform(commandRequest.executeAsync(), new Function<Customer, Customer>() {
            @Override
            public Customer apply(@Nullable Customer customer) {
                session.putCustomer(customer);
                return customer;
            }
        });
    }

}
