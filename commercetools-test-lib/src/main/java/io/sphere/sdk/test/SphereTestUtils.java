package io.sphere.sdk.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.*;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.utils.MoneyImpl;
import io.sphere.sdk.utils.SphereInternalUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.SoftAssertions;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.sphere.sdk.utils.SphereInternalUtils.toStream;
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
    public static final MonetaryAmount EURO_12 = MoneyImpl.of(12, EUR);
    public static final MonetaryAmount EURO_15 = MoneyImpl.of(15, EUR);
    public static final MonetaryAmount EURO_20 = MoneyImpl.of(20, EUR);
    public static final MonetaryAmount EURO_25 = MoneyImpl.of(25, EUR);
    public static final MonetaryAmount EURO_30 = MoneyImpl.of(30, EUR);
    public static final MonetaryAmount EURO_36 = MoneyImpl.of(36, EUR);
    public static final MonetaryAmount EURO_40 = MoneyImpl.of(40, EUR);
    public static final MonetaryAmount USD_30 = MoneyImpl.of(30, USD);
    public static final MonetaryAmount USD_20 = MoneyImpl.of(20, USD);

    public static ZonedDateTime now() {
        return ZonedDateTime. now().withZoneSameInstant(ZoneOffset.UTC);
    }

    public static ZonedDateTime tomorrowZonedDateTime() {
        return now().plus(1, ChronoUnit.DAYS);
    }

    /**
     * Creates a LocalizedString for the {@code Locale.ENGLISH}.
     * @param value the value of the english translation
     * @return localized string with value
     */
    public static LocalizedString en(final String value) {
        return LocalizedString.of(Locale.ENGLISH, value);
    }

    public static String en(final LocalizedString localizedString) {
        return localizedString.get(ENGLISH);
    }

    public static String englishSlugOf(final WithLocalizedSlug model) {
        return model.getSlug().get(ENGLISH);
    }

    public static <T> T firstOf(final PagedQueryResult<T> result) {
        return result.head().get();
    }

    public static LocalizedString randomSlug() {
        return LocalizedString.of(Locale.ENGLISH, randomKey());
    }

    public static String randomSortOrder() {
        final int append = 5;//hack to not have a trailing 0 which is not accepted in sphere
        return  "0." + randomInt() + append;
    }

    public static String randomKey() {
        return  "random-slug-" + random.nextInt();
    }

    /**
     * Creates an english random localized string.
     *
     * @return a random localized
     */
    public static LocalizedString randomLocalizedString() {
        return  en("random-localized-string-" + random.nextInt());
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

    public static <T> Function<T, T> consumerToFunction(final Consumer<? super T> consumer) {
        return x -> {
            consumer.accept(x);
            return x;
        };
    }

    public static String slugify(final String s) {
        return SphereInternalUtils.slugify(s);
    }

    public static String stringFromResource(final String resourcePath) {
        try {
            return IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath), "UTF-8");
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static JsonNode jsonNodeFromResource(final String resourcePath) {
        return SphereJsonUtils.parse(stringFromResource(resourcePath));
    }

    public static  <T> T draftFromJsonResource(final String resourcePath, final Class<T> clazz, final JsonNodeReferenceResolver referenceResolver) {
        final JsonNode draftAsJson = SphereTestUtils.jsonNodeFromResource(resourcePath);
        referenceResolver.replaceIds(draftAsJson);
        return SphereJsonUtils.readObject(draftAsJson, clazz);
    }

    public static void assertEventually(final Duration maxWaitTime, final Duration waitBeforeRetry, final Runnable block) {
        final long timeOutAt = System.currentTimeMillis() + maxWaitTime.toMillis();
        while (true) {
            try {
                block.run();

                // the block executed without throwing an exception, return
                return;
            } catch (AssertionError | ErrorResponseException e) {
                if (e instanceof ErrorResponseException && !((ErrorResponseException) e).hasErrorCode("SearchFacetPathNotFound")) {
                    throw e;
                }
                if (System.currentTimeMillis() > timeOutAt) {
                    throw e;
                }
            }

            try {
                Thread.sleep(waitBeforeRetry.toMillis());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void assertEventually(final Consumer<SoftAssertions> assertionsConsumer) {
        final Runnable block = () -> {
            final SoftAssertions softly = new SoftAssertions();
            assertionsConsumer.accept(softly);
            softly.assertAll();
        };
        assertEventually(block);
    }

    public static void assertEventually(final Runnable block) {
        final Boolean useLongTimeout = "true".equals(System.getenv("TRAVIS"))
                || StringUtils.isNotEmpty(System.getenv("TEAMCITY_VERSION"))
                || StringUtils.isNoneEmpty(System.getenv("GITHUB_WORKSPACE"));
        final Duration maxWaitTime = Duration.ofSeconds(useLongTimeout ? 60 : 30);
        final Duration waitBeforeRetry = Duration.ofMillis(100);
        assertEventually(maxWaitTime, waitBeforeRetry, block);
    }
}
