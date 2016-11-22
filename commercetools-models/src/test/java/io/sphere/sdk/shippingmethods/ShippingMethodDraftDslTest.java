package io.sphere.sdk.shippingmethods;

import io.sphere.sdk.taxcategories.TaxCategory;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;

public class ShippingMethodDraftDslTest {
    @Test
    public void isDefault() {
        final ShippingMethodDraftDsl draft = ShippingMethodDraftDsl.of("name", TaxCategory.referenceOfId("tax-id"), Collections.emptyList(), true);
        assertThat(draft.isDefault()).isTrue();
        assertThat(draft.withDefault(false).isDefault()).isFalse();
        assertThat(draft.withIsDefault(false).isDefault()).isFalse();
    }
}