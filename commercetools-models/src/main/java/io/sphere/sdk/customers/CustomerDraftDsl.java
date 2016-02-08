package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Address;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.types.CustomFieldsDraft;

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
public final class CustomerDraftDsl extends Base implements CustomerDraft {
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


    CustomerDraftDsl(@Nullable final String customerNumber, final String email,
                     final String firstName, final String lastName, final String middleName,
                     final String password, final String title,
                     @Nullable final String externalId,
                     @Nullable final String anonymousCartId, @Nullable final LocalDate dateOfBirth,
                     @Nullable final String companyName, @Nullable final String vatId,
                     @Nullable final Boolean emailVerified, @Nullable final Reference<CustomerGroup> customerGroup,
                     @Nullable final Integer defaultBillingAddress, @Nullable final Integer defaultShippingAddress,
                     final List<Address> addresses, @Nullable final CustomFieldsDraft custom) {
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

    public static CustomerDraftDsl of(final CustomerName customerName, final String email, final String password) {
        return CustomerDraftBuilder.of(customerName, email, password).build();
    }

    public static CustomerDraft of(final String email, final String password) {
        return CustomerDraftBuilder.of(email, password).build();
    }

    @Override
    @Nullable
    public String getCustomerNumber() {
        return customerNumber;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    @Nullable
    public String getExternalId() {
        return externalId;
    }

    @Override
    @Nullable
    public String getAnonymousCartId() {
        return anonymousCartId;
    }

    @Override
    public CustomerName getName() {
        return CustomerName.of(getTitle(), getFirstName(), getMiddleName(), getLastName());
    }

    @Override
    @Nullable
    public String getCompanyName() {
        return companyName;
    }

    @Override
    @Nullable
    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }

    @Override
    @Nullable
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    @Nullable
    public Integer getDefaultBillingAddress() {
        return defaultBillingAddress;
    }

    @Override
    @Nullable
    public Integer getDefaultShippingAddress() {
        return defaultShippingAddress;
    }

    /**
     * isEmailVerified
     * @return isEmailVerified
     * @deprecated use {@link #isEmailVerified()}
     */
    @JsonIgnore
    @Deprecated
    public Boolean IsEmailVerified() {
        return emailVerified;
    }

    @Override
    @JsonProperty("isEmailVerified")
    public Boolean isEmailVerified() {
        return emailVerified;
    }

    @Override
    @Nullable
    public String getVatId() {
        return vatId;
    }

    @Override
    public List<Address> getAddresses() {
        return addresses;
    }

    @Override
    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    public CustomerDraftDsl withCustomerNumber(@Nullable final String customerNumber) {
        return newBuilder().customerNumber(customerNumber).build();
    }

    public CustomerDraftDsl withExternalId(@Nullable final String externalId) {
        return newBuilder().externalId(externalId).build();
    }
    
    public CustomerDraftDsl withAnonymousCartId(@Nullable final String anonymousCartId) {
        return newBuilder().anonymousCartId(anonymousCartId).build();
    }

    public CustomerDraftDsl withPassword(final String password) {
        return newBuilder().password(password).build();
    }

    public CustomerDraftDsl withCart(final Cart cart) {
        Objects.requireNonNull(cart);
        return withAnonymousCartId(cart.getId());
    }
    
    public CustomerDraftDsl withDateOfBirth(@Nullable final LocalDate dateOfBirth) {
        return newBuilder().dateOfBirth(dateOfBirth).build();
    }
    
    public CustomerDraftDsl withCompanyName(@Nullable final String companyName) {
        return newBuilder().companyName(companyName).build();
    }

    public CustomerDraftDsl withVatId(@Nullable final String vatId) {
        return newBuilder().vatId(vatId).build();
    }

    public CustomerDraftDsl withEmailVerified(@Nullable final Boolean emailVerified) {
        return newBuilder().emailVerified(emailVerified).build();
    }

    public CustomerDraftDsl withCustomerGroup(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        return newBuilder().customerGroup(Optional.ofNullable(customerGroup).map(Referenceable::toReference).orElse(null)).build();
    }

    /**
     * Sets the default billing address by using address in {@link CustomerDraftDsl#getAddresses()} which has the index of {@code defaultBillingAddress}.
     * @param defaultBillingAddress the index of the list
     * @return copy
     */
    public CustomerDraftDsl withDefaultBillingAddress(@Nullable final Integer defaultBillingAddress) {
        return newBuilder().defaultBillingAddress(defaultBillingAddress).build();
    }

    /**
     * Sets the default shipping address by using address in {@link CustomerDraftDsl#getAddresses()} which has the index of {@code defaultShippingAddress}.
     * @param defaultShippingAddress the index of the list
     * @return copy
     */
    public CustomerDraftDsl withDefaultShippingAddress(@Nullable final Integer defaultShippingAddress) {
        return newBuilder().defaultShippingAddress(defaultShippingAddress).build();
    }

    public CustomerDraftDsl withAddresses(final List<Address> addresses) {
        return newBuilder().addresses(addresses).build();
    }

    public CustomerDraftDsl withCustom(@Nullable final CustomFieldsDraft custom) {
        return newBuilder().custom(custom).build();
    }

    private CustomerDraftBuilder newBuilder() {
        return CustomerDraftBuilder.of(this);
    }
}
