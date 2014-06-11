package io.sphere.sdk.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import io.sphere.sdk.utils.ListUtils;

import java.util.Collections;
import java.util.List;

public final class PagedQueryResult<T> {
    private final int offset;
    private final int count;
    private final int total;
    private final List<T> results;

    @JsonCreator
    PagedQueryResult(@JsonProperty("offset") final int offset, @JsonProperty("count") final int count,
                     @JsonProperty("total") final int total, @JsonProperty("results") final List<T> results) {
        this.offset = offset;
        this.count = count;
        this.total = total;
        this.results = results;
    }

    public int getOffset() {
        return offset;
    }

    public int getCount() {
        return count;
    }

    public int getTotal() {
        return total;
    }

    public List<T> getResults() {
        return results;
    }

    public Optional<T> headOption() {
        return ListUtils.headOption(getResults());
    }

    public static <T> PagedQueryResult<T> empty() {
        return new PagedQueryResult<T>(0, 0, 0, Collections.<T>emptyList());
    }

    @SuppressWarnings("rawtypes")//at runtime generic type is not determinable
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagedQueryResult that = (PagedQueryResult) o;

        if (count != that.count) return false;
        if (offset != that.offset) return false;
        if (total != that.total) return false;
        if (results != null ? !results.equals(that.results) : that.results != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = offset;
        result = 31 * result + count;
        result = 31 * result + total;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PagedQueryResponse{" +
                "offset=" + offset +
                ", count=" + count +
                ", total=" + total +
                ", results=" + results +
                '}';
    }
}
