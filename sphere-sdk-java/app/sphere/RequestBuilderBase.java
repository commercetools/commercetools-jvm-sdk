package sphere;

import java.util.List;
import java.util.ArrayList;

/** @inheritdoc */
abstract class RequestBuilderBase<T> implements RequestBuilder<T> {
    /** @inheritdoc */
    public abstract T get();
    
    protected List<String> expandPaths = new ArrayList<String>();
    /** @inheritdoc */
    public RequestBuilder<T> expand(String... paths) {
        for (String path: paths) {
          expandPaths.add(path);
        }
        return this;
    }
}
