package io.sphere.sdk.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;

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
 */
public class Address extends Base {

    @Nullable
    final CountryCode country;
    @Nullable
    final String id;
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

    @JsonCreator
    private Address(final CountryCode country, final String id, final String title, final String salutation, final String firstName, final String lastName, final String streetName, final String streetNumber, final String additionalStreetInfo, final String postalCode, final String city, final String region, final String state, final String company, final String department, final String building, final String apartment, final String poBox, final String phone, final String mobile, final String email, final String additionalAddressInfo) {
        this.country = country;
        this.id = id;
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
    }

    @JsonIgnore
    Address(final AddressBuilder builder) {
        this.country = builder.country;
        this.id = builder.id;
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
    }

    public CountryCode getCountry() {
        return country;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSalutation() {
        return salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public String getAdditionalStreetInfo() {
        return additionalStreetInfo;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getState() {
        return state;
    }

    public String getCompany() {
        return company;
    }

    public String getDepartment() {
        return department;
    }

    public String getBuilding() {
        return building;
    }

    public String getApartment() {
        return apartment;
    }

    public String getPoBox() {
        return poBox;
    }

    public String getPhone() {
        return phone;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getAdditionalAddressInfo() {
        return additionalAddressInfo;
    }

    public Address withCountry(final CountryCode country) {
        return AddressBuilder.of(this).country(country).build();
    }

    public Address withId(final String id) {
        return AddressBuilder.of(this).id(id).build();
    }

    public Address withTitle(final String title) {
        return AddressBuilder.of(this).title(title).build();
    }

    public Address withSalutation(final String salutation) {
        return AddressBuilder.of(this).salutation(salutation).build();
    }

    public Address withFirstName(final String firstName) {
        return AddressBuilder.of(this).firstName(firstName).build();
    }

    public Address withLastName(final String lastName) {
        return AddressBuilder.of(this).lastName(lastName).build();
    }

    public Address withStreetName(final String streetName) {
        return AddressBuilder.of(this).streetName(streetName).build();
    }

    public Address withStreetNumber(final String streetNumber) {
        return AddressBuilder.of(this).streetNumber(streetNumber).build();
    }

    public Address withAdditionalStreetInfo(final String additionalStreetInfo) {
        return AddressBuilder.of(this).additionalStreetInfo(additionalStreetInfo).build();
    }

    public Address withPostalCode(final String postalCode) {
        return AddressBuilder.of(this).postalCode(postalCode).build();
    }

    public Address withCity(final String city) {
        return AddressBuilder.of(this).city(city).build();
    }

    public Address withRegion(final String region) {
        return AddressBuilder.of(this).region(region).build();
    }

    public Address withState(final String state) {
        return AddressBuilder.of(this).state(state).build();
    }

    public Address withCompany(final String company) {
        return AddressBuilder.of(this).company(company).build();
    }

    public Address withDepartment(final String department) {
        return AddressBuilder.of(this).department(department).build();
    }

    public Address withBuilding(final String building) {
        return AddressBuilder.of(this).building(building).build();
    }

    public Address withApartment(final String apartment) {
        return AddressBuilder.of(this).apartment(apartment).build();
    }

    public Address withPoBox(final String poBox) {
        return AddressBuilder.of(this).poBox(poBox).build();
    }

    public Address withPhone(final String phone) {
        return AddressBuilder.of(this).phone(phone).build();
    }

    public Address withMobile(final String mobile) {
        return AddressBuilder.of(this).mobile(mobile).build();
    }

    public Address withEmail(final String email) {
        return AddressBuilder.of(this).email(email).build();
    }

    public Address withAdditionalAddressInfo(final String additionalAddressInfo) {
        return AddressBuilder.of(this).additionalAddressInfo(additionalAddressInfo).build();
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
                '}';
    }
}
