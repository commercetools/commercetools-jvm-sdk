package io.sphere.client.shop.model;

import com.google.common.base.Optional;
import org.joda.time.DateTime;

import javax.annotation.Nonnull;

/**
 * Represents a package to or from a customer within a {@link Delivery}.
 */
public final class Parcel {
    @Nonnull private String id = "";
    @Nonnull private DateTime createdAt;
    private ParcelMeasurements measurements;
    private TrackingData trackingData;

    //for JSON mapper
    public Parcel() {
    }

    @Nonnull
    public String getId() {
        return id;
    }

    @Nonnull
    public DateTime getCreatedAt() {
        return createdAt;
    }

    public Optional<ParcelMeasurements> getMeasurements() {
        return Optional.fromNullable(measurements);
    }

    public Optional<TrackingData> getTrackingData() {
        return Optional.fromNullable(trackingData);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Parcel parcel = (Parcel) o;

        if (!createdAt.equals(parcel.createdAt)) return false;
        if (!id.equals(parcel.id)) return false;
        if (measurements != null ? !measurements.equals(parcel.measurements) : parcel.measurements != null)
            return false;
        if (trackingData != null ? !trackingData.equals(parcel.trackingData) : parcel.trackingData != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + createdAt.hashCode();
        result = 31 * result + (measurements != null ? measurements.hashCode() : 0);
        result = 31 * result + (trackingData != null ? trackingData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Parcel{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", measurements=" + measurements +
                ", trackingData=" + trackingData +
                '}';
    }
}
