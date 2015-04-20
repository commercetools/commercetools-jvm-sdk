package io.sphere.sdk.queries;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.fest.assertions.Assertions.assertThat;

public class PagedQueryResultTest {

    public static final int TOTAL = 100;
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
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(0, TOTAL, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isTrue();
        assertThat(queryResult.isLast()).isFalse();
    }

    @Test
    public void middleOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(PAGE_SIZE, TOTAL, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isFalse();
    }

    @Test
    public void lastFilledOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(TOTAL - PAGE_SIZE, TOTAL, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isTrue();
    }

    @Test
    public void lastNotFullyFilledOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(95 - PAGE_SIZE, 95, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isTrue();
    }

    @Test
    public void lastOneResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(100, 101, listOfSize(1));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isTrue();
    }

    @Test
    public void beforeLastOneResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(99, 101, listOfSize(1));
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
        assertThat(PagedQueryResult.of(0, 500, listOfSize(25)).withOffset(5)).
                isEqualTo(PagedQueryResult.of(5, 500, listOfSize(25)));
    }

    @Test
    public void singleValueResult() throws Exception {
        final PagedQueryResult<String> result = PagedQueryResult.of("hello");
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getOffset()).isEqualTo(0);
        assertThat(result.getResults()).isEqualTo(Arrays.asList("hello"));
        assertThat(result.getTotal()).isEqualTo(1);
    }

    @Test
    public void withTotal() throws Exception {
        final PagedQueryResult<String> result = PagedQueryResult.of("hello").withTotal(500);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.getOffset()).isEqualTo(0);
        assertThat(result.getResults()).isEqualTo(Arrays.asList("hello"));
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
