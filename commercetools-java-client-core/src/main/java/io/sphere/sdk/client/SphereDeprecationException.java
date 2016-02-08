package io.sphere.sdk.client;

import io.sphere.sdk.models.SphereException;

import java.util.List;

public class SphereDeprecationException extends SphereException {
    static final long serialVersionUID = 0L;

    public SphereDeprecationException(final List<String> notices) {
        notices.forEach(note -> addNote("deprecation warning: " + note));
    }
}
