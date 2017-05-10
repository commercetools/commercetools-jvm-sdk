package io.sphere.sdk.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.models.Base;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Set;

public final class TypeReferences extends Base {
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

    public static TypeReference<BigDecimal> bigDecimalTypeReference() {
        return new TypeReference<BigDecimal>() {
            @Override
            public String toString() {
                return "TypeReference<BigDecimal>";
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

    public static TypeReference<Integer> integerTypeReference() {
        return new TypeReference<Integer>() {
            @Override
            public String toString() {
                return "TypeReference<Integer>";
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

    public static TypeReference<ZonedDateTime> zonedDateTimeTypeReference() {
        return new TypeReference<ZonedDateTime>() {
            @Override
            public String toString() {
                return "TypeReference<ZonedDateTime>";
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
    
    public static TypeReference<JsonNode> jsonNodeTypeReference() {
        return new TypeReference<JsonNode>() {
            @Override
            public String toString() {
                return "TypeReference<JsonNode>";
            }
        };
    }

    public static TypeReference<Locale> localeTypeReference() {
        return new TypeReference<Locale>() {
            @Override
            public String toString() {
                return "TypeReference<Locale>";
            }
        };
    }

    public static TypeReference<Set<String>> stringSetTypeReference() {
        return new TypeReference<Set<String>>() {
            @Override
            public String toString() {
                return "TypeReference<Set<String>>";
            }
        };
    }
}
