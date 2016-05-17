package io.sphere.sdk.orders;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@JsonDeserialize(as = ReturnInfoImpl.class)
public interface ReturnInfo {
    static ReturnInfo of(final List<ReturnItem> items, @Nullable final String returnTrackingId, @Nullable final ZonedDateTime returnDate) {
        return new ReturnInfoImpl(items, returnTrackingId, returnDate);
    }

    List<ReturnItem> getItems();

    @Nullable
    String getReturnTrackingId();

    @Nullable
    ZonedDateTime getReturnDate();
}
