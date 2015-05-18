package io.sphere.sdk.cartdiscounts;

import javax.money.MonetaryAmount;
import java.util.List;

public interface AbsoluteCartDiscountValue {
    List<MonetaryAmount> getMoney();
}
