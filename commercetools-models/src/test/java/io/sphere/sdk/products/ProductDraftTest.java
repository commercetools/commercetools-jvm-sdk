package io.sphere.sdk.products;

import io.sphere.sdk.annotations.NotOSGiCompatible;
import io.sphere.sdk.models.WithKey;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@NotOSGiCompatible
public class ProductDraftTest {

    @Test
    public void productDraftImplementsWithKey() {
        final ProductDraft productDraft = mock(ProductDraft.class);
        when(productDraft.getKey()).thenReturn("foo");

        final String key = getKey(productDraft);

        assertThat(key).isEqualTo(productDraft.getKey());
    }

    private <T extends WithKey> String getKey(@Nonnull final T resource) {
        return resource.getKey();
    }

}
