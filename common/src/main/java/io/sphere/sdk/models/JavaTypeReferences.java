package io.sphere.sdk.models;

import com.fasterxml.jackson.core.type.TypeReference;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

final class JavaTypeReferences {
    private JavaTypeReferences() {
    }

    static TypeReference<Boolean> booleanTypeReference() {
        return new TypeReference<Boolean>() {
            @Override
            public String toString() {
                return "TypeReference<Boolean>";
            }
        };
    }

    static TypeReference<Double> doubleTypeReference() {
        return new TypeReference<Double>() {
            @Override
            public String toString() {
                return "TypeReference<Double>";
            }
        };
    }
    
    static TypeReference<Long> longTypeReference() {
        return new TypeReference<Long>() {
            @Override
            public String toString() {
                return "TypeReference<Long>";
            }
        };
    }

    static TypeReference<String> stringTypeReference() {
        return new TypeReference<String>() {
            @Override
            public String toString() {
                return "TypeReference<String>";
            }
        };
    }
    
    static TypeReference<LocalDate> localDateTypeReference() {
        return new TypeReference<LocalDate>() {
            @Override
            public String toString() {
                return "TypeReference<LocalDate>";
            }
        };
    }

    static TypeReference<LocalTime> localTimeTypeReference() {
        return new TypeReference<LocalTime>() {
            @Override
            public String toString() {
                return "TypeReference<LocalTime>";
            }
        };
    }
    
    static TypeReference<Instant> instantTypeReference() {
        return new TypeReference<Instant>() {
            @Override
            public String toString() {
                return "TypeReference<Instant>";
            }
        };
    }
}
