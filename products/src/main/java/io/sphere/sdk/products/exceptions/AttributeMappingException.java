package io.sphere.sdk.products.exceptions;

import io.sphere.sdk.products.AttributeMapper;

import static java.lang.String.format;

public class AttributeMappingException extends RuntimeException {
    private static final long serialVersionUID = 4954925590077093840L;

    public AttributeMappingException(final Throwable cause) {
        super(cause);
    }

    public <T> AttributeMappingException(final Object objectWithAttributes, final String attributeName, final AttributeMapper<T> mapper, final Throwable cause) {
        super(format("%s does not contain an attribute '%s' which can be mapped with %s.", objectWithAttributes, attributeName, mapper), cause);
    }
}
