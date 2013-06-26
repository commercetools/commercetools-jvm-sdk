package io.sphere.client.model;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import org.codehaus.jackson.annotate.JsonCreator;

import javax.annotation.Nonnull;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class LocalizedString {

    private Map<Locale, String> strings;

    @JsonCreator
    public LocalizedString(Map<Locale, String> strings){
        this.strings = strings;
    }

    public String getRaw(Locale loc){
        return strings.get(loc);
    }

    @Nonnull
    public String get(Locale loc){
        String output = strings.get(loc);
        if (Strings.isNullOrEmpty(output)){
            Set<Locale> locales = strings.keySet();
            if (locales.isEmpty()){
                output = "";
            }
            else {
                loc = Iterables.get(locales, 0);
                output = strings.get(loc);
            }
        }
        return output;
    }

    @Override
    public String toString(){
        return "[LocalizedString " + strings.toString() + "]";
    }
}
