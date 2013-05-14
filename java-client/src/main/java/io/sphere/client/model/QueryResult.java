package io.sphere.client.model;


import org.codehaus.jackson.annotate.JsonProperty;

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

    public int getOffset() { return offset; }
    public int getCount() { return count; }
    public int getTotal() { return total; }
    @Nonnull public List<T> getResults() { return results; }
}
