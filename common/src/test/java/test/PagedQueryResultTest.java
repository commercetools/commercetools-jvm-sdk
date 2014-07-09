package test;

import io.sphere.sdk.queries.PagedQueryResult;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.fest.assertions.Assertions.assertThat;

public class PagedQueryResultTest {
    private static final int pageSize = 25;

    @Test
    public void oneFilledResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(0, 4, Arrays.asList(1, 2, 3, 4));
        assertThat(queryResult.isFirst()).isTrue();
        assertThat(queryResult.isLast(pageSize)).isTrue();
    }

    @Test
    public void emptyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.empty();
        assertThat(queryResult.isFirst()).isTrue();
        assertThat(queryResult.isLast(pageSize)).isTrue();
    }

    @Test
    public void firstOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(0, 100, listOfSize(pageSize));
        assertThat(queryResult.isFirst()).isTrue();
        assertThat(queryResult.isLast(pageSize)).isFalse();
    }

    @Test
    public void middleOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(2, 100, listOfSize(4));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast(pageSize)).isFalse();
    }

    @Test
    public void lastFilledOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(3, 100, listOfSize(25));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast(pageSize)).isTrue();
    }

    @Test
    public void lastNotFullyFilledOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(3, 95, listOfSize(20));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast(pageSize)).isTrue();
    }

    @Test
    public void lastOneResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(4, 101, listOfSize(1));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast(pageSize)).isTrue();
    }

    @Test
    public void beforeLastOneResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(3, 101, listOfSize(pageSize));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast(pageSize)).isFalse();
    }

    private List<Integer> listOfSize(final int size) {
        if (size <= 0) {
            return Collections.emptyList();
        } else {
            return IntStream.range(0, size).boxed().collect(Collectors.toList());
        }
    }
}
