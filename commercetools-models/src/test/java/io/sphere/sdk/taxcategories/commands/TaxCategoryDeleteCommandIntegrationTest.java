package io.sphere.sdk.taxcategories.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.taxcategories.*;
import io.sphere.sdk.taxcategories.queries.TaxCategoryByIdGet;
import io.sphere.sdk.taxcategories.queries.TaxCategoryByKeyGet;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.List;

import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

public class TaxCategoryDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void deleteByVersioned() {
        final List<TaxRateDraft> taxRates = singletonList(TaxRateDraftBuilder.of("de19", 0.19, true, CountryCode.DE).build());
        final TaxCategoryDraft taxCategoryDraft =
                TaxCategoryDraftBuilder.of(randomKey(), taxRates, "tax category description")
                        .build();
        final TaxCategory taxCategory = client().executeBlocking(TaxCategoryCreateCommand.of(taxCategoryDraft));
        final String taxCategoryId = taxCategory.getId();
        client().executeBlocking(TaxCategoryDeleteCommand.of(taxCategory));
        final TaxCategory deletedTaxCategory = client().executeBlocking(TaxCategoryByIdGet.of(taxCategoryId));
        assertThat(deletedTaxCategory).isNull();
    }

    @Test
    public void deleteByKey() {
        final String key = randomKey();
        final List<TaxRateDraft> taxRates = singletonList(TaxRateDraftBuilder.of("de19", 0.19, true, CountryCode.DE).build());
        final TaxCategoryDraft taxCategoryDraft =
                TaxCategoryDraftBuilder.of(randomKey(), taxRates, "tax category description")
                        .key(key)
                        .build();
        final TaxCategory taxCategory = client().executeBlocking(TaxCategoryCreateCommand.of(taxCategoryDraft));
        client().executeBlocking(TaxCategoryDeleteCommand.ofKey(taxCategory.getKey(), taxCategory.getVersion()));
        final TaxCategory deletedTaxCategory = client().executeBlocking(TaxCategoryByKeyGet.of(key));
        assertThat(deletedTaxCategory).isNull();
    }
}
