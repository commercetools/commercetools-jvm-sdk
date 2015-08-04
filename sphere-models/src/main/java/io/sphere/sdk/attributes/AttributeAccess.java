package io.sphere.sdk.attributes;

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
 *
 * @param <T> the type of the attribute
 * @see NamedAttributeAccess
 */
public interface AttributeAccess<T> {
    NamedAttributeAccess<T> ofName(String name);

    AttributeMapper<T> attributeMapper();

    boolean canHandle(AttributeDefinition attributeDefinition);

    static AttributeAccess<Boolean> ofBoolean() {
        return AttributeAccessImpl.ofPrimitive(booleanTypeReference(), BooleanType.class);
    }

    static AttributeAccess<Set<Boolean>> ofBooleanSet() {
        return AttributeAccessImpl.ofSet(BooleanType.class, new TypeReference<Set<Boolean>>() {
        });
    }

    static AttributeAccess<String> ofString() {
        return AttributeAccessImpl.ofPrimitive(stringTypeReference(), TextType.class);
    }

    static AttributeAccess<Set<String>> ofStringSet() {
        return AttributeAccessImpl.ofSet(TextType.class, new TypeReference<Set<String>>() {
        });
    }

    static AttributeAccess<String> ofText() {
        return ofString();
    }

    static AttributeAccess<Set<String>> ofTextSet() {
        return ofStringSet();
    }

    static AttributeAccess<LocalizedString> ofLocalizedString() {
        return AttributeAccessImpl.ofPrimitive(LocalizedString.typeReference(), LocalizedStringType.class);
    }

    static AttributeAccess<Set<LocalizedString>> ofLocalizedStringSet() {
        return AttributeAccessImpl.ofSet(LocalizedStringType.class, new TypeReference<Set<LocalizedString>>() {
        });
    }

    static AttributeAccess<EnumValue> ofPlainEnumValue() {
        return AttributeAccessImpl.ofEnumLike(EnumValue.typeReference(), EnumType.class);
    }

    static AttributeAccess<Set<EnumValue>> ofPlainEnumValueSet() {
        return AttributeAccessImpl.ofEnumLikeSet(EnumType.class, new TypeReference<Set<EnumValue>>() {
        });
    }

    static AttributeAccess<LocalizedEnumValue> ofLocalizedEnumValue() {
        return AttributeAccessImpl.ofEnumLike(LocalizedEnumValue.typeReference(), LocalizedEnumType.class);
    }

    static AttributeAccess<Set<LocalizedEnumValue>> ofLocalizedEnumValueSet() {
        return AttributeAccessImpl.ofEnumLikeSet(LocalizedEnumType.class, new TypeReference<Set<LocalizedEnumValue>>() {
        });
    }

    static AttributeAccess<Double> ofDouble() {
        return AttributeAccessImpl.ofPrimitive(doubleTypeReference(), NumberType.class);
    }

    static AttributeAccess<Set<Double>> ofDoubleSet() {
        return AttributeAccessImpl.ofSet(NumberType.class, new TypeReference<Set<Double>>() {
        });
    }

    static AttributeAccess<MonetaryAmount> ofMoney() {
        return AttributeAccessImpl.ofPrimitive(monetaryAmountTypeReference(), MoneyType.class);
    }

    static AttributeAccess<Set<MonetaryAmount>> ofMoneySet() {
        return AttributeAccessImpl.ofSet(MoneyType.class, new TypeReference<Set<MonetaryAmount>>() {
        });
    }

    static AttributeAccess<LocalDate> ofLocalDate() {
        return ofDate();
    }

    static AttributeAccess<Set<LocalDate>> ofLocalDateSet() {
        return ofDateSet();
    }

    static AttributeAccess<LocalDate> ofDate() {
        return AttributeAccessImpl.ofPrimitive(localDateTypeReference(), DateType.class);
    }

    static AttributeAccess<Set<LocalDate>> ofDateSet() {
        return AttributeAccessImpl.ofSet(DateType.class, new TypeReference<Set<LocalDate>>() {
        });
    }

    static AttributeAccess<LocalTime> ofLocalTime() {
        return ofTime();
    }

    static AttributeAccess<Set<LocalTime>> ofLocalTimeSet() {
        return ofTimeSet();
    }

    static AttributeAccess<LocalTime> ofTime() {
        return AttributeAccessImpl.ofPrimitive(localTimeTypeReference(), TimeType.class);
    }

    static AttributeAccess<Set<LocalTime>> ofTimeSet() {
        return AttributeAccessImpl.ofSet(TimeType.class, new TypeReference<Set<LocalTime>>() {
        });
    }

    static AttributeAccess<ZonedDateTime> ofDateTime() {
        return AttributeAccessImpl.ofPrimitive(ZonedDateTimeTypeReference(), DateTimeType.class);
    }

    static AttributeAccess<Set<ZonedDateTime>> ofDateTimeSet() {
        return AttributeAccessImpl.ofSet(DateTimeType.class, new TypeReference<Set<ZonedDateTime>>() {
        });
    }

    static AttributeAccess<ZonedDateTime> ofZonedDateTime() {
        return ofDateTime();
    }

    static AttributeAccess<Set<ZonedDateTime>> ofZonedDateTimeSet() {
        return ofDateTimeSet();
    }

    static AttributeAccess<Reference<Product>> ofProductReference() {
        return AttributeAccessImpl.ofReferenceType(ReferenceType.ofProduct());
    }

    static AttributeAccess<Set<Reference<Product>>> ofProductReferenceSet() {
        return AttributeAccessImpl.ofSet(ReferenceType.ofProduct(), new TypeReference<Set<Reference<Product>>>() {
        });
    }

    static AttributeAccess<Reference<ProductType>> ofProductTypeReference() {
        return AttributeAccessImpl.ofReferenceType(ReferenceType.ofProductType());
    }

    static AttributeAccess<Set<Reference<ProductType>>> ofProductTypeReferenceSet() {
        return AttributeAccessImpl.ofSet(ReferenceType.ofProductType(), new TypeReference<Set<Reference<ProductType>>>() {
        });
    }

    static AttributeAccess<Reference<Category>> ofCategoryReference() {
        return AttributeAccessImpl.ofReferenceType(ReferenceType.ofCategory());
    }

    static AttributeAccess<Set<Reference<Category>>> ofCategoryReferenceSet() {
        return AttributeAccessImpl.ofSet(ReferenceType.ofCategory(), new TypeReference<Set<Reference<Category>>>() {
        });
    }

    static AttributeAccess<Reference<Channel>> ofChannelReference() {
        return AttributeAccessImpl.ofReferenceType(ReferenceType.ofChannel());
    }

    static AttributeAccess<Set<Reference<Channel>>> ofChannelReferenceSet() {
        return AttributeAccessImpl.ofSet(ReferenceType.ofChannel(), new TypeReference<Set<Reference<Channel>>>() {
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
        }, attributeDefinition -> attributeDefinition.getAttributeType() instanceof NestedType);
    }

    static AttributeAccess<Set<AttributeContainer>> ofNestedSet() {
        return AttributeAccessImpl.ofSet(NestedType.class, new TypeReference<Set<AttributeContainer>>() {
            @Override
            public String toString() {
                return "TypeReference<Set<AttributeContainer>>";
            }
        }, new NestedSetAttributeMapperImpl());
    }
}
