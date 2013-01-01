package de.commercetools.sphere.client.shop.model;


/** Availability of a variant in a product catalog. */
public class VariantAvailability {
    private boolean isOnStock;
    private int restockableInDays;

    // for JSON deserializer
    protected VariantAvailability() { }

    public boolean isOnStock() {
        return isOnStock;
    }

    public int getRestockableInDays() {
        return restockableInDays;
    }
}
