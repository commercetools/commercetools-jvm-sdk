package io.sphere.sdk.customers;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Template to create a new Customer.
 *
 * @see io.sphere.sdk.customers.CustomerDraftBuilder
 * @see io.sphere.sdk.customers.commands.CustomerCreateCommand
 */
public class CustomerDraft extends Base {
    private final Optional<String> customerNumber;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final Optional<String> middleName;
    private final String password;
    private final Optional<String> title;
    private final Optional<String> externalId;
    private final Optional<String> anonymousCartId;
    private final Optional<LocalDate> dateOfBirth;
    private final Optional<String> companyName;
    private final Optional<String> vatId;
    private final Optional<Boolean> emailVerified;
    private final Optional<Reference<CustomerGroup>> customerGroup;
    private final Optional<Integer> defaultBillingAddress;
    private final Optional<Integer> defaultShippingAddress;
    private final List<Address> addresses;

    CustomerDraft(final Optional<String> customerNumber, final String email,
                  final String firstName, final String lastName, final Optional<String> middleName,
                  final String password, final Optional<String> title,
                  final Optional<String> externalId,
                  final Optional<String> anonymousCartId, final Optional<LocalDate> dateOfBirth,
                  final Optional<String> companyName, final Optional<String> vatId,
                  final Optional<Boolean> emailVerified, final Optional<Reference<CustomerGroup>> customerGroup,
                  final Optional<Integer> defaultBillingAddress, final Optional<Integer> defaultShippingAddress, final List<Address> addresses) {
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
        if (!isValidAddressIndex(addresses, defaultBillingAddress)
                || !isValidAddressIndex(addresses, defaultShippingAddress)) {
            throw new IllegalArgumentException("The defaultBillingAddress and defaultShippingAddress cannot contain an index which");
        }
        this.defaultBillingAddress = defaultBillingAddress;
        this.defaultShippingAddress = defaultShippingAddress;
    }

    private static boolean isValidAddressIndex(final List<Address> addresses, final Optional<Integer> addressIndex) {
        return addressIndex.map(i -> i < addresses.size() && i >= 0).orElse(true);
    }

    public static CustomerDraft of(final CustomerName customerName, final String email, final String password) {
        return CustomerDraftBuilder.of(customerName, email, password).build();
    }

