package io.sphere.sdk.products;

import java.util.Optional;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Money;
import io.sphere.sdk.models.Reference;

public class Price {
    private final Money value;
    private final Optional<CountryCode> country;
    private final Optional<Reference<CustomerGroup>> customerGroup;
    private final Optional<Reference<Channel>> channel;

    public Price(final Money value, final Optional<CountryCode> country, final Optional<Reference<CustomerGroup>> customerGroup, final Optional<Reference<Channel>> channel) {
        this.value = value;
        this.country = country;
        this.customerGroup = customerGroup;
        this.channel = channel;
    }

    public Money getValue() {
        return value;
    }

    public Optional<CountryCode> getCountry() {
        return country;
    }

    public Optional<Reference<CustomerGroup>> getCustomerGroup() {
        return customerGroup;
    }

    public Optional<Reference<Channel>> getChannel() {
        return channel;
    }
}
