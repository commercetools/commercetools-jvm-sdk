package io.sphere.sdk.products.attributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.json.TypeReferences;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.products.AttributeContainer;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.producttypes.ProductType;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Set;

import static io.sphere.sdk.json.TypeReferences.*;

/**
 * Container which has strategies to serialize/deserialize attribute values.
 *
 * <p>Product attributes are documented <a href="{@docRoot}/io/sphere/sdk/meta/ProductAttributeDocumentation.html">here</a>.</p>
 *
 * @param <T> the type of the attribute value
 * @see NamedAttributeAccess
 */
public interface AttributeAccess<T> {
    NamedAttributeAccess<T> ofName(String name);

    AttributeMapper<T> attributeMapper();

    boolean canHandle(AttributeDefinition attributeDefinition);

    static AttributeAccess<Boolean> ofBoolean() {
        return AttributeAccessImpl.ofPrimitive(booleanTypeReference(), BooleanAttributeType.class);
    }

    static AttributeAccess<Set<Boolean>> ofBooleanSet() {
        return AttributeAccessImpl.ofSet(BooleanAttributeType.class, new TypeReference<Set<Boolean>>() {
        });
    }

    static AttributeAccess<String> ofString() {
        return AttributeAccessImpl.ofPrimitive(stringTypeReference(), StringAttributeType.class);
    }

    static AttributeAccess<Set<String>> ofStringSet() {
        return AttributeAccessImpl.ofSet(StringAttributeType.class, new TypeReference<Set<String>>() {
        });
    }

    static AttributeAccess<String> ofText() {
        return ofString();
    }

    static AttributeAccess<Set<String>> ofTextSet() {
        return ofStringSet();
    }

    static AttributeAccess<LocalizedString> ofLocalizedString() {
        return AttributeAccessImpl.ofPrimitive(LocalizedString.typeReference(), LocalizedStringAttributeType.class);
    }

    static AttributeAccess<Set<LocalizedString>> ofLocalizedStringSet() {
        return AttributeAccessImpl.ofSet(LocalizedStringAttributeType.class, new TypeReference<Set<LocalizedString>>() {
        });
    }

    static AttributeAccess<EnumValue> ofEnumValue() {
        return AttributeAccessImpl.ofEnumLike(EnumValue.typeReference(), EnumAttributeType.class);
    }

    static AttributeAccess<Set<EnumValue>> ofEnumValueSet() {
        return AttributeAccessImpl.ofEnumLikeSet(EnumAttributeType.class, new TypeReference<Set<EnumValue>>() {
        });
    }

    static AttributeAccess<LocalizedEnumValue> ofLocalizedEnumValue() {
        return AttributeAccessImpl.ofEnumLike(LocalizedEnumValue.typeReference(), LocalizedEnumAttributeType.class);
    }

    static AttributeAccess<Set<LocalizedEnumValue>> ofLocalizedEnumValueSet() {
        return AttributeAccessImpl.ofEnumLikeSet(LocalizedEnumAttributeType.class, new TypeReference<Set<LocalizedEnumValue>>() {
        });
    }

    static AttributeAccess<Double> ofDouble() {
        return AttributeAccessImpl.ofPrimitive(doubleTypeReference(), NumberAttributeType.class);
    }

    static AttributeAccess<Set<Double>> ofDoubleSet() {
        return AttributeAccessImpl.ofSet(NumberAttributeType.class, new TypeReference<Set<Double>>() {
        });
    }

    static AttributeAccess<Integer> ofInteger() {
        return AttributeAccessImpl.ofPrimitive(integerTypeReference(), NumberAttributeType.class);
    }

    static AttributeAccess<Set<Integer>> ofIntegerSet() {
        return AttributeAccessImpl.ofSet(NumberAttributeType.class, new TypeReference<Set<Integer>>() {
        });
    }

    static AttributeAccess<Long> ofLong() {
        return AttributeAccessImpl.ofPrimitive(longTypeReference(), NumberAttributeType.class);
    }

    static AttributeAccess<Set<Long>> ofLongSet() {
        return AttributeAccessImpl.ofSet(NumberAttributeType.class, new TypeReference<Set<Long>>() {
        });
    }

    static AttributeAccess<MonetaryAmount> ofMoney() {
        return AttributeAccessImpl.ofPrimitive(monetaryAmountTypeReference(), MoneyAttributeType.class);
    }

    static AttributeAccess<Set<MonetaryAmount>> ofMoneySet() {
        return AttributeAccessImpl.ofSet(MoneyAttributeType.class, new TypeReference<Set<MonetaryAmount>>() {
        });
    }

    static AttributeAccess<LocalDate> ofLocalDate() {
        return ofDate();
    }

