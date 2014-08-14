package io.sphere.sdk.models;

import java.util.Optional;
import com.neovisionaries.i18n.CountryCode;
import net.jcip.annotations.Immutable;

/**
 * Represents a postal address.
 */
@Immutable
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
        return AddressBuilder.byAddress(this).country(country).build();
    }

    public Address withId(final Optional<String> id) {
        return AddressBuilder.byAddress(this).id(id).build();
    }

    public Address withTitle(final Optional<String> title) {
        return AddressBuilder.byAddress(this).title(title).build();
    }

    public Address withSalutation(final Optional<String> salutation) {
        return AddressBuilder.byAddress(this).salutation(salutation).build();
    }

    public Address withFirstName(final Optional<String> firstName) {
        return AddressBuilder.byAddress(this).firstName(firstName).build();
    }

    public Address withLastName(final Optional<String> lastName) {
        return AddressBuilder.byAddress(this).lastName(lastName).build();
    }

    public Address withStreetName(final Optional<String> streetName) {
        return AddressBuilder.byAddress(this).streetName(streetName).build();
    }

    public Address withStreetNumber(final Optional<String> streetNumber) {
        return AddressBuilder.byAddress(this).streetNumber(streetNumber).build();
    }

    public Address withAdditionalStreetInfo(final Optional<String> additionalStreetInfo) {
        return AddressBuilder.byAddress(this).additionalStreetInfo(additionalStreetInfo).build();
    }

    public Address withPostalCode(final Optional<String> postalCode) {
        return AddressBuilder.byAddress(this).postalCode(postalCode).build();
    }

    public Address withCity(final Optional<String> city) {
        return AddressBuilder.byAddress(this).city(city).build();
    }

    public Address withRegion(final Optional<String> region) {
        return AddressBuilder.byAddress(this).region(region).build();
    }

    public Address withState(final Optional<String> state) {
        return AddressBuilder.byAddress(this).state(state).build();
    }

    public Address withCompany(final Optional<String> company) {
        return AddressBuilder.byAddress(this).company(company).build();
    }

    public Address withDepartment(final Optional<String> department) {
        return AddressBuilder.byAddress(this).department(department).build();
    }

    public Address withBuilding(final Optional<String> building) {
        return AddressBuilder.byAddress(this).building(building).build();
    }

    public Address withApartment(final Optional<String> apartment) {
        return AddressBuilder.byAddress(this).apartment(apartment).build();
    }

    public Address withPoBox(final Optional<String> poBox) {
        return AddressBuilder.byAddress(this).poBox(poBox).build();
    }

    public Address withPhone(final Optional<String> phone) {
        return AddressBuilder.byAddress(this).phone(phone).build();
    }

    public Address withMobile(final Optional<String> mobile) {
        return AddressBuilder.byAddress(this).mobile(mobile).build();
    }

    public Address withEmail(final Optional<String> email) {
        return AddressBuilder.byAddress(this).email(email).build();
    }

    public Address withAdditionalAddressInfo(final Optional<String> additionalAddressInfo) {
        return AddressBuilder.byAddress(this).additionalAddressInfo(additionalAddressInfo).build();
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
