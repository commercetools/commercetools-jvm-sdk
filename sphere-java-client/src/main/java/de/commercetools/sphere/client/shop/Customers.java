package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.shop.model.*;
import de.commercetools.sphere.client.RequestBuilder;
import de.commercetools.sphere.client.util.CommandRequestBuilder;
import de.commercetools.sphere.client.model.QueryResult;

/** Sphere HTTP API for working with customers in a given project. */
public interface Customers {

    /** Creates a request builder that finds a customer by given id. */
    RequestBuilder<Customer> byId(String id);

    /** Creates a request builder that queries all customers. */
    RequestBuilder<QueryResult<Customer>> all();

    /** Returns a customer that matches the given credentials.
     * Returns null if a customer with the credentials does not exist. */
    public RequestBuilder<Customer> login(String email, String password);

    /** Creates a new customer. */
    public CommandRequestBuilder<Customer> signup(String email,
                                                  String password,
                                                  String firstName,
                                                  String lastName,
                                                  String middleName,
                                                  String title);

}