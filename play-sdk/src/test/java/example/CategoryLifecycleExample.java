package example;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryBuilder;
import io.sphere.sdk.categories.NewCategory;
import io.sphere.sdk.categories.NewCategoryBuilder;
import io.sphere.sdk.categories.commands.CategoryCreateCommand;
import io.sphere.sdk.client.PlayJavaClient;
import io.sphere.sdk.models.LocalizedString;
import play.libs.F;

import java.util.Locale;

import static java.util.Locale.ENGLISH;

public class CategoryLifecycleExample {
    private PlayJavaClient client;
    private NewCategory newCategory;

    public void createCategory() {
        CategoryCreateCommand command = new CategoryCreateCommand(newCategory);
        F.Promise<Category> result = client.execute(command);
    }

    public void newCategoryConstruction() {
        Category electronicCategory = previouslyConstructedCategory();
        LocalizedString name = LocalizedString.of(ENGLISH, "Video Games");
        LocalizedString slug = LocalizedString.of(ENGLISH, "video-games");
        LocalizedString description = LocalizedString.of(ENGLISH, "games for the PC");
        NewCategory category = NewCategoryBuilder.of(name, slug)
                .description(description)
                .orderHint("0.2")
                .parent(electronicCategory)
                .build();

    }

    private Category previouslyConstructedCategory() {
        return null;
    }

    public void categoryForUnitTest() {
        Category electronicCategory = previouslyConstructedCategory();
        LocalizedString name = LocalizedString.of(ENGLISH, "Video Games");
        LocalizedString slug = LocalizedString.of(ENGLISH, "video-games");
        LocalizedString description = LocalizedString.of(ENGLISH, "games for the PC");
        final Category category = CategoryBuilder.of("category-id", name, slug)
                .description(description)
                .parent(electronicCategory)
                .build();
    }
}
