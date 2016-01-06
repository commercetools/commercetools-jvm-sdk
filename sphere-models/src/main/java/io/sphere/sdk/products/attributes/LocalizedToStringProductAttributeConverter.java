package io.sphere.sdk.products.attributes;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeLocalRepository;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class LocalizedToStringProductAttributeConverter extends ProductAttributeConverterBase<String> implements ProductAttributeConverter<String> {
    private final List<Locale> locales;

    protected LocalizedToStringProductAttributeConverter(final Collection<ProductType> productTypes, final List<Locale> locales) {
        super(productTypes);
        this.locales = locales;
    }

    protected LocalizedToStringProductAttributeConverter(final ProductTypeLocalRepository productTypes, final List<Locale> locales) {
        super(productTypes);
        this.locales = locales;
    }

    protected Locale locale() {
        return getLocales().get(0);
    }

    protected List<Locale> getLocales() {
        return locales;
    }

    @Override
    protected String convertStringSet(final Set<String> stringSet, final ProductType productType) {
        return collectToString(stringSet.stream());
    }

    @Override
    protected String convertProductTypeReferenceSet(final Set<Reference<ProductType>> productTypeReferenceSet, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertProductTypeReference(final Reference<ProductType> productTypeReference, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertProductReferenceSet(final Set<Reference<Product>> referenceSet, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertProductReference(final Reference<Product> productReference, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertMoneySet(final Set<MonetaryAmount> monetaryAmountSet, final ProductType productType) {
        return collectToString(monetaryAmountSet.stream().map(money -> convertMoney(money, productType)));
    }

    @Override
    protected String convertLongSet(final Set<Long> longSet, final ProductType productType) {
        return collectToString(longSet.stream());
    }

    private <X> String collectToString(final Stream<X> stream) {
        return stream.map(value -> value.toString()).collect(joining(", "));
    }

    @Override
    protected String convertLong(final Long longValue, final ProductType productType) {
        return longValue.toString();
    }

    @Override
    protected String convertTimeSet(final Set<LocalTime> timeSet, final ProductType productType) {
        return collectToString(timeSet.stream().map(value -> convertTime(value, productType)));
    }

    @Override
    protected String convertTime(final LocalTime time, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertLocalizedStringSet(final Set<LocalizedString> localizedStringSet, final ProductType productType) {
        return collectToString(localizedStringSet.stream().map(value -> convertLocalizedString(value, productType)));
    }

    @Override
    protected String convertLocalizedEnumValueSet(final Set<LocalizedEnumValue> localizedEnumValueSet, final ProductType productType) {
        return collectToString(localizedEnumValueSet.stream().map(value -> convertLocalizedEnumValue(value, productType)));
    }

    @Override
    protected String convertIntegerSet(final Set<Integer> integerSet, final ProductType productType) {
        return collectToString(integerSet.stream());
    }

    @Override
    protected String convertInteger(final Integer integer, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertEnumValueSet(final Set<EnumValue> enumValueSet, final ProductType productType) {
        return collectToString(enumValueSet.stream().map(value -> convertEnumValue(value, productType)));
    }

    @Override
    protected String convertDoubleSet(final Set<Double> doubleSet, final ProductType productType) {
        return collectToString(doubleSet.stream());
    }

    @Override
    protected String convertDouble(final Double doubleValue, final ProductType productType) {
        return doubleValue.toString();
    }

    @Override
    protected String convertDateTimeSet(final Set<ZonedDateTime> zonedDateTimeSet, final ProductType productType) {
        return collectToString(zonedDateTimeSet.stream().map(value -> convertDateTime(value, productType)));
    }

    @Override
    protected String convertDateSet(final Set<LocalDate> dateSet, final ProductType productType) {
        return collectToString(dateSet.stream().map(value -> convertDate(value, productType)));
    }

    @Override
    protected String convertChannelReferenceSet(final Set<Reference<Channel>> channelReferenceSet, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertChannelReference(final Reference<Channel> channelReference, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertCategoryReferenceSet(final Set<Reference<Category>> v, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertCategoryReference(final Reference<Category> categoryReference, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertBooleanSet(final Set<Boolean> booleanSet, final ProductType productType) {
        return collectToString(booleanSet.stream().map(value -> convertBoolean(value, productType)));
    }

    @Override
    protected String convertString(final String stringValue, final ProductType productType) {
        return stringValue;
    }

    @Override
    protected String convertMoney(final MonetaryAmount moneyValue, final ProductType productType) {
        final MonetaryAmountFormat monetaryAmountFormat = MonetaryFormats.getAmountFormat(locale());
        return monetaryAmountFormat.format(moneyValue);
    }

    @Override
    protected String convertLocalizedString(final LocalizedString localizedString, final ProductType productType) {
        return localizedString.getTranslation(getLocales());
    }

    @Override
    protected String convertLocalizedEnumValue(final LocalizedEnumValue localizedEnumValue, final ProductType productType) {
        return localizedEnumValue.getLabel().getTranslation(getLocales());
    }

    @Override
    protected String convertEnumValue(final EnumValue enumValue, final ProductType productType) {
        return enumValue.getLabel();
    }

    @Override
    protected String convertDateTime(final ZonedDateTime dateTimeValue, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertDate(final LocalDate dateValue, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertBoolean(final Boolean booleanValue, final ProductType productType) {
        return booleanValue.toString();
    }
}
