package io.sphere.sdk.models;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public abstract class DefaultModelSubclassTest<T extends DefaultModel> {

    private T example1;
    private T example2;

    public static class ExampleData {
        public String id = "id-ExampleDataBase";
        public long version = 12;
        public DateTime createdAt = new DateTime("2004-11-13T21:39:45.618-08:00");
        public DateTime lastModifiedAt = createdAt.plusDays(3);

        public ExampleData version(final long version) {
            this.version = version;
            return this;
        }
    }

    @Before
    public void fixtures() {
        example1 = newExample1();
        example2 = newExample2();
    }

    @Test
    public void getId() {
        assertThat(example1.getId()).isEqualTo(new ExampleData().id);
    }

    @Test
    public void getVersion() {
        assertThat(example1.getVersion()).isEqualTo(new ExampleData().version);
    }

    @Test
    public void getCreatedAt() {
        assertThat(example1.getCreatedAt()).isEqualTo(new ExampleData().createdAt);
    }

    @Test
    public void getLastModifiedAt() {
        assertThat(example1.getLastModifiedAt()).isEqualTo(new ExampleData().lastModifiedAt);
    }


    @Test
    public void equalsForIdentity() {
        assertThat(example1.equals(example1)).isTrue();
    }

    @Test
    public void equalsForValueEquality() {
        assertThat(newExample1().equals(newExample1())).isTrue();
    }

    @Test
    public void equalsForDifferentClasses() {
        assertThat(example1.equals(1)).isFalse();
    }

    @Test
    public void equalsForDifferenceInBaseFields() {
        assertThat(example1.equals(newExample1(new ExampleData().version(500)))).isFalse();
    }

    @Test
    public void equalsForDifferenceInSubclassFields(){
        assertThat(example1.equals(example2)).isEqualTo(example2.equals(example1)).isFalse();
    }

    @Test
    public void hashCodeIsStable() {
        assertThat(example1.hashCode()).isEqualTo(example1.hashCode());
    }

    @Test
    public void hashCodeIsStableForSameDataInDifferentIncarnations() {
        assertThat(newExample1().hashCode()).isEqualTo(newExample1().hashCode());
    }

    @Test
    public void hashCodeDiffersForBaseAttributes() {
        assertThat(newExample1().hashCode()).isNotEqualTo(newExample1(new ExampleData().version(500)).hashCode());
    }

    @Test
    public void hashCodeDiffersForSubclassAttributes() {
        assertThat(example1.hashCode()).isNotEqualTo(example2.hashCode());
    }

    @Test
    public void toStringContainsBaseAttributes() {
        assertThat(example1.toString()).
                contains(example1.getId()).
                contains("" + example1.getVersion()).
                contains(example1.getCreatedAt().toString()).
                contains(example1.getLastModifiedAt().toString());
    }

    @Test
    public abstract void toStringContainsSubclassAttributes();

    protected DefaultModelBuilder<T> applyDataToBuilder(final DefaultModelBuilder<T> builder, final ExampleData data) {
        builder.setId(data.id);
        builder.setVersion(data.version);
        builder.setCreatedAt(data.createdAt);
        builder.setLastModifiedAt(data.lastModifiedAt);
        return builder;
    }

    protected T newExample2() {
        return applyDataToBuilder(newExample2Builder(), new ExampleData()).build();
    }

    protected T newExample1() {
        return newExample1(new ExampleData());
    }

    protected T newExample1(ExampleData data) {
        return applyDataToBuilder(newExample1Builder(), data).build();
    }

    protected abstract DefaultModelBuilder<T> newExample1Builder();

    protected abstract DefaultModelBuilder<T> newExample2Builder();


    public abstract void testSubclassGettersOfExample1(final T model);

    @Test
    public final void testSubclassGetters() {
        testSubclassGettersOfExample1(example1);
    }
}
