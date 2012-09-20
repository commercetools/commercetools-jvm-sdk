package de.commercetools.sphere.client.shop.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/** Shopping cart that is stored persistently in the backend.
 *  Once the cart is created on the backend, it can be fetched by id
 *  and an instance of a PersistentCart is returned.
 *
 *  All the read methods of this class read from memory and all the
 *  methods making changes to this instance also persist the changes on the backend. */
@JsonIgnoreProperties(ignoreUnknown=true) // temp until this class fully matches the json returned by the backend
public class Cart extends OrderLike {
    private String currencyCode;

    // for JSON deserializer
    private Cart() {}

    /** The currency of this cart. */
    public String getCurrencyCode() {
        return currencyCode;
    }

}
