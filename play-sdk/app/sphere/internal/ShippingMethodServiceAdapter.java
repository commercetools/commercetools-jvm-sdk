package sphere.internal;

import java.util.Currency;
import javax.annotation.Nonnull;
import io.sphere.client.shop.model.ShippingMethod;
import com.neovisionaries.i18n.CountryCode;
import net.jcip.annotations.Immutable;
import sphere.FetchRequest;
import sphere.QueryRequest;
import sphere.ShippingMethodService;
import sphere.util.Async;

/** ShippingMethodService with Play-specific async methods. */
@Immutable
public class ShippingMethodServiceAdapter implements ShippingMethodService {
    private final io.sphere.client.shop.ShippingMethodService service;
    public ShippingMethodServiceAdapter(@Nonnull io.sphere.client.shop.ShippingMethodService service) {
        if (service == null) throw new NullPointerException("service");
        this.service = service;
    }

    @Override public FetchRequest<ShippingMethod> byId(String id) {
        return Async.adapt(service.byId(id));
    }

    @Override public QueryRequest<ShippingMethod> all() {
        return Async.adapt(service.all());
    }

    @Override
    public QueryRequest<ShippingMethod> byLocation(CountryCode country, String state, Currency currency) {
        return Async.adapt(service.byLocation(country, state, currency));
    }

    @Override
    public QueryRequest<ShippingMethod> byLocation(CountryCode country, Currency currency) {
        return Async.adapt(service.byLocation(country, currency));
    }
}
