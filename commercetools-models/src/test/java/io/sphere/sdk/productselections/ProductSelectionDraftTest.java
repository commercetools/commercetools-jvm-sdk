package io.sphere.sdk.productselections;


import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.LocalizedString;
import org.junit.Test;

import java.io.IOException;

import static io.sphere.sdk.test.SphereTestUtils.en;
import static io.sphere.sdk.test.SphereTestUtils.randomKey;
import static org.assertj.core.api.Assertions.assertThat;


public class ProductSelectionDraftTest {

    @Test
    public void custom_withAnyProductSelectionDraft_ShouldSetProductSelectionDraftOnBuilder() {
        final LocalizedString name  = en("Summer");

        final ProductSelectionDraftDsl bla = ProductSelectionDraftBuilder.of(name).key(randomKey()).build();

        assertThat(bla.getCustom()).isNull();
    }

    @Test
    public void productSelectionReadFromJson() throws IOException {
        final ProductSelection productSelection = SphereJsonUtils.readObjectFromResource("productSelections/product-selection.json", ProductSelection.class);

        assertThat(productSelection.getType()).isEqualTo(ProductSelectionType.INDIVIDUAL);
    }
}