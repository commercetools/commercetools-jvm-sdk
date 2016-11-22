package io.sphere.sdk.shippingmethods;

import io.sphere.sdk.taxcategories.TaxCategory;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class ShippingMethodDraftBuilderTest {
    @Test
    public void isDefault() {
        final ShippingMethodDraftBuilder builder = ShippingMethodDraftBuilder.of("name", TaxCategory.referenceOfId("tax-id"), Collections.emptyList(), true);
        assertThat(builder.build().isDefault()).isTrue();
        assertThat(builder._default(false).build().isDefault()).isFalse();
        assertThat(builder.isDefault(true).build().isDefault()).isTrue();
    }
}