package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

public final class RelativeCartDiscountValue extends Base implements CartDiscountValue {
    private final Integer permyriad;

    @JsonCreator
    private RelativeCartDiscountValue(final Integer permyriad) {
        this.permyriad = permyriad;
    }

    public Integer getPermyriad() {
        return permyriad;
    }

    public Integer getBasisPoint() {
        return getPermyriad();
    }

    public static RelativeCartDiscountValue of(final Integer permyriad) {
        return new RelativeCartDiscountValue(permyriad);
    }
}
