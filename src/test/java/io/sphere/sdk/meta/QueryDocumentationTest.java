package io.sphere.sdk.meta;


import io.sphere.sdk.categories.Category;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.products.expansion.ProductProjectionExpansionModel;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.products.queries.ProductQueryModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import org.junit.Test;

import static io.sphere.sdk.products.ProductProjectionType.CURRENT;
import static io.sphere.sdk.queries.QuerySortDirection.DESC;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static org.assertj.core.api.Assertions.assertThat;

public class QueryDocumentationTest {

    public void queryForAllDemo() {
        final ProductQuery query = ProductQuery.of();
    }

    public void queryBySlug() {
        final ProductQuery queryBySlug = ProductQuery.of()
                .bySlug(CURRENT, ENGLISH, "blue-t-shirt");
    }

    public void queryByNames() {
        final QueryPredicate<Product> predicate = ProductQueryModel.of().masterData().current().name()
                .lang(ENGLISH).isIn(asList("blue t-shirt", "blue jeans"));
        final ProductQuery query = ProductQuery.of().withPredicates(predicate);
    }

    public void queryByNamesDesugared() {
        final QueryPredicate<Product> predicate = ProductQueryModel.of().masterData().current().name()
                .lang(ENGLISH).isIn(asList("blue t-shirt", "blue jeans"));
        final ProductQuery query = ProductQuery.of().withPredicates(predicate);
    }

    @Test
    public void testX() throws Exception {
        final QueryPredicate<Product> safePredicate = ProductQueryModel.of().masterData().current().name()
                .lang(ENGLISH).isIn(asList("blue t-shirt", "blue jeans"));
        final QueryPredicate<Product> unsafePredicate =
                QueryPredicate.of("masterData(current(name(en in (\"blue t-shirt\", \"blue jeans\"))))");
        assertThat(unsafePredicate).isEqualTo(safePredicate);
    }

    public void predicateOrExample() {
        final QueryPredicate<Product> nameIsFoo = ProductQueryModel.of().masterData().current().name()
                .lang(ENGLISH).is("foo");
        final QueryPredicate<Product> idIsBar = ProductQueryModel.of().id().is("bar");
        final ProductQuery query = ProductQuery.of().withPredicates(nameIsFoo.or(idIsBar));
    }

    public void predicateAndExample() {
        final QueryPredicate<Product> nameIsFoo = ProductQueryModel.of().masterData().current().name()
                .lang(ENGLISH).is("foo");
        final Reference<Category> cat1 = Category.reference("cat1");
        final QueryPredicate<Product> isInCat1 = ProductQueryModel.of().masterData().current()
                .categories().isIn(asList(cat1));
        final ProductQuery query = ProductQuery.of().withPredicates(nameIsFoo.and(isInCat1));
    }

    public void predicateAndWithWhereExample() {
        final Reference<Category> cat1 = Category.reference("cat1");
        final QueryPredicate<Product> nameIsFooAndIsInCat1 = ProductQueryModel.of().masterData().current()
                .where(cur -> cur.name().lang(ENGLISH).is("foo").and(cur.categories().isIn(asList(cat1))));
        final ProductQuery query = ProductQuery.of().withPredicates(nameIsFooAndIsInCat1);
    }

    @Test
    public void predicateNotExample() {
        final QueryPredicate<Product> nameIsFoo = ProductQueryModel.of()
                .masterData().current().name().lang(ENGLISH).is("foo");
        final QueryPredicate<Product> nameIsNotFoo = nameIsFoo.negate();
        assertThat(nameIsNotFoo).isEqualTo(QueryPredicate.of("not(masterData(current(name(en=\"foo\"))))"));
    }

    @Test
    public void notSyntax() throws Exception {
        final ProductQuery query = ProductQuery.of()
                .withPredicates(m -> m.not(m.masterData().current().name().lang(ENGLISH).is("foo")));
        assertThat(query.predicates()).isEqualTo(asList(QueryPredicate.of("not(masterData(current(name(en=\"foo\"))))")));
    }

    public void sortByName() {
        final QuerySort<Product> byNameAsc = ProductQueryModel.of().masterData().current().name()
                .lang(ENGLISH).sort().asc();
        final ProductQuery query = ProductQuery.of().withSort(asList(byNameAsc));
    }

    public void sortByNameAscAndIdDesc() {
        final QuerySort<Product> byNameAsc = ProductQueryModel.of().masterData().current().name()
                .lang(ENGLISH).sort().asc();
        final QuerySort<Product> byIdDesc = ProductQueryModel.of().id().sort().by(DESC);
        final ProductQuery query = ProductQuery.of().withSort(asList(byNameAsc, byIdDesc));
    }

    @Test
    public void sortCreationByString() {
        final QuerySort<Product> safeSort = ProductQueryModel.of().masterData().current().name()
                .lang(ENGLISH).sort().asc();
        final QuerySort<Product> unsafeSort = QuerySort.of("masterData.current.name.en asc");
        assertThat(safeSort).isEqualTo(unsafeSort);
    }

    public void queryAllExampleInPaginationContext() {
        final ProductQuery query = ProductQuery.of();
    }

    public void limitProductQueryTo4() {
        final ProductQuery query = ProductQuery.of().withLimit(4L);
    }

    @Test
    public void limitProductQueryTo4PlusOffset4() {
        final ProductQuery queryForFirst4 = ProductQuery.of().withLimit(4L);
        final ProductQuery queryForProductId04to07 = queryForFirst4.withOffset(4L);
        assertThat(queryForProductId04to07).isEqualTo(ProductQuery.of().withLimit(4L).withOffset(4L));
    }

    public void expandProductTypeForProduct() {
        final ProductQuery query = ProductQuery.of()
                .withExpansionPaths(m -> m.productType());
    }

    public void expandCategoryAndCategoryParentForProduct() {
        final ProductProjectionQuery query = ProductProjectionQuery.ofStaged()
                .withExpansionPaths(m -> m.categories().parent());
    }

    @Test
    public void createExpansionPathByString() throws Exception {
        final ExpansionPath<ProductProjection> safePath =
                ProductProjectionExpansionModel.of().categories().parent().expansionPaths().get(0);
        final ExpansionPath<ProductProjection> unsafePath = ExpansionPath.of("categories[*].parent");
        assertThat(safePath).isEqualTo(unsafePath);
    }
}