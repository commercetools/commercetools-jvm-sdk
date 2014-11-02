package io.sphere.sdk.meta;


import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.queries.Predicate;
import io.sphere.sdk.queries.QueryDsl;
import io.sphere.sdk.queries.Sort;
import org.junit.Test;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;
import static io.sphere.sdk.products.ProductProjectionType.STAGED;
import static io.sphere.sdk.queries.SortDirection.ASC;
import static io.sphere.sdk.queries.SortDirection.DESC;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.fest.assertions.Assertions.assertThat;

public class QueryDocumentationTest {

    public void queryForAllDemo() {
        final ProductQuery query = new ProductQuery();
    }

    public void queryBySlug() {
        final QueryDsl<Product> queryBySlug = new ProductQuery()
                .bySlug(CURRENT, ENGLISH, "blue-t-shirt");
    }

    public void queryByNames() {
        final Predicate<Product> predicate = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isOneOf("blue t-shirt", "blue jeans");
        final QueryDsl<Product> query = new ProductQuery().withPredicate(predicate);
    }

    public void queryByNamesDesugared() {
        final Predicate<Product> predicate = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isOneOf("blue t-shirt", "blue jeans");
        final QueryDsl<Product> query = new ProductQuery().withPredicate(predicate);
    }

    @Test
    public void testX() throws Exception {
        final Predicate<Product> safePredicate = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isOneOf("blue t-shirt", "blue jeans");
        final Predicate<Product> unsafePredicate =
                Predicate.of("masterData(current(name(en in (\"blue t-shirt\", \"blue jeans\"))))");
        assertThat(unsafePredicate).isEqualTo(safePredicate);
    }

    public void predicateOrExample() {
        final Predicate<Product> nameIsFoo = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).is("foo");
        final Predicate<Product> idIsBar = ProductQuery.model().id().is("bar");
        final QueryDsl<Product> query = new ProductQuery().withPredicate(nameIsFoo.or(idIsBar));
    }

    public void predicateAndExample() {
        final Predicate<Product> nameIsFoo = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).is("foo");
        final Reference<Category> cat1 = Category.reference("cat1");
        final Predicate<Product> isInCat1 = ProductQuery.model().masterData().current()
                .categories().isIn(cat1);
        final QueryDsl<Product> query = new ProductQuery().withPredicate(nameIsFoo.and(isInCat1));
    }

    public void predicateAndWithWhereExample() {
        final Reference<Category> cat1 = Category.reference("cat1");
        final Predicate<Product> nameIsFooAndIsInCat1 = ProductQuery.model().masterData().current()
                .where(cur -> cur.name().lang(ENGLISH).is("foo").and(cur.categories().isIn(cat1)));
        final QueryDsl<Product> query = new ProductQuery().withPredicate(nameIsFooAndIsInCat1);
    }

    public void predicateNotExample() {
        final Predicate<Product> nameIsNotFoo = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).isNot("foo");
        final QueryDsl<Product> query = new ProductQuery().withPredicate(nameIsNotFoo);
    }

    public void sortByName() {
        final Sort<Product> byNameAsc = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).sort(ASC);
        final QueryDsl<Product> query = new ProductQuery().withSort(asList(byNameAsc));
    }

    public void sortByNameAscAndIdDesc() {
        final Sort<Product> byNameAsc = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).sort(ASC);
        final Sort<Product> byIdDesc = ProductQuery.model().id().sort(DESC);
        final QueryDsl<Product> query = new ProductQuery().withSort(asList(byNameAsc, byIdDesc));
    }

    @Test
    public void sortCreationByString() {
        final Sort<Product> safeSort = ProductQuery.model().masterData().current().name()
                .lang(ENGLISH).sort(ASC);
        final Sort<Product> unsafeSort = Sort.of("masterData.current.name.en asc");
        assertThat(safeSort).isEqualTo(unsafeSort);
    }

    public void queryAllExampleInPaginationContext() {
        final ProductQuery query = new ProductQuery();
    }

    public void limitProductQueryTo4() {
        final QueryDsl<Product> query = new ProductQuery().withLimit(4);
    }

    @Test
    public void limitProductQueryTo4PlusOffset4() {
        final QueryDsl<Product> queryForFirst4 = new ProductQuery().withLimit(4);
        final QueryDsl<Product> queryForProductId04to07 = queryForFirst4.withOffset(4);
        assertThat(queryForProductId04to07).isEqualTo(new ProductQuery().withLimit(4).withOffset(4));
    }

    public void expandProductTypeForProduct() {
        final QueryDsl<Product> query = new ProductQuery()
                .withExpansionPath(ProductQuery.expansionPath().productType());
    }

    public void expandCategoryAndCategoryParentForProduct() {
        final ExpansionPath<ProductProjection> expansionPath =
                ProductProjectionQuery.expansionPath().categories().parent();
        final QueryDsl<ProductProjection> query = new ProductProjectionQuery(STAGED)
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