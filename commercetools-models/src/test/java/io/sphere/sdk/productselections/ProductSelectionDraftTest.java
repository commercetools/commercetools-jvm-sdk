package io.sphere.sdk.productselections;


import io.sphere.sdk.models.LocalizedString;
import org.junit.Test;

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

}