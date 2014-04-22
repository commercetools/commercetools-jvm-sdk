package io.sphere.client.model;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import io.sphere.internal.util.Util;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.map.ObjectMapper;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * A wrapper around an attribute which can be translated into a number of locales.
 * Note that even if your project only uses one language some attributes (name and description for example) will be
 * always be LocalizableStrings.
 */
public class LocalizedString {

    private Map<Locale, String> strings;

    @JsonCreator public LocalizedString(Map<Locale, String> strings){
        this.strings = ImmutableMap.copyOf(strings);
    }

    /**
     * LocalizedString containing the given entry.
     */
    public LocalizedString(Locale locale, String value){
        this.strings = ImmutableMap.of(locale, value);
    }

    /**
     * LocalizedString containing the given entries, in order.
     *
     * @throws IllegalArgumentException if duplicate keys are provided
     */
    public LocalizedString(Locale locale1, String value1, Locale locale2, String value2){
        this.strings = ImmutableMap.of(locale1, value1, locale2, value2);
    }
    
    public String toJsonString() {
        try {
            return new ObjectMapper().writer().writeValueAsString(strings);
        } catch (IOException e) {
            throw Util.toSphereException(e);
        }
    }

    /**
     * @return If the localized string contains only one translation it returns this. If there are more than one
     * a random one will be returned. If there are no translations an empty string is returned.
     */
    @Nonnull public String get(){ return getFirstOrEmpty(); }

    /**
     * Null-safe variant of `getRaw`. Tries to retrieve the translation for the specified locale
     * but if that is null or the empty string it will use a random translation. If there are no translation it will
     * return the empty string.
     */
    @Nonnull public String get(Locale loc){
        String output = strings.get(loc);
        if (Strings.isNullOrEmpty(output)){
            return "";
        }
        return output;
    }

    /**
     * Tries to retrieve the translation in the order given through the locales vargargs. If a translation is null
     * or the empty string, the next locale will be tried. If no translation could be found the empty string is
     * returned.
     */
    @Nonnull public String get(Locale... locales){
        for (Locale loc: locales){
            String translation = getRaw(loc);
            if (!Strings.isNullOrEmpty(translation)){
                return translation;
            }
        }
        return "";
    }

    /**
     * @return The raw map lookup value which may be null.
     */
    public String getRaw(Locale loc){
        return strings.get(loc);
    }

    /** Returns all available locales. */
    public Set<Locale> getLocales() {
        return strings.keySet();
    }

    private String getFirstOrEmpty() {
        Set<Locale> locales = strings.keySet();
        if (locales.isEmpty()) {
            return "";
        } else {
            Locale randomLocale = Iterables.get(locales, 0);
            return strings.get(randomLocale);
        }
    }

    @Override public String toString(){
        return toJsonString();
    }

    // ---------------------------------
    // equals() and hashCode()
    // ---------------------------------

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocalizedString localized = (LocalizedString) o;
        return this.strings.equals(localized.strings);
    }

    @Override public int hashCode() {
       return strings.hashCode();
    }
}
