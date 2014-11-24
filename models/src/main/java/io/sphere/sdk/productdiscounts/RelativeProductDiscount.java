package io.sphere.sdk.productdiscounts;


/**
 * A relative product discount with the corresponding basis point value.
 */
public class RelativeProductDiscount extends ProductDiscountValue {
    private final int permyriad;

    private RelativeProductDiscount(final int permyriad) {
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
     * Alias for {@link RelativeProductDiscount#getPermyriad()}
     * @return permyriad
     */
    public int getBasisPoint() {
        return getPermyriad();
    }

    public static RelativeProductDiscount of(final int permyriad) {
        return new RelativeProductDiscount(permyriad);
    }
}
