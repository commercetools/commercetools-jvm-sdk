package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.Image;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;

import javax.annotation.Nullable;

/**
 * Updates the {@code masterData} property of a {@link Product}.
 * <p>
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setImageLabel()}
 *
 * @see ProductVariant#getImages()
 * @see Image#getLabel()
 */
public final class SetImageLabel extends UpdateActionImpl<Product> {
    private final Integer variantId;

    private final String imageUrl;

    @Nullable
    private final String label;

    @Nullable
    private final Boolean staged;

    private SetImageLabel(final Integer variantId, final String imageUrl,
                          @Nullable final String label, @Nullable final Boolean staged) {
        super("setImageLabel");
        this.variantId = variantId;
        this.imageUrl = imageUrl;
        this.label = label;
        this.staged = staged;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Nullable
    public String getLabel() {
        return label;
    }

    @Nullable
    public Boolean isStaged() {
        return staged;
    }

    /**
     * Creates a new update action initialized with the given values.
     *
     * @param variantId initial value for the  property
     * @param imageUrl  initial value for the  property
     * @param label     initial value for the  property
     * @param staged    initial value for the  property
     * @return new object initialized with the given values
     */
    public static SetImageLabel of(final Integer variantId, final String imageUrl,
                                   @Nullable final String label, @Nullable final Boolean staged) {
        return new SetImageLabel(variantId, imageUrl, label, staged);
    }

    /**
     * Creates a new update action initialized with the given values.
     *
     * @param variantId initial value for the  property
     * @param imageUrl  initial value for the  property
     * @param label     initial value for the  property
     * @return new object initialized with the given values
     */
    public static SetImageLabel ofStaged(final Integer variantId, final String imageUrl,
                                         @Nullable final String label) {
        return new SetImageLabel(variantId, imageUrl, label, null);
    }

    /**
     * Creates a new update action initialized with the given values to unset the label.
     *
     * @param variantId initial value for the  property
     * @param imageUrl  initial value for the  property
     * @param staged    initial value for the  property
     * @return new object initialized with the given values
     */
    public static SetImageLabel ofUnset(final Integer variantId, final String imageUrl, @Nullable final Boolean staged) {
        return new SetImageLabel(variantId, imageUrl, null, staged);
    }


    /**
     * Creates a new update action initialized with the given values to unset the staged label.
     *
     * @param variantId initial value for the  property
     * @param imageUrl  initial value for the  property
     * @return new object initialized with the given values
     */
    public static SetImageLabel ofUnsetStaged(final Integer variantId, final String imageUrl) {
        return new SetImageLabel(variantId, imageUrl, null, null);
    }

    /**
     * Creates a copied update action initialized with the given parameter, the rest of the parameters are copied from the original object.
     * @param label The image label.
     * @return new object initialized with the copied values from the original object
     */
    public SetImageLabel withLabel(final String label) {
        return new SetImageLabel(getVariantId(), getImageUrl(), label, isStaged());
    }

    /**
     * Creates a copied update action initialized with the given parameter, the rest of the parameters are copied from the original object.
     * @param staged specifiy if image is staged
     * @return new object initialized with the copied values from the original object
     */
    public SetImageLabel withStaged(final Boolean staged) {
        return new SetImageLabel(getVariantId(), getImageUrl(), getLabel(), staged);
    }
}
