package io.sphere.sdk.meta;


import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.queries.*;
import org.junit.Test;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.queries.QuerySortDirection.ASC;
import static io.sphere.sdk.queries.QuerySortDirection.DESC;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryDocumentationTest {

    public void queryForAllDemo() {
        final ProductQuery query = ProductQuery.of();
    }

    public void queryBySlug() {
        final QueryDsl<Product> queryBySlug = ProductQuery.of()
                .bySlug(CURRENT, ENGLISH, "blue-t-shirt");
    }

    public void queryByNames() {
        final QueryPredicate<Product> predicate = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isIn("blue t-shirt", "blue jeans");
        final QueryDsl<Product> query = ProductQuery.of().withPredicate(predicate);
    }

    public void queryByNamesDesugared() {
        final QueryPredicate<Product> predicate = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isIn("blue t-shirt", "blue jeans");
        final QueryDsl<Product> query = ProductQuery.of().withPredicate(predicate);
    }

    @Test
    public void testX() throws Exception {
        final QueryPredicate<Product> safePredicate = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isIn("blue t-shirt", "blue jeans");
        final QueryPredicate<Product> unsafePredicate =
                QueryPredicate.of("masterData(current(name(en in (\"blue t-shirt\", \"blue jeans\"))))");
        assertThat(unsafePredicate).isEqualTo(safePredicate);
    }

    public void predicateOrExample() {
        final QueryPredicate<Product> nameIsFoo = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).is("foo");
        final QueryPredicate<Product> idIsBar = ProductQuery.model().id().is("bar");
        final QueryDsl<Product> query = ProductQuery.of().withPredicate(nameIsFoo.or(idIsBar));
    }

    public void predicateAndExample() {
        final QueryPredicate<Product> nameIsFoo = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).is("foo");
        final Reference<Category> cat1 = Category.reference("cat1");
        final QueryPredicate<Product> isInCat1 = ProductQuery.model().masterData().current()
                .categories().isIn(cat1);
        final QueryDsl<Product> query = ProductQuery.of().withPredicate(nameIsFoo.and(isInCat1));
    }

    public void predicateAndWithWhereExample() {
        final Reference<Category> cat1 = Category.reference("cat1");
        final QueryPredicate<Product> nameIsFooAndIsInCat1 = ProductQuery.model().masterData().current()
                .where(cur -> cur.name().lang(ENGLISH).is("foo").and(cur.categories().isIn(cat1)));
        final QueryDsl<Product> query = ProductQuery.of().withPredicate(nameIsFooAndIsInCat1);
    }

    public void predicateNotExample() {
        final QueryPredicate<Product> nameIsNotFoo = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isNot("foo");
        final QueryDsl<Product> query = ProductQuery.of().withPredicate(nameIsNotFoo);
    }

    public void sortByName() {
        final QuerySort<Product> byNameAsc = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).sort(ASC);
        final QueryDsl<Product> query = ProductQuery.of().withSort(asList(byNameAsc));
    }

    public void sortByNameAscAndIdDesc() {
        final QuerySort<Product> byNameAsc = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).sort(ASC);
        final QuerySort<Product> byIdDesc = ProductQuery.model().id().sort(DESC);
        final QueryDsl<Product> query = ProductQuery.of().withSort(asList(byNameAsc, byIdDesc));
    }

    @Test
    public void sortCreationByString() {
        final QuerySort<Product> safeSort = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).sort(ASC);
        final QuerySort<Product> unsafeSort = QuerySort.of("masterData.current.name.en asc");
        assertThat(safeSort).isEqualTo(unsafeSort);
    }

    public void queryAllExampleInPaginationContext() {
        final ProductQuery query = ProductQuery.of();
    }

    public void limitProductQueryTo4() {
        final QueryDsl<Product> query = ProductQuery.of().withLimit(4);
    }

    @Test
    public void limitProductQueryTo4PlusOffset4() {
        final QueryDsl<Product> queryForFirst4 = ProductQuery.of().withLimit(4);
        final QueryDsl<Product> queryForProductId04to07 = queryForFirst4.withOffset(4);
        assertThat(queryForProductId04to07).isEqualTo(ProductQuery.of().withLimit(4).withOffset(4));
    }

    public void expandProductTypeForProduct() {
        final QueryDsl<Product> query = ProductQuery.of()
                .withExpansionPath(ProductQuery.expansionPath().productType());
    }

    public void expandCategoryAndCategoryParentForProduct() {
        final ExpansionPath<ProductProjection> expansionPath =
                ProductProjectionQuery.expansionPath().categories().parent();
        final ProductProjectionQuery query = ProductProjectionQuery.of(STAGED)
                .withExpansionPath(expansionPath);
    }

    @Test
    public void createExpansionPathByString() throws Exception {
        final ExpansionPath<ProductProjection> safePath =
                ProductProjectionQuery.expansionPath().categories().parent();
        final ExpansionPath<ProductProjection> unsafePath = ExpansionPath.of("categories[*].parent");
        assertThat(safePath).isEqualTo(unsafePath);
    }
}