package io.sphere.sdk.products;

import io.sphere.sdk.annotations.NotOSGiCompatible;
import io.sphere.sdk.models.WithKey;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@NotOSGiCompatible
public class ProductLikeTest {

    @Test
    public void productLikeImplementsWithKey() {
        final ProductLike productLike = mock(ProductLike.class);
        when(productLike.getKey()).thenReturn("foo");

        final String key = getKey(productLike);

        assertThat(key).isEqualTo(productLike.getKey());
    }

    private <T extends WithKey> String getKey(@Nonnull final T resource) {
        return resource.getKey();
    }

}
