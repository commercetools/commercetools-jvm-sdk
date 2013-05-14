package io.sphere.client.shop.model;

import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.annotation.Nonnull;

/** A verification token with limited lifetime used when resetting
 *  customer password and when verifying customer email.
 *
 * <p>Use {@link #getValue()} to identify the token.
 *
 * <p>See {@link io.sphere.client.shop.CustomerService#createPasswordResetToken(String)}
 * and {@link io.sphere.client.shop.CustomerService#createEmailVerificationToken(String, int, int)}. */
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
     * <p>You should include this value as-is in a URL sent by email to the
     * customer, and when the customer clicks the link, pass this value  to
     * {@link io.sphere.client.shop.CustomerService#byToken(String)}. */
    @Nonnull public String getValue() { return value; }
}
