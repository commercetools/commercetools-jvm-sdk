package io.sphere.sdk.projects.commands.updateactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.projects.Project;
import io.sphere.sdk.projects.ShippingRateInputType;
import io.sphere.sdk.projects.ShippingRateInputTypeDraft;

import javax.annotation.Generated;
import javax.annotation.Nullable;

/**
 * Updates the {@code shippingRateInputType} property of a {@link Project}.
 *
 * @see Project#getShippingRateInputType()
 */
public final class SetShippingRateInputType extends UpdateActionImpl<Project> {
  @Nullable
  @JsonProperty("shippingRateInputType")
  private final ShippingRateInputTypeDraft shippingRateInputTypeDraft;

  private SetShippingRateInputType(@Nullable final ShippingRateInputTypeDraft shippingRateInputTypeDraft) {
    super("setShippingRateInputType");
    this.shippingRateInputTypeDraft = shippingRateInputTypeDraft;
  }

  @Nullable
  @JsonProperty("shippingRateInputType")
  public ShippingRateInputTypeDraft getShippingRateInputTypeDraft() {
    return shippingRateInputTypeDraft;
  }

  /**
   * Creates a new object initialized with the given values.
   *
   * @param shippingRateInputTypeDraft initial value for the {@link Project#getShippingRateInputType()} property
   * @return new object initialized with the given values
   */
  public static SetShippingRateInputType of(@Nullable final ShippingRateInputTypeDraft shippingRateInputTypeDraft) {
    return new SetShippingRateInputType(shippingRateInputTypeDraft);
  }

  /**
   * Creates a new object initialized with the given values.
   *
   * @return new object initialized with the given values
   */
  public static SetShippingRateInputType ofUnset() {
    return new SetShippingRateInputType(null);
  }
}