package io.sphere.sdk.queries;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

public class QueryDslTest {

    private static final Sort<String> FOO_SORT = Sort.of("foo ASC");
    private static final Sort<String> ID_SORT = Sort.of("id asc");
    private final QueryDsl<String> prototype = new QueryDslImpl<>("/categories", new TypeReference<PagedQueryResult<String>>() {
    });

    private final static Predicate<String> namePredicate = Predicate.of("name(en=\"myCategory\")");

    @Test
    public void pathWithoutAnyParameters() throws Exception {
        assertThat(prototype.httpRequestIntent().getPath()).isEqualTo("/categories?sort=id+asc");
    }

    @Test
    public void pathWithQuery() throws Exception {
        assertThat(prototype.withPredicate(namePredicate).httpRequestIntent().getPath()).isEqualTo(("/categories?where=name%28en%3D%22myCategory%22%29&sort=id+asc"));

    }

    @Test
    public void pathWithExplicitEmptySort() throws Exception {
        assertThat(prototype.withSort(new LinkedList<>()).httpRequestIntent().getPath()).isEqualTo(("/categories"));
    }

    @Test
    public void pathWithSort() throws Exception {
        assertThat(prototype.withSort(asList(Sort.<String>of("name.en desc"))).httpRequestIntent().getPath()).isEqualTo(("/categories?sort=name.en+desc"));
    }

    @Test
    public void pathWith2Sorts() throws Exception {
        assertThat(prototype.withSort(asList(Sort.<String>of("name.en desc"), Sort.<String>of("id asc"))).httpRequestIntent().getPath()).isEqualTo(("/categories?sort=name.en+desc&sort=id+asc"));
    }

    @Test
    public void pathWithPredicateSortLimitAndOffset() throws Exception {
        final QueryDsl<String> query = prototype.withSort(asList(Sort.<String>of("name.en desc"))).withPredicate(namePredicate).withOffset(400).withLimit(25);
        assertThat(query.httpRequestIntent().getPath()).isEqualTo(("/categories?where=name%28en%3D%22myCategory%22%29&sort=name.en+desc&limit=25&offset=400"));
    }

    @Test
    public void haveAnIdSorterByDefaultToPreventRandomOrderPaging() throws Exception {
        assertThat(prototype.sort()).containsExactly(Sort.of("id asc"));
    }

    @Test
    public void provideACopyMethodForPredicate() throws Exception {
        assertThat(prototype.predicate()).isEqualTo(Optional.empty());
        final Predicate<String> predicate = Predicate.of("id=\"foo\"");
        final QueryDsl<String> other = prototype.withPredicate(predicate);
        assertThat(other.predicate()).isEqualTo(Optional.of(predicate));
        assertThat(other).isNotSameAs(prototype);
    }

    @Test
    public void provideACopyMethodForSort() throws Exception {
        assertThat(prototype.sort().get(0).toSphereSort()).isEqualTo(("id asc"));
        final List<Sort<String>> sortList = asList(Sort.<String>of("foo desc"));
        final QueryDsl<String> other = prototype.withSort(sortList);
        assertThat(other.sort()).isEqualTo(sortList);
        assertThat(other).isNotSameAs(prototype);
    }

    @Test
    public void provideCopyMethodForLimit() throws Exception {
        assertThat(prototype.limit()).isEqualTo(Optional.empty());
        final QueryDsl<String> other = prototype.withLimit(4);
        assertThat(other.limit().get()).isEqualTo(4);
        assertThat(other).isNotSameAs(prototype);
    }

    @Test
    public void provideCopyMethodForOffset() throws Exception {
        assertThat(prototype.offset()).isEqualTo(Optional.empty());
        final QueryDsl<String> other = prototype.withOffset(4);
        assertThat(other.offset().get()).isEqualTo(4);
        assertThat(other).isNotSameAs(prototype);
    }

    @Test
    public void pagingToNextPage() throws Exception {
        final QueryDsl<String> nextPage = Queries.nextPage(prototype);
        assertThat(prototype.offset().orElse(0L)).isEqualTo((0L));
        assertThat(nextPage.offset().get()).isEqualTo(1);
        assertThat(Queries.nextPage(nextPage).offset().get()).isEqualTo(2);
    }

    @Test
    public void expansionPath() throws Exception {
        assertThat(prototype.expansionPaths()).isEqualTo(Collections.emptyList());
        final List<ExpansionPath<String>> paths = asList(ExpansionPath.<String>of("parent"));
        final QueryDsl<String> other = prototype.withExpansionPaths(paths);
        assertThat(other.expansionPaths()).isEqualTo(paths);
        assertThat(other).isNotSameAs(prototype);
    }

    @Test(expected = InvalidQueryOffsetException.class)
    public void offsetHasAMaximum() throws Exception {
        prototype.withOffset(100001);
    }

    @Test(expected = InvalidQueryOffsetException.class)
    public void offsetHasAMinimum() throws Exception {
        prototype.withOffset(-1);
    }

    @Test
    public void plusExpansionPath() throws Exception {
        final ExpansionPath<String> a = ExpansionPath.of("a");
        final ExpansionPath<String> b = ExpansionPath.of("b");
        assertThat(prototype.plusExpansionPath(a).expansionPaths()).containsExactly(a);
        assertThat(prototype.withExpansionPath(a).plusExpansionPath(b).expansionPaths()).containsExactly(a, b);
    }
}
