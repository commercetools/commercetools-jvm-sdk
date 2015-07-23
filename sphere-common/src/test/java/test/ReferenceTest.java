package test;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.TestEntity;
import org.junit.Test;

import static io.sphere.sdk.json.SphereJsonUtils.newObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

public class ReferenceTest {
    private static final String typeId = "typeId";
    private static final String id = "123";

    @Test
    public void createEmptyReference() throws Exception {
        final Reference<TestEntity> reference = newEmptyReference();
        assertThat(reference.getId()).isEqualTo(id);
        assertThat(reference.getTypeId()).isEqualTo(typeId);
        assertThat(reference.getObj()).isNull();
    }

    @Test
    public void createFilledReference() throws Exception {
        final Reference<TestEntity> reference = newFilledReference();
        assertThat(reference.getId()).isEqualTo(id);
        assertThat(reference.getTypeId()).isEqualTo(typeId);
        assertThat(reference.getObj()).isEqualTo(new TestEntity("value"));
    }

    @Test
    public void toStringContainsTheImportantFields() throws Exception {
        assertThat(newEmptyReference().toString()).isEqualTo("Reference{typeId='typeId', id='123', obj=null}");
        assertThat(newFilledReference().toString()).isEqualTo("Reference{typeId='typeId', id='123', obj=TestEntity{foo='value'}}");
    }

    @Test
    public void implementEquals() throws Exception {
        assertThat(newFilledReference()).isEqualTo(newFilledReference());
        assertThat(newEmptyReference()).isEqualTo(newEmptyReference());
        assertThat(newFilledReference()).isEqualTo(newEmptyReference());
        assertThat(newFilledReference()).isEqualTo(newEmptyReference().filled(new TestEntity("other value")));
        assertThat(Reference.<String>of(typeId, "Foo", "x")).isNotEqualTo(newEmptyReference());
    }

    @Test
    public void serializationDoesNotContainReferencedObject() throws Exception {
        assertThat(newObjectMapper().writeValueAsString(newFilledReference())).isEqualTo("{\"typeId\":\"typeId\",\"id\":\"123\"}");
    }

    @Test
    public void deserializationCanContainExpandedObject() throws Exception {
        final String json = String.format("{\"typeId\":\"%s\",\"id\": \"%s\",\"obj\":{\"foo\":\"value\"}}", typeId, id);
        final Reference<TestEntity> actual = newObjectMapper().<Reference<TestEntity>>readValue(json, new TypeReference<Reference<TestEntity>>() {
        });
        assertThat(actual).isEqualTo(newFilledReference());
    }

    @Test
    public void deserializationWithOutExpandedObjectNullStyle() throws Exception {
        final String json = String.format("{\"typeId\":\"%s\",\"id\": \"%s\",\"obj\":null}", typeId, id);
        deserializationWithOutExpandedObject(json);
    }

    @Test
    public void deserializationWithOutExpandedObjectMissingFieldStyle() throws Exception {
        final String json = String.format("{\"typeId\":\"%s\",\"id\": \"%s\"}", typeId, id);
        deserializationWithOutExpandedObject(json);
    }

    private void deserializationWithOutExpandedObject(final String json) throws java.io.IOException {
        final Reference<TestEntity> actual = newObjectMapper().<Reference<TestEntity>>readValue(json, new TypeReference<Reference<TestEntity>>() {
        });
        assertThat(actual).isEqualTo(newEmptyReference());
    }

    private Reference<TestEntity> newFilledReference() {
        return newEmptyReference().filled(new TestEntity("value"));
    }

    private Reference<TestEntity> newEmptyReference() {
        return Reference.<TestEntity>of(typeId, id);
    }
}
