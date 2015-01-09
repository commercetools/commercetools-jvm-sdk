package io.sphere.sdk.customobjects;

import io.sphere.sdk.models.Base;

import static java.lang.String.format;

/**
 * Holder for container and key for a custom object.
 */
public abstract class CustomObjectKey extends Base {
    private static final String keyContainerRegex = "[-_~.a-zA-Z0-9]+";
    private final String container;
    private final String key;

    protected CustomObjectKey(final String container, final String key) {
        this.container = validatedContainer(container);
        this.key = validatedKey(key);
    }

    public String getContainer() {
        return container;
    }

    public String getKey() {
        return key;
    }

    public static String validatedKey(final String key) {
        return validatedKey("key", key);
    }

    public static String validatedContainer(final String container) {
        return validatedKey("container", container);
    }

    private static String validatedKey(final String name, final String keyOrContainer) {
        if (!keyOrContainer.matches(keyContainerRegex)) {
            final String message = format("The %s \"%s\" does not have the correct format. Key and container need to match %s.", name, keyOrContainer, keyContainerRegex);
            throw new IllegalArgumentException(message);
        }
        return keyOrContainer;
    }
}
