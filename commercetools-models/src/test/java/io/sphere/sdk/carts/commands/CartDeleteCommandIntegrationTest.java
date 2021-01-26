package io.sphere.sdk.carts.commands;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.carts.Cart;
import io.sphere.sdk.carts.CartDraft;
import io.sphere.sdk.carts.CartDraftBuilder;
import io.sphere.sdk.carts.CartFixtures;
import io.sphere.sdk.carts.commands.updateactions.SetKey;
import io.sphere.sdk.carts.queries.CartByIdGet;
import io.sphere.sdk.carts.queries.CartQuery;
import io.sphere.sdk.queries.PagedQueryResult;
import io.sphere.sdk.test.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import static io.sphere.sdk.stores.StoreFixtures.withStore;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CartDeleteCommandIntegrationTest extends IntegrationTest {

    @Test
    public void deleteCartWithDataErasureTests() {
        Cart cart = CartFixtures.createCartWithCountry(client());
        client().executeBlocking(CartDeleteCommand.of(cart, true));
        Cart cartQueried = client().executeBlocking(CartByIdGet.of(cart));
        Assertions.assertThat(cartQueried).isNull();
    }
    
    @Test
    public void deleteCartInStoreWithDataErasure() {
        withStore(client(), store -> {
            final CartDraft cartDraft = CartDraft.of(EUR).withCountry(DE);
            final Cart cart = client().executeBlocking(CartInStoreCreateCommand.of(store.getKey(), cartDraft));
            assertThat(cart).isNotNull();
            assertThat(cart.getStore()).isNotNull();
            assertThat(cart.getStore().getKey()).isEqualTo(store.getKey());
            client().executeBlocking(CartInStoreDeleteCommand.of(store.getKey(), cart, true));
            PagedQueryResult<Cart> result = client().executeBlocking(CartQuery.of().withPredicates(cartQueryModel -> cartQueryModel.id().is(cart.getId())));
            assertThat(result.getResults().size()).isZero();
        });
    }

    @Test
    public void deleteCartByKey(){
        final CartDraft cartDraft = CartDraftBuilder.of(EUR).country(DE).build();
        final Cart cart = client().executeBlocking(CartCreateCommand.of(cartDraft));
        final String key = randomKey();
        final Cart updatedCart = client().executeBlocking(CartUpdateCommand.of(cart, SetKey.of(key)));
        assertThat(updatedCart.getKey()).isEqualTo(key);

        client().executeBlocking(CartDeleteCommand.ofKey(key, updatedCart.getVersion()));

        final PagedQueryResult<Cart> queryResult = client().executeBlocking(CartQuery.of().withPredicates(cartQueryModel -> cartQueryModel.key().is(updatedCart.getKey())));
        assertThat(queryResult.getResults()).isEmpty();
    }
}
