package io.sphere.client.shop.model;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import io.sphere.client.model.LocalizedString;
import io.sphere.internal.util.Log;
import io.sphere.client.model.Money;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Map;

/** Custom attribute of a {@link io.sphere.client.shop.model.Product}. */
@Immutable
public class Attribute {
    @Nonnull private final String name;
    private final Object value;

    /** Name of this custom attribute. */
    @Nonnull public String getName() { return name; }

    /** Value of this custom attribute. */
    public Object getValue() { return value; }

    @JsonCreator
    public Attribute(@JsonProperty("name") String name, @JsonProperty("value") Object value) {
        if (Strings.isNullOrEmpty(name)) throw new IllegalArgumentException("Attribute name can't be empty.");
        this.name = name;
        this.value = value;
    }

    // ------------------------------
    // Defaults
    // ------------------------------

    static String                   defaultString           = "";
    static int                      defaultInt              = 0;
    static double                   defaultDouble           = 0.0;
    static Money                    defaultMoney            = null;
    static DateTime                 defaultDateTime         = null;
    static Enum                     defaultEnum             = new Enum("", "");
    public static LocalizedString   defaultLocalizedString  = new LocalizedString(ImmutableMap.<Locale, String>of());

    // ------------------------------
    // Typed value getters
    // ------------------------------

    /** If this is a string attribute, returns the string value.
     *  @return The value or empty string if the value is not a string. */
    public String getString() {
        Object v = getValue();
        if (v == null || !(v instanceof String)) return defaultString;
        return (String)v;
    }

    /** If this is a string attribute, returns the string value.
     *  @return The value or empty string if the value is not a string. */
    public LocalizedString getLocalizedString() {
        Object v = getValue();
        if (v == null || !(v instanceof LocalizedString)) return defaultLocalizedString;
        return (LocalizedString)v;
    }

    /** If this is a number attribute, returns the integer value.
     *  @return The value or 0 if the value is not an integer. */
    public int getInt() {
        Object v = getValue();
        if (!(v instanceof Integer)) return defaultInt;
        return (Integer)v;
    }

    /** If this is a number attribute, returns the double value.
     *  @return The value or 0.0 if the value is not an double. */
    public double getDouble() {
        Object v = getValue();
        // getDouble should work for integers too
        if (v instanceof Integer) return getInt();
        if (!(v instanceof Double)) return defaultDouble;
        return (Double)v;
    }

    /** If this is a money attribute, returns the money value.
     *  @return The value or null if the value is not a money instance. */
    public Money getMoney() {
        // Jackson has no way of knowing that an attribute is a money attribute and its value should be parsed as Money.
        // It sees a json object {'currencyCode':'EUR','centAmount':1200} and parses it as LinkedHashMap.
        Object v = getValue();
        if (!(v instanceof Map)) return defaultMoney;
        return new ObjectMapper().convertValue(v, Money.class);
    }

    /** If this is an enum attribute, returns the value.
     *  @return The value or the empty string if the value is not an enum instance. */
    public Enum getEnum() {
        Object v = getValue();
        if (!(v instanceof Map)) return defaultEnum;
        else {
            Map map = (Map) v;
            String label = (String) map.get("label");
            if (Strings.isNullOrEmpty(label)){
                return defaultEnum;
            }
            else return new Enum((String) map.get("key"), label);
        }
    }

    private static DateTimeFormatter dateTimeFormat = ISODateTimeFormat.dateTimeParser();
    /** If this is a DateTime attribute, returns the DateTime value.
     *  @return The value or null if the value is not a DateTime. */
    public DateTime getDateTime() {
        // In case user creates attributes with LocalDate values
        Object v = getValue();
        if (v instanceof DateTime) return (DateTime)v;
        // The backend returns dates and times as strings
        String s = getString();
        if (Strings.isNullOrEmpty(s)) return defaultDateTime;
        try {
            return dateTimeFormat.parseDateTime(s);
        } catch (IllegalArgumentException e) {
            // To handle getDate() called on string attributes
            Log.error("Invalid DateTime: " + e.getMessage());
            return defaultDateTime;
        }
    }

    @Override public String toString() {
        return "[" + getName() + ": " + getValue() + "]";
    }

    // ---------------------------------
    // equals() and hashCode()
    // ---------------------------------

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attribute attribute = (Attribute) o;
        if (!name.equals(attribute.name)) return false;
        if (value != null ? !value.equals(attribute.value) : attribute.value != null) return false;
        return true;
    }

    @Override public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    /**
     * The value of a custom enum attribute.
     */
    public static class Enum {
        /**
         * The unique and machine-readable value for this enum value.
         */
        public final String key;
        /**
         * The human-readable, and translated label for this value.
         */
        public final String label;

        public Enum(String key, String label) {
            this.key = key;
            this.label = label;
        }

        @Override
        public String toString() {
            return "[Enum key='" + key + "' value='" + label +"']";
        }
    }
}
