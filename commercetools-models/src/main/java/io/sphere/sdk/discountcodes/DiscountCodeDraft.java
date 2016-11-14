package io.sphere.sdk.discountcodes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.cartdiscounts.CartDiscount;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * @see DiscountCodeDraftBuilder
 * @see DiscountCodeDraftDsl
 */
@JsonDeserialize(as = DiscountCodeDraftDsl.class)
@ResourceDraftValue(factoryMethods = {@FactoryMethod(parameterNames = {"code", "cartDiscounts"})},
additionalDslClassContents = {"    public DiscountCodeDraftDsl withCartDiscounts(final Referenceable<io.sphere.sdk.cartdiscounts.CartDiscount> cartDiscount) {\n" +
        "        return withCartDiscounts(Collections.singletonList(cartDiscount.toReference()));\n" +
        "    }",
"    public static DiscountCodeDraftDsl of(final String code, final Referenceable<io.sphere.sdk.cartdiscounts.CartDiscount> cartDiscount) {\n" +
        "        return of(code, Collections.singletonList(cartDiscount.toReference()));\n" +
        "    }",
"    public DiscountCodeDraftDsl withCartPredicate(@Nullable final io.sphere.sdk.cartdiscounts.CartDiscountPredicate cartPredicate) {\n" +
        "        return newBuilder().cartPredicate(cartPredicate.toSphereCartPredicate()).build();\n" +
        "    }"}, useBuilderStereotypeDslClass = true)
public interface DiscountCodeDraft {
    @Nullable
    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    String getCode();

    List<Reference<CartDiscount>> getCartDiscounts();

    @Nullable
    String getCartPredicate();

    @JsonProperty("isActive")
    Boolean isActive();

    @Nullable
    Long getMaxApplications();

    @Nullable
    Long getMaxApplicationsPerCustomer();

    static DiscountCodeDraftDsl of(final String code, final Referenceable<CartDiscount> cartDiscount) {
        return DiscountCodeDraft.of(code, Collections.singletonList(cartDiscount.toReference()));
    }

    static DiscountCodeDraftDsl of(final String code, final List<Reference<CartDiscount>> cartDiscounts) {
        return DiscountCodeDraftDsl.of(code, cartDiscounts);
    }
}
