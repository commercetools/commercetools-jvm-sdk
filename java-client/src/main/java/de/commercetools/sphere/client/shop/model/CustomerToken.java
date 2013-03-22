package io.sphere.client.shop.model;

import java.util.UUID;

import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;

@Immutable
@JsonIgnoreProperties("id")
public class CustomerToken {
    private String id;
    private UUID customerId;
    private DateTime createdAt;
    private DateTime expiresAt;
    private String value;

    // for JSON deserializer
    protected CustomerToken() { }

    public UUID getCustomerId() { return customerId; }

    public DateTime getCreatedAt() { return createdAt; }

    public DateTime getExpiresAt() { return expiresAt; }

    public String getValue() { return value; }
}
