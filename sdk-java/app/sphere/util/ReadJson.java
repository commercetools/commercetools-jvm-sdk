package sphere.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import play.libs.F;
import play.libs.WS;

import java.util.ArrayList;

/** Callback function for the {@link play.libs.WS} HTTP client that maps a JSON string response to a typed instance. */
public class ReadJson<T> implements F.Function<WS.Response, T> {

    /** Interestingly enough, if this is a subclass of TypeReference&lt;T&gt;, the actual used type T
     *  is available using reflection. See http://www.artima.com/weblogs/viewpost.jsp?thread=208860 */
    TypeReference<T> typeRef;

    /**
     * Creates new instance of ReadJson.
     * <p>
     * Example:
     * <pre>
     * {@code
     * new ReadJson<QueryResult<Product>>(new TypeReference<QueryResult<Product>>() { })
     * }
     * </pre>
     * @param typeRef Pass in an empty subclass of TypeReference&lt;T&gt; where T is exactly the required result type.
     *                This is to get around Java type erasure.
     * */
    public ReadJson(TypeReference<T> typeRef) {
        this.typeRef = typeRef;
    }

    @Override
    public T apply(WS.Response resp) throws Throwable {
        try {
            if (resp.getStatus() != 200) {
                throw new RuntimeException(String.format("The backend returned an error response [%s]:\n%s", resp.getStatus(), resp.getBody()));
            } else {
                ObjectMapper jsonParser = new ObjectMapper();
                return jsonParser.readValue(resp.getBody(), typeRef);
            }
        } catch(Exception e) {
            // Workaround for exception swallowing bug in Play 2.0 - at least print the stack trace
            // (fixed in master, so this can be removed with the next release of Play)
            e.printStackTrace();
            throw e;
        }
    }
}
