package io.sphere.sdk.products;

import io.sphere.sdk.attributes.Attribute;
import io.sphere.sdk.attributes.AttributeDraft;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class AttributeContainerDraft {
    public static AttributeContainer of(final AttributeDraft ... attributeDrafts) {
        return of(asList(attributeDrafts));
    }

    public static AttributeContainer of(final List<AttributeDraft> attributeDrafts) {
        final List<Attribute> attributes = attributeDrafts.stream()
                .map(attributeDraft -> Attribute.of(attributeDraft.getName(), attributeDraft.getValue()))
                .collect(toList());
        return AttributeContainer.of(attributes);
    }
}
