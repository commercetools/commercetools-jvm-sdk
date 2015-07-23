package io.sphere.sdk.test;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.models.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.utils.MoneyImpl;
import io.sphere.sdk.utils.StringUtils;
import org.apache.commons.io.IOUtils;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.stream.Collectors.toList;

public final class SphereTestUtils {
    public static final int MASTER_VARIANT_ID = 1;
    private static final Random random = new Random();

    private SphereTestUtils() {
        //pure utility class
    }

    public static final Locale GERMAN = Locale.GERMAN;
    public static final Locale ENGLISH = Locale.ENGLISH;

    public static final CountryCode DE = CountryCode.DE;
    public static final CountryCode GB = CountryCode.GB;
    public static final CountryCode US = CountryCode.US;

    public static final CurrencyUnit EUR = DefaultCurrencyUnits.EUR;
    public static final CurrencyUnit USD = DefaultCurrencyUnits.USD;
    public static final MonetaryAmount EURO_CT_1 = MoneyImpl.of(new BigDecimal("0.01"), EUR);
    public static final MonetaryAmount EURO_1 = MoneyImpl.of(1, EUR);
    public static final MonetaryAmount EURO_5 = MoneyImpl.of(5, EUR);
    public static final MonetaryAmount EURO_10 = MoneyImpl.of(10, EUR);
    public static final MonetaryAmount EURO_20 = MoneyImpl.of(20, EUR);
    public static final MonetaryAmount EURO_30 = MoneyImpl.of(30, EUR);

    public static final ZonedDateTime now() {
        return ZonedDateTime. now().withZoneSameInstant(ZoneOffset.UTC);
    }

    public static final ZonedDateTime tomorrowZonedDateTime() {
        return now().plus(1, ChronoUnit.DAYS);
    }

    /**
     * Creates a LocalizedStrings for the {@code Locale.ENGLISH}.
     * @param value the value of the english translation
     * @return localized string with value
     */
    public static LocalizedStrings en(final String value) {
        return LocalizedStrings.of(Locale.ENGLISH, value);
    }

    public static String en(final LocalizedStrings localizedStrings) {
        return localizedStrings.get(ENGLISH);
    }

    public static String englishSlugOf(final WithLocalizedSlug model) {
        return model.getSlug().get(ENGLISH);
    }

    public static <T> T firstOf(final PagedQueryResult<T> result) {
        return result.head().get();
    }

    public static LocalizedStrings randomSlug() {
        return LocalizedStrings.of(Locale.ENGLISH, randomKey());
    }

    public static Address randomAddress() {
        return AddressBuilder.of(CountryCode.DE).city(randomString()).build();
    }

    public static String randomSortOrder() {
        return  "0." + randomInt();
    }

    public static String randomKey() {
        return  "random-slug-" + random.nextInt();
    }

    public static String randomEmail(final Class<?> clazz) {
        return  "random-email-" + random.nextInt() + "-" + clazz.getSimpleName() + "@test.commercetools.de";
    }

    public static String randomString() {
        return "random string " + random.nextInt() + System.currentTimeMillis();
    }

    public static int randomInt() {
        return Math.abs(random.nextInt());
    }

    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    public static float randomFloat() {
        return Math.abs(random.nextFloat());
    }

    public static MetaAttributes randomMetaAttributes() {
        final String metaTitle = "meta title" + randomString();
        final String metaDescription = "meta description" + randomString();
        final String metaKeywords = "meta keywords," + randomString();
        return MetaAttributes.metaAttributesOf(ENGLISH, metaTitle, metaDescription, metaKeywords);
    }

    public static <T> List<String> toIds(final Iterable<? extends Identifiable<T>> elements) {
        return toStream(elements).map(element -> element.getId()).collect(toList());
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> List<T> asList(T... a) {
        return Arrays.asList(a);
    }

    public static  <T> T oneOf(final Set<T> set) {
        return set.iterator().next();
    }

    public static <T> Function<T, T> consumerToFunction(final Consumer<T> consumer) {
        return x -> {
            consumer.accept(x);
            return x;
        };
    }

    public static String slugify(final String s) {
        return StringUtils.slugify(s);
    }

    public static String stringFromResource(final String resourcePath) throws Exception {
        return IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath), "UTF-8");
    }
}
