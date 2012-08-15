package sphere;

import org.codehaus.jackson.type.TypeReference;
import play.libs.WS;

import java.util.List;
import java.util.ArrayList;

/** @inheritdoc */
abstract class RequestBuilderBase<T> implements RequestBuilder<T> {

    protected WS.WSRequestHolder requestHolder;

    protected RequestBuilderBase(WS.WSRequestHolder requestHolder) {
        this.requestHolder = requestHolder;
    }

    /** @inheritdoc */
    public abstract T get();
    
    /** @inheritdoc */
    public RequestBuilder<T> expand(String... paths) {
        for (String path: paths) {
          requestHolder.setQueryParameter("expand", path);
        }
        return this;
    }
}
