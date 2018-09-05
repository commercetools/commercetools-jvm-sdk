
package io.sphere.sdk.products.messages;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.messages.GenericMessageImpl;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.products.Product;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;


abstract class AbstractImageDeletionMessage extends GenericMessageImpl<Product> {


    @Nullable
    private final String[] removedImageUrls;


    AbstractImageDeletionMessage(final String id, final Long version, final ZonedDateTime createdAt, final ZonedDateTime lastModifiedAt, final JsonNode resource, final Long sequenceNumber, final Long resourceVersion, final String type, final UserProvidedIdentifiers resourceUserProvidedIdentifiers, final String[] removedImageUrls) {
        super(id, version, createdAt, lastModifiedAt, resource, sequenceNumber, resourceVersion, type,resourceUserProvidedIdentifiers , Product.class);
        this.removedImageUrls = removedImageUrls;
    }

    /**
     * List of images which were removed with this action.
     *
     * @return Array of URLs of removed images as string
     */
    public String[] getRemovedImageUrls() {
        return removedImageUrls;
    }
}
