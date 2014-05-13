package io.sphere.internal.request;

/** Creates GET, POST and DELETE requests. */
public interface BasicRequestFactory {
    /** Creates a basic GET request. */
    public <T> RequestHolder<T> createGet(String url);

    /** Creates a basic POST request. */
    public <T> RequestHolder<T> createPost(String url);

    /** Creates a basic DELETE request. */
    public <T> RequestHolder<T> createDelete(String url);
}