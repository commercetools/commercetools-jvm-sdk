package sphere.model;

import java.util.List;
import java.util.ArrayList;

/** Standard query result (a collection of objects) returned by the Sphere backend. */
public class QueryResult<T> {
    int skipped;
    int count;
    int total;
    List<T> results = new ArrayList<T>();

    public QueryResult(int skipped, int count, int total) {
        this.skipped = skipped;
        this.count = count;
        this.total = total;
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
