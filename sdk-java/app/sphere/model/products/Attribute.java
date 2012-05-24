package sphere.model.products;

public class Attribute {
    String name;
    Object value;

    public String getName() {
        return name;
    }
    public Object getValue() {
        return value;
    }

    public Attribute(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    // for the JSON deserializer
    private Attribute() { }
}
