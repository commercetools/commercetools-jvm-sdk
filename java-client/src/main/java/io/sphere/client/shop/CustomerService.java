package io.sphere.client.shop;

import com.google.common.base.Optional;
import io.sphere.client.CommandRequest;
import io.sphere.client.FetchRequest;
import io.sphere.client.QueryRequest;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerToken;
import io.sphere.client.shop.model.CustomerUpdate;

/** Sphere HTTP API for working with customers in a given project. */
public interface CustomerService extends BasicCustomerService {
    /** Creates a request that finds a customer by given id. */
    FetchRequest<Customer> byId(String id);

    /** Finds a customer with given credentials. */
    FetchRequest<AuthenticatedCustomerResult> byCredentials(
            String email, String password);

    /** Creates a request that queries all customers. */
    QueryRequest<Customer> all();

    /** Creates a new customer. */
    CommandRequest<Customer> signup(
            String email, String password, String firstName, String lastName, String middleName, String title);

    /** Creates a new customer and associates the anonymous cart to it. */
    CommandRequest<AuthenticatedCustomerResult> signupWithCart(
            String email, String password, String firstName, String lastName, String middleName, String title, String cartId, int cartVersion);

    /** Sets a new customer password. */
    CommandRequest<Optional<Customer>> changePassword(
            String customerId, int customerVersion, String currentPassword, String newPassword);

    /** Updates a customer with the CustomerUpdate object. */
    CommandRequest<Customer> updateCustomer(
            String customerId, int customerVersion, CustomerUpdate customerUpdate);

    /** Sets a new password.
     *
     * Requires a token that was previously generated using the {@link #createPasswordResetToken(String)} method. */
    CommandRequest<Customer> resetPassword(
            String customerId, int customerVersion, String tokenValue, String newPassword);

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
    CommandRequest<CustomerToken> createEmailVerificationToken(String customerId, int customerVersion, int ttlMinutes);

    /** Sets {@link Customer#isEmailVerified} to true.
     *
     * Requires a token that was previously generated using the {@link #createEmailVerificationToken} method. */
    CommandRequest<Customer> confirmEmail(
            String customerId, int customerVersion, String tokenValue);

}
