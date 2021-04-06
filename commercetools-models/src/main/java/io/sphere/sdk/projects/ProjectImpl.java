package io.sphere.sdk.projects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.CartsConfiguration;
import io.sphere.sdk.carts.ShoppingListsConfiguration;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

final class ProjectImpl extends ProjectImplBase {

    @JsonCreator
    public ProjectImpl(List<CountryCode> countries, ZonedDateTime createdAt, List<String> currencies, @Nullable ExternalOAuth externalOAuth, String key, List<String> languages, MessagesConfiguration messages, String name, @Nullable ShippingRateInputType shippingRateInputType, @JsonDeserialize(using = TrialUntilDeserializer.class) @Nullable ZonedDateTime trialUntil, Long version, CartsConfiguration carts, ShoppingListsConfiguration shoppingLists, SearchIndexingConfiguration searchIndexing) {
        super(carts, countries, createdAt, currencies, externalOAuth, key, languages, messages, name, searchIndexing, shippingRateInputType, shoppingLists, trialUntil, version);
    }
}
