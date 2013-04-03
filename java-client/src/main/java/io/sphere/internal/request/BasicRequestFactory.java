package io.sphere.internal.request;

/** Creates GET and POST requests. */
public interface BasicRequestFactory {
    /** Creates a basic GET request. */
    public <T> RequestHolder<T> createGet(String url);

    /** Creates a basic POST request. */
    public <T> RequestHolder<T> createPost(String url);
}