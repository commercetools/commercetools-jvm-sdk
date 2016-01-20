package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.*;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Template to create a new Customer.
 *
 * @see io.sphere.sdk.customers.CustomerDraftBuilder
 * @see io.sphere.sdk.customers.commands.CustomerCreateCommand
 */
public class CustomerDraft extends Base implements CustomDraft {
    @Nullable
    private final String customerNumber;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final String middleName;
    private final String password;
    private final String title;
    @Nullable
    private final String externalId;
    @Nullable
    private final String anonymousCartId;
    @Nullable
    private final LocalDate dateOfBirth;
    @Nullable
    private final String companyName;
    @Nullable
    private final String vatId;
    @Nullable
    private final Boolean emailVerified;
    @Nullable
    private final Reference<CustomerGroup> customerGroup;
    @Nullable
    private final Integer defaultBillingAddress;
    @Nullable
    private final Integer defaultShippingAddress;
    private final List<Address> addresses;
    @Nullable
    private final CustomFieldsDraft custom;


    CustomerDraft(final String customerNumber, final String email,
                  final String firstName, final String lastName, final String middleName,
                  final String password, final String title,
                  final String externalId,
                  final String anonymousCartId, final LocalDate dateOfBirth,
                  final String companyName, final String vatId,
                  final Boolean emailVerified, final Reference<CustomerGroup> customerGroup,
                  final Integer defaultBillingAddress, final Integer defaultShippingAddress,
                  final List<Address> addresses, final CustomFieldsDraft custom) {
        this.customerNumber = customerNumber;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.password = password;
        this.title = title;
        this.externalId = externalId;
        this.anonymousCartId = anonymousCartId;
        this.dateOfBirth = dateOfBirth;
        this.companyName = companyName;
        this.vatId = vatId;
        this.emailVerified = emailVerified;
        this.customerGroup = customerGroup;
        this.addresses = addresses;
        this.custom = custom;
        if (!isValidAddressIndex(addresses, defaultBillingAddress)
                || !isValidAddressIndex(addresses, defaultShippingAddress)) {
            throw new IllegalArgumentException("The defaultBillingAddress and defaultShippingAddress cannot contain an index which is not in the address list");
        }
        this.defaultBillingAddress = defaultBillingAddress;
        this.defaultShippingAddress = defaultShippingAddress;
    }

    private static boolean isValidAddressIndex(final List<Address> addresses, final Integer addressIndex) {
        return Optional.ofNullable(addressIndex).map(i -> i < addresses.size() && i >= 0).orElse(true);
    }

    public static CustomerDraft of(final CustomerName customerName, final String email, final String password) {
        return CustomerDraftBuilder.of(customerName, email, password).build();
    }

    public static CustomerDraft of(final String email, final String password) {
        return CustomerDraftBuilder.of(email, password).build();
    }

    @Nullable
    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getPassword() {
        return password;
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    public String getExternalId() {
        return externalId;
    }

    @Nullable
    public String getAnonymousCartId() {
        return anonymousCartId;
    }

    public CustomerName getName() {
        return CustomerName.of(getTitle(), getFirstName(), getMiddleName(), getLastName());
    }

    @Nullable
    public String getCompanyName() {
        return companyName;
    }

    @Nullable
    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }

    @Nullable
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Nullable
    public Integer getDefaultBillingAddress() {
        return defaultBillingAddress;
    }

    @Nullable
    public Integer getDefaultShippingAddress() {
        return defaultShippingAddress;
    }

    @JsonProperty("isEmailVerified")
    public Boolean IsEmailVerified() {
        return emailVerified;
    }

    @Nullable
    public String getVatId() {
        return vatId;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    public CustomerDraft withCustomerNumber(@Nullable final String customerNumber) {
        return newBuilder().customerNumber(customerNumber).build();
    }

    public CustomerDraft withExternalId(@Nullable final String externalId) {
        return newBuilder().externalId(externalId).build();
    }
    
    public CustomerDraft withAnonymousCartId(@Nullable final String anonymousCartId) {
        return newBuilder().anonymousCartId(anonymousCartId).build();
    }

    public CustomerDraft withPassword(final String password) {
        return newBuilder().password(password).build();
    }

    public CustomerDraft withCart(final Cart cart) {
        Objects.requireNonNull(cart);
        return withAnonymousCartId(cart.getId());
    }
    
    public CustomerDraft withDateOfBirth(@Nullable final LocalDate dateOfBirth) {
        return newBuilder().dateOfBirth(dateOfBirth).build();
    }
    
    public CustomerDraft withCompanyName(@Nullable final String companyName) {
        return newBuilder().companyName(companyName).build();
    }

    public CustomerDraft withVatId(@Nullable final String vatId) {
        return newBuilder().vatId(vatId).build();
    }

    public CustomerDraft withEmailVerified(@Nullable final Boolean emailVerified) {
        return newBuilder().emailVerified(emailVerified).build();
    }

    public CustomerDraft withCustomerGroup(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        return newBuilder().customerGroup(Optional.ofNullable(customerGroup).map(Referenceable::toReference).orElse(null)).build();
    }

    /**
     * Sets the default billing address by using address in {@link CustomerDraft#getAddresses()} which has the index of {@code defaultBillingAddress}.
     * @param defaultBillingAddress the index of the list
     * @return copy
     */
    public CustomerDraft withDefaultBillingAddress(@Nullable final Integer defaultBillingAddress) {
        return newBuilder().defaultBillingAddress(defaultBillingAddress).build();
    }

    /**
     * Sets the default shipping address by using address in {@link CustomerDraft#getAddresses()} which has the index of {@code defaultShippingAddress}.
     * @param defaultShippingAddress the index of the list
     * @return copy
     */
    public CustomerDraft withDefaultShippingAddress(@Nullable final Integer defaultShippingAddress) {
        return newBuilder().defaultShippingAddress(defaultShippingAddress).build();
    }

    public CustomerDraft withAddresses(final List<Address> addresses) {
        return newBuilder().addresses(addresses).build();
    }

    public CustomerDraft withCustom(@Nullable final CustomFieldsDraft custom) {
        return newBuilder().custom(custom).build();
    }

    private CustomerDraftBuilder newBuilder() {
        return CustomerDraftBuilder.of(this);
    }
}
