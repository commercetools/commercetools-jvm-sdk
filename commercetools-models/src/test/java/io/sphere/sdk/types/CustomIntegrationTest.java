package io.sphere.sdk.types;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.SphereRequest;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.commands.TypeDeleteCommand;
import io.sphere.sdk.types.queries.TypeQuery;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomIntegrationTest extends IntegrationTest {
    private static Type type;
    private static Category category1;
    private static Category category2;

    public static void deleteType() {
        client().executeBlocking(TypeDeleteCommand.of(type));
        //TODO only if no refs exist
    }

    @Test
    public void createCategoryWithType() throws Exception {
        final Category category =
                CreateCategoryWithTypeDemo.createCategoryWithType(client(), category1, category2);
        client().executeBlocking(CategoryDeleteCommand.of(category));
    }

    @Test
    public void assignTypeInUpdateAction() throws Exception {
        final Category category = TypeAssigningInUpdateActionDemo
                .updateCategoryWithType(client(), category1, category2);
        client().executeBlocking(CategoryDeleteCommand.of(category));
    }

    @Test
    public void updateFieldValues() throws Exception {
        final Category category =
                CreateCategoryWithTypeDemo.createCategoryWithType(client(), category1, category2);
        final Category updatedCategory = UpdateFieldValueDemo.updateFieldValues(client(), category);
        client().executeBlocking(CategoryDeleteCommand.of(updatedCategory));
    }

    @Test
    public void removeTypeFromObject() throws Exception {
        final Category category =
                CreateCategoryWithTypeDemo.createCategoryWithType(client(), category1, category2);

        final Category updatedCategory = RemoveTypeFromObjectDemo.removeTypeFromCategory(client(), category);

        client().executeBlocking(CategoryDeleteCommand.of(updatedCategory));

    }

    @BeforeClass
    public static void setup() {
        final TypeQuery typeQuery = TypeQuery.of().withPredicates(type -> type.key().is("category-customtype-key"));
        client().executeBlocking(typeQuery)
                .getResults().forEach(type -> {
            final CategoryQuery categoryQuery = CategoryQuery.of().withPredicates(category -> category.custom().type().is(type));
            client().executeBlocking(categoryQuery).getResults().forEach(cat -> client().executeBlocking(CategoryDeleteCommand.of(cat)));
            client().executeBlocking(TypeDeleteCommand.of(type));
        });
        type = CreateTypeDemo.createType(client());
        category1 = CategoryFixtures.createCategory(client());
        category2 = CategoryFixtures.createCategory(client());
    }

    @AfterClass
    public static void cleanUpType() {
        deleteType();
        type = null;
    }

    @AfterClass
    public static void cleanUpCategory1() {
        client().executeBlocking(CategoryDeleteCommand.of(category1));
    }

    @AfterClass
    public static void cleanUpCategory2() {
        client().executeBlocking(CategoryDeleteCommand.of(category2));
    }
}