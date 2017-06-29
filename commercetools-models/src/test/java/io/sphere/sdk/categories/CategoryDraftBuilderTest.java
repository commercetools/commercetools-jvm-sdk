package io.sphere.sdk.categories;

import io.sphere.sdk.models.LocalizedString;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link CategoryDraftBuilder}
 */
public class CategoryDraftBuilderTest {

    @Test
    public void copyFromCategory() {
        final Category parent = CategoryBuilder.of(randomString(), LocalizedString.ofEnglish(randomString()), LocalizedString.ofEnglish(randomString()))
                .build();

        final Category category = CategoryBuilder.of(randomString(), LocalizedString.ofEnglish(randomString()), LocalizedString.ofEnglish(randomString()))
                .externalId(randomString())
                .key(randomKey())
                .parent(parent)
                .assets(null)
                .build();
        final CategoryDraft categoryDraft = CategoryDraftBuilder.of(category).build();

        assertThat(category.getName()).isEqualTo(categoryDraft.getName());
        assertThat(category.getSlug()).isEqualTo(categoryDraft.getSlug());
        assertThat(category.getExternalId()).isEqualTo(categoryDraft.getExternalId());
        assertThat(category.getParent()).isEqualTo(categoryDraft.getParent());
        assertThat(category.getAssets()).isEqualTo(categoryDraft.getAssets());
        assertThat(category.getKey()).isEqualTo(categoryDraft.getKey());
    }
}
