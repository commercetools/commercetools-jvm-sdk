package io.sphere.sdk.products;

import io.sphere.sdk.products.attributes.Attribute;
import io.sphere.sdk.products.attributes.AttributeDraft;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * Creates {@link AttributeContainer}s as input objects.
 *
 * <p>May only required for nested attributes.</p>
 */
public interface AttributeContainerDraft {

    static AttributeContainer of(final AttributeDraft ... attributeDrafts) {
        return of(asList(attributeDrafts));
    }

    static AttributeContainer of(final List<AttributeDraft> attributeDrafts) {
        final List<Attribute> attributes = attributeDrafts.stream()
                .map(attributeDraft -> Attribute.of(attributeDraft.getName(), attributeDraft.getValue()))
                .collect(toList());
        return AttributeContainer.of(attributes);
    }
}
