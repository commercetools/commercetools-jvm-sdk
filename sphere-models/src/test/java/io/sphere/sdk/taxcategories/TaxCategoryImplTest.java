package io.sphere.sdk.taxcategories;

import io.sphere.sdk.models.DefaultModelBuilder;
import io.sphere.sdk.models.DefaultModelSubclassTest;

import java.util.Arrays;
import java.util.List;

import static com.neovisionaries.i18n.CountryCode.DE;
import static org.fest.assertions.Assertions.assertThat;

public class TaxCategoryImplTest extends DefaultModelSubclassTest<TaxCategory> {

    public static final String NAME_1 = "tax-category-1";
    public static final String NAME_2 = "tax-category-2";
    public static final String DESCRIPTION_1 = "description 1";
    public static final TaxRate GERMAN_DEFAULT_TAX_RATE = TaxRateBuilder.of("GERMAN default tax", 0.19, false, DE).build();
    public static final List<TaxRate> TAX_RATES = Arrays.asList(GERMAN_DEFAULT_TAX_RATE);


    @Override
    public void example1ToStringContainsSubclassAttributes(final String example1String) {
        assertThat(example1String).contains(NAME_1).contains("GERMAN default tax").contains(DESCRIPTION_1);
    }

    @Override
    protected DefaultModelBuilder<TaxCategory> newExample1Builder() {
        return TaxCategoryBuilder.of(NAME_1, TAX_RATES).description(DESCRIPTION_1);
    }

    @Override
    protected DefaultModelBuilder<TaxCategory> newExample2Builder() {
        return TaxCategoryBuilder.of(NAME_2, TAX_RATES);
    }

    @Override
    public void testSubclassGettersOfExample1(final TaxCategory model) {

    }
}
