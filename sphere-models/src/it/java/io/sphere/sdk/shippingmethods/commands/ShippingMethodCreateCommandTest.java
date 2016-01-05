package io.sphere.sdk.shippingmethods.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingMethodDraft;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.shippingmethods.ZoneRate;
import io.sphere.sdk.taxcategories.TaxCategoryDraft;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import io.sphere.sdk.zones.ZoneFixtures;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.money.CurrencyUnit;

import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.zones.ZoneFixtures.withZone;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingMethodCreateCommandTest extends IntegrationTest {

    public static final CountryCode COUNTRY_CODE = CountryCode.DG;

    @BeforeClass
    public static void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), COUNTRY_CODE);
    }

    @Test
    public void execution() throws Exception {
        final CurrencyUnit currencyUnit = USD;
        final TaxRate taxRate = TaxRate.of("x20", 0.20, true, COUNTRY_CODE);
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
}