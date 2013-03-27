package io.sphere.client.shop.model;


import io.sphere.client.model.Money;

import java.util.ArrayList;
import java.util.List;

/** The cart price with product tax rates applied. */
public class TaxedPrice {
    private Money totalNet;
    private Money totalGross;
    private List<LineItem> taxProtions = new ArrayList<LineItem>();  // initialize to prevent NPEs

    // for JSON deserializer
    private TaxedPrice() {}

    /** The total net price. (Total price without taxes). */
    public Money getTotalNet() { return totalNet; }

    /** The total gross price. (Total price with taxes)  */
    public Money getTotalGross() { return totalGross; }

    /** The portions for individual tax rates. The sum of all tax portion amounts + totalNet = totalGross. */
    public List<LineItem> getTaxProtions() { return taxProtions; }
}
