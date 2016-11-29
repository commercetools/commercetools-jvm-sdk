package io.sphere.sdk.products.attributes;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeLocalRepository;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static io.sphere.sdk.products.attributes.AttributeAccess.*;

public abstract class ProductAttributeConverterBase<T> extends Base implements ProductAttributeConverter<T> {
    private final ProductTypeLocalRepository productTypes;

    protected ProductAttributeConverterBase(final ProductTypeLocalRepository productTypes) {
        this.productTypes = productTypes;
    }

    protected ProductAttributeConverterBase(final Collection<ProductType> productTypes) {
        this(ProductTypeLocalRepository.of(productTypes));
    }


    @Nullable
    @Override
    public T convert(final Attribute attribute, final Referenceable<ProductType> productType) {
        final Optional<ProductType> productTypeOptional = findProductType(productType);
        return productTypeOptional.map(pt -> convertWithProductType(attribute, pt)).orElse(null);
    }

    protected Optional<ProductType> findProductType(final Referenceable<ProductType> productType) {
        final String productTypeId = productType.toReference().getId();
        return productTypes.findById(productTypeId);
    }

    protected T convertWithProductType(final Attribute attribute, final ProductType productType) {
        return AttributeExtraction.<T>of(productType, attribute)
                .ifIs(ofBoolean(), v -> convertBoolean(v, attribute, productType))
                .ifIs(ofBooleanSet(), v -> convertBooleanSet(v, attribute, productType))
                .ifIs(ofCategoryReference(), v -> convertCategoryReference(v, attribute, productType))
                .ifIs(ofCategoryReferenceSet(), v -> convertCategoryReferenceSet(v, attribute, productType))
                .ifIs(ofChannelReference(), v -> convertChannelReference(v, attribute, productType))
                .ifIs(ofChannelReferenceSet(), v -> convertChannelReferenceSet(v, attribute, productType))
                .ifIs(ofDate(), v -> convertDate(v, attribute, productType))
                .ifIs(ofDateSet(), v -> convertDateSet(v, attribute, productType))
                .ifIs(ofDateTime(), v -> convertDateTime(v, attribute, productType))
                .ifIs(ofDateTimeSet(), v -> convertDateTimeSet(v, attribute, productType))
                .ifIs(ofEnumValue(), v -> convertEnumValue(v, attribute, productType))
                .ifIs(ofEnumValueSet(), v -> convertEnumValueSet(v, attribute, productType))
                .ifIs(ofInteger(), v -> convertInteger(v, attribute, productType), value -> isInteger(attribute, productType))
                .ifIs(ofIntegerSet(), v -> convertIntegerSet(v, attribute, productType), value -> isIntegerSet(attribute, productType))
                .ifIs(ofLocalizedEnumValue(), v -> convertLocalizedEnumValue(v, attribute, productType))
                .ifIs(ofLocalizedEnumValueSet(), v -> convertLocalizedEnumValueSet(v, attribute, productType))
                .ifIs(ofLocalizedString(), v -> convertLocalizedString(v, attribute, productType))
                .ifIs(ofLocalizedStringSet(), v -> convertLocalizedStringSet(v, attribute, productType))
                .ifIs(ofLocalTime(), v -> convertTime(v, attribute, productType))
                .ifIs(ofLocalTimeSet(), v -> convertTimeSet(v, attribute, productType))
                .ifIs(ofLong(), v -> convertLong(v, attribute, productType), value -> isLong(attribute, productType))
                .ifIs(ofLongSet(), v -> convertLongSet(v, attribute, productType), value -> isLongSet(attribute, productType))
                .ifIs(ofMoney(), v -> convertMoney(v, attribute, productType))
                .ifIs(ofMoneySet(), v -> convertMoneySet(v, attribute, productType))
                .ifIs(ofProductReference(), v -> convertProductReference(v, attribute, productType))
                .ifIs(ofProductReferenceSet(), v -> convertProductReferenceSet(v, attribute, productType))
                .ifIs(ofProductTypeReference(), v -> convertProductTypeReference(v, attribute, productType))
                .ifIs(ofProductTypeReferenceSet(), v -> convertProductTypeReferenceSet(v, attribute, productType))
                .ifIs(ofString(), v -> convertString(v, attribute, productType))
                .ifIs(ofStringSet(), v -> convertStringSet(v, attribute, productType))
                .ifIs(ofTime(), v -> convertTime(v, attribute, productType))
                .ifIs(ofTimeSet(), v -> convertTimeSet(v, attribute, productType))

                //double is fallback if not int or long are used
                .ifIs(ofDouble(), v -> convertDouble(v, attribute, productType))
                .ifIs(ofDoubleSet(), v -> convertDoubleSet(v, attribute, productType))

                .findValue()
                .orElse(null);
    }

