package io.sphere.sdk.categories.queries;

import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryQueryBuilderTest {
    @Test
    public void name() {
        final CategoryQueryBuilder builder = CategoryQueryBuilder.of()
                .predicates(m -> m.id().is("f"))
                .fetchTotal(true)
                .limit(2)
                .offset(5)
                .plusPredicates(m -> m.name().locale(Locale.ENGLISH).is("bar"))
                .sort(m -> m.slug().locale(Locale.ENGLISH).sort().desc())
                .plusExpansionPaths(m -> m.parent());
        final CategoryQuery actual = builder.build();
        final CategoryQuery expected = CategoryQuery.of().byId("f")
                .withFetchTotal(true)
                .withLimit(2)
                .withOffset(5)
                .plusPredicates(m -> m.name().locale(Locale.ENGLISH).is("bar"))
                .withSort(m -> m.slug().locale(Locale.ENGLISH).sort().desc())
                .plusExpansionPaths(m -> m.parent());
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void queryByString() {
        final CategoryQuery actual = CategoryQuery.of()
                .withPredicates("id > :lastId")
                .plusPredicates("1 = 1");
        assertThat(actual.httpRequestIntent().toHttpRequest("").getUrl()).isEqualTo("/categories?where=id+%3E+%3AlastId&where=1+%3D+1");
    }

    @Test
    public void queryBuilderByString() {
        final CategoryQuery actual = CategoryQueryBuilder.of()
                .predicates("id > :lastId")
                .plusPredicates("1 = 1")
                .build();
        assertThat(actual.httpRequestIntent().toHttpRequest("").getUrl()).isEqualTo("/categories?where=id+%3E+%3AlastId&where=1+%3D+1");
    }

    @Test
    public void sortByString() {
        final CategoryQuery actual = CategoryQuery.of()
                                                  .withSort("id ASC")
                                                  .plusSort("name ASC");
        assertThat(actual.httpRequestIntent().toHttpRequest("").getUrl()).isEqualTo("/categories?sort=id+ASC&sort=name+ASC");
    }

    @Test
    public void sortBuilderByString() {
        final CategoryQuery actual = CategoryQueryBuilder.of()
                                                         .sort("id ASC")
                                                         .plusSort("name ASC")
                                                         .build();
        assertThat(actual.httpRequestIntent().toHttpRequest("").getUrl()).isEqualTo("/categories?sort=id+ASC&sort=name+ASC");
    }

    @Test
    public void expandByString() {
        final CategoryQuery actual = CategoryQuery.of()
                                                  .withExpansionPaths("id")
                                                  .plusExpansionPaths("name");
        assertThat(actual.httpRequestIntent().toHttpRequest("").getUrl()).isEqualTo("/categories?expand=id&expand=name");
    }

    @Test
    public void expandBuilderByString() {
        final CategoryQuery actual = CategoryQueryBuilder.of()
                                                         .expansionPaths("id")
                                                         .plusExpansionPaths("name")
                                                         .build();
        assertThat(actual.httpRequestIntent().toHttpRequest("").getUrl()).isEqualTo("/categories?expand=id&expand=name");
    }
}
