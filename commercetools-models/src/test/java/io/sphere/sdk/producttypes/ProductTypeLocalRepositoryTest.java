package io.sphere.sdk.producttypes;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.json.SphereJsonUtils;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ProductTypeLocalRepositoryTest {
    private static final List<ProductType> productTypes = SphereJsonUtils.readObjectFromResource("ProductTypeLocalRepositoryTest/product-types.json", new TypeReference<List<ProductType>>() {
    });

    @Test
    public void findById() {
        final ProductTypeLocalRepository repo = ProductTypeLocalRepository.of(productTypes);
        assertThat(repo.findById("id2")).isPresent();
        assertThat(repo.findById("id2").get().getKey()).isEqualTo("key2");
    }

    @Test
    public void findByKey() {
        final ProductTypeLocalRepository repo = ProductTypeLocalRepository.of(productTypes);
        assertThat(repo.findByKey("key2")).isPresent();
        assertThat(repo.findByKey("key2").get().getId()).isEqualTo("id2");
    }

    @Test
    public void getAll() {
        final ProductTypeLocalRepository repo = ProductTypeLocalRepository.of(productTypes);
        assertThat(repo.getAll()).extracting(e -> e.getKey()).contains("key1", "key2", "key3");
    }
}