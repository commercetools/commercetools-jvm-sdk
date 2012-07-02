package sphere.model;

public class Money {
    private int centAmount;
    private String currencyCode;

    public int getCentAmount() { return centAmount; }
    public String getCurrencyCode() { return currencyCode; }

    public Money(int centAmount, String currencyCode) {
        this.centAmount = centAmount;
        this.currencyCode = currencyCode;
    }

    private Money() { }
    
    @Override
    public String toString() {
        return (this.centAmount / 100) + " " + this.currencyCode;
    }
}
