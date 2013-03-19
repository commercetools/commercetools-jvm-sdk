package de.commercetools.internal;

import de.commercetools.internal.command.Command;
import de.commercetools.internal.command.CustomerCommands;
import de.commercetools.internal.request.RequestFactory;
import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.FetchRequest;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.AuthenticatedCustomerResult;
import de.commercetools.sphere.client.shop.CustomerService;
import de.commercetools.sphere.client.shop.model.Address;
import de.commercetools.sphere.client.shop.model.Customer;
import de.commercetools.sphere.client.shop.model.CustomerToken;
import de.commercetools.sphere.client.shop.model.CustomerUpdate;

import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;


public class CustomerServiceImpl extends ProjectScopedAPI implements CustomerService {
    private final RequestFactory requestFactory;

    public CustomerServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** {@inheritDoc}  */
    public FetchRequest<Customer> byId(String id) {
        return requestFactory.createFetchRequest(endpoints.customers.byId(id), new TypeReference<Customer>() {
        });
    }

    /** {@inheritDoc}  */
    public FetchRequest<Customer> byToken(String token) {
        return requestFactory.createFetchRequest(endpoints.customers.byToken(token), new TypeReference<Customer>() {
        });
    }

    /** {@inheritDoc}  */
    public QueryRequest<Customer> all() {
        return requestFactory.createQueryRequest(endpoints.customers.root(), new TypeReference<QueryResult<Customer>>() {});
    }

    /** {@inheritDoc}  */
    public FetchRequest<AuthenticatedCustomerResult> byCredentials(String email, String password) {
        return requestFactory.createFetchRequestWithErrorHandling(
                endpoints.customers.login(email, password), 401, new TypeReference<AuthenticatedCustomerResult>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Customer> signup(String email, String password, String firstName, String lastName, String middleName, String title) {
        return createCommandRequest(
                endpoints.customers.root(),
                new CustomerCommands.CreateCustomer(email, password, firstName, lastName, middleName, title));
    }

    /** {@inheritDoc}  */
    public CommandRequest<AuthenticatedCustomerResult> signupWithCart(
            String email, String password, String firstName, String lastName, String middleName, String title, String cartId, int cartVersion)
    {
        return requestFactory.createCommandRequest(
            endpoints.customers.signupWithCart(),
            new CustomerCommands.CreateCustomerWithCart(email, password, firstName, lastName, middleName, title, cartId, cartVersion),
            new TypeReference<AuthenticatedCustomerResult>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Optional<Customer>> changePassword(String customerId, int customerVersion, String currentPassword, String newPassword) {
        return requestFactory.createCommandRequestWithErrorHandling(
                endpoints.customers.changePassword(),
                new CustomerCommands.ChangePassword(customerId, customerVersion, currentPassword, newPassword),
                400,
                new TypeReference<Customer>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Customer> changeAddress(String customerId, int customerVersion, int addressIndex, Address address) {
        return createCommandRequest(
                endpoints.customers.changeShippingAddress(),
                new CustomerCommands.ChangeAddress(customerId, customerVersion, addressIndex, address));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Customer> removeAddress(String customerId, int customerVersion, int addressIndex) {
        return createCommandRequest(
                endpoints.customers.removeShippingAddress(),
                new CustomerCommands.RemoveAddress(customerId, customerVersion, addressIndex));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Customer> setDefaultShippingAddress(String customerId, int customerVersion, int addressIndex) {
        return createCommandRequest(
                endpoints.customers.setDefaultShippingAddress(),
                new CustomerCommands.SetDefaultShippingAddress(customerId, customerVersion, addressIndex));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Customer> updateCustomer(String customerId, int customerVersion, CustomerUpdate customerUpdate) {
        return createCommandRequest(
                endpoints.customers.updateCustomer(),
                new CustomerCommands.UpdateCustomer(customerId, customerVersion, customerUpdate));
    }

    /** {@inheritDoc}  */
    public CommandRequest<CustomerToken> createPasswordResetToken(String email) {
        return createCustomerTokenCommandRequest(
                endpoints.customers.createPasswordResetToken(),
                new CustomerCommands.CreatePasswordResetToken(email));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Customer> resetPassword(String customerId, int customerVersion, String tokenValue, String newPassword) {
        return createCommandRequest(
                endpoints.customers.resetPassword(),
                new CustomerCommands.ResetCustomerPassword(customerId, customerVersion, tokenValue, newPassword));
    }

    /** {@inheritDoc}  */
    public CommandRequest<CustomerToken> createEmailVerificationToken(String customerId, int customerVersion, int ttlMinutes) {
        return createCustomerTokenCommandRequest(
                endpoints.customers.createEmailVerificationToken(),
                new CustomerCommands.CreateEmailVerificationToken(customerId, customerVersion, ttlMinutes));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Customer> confirmEmail(String customerId, int customerVersion, String tokenValue) {
        return createCommandRequest(
                endpoints.customers.confirmEmail(),
                new CustomerCommands.VerifyCustomerEmail(customerId, customerVersion, tokenValue));
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<Customer> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<Customer>() {});
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<CustomerToken> createCustomerTokenCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<CustomerToken>() {});
    }
}
