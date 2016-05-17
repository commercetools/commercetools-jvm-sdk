package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

final class ReturnInfoImpl extends Base implements ReturnInfo {
    private final List<ReturnItem> items;
    @Nullable
    private final String returnTrackingId;
    @Nullable
    private final ZonedDateTime returnDate;

    @JsonCreator
    ReturnInfoImpl(final List<ReturnItem> items, @Nullable final String returnTrackingId, @Nullable final ZonedDateTime returnDate) {
        this.items = items;
        this.returnTrackingId = returnTrackingId;
        this.returnDate = returnDate;
    }

    public List<ReturnItem> getItems() {
        return items;
    }

    @Nullable
    public String getReturnTrackingId() {
        return returnTrackingId;
    }

    @Nullable
    public ZonedDateTime getReturnDate() {
        return returnDate;
    }
}
