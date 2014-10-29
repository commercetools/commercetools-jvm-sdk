package io.sphere.sdk.models;

import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;

/**
 * Represents a postal address.
 *
 * <p>Use {@link io.sphere.sdk.models.AddressBuilder} to create an address.</p>
 *
 * <p>You can can create a copy of an address with changed values by using the {@code with}-methods:</p>
 *
 * {@include.example example.AddressExample#withMethods()}
 */
public class Address extends Base {

    final CountryCode country;
    final Optional<String> id;
    final Optional<String> title;
    final Optional<String> salutation;
    final Optional<String> firstName;
    final Optional<String> lastName;
    final Optional<String> streetName;
    final Optional<String> streetNumber;
    final Optional<String> additionalStreetInfo;
    final Optional<String> postalCode;
    final Optional<String> city;
    final Optional<String> region;
    final Optional<String> state;
    final Optional<String> company;
    final Optional<String> department;
    final Optional<String> building;
    final Optional<String> apartment;
    final Optional<String> poBox;
    final Optional<String> phone;
    final Optional<String> mobile;
    final Optional<String> email;
    final Optional<String> additionalAddressInfo;

    @JsonCreator
    private Address(final CountryCode country, final Optional<String> id, final Optional<String> title, final Optional<String> salutation, final Optional<String> firstName, final Optional<String> lastName, final Optional<String> streetName, final Optional<String> streetNumber, final Optional<String> additionalStreetInfo, final Optional<String> postalCode, final Optional<String> city, final Optional<String> region, final Optional<String> state, final Optional<String> company, final Optional<String> department, final Optional<String> building, final Optional<String> apartment, final Optional<String> poBox, final Optional<String> phone, final Optional<String> mobile, final Optional<String> email, final Optional<String> additionalAddressInfo) {
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

    public Optional<String> getId() {
        return id;
    }

    public Optional<String> getTitle() {
        return title;
    }

    public Optional<String> getSalutation() {
        return salutation;
    }

    public Optional<String> getFirstName() {
        return firstName;
    }

    public Optional<String> getLastName() {
        return lastName;
    }

    public Optional<String> getStreetName() {
        return streetName;
    }

    public Optional<String> getStreetNumber() {
        return streetNumber;
    }

    public Optional<String> getAdditionalStreetInfo() {
        return additionalStreetInfo;
    }

    public Optional<String> getPostalCode() {
        return postalCode;
    }

    public Optional<String> getCity() {
        return city;
    }

    public Optional<String> getRegion() {
        return region;
    }

    public Optional<String> getState() {
        return state;
    }

    public Optional<String> getCompany() {
        return company;
    }

    public Optional<String> getDepartment() {
        return department;
    }

    public Optional<String> getBuilding() {
        return building;
    }

    public Optional<String> getApartment() {
        return apartment;
    }

    public Optional<String> getPoBox() {
        return poBox;
    }

    public Optional<String> getPhone() {
        return phone;
    }

    public Optional<String> getMobile() {
        return mobile;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public Optional<String> getAdditionalAddressInfo() {
        return additionalAddressInfo;
    }

    public Address withCountry(final CountryCode country) {
        return AddressBuilder.of(this).country(country).build();
    }

    public Address withId(final Optional<String> id) {
        return AddressBuilder.of(this).id(id).build();
    }

    public Address withId(final String id) {
        Objects.requireNonNull(id);
        return withId(Optional.of(id));
    }

    public Address withTitle(final Optional<String> title) {
        return AddressBuilder.of(this).title(title).build();
    }

    public Address withTitle(final String title) {
        Objects.requireNonNull(title);
        return withTitle(Optional.of(title));
    }

    public Address withSalutation(final Optional<String> salutation) {
        return AddressBuilder.of(this).salutation(salutation).build();
    }

    public Address withSalutation(final String salutation) {
        Objects.requireNonNull(salutation);
        return withSalutation(Optional.of(salutation));
    }

    public Address withFirstName(final Optional<String> firstName) {
        return AddressBuilder.of(this).firstName(firstName).build();
    }

    public Address withFirstName(final String firstName) {
        Objects.requireNonNull(firstName);
        return withFirstName(Optional.of(firstName));
    }

    public Address withLastName(final Optional<String> lastName) {
        return AddressBuilder.of(this).lastName(lastName).build();
    }

    public Address withLastName(final String lastName) {
        Objects.requireNonNull(lastName);
        return withLastName(Optional.of(lastName));
    }

    public Address withStreetName(final Optional<String> streetName) {
        return AddressBuilder.of(this).streetName(streetName).build();
    }

    public Address withStreetName(final String streetName) {
        Objects.requireNonNull(streetName);
        return withStreetName(Optional.of(streetName));
    }

    public Address withStreetNumber(final Optional<String> streetNumber) {
        return AddressBuilder.of(this).streetNumber(streetNumber).build();
    }

    public Address withStreetNumber(final String streetNumber) {
        Objects.requireNonNull(streetNumber);
        return withStreetNumber(Optional.of(streetNumber));
    }

    public Address withAdditionalStreetInfo(final Optional<String> additionalStreetInfo) {
        return AddressBuilder.of(this).additionalStreetInfo(additionalStreetInfo).build();
    }

    public Address withAdditionalStreetInfo(final String additionalStreetInfo) {
        Objects.requireNonNull(additionalStreetInfo);
        return withAdditionalStreetInfo(Optional.of(additionalStreetInfo));
    }

    public Address withPostalCode(final Optional<String> postalCode) {
        return AddressBuilder.of(this).postalCode(postalCode).build();
    }

    public Address withPostalCode(final String postalCode) {
        Objects.requireNonNull(postalCode);
        return withPostalCode(Optional.of(postalCode));
    }

    public Address withCity(final Optional<String> city) {
        return AddressBuilder.of(this).city(city).build();
    }

    public Address withCity(final String city) {
        Objects.requireNonNull(city);
        return withCity(Optional.of(city));
    }

    public Address withRegion(final Optional<String> region) {
        return AddressBuilder.of(this).region(region).build();
    }

    public Address withRegion(final String region) {
        Objects.requireNonNull(region);
        return withRegion(Optional.of(region));
    }

    public Address withState(final Optional<String> state) {
        return AddressBuilder.of(this).state(state).build();
    }

    public Address withState(final String state) {
        Objects.requireNonNull(state);
        return withState(Optional.of(state));
    }

    public Address withCompany(final Optional<String> company) {
        return AddressBuilder.of(this).company(company).build();
    }

    public Address withCompany(final String company) {
        Objects.requireNonNull(company);
        return withCompany(Optional.of(company));
    }

    public Address withDepartment(final Optional<String> department) {
        return AddressBuilder.of(this).department(department).build();
    }

    public Address withDepartment(final String department) {
        Objects.requireNonNull(department);
        return withDepartment(Optional.of(department));
    }

    public Address withBuilding(final Optional<String> building) {
        return AddressBuilder.of(this).building(building).build();
    }

    public Address withBuilding(final String building) {
        Objects.requireNonNull(building);
        return withBuilding(Optional.of(building));
    }

    public Address withApartment(final Optional<String> apartment) {
        return AddressBuilder.of(this).apartment(apartment).build();
    }

    public Address withApartment(final String apartment) {
        Objects.requireNonNull(apartment);
        return withApartment(Optional.of(apartment));
    }

    public Address withPoBox(final Optional<String> poBox) {
        return AddressBuilder.of(this).poBox(poBox).build();
    }

    public Address withPoBox(final String poBox) {
        Objects.requireNonNull(poBox);
        return withPoBox(Optional.of(poBox));
    }

    public Address withPhone(final Optional<String> phone) {
        return AddressBuilder.of(this).phone(phone).build();
    }

    public Address withPhone(final String phone) {
        Objects.requireNonNull(phone);
        return withPhone(Optional.of(phone));
    }

    public Address withMobile(final Optional<String> mobile) {
        return AddressBuilder.of(this).mobile(mobile).build();
    }

    public Address withMobile(final String mobile) {
        Objects.requireNonNull(mobile);
        return withMobile(Optional.of(mobile));
    }

    public Address withEmail(final Optional<String> email) {
        return AddressBuilder.of(this).email(email).build();
    }

    public Address withEmail(final String email) {
        Objects.requireNonNull(email);
        return withEmail(Optional.of(email));
    }

    public Address withAdditionalAddressInfo(final Optional<String> additionalAddressInfo) {
        return AddressBuilder.of(this).additionalAddressInfo(additionalAddressInfo).build();
    }

    public Address withAdditionalAddressInfo(final String additionalAddressInfo) {
        Objects.requireNonNull(additionalAddressInfo);
        return withAdditionalAddressInfo(Optional.of(additionalAddressInfo));
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
