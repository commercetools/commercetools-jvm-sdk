package io.sphere.sdk.models;

import com.neovisionaries.i18n.CountryCode;

import java.util.Optional;

/**
 * Builds Address instances using the Builder pattern (a stateful object).
 *
 * <p>Create an entire new address:</p>
 *
 * {@include.example example.AddressExample#byCountryCreation()}
 *
 * <p>Create an address based on an existing address:</p>
 *
 * {@include.example example.AddressExample#buildByExistingAddress()}
 */
public class AddressBuilder extends Base implements Builder<Address> {
    CountryCode country;
    Optional<String> id = Optional.empty();
    Optional<String> title = Optional.empty();
    Optional<String> salutation = Optional.empty();
    Optional<String> firstName = Optional.empty();
    Optional<String> lastName = Optional.empty();
    Optional<String> streetName = Optional.empty();
    Optional<String> streetNumber = Optional.empty();
    Optional<String> additionalStreetInfo = Optional.empty();
    Optional<String> postalCode = Optional.empty();
    Optional<String> city = Optional.empty();
    Optional<String> region = Optional.empty();
    Optional<String> state = Optional.empty();
    Optional<String> company = Optional.empty();
    Optional<String> department = Optional.empty();
    Optional<String> building = Optional.empty();
    Optional<String> apartment = Optional.empty();
    Optional<String> poBox = Optional.empty();
    Optional<String> phone = Optional.empty();
    Optional<String> mobile = Optional.empty();
    Optional<String> email = Optional.empty();
    Optional<String> additionalAddressInfo = Optional.empty();


    AddressBuilder(final CountryCode country) {
        this.country = country;
    }

    public static AddressBuilder of(final Address address) {
        final AddressBuilder builder = new AddressBuilder(address.country);
        builder.id = address.id;
        builder.title = address.title;
        builder.salutation = address.salutation;
        builder.firstName = address.firstName;
        builder.lastName = address.lastName;
        builder.streetName = address.streetName;
        builder.streetNumber = address.streetNumber;
        builder.additionalStreetInfo = address.additionalStreetInfo;
        builder.postalCode = address.postalCode;
        builder.city = address.city;
        builder.region = address.region;
        builder.state = address.state;
        builder.company = address.company;
        builder.department = address.department;
        builder.building = address.building;
        builder.apartment = address.apartment;
        builder.poBox = address.poBox;
        builder.phone = address.phone;
        builder.mobile = address.mobile;
        builder.email = address.email;
        builder.additionalAddressInfo = address.additionalAddressInfo;
        return builder;
    }

    public static AddressBuilder of(final CountryCode country) {
        return new AddressBuilder(country);
    }

    public Address build() {
        return new Address(this);
    }

    public AddressBuilder country(final CountryCode country) {
        this.country = country;
        return this;
    }

    public AddressBuilder id(final Optional<String> id) {
        this.id = id;
        return this;
    }

    public AddressBuilder id(final String id) {
        return id(Optional.ofNullable(id));
    }

    public AddressBuilder title(final Optional<String> title) {
        this.title = title;
        return this;
    }

    public AddressBuilder title(final String title) {
        return title(Optional.ofNullable(title));
    }

    public AddressBuilder salutation(final Optional<String> salutation) {
        this.salutation = salutation;
        return this;
    }

    public AddressBuilder salutation(final String salutation) {
        return salutation(Optional.ofNullable(salutation));
    }

    public AddressBuilder firstName(final Optional<String> firstName) {
        this.firstName = firstName;
        return this;
    }

    public AddressBuilder firstName(final String firstName) {
        return firstName(Optional.ofNullable(firstName));
    }

    public AddressBuilder lastName(final Optional<String> lastName) {
        this.lastName = lastName;
        return this;
    }

    public AddressBuilder lastName(final String lastName) {
        return lastName(Optional.ofNullable(lastName));
    }

    public AddressBuilder streetName(final Optional<String> streetName) {
        this.streetName = streetName;
        return this;
    }

    public AddressBuilder streetName(final String streetName) {
        return streetName(Optional.ofNullable(streetName));
    }

    public AddressBuilder streetNumber(final Optional<String> streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public AddressBuilder streetNumber(final String streetNumber) {
        return streetNumber(Optional.ofNullable(streetNumber));
    }

    public AddressBuilder additionalStreetInfo(final Optional<String> additionalStreetInfo) {
        this.additionalStreetInfo = additionalStreetInfo;
        return this;
    }

    public AddressBuilder additionalStreetInfo(final String additionalStreetInfo) {
        return additionalStreetInfo(Optional.ofNullable(additionalStreetInfo));
    }

    public AddressBuilder postalCode(final Optional<String> postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public AddressBuilder postalCode(final String postalCode) {
        return postalCode(Optional.ofNullable(postalCode));
    }

    public AddressBuilder city(final Optional<String> city) {
        this.city = city;
        return this;
    }

    public AddressBuilder city(final String city) {
        return city(Optional.ofNullable(city));
    }

    public AddressBuilder region(final Optional<String> region) {
        this.region = region;
        return this;
    }

    public AddressBuilder region(final String region) {
        return region(Optional.ofNullable(region));
    }

    public AddressBuilder state(final Optional<String> state) {
        this.state = state;
        return this;
    }

    public AddressBuilder state(final String state) {
        return state(Optional.ofNullable(state));
    }

    public AddressBuilder company(final Optional<String> company) {
        this.company = company;
        return this;
    }

    public AddressBuilder company(final String company) {
        return company(Optional.ofNullable(company));
    }

    public AddressBuilder department(final Optional<String> department) {
        this.department = department;
        return this;
    }

    public AddressBuilder department(final String department) {
        return department(Optional.ofNullable(department));
    }

    public AddressBuilder building(final Optional<String> building) {
        this.building = building;
        return this;
    }

    public AddressBuilder building(final String building) {
        return building(Optional.ofNullable(building));
    }

    public AddressBuilder apartment(final Optional<String> apartment) {
        this.apartment = apartment;
        return this;
    }

    public AddressBuilder apartment(final String apartment) {
        return apartment(Optional.ofNullable(apartment));
    }

    public AddressBuilder poBox(final Optional<String> poBox) {
        this.poBox = poBox;
        return this;
    }

    public AddressBuilder poBox(final String poBox) {
        return poBox(Optional.ofNullable(poBox));
    }

    public AddressBuilder phone(final Optional<String> phone) {
        this.phone = phone;
        return this;
    }

    public AddressBuilder phone(final String phone) {
        return phone(Optional.ofNullable(phone));
    }

    public AddressBuilder mobile(final Optional<String> mobile) {
        this.mobile = mobile;
        return this;
    }

    public AddressBuilder mobile(final String mobile) {
        return mobile(Optional.ofNullable(mobile));
    }

    public AddressBuilder email(final Optional<String> email) {
        this.email = email;
        return this;
    }

    public AddressBuilder email(final String email) {
        return email(Optional.ofNullable(email));
    }

    public AddressBuilder additionalAddressInfo(final Optional<String> additionalAddressInfo) {
        this.additionalAddressInfo = additionalAddressInfo;
        return this;
    }

    public AddressBuilder additionalAddressInfo(final String additionalAddressInfo) {
        return additionalAddressInfo(Optional.ofNullable(additionalAddressInfo));
    }
}
