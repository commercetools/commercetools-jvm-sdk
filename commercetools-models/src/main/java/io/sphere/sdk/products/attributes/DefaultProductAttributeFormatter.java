package io.sphere.sdk.products.attributes;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.*;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.products.ProductVariant;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeLocalRepository;
import org.javamoney.moneta.function.MonetaryFunctions;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class DefaultProductAttributeFormatter extends ProductAttributeConverterBase<String> implements ProductAttributeConverter<String> {
    private final List<Locale> locales;

    public DefaultProductAttributeFormatter(final ProductTypeLocalRepository productTypes, final List<Locale> locales) {
        super(productTypes);
        this.locales = locales;
    }

    public DefaultProductAttributeFormatter(final Collection<ProductType> productTypes, final List<Locale> locales) {
        this(ProductTypeLocalRepository.of(productTypes), locales);
    }

    /**
     Formats a product attribute as String. The product type reference is typically extracted using
     {@link io.sphere.sdk.products.ProductProjection#getProductType()} or
     {@link io.sphere.sdk.products.Product#getProductType()}.

     @param attribute the attribute which should be formatted
     @param productType the reference of the product type of this attribute, it is not necessary to expand the reference
     @return formatted attribute or null
     */
    @Nullable
    @Override
    public String convert(final Attribute attribute, final Referenceable<ProductType> productType) {
        return super.convert(attribute, productType);
    }

    /**
     Formats a product attribute as String, alias of {@link #convert(Attribute, Referenceable)}.
     The product type reference is typically extracted using
     {@link io.sphere.sdk.products.ProductProjection#getProductType()} or
     {@link io.sphere.sdk.products.Product#getProductType()}.

     @param attribute the attribute which should be formatted
     @param productType the reference of the product type of this attribute, it is not necessary to expand the reference
     @return formatted attribute or null
     */
    @Nullable
    public String format(final Attribute attribute, final Referenceable<ProductType> productType) {
        return convert(attribute, productType);
    }

    /**
     * Creates a list of attribute translated and formatted labels and values.
     * @param variant the product variant which holds attributes
     * @param productType the product type belonging to the product variant
     * @param attrNamesToShow a list containing the attribute names (the name is used as key) which clafies which attributes are allowed to displayed and also give an order to display them
     * @return a list of pairs where the key corresponds to a translated label and the value to the formatted value
     */
    public List<Map.Entry<String, String>> createAttributeEntryList(final ProductVariant variant, final Reference<ProductType> productType, final List<String> attrNamesToShow) {
        return variant.getAttributes().stream()
                .filter(a -> attrNamesToShow.contains(a.getName()))//remove attributes not in whitelist
                //sort so that the order is like in attrNamesToShow
                .sorted(Comparator.comparingInt(a -> attrNamesToShow.indexOf(a.getName())))
                .map(attribute -> createAttributeEntry(attribute, productType))
                .collect(toList());
    }

    /**
     * Creates an entry for a single attribute with translated and formatted label and value by requiring a reference to the product type.
     *
     * @param attribute the attribute as data source
     * @param productTypeRef the product type belonging to product containing the attributes
     * @return row data
     */
    @Nullable
    public Map.Entry<String, String> createAttributeEntry(final Attribute attribute, final Referenceable<ProductType> productTypeRef) {
        return findProductType(productTypeRef)
                .map(productType -> {
                    final String translatedValue = convertWithProductType(attribute, productType);
                    final String translatedLabel = productType.findAttribute(attribute.getName())
                            .map(ptA -> ptA.getLabel().get(locales))
                            .orElse(null);
                    return new StringStringMapEntry(translatedLabel, translatedValue);
                })
                .orElse(null);
    }

    protected Locale locale() {
        return getLocales().get(0);
    }

    protected List<Locale> getLocales() {
        return locales;
    }

    @Nullable
    protected <X> String convertReference(final Reference<X> reference, final ProductType productType) {
        return reference.getId();
    }

    @Nullable
    protected <X> String convertReferenceSet(final Set<Reference<X>> referenceSet, final ProductType productType) {
        return collectToStringPostSorted(referenceSet.stream().map(r -> convertReference(r, productType)));
    }

    @Override
    protected String convertStringSet(final Set<String> stringSet, final Attribute attribute, final ProductType productType) {
        return collectToStringPreSorted(stringSet.stream());
    }

    @Override
    protected String convertProductTypeReferenceSet(final Set<Reference<ProductType>> productTypeReferenceSet, final Attribute attribute, final ProductType productType) {
        return convertReferenceSet(productTypeReferenceSet, productType);
    }

    @Override
    protected String convertProductTypeReference(final Reference<ProductType> productTypeReference, final Attribute attribute, final ProductType productType) {
        return convertReference(productTypeReference, productType);
    }

    @Override
    protected String convertProductReferenceSet(final Set<Reference<Product>> referenceSet, final Attribute attribute, final ProductType productType) {
        return convertReferenceSet(referenceSet, productType);
    }

    @Override
    protected String convertProductReference(final Reference<Product> productReference, final Attribute attribute, final ProductType productType) {
        return convertReference(productReference, productType);
    }

    @Override
    protected String convertMoneySet(final Set<MonetaryAmount> monetaryAmountSet, final Attribute attribute, final ProductType productType) {
        final Comparator<MonetaryAmount> comparator = MonetaryFunctions.sortCurrencyUnit().thenComparing(MonetaryFunctions.sortNumber());
        return collectToString(monetaryAmountSet.stream().sorted(comparator).map(money -> convertMoney(money, attribute, productType)));
    }

    @Override
    protected String convertLongSet(final Set<Long> longSet, final Attribute attribute, final ProductType productType) {
        return collectToString(longSet.stream().sorted().map(value -> convertLong(value, attribute, productType)));
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
    protected String convertLong(final Long longValue, final Attribute attribute, final ProductType productType) {
        return longValue.toString();
    }

    @Override
    protected String convertTimeSet(final Set<LocalTime> timeSet, final Attribute attribute, final ProductType productType) {
        return collectToString(timeSet.stream().sorted().map(value -> convertTime(value, attribute, productType)));
    }

    @Override
    protected String convertTime(final LocalTime time, final Attribute attribute, final ProductType productType) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale());
        return dateTimeFormatter.format(time);
    }

    @Override
    protected String convertLocalizedStringSet(final Set<LocalizedString> localizedStringSet, final Attribute attribute, final ProductType productType) {
        return collectToStringPostSorted(localizedStringSet.stream().map(value -> convertLocalizedString(value, attribute, productType)));
    }

    @Override
    protected String convertLocalizedEnumValueSet(final Set<LocalizedEnumValue> localizedEnumValueSet, final Attribute attribute, final ProductType productType) {
        return collectToStringPostSorted(localizedEnumValueSet.stream().map(value -> convertLocalizedEnumValue(value, attribute, productType)));
    }

    @Override
    protected String convertIntegerSet(final Set<Integer> integerSet, final Attribute attribute, final ProductType productType) {
        return collectToString(integerSet.stream().sorted());
    }

    @Override
    protected String convertInteger(final Integer integer, final Attribute attribute, final ProductType productType) {
        return integer.toString();
    }

    @Override
    protected String convertEnumValueSet(final Set<EnumValue> enumValueSet, final Attribute attribute, final ProductType productType) {
        return collectToStringPostSorted(enumValueSet.stream().map(value -> convertEnumValue(value, attribute, productType)));
    }

    @Override
    protected String convertDoubleSet(final Set<Double> doubleSet, final Attribute attribute, final ProductType productType) {
        return collectToString(doubleSet.stream().sorted());
    }

    @Override
    protected String convertDouble(final Double doubleValue, final Attribute attribute, final ProductType productType) {
        return doubleValue.toString();
    }

    @Override
    protected String convertDateTimeSet(final Set<ZonedDateTime> zonedDateTimeSet, final Attribute attribute, final ProductType productType) {
        return collectToString(zonedDateTimeSet.stream().sorted().map(value -> convertDateTime(value, attribute, productType)));
    }

    @Override
    protected String convertDateSet(final Set<LocalDate> dateSet, final Attribute attribute, final ProductType productType) {
        return collectToString(dateSet.stream().sorted().map(value -> convertDate(value, attribute, productType)));
    }

    @Override
    protected String convertChannelReferenceSet(final Set<Reference<Channel>> channelReferenceSet, final Attribute attribute, final ProductType productType) {
        return convertReferenceSet(channelReferenceSet, productType);
    }

    @Override
    protected String convertChannelReference(final Reference<Channel> channelReference, final Attribute attribute, final ProductType productType) {
        return convertReference(channelReference, productType);
    }

    @Override
    protected String convertCategoryReferenceSet(final Set<Reference<Category>> categoryReferenceSet, final Attribute attribute, final ProductType productType) {
        return convertReferenceSet(categoryReferenceSet, productType);
    }

    @Override
    protected String convertCategoryReference(final Reference<Category> categoryReference, final Attribute attribute, final ProductType productType) {
        return convertReference(categoryReference, productType);
    }

    @Override
    protected String convertBooleanSet(final Set<Boolean> booleanSet, final Attribute attribute, final ProductType productType) {
        return collectToString(booleanSet.stream().sorted().map(value -> convertBoolean(value, attribute, productType)));
    }

    @Override
    protected String convertString(final String stringValue, final Attribute attribute, final ProductType productType) {
        return stringValue;
    }

    @Override
    protected String convertMoney(final MonetaryAmount moneyValue, final Attribute attribute, final ProductType productType) {
        final MonetaryAmountFormat monetaryAmountFormat = MonetaryFormats.getAmountFormat(locale());
        return monetaryAmountFormat.format(moneyValue);
    }

    @Override
    protected String convertLocalizedString(final LocalizedString localizedString, final Attribute attribute, final ProductType productType) {
        return localizedString.getTranslation(getLocales());
    }

    @Override
    protected String convertLocalizedEnumValue(final LocalizedEnumValue localizedEnumValue, final Attribute attribute, final ProductType productType) {
        return localizedEnumValue.getLabel().getTranslation(getLocales());
    }

    @Override
    protected String convertEnumValue(final EnumValue enumValue, final Attribute attribute, final ProductType productType) {
        return enumValue.getLabel();
    }

    @Override
    protected String convertDateTime(final ZonedDateTime dateTimeValue, final Attribute attribute, final ProductType productType) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale());
        return dateTimeFormatter.format(dateTimeValue);
    }

    @Override
    protected String convertDate(final LocalDate dateValue, final Attribute attribute, final ProductType productType) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(locale());
        return dateTimeFormatter.format(dateValue);
    }

    @Override
    protected String convertBoolean(final Boolean booleanValue, final Attribute attribute, final ProductType productType) {
        return booleanValue.toString();
    }

    private static class StringStringMapEntry extends Base implements Map.Entry<String, String> {
        private final String key;
        private String value;

        private StringStringMapEntry(final String key, final String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String getKey() {
            return key;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public String setValue(final String value) {
            return value;
        }
    }
}
