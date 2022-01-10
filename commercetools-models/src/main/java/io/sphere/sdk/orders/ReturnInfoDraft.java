package io.sphere.sdk.orders;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@ResourceDraftValue(factoryMethods = {@FactoryMethod(parameterNames = {})})
public interface ReturnInfoDraft {

    List<ReturnItemDraft> getItems();

    @Nullable
    String getReturnTrackingId();

    @Nullable
    ZonedDateTime getReturnDate();

    static ReturnInfoDraft of(final List<ReturnItemDraft> items, @Nullable final String returnTrackingId, @Nullable final ZonedDateTime returnDate) {
        return new ReturnInfoDraftDsl(items, returnDate, returnTrackingId);
    }
}
