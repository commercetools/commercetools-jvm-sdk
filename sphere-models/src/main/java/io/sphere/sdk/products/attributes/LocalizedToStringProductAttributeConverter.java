package io.sphere.sdk.products.attributes;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeLocalRepository;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class LocalizedToStringProductAttributeConverter extends ProductAttributeConverterBase<String> implements ProductAttributeConverter<String> {
    private final List<Locale> locales;

    protected LocalizedToStringProductAttributeConverter(final ProductTypeLocalRepository productTypes, final List<Locale> locales) {
        super(productTypes);
        this.locales = locales;
    }

    protected LocalizedToStringProductAttributeConverter(final Collection<ProductType> productTypes, final List<Locale> locales) {
        this(ProductTypeLocalRepository.of(productTypes), locales);
    }

    protected Locale locale() {
        return getLocales().get(0);
    }

    protected List<Locale> getLocales() {
        return locales;
    }

    @Nullable
    protected <X> String convertReference(final Reference<X> reference, final ProductType productType) {
        return null;
    }

    @Nullable
    protected <X> String convertReferenceSet(final Set<Reference<X>> referenceSet, final ProductType productType) {
        return null;
    }

    @Override
    protected String convertStringSet(final Set<String> stringSet, final ProductType productType) {
        return collectToStringPreSorted(stringSet.stream());
    }

    @Override
    protected String convertProductTypeReferenceSet(final Set<Reference<ProductType>> productTypeReferenceSet, final ProductType productType) {
        return convertReferenceSet(productTypeReferenceSet, productType);
    }

    @Override
    protected String convertProductTypeReference(final Reference<ProductType> productTypeReference, final ProductType productType) {
        return convertReference(productTypeReference, productType);
    }

    @Override
    protected String convertProductReferenceSet(final Set<Reference<Product>> referenceSet, final ProductType productType) {
        return convertReferenceSet(referenceSet, productType);
    }

    @Override
    protected String convertProductReference(final Reference<Product> productReference, final ProductType productType) {
        return convertReference(productReference, productType);
    }

    //todo sort by currency???
    @Override
    protected String convertMoneySet(final Set<MonetaryAmount> monetaryAmountSet, final ProductType productType) {
        return collectToString(monetaryAmountSet.stream().sorted().map(money -> convertMoney(money, productType)));
    }

    @Override
    protected String convertLongSet(final Set<Long> longSet, final ProductType productType) {
        return collectToString(longSet.stream().sorted().map(value -> convertLong(value, productType)));
    }

    private <X> String collectToString(final Stream<X> stream) {
        return stream.filter(x -> x != null).map(value -> value.toString()).collect(joining(", "));
    }

    private <X> String collectToStringPreSorted(final Stream<X> stream) {
        return stream.filter(x -> x != null).sorted().map(value -> value.toString()).collect(joining(", "));
    }

    private <X> String collectToStringPostSorted(final Stream<X> stream) {
        return stream.filter(x -> x != null).map(value -> value.toString()).sorted().collect(joining(", "));
    }

    @Override
    protected String convertLong(final Long longValue, final ProductType productType) {
        return longValue.toString();
    }

    @Override
    protected String convertTimeSet(final Set<LocalTime> timeSet, final ProductType productType) {
        return collectToString(timeSet.stream().sorted().map(value -> convertTime(value, productType)));
    }

    @Override
    protected String convertTime(final LocalTime time, final ProductType productType) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale());
        return dateTimeFormatter.format(time);
    }

    @Override
    protected String convertLocalizedStringSet(final Set<LocalizedString> localizedStringSet, final ProductType productType) {
        return collectToStringPostSorted(localizedStringSet.stream().map(value -> convertLocalizedString(value, productType)));
    }

    @Override
    protected String convertLocalizedEnumValueSet(final Set<LocalizedEnumValue> localizedEnumValueSet, final ProductType productType) {
        return collectToStringPostSorted(localizedEnumValueSet.stream().map(value -> convertLocalizedEnumValue(value, productType)));
    }

    @Override
    protected String convertIntegerSet(final Set<Integer> integerSet, final ProductType productType) {
        return collectToString(integerSet.stream().sorted());
    }

    @Override
    protected String convertInteger(final Integer integer, final ProductType productType) {
        return integer.toString();
    }

    @Override
    protected String convertEnumValueSet(final Set<EnumValue> enumValueSet, final ProductType productType) {
        return collectToStringPostSorted(enumValueSet.stream().map(value -> convertEnumValue(value, productType)));
    }

    @Override
    protected String convertDoubleSet(final Set<Double> doubleSet, final ProductType productType) {
        return collectToString(doubleSet.stream().sorted());
    }

    @Override
    protected String convertDouble(final Double doubleValue, final ProductType productType) {
        return doubleValue.toString();
    }

    @Override
    protected String convertDateTimeSet(final Set<ZonedDateTime> zonedDateTimeSet, final ProductType productType) {
        return collectToString(zonedDateTimeSet.stream().sorted().map(value -> convertDateTime(value, productType)));
    }

    @Override
    protected String convertDateSet(final Set<LocalDate> dateSet, final ProductType productType) {
        return collectToString(dateSet.stream().sorted().map(value -> convertDate(value, productType)));
    }

    @Override
    protected String convertChannelReferenceSet(final Set<Reference<Channel>> channelReferenceSet, final ProductType productType) {
        return convertReferenceSet(channelReferenceSet, productType);
    }

    @Override
    protected String convertChannelReference(final Reference<Channel> channelReference, final ProductType productType) {
        return convertReference(channelReference, productType);
    }

    @Override
    protected String convertCategoryReferenceSet(final Set<Reference<Category>> categoryReferenceSet, final ProductType productType) {
        return convertReferenceSet(categoryReferenceSet, productType);
    }

    @Override
    protected String convertCategoryReference(final Reference<Category> categoryReference, final ProductType productType) {
        return convertReference(categoryReference, productType);
    }

    @Override
    protected String convertBooleanSet(final Set<Boolean> booleanSet, final ProductType productType) {
        return collectToString(booleanSet.stream().sorted().map(value -> convertBoolean(value, productType)));
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
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale());
        return dateTimeFormatter.format(dateTimeValue);
    }

    @Override
    protected String convertDate(final LocalDate dateValue, final ProductType productType) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale());
        return dateTimeFormatter.format(dateValue);
    }

    @Override
    protected String convertBoolean(final Boolean booleanValue, final ProductType productType) {
        return booleanValue.toString();
    }
}
