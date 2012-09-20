package de.commercetools.sphere.client.model;

import net.jcip.annotations.*;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

@Immutable
public class Money {
    private final long centAmount;
    private final String currencyCode;

    // tell the JSON deserializer to use this constructor (to be able to make fields final)
    @JsonCreator
    public Money(@JsonProperty("centAmount") long centAmount, @JsonProperty("currencyCode") String currencyCode) {
        this.centAmount = centAmount;
        this.currencyCode = currencyCode;
    }

    /** Returns a Money instance that is a sum of this instance and given instance. */
    public Money plus(Money that) {
        if (that.currencyCode != this.currencyCode)
            throw new IllegalArgumentException(String.format("Can't add Money instances of different currency: %s + %s", this, that));
        return new Money(this.centAmount + that.centAmount, this.currencyCode);
    }

    public long getCentAmount() { return centAmount; }
    public String getCurrencyCode() { return currencyCode; }

    @Override
    public String toString() {
        return (this.centAmount / 100) + " " + this.currencyCode;
    }
}
