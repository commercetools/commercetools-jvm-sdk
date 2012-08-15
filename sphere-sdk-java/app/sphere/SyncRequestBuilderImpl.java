package sphere;

import play.libs.F;

/** Convenience request builder that waits for the asynchronous result and returns it. */
class SyncRequestBuilderImpl<T> implements RequestBuilder<T> {

    private AsyncRequestBuilder<T> async;
    
    SyncRequestBuilderImpl(AsyncRequestBuilder<T> async) {
        this.async = async;
    }

    /** Waits for the promise to complete. */
    public T get() {
        return this.async.get().get();
    }

    public RequestBuilder<T> expand(String... paths) {
        this.async.expand(paths);
        return this;
    }
}
