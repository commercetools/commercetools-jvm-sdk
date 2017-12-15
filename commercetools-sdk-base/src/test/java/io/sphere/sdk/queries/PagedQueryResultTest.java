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
    public static final Long PAGE_SIZE = 25L;
    final PagedQueryResult<Integer> a = PagedQueryResult.of(listOfSize(1L));
    final PagedQueryResult<Integer> b = PagedQueryResult.of(listOfSize(2L));

    @Test
    public void oneFilledResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(listOfSize(4L));
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
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(0L, PAGE_SIZE, TOTAL, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isTrue();
        assertThat(queryResult.isLast()).isFalse();
    }

    @Test
    public void middleOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(PAGE_SIZE, PAGE_SIZE, TOTAL, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isFalse();
    }

    @Test
    public void pagesIndexingResults() throws Exception {
        { // empty results
            final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(0L, PAGE_SIZE, 0L, listOfSize(0L));
            assertThat(queryResult.getPageIndex()).isEqualTo(0);
            assertThat(queryResult.getTotalPages()).isEqualTo(0);

        }
        { // first page
            final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(0L, PAGE_SIZE, 106L, listOfSize(PAGE_SIZE));
            assertThat(queryResult.getPageIndex()).isEqualTo(0);
            assertThat(queryResult.getTotalPages()).isEqualTo(5);
        }
        { // middle page
            final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(75L, PAGE_SIZE, 106L, listOfSize(PAGE_SIZE));
            assertThat(queryResult.getPageIndex()).isEqualTo(3);
            assertThat(queryResult.getTotalPages()).isEqualTo(5);
        }

        { // last page with few products
            final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(100L, PAGE_SIZE, 106L, listOfSize(6L));
            assertThat(queryResult.getPageIndex()).isEqualTo(4);
            assertThat(queryResult.getTotalPages()).isEqualTo(5);
        }
        { // last page with max products
            final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(100L, PAGE_SIZE, 125L, listOfSize(PAGE_SIZE));
            assertThat(queryResult.getPageIndex()).isEqualTo(4);
            assertThat(queryResult.getTotalPages()).isEqualTo(5);
        }
    }

    @Test
    public void getPagesCountForEmptyResults() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of( 80L, PAGE_SIZE, 0L, listOfSize(0L));
        assertThat(queryResult.getTotalPages()).isEqualTo(0);
    }

    @Test
    public void lastFilledOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(TOTAL - PAGE_SIZE, PAGE_SIZE, TOTAL, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isTrue();
    }

    @Test
    public void lastNotFullyFilledOfManyResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(95L - PAGE_SIZE, PAGE_SIZE, 95L, listOfSize(PAGE_SIZE));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isTrue();
    }

    @Test
    public void lastOneResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(100L, PAGE_SIZE, 101L, listOfSize(1L));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isTrue();
    }

    @Test
    public void beforeLastOneResult() throws Exception {
        final PagedQueryResult<Integer> queryResult = PagedQueryResult.of(99L, PAGE_SIZE, 101L, listOfSize(1L));
        assertThat(queryResult.isFirst()).isFalse();
        assertThat(queryResult.isLast()).isFalse();
    }

    @Test
    public void testEquals() throws Exception {
        assertThat(a).isEqualTo(a);
        assertThat(a).isNotEqualTo(b);
        assertThat(PagedQueryResult.of(listOfSize(1L))).isEqualTo(PagedQueryResult.of(listOfSize(1L)));
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
        assertThat(PagedQueryResult.of(0L, PAGE_SIZE, 500L, listOfSize(PAGE_SIZE)).withOffset(5L)).
                isEqualTo(PagedQueryResult.of(5L, PAGE_SIZE, 500L, listOfSize(PAGE_SIZE)));
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

    private List<Integer> listOfSize(final Long size) {
        if (size <= 0) {
            return Collections.emptyList();
        } else {
            return IntStream.range(0, size.intValue()).boxed().collect(Collectors.toList());
        }
    }
}
