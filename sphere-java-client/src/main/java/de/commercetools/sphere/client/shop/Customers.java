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

    /** Sets a new consumer password. */
    public CommandRequestBuilder<Customer> changePassword(String customerId,
                                                          int customerVersion,
                                                          String currentPassword,
                                                          String newPassword);

    /** The address in shippingAddresses list referenced by addressIndex is replaced with the given address. */
    public CommandRequestBuilder<Customer> changeShippingAddress(String customerId,
                                                                 int customerVersion,
                                                                 int addressIndex,
                                                                 Address address);

    /** Removes the address in shippingAddresses list referenced by addressIndex. */
    public CommandRequestBuilder<Customer> removeShippingAddress(String customerId,
                                                                 int customerVersion,
                                                                 int addressIndex);

    /** The Customer.defaultShippingAddress is set to addressIndex. */
    public CommandRequestBuilder<Customer> setDefaultShippingAddress(String customerId,
                                                                     int customerVersion,
                                                                     int addressIndex);

    /** Updates a customer with the CustomerUpdate object. */
    public CommandRequestBuilder<Customer> updateCustomer(String customerId,
                                                          int customerVersion,
                                                          CustomerUpdate customerUpdate);
}