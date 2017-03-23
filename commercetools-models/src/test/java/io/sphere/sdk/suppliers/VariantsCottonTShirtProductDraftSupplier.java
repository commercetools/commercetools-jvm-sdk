package io.sphere.sdk.suppliers;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.*;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Colors;
import io.sphere.sdk.suppliers.TShirtProductTypeDraftSupplier.Sizes;
import io.sphere.sdk.utils.MoneyImpl;

import java.util.Locale;
import java.util.UUID;
import java.util.function.Supplier;

import static io.sphere.sdk.test.SphereTestUtils.EUR;
import static io.sphere.sdk.test.SphereTestUtils.asList;

public class VariantsCottonTShirtProductDraftSupplier implements Supplier<ProductDraft> {
    private final Reference<ProductType> productType;
    private final String name;
    private final Referenceable<CustomerGroup> customerGroup;

    public VariantsCottonTShirtProductDraftSupplier(final Referenceable<ProductType> productType, final String name, final Referenceable<CustomerGroup> customerGroup) {
        this.customerGroup = customerGroup;
        this.productType = productType.toReference();
        this.name = name;
    }

    @Override
    public ProductDraft get() {
        final ProductVariantDraft masterVariant = ProductVariantDraftBuilder.of()
                .attributes(Sizes.ATTRIBUTE.draftOf(Sizes.S), Colors.ATTRIBUTE.draftOf(Colors.GREEN))
                .sku(UUID.randomUUID().toString())
                .prices(PriceDraft.of(MoneyImpl.ofCents(1234, EUR)))
                .build();
        final ProductVariantDraft secondVariant = ProductVariantDraftBuilder.of()
                .attributes(Sizes.ATTRIBUTE.draftOf(Sizes.M), Colors.ATTRIBUTE.draftOf(Colors.GREEN))
                .sku(UUID.randomUUID().toString())
                .prices(PriceDraft.of(MoneyImpl.ofCents(1234, EUR)), PriceDraft.of(MoneyImpl.ofCents(600, EUR)).withCustomerGroup(customerGroup))
                .build();
        final LocalizedString slug = en(name).slugifiedUnique();
        return ProductDraftBuilder.of(productType, en(name), slug, masterVariant)
                .variants(asList(secondVariant))
                .description(en(name))
                .metaTitle(en("cotton t-shirt"))
                .metaDescription(en("cotton t-shirt description"))
                .metaKeywords(en("cotton, t-shirt, clothes"))
                .build();
    }

    private LocalizedString en(final String value) {
        return LocalizedString.of(Locale.ENGLISH, value);
    }
}
