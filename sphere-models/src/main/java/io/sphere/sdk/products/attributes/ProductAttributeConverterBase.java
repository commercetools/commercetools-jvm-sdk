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


    //TODO maybe parameter product type id
    @Nullable
    @Override
    public T convert(final Attribute attribute, final Referenceable<ProductType> productType) {

        final String productTypeId = productType.toReference().getId();
        final Optional<ProductType> productTypeOptional = productTypes.findById(productTypeId);
        return productTypeOptional.map(pt -> convertWithProductType(attribute, pt)).orElse(null);
    }

    //TODO attribute or its name should also be part, otherwise the name is lost
    protected T convertWithProductType(final Attribute attribute, final ProductType productType) {
        return AttributeExtraction.<T>of(productType, attribute)
                .ifIs(ofBoolean(), v -> convertBoolean(v, productType))
                .ifIs(ofBooleanSet(), v -> convertBooleanSet(v, productType))
                .ifIs(ofCategoryReference(), v -> convertCategoryReference(v, productType))
                .ifIs(ofCategoryReferenceSet(), v -> convertCategoryReferenceSet(v, productType))
                .ifIs(ofChannelReference(), v -> convertChannelReference(v, productType))
                .ifIs(ofChannelReferenceSet(), v -> convertChannelReferenceSet(v, productType))
                .ifIs(ofDate(), v -> convertDate(v, productType))
                .ifIs(ofDateSet(), v -> convertDateSet(v, productType))
                .ifIs(ofDateTime(), v -> convertDateTime(v, productType))
                .ifIs(ofDateTimeSet(), v -> convertDateTimeSet(v, productType))
                .ifIs(ofDouble(), v -> convertDouble(v, productType), value -> isDouble(attribute, productType))
                .ifIs(ofDoubleSet(), v -> convertDoubleSet(v, productType), value -> isDoubleSet(attribute, productType))
                .ifIs(ofEnumValue(), v -> convertEnumValue(v, productType))
                .ifIs(ofEnumValueSet(), v -> convertEnumValueSet(v, productType))
                .ifIs(ofInteger(), v -> convertInteger(v, productType), value -> isInteger(attribute, productType))
                .ifIs(ofIntegerSet(), v -> convertIntegerSet(v, productType), value -> isIntegerSet(attribute, productType))
                .ifIs(ofLocalizedEnumValue(), v -> convertLocalizedEnumValue(v, productType))
                .ifIs(ofLocalizedEnumValueSet(), v -> convertLocalizedEnumValueSet(v, productType))
                .ifIs(ofLocalizedString(), v -> convertLocalizedString(v, productType))
                .ifIs(ofLocalizedStringSet(), v -> convertLocalizedStringSet(v, productType))
                .ifIs(ofLocalTime(), v -> convertTime(v, productType))
                .ifIs(ofLocalTimeSet(), v -> convertTimeSet(v, productType))
                .ifIs(ofLong(), v -> convertLong(v, productType), value -> isLong(attribute, productType))
                .ifIs(ofLongSet(), v -> convertLongSet(v, productType), value -> isLongSet(attribute, productType))
                .ifIs(ofMoney(), v -> convertMoney(v, productType))
                .ifIs(ofMoneySet(), v -> convertMoneySet(v, productType))
                .ifIs(ofProductReference(), v -> convertProductReference(v, productType))
                .ifIs(ofProductReferenceSet(), v -> convertProductReferenceSet(v, productType))
                .ifIs(ofProductTypeReference(), v -> convertProductTypeReference(v, productType))
                .ifIs(ofProductTypeReferenceSet(), v -> convertProductTypeReferenceSet(v, productType))
                .ifIs(ofString(), v -> convertString(v, productType))
                .ifIs(ofStringSet(), v -> convertStringSet(v, productType))
                .ifIs(ofTime(), v -> convertTime(v, productType))
                .ifIs(ofTimeSet(), v -> convertTimeSet(v, productType))
                .findValue()
                .orElse(null);
    }

    @Nullable
    protected abstract T convertStringSet(final Set<String> stringSet, final ProductType productType);

    @Nullable
    protected abstract T convertProductTypeReferenceSet(final Set<Reference<ProductType>> productTypeReferenceSet, final ProductType productType);

    @Nullable
    protected abstract T convertProductTypeReference(final Reference<ProductType> productTypeReference, final ProductType productType);

    @Nullable
    protected abstract T convertProductReferenceSet(final Set<Reference<Product>> referenceSet, final ProductType productType);

    @Nullable
    protected abstract T convertProductReference(final Reference<Product> productReference, final ProductType productType);

    @Nullable
    protected abstract T convertMoneySet(final Set<MonetaryAmount> monetaryAmountSet, final ProductType productType);

    @Nullable
    protected abstract T convertLongSet(final Set<Long> longSet, final ProductType productType);

    @Nullable
    protected abstract T convertLong(final Long longValue, final ProductType productType);

    @Nullable
    protected abstract T convertTimeSet(final Set<LocalTime> timeSet, final ProductType productType);

    @Nullable
    protected abstract T convertTime(final LocalTime time, final ProductType productType);

    @Nullable
    protected abstract T convertLocalizedStringSet(final Set<LocalizedString> localizedStringSet, final ProductType productType);

    @Nullable
    protected abstract T convertLocalizedEnumValueSet(final Set<LocalizedEnumValue> localizedEnumValueSet, final ProductType productType);

    protected Collection<String> integerAttributes() {
        return Collections.emptyList();
    }

    protected Collection<String> longAttributes() {
        return Collections.emptyList();
    }

    protected Collection<String> doubleAttributes() {
        return Collections.emptyList();
    }

    protected Collection<String> integerSetAttributes() {
        return Collections.emptyList();
    }

    protected Collection<String> longSetAttributes() {
        return Collections.emptyList();
    }

    protected Collection<String> doubleSetAttributes() {
        return Collections.emptyList();
    }

    @Nullable
    protected abstract T convertIntegerSet(final Set<Integer> integerSet, final ProductType productType);

    @Nullable
    protected abstract T convertInteger(final Integer integer, final ProductType productType);

    @Nullable
    protected abstract T convertEnumValueSet(final Set<EnumValue> enumValueSet, final ProductType productType);

    @Nullable
    protected abstract T convertDoubleSet(final Set<Double> doubleSet, final ProductType productType);

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

    protected boolean isDoubleSet(final Attribute attribute, final ProductType productType) {
        return doubleSetAttributes().contains(attribute.getName());
    }

    protected boolean isDouble(final Attribute attribute, final ProductType productType) {
        return doubleAttributes().contains(attribute.getName());
    }

    @Nullable
    protected abstract T convertDouble(final Double doubleValue, final ProductType productType);

    @Nullable
    protected abstract T convertDateTimeSet(final Set<ZonedDateTime> zonedDateTimeSet, final ProductType productType);

    @Nullable
    protected abstract T convertDateSet(final Set<LocalDate> dateSet, final ProductType productType);

    @Nullable
    protected abstract T convertChannelReferenceSet(final Set<Reference<Channel>> channelReferenceSet, final ProductType productType);

    @Nullable
    protected abstract T convertChannelReference(final Reference<Channel> channelReference, final ProductType productType);

    @Nullable
    protected abstract T convertCategoryReferenceSet(final Set<Reference<Category>> v, final ProductType productType);

    @Nullable
    protected abstract T convertCategoryReference(final Reference<Category> categoryReference, final ProductType productType);

    @Nullable
    protected abstract T convertBooleanSet(final Set<Boolean> booleanSet, final ProductType productType);

    @Nullable
    protected abstract T convertString(final String stringValue, final ProductType productType);

    @Nullable
    protected abstract T convertMoney(final MonetaryAmount moneyValue, final ProductType productType);

    @Nullable
    protected abstract T convertLocalizedString(final LocalizedString localizedString, final ProductType productType);

    @Nullable
    protected abstract T convertLocalizedEnumValue(final LocalizedEnumValue localizedEnumValue, final ProductType productType);

    @Nullable
    protected abstract T convertEnumValue(final EnumValue enumValue, final ProductType productType);

    @Nullable
    protected abstract T convertDateTime(final ZonedDateTime dateTimeValue, final ProductType productType);

    @Nullable
    protected abstract T convertDate(final LocalDate dateValue, final ProductType productType);

    @Nullable
    protected abstract T convertBoolean(final Boolean booleanValue, final ProductType productType);
}
