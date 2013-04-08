package sphere.internal;

import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerToken;
import net.jcip.annotations.Immutable;
import sphere.CustomerService;
import sphere.CommandRequest;
import sphere.FetchRequest;

import javax.annotation.Nonnull;

/** CustomerService with Play-specific async methods.
 * Additional methods are exposed via {@link sphere.SphereClient#currentCustomer()}. */
@Immutable
public class CustomerServiceAdapter implements CustomerService {
    private final io.sphere.client.shop.CustomerService service;
    public CustomerServiceAdapter(@Nonnull io.sphere.client.shop.CustomerService service) {
        if (service == null) throw new NullPointerException("service");
        this.service = service;
    }

    @Override public FetchRequest<Customer> byToken(String token) {
        return new FetchRequestAdapter<Customer>(service.byToken(token));
    }

    @Override public CommandRequest<CustomerToken> createPasswordResetToken(String email) {
        return new CommandRequestAdapter<CustomerToken>(service.createPasswordResetToken(email));
    }
}
