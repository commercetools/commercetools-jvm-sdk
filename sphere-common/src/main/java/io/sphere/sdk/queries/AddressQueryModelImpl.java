package io.sphere.sdk.queries;

final class AddressQueryModelImpl<T> extends QueryModelImpl<T> implements AddressQueryModel<T> {
    public AddressQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQueryModel<T> additionalAddressInfo() {
        return stringModel("additionalAddressInfo");
    }

    @Override
    public StringQueryModel<T> id() {
        return stringModel("id");
    }

    @Override
    public StringQueryModel<T> title() {
        return stringModel("title");
    }

    @Override
    public StringQueryModel<T> salutation() {
        return stringModel("salutation");
    }

    @Override
    public StringQueryModel<T> firstName() {
        return stringModel("firstName");
    }

    @Override
    public StringQueryModel<T> lastName() {
        return stringModel("lastName");
    }

    @Override
    public StringQueryModel<T> streetName() {
        return stringModel("streetName");
    }

    @Override
    public StringQueryModel<T> streetNumber() {
        return stringModel("streetNumber");
    }

    @Override
    public StringQueryModel<T> additionalStreetInfo() {
        return stringModel("additionalStreetInfo");
    }

    @Override
    public StringQueryModel<T> postalCode() {
        return stringModel("postalCode");
    }

    @Override
    public StringQueryModel<T> city() {
        return stringModel("city");
    }

    @Override
    public StringQueryModel<T> region() {
        return stringModel("region");
    }

    @Override
    public StringQueryModel<T> state() {
        return stringModel("state");
    }

    @Override
    public StringQueryModel<T> company() {
        return stringModel("company");
    }

    @Override
    public StringQueryModel<T> department() {
        return stringModel("department");
    }

    @Override
    public StringQueryModel<T> building() {
        return stringModel("building");
    }

    @Override
    public StringQueryModel<T> apartment() {
        return stringModel("apartment");
    }

    @Override
    public StringQueryModel<T> pOBox() {
        return stringModel("pOBox");
    }

    @Override
    public StringQueryModel<T> phone() {
        return stringModel("phone");
    }

    @Override
    public StringQueryModel<T> mobile() {
        return stringModel("mobile");
    }

    @Override
    public StringQueryModel<T> email() {
        return stringModel("email");
    }

    @Override
    public CountryQueryModel<T> country() {
        return countryQueryModel("country");
    }

    @Override
    public QueryPredicate<T> isNotPresent() {
        return isNotPresentPredicate();
    }

    @Override
    public QueryPredicate<T> isPresent() {
        return isPresentPredicate();
    }
}
