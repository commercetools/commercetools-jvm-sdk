package io.sphere.sdk.orders;

import io.sphere.sdk.carts.ItemState;
import io.sphere.sdk.models.*;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.TaxRate;
import org.apache.commons.lang3.RandomStringUtils;

import javax.money.MonetaryAmount;
import java.util.Optional;
import java.util.Set;

public class CustomLineItemImportDraftBuilder extends Base implements Builder<CustomLineItemImportDraft> {
    private String id = RandomStringUtils.randomAlphanumeric(20);
    private final LocalizedStrings name;
    private final MonetaryAmount money;
    private String slug = RandomStringUtils.randomAlphanumeric(20);
    private final long quantity;
    private Optional<Set<ItemState>> state = Optional.empty();
    private final Reference<TaxCategory> taxCategory;
    private Optional<TaxRate> taxRate;

    private CustomLineItemImportDraftBuilder(final LocalizedStrings name, final MonetaryAmount money, final long quantity, final Reference<TaxCategory> taxCategory) {
        this.name = name;
        this.money = money;
        this.quantity = quantity;
        this.taxCategory = taxCategory;
    }

    public static CustomLineItemImportDraftBuilder of(final LocalizedStrings name, final long quantity, final MonetaryAmount money, final Referenceable<TaxCategory> taxCategory) {
        return new CustomLineItemImportDraftBuilder(name, money, quantity, taxCategory.toReference());
    }

    public CustomLineItemImportDraftBuilder id(final String id) {
        this.id = id;
        return this;
    }

    public CustomLineItemImportDraftBuilder slug(final String slug) {
        this.slug = slug;
        return this;
    }

    public CustomLineItemImportDraftBuilder state(final Set<ItemState> state) {
        this.state = Optional.of(state);
        return this;
    }

    public CustomLineItemImportDraftBuilder taxRate(final TaxRate taxRate) {
        return taxRate(Optional.of(taxRate));
    }

    public CustomLineItemImportDraftBuilder taxRate(final Optional<TaxRate> taxRate) {
        this.taxRate = taxRate;
        return this;
    }

    @Override
    public CustomLineItemImportDraft build() {
        return new CustomLineItemImportDraftImpl(id, name, money, slug, quantity, state, taxCategory, taxRate);
    }
}
