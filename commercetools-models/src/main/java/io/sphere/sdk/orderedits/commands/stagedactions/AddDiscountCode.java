package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.discountcodes.DiscountCode;

public final class AddDiscountCode extends OrderEditStagedUpdateActionBase {

    private final String code;

    @JsonCreator
    private AddDiscountCode(final String code) {
        super("addDiscountCode");
        this.code = code;
    }

    public static AddDiscountCode of(final DiscountCode code) {
        return of(code.getCode());
    }

    public static AddDiscountCode of(final String code) {
        return new AddDiscountCode(code);
    }

    public String getCode() {
        return code;
    }
}