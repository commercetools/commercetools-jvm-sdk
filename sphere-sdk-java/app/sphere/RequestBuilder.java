package sphere;

/** Represents a request to the Sphere backend.
 *  Use {@link #get()} to execute the request and obtain results. */
public interface RequestBuilder<T> {
    /** Executes the request to the Sphere backend and returns result. */
    public T get();

    /** Requests references to be expanded in the returned JSON documents.
     *  Expanded references contain the full target objects they link to.
     *
     *  @param paths The paths to be expanded, such as 'vendor', 'categories[*]' or 'variants[*].vendor'. */
    public RequestBuilder<T> expand(String... paths);
}
