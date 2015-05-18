package io.sphere.sdk.cartdiscounts;

public interface RelativeCartDiscountValue extends CartDiscountValue {
    int getPermyriad();

    default int getBasisPoint() {
        return getPermyriad();
    }
}
