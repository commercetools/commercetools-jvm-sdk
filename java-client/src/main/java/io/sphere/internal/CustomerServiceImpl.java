package io.sphere.internal;

import com.google.common.base.Strings;
import io.sphere.client.*;
import io.sphere.client.exceptions.*;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.CustomerService;
import io.sphere.client.shop.SignInResult;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerName;
import io.sphere.client.shop.model.CustomerToken;
import io.sphere.client.shop.model.CustomerUpdate;
import io.sphere.internal.command.Command;
import io.sphere.internal.command.CustomerCommands;
import io.sphere.internal.command.UpdateCommand;
import io.sphere.internal.request.RequestFactory;
import static io.sphere.internal.util.Util.getSingleError;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;
import static io.sphere.internal.errors.ErrorHandling.handleDuplicateField;

public class CustomerServiceImpl extends ProjectScopedAPI<Customer> implements CustomerService {
    public CustomerServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(requestFactory, endpoints, new TypeReference<Customer>() {}, new TypeReference<QueryResult<Customer>>() { });
    }

    @Override public FetchRequest<Customer> byId(String id) {
        return requestFactory.createFetchRequest(
                endpoints.customers.byId(id),
                Optional.<ApiMode>absent(),
                new TypeReference<Customer>() {});
    }

    @Override public FetchRequest<Customer> byToken(String token) {
        return requestFactory.createFetchRequest(
                endpoints.customers.byToken(token),
                Optional.<ApiMode>absent(),
                new TypeReference<Customer>() {});
    }

    @Deprecated
    @Override public QueryRequest<Customer> all() {
        return query();
    }

    @Override public QueryRequest<Customer> query() {
        return queryImpl(endpoints.customers.root());
    }

    /** Handles DuplicateField('email') on sign-up. */
    private Function<SphereBackendException, SphereException> handleDuplicateEmail(final String email) {
        return handleDuplicateField("email", new EmailAlreadyInUseException(email));
    }

    /** Handles InvalidCredentials on sign-in. */
    private Function<SphereBackendException, SphereException> handleInvalidCredentials() {
        return new Function<SphereBackendException, SphereException>() {
            public SphereException apply(SphereBackendException e) {
                SphereError.InvalidCredentials err = getSingleError(e, SphereError.InvalidCredentials.class);
                if (err != null) {
                    return new InvalidCredentialsException();
                }
                return null;
            }
        };
    }

    @Override public CommandRequest<SignInResult> signUp(String email, String password, CustomerName name) {
        CommandRequest<SignInResult> signUpCmd = createSignInResultCommandRequest(
                endpoints.customers.root(),
                new CustomerCommands.CreateCustomer(email, password, name.getFirstName(), name.getLastName(),
                        name.getMiddleName(), name.getTitle()));
        return signUpCmd.withErrorHandling(handleDuplicateEmail(email));
    }


    @Override public CommandRequest<SignInResult> signUp(String email, String password, CustomerName name, String cartId) {
        if (Strings.isNullOrEmpty(cartId)) throw new IllegalArgumentException("cartId can't be empty.");
        CommandRequest<SignInResult> signUpCmd = createSignInResultCommandRequest(
                endpoints.customers.root(),
                new CustomerCommands.CreateCustomerWithCart(email, password, name.getFirstName(), name.getLastName(),
                        name.getMiddleName(), name.getTitle(), cartId));
        return signUpCmd.withErrorHandling(handleDuplicateEmail(email));
    }

    @Override public CommandRequest<SignInResult> signIn(String email, String password) {
        CommandRequest<SignInResult> signInCmd = createSignInResultCommandRequest(
                endpoints.login(),
                new CustomerCommands.SignIn(email, password));
        return signInCmd.withErrorHandling(handleInvalidCredentials());
    }

    @Override public CommandRequest<SignInResult> signIn(String email, String password, String cartId) {
        if (Strings.isNullOrEmpty(cartId)) throw new IllegalArgumentException("cartId can't be empty.");
        CommandRequest<SignInResult> signInCmd = createSignInResultCommandRequest(
                endpoints.login(),
                new CustomerCommands.SignInWithCart(email, password, cartId));
        return signInCmd.withErrorHandling(handleInvalidCredentials());
    }

    @Override public CommandRequest<Customer> changePassword(VersionedId customerId, String currentPassword, String newPassword) {
        CommandRequest<Customer> changePasswordCmd = requestFactory.createCommandRequest(
                endpoints.customers.changePassword(),
                new CustomerCommands.ChangePassword(customerId.getId(), customerId.getVersion(), currentPassword, newPassword),
                new TypeReference<Customer>() {
                });
        return changePasswordCmd.withErrorHandling(new Function<SphereBackendException, SphereException>() {
            public SphereException apply(SphereBackendException e) {
                if (getSingleError(e, SphereError.InvalidCurrentPassword.class) != null) {
                    return new InvalidPasswordException();
                }
                return null;
            }
        });
    }

    @Override public CommandRequest<Customer> update(VersionedId customerId, CustomerUpdate customerUpdate) {
        return createCommandRequest(
                endpoints.customers.byId(customerId.getId()),
                new UpdateCommand<CustomerCommands.CustomerUpdateAction>(customerId.getVersion(), customerUpdate));
    }

    @Override public CommandRequest<CustomerToken> createPasswordResetToken(String email) {
        return createCustomerTokenCommandRequest(
                endpoints.customers.createPasswordResetToken(),
                new CustomerCommands.CreatePasswordResetToken(email));
    }

    @Override public CommandRequest<CustomerToken> createPasswordResetToken(String email, int ttlMinutes) {
        return createCustomerTokenCommandRequest(
                endpoints.customers.createPasswordResetToken(),
                new CustomerCommands.CreatePasswordResetToken(email, ttlMinutes));
    }

    @Override public CommandRequest<Customer> resetPassword(VersionedId customerId, String token, String newPassword) {
        return createCommandRequest(
                endpoints.customers.resetPassword(),
                new CustomerCommands.ResetCustomerPassword(customerId.getId(), customerId.getVersion(), token, newPassword));
    }

    @Override public CommandRequest<CustomerToken> createEmailVerificationToken(VersionedId customerId, int ttlMinutes) {
        return createCustomerTokenCommandRequest(
                endpoints.customers.createEmailVerificationToken(),
                new CustomerCommands.CreateEmailVerificationToken(customerId.getId(), customerId.getVersion(), ttlMinutes));
    }

    @Override public CommandRequest<Customer> confirmEmail(VersionedId customerId, String token) {
        return createCommandRequest(
                endpoints.customers.confirmEmail(),
                new CustomerCommands.VerifyCustomerEmail(customerId.getId(), customerId.getVersion(), token));
    }

    // ---------------------------------------
    // Helpers to save some repetitive code
    // ---------------------------------------

    /** Used by both signIn and signUp, the result type is the same. */
    private CommandRequest<SignInResult> createSignInResultCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<SignInResult>() {});
    }

    private CommandRequest<CustomerToken> createCustomerTokenCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<CustomerToken>() {});
    }
}
