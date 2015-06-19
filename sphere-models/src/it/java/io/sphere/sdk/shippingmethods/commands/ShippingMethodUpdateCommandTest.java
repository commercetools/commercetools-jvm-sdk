package io.sphere.sdk.shippingmethods.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.shippingmethods.ZoneRate;
import io.sphere.sdk.shippingmethods.commands.updateactions.*;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodByIdFetch;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQuery;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import io.sphere.sdk.zones.ZoneFixtures;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withUpdateableShippingMethod;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.zones.ZoneFixtures.withZone;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingMethodUpdateCommandTest extends IntegrationTest {
    @BeforeClass
    public static void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), CountryCode.EA);
    }

    @Test
    public void setDescription() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final String newDescription = randomString();
            assertThat(shippingMethod.getDescription()).isNotEqualTo(Optional.of(newDescription));
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, SetDescription.of(newDescription));
            final ShippingMethod updatedShippingMethod = execute(cmd);
            assertThat(updatedShippingMethod.getDescription().get()).isEqualTo(newDescription);
            return updatedShippingMethod;
        });
    }

    @Test
    public void changeName() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final String newName = randomString();
            assertThat(shippingMethod.getName()).isNotEqualTo(newName);
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, ChangeName.of(newName));
            final ShippingMethod updatedShippingMethod = execute(cmd);
            assertThat(updatedShippingMethod.getName()).isEqualTo(newName);
            return updatedShippingMethod;
        });
    }

    @Test
    public void changeTaxCategory() throws Exception {
        final TaxCategory newTaxCategory = TaxCategoryFixtures.defaultTaxCategory(client());
        withUpdateableShippingMethod(client(), shippingMethod -> {
            assertThat(shippingMethod.getTaxCategory().getId()).isNotEqualTo(newTaxCategory.getId());
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, ChangeTaxCategory.of(newTaxCategory));
            final ShippingMethod updatedShippingMethod = execute(cmd);
            assertThat(updatedShippingMethod.getTaxCategory().getId()).isEqualTo(newTaxCategory.getId());
            return updatedShippingMethod;
        });
    }

    @Test
    public void changeIsDefault() throws Exception {
        //only one can be default one, so clean up if there is any
        final Query<ShippingMethod> query = ShippingMethodQuery.of().withPredicate(QueryPredicate.of("isDefault = true"));
        final Optional<ShippingMethod> defaultShippingMethodOption = execute(query).head();
        defaultShippingMethodOption.ifPresent(sm -> execute(ShippingMethodUpdateCommand.of(sm, ChangeIsDefault.toFalse())));

        withUpdateableShippingMethod(client(), shippingMethod -> {
            assertThat(shippingMethod.isDefault()).isFalse();
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, ChangeIsDefault.toTrue());
            final ShippingMethod updatedShippingMethod = execute(cmd);
            assertThat(updatedShippingMethod.isDefault()).isTrue();

            final int defaultShippingMethods = execute(ShippingMethodQuery.of().byIsDefault()).size();
            assertThat(defaultShippingMethods).isEqualTo(1);

            return execute(ShippingMethodUpdateCommand.of(updatedShippingMethod, ChangeIsDefault.toFalse()));
        });
    }

    @Test
    public void workingWithZones() throws Exception {
        withZone(client(), zone -> {
            withUpdateableShippingMethod(client(), shippingMethod -> {
                final long count = shippingMethod.getZones().stream().filter(z -> z.hasSameIdAs(zone)).count();
                assertThat(count).overridingErrorMessage("zone is not used yet").isEqualTo(0);

                //addZone
                final ShippingMethod shippingMethodWithZone = execute(ShippingMethodUpdateCommand.of(shippingMethod, AddZone.of(zone)));
                final ZoneRate zoneRate = shippingMethodWithZone.getZoneRates().stream()
                        .filter(rate -> rate.getZone().hasSameIdAs(zone))
                        .findFirst()
                        .get();
                assertThat(zoneRate.getShippingRates()).isEmpty();

                //addShippingRate
                final ShippingRate shippingRate = ShippingRate.of(MoneyImpl.of(30, USD));
                final ShippingMethod shippingMethodWithShippingRate =
                        execute(ShippingMethodUpdateCommand.of(shippingMethodWithZone, AddShippingRate.of(shippingRate, zone)));
                assertThat(shippingMethodWithShippingRate.getShippingRatesForZone(zone)).isEqualTo(asList(shippingRate));

                //check reference expansion
                final ShippingMethod loadedShippingMethod = execute(ShippingMethodByIdFetch.of(shippingMethod)
                                .plusExpansionPaths(m -> m.zoneRates().zone())
                ).get();
                assertThat(loadedShippingMethod.getZoneRates().get(0).getZone().getObj()).isPresent();
                assertThat(loadedShippingMethod.getZones().get(0).getObj())
                        .overridingErrorMessage("the convenience method also has expanded references").isPresent();

                //removeShippingRate
                final ShippingMethod shippingMethodWithoutShippingRate =
                        execute(ShippingMethodUpdateCommand.of(shippingMethodWithShippingRate, RemoveShippingRate.of(shippingRate, zone)));
                assertThat(shippingMethodWithoutShippingRate.getShippingRatesForZone(zone)).isEmpty();

                //removeZone
                final ShippingMethod shippingMethodWithoutZone =
                        execute(ShippingMethodUpdateCommand.of(shippingMethodWithoutShippingRate, RemoveZone.of(zone)));
                assertThat(shippingMethodWithoutZone.getZoneRates()).isEqualTo(shippingMethod.getZoneRates());

                return shippingMethodWithoutZone;
            });
        }, CountryCode.EA);
    }
}