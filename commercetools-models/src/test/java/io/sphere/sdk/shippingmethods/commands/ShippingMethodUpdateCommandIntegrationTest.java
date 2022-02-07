package io.sphere.sdk.shippingmethods.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.commands.ReviewUpdateCommand;
import io.sphere.sdk.shippingmethods.ShippingMethod;
import io.sphere.sdk.shippingmethods.ShippingRate;
import io.sphere.sdk.shippingmethods.ZoneRate;
import io.sphere.sdk.shippingmethods.commands.updateactions.*;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodByIdGet;
import io.sphere.sdk.shippingmethods.queries.ShippingMethodQuery;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxCategoryFixtures;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import io.sphere.sdk.zones.ZoneFixtures;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.Optional;

import static io.sphere.sdk.reviews.ReviewFixtures.withUpdateableReview;
import static io.sphere.sdk.shippingmethods.ShippingMethodFixtures.withUpdateableShippingMethod;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.types.TypeFixtures.STRING_FIELD_NAME;
import static io.sphere.sdk.types.TypeFixtures.withUpdateableType;
import static io.sphere.sdk.zones.ZoneFixtures.withZone;
import static java.util.Collections.singletonMap;
import static org.assertj.core.api.Assertions.assertThat;

public class ShippingMethodUpdateCommandIntegrationTest extends IntegrationTest {
    @BeforeClass
    public static void deleteRemainingZone() throws Exception {
        ZoneFixtures.deleteZonesForCountries(client(), CountryCode.EA);
    }

    @Test
    public void updateByKey() throws Exception {
        final String key = randomKey();
        withUpdateableShippingMethod(client(), builder -> builder.key(key), shippingMethod -> {
            final String newDescription = randomString();
            assertThat(shippingMethod.getDescription()).isNotEqualTo(newDescription);
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.ofKey(key, shippingMethod.getVersion(), SetDescription.of(newDescription));
            final ShippingMethod updatedShippingMethod = client().executeBlocking(cmd);
            assertThat(updatedShippingMethod.getDescription()).isEqualTo(newDescription);
            return updatedShippingMethod;
        });
    }

    @Test
    public void updateByKeyWithLocalizedDescription() throws Exception {
        final String key = randomKey();
        withUpdateableShippingMethod(client(), builder -> builder.key(key), shippingMethod -> {
            final LocalizedString newLocalizedDescription = LocalizedString.ofEnglish(randomString());
            assertThat(shippingMethod.getLocalizedDescription()).isNotEqualTo(newLocalizedDescription);
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.ofKey(key, shippingMethod.getVersion(), SetLocalizedDescription.of(newLocalizedDescription));
            final ShippingMethod updatedShippingMethod = client().executeBlocking(cmd);
            assertThat(updatedShippingMethod.getLocalizedDescription()).isEqualTo(newLocalizedDescription);

            return updatedShippingMethod;
        });
    }

