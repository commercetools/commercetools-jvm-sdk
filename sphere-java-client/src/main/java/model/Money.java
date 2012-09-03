package de.commercetools.sphere.client.model;

import net.jcip.annotations.*;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

@Immutable
public class Money {

    private final int centAmount;
    private final String currencyCode;

    public int getCentAmount() { return centAmount; }
    public String getCurrencyCode() { return currencyCode; }

    @JsonCreator
    public Money(@JsonProperty("centAmount") int centAmount, @JsonProperty("currencyCode") String currencyCode) {
        this.centAmount = centAmount;
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return (this.centAmount / 100) + " " + this.currencyCode;
    }
}
