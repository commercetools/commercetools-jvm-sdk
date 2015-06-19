package io.sphere.sdk.attributestutorial;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.attributes.*;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.json.JsonUtils;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.PlainEnumValue;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.errors.InvalidField;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.OrderImportCommand;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.commands.ProductCreateCommand;
import io.sphere.sdk.products.commands.ProductDeleteCommand;
import io.sphere.sdk.products.commands.ProductUpdateCommand;
import io.sphere.sdk.products.commands.updateactions.SetAttribute;
import io.sphere.sdk.products.queries.ProductProjectionQuery;
import io.sphere.sdk.products.queries.ProductQuery;
import io.sphere.sdk.producttypes.ProductType;
import io.sphere.sdk.producttypes.ProductTypeDraft;
import io.sphere.sdk.producttypes.commands.ProductTypeCreateCommand;
import io.sphere.sdk.producttypes.commands.ProductTypeDeleteCommand;
import io.sphere.sdk.producttypes.queries.ProductTypeQuery;
import io.sphere.sdk.queries.ExpansionPath;
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static io.sphere.sdk.products.ProductUpdateScope.STAGED_AND_CURRENT;
import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SetUtils.asSet;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static java.util.Locale.US;
import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTypeCreationDemoTest extends IntegrationTest {

    private static final String PRODUCT_TYPE_NAME = "tshirt-product-attribute-tutorial";
    private static final String COLOR_ATTR_NAME = "AttributeTutorialColor";
    private static final String SIZE_ATTR_NAME = "AttributeTutorialSize";
    private static final String MATCHING_PRODUCTS_ATTR_NAME = "AttributeTutorialMatchingProducts";
    private static final String LAUNDRY_SYMBOLS_ATTR_NAME = "AttributeTutorialLaundrySymbols";
    private static final String RRP_ATTR_NAME = "AttributeTutorialRrp";
    private static final String AVAILABLE_SINCE_ATTR_NAME = "AttributeTutorialAvailableSince";

    public void demoCheckingIfProductTypeExist() {
        final ProductTypeQuery query = ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME);
        final boolean productTypeAlreadyExists = client().execute(query).head().isPresent();
    }

    public ProductType fetchProductTypeByName() {
        final Optional<ProductType> productTypeOptional =
                client().execute(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).head();
        final ProductType productType = productTypeOptional
                .orElseThrow(() -> new RuntimeException("product type " + PRODUCT_TYPE_NAME + " is not present."));
        //end example parsing here
        return productType;
    }

    @AfterClass
    @BeforeClass
    public static void deleteProductsAndProductType() {
        final List<ProductType> productTypes = execute(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).getResults();
        if (!productTypes.isEmpty()) {
            final ProductQuery productQuery = ProductQuery.of()
                    .withPredicate(m -> m.productType().isIn(productTypes))
                    .withLimit(500);
            execute(productQuery).getResults().forEach(p -> execute(ProductDeleteCommand.of(p)));
            productTypes.forEach(p -> execute(ProductTypeDeleteCommand.of(p)));
        }
    }

    @BeforeClass
    public static void createProductType() throws Exception {
        final LocalizedEnumValue green = LocalizedEnumValue.of("green",
                LocalizedStrings.of(ENGLISH, "green").plus(GERMAN, "grün"));
        final LocalizedEnumValue red = LocalizedEnumValue.of("red",
                LocalizedStrings.of(ENGLISH, "red").plus(GERMAN, "rot"));
        final AttributeDefinition color = AttributeDefinitionBuilder
                .of(COLOR_ATTR_NAME, en("color"), LocalizedEnumType.of(red, green))
                .required(true)
                .build();

        final PlainEnumValue s = PlainEnumValue.of("S", "S");
        final PlainEnumValue m = PlainEnumValue.of("M", "M");
        final PlainEnumValue x = PlainEnumValue.of("X", "X");
        final AttributeDefinition size = AttributeDefinitionBuilder
                .of(SIZE_ATTR_NAME, en("size"), EnumType.of(s, m, x))
                .required(true)
                .build();

        final LocalizedEnumValue cold = LocalizedEnumValue.of("cold",
                LocalizedStrings.of(ENGLISH, "Wash at or below 30°C ").plus(GERMAN, "30°C"));
        final LocalizedEnumValue hot = LocalizedEnumValue.of("hot",
                LocalizedStrings.of(ENGLISH, "Wash at or below 60°C ").plus(GERMAN, "60°C"));
        final LocalizedEnumValue tumbleDrying = LocalizedEnumValue.of("tumbleDrying",
                LocalizedStrings.of(ENGLISH, "tumble drying").plus(GERMAN, "Trommeltrocknen"));
        final LocalizedEnumValue noTumbleDrying = LocalizedEnumValue.of("noTumbleDrying",
                LocalizedStrings.of(ENGLISH, "no tumble drying").plus(GERMAN, "Nicht im Trommeltrockner trocknen"));
        final SetType laundryLabelType = SetType.of(LocalizedEnumType.of(cold, hot, tumbleDrying, noTumbleDrying));
        final AttributeDefinition laundrySymbols = AttributeDefinitionBuilder
                .of(LAUNDRY_SYMBOLS_ATTR_NAME, en("washing labels"), laundryLabelType)
                .build();

        final AttributeDefinition matchingProducts = AttributeDefinitionBuilder
                .of(MATCHING_PRODUCTS_ATTR_NAME, en("matching products"), SetType.of(ReferenceType.ofProduct()))
                .build();

        final AttributeDefinition rrp = AttributeDefinitionBuilder
                .of(RRP_ATTR_NAME, en("recommended retail price"), MoneyType.of())
                .build();

        final AttributeDefinition availableSince = AttributeDefinitionBuilder
                .of(AVAILABLE_SINCE_ATTR_NAME, en("available since"), DateType.of())
                .build();


        final List<AttributeDefinition> attributes = asList(color, size, laundrySymbols,
                matchingProducts, rrp, availableSince);
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(PRODUCT_TYPE_NAME, "a 'T' shaped cloth", attributes);
        final ProductType productType = client().execute(ProductTypeCreateCommand.of(productTypeDraft));
    }

    @Test
    public void productCreation() throws Exception {
        createProduct();
    }

    public Product createProduct() throws Exception {
        final ProductType productType = fetchProductTypeByName();
        final Reference<Product> similarProductReference = ProductFixtures.referenceableProduct(client()).toReference();
        final ProductVariantDraft masterVariantDraft = ProductVariantDraftBuilder.of()
                .plusAttribute(COLOR_ATTR_NAME, "green")//special case: any enums are set with key (String)
                .plusAttribute(SIZE_ATTR_NAME, "S")//special case: any enums are set with key (String)
                .plusAttribute(LAUNDRY_SYMBOLS_ATTR_NAME,
                        asSet("cold", "tumbleDrying"))//special case: java.util.Set of any enums is set with java.util.Set of keys (String)
                .plusAttribute(MATCHING_PRODUCTS_ATTR_NAME, asSet(similarProductReference))
                .plusAttribute(RRP_ATTR_NAME, MoneyImpl.of(300, EUR))
                .plusAttribute(AVAILABLE_SINCE_ATTR_NAME, LocalDate.of(2015, 2, 2))
                .build();
        final ProductDraft draft = ProductDraftBuilder
                .of(productType, en("basic shirt"), randomSlug(), masterVariantDraft)
                .build();

        final Product product = client().execute(ProductCreateCommand.of(draft));

        final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
        assertThat(masterVariant.getAttribute(COLOR_ATTR_NAME, AttributeAccess.ofLocalizedEnumValue()))
                .overridingErrorMessage("on the get side, the while enum is delivered")
                .contains(LocalizedEnumValue.of("green", LocalizedStrings.of(ENGLISH, "green").plus(GERMAN, "grün")));
        assertThat(masterVariant.getAttribute(SIZE_ATTR_NAME, AttributeAccess.ofPlainEnumValue()))
                .contains(PlainEnumValue.of("S", "S"));
        final LocalizedEnumValue cold = LocalizedEnumValue.of("cold",
                LocalizedStrings.of(ENGLISH, "Wash at or below 30°C ").plus(GERMAN, "30°C"));
        final LocalizedEnumValue tumbleDrying = LocalizedEnumValue.of("tumbleDrying",
                LocalizedStrings.of(ENGLISH, "tumble drying").plus(GERMAN, "Trommeltrocknen"));
        assertThat(masterVariant.getAttribute(LAUNDRY_SYMBOLS_ATTR_NAME, AttributeAccess.ofLocalizedEnumValueSet()))
                .contains(asSet(cold, tumbleDrying));
        assertThat(masterVariant.getAttribute(MATCHING_PRODUCTS_ATTR_NAME, AttributeAccess.ofProductReferenceSet()))
                .contains(asSet(similarProductReference));
        assertThat(masterVariant.getAttribute(RRP_ATTR_NAME, AttributeAccess.ofMoney()))
                .contains(MoneyImpl.of(300, EUR));
        assertThat(masterVariant.getAttribute(AVAILABLE_SINCE_ATTR_NAME, AttributeAccess.ofDate()))
                .contains(LocalDate.of(2015, 2, 2));

        return product;
    }

    @Test
    public void productCreationWithGetterSetter() throws Exception {
        /* the declarations you could put into a separate class */
        final NamedAttributeAccess<LocalizedEnumValue> color = AttributeAccess.ofLocalizedEnumValue().ofName(COLOR_ATTR_NAME);
        final NamedAttributeAccess<PlainEnumValue> size = AttributeAccess.ofPlainEnumValue().ofName(SIZE_ATTR_NAME);
        final NamedAttributeAccess<Set<LocalizedEnumValue>> laundrySymbols =
                AttributeAccess.ofLocalizedEnumValueSet().ofName(LAUNDRY_SYMBOLS_ATTR_NAME);
        final NamedAttributeAccess<Set<Reference<Product>>> matchingProducts =
                AttributeAccess.ofProductReferenceSet().ofName(MATCHING_PRODUCTS_ATTR_NAME);
        final NamedAttributeAccess<MonetaryAmount> rrp = AttributeAccess.ofMoney().ofName(RRP_ATTR_NAME);
        final NamedAttributeAccess<LocalDate> availableSince = AttributeAccess.ofDate().ofName(AVAILABLE_SINCE_ATTR_NAME);

        final LocalizedEnumValue cold = LocalizedEnumValue.of("cold",
                LocalizedStrings.of(ENGLISH, "Wash at or below 30°C ").plus(GERMAN, "30°C"));
        final LocalizedEnumValue tumbleDrying = LocalizedEnumValue.of("tumbleDrying",
                LocalizedStrings.of(ENGLISH, "tumble drying").plus(GERMAN, "Trommeltrocknen"));
        final Reference<Product> productReference = ProductFixtures.referenceableProduct(client()).toReference();
        final ProductType productType = fetchProductTypeByName();
        final ProductVariantDraft masterVariantDraft = ProductVariantDraftBuilder.of()
               /*
                   other stuff using the NamedAttributeAccess
                   type-safe
                */
                .plusAttribute(color, LocalizedEnumValue.of("green",
                        LocalizedStrings.of(ENGLISH, "green").plus(GERMAN, "grün")))//will extract the key
                .plusAttribute(size, PlainEnumValue.of("S", "S"))
                .plusAttribute(laundrySymbols, asSet(cold, tumbleDrying))//will extract the keys, so you do not need to
                        //remember the special cases, there is also no problem mixing the styles
                .plusAttribute(matchingProducts, asSet(productReference))
//                .plusAttribute(matchingProducts, "foobar") won't compile!
                .plusAttribute(rrp, MoneyImpl.of(300, EUR))
                .plusAttribute(availableSince, LocalDate.of(2015, 2, 2))
                .build();
        final ProductDraft draft = ProductDraftBuilder
                .of(productType, en("basic shirt"), randomSlug(), masterVariantDraft)
                .build();

        final Product product = client().execute(ProductCreateCommand.of(draft));

        final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
        assertThat(masterVariant.getAttribute(color))
                .contains(LocalizedEnumValue.of("green", LocalizedStrings.of(ENGLISH, "green").plus(GERMAN, "grün")));
        assertThat(masterVariant.getAttribute(size)).contains(PlainEnumValue.of("S", "S"));
        assertThat(masterVariant.getAttribute(laundrySymbols)).contains(asSet(cold, tumbleDrying));
        assertThat(masterVariant.getAttribute(matchingProducts)).contains(asSet(productReference));
        assertThat(masterVariant.getAttribute(rrp)).contains(MoneyImpl.of(300, EUR));
        assertThat(masterVariant.getAttribute(availableSince)).contains(LocalDate.of(2015, 2, 2));
    }

    @Test
    public void invalidTypeCausesException() throws Exception {
        final ProductType productType = fetchProductTypeByName();
        final ProductVariantDraft masterVariantDraft = ProductVariantDraftBuilder.of()
                .plusAttribute(COLOR_ATTR_NAME, 1)//1 is of illegal type and of illegal key
                .build();
        final ProductDraft draft = ProductDraftBuilder
                .of(productType, en("basic shirt"), randomSlug(), masterVariantDraft)
                .build();
        assertThatThrownBy(() -> execute(ProductCreateCommand.of(draft)))
                .isInstanceOf(ErrorResponseException.class)
                .matches(e -> ((ErrorResponseException)e).hasErrorCode(InvalidField.CODE));
    }

    @Test
    public void readAttributeWithoutProductTypeWithName() throws Exception {
        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        final Optional<PlainEnumValue> attributeOption =
                masterVariant.getAttribute(SIZE_ATTR_NAME, AttributeAccess.ofPlainEnumValue());
        assertThat(attributeOption).contains(PlainEnumValue.of("S", "S"));
    }

    @Test
    public void readAttributeWithoutProductTypeWithNamedAccess() throws Exception {
        final NamedAttributeAccess<PlainEnumValue> size = AttributeAccess.ofPlainEnumValue().ofName(SIZE_ATTR_NAME);

        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        final Optional<PlainEnumValue> attributeOption = masterVariant.getAttribute(size);
        assertThat(attributeOption).contains(PlainEnumValue.of("S", "S"));
    }

    @Test
    public void readAttributeWithoutProductTypeWithNamedAccessWithWrongType() throws Exception {
        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        assertThatThrownBy(() -> masterVariant.getAttribute(SIZE_ATTR_NAME, AttributeAccess.ofBoolean()))
                .isInstanceOf(JsonException.class);
    }

    @Test
    public void notPresentAttributeRead() throws Exception {
        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        final Optional<Boolean> attributeOption = masterVariant.getAttribute("notpresent", AttributeAccess.ofBoolean());
        assertThat(attributeOption).isEmpty();
    }

    @Test
    public void readAttributeWithoutProductTypeWithJson() throws Exception {
        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        final Optional<Attribute> attributeOption = masterVariant.getAttribute(SIZE_ATTR_NAME);
        final JsonNode expectedJsonNode = JsonUtils.toJsonNode(PlainEnumValue.of("S", "S"));
        assertThat(attributeOption.get().getValue(AttributeAccess.ofJsonNode())).isEqualTo(expectedJsonNode);
    }

    @Test
    public void showProductAttributeTable() throws Exception {
        //this serves as whitelist which product attributes should be displayed
        //and also in this example it provides a constant order for the attributes to be displayed
        final List<String> attrNamesToShow = asList(COLOR_ATTR_NAME, SIZE_ATTR_NAME,
                MATCHING_PRODUCTS_ATTR_NAME, LAUNDRY_SYMBOLS_ATTR_NAME, RRP_ATTR_NAME, AVAILABLE_SINCE_ATTR_NAME);

        final Product product = createProduct();
        final ProductProjectionQuery query = ProductProjectionQuery.ofStaged()
                .withPredicate(m -> m.id().is(product.getId()))
                .plusExpansionPaths(m -> m.masterVariant().attributes().valueSet())
                .plusExpansionPaths(m -> m.productType());

        final ProductProjection productProjection = execute(query).head().get();

        final ProductType productType = productProjection.getProductType().getObj().get();
        final ProductVariant masterVariant = productProjection.getMasterVariant();
        final List<Attribute> attributes = masterVariant.getAttributes();
        final MonetaryAmountFormat moneyFormat = MonetaryFormats.getAmountFormat(US);

        final Function<Attribute, Optional<Pair<String, String>>> attributeValueExtractor = attribute -> {
            final Optional<String> extractedResult = AttributeExtraction.<String>of(productType, attribute)
                    .ifIs(AttributeAccess.ofLocalizedEnumValue(), v -> v.getLabel().get(ENGLISH).orElse(""))
                    .ifIs(AttributeAccess.ofPlainEnumValue(), v -> v.getLabel())
                    .ifIs(AttributeAccess.ofLocalizedEnumValueSet(), v ->
                            v.stream()
                                    .map(x -> x.getLabel().get(ENGLISH))
                                    .filter(x -> x.isPresent())
                                    .map(x -> x.get())
                                    .collect(joining(", ")))
                    .ifIs(AttributeAccess.ofProductReferenceSet(), set -> set.stream()
                            .map(productReference -> productReference.getObj()
                                    .map(prod -> prod.getMasterData().getStaged().getName().get(ENGLISH).orElse(""))
                                    .orElse(productReference.getId()))
                            .collect(joining(", ")))
                    .ifIs(AttributeAccess.ofMoney(), money -> moneyFormat.format(money))
                    .ifIs(AttributeAccess.ofDate(), date -> date.toString())
                    .getValue();
            final Optional<Pair<String, String>> tableRowData = extractedResult.map(value -> {
                final String label = productType.getAttribute(attribute.getName()).get().getLabel().get(ENGLISH).get();
                return ImmutablePair.of(label, value);
            });
            return tableRowData;
        };

        final List<Pair<String, String>> tableData = attributes.stream()

                .filter(a -> attrNamesToShow.contains(a.getName()))//remove attributes not in whitelist
                    //sort so that the order is like in attrNamesToShow
                .sorted(Comparator.comparingInt(a -> attrNamesToShow.indexOf(a.getName())))
                .map(attributeValueExtractor)
                .filter(x -> x.isPresent())
                .map(x -> x.get())
                .collect(toList());


        //table column length logic
        final List<ImmutablePair<Integer, Integer>> entryLengths = tableData.stream()
                .map(entry -> ImmutablePair.of(entry.getLeft().length(), entry.getRight().length())).collect(toList());
        final StringBuilder stringBuilder = new StringBuilder("\n");
        final int keyColumnWidth = entryLengths.stream().mapToInt(entry -> entry.getLeft()).max().orElse(1);
        final int valueColumnWidth = entryLengths.stream().mapToInt(entry -> entry.getRight()).max().orElse(1);

        for (final Pair<String, String> entry : tableData) {
            stringBuilder.append(String.format("%-" + keyColumnWidth + "s", entry.getLeft()))
                    .append(" | ")
                    .append(String.format("%-" + valueColumnWidth + "s", entry.getRight()))
                    .append("\n")
                    .append(org.apache.commons.lang3.StringUtils.repeat('-', keyColumnWidth + valueColumnWidth + 3))
                    .append("\n");
        }
        final String table = stringBuilder.toString();
        final String expected = "\n" +
                "color                    | green                                \n" +
                "----------------------------------------------------------------\n" +
                "size                     | S                                    \n" +
                "----------------------------------------------------------------\n" +
                "matching products        | referenceable product                \n" +
                "----------------------------------------------------------------\n" +
                "washing labels           | tumble drying, Wash at or below 30°C \n" +
                "----------------------------------------------------------------\n" +
                "recommended retail price | EUR300.00                            \n" +
                "----------------------------------------------------------------\n" +
                "available since          | 2015-02-02                           \n" +
                "----------------------------------------------------------------\n";
        assertThat(table).isEqualTo(expected);
    }

    @Test
    public void attributesForUnitTests() throws Exception {
        final Product referencedProduct = JsonUtils.readObjectFromResource("product1.json", Product.typeReference());
        final Reference<Product> productReference = referencedProduct.toReference();
        assertThat(productReference.getObj())
                .overridingErrorMessage("product reference is expanded")
                .isPresent();
        final AttributeAccess<Reference<Product>> access = AttributeAccess.ofProductReference();
        final Attribute attribute = Attribute.of("attrname", access, productReference);
        assertThat(attribute.getValue(access)).isEqualTo(productReference);
        assertThat(attribute.getValue(access).getObj()).isPresent();
    }

    @Test
    public void updateAttributes() throws Exception {
        final Product product = createProduct();
        final int masterVariantId = 1;
        final Function<AttributeDraft, SetAttribute> draft = attrDraft ->
                SetAttribute.of(masterVariantId, attrDraft, STAGED_AND_CURRENT);
        final List<SetAttribute> updateActions = asList(
                draft.apply(AttributeDraft.of(COLOR_ATTR_NAME, "red")),//don't forget: enum like => use only keys
                draft.apply(AttributeDraft.of(SIZE_ATTR_NAME, "M")),
                draft.apply(AttributeDraft.of(LAUNDRY_SYMBOLS_ATTR_NAME, asSet("cold"))),
                draft.apply(AttributeDraft.of(RRP_ATTR_NAME, MoneyImpl.of(20, EUR)))
        );

        final Product updatedProduct = execute(ProductUpdateCommand.of(product, updateActions));

        final ProductVariant masterVariant = updatedProduct.getMasterData().getStaged().getMasterVariant();
        assertThat(masterVariant.getAttribute(COLOR_ATTR_NAME, AttributeAccess.ofLocalizedEnumValue()))
                .contains(LocalizedEnumValue.of("red", LocalizedStrings.of(ENGLISH, "red").plus(GERMAN, "rot")));
        assertThat(masterVariant.getAttribute(SIZE_ATTR_NAME, AttributeAccess.ofPlainEnumValue()))
                .contains(PlainEnumValue.of("M", "M"));
        final LocalizedEnumValue cold = LocalizedEnumValue.of("cold",
                LocalizedStrings.of(ENGLISH, "Wash at or below 30°C ").plus(GERMAN, "30°C"));
        assertThat(masterVariant.getAttribute(LAUNDRY_SYMBOLS_ATTR_NAME, AttributeAccess.ofLocalizedEnumValueSet()))
                .contains(asSet(cold));
        assertThat(masterVariant.getAttribute(RRP_ATTR_NAME, AttributeAccess.ofMoney()))
                .contains(MoneyImpl.of(20, EUR));
    }

    @Test
    public void updateWithWrongType() throws Exception {
        final Product product = createProduct();
        assertThatThrownBy(() -> execute(ProductUpdateCommand.of(product,
                SetAttribute.of(1, AttributeDraft.of(LAUNDRY_SYMBOLS_ATTR_NAME, "cold"), STAGED_AND_CURRENT))))
                .isInstanceOf(ErrorResponseException.class)
                .matches(e -> ((ErrorResponseException)e).hasErrorCode(InvalidField.CODE));
    }

    @Test
    public void orderImportExample() throws Exception {
        final Product product = createProduct();
        //yellow is not defined in the product type, but for order imports this works to add use it on the fly
        final LocalizedEnumValue yellow =
                LocalizedEnumValue.of("yellow", LocalizedStrings.of(ENGLISH, "yellow").plus(GERMAN, "gelb"));
        final ProductVariantImportDraft productVariantImportDraft = ProductVariantImportDraftBuilder.of(product.getId(), 1)
                .attributes(
                        AttributeImportDraft.of(COLOR_ATTR_NAME, yellow),
                        AttributeImportDraft.of(RRP_ATTR_NAME, EURO_30)
                ).build();
        final LineItemImportDraft lineItemImportDraft =
                LineItemImportDraftBuilder.of(productVariantImportDraft, 1, Price.of(EURO_30), en("product name"))
                .build();
        final OrderImportDraft orderImportDraft = OrderImportDraftBuilder
                .ofLineItems(EURO_20, OrderState.COMPLETE, asList(lineItemImportDraft))
                .build();

        final Order order = execute(OrderImportCommand.of(orderImportDraft));

        final ProductVariant productVariant = order.getLineItems().get(0).getVariant();
        final Optional<LocalizedEnumValue> colorAttribute =
                productVariant.getAttribute(COLOR_ATTR_NAME, AttributeAccess.ofLocalizedEnumValue());
        assertThat(colorAttribute).contains(yellow);
        final Optional<MonetaryAmount> rrpAttribute =
                productVariant.getAttribute(RRP_ATTR_NAME, AttributeAccess.ofMoney());
        assertThat(rrpAttribute).contains(EURO_30);
        final Set<String> presentAttributes = productVariant.getAttributes().stream()
                .map(attr -> attr.getName())
                .collect(toSet());
        assertThat(presentAttributes).containsOnly(COLOR_ATTR_NAME, RRP_ATTR_NAME);
    }
}
