package io.sphere.internal;

import io.sphere.client.*;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.CustomerWithCart;
import io.sphere.client.shop.CustomerService;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerName;
import io.sphere.client.shop.model.CustomerToken;
import io.sphere.client.shop.model.CustomerUpdate;
import io.sphere.internal.command.Command;
import io.sphere.internal.command.CustomerCommands;
import io.sphere.internal.command.UpdateCommand;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;


public class CustomerServiceImpl extends ProjectScopedAPI implements CustomerService {
    private final RequestFactory requestFactory;

    public CustomerServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    @Override public FetchRequest<Customer> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.customers.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<Customer>() {});
    }

    @Override public FetchRequest<Customer> byToken(String token) {
        return requestFactory.createFetchRequest(
                endpoints.customers.byToken(token),
                Optional.<ApiMode>absent(),
                new TypeReference<Customer>() {});
    }

    @Override public QueryRequest<Customer> all() {
        return requestFactory.createQueryRequest(
                endpoints.customers.root(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Customer>>() {});
    }

    @Override public FetchRequest<CustomerWithCart> byCredentials(String email, String password) {
        return requestFactory.createFetchRequestWithErrorHandling(
                endpoints.customers.login(email, password),
                Optional.<ApiMode>absent(),
                401,
                new TypeReference<CustomerWithCart>() {
                });
    }

    @Override public CommandRequest<Customer> signup(String email, String password, CustomerName name) {
        return createCommandRequest(
                endpoints.customers.root(),
                new CustomerCommands.CreateCustomer(
                        email, password, name.getFirstName(), name.getLastName(), name.getMiddleName(), name.getTitle()));
    }

    @Override public CommandRequest<CustomerWithCart> signupWithCart(
            String email, String password, CustomerName name, String cartId, int cartVersion) {
        return requestFactory.createCommandRequest(
            endpoints.customers.signupWithCart(),
            new CustomerCommands.CreateCustomerWithCart(
                    email, password, name.getFirstName(), name.getLastName(), name.getMiddleName(), name.getTitle(), cartId, cartVersion),
            new TypeReference<CustomerWithCart>() {});
    }

    @Override public CommandRequest<Customer> changePassword(VersionedId customerId, String currentPassword, String newPassword) {
        return requestFactory.createCommandRequest(
                endpoints.customers.changePassword(),
                new CustomerCommands.ChangePassword(customerId.getId(), customerId.getVersion(), currentPassword, newPassword),
                new TypeReference<Customer>() {
                });
    }

    @Override public CommandRequest<Customer> update(VersionedId customerId, CustomerUpdate customerUpdate) {
        return createCommandRequest(
                endpoints.customers.byId(customerId.getId()),
                new UpdateCommand<CustomerCommands.CustomerUpdateAction>(customerId.getVersion(), customerUpdate));
    }

    @Override public CommandRequest<CustomerToken> createPasswordResetToken(String email) {
        return createCustomerTokenCommandRequest(
                endpoints.customers.createPasswordResetToken(),
                new CustomerCommands.CreatePasswordResetToken(email));
    }

    @Override public CommandRequest<Customer> resetPassword(VersionedId customerId, String token, String newPassword) {
        return createCommandRequest(
                endpoints.customers.resetPassword(),
                new CustomerCommands.ResetCustomerPassword(customerId.getId(), customerId.getVersion(), token, newPassword));
    }

    @Override public CommandRequest<CustomerToken> createEmailVerificationToken(VersionedId customerId, int ttlMinutes) {
        return createCustomerTokenCommandRequest(
                endpoints.customers.createEmailVerificationToken(),
                new CustomerCommands.CreateEmailVerificationToken(customerId.getId(), customerId.getVersion(), ttlMinutes));
    }

    @Override public CommandRequest<Customer> confirmEmail(VersionedId customerId, String token) {
        return createCommandRequest(
                endpoints.customers.confirmEmail(),
                new CustomerCommands.VerifyCustomerEmail(customerId.getId(), customerId.getVersion(), token));
    }

    /** Helper to save some repetitive code. */
    private CommandRequest<Customer> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<Customer>() {});
    }

    /** Helper to save some repetitive code. */
    private CommandRequest<CustomerToken> createCustomerTokenCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<CustomerToken>() {});
    }
}
