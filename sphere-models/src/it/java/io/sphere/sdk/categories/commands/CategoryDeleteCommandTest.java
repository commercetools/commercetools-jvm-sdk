package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.test.IntegrationTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;
import static io.sphere.sdk.test.SphereTestUtils.*;

public class CategoryDeleteCommandTest extends IntegrationTest {
    @Test
    public void execution() throws Exception {
        final CategoryDraft draft = CategoryDraftBuilder.of(randomSlug(), randomSlug()).build();
        final Category category = execute(CategoryCreateCommand.of(draft));

        execute(CategoryDeleteCommand.of(category));
        assertThat(execute(CategoryQuery.of().byId(category.getId())).head()).isEmpty();
    }

    @Test
    public void referenceExpansion() throws Exception {
        final Category parent = execute(CategoryCreateCommand.of(CategoryDraftBuilder.of(randomSlug(), randomSlug()).build()));
        final Category category = execute(CategoryCreateCommand.of(CategoryDraftBuilder.of(randomSlug(), randomSlug()).parent(parent).build()));
        final CategoryDeleteCommand deleteCommand = CategoryDeleteCommand.of(category).plusExpansionPaths(m -> m.parent());
        final Category deletedCategory = execute(deleteCommand);


        System.err.println(deleteCommand.httpRequestIntent().getPath());

        assertThat(deletedCategory.getParent().getObj()).isNotNull().isEqualTo(parent);
        execute(CategoryDeleteCommand.of(parent));
    }
}