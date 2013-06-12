package io.sphere.client.shop.model;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import javax.annotation.Nonnull;
import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Reference;
import io.sphere.client.model.ReferenceId;
import io.sphere.client.model.VersionedId;
import org.codehaus.jackson.annotate.JsonProperty;

/** A shipping method defines a specific way of shipping, with different rates for different geographic locations. Example
 *  shipping methods are "DHL", "DHL Express" and "UPS".
 *  TODO: add a link to the shipping method example in the project-ws wiki.
 *  */
public class ShippingMethod {
    private static final String typeId = "shipping-method";
    @Nonnull private String id;
    @JsonProperty("version") private int version;
    @Nonnull private String name;
    @Nonnull private String description = "";
    @Nonnull private Reference<TaxCategory> taxCategory = EmptyReference.create("taxCategory");
    @JsonProperty("isDefault") private boolean isDefault;
    @Nonnull private List<ZoneRate> zoneRates = new ArrayList<ZoneRate>();

    public static ReferenceId<ShippingMethod> reference(String id) {
        return ReferenceId.create(typeId, id);
    }
    
    // for JSON deserializer
    protected ShippingMethod() {}

    /** The unique id. */
    @Nonnull public String getId() { return id; }

    /** The {@link #getId() id} plus version. */
    @Nonnull public VersionedId getIdAndVersion() { return VersionedId.create(id, version); }

    /** The name of the shipping method. */
    @Nonnull public String getName() { return name; }

    /** The description of the shipping method. */
    @Nonnull public String getDescription() { return description; }

    /** The tax category of the shipping method. */
    @Nonnull public Reference<TaxCategory> getTaxCategory() { return taxCategory; }

    /** true if this is a default shipping method. Only one shipping method can be default. */
    public boolean isDefault() { return isDefault; }

    /** The list of shipping rates per zone. */
    @Nonnull public List<ZoneRate> getZoneRates() { return zoneRates; }
    
    /** Determines a shipping rate for a location and currency. If the given location contains a state and a shipping rate
     * for the country and state is not found then the method tries to find a shipping rate for a country (without state). 
     * Returns null if no shipping rate can be determined. */
    public ShippingRate shippingRateForLocation(Location location, Currency currency) {
        ZoneRate zoneRate = zoneRateForLocation(location);
        if (zoneRate == null && !(location.getState() == null || location.getState().isEmpty())) 
            zoneRate = zoneRateForLocation(new Location(location.getCountry()));
        if (zoneRate != null) 
            return zoneRate.getShippingRate(currency);
        return null;
    }
    
    private ZoneRate zoneRateForLocation(Location location) {
        for (ZoneRate zR: zoneRates) {
            if (zR.getZone().isExpanded()) {
                List<Location> locations = zR.getZone().get().getLocations();
                for (Location l: locations)
                    if (l.matches(location)) return zR;
            }
        }
        return null;
    }
}
