package test;

import io.sphere.sdk.queries.PagedQueryResult;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.fest.assertions.Assertions.assertThat;

public class PagedQueryResultTest {

    public static final int TOTAL = 100;
    public static int PAGE_SIZE = 25;

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

    private List<Integer> listOfSize(final int size) {
        if (size <= 0) {
            return Collections.emptyList();
        } else {
            return IntStream.range(0, size).boxed().collect(Collectors.toList());
        }
    }
}
