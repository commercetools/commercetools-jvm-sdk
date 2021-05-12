package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.randomSlug;
import static org.assertj.core.api.Assertions.*;

public class CategoryCreateCommandTest {
    @Test
    public void containsDraftGetter() {
        final CategoryDraft categoryDraft = CategoryDraftBuilder.of(randomSlug(), randomSlug()).build();
        final CategoryCreateCommand categoryCreateCommand = CategoryCreateCommand.of(categoryDraft);
        assertThat(categoryCreateCommand.getDraft()).isEqualTo(categoryDraft);
    }

    @Test
    public void expandByString() {
        final CategoryDraft categoryDraft = CategoryDraftBuilder.of(randomSlug(), randomSlug()).build();
        final CategoryCreateCommand actual = CategoryCreateCommand.of(categoryDraft).withExpansionPaths("id").plusExpansionPaths("name");
        assertThat(actual.httpRequestIntent().toHttpRequest("").getUrl()).isEqualTo("/categories?expand=id&expand=name");
    }
}