    @Nullable
    protected abstract T convertStringSet(final Set<String> stringSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertProductTypeReferenceSet(final Set<Reference<ProductType>> productTypeReferenceSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertProductTypeReference(final Reference<ProductType> productTypeReference, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertProductReferenceSet(final Set<Reference<Product>> referenceSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertProductReference(final Reference<Product> productReference, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertMoneySet(final Set<MonetaryAmount> monetaryAmountSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertLongSet(final Set<Long> longSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertLong(final Long longValue, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertTimeSet(final Set<LocalTime> timeSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertTime(final LocalTime time, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertLocalizedStringSet(final Set<LocalizedString> localizedStringSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertLocalizedEnumValueSet(final Set<LocalizedEnumValue> localizedEnumValueSet, final Attribute attribute, final ProductType productType);

    protected Collection<String> integerAttributes() {
        return Collections.emptyList();
    }

    protected Collection<String> longAttributes() {
        return Collections.emptyList();
    }

    protected Collection<String> integerSetAttributes() {
        return Collections.emptyList();
    }

    protected Collection<String> longSetAttributes() {
        return Collections.emptyList();
    }

    @Nullable
    protected abstract T convertIntegerSet(final Set<Integer> integerSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertInteger(final Integer integer, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertEnumValueSet(final Set<EnumValue> enumValueSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertDoubleSet(final Set<Double> doubleSet, final Attribute attribute, final ProductType productType);

    protected boolean isIntegerSet(final Attribute attribute, final ProductType productType) {
        return integerSetAttributes().contains(attribute.getName());
    }

    protected boolean isInteger(final Attribute attribute, final ProductType productType) {
        return integerAttributes().contains(attribute.getName());
    }

    protected boolean isLongSet(final Attribute attribute, final ProductType productType) {
        return longSetAttributes().contains(attribute.getName());
    }

    protected boolean isLong(final Attribute attribute, final ProductType productType) {
        return longAttributes().contains(attribute.getName());
    }

    @Nullable
    protected abstract T convertDouble(final Double doubleValue, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertDateTimeSet(final Set<ZonedDateTime> zonedDateTimeSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertDateSet(final Set<LocalDate> dateSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertChannelReferenceSet(final Set<Reference<Channel>> channelReferenceSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertChannelReference(final Reference<Channel> channelReference, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertCategoryReferenceSet(final Set<Reference<Category>> v, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertCategoryReference(final Reference<Category> categoryReference, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertBooleanSet(final Set<Boolean> booleanSet, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertString(final String stringValue, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertMoney(final MonetaryAmount moneyValue, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertLocalizedString(final LocalizedString localizedString, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertLocalizedEnumValue(final LocalizedEnumValue localizedEnumValue, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertEnumValue(final EnumValue enumValue, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertDateTime(final ZonedDateTime dateTimeValue, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertDate(final LocalDate dateValue, final Attribute attribute, final ProductType productType);

    @Nullable
    protected abstract T convertBoolean(final Boolean booleanValue, final Attribute attribute, final ProductType productType);
}
