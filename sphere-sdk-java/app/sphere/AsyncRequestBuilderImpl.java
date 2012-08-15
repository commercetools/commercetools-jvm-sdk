package sphere;

import play.libs.F;
import play.libs.WS;
import sphere.util.ReadJson;
import org.codehaus.jackson.type.TypeReference;

/** Request builder that returns a promise with the result. */
class AsyncRequestBuilderImpl<T> extends RequestBuilderBase<F.Promise<T>> implements AsyncRequestBuilder<T> {

    TypeReference<T> jsonParserTypeRef;

    public AsyncRequestBuilderImpl(WS.WSRequestHolder requestHolder, TypeReference<T> jsonParserTypeRef) {
        super(requestHolder);
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    public F.Promise<T> get() {
        if (Log.isTraceEnabled()) {
            // TODO log the full raw request URL, and ideally also timing (when switching to ning)
            //Log.trace(this.requestHolder.getRequestURL());
        }
        return this.requestHolder.get().map(new ReadJson<T>(jsonParserTypeRef));
    }
}
