package io.sphere.client.shop.model;

import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.annotation.Nonnull;

/** A verification token with limited lifetime used when
 * {@link io.sphere.client.shop.CustomerService#createPasswordResetToken(String) resetting customer password} and when
 * {@link io.sphere.client.shop.CustomerService#createEmailVerificationToken(io.sphere.client.model.VersionedId, int) verifying customer email}.
 *
 * <p>Use {@link #getValue()} to identify the token. */
@Immutable
// The backend token has these helper fields which are not exposed for now.
@JsonIgnoreProperties({"id", "createdAt", "expiresAt"})
public class CustomerToken {
    @Nonnull private String customerId;
    @Nonnull private String value;

    // for JSON deserializer and tests
    protected CustomerToken() { }

    /** Id of the customer for who the token was created. */
    @Nonnull public String getCustomerId() { return customerId; }

    /** The value of the token.
     *
     * <p>You should include this value as-is in URLs sent in emails.
     * <p>You can pass this as-is to
     * {@link io.sphere.client.shop.CustomerService#byToken(String) CustomerService.byToken}. */
    @Nonnull public String getValue() { return value; }
}
