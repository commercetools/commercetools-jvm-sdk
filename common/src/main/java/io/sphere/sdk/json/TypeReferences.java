package io.sphere.sdk.json;

import com.fasterxml.jackson.core.type.TypeReference;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public final class TypeReferences {
    private TypeReferences() {
    }

    public static TypeReference<Boolean> booleanTypeReference() {
        return new TypeReference<Boolean>() {
            @Override
            public String toString() {
                return "TypeReference<Boolean>";
            }
        };
    }

    public static TypeReference<Double> doubleTypeReference() {
        return new TypeReference<Double>() {
            @Override
            public String toString() {
                return "TypeReference<Double>";
            }
        };
    }

    public static TypeReference<Long> longTypeReference() {
        return new TypeReference<Long>() {
            @Override
            public String toString() {
                return "TypeReference<Long>";
            }
        };
    }

    public static TypeReference<String> stringTypeReference() {
        return new TypeReference<String>() {
            @Override
            public String toString() {
                return "TypeReference<String>";
            }
        };
    }

    public static TypeReference<LocalDate> localDateTypeReference() {
        return new TypeReference<LocalDate>() {
            @Override
            public String toString() {
                return "TypeReference<LocalDate>";
            }
        };
    }

    public static TypeReference<LocalTime> localTimeTypeReference() {
        return new TypeReference<LocalTime>() {
            @Override
            public String toString() {
                return "TypeReference<LocalTime>";
            }
        };
    }

    public static TypeReference<Instant> instantTypeReference() {
        return new TypeReference<Instant>() {
            @Override
            public String toString() {
                return "TypeReference<Instant>";
            }
        };
    }

    public static TypeReference<MonetaryAmount> monetaryAmountTypeReference() {
        return new TypeReference<MonetaryAmount>() {
            @Override
            public String toString() {
                return "TypeReference<MonetaryAmount>";
            }
        };
    }
}
