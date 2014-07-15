package test;

import java.util.Optional;
import io.sphere.sdk.categories.*;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.queries.Query;
import io.sphere.sdk.queries.QueryIntegrationTest;
import io.sphere.sdk.requests.ClientRequest;
import org.junit.Test;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.fest.assertions.Assertions.assertThat;

import java.util.List;
import java.util.Locale;

public class CategoryIntegrationTest extends QueryIntegrationTest<Category> {

    public static final Locale LOCALE = Locale.ENGLISH;

    @Override
    protected ClientRequest<Category> deleteCommand(final Versioned item) {
        return new CategoryDeleteByIdCommand(item);
    }

    @Override
    protected ClientRequest<Category> newCreateCommandForName(final String name) {
        final LocalizedString localized = en(name);
        return createCreateCommand(localized, localized);
    }

    @Override
    protected String extractName(final Category instance) {
        return instance.getSlug().get(Locale.ENGLISH).get();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Category>> queryRequestForQueryAll() {
        return Category.query();
    }

    @Override
    protected ClientRequest<PagedQueryResult<Category>> queryObjectForName(final String name) {
        return Category.query().bySlug(LOCALE, name);
    }

    @Override
    protected ClientRequest<PagedQueryResult<Category>> queryObjectForNames(final List<String> names) {
        return Category.query().withPredicate(CategoryQueryModel.get().slug().lang(Locale.ENGLISH).isOneOf(names));
    }

    @Test
    public void queryByName() throws Exception {
        final String name = "name xyz";
        final String slug = "slug-xyz";
        cleanUpByName(slug);
        final Category category = client().execute(createCreateCommand(en(name), en(slug)));
        final Query<Category> query = Category.query().byName(LOCALE, name);
        assertThat(client().execute(query).head().get().getId()).isEqualTo(category.getId());
        cleanUpByName(slug);
    }

    @Test
    public void queryByNotName() throws Exception {
        final String name = "name xyz";
        final String slug = "slug-xyz";
        cleanUpByName(slug);
        final Category category = client().execute(createCreateCommand(en(name), en(slug)));
        final Query<Category> query = Category.query().
                withPredicate(CategoryQueryModel.get().name().lang(Locale.ENGLISH).isNot(name));
        final long actual = client().execute(query).getResults().stream().filter(c -> c.getId().equals(name)).count();
        assertThat(actual).isEqualTo(0);
        cleanUpByName(slug);
    }

    @Test
    public void createSmallCategoryHierarchy() throws Exception {
        final String slug = "create-category-test";
        final String name = "create category test";
        final String desc = "desc create category test";
        final String hint = "0.5";
        final String parentName = name + "parent";
        final String parentSlug = slug + "parent";

        cleanUpByName(slug);
        cleanUpByName(parentSlug);
        final NewCategory newParentCategory = NewCategoryBuilder.create(en(parentName), en(parentSlug)).description(en(desc + "parent")).orderHint(hint + "3").build();
        final Category parentCategory = client().execute(new CategoryCreateCommand(newParentCategory));
        final Reference<Category> reference = Category.reference(parentCategory);
        final NewCategory newCategory = NewCategoryBuilder.create(en(name), en(slug)).description(en(desc)).orderHint(hint).parent(reference).build();
        final Category category = client().execute(new CategoryCreateCommand(newCategory));
        assertThat(category.getName()).isEqualTo(en(name));
        assertThat(category.getDescription().get()).isEqualTo(en(desc));
        assertThat(category.getSlug()).isEqualTo(en(slug));
        assertThat(category.getOrderHint().get()).isEqualTo(hint);
        assertThat(category.getParent()).isEqualTo((Optional.of(reference.filled(Optional.empty()))));
        assertThat(parentCategory.getParent()).isEqualTo(Optional.empty());
        cleanUpByName(slug);
        cleanUpByName(parentSlug);
    }

    private ClientRequest<Category> createCreateCommand(final LocalizedString localizedName, final LocalizedString slug) {
        final NewCategory newCategory = NewCategoryBuilder.create(localizedName, slug).description(localizedName).build();
        return new CategoryCreateCommand(newCategory);
    }
}
