package example;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryBuilder;
import io.sphere.sdk.categories.NewCategory;
import io.sphere.sdk.categories.NewCategoryBuilder;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.categories.commands.CategoryDeleteByIdCommand;
import io.sphere.sdk.categories.queries.CategoryQuery;
import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.commands.Command;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import play.libs.F;

import static java.util.Locale.ENGLISH;

public class CategoryLifecycleExample {
    private PlayJavaClient client;
    private NewCategory newCategory;
    private Category category;

    public void createCategory() {
        final NewCategory newCategory = createCategoryTemplate();
        final Command<Category> command = new CategoryCreateCommand(newCategory);
        final F.Promise<Category> result = client.execute(command);
    }

    private NewCategory createCategoryTemplate() {
        return null;
    }

    public void newCategoryConstruction() {
        final Referenceable<Category> electronicCategory = parentCategory();//already created category we need as reference
        final LocalizedString name = LocalizedString.of(ENGLISH, "Video Games");
        final LocalizedString slug = LocalizedString.of(ENGLISH, "video-games");
        final LocalizedString description = LocalizedString.of(ENGLISH, "games for the PC");
        final NewCategory newCategory = NewCategoryBuilder.of(name, slug)
                .description(description)
                .orderHint("0.2")
                .parent(electronicCategory)
                .build();

    }

    private Category parentCategory() {
        return null;
    }

    public void categoryForUnitTest() {
        final Category electronicCategory = parentCategory();
        final LocalizedString name = LocalizedString.of(ENGLISH, "Video Games");
        final LocalizedString slug = LocalizedString.of(ENGLISH, "video-games");
        final LocalizedString description = LocalizedString.of(ENGLISH, "games for the PC");
        final Category category = CategoryBuilder.of("category-id", name, slug)
                .description(description)
                .parent(electronicCategory)
                .build();
    }

    public void delete() {
        Category category = findCategory();
        Command<Category> command = new CategoryDeleteByIdCommand(category);
        F.Promise<Category> result = client.execute(command);
    }

    private Category findCategory() {
        return null;
    }

    public void query() {
        final ExpansionPath<Category> expand = CategoryQuery.expansionPath().parent();//fill parent reference
        Query<Category> query = new CategoryQuery().bySlug(ENGLISH, "video-games").withExpansionPaths(expand);
        F.Promise<PagedQueryResult<Category>> result = client.execute(query);
    }
}
