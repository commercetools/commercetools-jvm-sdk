package io.sphere.internal;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.ProjectEndpoints;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.QueryResult;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.AuthenticatedCustomerResult;
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

    @Override public FetchRequest<AuthenticatedCustomerResult> byCredentials(String email, String password) {
        return requestFactory.createFetchRequestWithErrorHandling(
                endpoints.customers.login(email, password),
                Optional.<ApiMode>absent(),
                401,
                new TypeReference<AuthenticatedCustomerResult>() {});
    }

    @Override public CommandRequest<Customer> signup(String email, String password, CustomerName name) {
        return createCommandRequest(
                endpoints.customers.root(),
                new CustomerCommands.CreateCustomer(
                        email, password, name.getFirstName(), name.getLastName(), name.getMiddleName(), name.getTitle()));
    }

    @Override public CommandRequest<AuthenticatedCustomerResult> signupWithCart(
            String email, String password, CustomerName name, String cartId, int cartVersion) {
        return requestFactory.createCommandRequest(
            endpoints.customers.signupWithCart(),
            new CustomerCommands.CreateCustomerWithCart(
                    email, password, name.getFirstName(), name.getLastName(), name.getMiddleName(), name.getTitle(), cartId, cartVersion),
            new TypeReference<AuthenticatedCustomerResult>() {});
    }

    @Override public CommandRequest<Optional<Customer>> changePassword(String customerId, int customerVersion, String currentPassword, String newPassword) {
        return requestFactory.createCommandRequestWithErrorHandling(
                endpoints.customers.changePassword(),
                new CustomerCommands.ChangePassword(customerId, customerVersion, currentPassword, newPassword),
                400,
                new TypeReference<Customer>() {});
    }

    @Override public CommandRequest<Customer> update(String customerId, int customerVersion, CustomerUpdate customerUpdate) {
        return createCommandRequest(
                endpoints.customers.byId(customerId),
                new UpdateCommand<CustomerCommands.CustomerUpdateAction>(customerVersion, customerUpdate));
    }

    @Override public CommandRequest<CustomerToken> createPasswordResetToken(String email) {
        return createCustomerTokenCommandRequest(
                endpoints.customers.createPasswordResetToken(),
                new CustomerCommands.CreatePasswordResetToken(email));
    }

    @Override public CommandRequest<Customer> resetPassword(String customerId, int customerVersion, String token, String newPassword) {
        return createCommandRequest(
                endpoints.customers.resetPassword(),
                new CustomerCommands.ResetCustomerPassword(customerId, customerVersion, token, newPassword));
    }

    @Override public CommandRequest<CustomerToken> createEmailVerificationToken(String customerId, int customerVersion, int ttlMinutes) {
        return createCustomerTokenCommandRequest(
                endpoints.customers.createEmailVerificationToken(),
                new CustomerCommands.CreateEmailVerificationToken(customerId, customerVersion, ttlMinutes));
    }

    @Override public CommandRequest<Customer> confirmEmail(String customerId, int customerVersion, String token) {
        return createCommandRequest(
                endpoints.customers.confirmEmail(),
                new CustomerCommands.VerifyCustomerEmail(customerId, customerVersion, token));
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
