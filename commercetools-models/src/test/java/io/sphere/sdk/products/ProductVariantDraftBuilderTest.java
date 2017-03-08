package io.sphere.sdk.products;

import org.junit.Test;

import java.util.Collections;

import static io.sphere.sdk.test.SphereTestUtils.randomString;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link ProductVariantDraftBuilder}.
 */
public class ProductVariantDraftBuilderTest {

    @Test
    public void ofTemplateShouldCopyAllProperties() throws Exception {
        final ProductVariantDraftDsl template = ProductVariantDraftBuilder.of()
                .attributes(Collections.emptyList())
                .assets(Collections.emptyList())
                .key(randomString())
                .sku(randomString())
                .prices(Collections.emptyList())
                .build();

        final ProductVariantDraftDsl copy = ProductVariantDraftBuilder.of(template).build();

        assertThat(copy).isEqualTo(template);
    }

}