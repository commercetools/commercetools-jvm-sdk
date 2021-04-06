package io.sphere.sdk.models;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.test.SphereTestUtils;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.CustomFieldsDraft;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

public class AddressTest {
    @Test
    public void equalsIgnoreId() {
        final Address addressWithoutId = Address.of(CountryCode.DE);
        final Address addressWithId = addressWithoutId.withId("foo");
        assertThat(addressWithoutId)
                .isNotEqualTo(addressWithId)
                .matches(address -> address.equalsIgnoreId(addressWithId));
    }

    @Test
    public void fax() {
        final String fax = "030000000";
        final Address address = Address.of(CountryCode.DE).withFax(fax);
        assertThat(address.getFax()).isEqualTo(fax);
    }

    @Test
    public void externalId() {
        final String externalId = "030000000";
        final Address address = Address.of(CountryCode.DE).withExternalId(externalId);
        assertThat(address.getExternalId()).isEqualTo(externalId);
    }

    @Test
    public void addressPOBox() {
        final Address address = SphereJsonUtils.readObject(SphereTestUtils.stringFromResource("address.json"), Address.class);
        assertThat(address.getPoBox()).isEqualTo("1234567890");

        final String poAddress = SphereJsonUtils.toJsonString(Address.of(CountryCode.DE).withPoBox("12345"));
        assertThat(poAddress).isEqualTo("{\"country\":\"DE\",\"pOBox\":\"12345\"}");
    }

    @Test
    public void customFieldsWrite() {
        final Address address = Address.of(CountryCode.DE);
        final String customAddress = SphereJsonUtils.toJsonString(address.withCustomFields(CustomFieldsDraft.ofTypeKeyAndObjects("test", new HashMap<>())));

        assertThat(customAddress).isEqualTo("{\"country\":\"DE\",\"custom\":{\"type\":{\"key\":\"test\"},\"fields\":{}}}");
    }

    @Test
    public void customFieldsRead() {
        final Address address = SphereJsonUtils.readObject(SphereTestUtils.stringFromResource("address.json"), Address.class);
        assertThat(address.getCustomFieldsDraft()).isNull();

        final CustomFields fields = address.getCustomFields();
        assertThat(fields).isInstanceOf(CustomFields.class);
        assertThat(fields.getFieldAsString("foo")).isEqualTo("bar");

        final Address customAddress = Address.of(CountryCode.DE).withCustomFields(CustomFieldsDraft.ofTypeKeyAndObjects("test", new HashMap<>()));
        assertThat(customAddress.getCustomFieldsDraft()).isInstanceOf(CustomFieldsDraft.class);
        assertThat(customAddress.getCustomFields()).isNull();

        final String customAddressString = SphereJsonUtils.toJsonString(customAddress);
        assertThat(customAddressString).isEqualTo("{\"country\":\"DE\",\"custom\":{\"type\":{\"key\":\"test\"},\"fields\":{}}}");

        final Address customAddress2 = customAddress.withCustomFields(fields);
        assertThat(customAddress2.getCustomFields()).isInstanceOf(CustomFields.class);
        assertThat(customAddress2.getCustomFieldsDraft()).isNull();

        final String customAddressString2 = SphereJsonUtils.toJsonString(customAddress2);
        assertThat(customAddressString2).isEqualTo("{\"country\":\"DE\",\"custom\":{\"type\":{\"typeId\":\"type\",\"id\":\"abc\"},\"fieldsJsonMap\":{\"foo\":\"bar\"}}}");

    }
}
