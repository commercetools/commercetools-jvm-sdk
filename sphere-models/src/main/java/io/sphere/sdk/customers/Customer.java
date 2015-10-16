package io.sphere.sdk.customers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;
import io.sphere.sdk.customers.commands.CustomerPasswordResetCommand;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.types.Custom;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * A customer is a person purchasing products. Carts, Orders and Reviews can be associated to a customer.
 *
 * <p>A Customer can have {@link io.sphere.sdk.types.Custom custom fields}.</p>
 *
 * <h3 id="create-customer">Create a customer/sign-up</h3>
 *
 * The customer who signs up can have an anonymous cart which can be assigned to the customer.
 * For convenience the {@link io.sphere.sdk.customers.commands.CustomerCreateCommand}
 * does not directly return a customer but an object ({@link CustomerSignInResult}) which has a field "customer" for the {@link Customer}
 * and another field "cart" which can optionally have a cart if in the customer creation the cart id has been used.
 *
 * <p>An example for creating a customer without a cart:</p>
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandTest#createCustomer()}
 *
 * <p>Example for creating a customer with a cart:</p>
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandTest#createCustomerWithCart()}
 * <h3 id="verify-email">Verify the customers email address</h3>
 *
 * The customer contains the property {@link Customer#isEmailVerified()}, which is by default false.
 * If the shop is not supposed to use the commercetools platform email authentication and proved the customer email somehow else
 * then a customer can be created with this field set to true: {@link CustomerDraftBuilder#emailVerified(Boolean)}.
 *
 * To verify the customers email address with commercetools platform first an email token needs to be created with {@link io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommand}.
 * You can specify a certain time frame so that the token gets invalidated at some point.
 * The response contains a token {@link CustomerToken#getValue()} which needs to be sent to the customer.
 * Commercetools platform won't send an email to the customer. The shop must implement this feature.
 * When the customer received the token send he/she needs to submit to the shop and then the shop to the platform with {@link io.sphere.sdk.customers.commands.CustomerVerifyEmailCommand}.
 *
 * <p>Example</p>
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommandTest#execution()}
 *
 * <h3 id="sign-in">Sign-in a customer</h3>
 *
 * Before signing in, a customer might have created an anonymous cart.
 * After signing in with {@link io.sphere.sdk.customers.commands.CustomerSignInCommand}, the content of the anonymous cart should be in the customer's cart.
 * If the customer did not have a cart associated to him, then the anonymous cart becomes the customer's cart.
 * If a customer already had a cart associated to him, then the content of the anonymous cart will be copied to the customer's cart.
 * If a line item in the anonymous cart matches an existing line item in the customer's cart (same product ID and variant ID),
 * then the maximum quantity of both line items is used as the new quantity.
 *
 * <p>For convenience the {@link io.sphere.sdk.customers.commands.CustomerCreateCommand} which contains the customer
 * and the optional cart.</p>
 *
 * <p>Example for a successful sign in:</p>
 * {@include.example io.sphere.sdk.customers.commands.CustomerSignInCommandTest#execution()}
 *
 *  <p>Example for invalid credentials:</p>
 * {@include.example io.sphere.sdk.customers.commands.CustomerSignInCommandTest#executionWithInvalidEmail()}
 *
 * <h3 id="password-change">Changing the password of a customer</h3>
 * This covers the case that the customer forgot the password or just want to change it.
 *
 * First a password reset token needs to be created with {@link CustomerCreatePasswordTokenCommand} by using the customers email.
 * This token ({@link CustomerToken#getValue()} from the result of {@link CustomerCreatePasswordTokenCommand}) needs to be sent to the customers email address by the shop. Commercetools platform won't send the email.
 * The customer receives the token and can submit it to the shop including the new password. To change then the password in the commercetools platform use {@link CustomerPasswordResetCommand}.
 * The result of the command is the updated customer, so if the customer needs to sign in after the password change the command {@link io.sphere.sdk.customers.commands.CustomerSignInCommand} is required.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerPasswordResetCommandTest#execution()}
 *
 * <h3 id="update-customer">Update customer data</h3>
 *
 * See {@link io.sphere.sdk.customers.commands.CustomerUpdateCommand}.
 *
 * <h3 id="delete-customer">Delete a customer entry</h3>
 *
 * See {@link io.sphere.sdk.customers.commands.CustomerDeleteCommand}.
 *
 *
 * @see io.sphere.sdk.customers.commands.CustomerChangePasswordCommand
 * @see io.sphere.sdk.customers.commands.CustomerCreateCommand
 * @see io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand
 * @see io.sphere.sdk.customers.commands.CustomerDeleteCommand
 * @see io.sphere.sdk.customers.commands.CustomerPasswordResetCommand
 * @see io.sphere.sdk.customers.commands.CustomerSignInCommand
 * @see io.sphere.sdk.customers.commands.CustomerUpdateCommand
 * @see io.sphere.sdk.customers.commands.CustomerVerifyEmailCommand
 * @see io.sphere.sdk.customers.queries.CustomerByIdGet
 * @see io.sphere.sdk.customers.queries.CustomerByTokenGet
 * @see io.sphere.sdk.customers.queries.CustomerQuery
 */
@JsonDeserialize(as = CustomerImpl.class)
public interface Customer extends Resource<Customer>, Custom {
    @Nullable
    String getCustomerNumber();

    String getEmail();

    String getFirstName();

    String getLastName();

    String getPassword();

    String getMiddleName();

    String getTitle();

    List<Address> getAddresses();

    @Nullable
    String getDefaultShippingAddressId();

    default Optional<Address> findDefaultShippingAddress() {
        return getAddresses().stream()
                .filter(address -> address.getId() != null && address.getId().equals(getDefaultShippingAddressId()))
                .findFirst();
    }

    @Nullable
    default Address getDefaultShippingAddress() {
        return findDefaultShippingAddress().orElse(null);
    }

    @Nullable
    String getDefaultBillingAddressId();

    @Nullable
    default Address getDefaultBillingAddress() {
        return findDefaultBillingAddress().orElse(null);
    }

    default Optional<Address> findDefaultBillingAddress() {
        final String defaultBillingAddressId = getDefaultBillingAddressId();

        return getAddresses().stream()
                .filter(address -> defaultBillingAddressId != null && address.getId() != null && address.getId().equals(defaultBillingAddressId))
                .findFirst();
    }

    Boolean isEmailVerified();

    @Nullable
    String getExternalId();

    @Nullable
    Reference<CustomerGroup> getCustomerGroup();

    default CustomerName getName() {
        return CustomerName.of(getTitle(), getFirstName(), getMiddleName(), getLastName());
    }

    @Nullable
    String getCompanyName();

    @Nullable
    String getVatId();

    @Nullable
    LocalDate getDateOfBirth();

    @Override
    default Reference<Customer> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    static String resourceTypeId(){
        return "customer";
    }

    static String referenceTypeId(){
        return "customer";
    }

    /**
     *
     * @deprecated use {@link #referenceTypeId()} instead
     * @return referenceTypeId
     */
    @Deprecated
    static String typeId(){
        return "customer";
    }

    static TypeReference<Customer> typeReference(){
        return new TypeReference<Customer>() {
            @Override
            public String toString() {
                return "TypeReference<Customer>";
            }
        };
    }
}
