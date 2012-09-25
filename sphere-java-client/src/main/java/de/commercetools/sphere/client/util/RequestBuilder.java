package de.commercetools.sphere.client.util;

import de.commercetools.sphere.client.BackendException;
import com.google.common.util.concurrent.ListenableFuture;

/** Represents a query request to the Sphere backend.
 *  Use {@link #fetch} or {@link #fetchAsync} to execute the request. */
public interface RequestBuilder<T> {
    /** Executes the request to the Sphere backend and returns a result. */
    T fetch();

    /** Executes the request in a non-blocking way and returns a future that provides a notification
     *  when the results from the Sphere backend arrive. */
    ListenableFuture<T> fetchAsync();

    /** Sets the page number for paging through results. Page numbers start at zero. */
    RequestBuilder<T> page(int page);

    /** Sets the size of a page for paging through results. When page size is not set, the default of 10 is used. */
    RequestBuilder<T> pageSize(int pageSize);

    /** Requests references to be expanded in the returned JSON documents.
     *  Expanded references contain the full target objects they link to.
     *
     *  For example, by expanding a path 'owner' in the following document
     *  {{{
     *  {
     *    "name": "Product A"
     *    "vendor": {
     *      "typeId": "vendor",
     *      "id": "7ba61480-6a72-4a2a-a72e-cd39f75a7ef2"
     *    }
     *  }
     *  }}}
     *
     *  we obtain:
     *
     *  {{{
     *  {
     *    "name": "Product A"
     *    "owner": {
     *      typeId: "vendor",
     *      id: "7ba61480-6a72-4a2a-a72e-cd39f75a7ef2"
     *      obj: {
     *        "name": "Vendor A",
     *        "imageURLs": []"
     *      }
     *    }
     *  }
     *  }}}
     *
     *  @param paths The paths to be expanded, such as 'vendor', 'categories[*]' or 'variants[*].vendor'. */
    RequestBuilder<T> expand(String... paths);
}
