package io.sphere.client.shop.model;

import net.jcip.annotations.Immutable;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Strings.isNullOrEmpty;


/** TrackingData is some info about the delivery (like a DHL tracking number) which is useful to keep an eye
 * on your delivery, view its status etc.*/
@Immutable
public class TrackingData {

    private String trackingId;
    private String carrier;
    private boolean isReturn;

    public TrackingData(String trackingId, String carrier, boolean isReturn) {
        if (isNullOrEmpty(trackingId)) {
            throw new IllegalArgumentException("Min length of trackingId is 1.");
        }
        this.trackingId = trackingId;
        this.carrier = firstNonNull(carrier, "");
        this.isReturn = isReturn;
    }

    /**
     * Creates TrackingData item with isReturn=false.
     */
    public TrackingData(String trackingId, String carrier) {
        this(trackingId, carrier, false);
    }

    /**
     * Creates TrackingData item with empty carrier field and isReturn=false.
     */
    public TrackingData(String trackingId) {
        this(trackingId, "");
    }

    //for JSON mapper
    @SuppressWarnings("unused")
    protected TrackingData() {
    }

    /** parcel tracking number */
    public String getTrackingId() {
        return trackingId;
    }

    /** parcel shipping company */
    public String getCarrier() {
        return carrier;
    }

    /** true, if the parcel is on the way back to the merchant and false if it is the delivery to the customer. */
    public boolean isReturn() {
        return isReturn;
    }

    @Override
    public String toString() {
        return "TrackingData{" +
                "trackingId='" + trackingId + '\'' +
                ", carrier='" + carrier + '\'' +
                ", isReturn=" + isReturn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackingData that = (TrackingData) o;

        if (isReturn != that.isReturn) return false;
        if (carrier != null ? !carrier.equals(that.carrier) : that.carrier != null) return false;
        if (!trackingId.equals(that.trackingId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = trackingId.hashCode();
        result = 31 * result + (carrier != null ? carrier.hashCode() : 0);
        result = 31 * result + (isReturn ? 1 : 0);
        return result;
    }
}
