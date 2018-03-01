package io.sphere.sdk.shippingmethods;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.money.MonetaryAmount;

/**
 * Builder for {@link CartScore}.
 */
public final class CartScoreBuilder extends Base implements Builder<CartScore> {

  private final MonetaryAmount price;

  @Nullable
  private final PriceFunction priceFunction;

  @Nullable
  private final Long score;

  private String type;


  CartScoreBuilder(final MonetaryAmount price, @Nullable final PriceFunction priceFunction, @Nullable final Long score, final String type) {
    this.price = price;
    this.priceFunction = priceFunction;
    this.score = score;
    this.type = type;
  }


  /**
   * Creates a new instance of {@code CartScore} with the values of this builder.
   *
   * @return the instance
   */
  public CartScore build() {
    return new CartScoreImpl(null, price, priceFunction, score, type);
  }

  /**
   * Creates a new object initialized with the given values.
   *
   * @param score initial value for the {@link CartScore#getScore()} property
   * @param price initial value for the {@link ShippingRatePriceTier#getPrice()} property
   * @return new object initialized with the given values
   */
  public static CartScoreBuilder of(final Long score, final MonetaryAmount price) {
    return new CartScoreBuilder( price, null, score, null);
  }

  /**
   * Creates a new object initialized with the given values.
   *
   * @param priceFunction initial value for the {@link CartScore#getPriceFunction()} property
   * @return new object initialized with the given values
   */
  public static CartScoreBuilder of(final Long score, final PriceFunction priceFunction) {
    return new CartScoreBuilder( null, priceFunction, score, null);
  }

  /**
   * Creates a new object initialized with the fields of the template parameter.
   *
   * @param template the template
   * @return a new object initialized from the template
   */
  public static CartScoreBuilder of(final CartScore template) {
    return new CartScoreBuilder( template.getPrice(), template.getPriceFunction(), template.getScore(), template.getType());
  }
}
