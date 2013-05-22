package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerName;
import io.sphere.client.shop.model.CustomerToken;
import io.sphere.client.shop.model.CustomerUpdate;

/** Sphere HTTP API for working with customers in a given project. */
public interface CustomerService {
    /** Finds a customer by id. */
    FetchRequest<Customer> byId(String id);

    /** Finds a customer with given credentials. */
    FetchRequest<CustomerWithCart> byCredentials(
            String email, String password);

    /** Creates a request that finds a customer by a token value. */
    FetchRequest<Customer> byToken(String token);

    /** Queries all customers. */
    QueryRequest<Customer> all();

    /** Creates a new customer. */
    CommandRequest<Customer> signup(
            String email, String password, CustomerName name);

    /** Creates a new customer and associates an anonymous cart to the customer. */
    CommandRequest<CustomerWithCart> signupWithCart(
            String email, String password, CustomerName customerName, String cartId, int cartVersion);

    /** Sets a new password for a customer. */
    CommandRequest<Customer> changePassword(VersionedId customerId, String currentPassword, String newPassword);

    /** Updates a customer. */
    CommandRequest<Customer> update(VersionedId customerId, CustomerUpdate customerUpdate);

    /** Sets a new password for a customer.
     *
     * Requires a token that was previously generated using the {@link #createPasswordResetToken(String)} method. */
    CommandRequest<Customer> resetPassword(VersionedId customerId, String token, String newPassword);

    /** Creates a token used to verify customer's email.
     *
     *  <p>Typically, verification emails are sent as part of the signup process but the decision is when and whether
     *  to verify customer emails is up to you.
     *
     *  <p>The typical workflow is the following:
     *  <ol>
     *    <li>Create an email verification token using this method.
     *    <li>Send an email containing a link with the token to the customer.
     *    <li>The link points to a page where the customer has to log in (if not already logged in).
     *    If the customer is successfully logged in, you can call {@link CustomerService#confirmEmail},
     *    passing in current customer's id, version and the token, extracted from the URL.
     *
     *  See also {@link CustomerService}.
     *
     *  @param ttlMinutes Validity of the token in minutes. The maximum allowed value is 43200 (30 days). */
    CommandRequest<CustomerToken> createEmailVerificationToken(VersionedId customerId, int ttlMinutes);

    /** Sets {@link Customer#isEmailVerified} to true.
     *
     * Requires a token that was previously generated using the {@link #createEmailVerificationToken} method. */
    CommandRequest<Customer> confirmEmail(VersionedId customerId, String token);

    /** Creates a password reset token for the customer with the given email.
     * The validity of the token is 10 minutes.
     *
     *  <p>The typical workflow is the following:
     *  <ol>
     *    <li>Customer enters his or her email in a password reset form and submits.
     *    <li>Create a password reset token using this method.
     *    <li>Send an email containing a link with the token to the customer.
     *    <li>The link points to a form where the customer can enter a new password. The form should load the customer
     *    using {@link #byToken} and remember customer's id and version in hidden form fields.
     *    If the customer can't be found, the token has expired or is invalid.
     *    <li>When the customer submits the form with the new password, call {@link CustomerService#resetPassword},
     *    passing in the customer id and version, the new password and the token (the token is extracted from the URL).
     *  </ol>
     *
     * @param email Email address for which the token should be created. */
    CommandRequest<CustomerToken> createPasswordResetToken(String email);
}
