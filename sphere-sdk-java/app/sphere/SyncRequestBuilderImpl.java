package sphere;

import play.libs.F;

/** Convenience request builder that waits for the asynchronous result and returns it. */
class SyncRequestBuilderImpl<T> extends RequestBuilderBase<T> {

    private AsyncRequestBuilder<T> async;
    
    SyncRequestBuilderImpl(AsyncRequestBuilder<T> async) {
        this.async = async;
    }

    /** Waits for the promise to complete. */
    public T get() {
        return this.async.get().get();
    }
}
