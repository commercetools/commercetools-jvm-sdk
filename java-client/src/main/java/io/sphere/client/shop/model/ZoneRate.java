package io.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import javax.annotation.Nonnull;
import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Reference;

/** ZoneRate defines shipping rates (prices) for a specific zone. Shipping rates is an array because the rates for 
 * different currencies can be defined. Used in {@link ShippingMethod}. */
public class ZoneRate {
    @Nonnull private Reference<Zone> zone = EmptyReference.create("zone");
    @Nonnull private List<ShippingRate> shippingRates = new ArrayList<ShippingRate>();

    // for JSON deserializer
    private ZoneRate() {}

    /** The zone. */
    @Nonnull public Reference<Zone> getZone() { return zone; }

    /** The shipping rates for the zone. The list does not contain two shipping rates with the same currency. */
    @Nonnull public List<ShippingRate> getShippingRates() { return shippingRates; }
    
    /** @return a shipping rate for a specific currency. 
     *          null if a rate for the given currency does not exist. */
    public ShippingRate getShippingRate(Currency currency) {
        for (ShippingRate rate: shippingRates) {
            if (rate.getPrice().getCurrencyCode().equals(currency.getCurrencyCode()))
                return rate;
        }
        return null;
    }

    @Override
    public String toString() {
        return "ZoneRate{" +
                "zone=" + zone +
                ", shippingRates=" + shippingRates +
                '}';
    }
}
