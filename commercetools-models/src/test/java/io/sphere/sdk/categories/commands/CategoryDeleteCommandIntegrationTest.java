package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryBuilder;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void execution() throws Exception {
        final CategoryDraft draft = CategoryDraftBuilder.of(randomSlug(), randomSlug()).build();
        final Category category = client().executeBlocking(CategoryCreateCommand.of(draft));

        client().executeBlocking(CategoryDeleteCommand.of(category));
        assertThat(client().executeBlocking(CategoryQuery.of().byId(category.getId())).head()).isEmpty();
    }

    @Test
    public void referenceExpansion() throws Exception {
        final Category parent = client().executeBlocking(CategoryCreateCommand.of(CategoryDraftBuilder.of(randomSlug(), randomSlug()).build()));
        final Category category = client().executeBlocking(CategoryCreateCommand.of(CategoryDraftBuilder.of(randomSlug(), randomSlug()).parent(parent).build()));
        final CategoryDeleteCommand deleteCommand = CategoryDeleteCommand.of(category).plusExpansionPaths(m -> m.parent());
        final Category deletedCategory = client().executeBlocking(deleteCommand);

        assertThat(deletedCategory.getParent().getObj()).isNotNull().isEqualTo(parent);
        client().executeBlocking(CategoryDeleteCommand.of(parent));
    }

    @Test
    public void executionForDeletionByKey() throws Exception {
        final String key = randomKey();
        final CategoryDraft draft = CategoryDraftBuilder.of(randomLocalizedString(), randomSlug()).key(key).build();
        final Category category = client().executeBlocking(CategoryCreateCommand.of(draft));

        client().executeBlocking(CategoryDeleteCommand.ofKey(key,category.getVersion()));
        assertThat(client().executeBlocking(CategoryQuery.of().byId(category.getId())).head()).isEmpty();
    }

    @Test
    public void referenceExpansionForDeletionByKey() throws Exception {
        final String key = randomKey();
        final Category parent = client().executeBlocking(CategoryCreateCommand.of(CategoryDraftBuilder.of(randomLocalizedString(), randomSlug()).build()));
        final Category category = client().executeBlocking(CategoryCreateCommand.of(CategoryDraftBuilder.of(randomLocalizedString(), randomSlug()).key(key).parent(parent).build()));
        final CategoryDeleteCommand deleteCommand = CategoryDeleteCommand.ofKey(key, category.getVersion()).plusExpansionPaths(m -> m.parent());
        final Category deletedCategory = client().executeBlocking(deleteCommand);

        assertThat(deletedCategory.getParent().getObj()).isNotNull().isEqualTo(parent);
        client().executeBlocking(CategoryDeleteCommand.of(parent));
    }

    @Test
    public void expandByString() {
        final Category category = CategoryBuilder.of("abc", randomSlug(), randomSlug()).build();
        final CategoryDeleteCommand actual = CategoryDeleteCommand.of(category).withExpansionPaths("id").plusExpansionPaths("name");
        assertThat(actual.httpRequestIntent().toHttpRequest("").getUrl()).isEqualTo("/categories/abc?version=1&expand=id&expand=name");
    }

}
