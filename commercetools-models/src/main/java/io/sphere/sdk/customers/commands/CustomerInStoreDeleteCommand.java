package io.sphere.sdk.customers.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.customers.expansion.CustomerExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;

import static io.sphere.sdk.client.SphereRequestUtils.urlEncode;

public interface CustomerInStoreDeleteCommand extends MetaModelReferenceExpansionDsl<Customer, CustomerInStoreDeleteCommand, CustomerExpansionModel<Customer>>, DeleteCommand<Customer>  {

    static CustomerInStoreDeleteCommand of(final String storeKey, final Versioned<Customer> versioned) {
        return new CustomerInStoreDeleteCommandImpl(storeKey, versioned,false);
    }
    
    static CustomerInStoreDeleteCommand of(final String storeKey, final Versioned<Customer> versioned,final boolean eraseData) {
        return new CustomerInStoreDeleteCommandImpl(storeKey, versioned, eraseData);
    }

    static CustomerInStoreDeleteCommand ofKey(final String storeKey, final String key, final Long version) {
        final Versioned<Customer> versioned = Versioned.of("key=" + urlEncode(key), version);//hack for simple reuse
        return of(storeKey, versioned);
    }
    
    static CustomerInStoreDeleteCommand ofKey(final String storeKey, final String key, final Long version, final boolean eraseData) {
        final Versioned<Customer> versioned = Versioned.of("key=" + urlEncode(key), version);//hack for simple reuse
        return of(storeKey, versioned, eraseData);
    }
}