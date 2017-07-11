package io.sphere.sdk.queries;

import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class PagedQueryResultTest {

    public static final Long TOTAL = 100L;
    public static final int PAGE_SIZE = 25;
    final PagedQueryResult<Integer> a = PagedQueryResult.of(listOfSize(1));
    final PagedQueryResult<Integer> b = PagedQueryResult.of(listOfSize(2));

    @Test
    public void oneFilledResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(listOfSize(4));
        assertThat(queryResult.isFirst()).isTrue();
        assertThat(queryResult.isLast()).isTrue();
    }

    @Test
    public void emptyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.empty();
        assertThat(queryResult.isFirst()).isTrue();
        assertThat(queryResult.isLast()).isTrue();
    }

    @Test
    public void firstOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(0L, TOTAL, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isTrue();
        assertThat(queryResult.isLast()).isFalse();
    }

    @Test
    public void middleOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of((long) PAGE_SIZE, TOTAL, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isFalse();
    }

    @Test
    public void getResultPage() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of((long) 80, TOTAL, listOfSize(PAGE_SIZE));
        assertThat(queryResult.getPage()).isEqualTo(3);
    }


    @Test
    public void getPageForEmptyResults() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of((long) 80, TOTAL, listOfSize(0));
        assertThat(queryResult.getPage()).isEqualTo(0);
    }

    @Test
    public void lastFilledOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(TOTAL - PAGE_SIZE, TOTAL, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isTrue();
    }

    @Test
    public void lastNotFullyFilledOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(95L - PAGE_SIZE, 95L, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isTrue();
    }

    @Test
    public void lastOneResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(100L, 101L, listOfSize(1));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isTrue();
    }

    @Test
    public void beforeLastOneResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(99L, 101L, listOfSize(1));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isFalse();
    }

    @Test
    public void testEquals() throws Exception {
        assertThat(a).isEqualTo(a);
        assertThat(a).isNotEqualTo(b);
        assertThat(PagedQueryResult.of(listOfSize(1))).isEqualTo(PagedQueryResult.of(listOfSize(1)));
    }

    @Test
    public void testHashCode() throws Exception {
        assertThat(a.hashCode()).isEqualTo(a.hashCode());
        assertThat(a.hashCode()).isNotEqualTo(b.hashCode());
    }

    @Test
    public void headContainsOptionalFirstValue() throws Exception {
        assertThat(a.head()).isEqualTo(Optional.of(0));
    }

    @Test
    public void withOffset() throws Exception {
        assertThat(PagedQueryResult.of(0L, 500L, listOfSize(25)).withOffset(5L)).
                isEqualTo(PagedQueryResult.of(5L, 500L, listOfSize(25)));
    }

    @Test
    public void singleValueResult() throws Exception {
        final PagedQueryResult<String> result = PagedQueryResult.of("hello");
        assertThat(result.getCount()).isEqualTo(1);
        assertThat(result.getOffset()).isEqualTo(0);
        assertThat(result.getResults()).isEqualTo(Collections.singletonList("hello"));
        assertThat(result.getTotal()).isEqualTo(1);
    }

    @Test
    public void withTotal() throws Exception {
        final PagedQueryResult<String> result = PagedQueryResult.of("hello").withTotal(500L);
        assertThat(result.getCount()).isEqualTo(1);
        assertThat(result.getOffset()).isEqualTo(0);
        assertThat(result.getResults()).isEqualTo(Collections.singletonList("hello"));
        assertThat(result.getTotal()).isEqualTo(500);
    }

    private List<Integer> listOfSize(final int size) {
        if (size <= 0) {
            return Collections.emptyList();
        } else {
            return IntStream.range(0, size).boxed().collect(Collectors.toList());
        }
    }
}
