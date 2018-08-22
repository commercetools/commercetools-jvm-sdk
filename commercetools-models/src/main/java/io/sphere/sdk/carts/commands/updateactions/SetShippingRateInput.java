package io.sphere.sdk.carts.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.ShippingRateInputDraft;
import io.sphere.sdk.commands.UpdateActionImpl;

import javax.annotation.Nullable;

/**
 * Updates the {@code shippingRateInput} property of a {@link Cart}.
 *
 * @see Cart#getShippingRateInput()
 */
public final class SetShippingRateInput extends UpdateActionImpl<Cart> {
  @Nullable
  private final ShippingRateInputDraft shippingRateInput;

  private SetShippingRateInput(@Nullable final ShippingRateInputDraft shippingRateInput) {
    super("setShippingRateInput");
    this.shippingRateInput = shippingRateInput;
  }

  @Nullable
  @JsonProperty("shippingRateInput")
  public ShippingRateInputDraft getShippingRateInput() {
    return shippingRateInput;
  }

  /**
   * Creates a new object initialized with the given values.
   *
   * @param shippingRateInput initial value for the {@link Cart#getShippingRateInput()} property
   * @return new object initialized with the given values
   */
  public static SetShippingRateInput of(@Nullable final ShippingRateInputDraft shippingRateInput) {
    return new SetShippingRateInput(shippingRateInput);
  }

  /**
   * Creates a new object initialized with the given values.
   *
   * @return new object initialized with the given values
   */
  public static SetShippingRateInput ofUnset() {
    return new SetShippingRateInput(null);
  }
}
