package sphere;

import play.libs.F;
import play.libs.WS;
import sphere.util.ReadJson;
import org.codehaus.jackson.type.TypeReference;
import com.google.common.base.Function;

/** Request builder that returns a promise with the result. */
class AsyncRequestBuilderImpl<T> extends RequestBuilderBase<F.Promise<T>> implements AsyncRequestBuilder<T> {

    private WS.WSRequestHolder requestHolder;
    private TypeReference<T> jsonParserTypeRef;
    
    public AsyncRequestBuilderImpl(WS.WSRequestHolder requestHolder, TypeReference<T> jsonParserTypeRef) {
        this.requestHolder = requestHolder;
        this.jsonParserTypeRef = jsonParserTypeRef;
    }

    public F.Promise<T> get() {
        return this.requestHolder.get().map(new ReadJson<T>(jsonParserTypeRef));
    }
}
