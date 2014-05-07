package io.sphere.sdk.models;

import com.google.common.base.Optional;
import com.neovisionaries.i18n.CountryCode;
import net.jcip.annotations.Immutable;

/**
 * Represents a postal address.
 */
@Immutable
public class Address {

    private final CountryCode country;
    private final Optional<String> id;
    private final Optional<String> title;
    private final Optional<String> salutation;
    private final Optional<String> firstName;
    private final Optional<String> lastName;
    private final Optional<String> streetName;
    private final Optional<String> streetNumber;
    private final Optional<String> additionalStreetInfo;
    private final Optional<String> postalCode;
    private final Optional<String> city;
    private final Optional<String> region;
    private final Optional<String> state;
    private final Optional<String> company;
    private final Optional<String> department;
    private final Optional<String> building;
    private final Optional<String> apartment;
    private final Optional<String> poBox;
    private final Optional<String> phone;
    private final Optional<String> mobile;
    private final Optional<String> email;
    private final Optional<String> additionalAddressInfo;

    private Address(final Builder builder) {
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

    /**
     * Builds Address instances using the Builder pattern (a stateful object)
     */
    public static class Builder {
        private CountryCode country;
        private Optional<String> id = Optional.absent();
        private Optional<String> title = Optional.absent();
        private Optional<String> salutation = Optional.absent();
        private Optional<String> firstName = Optional.absent();
        private Optional<String> lastName = Optional.absent();
        private Optional<String> streetName = Optional.absent();
        private Optional<String> streetNumber = Optional.absent();
        private Optional<String> additionalStreetInfo = Optional.absent();
        private Optional<String> postalCode = Optional.absent();
        private Optional<String> city = Optional.absent();
        private Optional<String> region = Optional.absent();
        private Optional<String> state = Optional.absent();
        private Optional<String> company = Optional.absent();
        private Optional<String> department = Optional.absent();
        private Optional<String> building = Optional.absent();
        private Optional<String> apartment = Optional.absent();
        private Optional<String> poBox = Optional.absent();
        private Optional<String> phone = Optional.absent();
        private Optional<String> mobile = Optional.absent();
        private Optional<String> email = Optional.absent();
        private Optional<String> additionalAddressInfo = Optional.absent();


        private Builder(final CountryCode country) {
            this.country = country;
        }

        public static Builder byAddress(final Address address) {
            final Builder builder = new Builder(address.country);
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
        
        public static Builder create(final CountryCode country) {
            return new Builder(country);
        }
        
        public Address build() {
            return new Address(this);
        }

        public Builder country(final CountryCode country) {
            this.country = country;
            return this;
        }

        public Builder id(final Optional<String> id) {
            this.id = id;
            return this;
        }

        public Builder id(final String id) {
            return id(Optional.fromNullable(id));
        }

        public Builder title(final Optional<String> title) {
            this.title = title;
            return this;
        }

        public Builder title(final String title) {
            return title(Optional.fromNullable(title));
        }

        public Builder salutation(final Optional<String> salutation) {
            this.salutation = salutation;
            return this;
        }

        public Builder salutation(final String salutation) {
            return salutation(Optional.fromNullable(salutation));
        }

        public Builder firstName(final Optional<String> firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder firstName(final String firstName) {
            return firstName(Optional.fromNullable(firstName));
        }

        public Builder lastName(final Optional<String> lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder lastName(final String lastName) {
            return lastName(Optional.fromNullable(lastName));
        }

        public Builder streetName(final Optional<String> streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder streetName(final String streetName) {
            return streetName(Optional.fromNullable(streetName));
        }

        public Builder streetNumber(final Optional<String> streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public Builder streetNumber(final String streetNumber) {
            return streetNumber(Optional.fromNullable(streetNumber));
        }

        public Builder additionalStreetInfo(final Optional<String> additionalStreetInfo) {
            this.additionalStreetInfo = additionalStreetInfo;
            return this;
        }

        public Builder additionalStreetInfo(final String additionalStreetInfo) {
            return additionalStreetInfo(Optional.fromNullable(additionalStreetInfo));
        }

        public Builder postalCode(final Optional<String> postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder postalCode(final String postalCode) {
            return postalCode(Optional.fromNullable(postalCode));
        }

        public Builder city(final Optional<String> city) {
            this.city = city;
            return this;
        }

        public Builder city(final String city) {
            return city(Optional.fromNullable(city));
        }

        public Builder region(final Optional<String> region) {
            this.region = region;
            return this;
        }

        public Builder region(final String region) {
            return region(Optional.fromNullable(region));
        }

        public Builder state(final Optional<String> state) {
            this.state = state;
            return this;
        }

        public Builder state(final String state) {
            return state(Optional.fromNullable(state));
        }

        public Builder company(final Optional<String> company) {
            this.company = company;
            return this;
        }

        public Builder company(final String company) {
            return company(Optional.fromNullable(company));
        }

        public Builder department(final Optional<String> department) {
            this.department = department;
            return this;
        }

        public Builder department(final String department) {
            return department(Optional.fromNullable(department));
        }

        public Builder building(final Optional<String> building) {
            this.building = building;
            return this;
        }
        public Builder building(final String building) {
            return building(Optional.fromNullable(building));
        }

        public Builder apartment(final Optional<String> apartment) {
            this.apartment = apartment;
            return this;
        }

        public Builder apartment(final String apartment) {
            return apartment(Optional.fromNullable(apartment));
        }

        public Builder poBox(final Optional<String> poBox) {
            this.poBox = poBox;
            return this;
        }

        public Builder poBox(final String poBox) {
            return poBox(Optional.fromNullable(poBox));
        }

        public Builder phone(final Optional<String> phone) {
            this.phone = phone;
            return this;
        }

        public Builder phone(final String phone) {
            return phone(Optional.fromNullable(phone));
        }

        public Builder mobile(final Optional<String> mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder mobile(final String mobile) {
            return mobile(Optional.fromNullable(mobile));
        }

        public Builder email(final Optional<String> email) {
            this.email = email;
            return this;
        }

        public Builder email(final String email) {
            return email(Optional.fromNullable(email));
        }

        public Builder additionalAddressInfo(final Optional<String> additionalAddressInfo) {
            this.additionalAddressInfo = additionalAddressInfo;
            return this;
        }
        public Builder additionalAddressInfo(final String additionalAddressInfo) {
            return additionalAddressInfo(Optional.fromNullable(additionalAddressInfo));
        }
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
        return Builder.byAddress(this).country(country).build();
    }

    public Address withId(final Optional<String> id) {
        return Builder.byAddress(this).id(id).build();
    }

    public Address withTitle(final Optional<String> title) {
        return Builder.byAddress(this).title(title).build();
    }

    public Address withSalutation(final Optional<String> salutation) {
        return Builder.byAddress(this).salutation(salutation).build();
    }

    public Address withFirstName(final Optional<String> firstName) {
        return Builder.byAddress(this).firstName(firstName).build();
    }

    public Address withLastName(final Optional<String> lastName) {
        return Builder.byAddress(this).lastName(lastName).build();
    }

    public Address withStreetName(final Optional<String> streetName) {
        return Builder.byAddress(this).streetName(streetName).build();
    }

    public Address withStreetNumber(final Optional<String> streetNumber) {
        return Builder.byAddress(this).streetNumber(streetNumber).build();
    }

    public Address withAdditionalStreetInfo(final Optional<String> additionalStreetInfo) {
        return Builder.byAddress(this).additionalStreetInfo(additionalStreetInfo).build();
    }

    public Address withPostalCode(final Optional<String> postalCode) {
        return Builder.byAddress(this).postalCode(postalCode).build();
    }

    public Address withCity(final Optional<String> city) {
        return Builder.byAddress(this).city(city).build();
    }

    public Address withRegion(final Optional<String> region) {
        return Builder.byAddress(this).region(region).build();
    }

    public Address withState(final Optional<String> state) {
        return Builder.byAddress(this).state(state).build();
    }

    public Address withCompany(final Optional<String> company) {
        return Builder.byAddress(this).company(company).build();
    }

    public Address withDepartment(final Optional<String> department) {
        return Builder.byAddress(this).department(department).build();
}

    public Address withBuilding(final Optional<String> building) {
        return Builder.byAddress(this).building(building).build();
    }

    public Address withApartment(final Optional<String> apartment) {
        return Builder.byAddress(this).apartment(apartment).build();
    }

    public Address withPoBox(final Optional<String> poBox) {
        return Builder.byAddress(this).poBox(poBox).build();
    }

    public Address withPhone(final Optional<String> phone) {
        return Builder.byAddress(this).phone(phone).build();
    }

    public Address withMobile(final Optional<String> mobile) {
        return Builder.byAddress(this).mobile(mobile).build();
    }

    public Address withEmail(final Optional<String> email) {
        return Builder.byAddress(this).email(email).build();
    }

    public Address withAdditionalAddressInfo(final Optional<String> additionalAddressInfo) {
        return Builder.byAddress(this).additionalAddressInfo(additionalAddressInfo).build();
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
