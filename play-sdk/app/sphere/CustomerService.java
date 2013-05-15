package sphere;

import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerToken;
import play.libs.F.Promise;

/** Sphere HTTP API for working with customers in a given project.
 *
 * <p>For additional methods related to the currently authenticated customer,
 * see {@link sphere.SphereClient#currentCustomer()}. */
public interface CustomerService {
    /** Finds a customer associated to given token.
     *  If the token is invalid or has expired, no customer will be found. */
    FetchRequest<Customer> byToken(String token);

    /** Creates a password reset token for a customer.
     *  The validity of the token is 10 minutes.
     *
     *  <p>The typical workflow is the following:
     *  <ol>
     *    <li>Customer enters his or her email in a password reset form and submits.
     *    <li>Create a password reset token using this method.
     *    <li>Send an email containing a link with the token to the customer.
     *    <li>The link points to a form where the customer can enter a new password. The form
     *    should load the customer using {@link #byToken} and store customer's id and version
     *    in hidden form fields.
     *    If the customer can't be found, the token is invalid or expired.
     *    <li>When the customer submits the form with the new password, call
     *    {@link #resetPassword(String, int, String, String) resetPassword} or
     *    {@link #resetPasswordAsync(String, int, String, String) resetPasswordAsync}
     *    passing in the new password and the token (extracted from query string).
     *  </ol>
     *
     * @param email Email address for which the token should be created. */
    CustomerToken createPasswordResetToken(String email);

    /** Creates a password reset token for a customer asynchronously.
     *  @see {@link #createPasswordResetToken(String) createPasswordResetToken}
     *
     * @param email Email address for which the token should be created. */
    Promise<CustomerToken> createPasswordResetTokenAsync(String email);

    /** Sets a new password for the current customer.
     *
     *  @param token A token that was previously generated using the
     *               {@link #createPasswordResetToken(String) createPasswordResetToken} method.
     *  @param newPassword New plaintext password to be set for the customer. */
    Customer resetPassword(String customerId, int customerVersion, String token, String newPassword);

    /** Sets a new password for the current customer asynchronously.
     *
     *  @param token A token that was previously generated using the
     *               {@link #createPasswordResetToken(String) createPasswordResetToken} method.
     *  @param newPassword New plaintext password to be set for the customer. */
    Promise<Customer> resetPasswordAsync(String customerId, int customerVersion, String token, String newPassword);
}
