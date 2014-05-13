package io.sphere.internal.errors;

import com.google.common.base.Function;
import io.sphere.client.SphereError;
import io.sphere.client.exceptions.SphereBackendException;
import io.sphere.client.exceptions.SphereException;

import static io.sphere.internal.util.Util.getSingleError;

public class ErrorHandling {
    public static Function<SphereBackendException, SphereException> handleDuplicateField(final String fieldName, final SphereException exceptionToEmit) {
        return new Function<SphereBackendException, SphereException>() {
            public SphereException apply(SphereBackendException e) {
                SphereError.DuplicateField err = getSingleError(e, SphereError.DuplicateField.class);
                if (err != null && err.getField().equals(fieldName)) {
                    return exceptionToEmit;
                }
                return null;
            }
        };
    }
}
