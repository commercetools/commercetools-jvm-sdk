package sphere.internal;

import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerToken;
import net.jcip.annotations.Immutable;
import play.libs.F.Promise;
import sphere.CustomerService;
import sphere.FetchRequest;
import sphere.util.Async;

import javax.annotation.Nonnull;

/** CustomerService with Play-specific async methods.
 *  Additional methods are exposed via {@link sphere.SphereClient#currentCustomer()}. */
@Immutable
public class CustomerServiceAdapter implements CustomerService {
    private final io.sphere.client.shop.CustomerService service;
    public CustomerServiceAdapter(@Nonnull io.sphere.client.shop.CustomerService service) {
        if (service == null) throw new NullPointerException("service");
        this.service = service;
    }

    @Override public FetchRequest<Customer> byToken(String token) {
        return Async.adapt(service.byToken(token));
    }

    @Override public CustomerToken createPasswordResetToken(String email) {
        return Async.await(createPasswordResetTokenAsync(email));
    }

    @Override public Promise<CustomerToken> createPasswordResetTokenAsync(String email) {
        return Async.execute(service.createPasswordResetToken(email));
    }

    @Override public Customer resetPassword(VersionedId customerId, String token, String newPassword) {
        return service.resetPassword(customerId, token, newPassword).execute();
    }

    @Override public Promise<Customer> resetPasswordAsync(VersionedId customerId, String token, String newPassword) {
        return Async.execute(service.resetPassword(customerId, token, newPassword));
    }
}
