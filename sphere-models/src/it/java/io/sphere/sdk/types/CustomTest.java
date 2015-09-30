package io.sphere.sdk.types;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryDraft;
import io.sphere.sdk.categories.CategoryDraftBuilder;
import io.sphere.sdk.categories.CategoryFixtures;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteCommand;
import io.sphere.sdk.categories.commands.CategoryUpdateCommand;
import io.sphere.sdk.categories.commands.updateactions.SetCustomType;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.types.commands.TypeDeleteCommand;
import io.sphere.sdk.types.queries.TypeQuery;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CustomTest extends IntegrationTest {
    private static Type type;
    private static Category category1;
    private static Category category2;

    public static void deleteType() {
        execute(TypeDeleteCommand.of(type));
        //TODO only if no refs exist
    }

    @Test
    public void createCategoryWithType() throws Exception {
        final Category category =
                CreateCategoryWithTypeDemo.createCategoryWithType(client(), category1, category2);
        execute(CategoryDeleteCommand.of(category));
    }

    @Test
    public void assignTypeInUpdateAction() throws Exception {
        final Category category = TypeAssigningInUpdateActionDemo
                .updateCategoryWithType(client(), category1, category2);
        execute(CategoryDeleteCommand.of(category));
    }

    @BeforeClass
    public static void setup() {
        execute(TypeQuery.of().withPredicates(type -> type.key().is("category-customtype-key")))
                .getResults().forEach(type -> {
            execute(CategoryQuery.of().withPredicates(category -> category.custom().type().is(type))).getResults().forEach(cat -> execute(CategoryDeleteCommand.of(cat)));
            execute(TypeDeleteCommand.of(type));
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
        execute(CategoryDeleteCommand.of(category1));
    }

    @AfterClass
    public static void cleanUpCategory2() {
        execute(CategoryDeleteCommand.of(category2));
    }
}