package de.commercetools.internal;

import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.RequestBuilder;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.Customers;
import de.commercetools.sphere.client.shop.model.Customer;
import de.commercetools.sphere.client.util.CommandRequestBuilder;

import org.codehaus.jackson.type.TypeReference;

public class CustomersImpl extends ProjectScopedAPI implements Customers {
    private final RequestFactory requestFactory;

    public CustomersImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** {@inheritDoc}  */
    public RequestBuilder<Customer> byId(String id) {
        return requestFactory.createQueryRequest(endpoints.customers(id), new TypeReference<Customer>() {});
    }

    /** {@inheritDoc}  */
    public RequestBuilder<QueryResult<Customer>> all() {
        return requestFactory.createQueryRequest(endpoints.customers(), new TypeReference<QueryResult<Customer>>() {});
    }

    /** {@inheritDoc}  */
    public RequestBuilder<Customer> login(String email, String password) {
        return requestFactory.createQueryRequest(endpoints.login(email, password), new TypeReference<Customer>() {});
    }

    /** {@inheritDoc}  */
    public CommandRequestBuilder<Customer> signup(String email, String password, String firstName, String lastName, String middleName, String title) {
        return createCommandRequest(
                endpoints.customers(),
                new CustomerCommands.CreateCustomer(email, password, firstName, lastName, middleName, title));
    }

    /** Helper to save some repetitive code in this class. */
    private CommandRequestBuilder<Customer> createCommandRequest(String url, Command command) {
        return requestFactory.<Customer>createCommandRequest(url, command, new TypeReference<Customer>() {});
    }
}
