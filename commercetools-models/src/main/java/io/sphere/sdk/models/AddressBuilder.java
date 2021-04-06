package io.sphere.sdk.models;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Builds {@link Address} instances using the Builder pattern (a stateful object).
 *
 * <p>Create an entire new address:</p>
 *
 * {@include.example example.AddressExample#byCountryCreation()}
 *
 * <p>Create an address based on an existing address:</p>
 *
 * {@include.example example.AddressExample#buildByExistingAddress()}
 */
public final class AddressBuilder extends Base implements Builder<Address> {
    CountryCode country;
    @Nullable
    String id;
    @Nullable
    String key;
    @Nullable
    String title;
    @Nullable
    String salutation;
    @Nullable
    String firstName;
    @Nullable
    String lastName;
    @Nullable
    String streetName;
    @Nullable
    String streetNumber;
    @Nullable
    String additionalStreetInfo;
    @Nullable
    String postalCode;
    @Nullable
    String city;
    @Nullable
    String region;
    @Nullable
    String state;
    @Nullable
    String company;
    @Nullable
    String department;
    @Nullable
    String building;
    @Nullable
    String apartment;
    @Nullable
    String poBox;
    @Nullable
    String phone;
    @Nullable
    String mobile;
    @Nullable
    String email;
    @Nullable
    String additionalAddressInfo;
    @Nullable
    String fax;
    @Nullable
    String externalId;
    @Nullable
    CustomFields customFields;
    @Nullable
    CustomFieldsDraft customFieldsDraft;

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
        builder.fax = address.fax;
        builder.externalId = address.externalId;
        builder.key = address.key;
        builder.customFields = address.customFields;
        builder.customFieldsDraft = address.customFieldsDraft;
        return builder;
    }

    public static AddressBuilder of(final CountryCode country) {
        Objects.requireNonNull(country);
        return new AddressBuilder(country);
    }

    public Address build() {
        return new Address(this);
    }

    public AddressBuilder country(final CountryCode country) {
        Objects.requireNonNull(country);
        this.country = country;
        return this;
    }

    public AddressBuilder id(@Nullable final String id) {
        this.id = id;
        return this;
    }

    public AddressBuilder key(@Nullable final String key) {
        this.key = key;
        return this;
    }

    public AddressBuilder title(@Nullable final String title) {
        this.title = title;
        return this;
    }

    public AddressBuilder salutation(@Nullable final String salutation) {
        this.salutation = salutation;
        return this;
    }

    public AddressBuilder firstName(@Nullable final String firstName) {
        this.firstName = firstName;
        return this;
    }

    public AddressBuilder lastName(@Nullable final String lastName) {
        this.lastName = lastName;
        return this;
    }

    public AddressBuilder streetName(@Nullable final String streetName) {
        this.streetName = streetName;
        return this;
    }

    public AddressBuilder streetNumber(@Nullable final String streetNumber) {
        this.streetNumber = streetNumber;
        return this;
    }

    public AddressBuilder additionalStreetInfo(@Nullable final String additionalStreetInfo) {
        this.additionalStreetInfo = additionalStreetInfo;
        return this;
    }

    public AddressBuilder postalCode(@Nullable final String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public AddressBuilder city(@Nullable final String city) {
        this.city = city;
        return this;
    }

    public AddressBuilder region(@Nullable final String region) {
        this.region = region;
        return this;
    }

    public AddressBuilder state(@Nullable final String state) {
        this.state = state;
        return this;
    }

    public AddressBuilder company(@Nullable final String company) {
        this.company = company;
        return this;
    }

    public AddressBuilder department(@Nullable final String department) {
        this.department = department;
        return this;
    }

    public AddressBuilder building(@Nullable final String building) {
        this.building = building;
        return this;
    }

    public AddressBuilder apartment(@Nullable final String apartment) {
        this.apartment = apartment;
        return this;
    }

    public AddressBuilder poBox(@Nullable final String poBox) {
        this.poBox = poBox;
        return this;
    }

    public AddressBuilder phone(@Nullable final String phone) {
        this.phone = phone;
        return this;
    }

    public AddressBuilder mobile(@Nullable final String mobile) {
        this.mobile = mobile;
        return this;
    }

    public AddressBuilder email(@Nullable final String email) {
        this.email = email;
        return this;
    }

    public AddressBuilder additionalAddressInfo(@Nullable final String additionalAddressInfo) {
        this.additionalAddressInfo = additionalAddressInfo;
        return this;
    }

    public AddressBuilder fax(@Nullable final String fax) {
        this.fax = fax;
        return this;
    }

    public AddressBuilder externalId(@Nullable final String externalId) {
        this.externalId = externalId;
        return this;
    }

    public AddressBuilder customFields(@Nullable final CustomFields customFields) {
        this.customFields = customFields;
        this.customFieldsDraft = null;
        return this;
    }

    public AddressBuilder customFields(@Nullable final CustomFieldsDraft customFieldsDraft) {
        this.customFieldsDraft = customFieldsDraft;
        this.customFields = null;
        return this;
    }
}
