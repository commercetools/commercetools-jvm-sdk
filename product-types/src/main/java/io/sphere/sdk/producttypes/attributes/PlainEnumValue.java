package io.sphere.sdk.producttypes.attributes;

import io.sphere.sdk.models.Base;

public class PlainEnumValue extends Base {
    private final String key;
    private final String label;

    private PlainEnumValue(final String key, final String label) {
        this.key = key;
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    /**
     *
     * @param key The key of the value used as a programmatic identifier, e.g. in facets & filters.
     * @param label A descriptive label of the value.
     */
    public static PlainEnumValue of(final String key, final String label) {
        return new PlainEnumValue(key, label);
    }
}
