package io.sphere.sdk.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation specifies the type name of a message type.
 *
 * Only concrete message types must be annotated with this annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageTypeInfo {
    /**
     * The message type name.
     *
     * @return the message type name
     */
    String type();

    String resourceType();
}
