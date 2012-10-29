package de.commercetools.internal;

import de.commercetools.internal.command.Command;
import de.commercetools.internal.command.CustomerCommands;
import de.commercetools.internal.request.RequestFactory;
import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.Customers;
import de.commercetools.sphere.client.shop.model.Address;
import de.commercetools.sphere.client.shop.model.Customer;
import de.commercetools.sphere.client.shop.model.CustomerToken;
import de.commercetools.sphere.client.shop.model.CustomerUpdate;
import de.commercetools.sphere.client.CommandRequest;

import org.codehaus.jackson.type.TypeReference;


public class CustomersImpl extends ProjectScopedAPI implements Customers {
    private final RequestFactory requestFactory;

    public CustomersImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** {@inheritDoc}  */
    public QueryRequest<Customer> byId(String id) {
        return requestFactory.createQueryRequest(endpoints.customers.byId(id), new TypeReference<Customer>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Customer> byToken(String token) {
        return requestFactory.createQueryRequest(endpoints.customers.byToken(token), new TypeReference<Customer>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<QueryResult<Customer>> all() {
        return requestFactory.createQueryRequest(endpoints.customers.root(), new TypeReference<QueryResult<Customer>>() {});
    }

    /** {@inheritDoc}  */
    public QueryRequest<Customer> login(String email, String password) {
        return requestFactory.createQueryRequest(endpoints.customers.login(email, password), new TypeReference<Customer>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequest<Customer> signup(String email, String password, String firstName, String lastName, String middleName, String title) {
        return createCommandRequest(
                endpoints.customers.root(),
                new CustomerCommands.CreateCustomer(email, password, firstName, lastName, middleName, title));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Customer> changePassword(String customerId, int customerVersion, String currentPassword, String newPassword) {
        return createCommandRequest(
                endpoints.customers.changePassword(),
                new CustomerCommands.ChangePassword(customerId, customerVersion, currentPassword, newPassword));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Customer> changeShippingAddress(String customerId, int customerVersion, int addressIndex, Address address) {
        return createCommandRequest(
                endpoints.customers.changeShippingAddress(),
                new CustomerCommands.ChangeShippingAddress(customerId, customerVersion, addressIndex, address));
    }

    /** {@inheritDoc}  */
    public CommandRequest<Customer> removeShippingAddress(String customerId, int customerVersion, int addressIndex) {
        return createCommandRequest(
                endpoints.customers.removeShippingAddress(),
                new CustomerCommands.RemoveShippingAddress(customerId, customerVersion, addressIndex));
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
    public CommandRequest<Customer> verifyEmail(String customerId, int customerVersion, String tokenValue) {
        return createCommandRequest(
                endpoints.customers.verifyEmail(),
                new CustomerCommands.VerifyCustomerEmail(customerId, customerVersion, tokenValue));
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<Customer> createCommandRequest(String url, Command command) {
        return requestFactory.<Customer>createCommandRequest(url, command, new TypeReference<Customer>() {});
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequest<CustomerToken> createCustomerTokenCommandRequest(String url, Command command) {
        return requestFactory.<CustomerToken>createCommandRequest(url, command, new TypeReference<CustomerToken>() {});
    }
}
