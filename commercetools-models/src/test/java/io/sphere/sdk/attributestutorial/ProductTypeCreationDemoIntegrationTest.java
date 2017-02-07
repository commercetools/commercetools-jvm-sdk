package io.sphere.sdk.attributestutorial;

import com.fasterxml.jackson.databind.JsonNode;
import io.sphere.sdk.client.ErrorResponseException;
import io.sphere.sdk.json.JsonException;
import io.sphere.sdk.json.SphereJsonUtils;
import io.sphere.sdk.models.EnumValue;
import io.sphere.sdk.models.LocalizedEnumValue;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.errors.InvalidField;
import io.sphere.sdk.orders.*;
import io.sphere.sdk.orders.commands.OrderImportCommand;
import io.sphere.sdk.products.*;
import io.sphere.sdk.products.attributes.*;
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
import io.sphere.sdk.test.IntegrationTest;
import io.sphere.sdk.utils.MoneyImpl;
import net.jcip.annotations.NotThreadSafe;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.money.MonetaryAmount;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

import static io.sphere.sdk.test.SphereTestUtils.*;
import static io.sphere.sdk.utils.SphereInternalUtils.asSet;
import static java.util.Arrays.asList;
import static java.util.Locale.ENGLISH;
import static java.util.Locale.GERMAN;
import static java.util.Locale.US;
import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.*;

@NotThreadSafe
public class ProductTypeCreationDemoIntegrationTest extends IntegrationTest {

    private static final String PRODUCT_TYPE_NAME = "tshirt-product-attribute-tutorial";
    private static final String BOOK_PRODUCT_TYPE_NAME = "book-product-attribute-tutorial";
    private static final String COLOR_ATTR_NAME = "AttributeTutorialColor";
    private static final String SIZE_ATTR_NAME = "AttributeTutorialSize";
    private static final String MATCHING_PRODUCTS_ATTR_NAME = "AttributeTutorialMatchingProducts";
    private static final String LAUNDRY_SYMBOLS_ATTR_NAME = "AttributeTutorialLaundrySymbols";
    private static final String RRP_ATTR_NAME = "AttributeTutorialRrp";
    private static final String AVAILABLE_SINCE_ATTR_NAME = "AttributeTutorialAvailableSince";
    private static final String ISBN_ATTR_NAME = "AttributeTutorialIsbn";

    public ProductType fetchProductTypeByName() {
        final Optional<ProductType> productTypeOptional =
                client().executeBlocking(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).head();
        final ProductType productType = productTypeOptional
                .orElseThrow(() -> new RuntimeException("product type " + PRODUCT_TYPE_NAME + " is not present."));
        //end example parsing here
        return productType;
    }

    @AfterClass
    @BeforeClass
    public static void deleteProductsAndProductType() {
        final List<ProductType> productTypes = client().executeBlocking(ProductTypeQuery.of().byName(PRODUCT_TYPE_NAME)).getResults();
        if (!productTypes.isEmpty()) {
            final ProductQuery productQuery = ProductQuery.of()
                    .withPredicates(m -> m.productType().isIn(productTypes))
                    .withLimit(500L);
            client().executeBlocking(productQuery).getResults().forEach(p -> client().executeBlocking(ProductDeleteCommand.of(p)));
            productTypes.forEach(p -> client().executeBlocking(ProductTypeDeleteCommand.of(p)));
        }
    }

