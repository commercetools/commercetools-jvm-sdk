package io.sphere.sdk.products;

import io.sphere.sdk.attributes.AttributeDraft;

import java.util.List;

import static java.util.Arrays.asList;

public class AttributeContainerDraft {
    private final List<AttributeDraft> attributes;

    private AttributeContainerDraft(final List<AttributeDraft> attributes) {
        this.attributes = attributes;
    }

    public static AttributeContainerDraft of(final AttributeDraft ... attributeDrafts) {
        return of(asList(attributeDrafts));
    }

    public static AttributeContainerDraft of(final List<AttributeDraft> attributes) {
        return new AttributeContainerDraft(attributes);
    }

    public List<AttributeDraft> getAttributes() {
        return attributes;
    }
}
