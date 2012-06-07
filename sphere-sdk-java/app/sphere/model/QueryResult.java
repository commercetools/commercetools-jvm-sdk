package sphere.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

/** Standard query result (a collection of objects) returned by the Sphere backend. */
public class QueryResult<T> {
    int skipped;  // TODO use public final, rename offset
    int count;
    int total;
    List<T> results = new ArrayList<T>();

    public QueryResult(int skipped, int count, int total, Collection<T> results) {
        this.skipped = skipped;
        this.count = count;
        this.total = total;
        this.results = new ArrayList<T>(results);
    }

    // for JSON deserializer
    private QueryResult() { }

    public int getSkipped() {
        return skipped;
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
}