    public Optional<String> getCustomerNumber() {
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

    public Optional<String> getMiddleName() {
        return middleName;
    }

    public String getPassword() {
        return password;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getExternalId() {
        return externalId;
    }

    public Optional<String> getAnonymousCartId() {
        return anonymousCartId;
    }

    public CustomerName getName() {
        return CustomerName.of(getTitle(), getFirstName(), getMiddleName(), getLastName());
    }

    public Optional<String> getCompanyName() {
        return companyName;
    }

    public Optional<Reference<CustomerGroup>> getCustomerGroup() {
        return customerGroup;
    }

    public Optional<LocalDate> getDateOfBirth() {
        return dateOfBirth;
    }

    public Optional<Integer> getDefaultBillingAddress() {
        return defaultBillingAddress;
    }

    public Optional<Integer> getDefaultShippingAddress() {
        return defaultShippingAddress;
    }

    @JsonProperty("isEmailVerified")
    public Optional<Boolean> IsEmailVerified() {
        return emailVerified;
    }

    public Optional<String> getVatId() {
        return vatId;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public CustomerDraft withCustomerNumber(final Optional<String> customerNumber) {
        return newBuilder().customerNumber(customerNumber).build();
    }

    public CustomerDraft withCustomerNumber(final String customerNumber) {
        return withCustomerNumber(Optional.of(customerNumber));
    }
    
    public CustomerDraft withExternalId(final Optional<String> externalId) {
        return newBuilder().externalId(externalId).build();
    }

    public CustomerDraft withExternalId(final String externalId) {
        return withExternalId(Optional.of(externalId));
    }    
    
    public CustomerDraft withAnonymousCartId(final Optional<String> anonymousCartId) {
        return newBuilder().anonymousCartId(anonymousCartId).build();
    }

    public CustomerDraft withPassword(final String password) {
        return newBuilder().password(password).build();
    }

    public CustomerDraft withCart(final Cart cart) {
        return withAnonymousCartId(Optional.of(cart.getId()));
    }
    
    public CustomerDraft withDateOfBirth(final LocalDate dateOfBirth) {
        return newBuilder().dateOfBirth(dateOfBirth).build();
    }   
    
    public CustomerDraft withDateOfBirth(final Optional<LocalDate> dateOfBirth) {
        return newBuilder().dateOfBirth(dateOfBirth).build();
    }    
    
    public CustomerDraft withCompanyName(final String companyName) {
        return newBuilder().companyName(companyName).build();
    }   
    
    public CustomerDraft withCompanyName(final Optional<String> companyName) {
        return newBuilder().companyName(companyName).build();
    }

    public CustomerDraft withVatId(final String vatId) {
        return newBuilder().vatId(vatId).build();
    }

    public CustomerDraft withVatId(final Optional<String> vatId) {
        return newBuilder().vatId(vatId).build();
    }

    public CustomerDraft withEmailVerified(final boolean emailVerified) {
        return newBuilder().emailVerified(emailVerified).build();
    }

    public CustomerDraft withEmailVerified(final Optional<Boolean> emailVerified) {
        return newBuilder().emailVerified(emailVerified).build();
    }

    public CustomerDraft withCustomerGroup(final Referenceable<CustomerGroup> customerGroup) {
        return newBuilder().customerGroup(customerGroup).build();
    }

    public CustomerDraft withCustomerGroup(final Optional<Reference<CustomerGroup>> customerGroup) {
        return newBuilder().customerGroup(customerGroup).build();
    }

    /**
     * Sets the default billing address by using address in {@link CustomerDraft#getAddresses()} which has the index of {@code defaultBillingAddress}.
     * @param defaultBillingAddress the index of the list
     * @return copy
     */
    public CustomerDraft withDefaultBillingAddress(final int defaultBillingAddress) {
        return newBuilder().defaultBillingAddress(defaultBillingAddress).build();
    }

    /**
     * Sets the default billing address by using address in {@link CustomerDraft#getAddresses()} which has the index of {@code defaultBillingAddress}.
     * @param defaultBillingAddress the index of the list
     * @return copy
     */
    public CustomerDraft withDefaultBillingAddress(final Optional<Integer> defaultBillingAddress) {
        return newBuilder().defaultBillingAddress(defaultBillingAddress).build();
    }

    /**
     * Sets the default shipping address by using address in {@link CustomerDraft#getAddresses()} which has the index of {@code defaultShippingAddress}.
     * @param defaultShippingAddress the index of the list
     * @return copy
     */
    public CustomerDraft withDefaultShippingAddress(final int defaultShippingAddress) {
        return newBuilder().defaultShippingAddress(defaultShippingAddress).build();
    }

    /**
     * Sets the default shipping address by using address in {@link CustomerDraft#getAddresses()} which has the index of {@code defaultShippingAddress}.
     * @param defaultShippingAddress the index of the list
     * @return copy
     */
    public CustomerDraft withDefaultShippingAddress(final Optional<Integer> defaultShippingAddress) {
        return newBuilder().defaultShippingAddress(defaultShippingAddress).build();
    }

    public CustomerDraft withAddresses(final List<Address> addresses) {
        return newBuilder().addresses(addresses).build();
    }

    private CustomerDraftBuilder newBuilder() {
        return CustomerDraftBuilder.of(this);
    }

    //it is final to prevent subclasses to log the password
    @Override
    public final String toString() {
        final CustomerDraft outObject = withPassword("**removed from output**");
        return ToStringBuilder.reflectionToString(outObject, SdkDefaults.TO_STRING_STYLE);
    }
}
