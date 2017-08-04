package io.sphere.sdk.taxcategories.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;
import io.sphere.sdk.taxcategories.TaxRateDraft;
import io.sphere.sdk.taxcategories.TaxRateDraftBuilder;
import io.sphere.sdk.taxcategories.commands.updateactions.*;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.taxcategories.TaxCategoryFixtures.withUpdateableTaxCategory;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxCategoryUpdateCommandIntegrationTest extends IntegrationTest {

    @Test
    public void setKey() {
        withUpdateableTaxCategory(client(), taxCategory -> {
            final String newKey = randomKey();
            assertThat(taxCategory.getKey()).isNotEqualTo(newKey);

            final TaxCategory updatedTaxCategory = client().executeBlocking(TaxCategoryUpdateCommand.of(taxCategory, SetKey.of(newKey)));
            assertThat(updatedTaxCategory.getKey()).isEqualTo(newKey);
            return updatedTaxCategory;
        });
    }

    @Test
    public void changeName() {
        withUpdateableTaxCategory(client(), taxCategory -> {
            final String newName = randomKey();
            final TaxCategory updatedTaxCategory = client().executeBlocking(TaxCategoryUpdateCommand.of(taxCategory, ChangeName.of(newName)));
            assertThat(updatedTaxCategory.getName()).isEqualTo(newName);
            return updatedTaxCategory;
        });
    }

    @Test
    public void setDescription() {
        withUpdateableTaxCategory(client(), taxCategory -> {
            final String newDescription = randomKey();
            final TaxCategory updatedTaxCategory = client().executeBlocking(TaxCategoryUpdateCommand.of(taxCategory, SetDescription.of(newDescription)));
            assertThat(updatedTaxCategory.getDescription()).isEqualTo(newDescription);
            return updatedTaxCategory;
        });
    }

    @Test
    public void addTaxRate() {
        withUpdateableTaxCategory(client(), taxCategory -> {
            //add tax rate
            final String name = "ag7";
            final CountryCode countryCode = CountryCode.AG;
            final TaxRateDraft de7 = TaxRateDraftBuilder.of(name, 0.07, true, countryCode).build();
            final TaxCategory taxCategoryWithTaRate = client().executeBlocking(TaxCategoryUpdateCommand.of(taxCategory, AddTaxRate.of(de7)));
            final TaxRate actual = taxCategoryWithTaRate.getTaxRates().stream().filter(rate -> name.equals(rate.getName())).findFirst().get();
            assertThat(actual.getCountry()).isEqualTo(countryCode);
            assertThat(actual.getAmount()).isEqualTo(0.07);
            assertThat(actual.getId())
                    .overridingErrorMessage("the tax rate fetched from API has an id")
                    .isNotNull();
            assertThat(actual.getName()).isEqualTo(de7.getName());


            //remove tax rate
            final TaxCategory updatedTaxCategory = client().executeBlocking(TaxCategoryUpdateCommand.of(taxCategoryWithTaRate, RemoveTaxRate.of(actual.getId())));
            assertThat(updatedTaxCategory.getTaxRates()).matches(rates -> !rates.stream().anyMatch(rate -> name.equals(rate.getName())));

            return updatedTaxCategory;
        });
    }

    @Test
    public void replaceTaxRate() {
        withUpdateableTaxCategory(client(), taxCategory -> {
            final TaxRate old = taxCategory.getTaxRates().get(0);
            final double newAmount = old.getAmount() * 2;
            final TaxRateDraft newTaxRate = TaxRateDraftBuilder.of(old).amount(newAmount).build();

            final TaxCategory updatedTaxCategory = client().executeBlocking(TaxCategoryUpdateCommand.of(taxCategory, ReplaceTaxRate.of(old.getId(), newTaxRate)));
            final TaxRate actual = updatedTaxCategory.getTaxRates().get(0);
            assertThat(actual.getCountry()).isEqualTo(old.getCountry());
            assertThat(actual.getAmount()).isEqualTo(newAmount);
            assertThat(actual.getName()).isEqualTo(old.getName());
            return updatedTaxCategory;
        });
    }
}