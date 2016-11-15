package io.sphere.sdk.cartdiscounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.LocalizedString;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * Draft to create a {@link CartDiscount}.
 * @see CartDiscountDraftBuilder
 */
@JsonDeserialize(as = CartDiscountDraftDsl.class)
@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"name", "cartPredicate", "value", "target", "sortOrder", "requiresDiscountCode"}, useLowercaseBooleans = true), additionalBuilderClassContents = {"    public CartDiscountDraftBuilder cartPredicate(final CartDiscountPredicate cartPredicate) {\n" +
        "        this.cartPredicate = cartPredicate.toSphereCartPredicate();\n" +
        "return this;\n" +
        "    }",
"    public static CartDiscountDraftBuilder of(final io.sphere.sdk.models.LocalizedString name, final CartDiscountPredicate cartPredicate, final io.sphere.sdk.cartdiscounts.CartDiscountValue value, final io.sphere.sdk.cartdiscounts.CartDiscountTarget target, final java.lang.String sortOrder, final boolean requiresDiscountCode) {\n" +
        "        return of(name, cartPredicate.toSphereCartPredicate(), value, target, sortOrder, requiresDiscountCode);\n" +
        "    }",
"    public CartDiscountDraftBuilder active(final boolean active) {\n" +
        "        this.active = active;\n" +
        "        return this;\n" +
        "    }\n" +
        "\n" +
        "    public CartDiscountDraftBuilder isActive(final boolean active) {\n" +
        "        this.active = active;\n" +
        "        return this;\n" +
        "    }", "    public CartDiscountDraftBuilder requiresDiscountCode(final boolean requiresDiscountCode) {\n" +
        "        this.requiresDiscountCode = requiresDiscountCode;\n" +
        "return this;\n" +
        "    } "})
public interface CartDiscountDraft {
    String getCartPredicate();

    @Nullable
    LocalizedString getDescription();

    @JsonProperty("isActive")
    Boolean isActive();

    LocalizedString getName();

    @JsonProperty("requiresDiscountCode")
    Boolean isRequiresDiscountCode();

    String getSortOrder();

    CartDiscountTarget getTarget();

    @Nullable
    ZonedDateTime getValidFrom();

    @Nullable
    ZonedDateTime getValidUntil();

    CartDiscountValue getValue();
}
