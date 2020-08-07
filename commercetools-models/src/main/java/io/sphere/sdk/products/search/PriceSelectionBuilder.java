package io.sphere.sdk.products.search;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class PriceSelectionBuilder extends Base implements Builder<PriceSelectionDsl> {
    @Nonnull
    private String priceCurrency;
    @Nullable
    private String priceCountry;
    @Nullable
    private String priceCustomerGroup;
    @Nullable
    private String priceChannel;

    private PriceSelectionBuilder(@Nonnull final String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }

    public static PriceSelectionBuilder ofCurrencyCode(final String currencyCode) {
        return new PriceSelectionBuilder(currencyCode);
    }

    public static PriceSelectionBuilder of(@Nonnull final CurrencyUnit currencyUnit) {
        return ofCurrencyCode(currencyUnit.getCurrencyCode());
    }

    public static PriceSelectionBuilder of(@Nonnull final PriceSelection template) {
        return ofCurrencyCode(template.getPriceCurrency())
                .priceCountryCode(template.getPriceCountry())
                .priceCustomerGroupId(template.getPriceCustomerGroup())
                .priceChannelId(template.getPriceChannel());
    }

    public PriceSelectionBuilder priceCurrencyCode(@Nonnull final String priceCurrencyCode) {
        this.priceCurrency = priceCurrencyCode;
        return this;
    }

    public PriceSelectionBuilder priceCurrency(@Nonnull final CurrencyUnit currencyUnit) {
        return priceCurrencyCode(currencyUnit.getCurrencyCode());
    }
    
    public PriceSelectionBuilder priceCountryCode(@Nullable final String priceCountryCode) {
        this.priceCountry = priceCountryCode;
        return this;
    }

    public PriceSelectionBuilder priceCountry(@Nullable final CountryCode priceCountry) {
        this.priceCountry = Optional.ofNullable(priceCountry).map(c -> c.getAlpha2()).orElse(null);
        return this;
    }
    
    public PriceSelectionBuilder priceCustomerGroup(@Nullable final Referenceable<CustomerGroup> priceCustomerGroup) {
        this.priceCustomerGroup = Optional.ofNullable(priceCustomerGroup).map(group -> group.toReference().getId()).orElse(null);
        return this;
    }

    public PriceSelectionBuilder priceCustomerGroupId(@Nullable final String priceCustomerGroupId) {
        this.priceCustomerGroup = priceCustomerGroupId;
        return this;
    }
    
    public PriceSelectionBuilder priceChannelId(@Nullable final String priceChannelId) {
        this.priceChannel = priceChannelId;
        return this;
    }

    public PriceSelectionBuilder priceChannel(@Nullable final Referenceable<Channel> priceChannel) {
        this.priceChannel = Optional.ofNullable(priceChannel).map(channel -> channel.toReference().getId()).orElse(null);
        return this;
    }

    @Nullable
    public String getPriceChannel() {
        return priceChannel;
    }

    @Nullable
    public String getPriceCountry() {
        return priceCountry;
    }

    @Nonnull
    public String getPriceCurrency() {
        return priceCurrency;
    }

    @Nullable
    public String getPriceCustomerGroup() {
        return priceCustomerGroup;
    }

    @Override
    public PriceSelectionDsl build() {
        return new PriceSelectionDsl(priceCurrency, priceCountry, priceCustomerGroup, priceChannel);
    }
}
