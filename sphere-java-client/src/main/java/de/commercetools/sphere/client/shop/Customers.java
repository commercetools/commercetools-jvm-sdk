package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.shop.model.*;
import de.commercetools.sphere.client.model.QueryResult;

/** Sphere HTTP API for working with customers in a given project. */
public interface Customers {

    /** Creates a request that finds a customer by given id. */
    QueryRequest<Customer> byId(String id);

    /** Creates a request that queries all customers. */
    QueryRequest<QueryResult<Customer>> all();

    /** Returns a customer that matches the given credentials.
     * Returns null if a customer with the credentials does not exist. */
    public QueryRequest<Customer> login(String email, String password);

    /** Creates a new customer. */
    public CommandRequest<Customer> signup(String email,
                                                  String password,
                                                  String firstName,
                                                  String lastName,
                                                  String middleName,
                                                  String title);

    /** Sets a new customer password. */
    public CommandRequest<Customer> changePassword(String customerId,
                                                          int customerVersion,
                                                          String currentPassword,
                                                          String newPassword);

    /** The address in shippingAddresses list referenced by addressIndex is replaced with the given address. */
    public CommandRequest<Customer> changeShippingAddress(String customerId,
                                                                 int customerVersion,
                                                                 int addressIndex,
                                                                 Address address);

    /** Removes the address in shippingAddresses list referenced by addressIndex. */
    public CommandRequest<Customer> removeShippingAddress(String customerId,
                                                                 int customerVersion,
                                                                 int addressIndex);

    /** The Customer.defaultShippingAddress is set to addressIndex. */
    public CommandRequest<Customer> setDefaultShippingAddress(String customerId,
                                                                     int customerVersion,
                                                                     int addressIndex);

    /** Updates a customer with the CustomerUpdate object. */
    public CommandRequest<Customer> updateCustomer(String customerId,
                                                          int customerVersion,
                                                          CustomerUpdate customerUpdate);
}