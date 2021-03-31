package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.*;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Represents a postal address.
 *
 * <p>Use {@link io.sphere.sdk.models.AddressBuilder} to create an address.</p>
 *
 * <p>You can alternatively create a copy of an address with changed values by using the {@code with}-methods:</p>
 *
 * {@include.example example.AddressExample#withMethods()}
 *
 * @see Cart#getBillingAddress()
 * @see Cart#getShippingAddress()
 * @see io.sphere.sdk.orders.Order#getBillingAddress()
 * @see io.sphere.sdk.orders.Order#getShippingAddress()
 * @see Customer#getAddresses()
 * @see Customer#getDefaultBillingAddress()
 * @see Customer#getDefaultShippingAddress()
 * @see io.sphere.sdk.customers.commands.updateactions.AddAddress
 * @see io.sphere.sdk.customers.commands.updateactions.ChangeAddress
 * @see io.sphere.sdk.customers.commands.updateactions.RemoveAddress
 * @see io.sphere.sdk.customers.commands.updateactions.SetDefaultBillingAddress
 * @see io.sphere.sdk.customers.commands.updateactions.SetDefaultShippingAddress
 * @see io.sphere.sdk.carts.commands.updateactions.SetBillingAddress
 * @see io.sphere.sdk.carts.commands.updateactions.SetShippingAddress
 */
public final class Address extends Base implements WithKey {

    @Nullable
    final CountryCode country;
    @Nullable
    final String id;
    @Nullable
    final String key;
    @Nullable
    final String title;
    @Nullable
    final String salutation;
    @Nullable
    final String firstName;
    @Nullable
    final String lastName;
    @Nullable
    final String streetName;
    @Nullable
    final String streetNumber;
    @Nullable
    final String additionalStreetInfo;
    @Nullable
    final String postalCode;
    @Nullable
    final String city;
    @Nullable
    final String region;
    @Nullable
    final String state;
    @Nullable
    final String company;
    @Nullable
    final String department;
    @Nullable
    final String building;
    @Nullable
    final String apartment;
    @Nullable
    final String poBox;
    @Nullable
    final String phone;
    @Nullable
    final String mobile;
    @Nullable
    final String email;
    @Nullable
    final String additionalAddressInfo;
    @Nullable
    final String fax;
    @Nullable
    final String externalId;
    @Nullable
    final CustomFields customFields;
    @Nullable
    final CustomFieldsDraft customFieldsDraft;

    @JsonCreator
    private Address(final CountryCode country, @Nullable final String id,@Nullable final String key, @Nullable final String title, @Nullable final String salutation, @Nullable final String firstName, @Nullable final String lastName, @Nullable final String streetName, @Nullable final String streetNumber, @Nullable final String additionalStreetInfo, @Nullable final String postalCode, @Nullable final String city, @Nullable final String region, @Nullable final String state, @Nullable final String company, @Nullable final String department, @Nullable final String building, @Nullable final String apartment, @JsonProperty("pOBox") @Nullable final String poBox, @Nullable final String phone, @Nullable final String mobile, @Nullable final String email, @Nullable final String additionalAddressInfo, @Nullable final String fax, @Nullable final String externalId, @JsonProperty("custom") @Nullable CustomFields customFields, @Nullable CustomFieldsDraft customFieldsDraft) {
        this.country = country;
        this.id = id;
        this.key = key;
        this.title = title;
        this.salutation = salutation;
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.additionalStreetInfo = additionalStreetInfo;
        this.postalCode = postalCode;
        this.city = city;
        this.region = region;
        this.state = state;
        this.company = company;
        this.department = department;
        this.building = building;
        this.apartment = apartment;
        this.poBox = poBox;
        this.phone = phone;
        this.mobile = mobile;
        this.email = email;
        this.additionalAddressInfo = additionalAddressInfo;
        this.fax = fax;
        this.externalId = externalId;
        this.customFieldsDraft = customFieldsDraft;
        this.customFields = customFields;
    }

    @JsonIgnore
    Address(final AddressBuilder builder) {
        this.country = builder.country;
        this.id = builder.id;
        this.key = builder.key;
        this.title = builder.title;
        this.salutation = builder.salutation;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.streetName = builder.streetName;
        this.streetNumber = builder.streetNumber;
        this.additionalStreetInfo = builder.additionalStreetInfo;
        this.postalCode = builder.postalCode;
        this.city = builder.city;
        this.region = builder.region;
        this.state = builder.state;
        this.company = builder.company;
        this.department = builder.department;
        this.building = builder.building;
        this.apartment = builder.apartment;
        this.poBox = builder.poBox;
        this.phone = builder.phone;
        this.mobile = builder.mobile;
        this.email = builder.email;
        this.additionalAddressInfo = builder.additionalAddressInfo;
        this.fax = builder.fax;
        this.externalId = builder.externalId;
        this.customFields = builder.customFields;
        this.customFieldsDraft = builder.customFieldsDraft;
    }

    public CountryCode getCountry() {
        return country;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getSalutation() {
        return salutation;
    }

    @Nullable
    public String getFirstName() {
        return firstName;
    }

    @Nullable
    public String getLastName() {
        return lastName;
    }

    @Nullable
    public String getStreetName() {
        return streetName;
    }

    @Nullable
    public String getStreetNumber() {
        return streetNumber;
    }

    @Nullable
    public String getAdditionalStreetInfo() {
        return additionalStreetInfo;
    }

    @Nullable
    public String getPostalCode() {
        return postalCode;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    @Nullable
    public String getRegion() {
        return region;
    }

    @Nullable
    public String getState() {
        return state;
    }

    @Nullable
    public String getCompany() {
        return company;
    }

    @Nullable
    public String getDepartment() {
        return department;
    }

    @Nullable
    public String getBuilding() {
        return building;
    }

    @Nullable
    public String getApartment() {
        return apartment;
    }

    @Nullable
    @JsonGetter("pOBox")
    public String getPoBox() {
        return poBox;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    @Nullable
    public String getMobile() {
        return mobile;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getAdditionalAddressInfo() {
        return additionalAddressInfo;
    }

    @Nullable
    public String getFax() {
        return fax;
    }

    @Nullable
    public String getExternalId() {
        return externalId;
    }

    @Override
    @Nullable
    public String getKey() {
        return key;
    }

    @Nullable
    @JsonIgnore
    public CustomFields getCustomFields() {
        return customFields;
    }

    @Nullable
    @JsonIgnore
    public CustomFieldsDraft getCustomFieldsDraft() {
        return customFieldsDraft;
    }

    @Nullable
    @JsonGetter("custom")
    public Object getCustom() {
        return customFieldsDraft != null ? customFieldsDraft : customFields;
    }

    public Address withCountry(final CountryCode country) {
        return AddressBuilder.of(this).country(country).build();
    }

    public Address withId(@Nullable final String id) {
        return AddressBuilder.of(this).id(id).build();
    }

    public Address withKey(@Nullable final String key) {
        return AddressBuilder.of(this).key(key).build();
    }

    public Address withTitle(@Nullable final String title) {
        return AddressBuilder.of(this).title(title).build();
    }

    public Address withSalutation(@Nullable final String salutation) {
        return AddressBuilder.of(this).salutation(salutation).build();
    }

    public Address withFirstName(@Nullable final String firstName) {
        return AddressBuilder.of(this).firstName(firstName).build();
    }

    public Address withLastName(@Nullable final String lastName) {
        return AddressBuilder.of(this).lastName(lastName).build();
    }

    public Address withStreetName(@Nullable final String streetName) {
        return AddressBuilder.of(this).streetName(streetName).build();
    }

    public Address withStreetNumber(@Nullable final String streetNumber) {
        return AddressBuilder.of(this).streetNumber(streetNumber).build();
    }

    public Address withAdditionalStreetInfo(@Nullable final String additionalStreetInfo) {
        return AddressBuilder.of(this).additionalStreetInfo(additionalStreetInfo).build();
    }

    public Address withPostalCode(@Nullable final String postalCode) {
        return AddressBuilder.of(this).postalCode(postalCode).build();
    }

    public Address withCity(@Nullable final String city) {
        return AddressBuilder.of(this).city(city).build();
    }

    public Address withRegion(@Nullable final String region) {
        return AddressBuilder.of(this).region(region).build();
    }

    public Address withState(@Nullable final String state) {
        return AddressBuilder.of(this).state(state).build();
    }

    public Address withCompany(@Nullable final String company) {
        return AddressBuilder.of(this).company(company).build();
    }

    public Address withDepartment(@Nullable final String department) {
        return AddressBuilder.of(this).department(department).build();
    }

    public Address withBuilding(@Nullable final String building) {
        return AddressBuilder.of(this).building(building).build();
    }

    public Address withApartment(@Nullable final String apartment) {
        return AddressBuilder.of(this).apartment(apartment).build();
    }

    public Address withPoBox(@Nullable final String poBox) {
        return AddressBuilder.of(this).poBox(poBox).build();
    }

    public Address withPhone(@Nullable final String phone) {
        return AddressBuilder.of(this).phone(phone).build();
    }

    public Address withMobile(@Nullable final String mobile) {
        return AddressBuilder.of(this).mobile(mobile).build();
    }

    public Address withEmail(@Nullable final String email) {
        return AddressBuilder.of(this).email(email).build();
    }

    public Address withAdditionalAddressInfo(@Nullable final String additionalAddressInfo) {
        return AddressBuilder.of(this).additionalAddressInfo(additionalAddressInfo).build();
    }

    public Address withFax(@Nullable final String fax) {
        return AddressBuilder.of(this).fax(fax).build();
    }

    public Address withExternalId(@Nullable final String externalId) {
        return AddressBuilder.of(this).externalId(externalId).build();
    }

    public Address withCustomFields(@Nullable final CustomFields customFields) {
        return AddressBuilder.of(this).customFields(customFields).build();
    }

    public Address withCustomFields(@Nullable final CustomFieldsDraft customFields) {
        return AddressBuilder.of(this).customFields(customFields).build();
    }


    public static Address of(final CountryCode country) {
        Objects.requireNonNull(country);
        return AddressBuilder.of(country).build();
    }

    @Override
    public String toString() {
        return "Address{" +
                "country=" + country +
                ", id=" + id +
                ", key=" + key +
                ", title=" + title +
                ", salutation=" + salutation +
                ", firstName=" + firstName +
                ", lastName=" + lastName +
                ", streetName=" + streetName +
                ", streetNumber=" + streetNumber +
                ", additionalStreetInfo=" + additionalStreetInfo +
                ", postalCode=" + postalCode +
                ", city=" + city +
                ", region=" + region +
                ", state=" + state +
                ", company=" + company +
                ", department=" + department +
                ", building=" + building +
                ", apartment=" + apartment +
                ", poBox=" + poBox +
                ", phone=" + phone +
                ", mobile=" + mobile +
                ", email=" + email +
                ", additionalAddressInfo=" + additionalAddressInfo +
                ", fax=" + fax +
                ", externalId=" + externalId +
                ", customFields=" + customFields +
                ", customFieldsDraft=" + customFieldsDraft +
                '}';
    }

    public boolean equalsIgnoreId(final Address address) {
        return address != null && address.withId(null).equals(withId(null));
    }

    public static String resourceTypeId() {
        return "address";
    }
}
