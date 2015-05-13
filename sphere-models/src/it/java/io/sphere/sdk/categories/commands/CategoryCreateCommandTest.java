package io.sphere.sdk.categories.commands;

import com.github.slugify.Slugify;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import java.util.Locale;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.*;

public class CategoryCreateCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final LocalizedStrings name = LocalizedStrings.of(Locale.ENGLISH, "winter clothing");
        final LocalizedStrings slug = name.mapValue((locale, value) -> new Slugify().slugify(value + "-" + randomKey()));
        final String externalId = randomKey();
        final CategoryDraft categoryDraft = CategoryDraftBuilder.of(name, slug).externalId(externalId).build();
        final Category category = execute(CategoryCreateCommand.of(categoryDraft));
        assertThat(category.getName()).isEqualTo(name);
        assertThat(category.getSlug()).isEqualTo(slug);
        assertThat(category.getExternalId()).contains(externalId);
    }


}