package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.customers.commands.CustomerCreatePasswordTokenCommand;
import io.sphere.sdk.customers.commands.CustomerPasswordResetCommand;
import io.sphere.sdk.models.*;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.*;

import static java.util.stream.Collectors.toList;

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
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandIntegrationTest#createCustomer()}
 *
 * <p>Example for creating a customer with a cart:</p>
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateCommandIntegrationTest#createCustomerWithCart()}
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
 * {@include.example io.sphere.sdk.customers.commands.CustomerCreateEmailTokenCommandIntegrationTest#execution()}
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
 * {@include.example io.sphere.sdk.customers.commands.CustomerSignInCommandIntegrationTest#execution()}
 *
 *  <p>Example for invalid credentials:</p>
 * {@include.example io.sphere.sdk.customers.commands.CustomerSignInCommandIntegrationTest#executionWithInvalidEmail()}
 *
 * <h3 id="password-change">Changing the password of a customer</h3>
 * This covers the case that the customer forgot the password or just want to change it.
 *
 * First a password reset token needs to be created with {@link CustomerCreatePasswordTokenCommand} by using the customers email.
 * This token ({@link CustomerToken#getValue()} from the result of {@link CustomerCreatePasswordTokenCommand}) needs to be sent to the customers email address by the shop. Commercetools platform won't send the email.
 * The customer receives the token and can submit it to the shop including the new password. To change then the password in the commercetools platform use {@link CustomerPasswordResetCommand}.
 * The result of the command is the updated customer, so if the customer needs to sign in after the password change the command {@link io.sphere.sdk.customers.commands.CustomerSignInCommand} is required.
 *
 * {@include.example io.sphere.sdk.customers.commands.CustomerPasswordResetCommandIntegrationTest#execution()}
 *
 * <h3 id="update-customer">Update customer data</h3>
 *
 * See {@link io.sphere.sdk.customers.commands.CustomerUpdateCommand}.
 *
 * <h3 id="delete-customer">Delete a customer entry</h3>
 *
 * See {@link io.sphere.sdk.customers.commands.CustomerDeleteCommand}.
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
 * @see io.sphere.sdk.customers.queries.CustomerQuery
 * @see CustomerGroup
 * @see Cart#getCustomerId()
 * @see io.sphere.sdk.orders.Order#getCustomerId()
 * @see Payment#getCustomer()
 * @see io.sphere.sdk.payments.commands.updateactions.SetCustomer
 * @see Review#getCustomer()
 * @see io.sphere.sdk.reviews.commands.updateactions.SetCustomer
 */
@JsonDeserialize(as = CustomerImpl.class)
@ResourceValue
@HasQueryEndpoint(additionalContentsQueryInterface = "    default CustomerQuery byEmail(final String email) {\n" +
        "        return withPredicates(m -> m.lowercaseEmail().is(email.toLowerCase()));\n" +
        "    }")
@ResourceInfo(pluralName = "customers", pathElement = "customers")
@HasByIdGetEndpoint(javadocSummary = "Fetches a customer by a known ID.", includeExamples = "io.sphere.sdk.customers.queries.CustomerByIdGetIntegrationTest#execution()")
@HasByKeyGetEndpoint(javadocSummary = "Fetches a customer by a known key.", includeExamples = "io.sphere.sdk.customers.queries.CustomerByKeyGetIntegrationTest#execution()")
@HasUpdateCommand(javadocSummary = " Updates a customer.\n" +
        " \n" +
        " To update the properties {@link Customer#isEmailVerified()} or {@link Customer#getPassword()} special commands are required which are documented in the {@link Customer customer Javadoc}.",
        updateWith = "key")
@HasDeleteCommand(
        javadocSummary = "Deletes a customer.", includeExamples = "io.sphere.sdk.customers.commands.CustomerDeleteCommandIntegrationTest#execution()",
        deleteWith = "key",
        canEraseUsersData = true
)
@HasQueryModel(additionalContents = {
        "StringQuerySortingModel<Customer> lowercaseEmail();",
        "BooleanQueryModel<Customer> isEmailVerified();"
})
@HasUpdateActions
public interface Customer extends Resource<Customer>, Custom, WithKey {
    /**
     * Gets the ID of this customer.
     *
     * @see io.sphere.sdk.customers.queries.CustomerByIdGet
     * @see io.sphere.sdk.carts.queries.CartByCustomerIdGet
     *
     * @return ID
     */
    @HasNoUpdateAction
    @Override
    String getId();

    /**
     * User-specific unique identifier for a customer. Must be unique across a project.
     *
     * @see io.sphere.sdk.customers.queries.CustomerByKeyGet
     * @return key
     */
    @Nullable
    String getKey();

    /**
     * The customer number can be used to create a more human-readable (in contrast to ID) identifier for the customer.
     * It should be unique across a project. Once the field was set it cannot be changed anymore.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.SetCustomerNumber
     *
     * @return customer number or null
     */
    @Nullable
    String getCustomerNumber();

    /**
     * Customer’s email address that must be unique across a project.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.ChangeEmail
     *
     * @return email
     */
    String getEmail();

    /**
     * The customer's email address in lowercase.
     *
     * @return email in lowercase
     */
    @JsonIgnore
    default String getLowercaseEmail() {
        return getEmail().toLowerCase();
    }

    /**
     * First name of the customer.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.ChangeName
     * @see io.sphere.sdk.customers.commands.updateactions.SetFirstName
     *
     * @return first name or null
     */
    @Nullable
    String getFirstName();

    /**
     * Last name of the customer.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.ChangeName
     * @see io.sphere.sdk.customers.commands.updateactions.SetLastName
     *
     * @return last name
     */
    @Nullable
    String getLastName();

    @HasNoUpdateAction
    @IgnoreInQueryModel
    String getPassword();

    /**
     * Middle name of the customer.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.ChangeName
     * @see io.sphere.sdk.customers.commands.updateactions.SetMiddleName
     *
     * @return middle name
     */
    @Nullable
    String getMiddleName();

    /**
     * Title of the customer.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.ChangeName
     * @see io.sphere.sdk.customers.commands.updateactions.SetTitle
     *
     * @return title
     */
    @Nullable
    String getTitle();

    /**
     * Addresses related to this customer.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.AddAddress
     * @see io.sphere.sdk.customers.commands.updateactions.ChangeAddress
     * @see io.sphere.sdk.customers.commands.updateactions.RemoveAddress
     * @see io.sphere.sdk.customers.commands.updateactions.SetDefaultBillingAddress
     * @see io.sphere.sdk.customers.commands.updateactions.SetDefaultShippingAddress
     *
     * @return addressbook
     */
    @IgnoreInQueryModel
    @Nonnull
    List<Address> getAddresses();

    /**
     * ID of an address in {@link #getAddresses()} which contains the standard shipping address.
     *
     * <p>Access to the default shipping address is also possible with {@link #getDefaultShippingAddress()}.</p>
     *
     * @see io.sphere.sdk.customers.commands.updateactions.SetDefaultShippingAddress
     *
     * @return ID or null
     */
    @HasNoUpdateAction
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

    /**
     * ID of an address in {@link #getAddresses()} which contains the standard billing address.
     *
     * <p>Access to the default billing address is also possible with {@link #getDefaultBillingAddress()}.</p>
     *
     * @see io.sphere.sdk.customers.commands.updateactions.SetDefaultBillingAddress
     *
     * @return ID or null
     */
    @HasNoUpdateAction
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

    /**
     * Find the address in {@link #getAddresses()} by the address id
     * @param addressId the Id string of the address to find
     * @return Address or null
     */
    @HasNoUpdateAction
    @Nullable
    default Address getAddressById(final String addressId) {
        return findAddressById(addressId).orElse(null);
    }

    default Optional<Address> findAddressById(final String addressId) {
        return getAddresses().stream()
                .filter( address -> address.getId().equals(addressId) )
                .findFirst();
    }

    @HasNoUpdateAction
    @JsonProperty("isEmailVerified")
    @IgnoreInQueryModel//see class annotations
    Boolean isEmailVerified();

    /**
     * ID of an external system for this customer.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.SetExternalId
     *
     * @return ID or null
     */
    @Nullable
    String getExternalId();

    /**
     * The customer group of the customer.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.SetCustomerGroup
     *
     * @return group or null
     */
    @Nullable
    Reference<CustomerGroup> getCustomerGroup();

    default CustomerName getName() {
        return CustomerName.of(getTitle(), getFirstName(), getMiddleName(), getLastName());
    }

    /**
     * Name of the company this customer belongs to.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.SetCompanyName
     *
     * @return name or null
     */
    @Nullable
    @IgnoreInQueryModel
    String getCompanyName();

    /**
     * Value added tax identification number.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.SetVatId
     *
     * @return vat ID or null
     */
    @Nullable
    @IgnoreInQueryModel
    String getVatId();

    /**
     * The date of birth for this customer.
     *
     * @see io.sphere.sdk.customers.commands.updateactions.SetDateOfBirth
     *
     * @return date of birth or null
     */
    @Nullable
    @IgnoreInQueryModel
    LocalDate getDateOfBirth();

    @Override
    default Reference<Customer> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId(){
        return "customer";
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "customer";
    }

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<Customer> typeReference() {
        return new TypeReference<Customer>() {
            @Override
            public String toString() {
                return "TypeReference<Customer>";
            }
        };
    }

    /**
     * Creates a reference for one item of this class by a known ID.
     *
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     *
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<Customer> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    @Nullable
    @Override
    CustomFields getCustom();

    @Nullable
    Locale getLocale();

    @Nonnull
    List<String> getShippingAddressIds();

    @Nonnull
    List<String> getBillingAddressIds();


    /**
     * Customer’s salutation
     *
     * @see io.sphere.sdk.customers.commands.updateactions.SetSalutation
     *
     * @return salutation
     */
    @Nullable
    String getSalutation();

    @Nullable
    @IgnoreInQueryModel
    List<KeyReference<Store>> getStores();

    default List<Address> getShippingAddresses() {
        final Set<String> ids = new HashSet<>(getShippingAddressIds());
        return getAddresses().stream()
                .filter(id -> ids.contains(id.getId()))
                .collect(toList());
    }

    default List<Address> getBillingAddresses() {
        final Set<String> ids = new HashSet<>(getBillingAddressIds());
        return getAddresses().stream()
                .filter(id -> ids.contains(id.getId()))
                .collect(toList());
    }
}
