package sphere;

import java.util.Currency;

//TODO: this interface was needed for testing. Verify the decision and add more fields from ConfigImpl.
public interface Config {

    /** Currency used for shopping carts. */
    public Currency shopCurrency();
}
