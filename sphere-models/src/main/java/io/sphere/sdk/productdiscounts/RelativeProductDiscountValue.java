package io.sphere.sdk.productdiscounts;


import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.models.Base;

/**
 * A relative product discount with the corresponding basis point value.
 */
public class RelativeProductDiscountValue extends Base implements ProductDiscountValue {
    private final int permyriad;

    @JsonCreator
    private RelativeProductDiscountValue(final int permyriad) {
        this.permyriad = permyriad;
    }

    /**
     * Per ten thousand. The fraction the price is reduced. 1000 will result in a 10% price reduction.
     * @return permyriad
     */
    public int getPermyriad() {
        return permyriad;
    }

    /**
     * Alias for {@link RelativeProductDiscountValue#getPermyriad()}
     * @return permyriad
     */
    public int getBasisPoint() {
        return getPermyriad();
    }

    public static RelativeProductDiscountValue of(final int permyriad) {
        return new RelativeProductDiscountValue(permyriad);
    }
}
