package io.sphere.sdk.orders.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.orders.Order;
import io.sphere.sdk.orders.ReturnItemDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

/**

    Adds return data to an order.

    {@doc.gen intro}

    {@include.example io.sphere.sdk.orders.commands.OrderUpdateCommandIntegrationTest#addReturnInfo()}

 @see Order#getReturnInfo()
 */
public final class AddReturnInfo extends UpdateActionImpl<Order> {
    @Nullable
    private final ZonedDateTime returnDate;
    @Nullable
    private final String returnTrackingId;
    private final List<? extends ReturnItemDraft> items;

    private AddReturnInfo(final List<? extends ReturnItemDraft> items, @Nullable final ZonedDateTime returnDate, @Nullable final String returnTrackingId) {
        super("addReturnInfo");
        this.returnDate = returnDate;
        this.returnTrackingId = returnTrackingId;
        this.items = items;
    }

    public static AddReturnInfo of(final List<? extends ReturnItemDraft> items, @Nullable final ZonedDateTime returnDate, @Nullable final String returnTrackingId) {
        return new AddReturnInfo(items, returnDate, returnTrackingId);
    }

    public static AddReturnInfo of(final List<? extends ReturnItemDraft> items) {
        return of(items, null, null);
    }

    public AddReturnInfo withReturnDate(final ZonedDateTime returnDate) {
        return of(items, returnDate, returnTrackingId);
    }

    public AddReturnInfo withReturnTrackingId(final String returnTrackingId) {
        return of(items, returnDate, returnTrackingId);
    }

    @Nullable
    public ZonedDateTime getReturnDate() {
        return returnDate;
    }

    @Nullable
    public String getReturnTrackingId() {
        return returnTrackingId;
    }

    public List<? extends ReturnItemDraft> getItems() {
        return items;
    }
}