    @Test
    public void setDescription() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final String newDescription = randomString();
            assertThat(shippingMethod.getDescription()).isNotEqualTo(newDescription);
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, SetDescription.of(newDescription));
            final ShippingMethod updatedShippingMethod = client().executeBlocking(cmd);
            assertThat(updatedShippingMethod.getDescription()).isEqualTo(newDescription);
            return updatedShippingMethod;
        });
    }

    @Test
    public void setLocalizedDescription() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final LocalizedString newLocalizedDescription = LocalizedString.ofEnglish(randomString());
            assertThat(shippingMethod.getLocalizedDescription()).isNotEqualTo(newLocalizedDescription);
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, SetLocalizedDescription.of(newLocalizedDescription));
            final ShippingMethod updatedShippingMethod = client().executeBlocking(cmd);
            assertThat(updatedShippingMethod.getLocalizedDescription()).isEqualTo(newLocalizedDescription);

            return updatedShippingMethod;
        });
    }

    @Test
    public void setKey() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final String newKey = randomKey();
            assertThat(shippingMethod.getKey()).isNotEqualTo(newKey);
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, SetKey.of(newKey));
            final ShippingMethod updatedShippingMethod = client().executeBlocking(cmd);
            assertThat(updatedShippingMethod.getKey()).isEqualTo(newKey);
            return updatedShippingMethod;
        });
    }

    @Test
    public void setPredicate() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final String predicate = "1=1";
            final ShippingMethod updatedShippingMethod = client().executeBlocking(ShippingMethodUpdateCommand.of(shippingMethod, SetPredicate.of(predicate)));

            assertThat(updatedShippingMethod.getPredicate()).isEqualTo(predicate);

            return updatedShippingMethod;
        });
    }

    @Test
    public void changeName() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final String newName = randomString();
            assertThat(shippingMethod.getName()).isNotEqualTo(newName);
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, ChangeName.of(newName));
            final ShippingMethod updatedShippingMethod = client().executeBlocking(cmd);
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
            final ShippingMethod updatedShippingMethod = client().executeBlocking(cmd);
            assertThat(updatedShippingMethod.getTaxCategory().getId()).isEqualTo(newTaxCategory.getId());
            return updatedShippingMethod;
        });
    }

    @Test
    public void changeIsDefault() throws Exception {
        //only one can be default one, so clean up if there is any
        final Query<ShippingMethod> query = ShippingMethodQuery.of().withPredicates(QueryPredicate.of("isDefault = true"));
        final Optional<ShippingMethod> defaultShippingMethodOption = client().executeBlocking(query).head();
        defaultShippingMethodOption.ifPresent(sm -> client().executeBlocking(ShippingMethodUpdateCommand.of(sm, ChangeIsDefault.toFalse())));

        withUpdateableShippingMethod(client(), shippingMethod -> {
            assertThat(shippingMethod.isDefault()).isFalse();
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, ChangeIsDefault.toTrue());
            final ShippingMethod updatedShippingMethod = client().executeBlocking(cmd);
            assertThat(updatedShippingMethod.isDefault()).isTrue();

            final Long defaultShippingMethods = client().executeBlocking(ShippingMethodQuery.of().byIsDefault()).getCount();
            assertThat(defaultShippingMethods).isEqualTo(1);

            return client().executeBlocking(ShippingMethodUpdateCommand.of(updatedShippingMethod, ChangeIsDefault.toFalse()));
        });
    }

    @Test
    public void workingWithZones() throws Exception {
        withZone(client(), zone -> {
            withUpdateableShippingMethod(client(), shippingMethod -> {
                final long count = shippingMethod.getZones().stream().filter(z -> z.hasSameIdAs(zone)).count();
                assertThat(count).overridingErrorMessage("zone is not used yet").isEqualTo(0);

                //addZone
                final ShippingMethod shippingMethodWithZone = client().executeBlocking(ShippingMethodUpdateCommand.of(shippingMethod, AddZone.of(zone)));
                final ZoneRate zoneRate = shippingMethodWithZone.getZoneRates().stream()
                        .filter(rate -> rate.getZone().hasSameIdAs(zone))
                        .findFirst()
                        .get();
                assertThat(zoneRate.getShippingRates()).isEmpty();

                //addShippingRate
                final ShippingRate shippingRate = ShippingRate.of(MoneyImpl.of(30, USD),null, Collections.EMPTY_LIST);
                final ShippingMethod shippingMethodWithShippingRate =
                        client().executeBlocking(ShippingMethodUpdateCommand.of(shippingMethodWithZone, AddShippingRate.of(shippingRate, zone)));
                assertThat(shippingMethodWithShippingRate.getShippingRatesForZone(zone)).isEqualTo(asList(shippingRate));

                //check reference expansion
                final ShippingMethodByIdGet shippingMethodByIdGet = ShippingMethodByIdGet.of(shippingMethod)
                        .plusExpansionPaths(m -> m.zoneRates().zone());
                final ShippingMethod loadedShippingMethod = client().executeBlocking(shippingMethodByIdGet);
                assertThat(loadedShippingMethod.getZoneRates().get(0).getZone().getObj()).isNotNull();
                assertThat(loadedShippingMethod.getZones().get(0).getObj())
                        .overridingErrorMessage("the convenience method also has expanded references").isNotNull();

                //removeShippingRate
                final ShippingMethod shippingMethodWithoutShippingRate =
                        client().executeBlocking(ShippingMethodUpdateCommand.of(shippingMethodWithShippingRate, RemoveShippingRate.of(shippingRate, zone)));
                assertThat(shippingMethodWithoutShippingRate.getShippingRatesForZone(zone)).isEmpty();

                //removeZone
                final ShippingMethod shippingMethodWithoutZone =
                        client().executeBlocking(ShippingMethodUpdateCommand.of(shippingMethodWithoutShippingRate, RemoveZone.of(zone.toResourceIdentifier())));
                assertThat(shippingMethodWithoutZone.getZoneRates()).isEqualTo(shippingMethod.getZoneRates());

                return shippingMethodWithoutZone;
            });
        }, CountryCode.EA);
    }

    @Test
    public void setCustomType() {
        withUpdateableType(client(), type -> {
            withUpdateableShippingMethod(client(), shippingMethod -> {
                final SetCustomType updateAction = SetCustomType
                        .ofTypeIdAndObjects(type.getId(), singletonMap(STRING_FIELD_NAME, "foo"));
                final ShippingMethod updatedShippingMethod = client().executeBlocking(ShippingMethodUpdateCommand.of(shippingMethod, updateAction));

                assertThat(updatedShippingMethod.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("foo");

                final ShippingMethod updatedShippingMethod2 = client().executeBlocking(ShippingMethodUpdateCommand.of(updatedShippingMethod,
                       SetCustomField.ofObject(STRING_FIELD_NAME, "bar")));

                assertThat(updatedShippingMethod2.getCustom().getFieldAsString(STRING_FIELD_NAME)).isEqualTo("bar");

                return updatedShippingMethod2;
            });
            return type;
        });

    }

    @Test
    public void setLocalizedName() throws Exception {
        withUpdateableShippingMethod(client(), shippingMethod -> {
            final LocalizedString newLocalizedName = LocalizedString.ofEnglish(randomString());
            assertThat(shippingMethod.getLocalizedDescription()).isNotEqualTo(newLocalizedName);
            final ShippingMethodUpdateCommand cmd = ShippingMethodUpdateCommand.of(shippingMethod, SetLocalizedName.of(newLocalizedName));
            final ShippingMethod updatedShippingMethod = client().executeBlocking(cmd);
            assertThat(updatedShippingMethod.getLocalizedName()).isEqualTo(newLocalizedName);

            return updatedShippingMethod;
        });
    }
}
