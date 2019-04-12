package io.sphere.sdk.extensions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;

/**
 * The Authorization header will be set to the content of headerValue.
 * The authentication scheme (e.g. Basic or Bearer) should be included in the headerValue.
 *
 * As an example for Basic Authentication, the @{link {@link #getHeaderValue()}} should be set to <code>Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==</code>.
 */
@ResourceValue
@JsonDeserialize(as = AuthorizationHeaderAuthenticationImpl.class)
@HasBuilder(factoryMethods = @FactoryMethod(parameterNames = "headerValue"))
public interface AuthorizationHeaderAuthentication extends HttpDestinationAuthentication {
    /**
     * Partially hidden on retrieval.
     *
     * @return the authorization header value
     */
    String getHeaderValue();
}
