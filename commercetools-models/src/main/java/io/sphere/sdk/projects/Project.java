package io.sphere.sdk.projects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.annotations.HasUpdateAction;
import io.sphere.sdk.annotations.PropertySpec;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.carts.CartsConfiguration;
import io.sphere.sdk.carts.ShoppingListsConfiguration;
import io.sphere.sdk.models.CreationTimestamped;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;


@ResourceValue(abstractResourceClass = true)
@JsonDeserialize(as = ProjectImpl.class)
public interface Project extends CreationTimestamped, WithKey {
    /**
     * The unique key of the project.
     *
     * @return key
     */
    String getKey();

    /**
     * The current version of the project.
     * @return version
     */
    Long getVersion();

    /**
     * The name of the project.
     * @return name
     */
    @HasUpdateAction
    String getName();

    /**
     * Enabled countries.
     * @return countries
     */
    @HasUpdateAction
    List<CountryCode> getCountries();

    /**
     * A two-digit language code as per ISO 3166-1 alpha-2 String.
     * @see #getLanguageLocales()
     * @return language
     */
    @HasUpdateAction
    List<String> getLanguages();

    /**
     * The languages as list of {@link Locale}s of this project.
     * @see #getLanguages()
     * @return languages
     */
    default List<Locale> getLanguageLocales() {
        return getLanguages().stream()
                .map(Locale::forLanguageTag)
                .collect(toList());
    }

    @Nullable
    ZonedDateTime getTrialUntil();

    @Override
    ZonedDateTime getCreatedAt();

    /**
     * A three-digit currency code as per ISO 4217.
     * @see #getCurrencyUnits()
     * @return currency codes
     */
    @HasUpdateAction
    List<String> getCurrencies();

    /**
     * Currencies assigned to this project as {@link CurrencyUnit}.
     * @see #getCurrencies()
     * @return currencies
     */
    default List<CurrencyUnit> getCurrencyUnits() {
        return getCurrencies().stream()
                .map(Monetary::getCurrency)
                .collect(toList());
    }
    @HasUpdateAction
    @HasUpdateAction(value = "changeMessagesEnabled", fields = {@PropertySpec(name = "messagesEnabled", type = Boolean.class)})
    @HasUpdateAction(value = "changeMessagesConfiguration", fields = {@PropertySpec(name = "messagesConfiguration", type = MessagesConfigurationDraft.class)})
    MessagesConfiguration getMessages();

    /**
     * The shippingRateInput is used as an input to select a shipping rate price tier at the project level
     *@return shippingRateInput
     */
    @Nullable
    ShippingRateInputType getShippingRateInputType();

    /**
     * If this property is set, it allows to use OAuth2 Bearer tokens issued by another service that provides a RFC 7662-compliant endpoint.
     * @return external oAuth endpoint
     */
    @Nullable
    @HasUpdateAction
    ExternalOAuth getExternalOAuth();

    @HasUpdateAction(value = "changeCountryTaxRateFallbackEnabled", fields = {@PropertySpec(name = "countryTaxRateFallbackEnabled", type = Boolean.class)})
    @HasUpdateAction(value = "changeCartsConfiguration", fields = {@PropertySpec(name = "cartsConfiguration", type = CartsConfiguration.class)})
    CartsConfiguration getCarts();

    @HasUpdateAction(value = "changeShoppingListsConfiguration", fields = {@PropertySpec(name = "shoppingListsConfiguration", type = ShoppingListsConfiguration.class)})
    ShoppingListsConfiguration getShoppingLists();

    @Nullable
    @HasUpdateAction(value = "changeProductSearchIndexingEnabled", fields = {@PropertySpec(name = "enabled", type = Boolean.class)})
    SearchIndexingConfiguration getSearchIndexing();

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<Project> typeReference() {
        return new TypeReference<Project>(){
            @Override
            public String toString() {
                return "TypeReference<Project>";
            }
        };
    }
}
