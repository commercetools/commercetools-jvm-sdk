package io.sphere.client.shop.model;

/** A {@link Customer} can be a member of a customer group (e.g. reseller, gold member).
 * A customer group can be used in price calculations with special prices being assigned to certain customer groups. */
public class CustomerGroup {
    private String id;
    private int version;
    private String name;

    // for JSON deserializer
    private CustomerGroup() {}

    public String getId() { return id; }

    public int getVersion() { return version; }

    public String getName() { return name; }
}
