package io.sphere.sdk.queries;

public interface AddressQueryModel<T> extends OptionalQueryModel<T> {
    StringQueryModel<T> id(); 
    StringQueryModel<T> title(); 
    StringQueryModel<T> salutation(); 
    StringQueryModel<T> firstName(); 
    StringQueryModel<T> lastName(); 
    StringQueryModel<T> streetName(); 
    StringQueryModel<T> streetNumber(); 
    StringQueryModel<T> additionalStreetInfo(); 
    StringQueryModel<T> postalCode(); 
    StringQueryModel<T> city(); 
    StringQueryModel<T> region(); 
    StringQueryModel<T> state(); 
    StringQueryModel<T> company(); 
    StringQueryModel<T> department(); 
    StringQueryModel<T> building(); 
    StringQueryModel<T> apartment(); 
    StringQueryModel<T> pOBox(); 
    StringQueryModel<T> phone(); 
    StringQueryModel<T> mobile(); 
    StringQueryModel<T> email(); 
    StringQueryModel<T> additionalAddressInfo(); 
    CountryQueryModel<T> country();

    @Override
    QueryPredicate<T> isNotPresent();

    @Override
    QueryPredicate<T> isPresent();
}
