package io.sphere.sdk.client;

import java.util.List;

public final class PagedQueryResult<T> {
    private final int offset;
    private final int count;
    private final int total;
    private final List<T> results;

    private PagedQueryResult(final int offset, final int count, final int total, final List<T> results) {
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