    static AttributeAccess<Set<LocalDate>> ofLocalDateSet() {
        return ofDateSet();
    }

    static AttributeAccess<LocalDate> ofDate() {
        return AttributeAccessImpl.ofPrimitive(localDateTypeReference(), DateAttributeType.class);
    }

    static AttributeAccess<Set<LocalDate>> ofDateSet() {
        return AttributeAccessImpl.ofSet(DateAttributeType.class, new TypeReference<Set<LocalDate>>() {
        });
    }

    static AttributeAccess<LocalTime> ofLocalTime() {
        return ofTime();
    }

    static AttributeAccess<Set<LocalTime>> ofLocalTimeSet() {
        return ofTimeSet();
    }

    static AttributeAccess<LocalTime> ofTime() {
        return AttributeAccessImpl.ofPrimitive(localTimeTypeReference(), TimeAttributeType.class);
    }

    static AttributeAccess<Set<LocalTime>> ofTimeSet() {
        return AttributeAccessImpl.ofSet(TimeAttributeType.class, new TypeReference<Set<LocalTime>>() {
        });
    }

    static AttributeAccess<ZonedDateTime> ofDateTime() {
        return AttributeAccessImpl.ofPrimitive(zonedDateTimeTypeReference(), DateTimeAttributeType.class);
    }

    static AttributeAccess<Set<ZonedDateTime>> ofDateTimeSet() {
        return AttributeAccessImpl.ofSet(DateTimeAttributeType.class, new TypeReference<Set<ZonedDateTime>>() {
        });
    }

    static AttributeAccess<ZonedDateTime> ofZonedDateTime() {
        return ofDateTime();
    }

    static AttributeAccess<Set<ZonedDateTime>> ofZonedDateTimeSet() {
        return ofDateTimeSet();
    }

    static AttributeAccess<Reference<Product>> ofProductReference() {
        return AttributeAccessImpl.ofReferenceType(ReferenceAttributeType.ofProduct());
    }

    static AttributeAccess<Set<Reference<Product>>> ofProductReferenceSet() {
        return AttributeAccessImpl.ofSet(ReferenceAttributeType.ofProduct(), new TypeReference<Set<Reference<Product>>>() {
        });
    }

    static AttributeAccess<Reference<ProductType>> ofProductTypeReference() {
        return AttributeAccessImpl.ofReferenceType(ReferenceAttributeType.ofProductType());
    }

    static AttributeAccess<Set<Reference<ProductType>>> ofProductTypeReferenceSet() {
        return AttributeAccessImpl.ofSet(ReferenceAttributeType.ofProductType(), new TypeReference<Set<Reference<ProductType>>>() {
        });
    }

    static AttributeAccess<Reference<Category>> ofCategoryReference() {
        return AttributeAccessImpl.ofReferenceType(ReferenceAttributeType.ofCategory());
    }

    static AttributeAccess<Set<Reference<Category>>> ofCategoryReferenceSet() {
        return AttributeAccessImpl.ofSet(ReferenceAttributeType.ofCategory(), new TypeReference<Set<Reference<Category>>>() {
        });
    }

    static AttributeAccess<Reference<Channel>> ofChannelReference() {
        return AttributeAccessImpl.ofReferenceType(ReferenceAttributeType.ofChannel());
    }

    static AttributeAccess<Set<Reference<Channel>>> ofChannelReferenceSet() {
        return AttributeAccessImpl.ofSet(ReferenceAttributeType.ofChannel(), new TypeReference<Set<Reference<Channel>>>() {
        });
    }

    static AttributeAccess<JsonNode> ofJsonNode() {
        final AttributeMapper<JsonNode> attributeMapper = new AttributeMapper<JsonNode>() {
            @Override
            public JsonNode deserialize(final JsonNode value) {
                return value;
            }

            @Override
            public JsonNode serialize(final JsonNode value) {
                return value;
            }
        };
        return new AttributeAccessImpl<>(attributeMapper, TypeReferences.jsonNodeTypeReference(), ad -> true);
    }

    static AttributeAccess<AttributeContainer> ofNested() {
        return new AttributeAccessImpl<>(new NestedAttributeMapperImpl(), new TypeReference<AttributeContainer>() {
            @Override
            public String toString() {
                return "TypeReference<AttributeContainer>";
            }
        }, attributeDefinition -> attributeDefinition.getAttributeType() instanceof NestedAttributeType);
    }

    static AttributeAccess<Set<AttributeContainer>> ofNestedSet() {
        return AttributeAccessImpl.ofSet(NestedAttributeType.class, new TypeReference<Set<AttributeContainer>>() {
            @Override
            public String toString() {
                return "TypeReference<Set<AttributeContainer>>";
            }
        }, new NestedSetAttributeMapperImpl());
    }
}
