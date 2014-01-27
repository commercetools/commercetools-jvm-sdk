package io.sphere.client.shop.model;

import net.jcip.annotations.Immutable;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Strings.nullToEmpty;


/** TrackingData is some info about the delivery (like a DHL tracking number) which is useful to keep an eye
 * on your delivery, view its status etc.*/
@Immutable
public class TrackingData {

    private String trackingId = "";
    private String carrier = "";
    private String provider = "";
    private String providerTransaction = "";
    private boolean isReturn = false;

    public TrackingData(String trackingId, String carrier, String provider, String providerTransaction, boolean isReturn) {
        this.provider = firstNonNull(provider, "");
        this.providerTransaction = firstNonNull(providerTransaction, "");
        this.trackingId = firstNonNull(trackingId, "");
        this.carrier = firstNonNull(carrier, "");
        this.isReturn = isReturn;
    }

    /**
     * Creates TrackingData item with isReturn=false.
     */
    public TrackingData(String trackingId, String carrier) {
        this(trackingId, carrier, "", "", false);
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
    public String getCarrier() { return nullToEmpty(carrier); }

    /** true, if the parcel is on the way back to the merchant and false if it is the delivery to the customer. */
    public boolean isReturn() {
        return isReturn;
    }

    public String getProvider() {
        return provider;
    }

    public String getProviderTransaction() {
        return providerTransaction;
    }

    @Override
    public String toString() {
        return "TrackingData{" +
                "trackingId='" + trackingId + '\'' +
                ", carrier='" + carrier + '\'' +
                ", provider='" + provider + '\'' +
                ", providerTransaction='" + providerTransaction + '\'' +
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

    public static class Builder {
        private String trackingId;
        private String carrier;
        private String provider;
        private String providerTransaction;
        private boolean isReturn = false;

        public Builder() {
        }

        public Builder(final String trackingId) {
            setTrackingId(trackingId);
        }

        public Builder setTrackingId(String trackingId) {
            this.trackingId = trackingId;
            return this;
        }

        public Builder setCarrier(String carrier) {
            this.carrier = carrier;
            return this;
        }

        public Builder setProvider(String provider) {
            this.provider = provider;
            return this;
        }

        public Builder setProviderTransaction(String providerTransaction) {
            this.providerTransaction = providerTransaction;
            return this;
        }

        public Builder setReturn(boolean isReturn) {
            this.isReturn = isReturn;
            return this;
        }

        public TrackingData build() {
            return new TrackingData(trackingId, carrier, provider, providerTransaction, isReturn);
        }
    }
}
