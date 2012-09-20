package de.commercetools.sphere.client.shop.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/** Shopping cart that is stored persistently in the backend.
 *  Once the cart is created on the backend, it can be fetched by id
 *  and an instance of a PersistentCart is returned.
 *
 *  All the read methods of this class read from memory and all the
 *  methods making changes to this instance also persist the changes on the backend. */
@JsonIgnoreProperties(ignoreUnknown=true) // temp until this class fully matches the json returned by the backend
public class Cart extends LineItemContainer {
    private String currencyCode;

    // for JSON deserializer
    private Cart() {}

    /** Returns an empty dummy cart.
     *  This is useful if you want to work with a dummy instance of a cart that does not exist on the backend.
     *  To modify a cart, it has to exist on the backend, see {@link de.commercetools.sphere.client.shop.Carts}. */
    public static Cart empty() {
        return new Cart();
    }

    /** The currency of this cart. */
    public String getCurrencyCode() {
        return currencyCode;
    }
}
