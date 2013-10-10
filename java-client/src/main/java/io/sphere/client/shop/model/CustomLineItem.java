package io.sphere.client.shop.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.LocalizedString;
import io.sphere.client.model.Money;
import io.sphere.client.model.Reference;

/** A custom line item are used for line items that do not reference an existing product. They can also be used
 *  for price reductions. */
public class CustomLineItem {
    @Nonnull private String id;
    @Nonnull private LocalizedString name = Attribute.defaultLocalizedString;
    @Nonnull private Money money;
    @Nonnull private String slug;
    private int quantity;
    @Nonnull private final Reference<TaxCategory> taxCategory = EmptyReference.create("taxCategory");
    private TaxRate taxRate;

    // for JSON deserializer
    private CustomLineItem() {}

    /** Unique id of this line item. */
    @Nonnull public String getId() { return id; }

     /** The localized name of this line item. */
    @Nonnull public LocalizedString getName() { return name; }

    /** The amount (price) of this line item. */
    @Nonnull public Money getMoney() { return money; }
  
    /** The slug of the line item. Should be used to by the client to reference a custom line item. */
    @Nonnull public String getSlug() { return slug; }

    /** The tax category of the custom line item. */
    @Nonnull public Reference<TaxCategory> getTaxCategory() { return taxCategory; }

    /** Number of items. */
    public int getQuantity() { return quantity; }
  
    /** The tax rate of this line item. Optional.
      *
      *  <p>The tax rate is selected based on the cart's shipping address and is only set when the
      *  shipping address is set. */
    @Nullable public TaxRate getTaxRate() { return taxRate; }

    @Override
    public String toString() {
        return "CustomLineItem{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", money=" + money +
                ", slug='" + slug + '\'' +
                ", quantity=" + quantity +
                ", taxCategory=" + taxCategory +
                ", taxRate=" + taxRate +
                '}';
    }
}
