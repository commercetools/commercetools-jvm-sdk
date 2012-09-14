package de.commercetools.sphere.client.shop.model;

import java.util.List;
import java.util.ArrayList;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/** Shopping cart that is stored persistently in the backend.
 *  Once the cart is created on the backend, it can be fetched by id
 *  and an instance of a PersistentCart is returned.
 *
 *  All the read methods of this class read from memory and all the
 *  methods making changes to this instance also persist the changes on the backend. */
@JsonIgnoreProperties(ignoreUnknown=true) // temp until this class fully matches the json returned by the backend
public class Cart {
    private String id;
    private String version;
    private List<LineItem> lineItems = new ArrayList<LineItem>();  // initialize to prevent NPEs

    // for JSON deserializer
    private Cart() {}

    /** Gets the the items in this cart directly from this instance, without making a query to the backend. */
    public List<LineItem> getLineItems() { return lineItems; }
}
