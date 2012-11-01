package sphere;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import de.commercetools.internal.util.Log;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.shop.Customers;
import de.commercetools.sphere.client.shop.model.Customer;
import net.jcip.annotations.ThreadSafe;
import play.mvc.Http;

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

    /** Ensures that the CurrentCustomer's session always contains the customer id. */
    public static CurrentCustomer getCurrentCustomer(Http.Session httpSession, Customers customerService) {
        final Session session = new Session(Http.Context.current().session());
        if (session.getCustomerId() != null) return new CurrentCustomer(session, customerService);
        else return null;
    }


    // --------------------------------------
    // Command helpers
    // --------------------------------------

//    private ListenableFuture<Customer> executeAsync(CommandRequest<Customer> commandRequest, String logMessage) {
//        Log.trace(logMessage);
//        return Futures.transform(commandRequest.executeAsync(), new Function<Customer, Customer>() {
//            @Override
//            public Customer apply(@Nullable Customer customer) {
//                session.putCustomer(customer);
//                return customer;
//            }
//        });
//    }

}
