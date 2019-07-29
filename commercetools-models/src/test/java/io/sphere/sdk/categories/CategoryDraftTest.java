package io.sphere.sdk.categories;

import io.sphere.sdk.annotations.NotOSGiCompatible;
import io.sphere.sdk.models.WithKey;
import org.junit.Test;

import javax.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@NotOSGiCompatible
public class CategoryDraftTest {

    @Test
    public void categoryDraftImplementsWithKey() {
        final CategoryDraft category = mock(CategoryDraft.class);
        when(category.getKey()).thenReturn("foo");

        final String key = getKey(category);

        assertThat(key).isEqualTo(category.getKey());
    }

    private <T extends WithKey> String getKey(@Nonnull final T resource) {
        return resource.getKey();
    }
}
