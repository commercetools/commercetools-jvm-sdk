package io.sphere.internal;

import com.google.common.base.Function;
import io.sphere.client.*;
import io.sphere.client.exceptions.EmailAlreadyInUseException;
import io.sphere.client.exceptions.InvalidPasswordException;
import io.sphere.client.exceptions.SphereBackendException;
import io.sphere.client.exceptions.SphereException;
import io.sphere.client.model.QueryResult;
import io.sphere.client.model.VersionedId;
import io.sphere.client.shop.ApiMode;
import io.sphere.client.shop.CustomerWithCart;
import io.sphere.client.shop.CustomerService;
import io.sphere.client.shop.model.Customer;
import io.sphere.client.shop.model.CustomerName;
import io.sphere.client.shop.model.CustomerToken;
import io.sphere.client.shop.model.CustomerUpdate;
import io.sphere.internal.command.Command;
import io.sphere.internal.command.CustomerCommands;
import io.sphere.internal.command.UpdateCommand;
import io.sphere.internal.request.RequestFactory;
import com.google.common.base.Optional;
import org.codehaus.jackson.type.TypeReference;

import static io.sphere.internal.util.Util.*;

public class CustomerServiceImpl extends ProjectScopedAPI implements CustomerService {
    private final RequestFactory requestFactory;

    public CustomerServiceImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
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

    @Override public QueryRequest<Customer> all() {
        return requestFactory.createQueryRequest(
                endpoints.customers.root(),
                Optional.<ApiMode>absent(),
                new TypeReference<QueryResult<Customer>>() {});
    }

    @Override public FetchRequest<CustomerWithCart> byCredentials(String email, String password) {
        return requestFactory.createFetchRequestWithErrorHandling(
                endpoints.customers.login(email, password),
                Optional.<ApiMode>absent(),
                401,
                new TypeReference<CustomerWithCart>() {
                });
    }

    /** Handles DuplicateField('email') on signup. */
    private Function<SphereBackendException, SphereException> handleDuplicateEmail(final String email) {
        return new Function<SphereBackendException, SphereException>() {
            public SphereException apply(SphereBackendException e) {
                SphereError.DuplicateField err = getSingleError(e, SphereError.DuplicateField.class);
                if (err != null && err.getField().equals("email")) {
                    return new EmailAlreadyInUseException(email);
                }
                return null;
            }
        };
    }

    @Override public CommandRequest<Customer> signup(String email, String password, CustomerName name) {
        CommandRequest<Customer> signupCmd = createCommandRequest(
                endpoints.customers.root(),
                new CustomerCommands.CreateCustomer(
                        email, password, name.getFirstName(), name.getLastName(), name.getMiddleName(), name.getTitle()));
        return signupCmd.withErrorHandling(handleDuplicateEmail(email));
    }

    @Override public CommandRequest<CustomerWithCart> signupWithCart(
            String email, String password, CustomerName name, VersionedId cartId) {
        CommandRequest<CustomerWithCart> signupCmd = requestFactory.createCommandRequest(
            endpoints.customers.signupWithCart(),
            new CustomerCommands.CreateCustomerWithCart(
                    email, password, name.getFirstName(), name.getLastName(), name.getMiddleName(), name.getTitle(),
                    cartId.getId(), cartId.getVersion()),
            new TypeReference<CustomerWithCart>() {});
        return signupCmd.withErrorHandling(handleDuplicateEmail(email));
    }

    @Override public CommandRequest<Customer> changePassword(VersionedId customerId, String currentPassword, String newPassword) {
        CommandRequest<Customer> changePasswordCmd = requestFactory.createCommandRequest(
                endpoints.customers.changePassword(),
                new CustomerCommands.ChangePassword(customerId.getId(), customerId.getVersion(), currentPassword, newPassword),
                new TypeReference<Customer>() {});
        return changePasswordCmd.withErrorHandling(new Function<SphereBackendException, SphereException>() {
            public SphereException apply(SphereBackendException e) {
                // This should be an InvalidField with field == 'password'
                if (getSingleError(e, SphereError.InvalidInput.class) != null) {
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

    /** Helper to save some repetitive code. */
    private CommandRequest<Customer> createCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<Customer>() {});
    }

    /** Helper to save some repetitive code. */
    private CommandRequest<CustomerToken> createCustomerTokenCommandRequest(String url, Command command) {
        return requestFactory.createCommandRequest(url, command, new TypeReference<CustomerToken>() {});
    }
}
