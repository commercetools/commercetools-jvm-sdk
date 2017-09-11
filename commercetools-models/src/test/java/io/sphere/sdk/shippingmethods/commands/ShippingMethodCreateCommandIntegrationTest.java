package io.sphere.sdk.shippingmethods.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.cartdiscounts.CartPredicate;
import io.sphere.sdk.shippingmethods.*;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQuery;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;
import io.sphere.sdk.taxcategories.TaxRateDraft;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.test.JsonNodeReferenceResolver;
import io.sphere.sdk.utils.MoneyImpl;
import io.sphere.sdk.zones.ZoneFixtures;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.money.CurrencyUnit;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withShippingMethod;
import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.zones.ZoneFixtures.withZone;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingMethodCreateCommandIntegrationTest extends IntegrationTest {

    public static final CountryCode COUNTRY_CODE = CountryCode.DG;

    @Before
    public void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), COUNTRY_CODE);
    }

    @BeforeClass
    public static void cleanShippingMethod() throws Exception {
        client().executeBlocking(ShippingMethodQuery.of().withPredicates(m -> m.name().is("demo shipping method")))
                .getResults()
                .forEach(method -> client().executeBlocking(ShippingMethodDeleteCommand.of(method)));
    }

    @Test
    public void execution() throws Exception {
        final CurrencyUnit currencyUnit = USD;
        final TaxRateDraft taxRate = TaxRateDraft.of("x20", 0.20, true, COUNTRY_CODE);
        withZone(client(), zone -> {
            withTaxCategory(client(), TaxCategoryDraft.of("taxcat", asList(taxRate)), taxCategory -> {
                final ZoneRate zoneRate = ZoneRate.of(zone, asList(ShippingRate.of(MoneyImpl.of(30, currencyUnit))));
                final ShippingMethodDraft draft =
                        ShippingMethodDraft.of("standard shipping", "description", taxCategory, asList(zoneRate));
                final ShippingMethod shippingMethod = client().executeBlocking(ShippingMethodCreateCommand.of(draft));
                //deletion
                client().executeBlocking(ShippingMethodDeleteCommand.of(shippingMethod));
            });
        }, COUNTRY_CODE);
    }

    @Test
    public void createShippingMethodWithPredicate() throws Exception {
        final CurrencyUnit currencyUnit = USD;
        final TaxRateDraft taxRate = TaxRateDraft.of("x20", 0.20, true, COUNTRY_CODE);
        withZone(client(), zone -> {
            withTaxCategory(client(), TaxCategoryDraft.of("taxcat", asList(taxRate)), taxCategory -> {
                final ZoneRate zoneRate = ZoneRate.of(zone, asList(ShippingRate.of(MoneyImpl.of(30, currencyUnit))));
                final ShippingMethodDraft draft =
                        ShippingMethodDraftBuilder.of("standard shipping", "description", taxCategory.toReference(), asList(zoneRate), false)
                                .predicate(CartPredicate.of("customer.email = \"john@example.com\""))
                                .build();
                final ShippingMethod shippingMethod = client().executeBlocking(ShippingMethodCreateCommand.of(draft));
                //deletion
                client().executeBlocking(ShippingMethodDeleteCommand.of(shippingMethod));
            });
        }, COUNTRY_CODE);
    }

    @Test
    public void createByJson() {
        withTaxCategory(client(), taxCategory -> {
            withZone(client(), zone -> {
                final JsonNodeReferenceResolver referenceResolver = new JsonNodeReferenceResolver();
                referenceResolver.addResourceByKey("standard-tax", taxCategory);
                referenceResolver.addResourceByKey("zone-id", zone);
                final ShippingMethodDraft draft = draftFromJsonResource("drafts-tests/shippingMethod.json", ShippingMethodDraft.class, referenceResolver);
                withShippingMethod(client(), draft, shippingMethod -> {
                    assertThat(shippingMethod.getName()).isEqualTo("demo shipping method");
                    assertThat(shippingMethod.getTaxCategory()).isEqualTo(taxCategory.toReference());
                    final ZoneRate zoneRate = shippingMethod.getZoneRates().get(0);
                    assertThat(zoneRate.getZone()).isEqualTo(zone.toReference());
                    final ShippingRate shippingRate = zoneRate.getShippingRates().get(0);
                    assertThat(shippingRate.getPrice()).isEqualTo(EURO_20);
                });
            }, COUNTRY_CODE);
        });
    }
}