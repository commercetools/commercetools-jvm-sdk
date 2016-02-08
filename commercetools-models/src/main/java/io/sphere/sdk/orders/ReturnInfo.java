package io.sphere.sdk.orders;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

public final class ReturnInfo extends Base {
    private final List<ReturnItem> items;
    @Nullable
    private final String returnTrackingId;
    @Nullable
    private final ZonedDateTime returnDate;

    @JsonCreator
    private ReturnInfo(final List<ReturnItem> items, @Nullable final String returnTrackingId, @Nullable final ZonedDateTime returnDate) {
        this.items = items;
        this.returnTrackingId = returnTrackingId;
        this.returnDate = returnDate;
    }

    public static ReturnInfo of(final List<ReturnItem> items, @Nullable final String returnTrackingId, @Nullable final ZonedDateTime returnDate) {
        return new ReturnInfo(items, returnTrackingId, returnDate);
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
