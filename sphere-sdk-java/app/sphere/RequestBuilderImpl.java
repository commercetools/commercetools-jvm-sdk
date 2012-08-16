package sphere;

import play.libs.F;
import play.libs.WS;
import sphere.util.ReadJson;
import org.codehaus.jackson.type.TypeReference;

import java.util.List;
import java.util.ArrayList;

/** @inheritdoc */
class RequestBuilderImpl<T> implements RequestBuilder<T> {

    protected WS.WSRequestHolder requestHolder;
    TypeReference<T> jsonParserTypeRef;

    protected RequestBuilderImpl(WS.WSRequestHolder requestHolder, TypeReference<T> jsonParserTypeRef) {
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    /** @inheritdoc */
    public T fetch() {
        return fetchAsync().get();
    }

    /** @inheritdoc */
    public F.Promise<T> fetchAsync() {
        if (Log.isTraceEnabled()) {
            // TODO log the full raw request URL, and ideally also timing (when switching to ning)
            //Log.trace(this.requestHolder.getRequestURL());
        }
        return this.requestHolder.get().map(new ReadJson<T>(jsonParserTypeRef));
    }

    /** @inheritdoc */
    public RequestBuilder<T> expand(String... paths) {
        for (String path: paths) {
            requestHolder.setQueryParameter("expand", path);
        }
        return this;
    }
}