    @BeforeClass
    public static void createBookProductType() throws Exception {
        final AttributeDefinition isbn = AttributeDefinitionBuilder
                .of(ISBN_ATTR_NAME, en("ISBN"), StringAttributeType.of())
                .build();

        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(randomKey(), BOOK_PRODUCT_TYPE_NAME, "books", asList(isbn));
        final ProductType productType = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));
    }

    @BeforeClass
    public static void createProductType() throws Exception {
        final LocalizedEnumValue green = LocalizedEnumValue.of("green",
                LocalizedString.of(ENGLISH, "green").plus(GERMAN, "grün"));
        final LocalizedEnumValue red = LocalizedEnumValue.of("red",
                LocalizedString.of(ENGLISH, "red").plus(GERMAN, "rot"));
        final AttributeDefinition color = AttributeDefinitionBuilder
                .of(COLOR_ATTR_NAME, en("color"), LocalizedEnumAttributeType.of(red, green))
                .required(true)
                .build();

        final EnumValue s = EnumValue.of("S", "S");
        final EnumValue m = EnumValue.of("M", "M");
        final EnumValue x = EnumValue.of("X", "X");
        final AttributeDefinition size = AttributeDefinitionBuilder
                .of(SIZE_ATTR_NAME, en("size"), EnumAttributeType.of(s, m, x))
                .required(true)
                .build();

        final LocalizedEnumValue cold = LocalizedEnumValue.of("cold",
                LocalizedString.of(ENGLISH, "Wash at or below 30°C ").plus(GERMAN, "30°C"));
        final LocalizedEnumValue hot = LocalizedEnumValue.of("hot",
                LocalizedString.of(ENGLISH, "Wash at or below 60°C ").plus(GERMAN, "60°C"));
        final LocalizedEnumValue tumbleDrying = LocalizedEnumValue.of("tumbleDrying",
                LocalizedString.of(ENGLISH, "tumble drying").plus(GERMAN, "Trommeltrocknen"));
        final LocalizedEnumValue noTumbleDrying = LocalizedEnumValue.of("noTumbleDrying",
                LocalizedString.of(ENGLISH, "no tumble drying").plus(GERMAN, "Nicht im Trommeltrockner trocknen"));
        final SetAttributeType laundryLabelType = SetAttributeType.of(LocalizedEnumAttributeType.of(cold, hot, tumbleDrying, noTumbleDrying));
        final AttributeDefinition laundrySymbols = AttributeDefinitionBuilder
                .of(LAUNDRY_SYMBOLS_ATTR_NAME, en("washing labels"), laundryLabelType)
                .build();

        final AttributeDefinition matchingProducts = AttributeDefinitionBuilder
                .of(MATCHING_PRODUCTS_ATTR_NAME, en("matching products"), SetAttributeType.of(ReferenceAttributeType.ofProduct()))
                .build();

        final AttributeDefinition rrp = AttributeDefinitionBuilder
                .of(RRP_ATTR_NAME, en("recommended retail price"), MoneyAttributeType.of())
                .build();

        final AttributeDefinition availableSince = AttributeDefinitionBuilder
                .of(AVAILABLE_SINCE_ATTR_NAME, en("available since"), DateAttributeType.of())
                .build();


        final List<AttributeDefinition> attributes = asList(color, size, laundrySymbols,
                matchingProducts, rrp, availableSince);
        final ProductTypeDraft productTypeDraft = ProductTypeDraft.of(randomKey(), PRODUCT_TYPE_NAME, "a 'T' shaped cloth", attributes);
        final ProductType productType = client().executeBlocking(ProductTypeCreateCommand.of(productTypeDraft));
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

        final Product product = client().executeBlocking(ProductCreateCommand.of(draft));

        final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
        assertThat(masterVariant.findAttribute(COLOR_ATTR_NAME, AttributeAccess.ofLocalizedEnumValue()))
                .overridingErrorMessage("on the get side, the while enum is delivered")
                .contains(LocalizedEnumValue.of("green", LocalizedString.of(ENGLISH, "green").plus(GERMAN, "grün")));
        assertThat(masterVariant.findAttribute(SIZE_ATTR_NAME, AttributeAccess.ofEnumValue()))
                .contains(EnumValue.of("S", "S"));
        final LocalizedEnumValue cold = LocalizedEnumValue.of("cold",
                LocalizedString.of(ENGLISH, "Wash at or below 30°C ").plus(GERMAN, "30°C"));
        final LocalizedEnumValue tumbleDrying = LocalizedEnumValue.of("tumbleDrying",
                LocalizedString.of(ENGLISH, "tumble drying").plus(GERMAN, "Trommeltrocknen"));
        assertThat(masterVariant.findAttribute(LAUNDRY_SYMBOLS_ATTR_NAME, AttributeAccess.ofLocalizedEnumValueSet()))
                .contains(asSet(cold, tumbleDrying));
        assertThat(masterVariant.findAttribute(MATCHING_PRODUCTS_ATTR_NAME, AttributeAccess.ofProductReferenceSet()))
                .contains(asSet(similarProductReference));
        assertThat(masterVariant.findAttribute(RRP_ATTR_NAME, AttributeAccess.ofMoney()))
                .contains(MoneyImpl.of(300, EUR));
        assertThat(masterVariant.findAttribute(AVAILABLE_SINCE_ATTR_NAME, AttributeAccess.ofDate()))
                .contains(LocalDate.of(2015, 2, 2));

        return product;
    }

    public Product createBookProduct() throws Exception {
        final ProductType productType = client().executeBlocking(ProductTypeQuery.of().byName(BOOK_PRODUCT_TYPE_NAME)).head().get();
        final ProductVariantDraft masterVariantDraft = ProductVariantDraftBuilder.of()
                .plusAttribute(ISBN_ATTR_NAME, "978-3-86680-192-9")
                .build();
        final ProductDraft draft = ProductDraftBuilder
                .of(productType, en("a book"), randomSlug(), masterVariantDraft)
                .build();

        final Product product = client().executeBlocking(ProductCreateCommand.of(draft));

        final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
        assertThat(masterVariant.findAttribute(ISBN_ATTR_NAME, AttributeAccess.ofText()))
                .contains("978-3-86680-192-9");
        return product;
    }

    @Test
    public void productCreationWithGetterSetter() throws Exception {
        /* the declarations you could put into a separate class */
        final NamedAttributeAccess<LocalizedEnumValue> color = AttributeAccess.ofLocalizedEnumValue().ofName(COLOR_ATTR_NAME);
        final NamedAttributeAccess<EnumValue> size = AttributeAccess.ofEnumValue().ofName(SIZE_ATTR_NAME);
        final NamedAttributeAccess<Set<LocalizedEnumValue>> laundrySymbols =
                AttributeAccess.ofLocalizedEnumValueSet().ofName(LAUNDRY_SYMBOLS_ATTR_NAME);
        final NamedAttributeAccess<Set<Reference<Product>>> matchingProducts =
                AttributeAccess.ofProductReferenceSet().ofName(MATCHING_PRODUCTS_ATTR_NAME);
        final NamedAttributeAccess<MonetaryAmount> rrp = AttributeAccess.ofMoney().ofName(RRP_ATTR_NAME);
        final NamedAttributeAccess<LocalDate> availableSince = AttributeAccess.ofDate().ofName(AVAILABLE_SINCE_ATTR_NAME);

        final LocalizedEnumValue cold = LocalizedEnumValue.of("cold",
                LocalizedString.of(ENGLISH, "Wash at or below 30°C ").plus(GERMAN, "30°C"));
        final LocalizedEnumValue tumbleDrying = LocalizedEnumValue.of("tumbleDrying",
                LocalizedString.of(ENGLISH, "tumble drying").plus(GERMAN, "Trommeltrocknen"));
        final Reference<Product> productReference = ProductFixtures.referenceableProduct(client()).toReference();
        final ProductType productType = fetchProductTypeByName();
        final ProductVariantDraft masterVariantDraft = ProductVariantDraftBuilder.of()
               /*
                   other stuff using the NamedAttributeAccess
                   type-safe
                */
                .plusAttribute(color, LocalizedEnumValue.of("green",
                        LocalizedString.of(ENGLISH, "green").plus(GERMAN, "grün")))//will extract the key
                .plusAttribute(size, EnumValue.of("S", "S"))
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

        final Product product = client().executeBlocking(ProductCreateCommand.of(draft));

        final ProductVariant masterVariant = product.getMasterData().getStaged().getMasterVariant();
        assertThat(masterVariant.findAttribute(color))
                .contains(LocalizedEnumValue.of("green", LocalizedString.of(ENGLISH, "green").plus(GERMAN, "grün")));
        assertThat(masterVariant.findAttribute(size)).contains(EnumValue.of("S", "S"));
        assertThat(masterVariant.findAttribute(laundrySymbols)).contains(asSet(cold, tumbleDrying));
        assertThat(masterVariant.findAttribute(matchingProducts)).contains(asSet(productReference));
        assertThat(masterVariant.findAttribute(rrp)).contains(MoneyImpl.of(300, EUR));
        assertThat(masterVariant.findAttribute(availableSince)).contains(LocalDate.of(2015, 2, 2));
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
        assertThatThrownBy(() -> client().executeBlocking(ProductCreateCommand.of(draft)))
                .isInstanceOf(ErrorResponseException.class)
                .matches(e -> ((ErrorResponseException)e).hasErrorCode(InvalidField.CODE));
    }

    @Test
    public void readAttributeWithoutProductTypeWithName() throws Exception {
        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        final Optional<EnumValue> attributeOption =
                masterVariant.findAttribute(SIZE_ATTR_NAME, AttributeAccess.ofEnumValue());
        assertThat(attributeOption).contains(EnumValue.of("S", "S"));
    }

    @Test
    public void readAttributeWithoutProductTypeWithNamedAccess() throws Exception {
        final NamedAttributeAccess<EnumValue> size = AttributeAccess.ofEnumValue().ofName(SIZE_ATTR_NAME);

        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        final Optional<EnumValue> attributeOption = masterVariant.findAttribute(size);
        assertThat(attributeOption).contains(EnumValue.of("S", "S"));
    }

    @Test
    public void readAttributeGetValueAs() throws Exception {
        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        final String attributeValue = masterVariant.findAttribute(SIZE_ATTR_NAME)
                .map((Attribute a) -> {
                    final EnumValue enumValue = a.getValueAsEnumValue();
                    return enumValue.getLabel();
                })
                .orElse("not found");
        assertThat(attributeValue).isEqualTo("S");
    }

    @Test
    public void readAttributeGetValueAsWithWrongType() throws Exception {
        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        final Throwable throwable = catchThrowable(
                () -> masterVariant.findAttribute(SIZE_ATTR_NAME)
                        .map((Attribute a) -> a.getValueAsBoolean())
                        .orElse(true)
        );
        assertThat(throwable).isInstanceOf(JsonException.class);
    }

    @Test
    public void readAttributeWithoutProductTypeWithNamedAccessWithWrongType() throws Exception {
        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        assertThatThrownBy(() -> masterVariant.findAttribute(SIZE_ATTR_NAME, AttributeAccess.ofBoolean()))
                .isInstanceOf(JsonException.class);
    }

    @Test
    public void notPresentAttributeRead() throws Exception {
        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        final Optional<Boolean> attributeOption = masterVariant.findAttribute("notpresent", AttributeAccess.ofBoolean());
        assertThat(attributeOption).isEmpty();
    }

    @Test
    public void readAttributeWithoutProductTypeWithJson() throws Exception {
        final ProductVariant masterVariant = createProduct().getMasterData().getStaged().getMasterVariant();

        final Attribute attr = masterVariant.getAttribute(SIZE_ATTR_NAME);
        final JsonNode expectedJsonNode = SphereJsonUtils.toJsonNode(EnumValue.of("S", "S"));
        assertThat(attr.getValue(AttributeAccess.ofJsonNode())).isEqualTo(expectedJsonNode);
    }

    @Test
    public void showProductAttributeTableWithDefaultFormatter() throws Exception {
        final List<String> attrNamesToShow = asList(COLOR_ATTR_NAME, SIZE_ATTR_NAME,
                MATCHING_PRODUCTS_ATTR_NAME, LAUNDRY_SYMBOLS_ATTR_NAME, RRP_ATTR_NAME, AVAILABLE_SINCE_ATTR_NAME);

        final Product product = createProduct();
        final ProductProjectionQuery query = ProductProjectionQuery.ofStaged()
                .withPredicates(m -> m.id().is(product.getId()))
                .plusExpansionPaths(m -> m.masterVariant().attributes().valueSet())
                .plusExpansionPaths(m -> m.productType());

        final ProductProjection productProjection = client().executeBlocking(query).head().get();

        final List<ProductType> productTypes = Collections.singletonList(productProjection.getProductType().getObj());
        final List<Locale> locales = Collections.singletonList(ENGLISH);
        final DefaultProductAttributeFormatter formatter = DefaultProductAttributeFormatterDemo.createFormatter(productTypes, locales);
        DefaultProductAttributeFormatterDemo.example(attrNamesToShow, productProjection, formatter);
    }

    @Test
    public void showProductAttributeTable() throws Exception {
        //this serves as whitelist which product attributes should be displayed
        //and also in this example it provides a constant order for the attributes to be displayed
        final List<String> attrNamesToShow = asList(COLOR_ATTR_NAME, SIZE_ATTR_NAME,
                MATCHING_PRODUCTS_ATTR_NAME, LAUNDRY_SYMBOLS_ATTR_NAME, RRP_ATTR_NAME, AVAILABLE_SINCE_ATTR_NAME);

        final Product product = createProduct();
        final ProductProjectionQuery query = ProductProjectionQuery.ofStaged()
                .withPredicates(m -> m.id().is(product.getId()))
                .plusExpansionPaths(m -> m.masterVariant().attributes().valueSet())
                .plusExpansionPaths(m -> m.productType());

        final ProductProjection productProjection = client().executeBlocking(query).head().get();

        final ProductType productType = productProjection.getProductType().getObj();
        final ProductVariant masterVariant = productProjection.getMasterVariant();
        final List<Attribute> attributes = masterVariant.getAttributes();
        final MonetaryAmountFormat moneyFormat = MonetaryFormats.getAmountFormat(US);

        final Function<Attribute, Optional<Pair<String, String>>> attributeValueExtractor = attribute -> {
            final Optional<String> extractedResult = AttributeExtraction.<String>of(productType, attribute)
                    .ifIs(AttributeAccess.ofLocalizedEnumValue(), v -> v.getLabel().find(ENGLISH).orElse(""))
                    .ifIs(AttributeAccess.ofEnumValue(), v -> v.getLabel())
                    .ifIs(AttributeAccess.ofLocalizedEnumValueSet(), v ->
                            v.stream()
                                    .map(x -> x.getLabel().get(ENGLISH))
                                    .filter(x -> x != null)
                                    .collect(joining(", ")))
                    .ifIs(AttributeAccess.ofProductReferenceSet(), set -> set.stream()
                            .map(productReference -> Optional.ofNullable(productReference.getObj())
                                    .map(prod -> prod.getMasterData().getStaged().getName().find(ENGLISH).orElse(""))
                                    .orElse(productReference.getId()))
                            .collect(joining(", ")))
                    .ifIs(AttributeAccess.ofMoney(), money -> moneyFormat.format(money))
                    .ifIs(AttributeAccess.ofDate(), date -> date.toString())
                    .findValue();
            final Optional<Pair<String, String>> tableRowData = extractedResult.map(value -> {
                final String label = productType.getAttribute(attribute.getName()).getLabel().get(ENGLISH);
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
                "washing labels           | Wash at or below 30°C , tumble drying\n" +
                "----------------------------------------------------------------\n" +
                "recommended retail price | EUR300.00                            \n" +
                "----------------------------------------------------------------\n" +
                "available since          | 2015-02-02                           \n" +
                "----------------------------------------------------------------\n";
        assertThat(table).isEqualTo(expected);
    }

    @Test
    public void attributesForUnitTests() throws Exception {
        final Product referencedProduct = SphereJsonUtils.readObjectFromResource("product1.json", Product.typeReference());
        final Reference<Product> productReference = referencedProduct.toReference();
        assertThat(productReference.getObj())
                .overridingErrorMessage("product reference is expanded")
                .isNotNull();
        final AttributeAccess<Reference<Product>> access = AttributeAccess.ofProductReference();
        final Attribute attribute = Attribute.of("attrname", access, productReference);
        assertThat(attribute.getValue(access)).isEqualTo(productReference);
        assertThat(attribute.getValue(access).getObj()).isNotNull();
    }

    @Test
    public void updateAttributesBooks() throws Exception {
        final Product product = createBookProduct();
        final int masterVariantId = 1;

        final AttributeDraft attributeDraft = AttributeDraft.of(ISBN_ATTR_NAME, "978-3-86680-192-8");
        final SetAttribute updateAction = SetAttribute.of(masterVariantId, attributeDraft);

        final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, updateAction));

        final ProductVariant masterVariant = updatedProduct.getMasterData().getStaged().getMasterVariant();
        assertThat(masterVariant.findAttribute(ISBN_ATTR_NAME, AttributeAccess.ofText()))
                .contains("978-3-86680-192-8");
    }

    @Test
    public void updateAttributes() throws Exception {
        final Product product = createProduct();
        final int masterVariantId = 1;
        final Function<AttributeDraft, SetAttribute> draft = attrDraft ->
                SetAttribute.of(masterVariantId, attrDraft);
        final List<SetAttribute> updateActions = asList(
                draft.apply(AttributeDraft.of(COLOR_ATTR_NAME, "red")),//don't forget: enum like => use only keys
                draft.apply(AttributeDraft.of(SIZE_ATTR_NAME, "M")),
                draft.apply(AttributeDraft.of(LAUNDRY_SYMBOLS_ATTR_NAME, asSet("cold"))),
                draft.apply(AttributeDraft.of(RRP_ATTR_NAME, MoneyImpl.of(20, EUR)))
        );

        final Product updatedProduct = client().executeBlocking(ProductUpdateCommand.of(product, updateActions));

        final ProductVariant masterVariant = updatedProduct.getMasterData().getStaged().getMasterVariant();
        assertThat(masterVariant.findAttribute(COLOR_ATTR_NAME, AttributeAccess.ofLocalizedEnumValue()))
                .contains(LocalizedEnumValue.of("red", LocalizedString.of(ENGLISH, "red").plus(GERMAN, "rot")));
        assertThat(masterVariant.findAttribute(SIZE_ATTR_NAME, AttributeAccess.ofEnumValue()))
                .contains(EnumValue.of("M", "M"));
        final LocalizedEnumValue cold = LocalizedEnumValue.of("cold",
                LocalizedString.of(ENGLISH, "Wash at or below 30°C ").plus(GERMAN, "30°C"));
        assertThat(masterVariant.findAttribute(LAUNDRY_SYMBOLS_ATTR_NAME, AttributeAccess.ofLocalizedEnumValueSet()))
                .contains(asSet(cold));
        assertThat(masterVariant.findAttribute(RRP_ATTR_NAME, AttributeAccess.ofMoney()))
                .contains(MoneyImpl.of(20, EUR));
    }

    @Test
    public void updateWithWrongType() throws Exception {
        final Product product = createProduct();
        assertThatThrownBy(() -> client().executeBlocking(ProductUpdateCommand.of(product,
                SetAttribute.of(1, AttributeDraft.of(LAUNDRY_SYMBOLS_ATTR_NAME, "cold")))))
                .isInstanceOf(ErrorResponseException.class)
                .matches(e -> ((ErrorResponseException)e).hasErrorCode(InvalidField.CODE));
    }

    @Test
    public void orderImportExample() throws Exception {
        final Product product = createProduct();
        //yellow is not defined in the product type, but for order imports this works to add use it on the fly
        final LocalizedEnumValue yellow =
                LocalizedEnumValue.of("yellow", LocalizedString.of(ENGLISH, "yellow").plus(GERMAN, "gelb"));
        final ProductVariantImportDraft productVariantImportDraft = ProductVariantImportDraftBuilder.of(product.getId(), 1)
                .attributes(
                        AttributeImportDraft.of(COLOR_ATTR_NAME, yellow),
                        AttributeImportDraft.of(RRP_ATTR_NAME, EURO_30)
                ).build();
        final LineItemImportDraft lineItemImportDraft =
                LineItemImportDraftBuilder.of(productVariantImportDraft, 1L, Price.of(EURO_30), en("product name"))
                .build();
        final OrderImportDraft orderImportDraft = OrderImportDraftBuilder
                .ofLineItems(EURO_20, OrderState.COMPLETE, asList(lineItemImportDraft))
                .build();

        final Order order = client().executeBlocking(OrderImportCommand.of(orderImportDraft));

        final ProductVariant productVariant = order.getLineItems().get(0).getVariant();
        final Optional<LocalizedEnumValue> colorAttribute =
                productVariant.findAttribute(COLOR_ATTR_NAME, AttributeAccess.ofLocalizedEnumValue());
        assertThat(colorAttribute).contains(yellow);
        final Optional<MonetaryAmount> rrpAttribute =
                productVariant.findAttribute(RRP_ATTR_NAME, AttributeAccess.ofMoney());
        assertThat(rrpAttribute).contains(EURO_30);
        final Set<String> presentAttributes = productVariant.getAttributes().stream()
                .map(attr -> attr.getName())
                .collect(toSet());
        assertThat(presentAttributes).containsOnly(COLOR_ATTR_NAME, RRP_ATTR_NAME);
    }
}
