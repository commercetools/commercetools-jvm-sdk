package io.sphere.client.shop;

import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerToken;

/** Sphere HTTP API for working with customers in a given project.
 *
 * <p>This interface only exposes methods independent of specific customer instance.
 * See {@link CustomerService} for methods that work with a specific customer instance. */
public interface BasicCustomerService {
    /** Creates a request that finds a customer by a token value. */
    FetchRequest<Customer> byToken(String token);

    /** Creates a password reset token for the customer with the given email.
     * The validity of the token is 10 minutes.
     *
     *  <p>The typical workflow is the following:
     *  <ol>
     *    <li>Customer enters his or her email in a password reset form and submits.
     *    <li>Create a password reset token using this method.
     *    <li>Send an email containing a link with the token to the customer.
     *    <li>The link points to a form where the customer can enter a new password. The form should load the customer
     *    using {@link BasicCustomerService#byToken} and should remember customer's id and version in hidden form fields.
     *    If the customer can't be found, the token has expired or is invalid.
     *    <li>When the customer submits the form with the new password, call {@link CustomerService#resetPassword},
     *    passing in the customer id and version, the new password and the token (the token is extracted from the URL).
     *  </ol>
     *
     * @param email Email address for which the token should be created. */
    CommandRequest<CustomerToken> createPasswordResetToken(String email);
}
