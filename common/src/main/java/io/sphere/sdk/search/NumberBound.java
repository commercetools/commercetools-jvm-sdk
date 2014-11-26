package io.sphere.sdk.search;

import java.math.BigDecimal;

public class NumberBound extends Bound<BigDecimal> {

    private NumberBound(final BigDecimal value, final BoundType type) {
        super(value, type);
    }

    @Override
    public String render() {
        return endpoint().toPlainString();
    }

    public static NumberBound of(BigDecimal value) {
        return new NumberBound(value, BoundType.EXCLUSIVE);
    }

    public static NumberBound of(int value) {
        return of(new BigDecimal(value));
    }
}
