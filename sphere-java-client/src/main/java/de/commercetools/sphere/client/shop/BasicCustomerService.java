package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.CommandRequest;
import de.commercetools.sphere.client.QueryRequest;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.shop.model.Customer;
import de.commercetools.sphere.client.shop.model.CustomerToken;

/** Sphere HTTP API for working with customers in a given project.
 *
 * This interface only exposes methods that do not work on a customer with a particular id and version.
 * The methods on a particular customer are exposed in {@link CustomerService}. */
public interface BasicCustomerService {
    // TODO move to CustomerService, we don't want a shop to be able to get any customer
    /** Creates a request that finds a customer by given id. */
    QueryRequest<Customer> byId(String id);

    // TODO move to CustomerService, we don't want a shop to be able to list all customers
    /** Creates a request that queries all customers. */
    QueryRequest<QueryResult<Customer>> all();

    /** Creates a password reset customer token for the customer with the given email.
     *  The returned token is then used with the resetPassword method to set a new password.
     *
     *  If a customer forgot the password, the typical use case would be:
     *    1. Customer enters his email in the password reset form.
     *    2. A token is created for the customer with the createPasswordResetToken.
     *    3. A link containing the token is sent to the customer by email.
     *    4. The link in the email points to a form where the customer can enter a new password.
     *    5. When the customer submits the new password, the resetPassword method is called with the new password and the
     *    token (the token is extracted from the link).
     *
     *  See also {@link CustomerService}. */
    public CommandRequest<CustomerToken> createPasswordResetToken(String email);

    /** Creates a token used to verify customers email (set the Customer.isEmailVerified to true).
     *  The ttlMinutes sets the time-to-live of the token in minutes. The token becomes invalid after the ttl expires.
     *  Maximum ttlMinutes value can be 1 month. The created token is then used with the verifyEmail method.
     *
     *  Customer's email could be verified as follows:
     *    1. Customer click on a verify email button.
     *    2. A token is created with the createEmailVerificationToken for the current customer.
     *    3. A link containing the token is sent to the customer by email.
     *    4. The link points to a page where the customer has to log in (if not already logged in) which calls the
     *      verifyEmail command with the customer and the token value extracted from the link.
     *
     *  See also {@link CustomerService}. */
    public CommandRequest<CustomerToken> createEmailVerificationToken(String customerId, int customerVersion, int ttlMinutes);

    /** Creates a request that finds a customer by a token value */
    public QueryRequest<Customer> byToken(String token);
}
