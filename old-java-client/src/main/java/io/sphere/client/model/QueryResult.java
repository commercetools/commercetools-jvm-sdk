package io.sphere.client.model;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

/** Result of a query to the Sphere backend. */
public class QueryResult<T> {
    private int offset;
    private int count;
    private int total;
    @Nonnull List<T> results = new ArrayList<T>();

    public QueryResult(int skipped, int count, int total, Collection<T> results) {
        this.offset = skipped;
        this.count = count;
        this.total = total;
        this.results = new ArrayList<T>(results);
    }

    // for JSON deserializer
    private QueryResult() { }

    /** Offset from the start, begins at 0. */
    public int getOffset() { return offset; }
    /** The actual number of results returned. */
    public int getCount() { return count; }
    /** The total number of results matching the query. */
    public int getTotal() { return total; }
    /** The total results in the result set. */
    @Nonnull public List<T> getResults() { return results; }

    @Override
    public String toString() {
        return "QueryResult{" +
                "offset=" + offset +
                ", count=" + count +
                ", total=" + total +
                ", results=" + results +
                '}';
    }
}
