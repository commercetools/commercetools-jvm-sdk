package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.utils.MoneyImpl;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@JsonDeserialize(as = PriceDraftDsl.class)
public interface PriceDraft extends CustomDraft {
    MonetaryAmount getValue();
    @Nullable
    CountryCode getCountry();
    @Nullable
    Reference<CustomerGroup> getCustomerGroup();

    @Nullable
    Reference<Channel> getChannel();

    @Nullable
    ZonedDateTime getValidFrom();
    @Nullable
    ZonedDateTime getValidUntil();

    CustomFieldsDraft getCustom();

    @Nullable
    List<PriceTier> getTiers();

    @JsonIgnore
    static PriceDraftDsl of(final MonetaryAmount money) {
        return PriceDraftBuilder.of(money).build();
    }

    @JsonIgnore
    static PriceDraftDsl of(final BigDecimal amount, final CurrencyUnit currencyUnit) {
        return of(MoneyImpl.of(amount, currencyUnit));
    }

    static PriceDraftDsl of(final Price template) {
        return PriceDraftDsl.of(template);
    }
}
