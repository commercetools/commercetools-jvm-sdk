package io.sphere.sdk.categories

import io.sphere.sdk.client.SdkIntegrationTest
import org.scalatest._
import java.util.Locale
import com.google.common.base.{Function => GFunction, Supplier, Optional}
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._

class CategoryIntegrationSpec extends FunSuite with Matchers with SdkIntegrationTest {
    test("query all categories"){
      val pagedQueryResult = client.execute(new CategoryQuery)
      pagedQueryResult.getCount should be > (3)
      pagedQueryResult.getTotal should be > (3)
      pagedQueryResult.getOffset should be(0)
      pagedQueryResult.getResults.get(0).getId.length should be > (5)
    }

  test("query by name"){
    val name = "query by name test name"
    val slug = "query-by-name-test-slug"

    withCleanup{
      deleteCategoryByName(name)
    }{
      val category = createCategory(slug, name)
      client.execute(Category.query().byName(locale, name)).headOption.get.getId should be(category.getId)
    }
  }

  test("query not by name"){
    val name = "query by not name test name"
    val slug = "query-by-not-name-test-slug"

    withCleanup{
      deleteCategoryByName(name)
    }{
      val category = createCategory(slug, name)
      client.execute(Category.query.byName(locale, name)).headOption.get.getId should be(category.getId)
      val notNameQuery = Category.query.withPredicate(CategoryQueryModel.get.name.lang(locale).isNot(name))
      client.execute(notNameQuery).getResults.map(_.getName) should not contain(name)
    }
  }

  test("query by id"){
    val slug1 = "query-by-id-test-slug-1"
    val slug2 = "query-by-id-test-slug-2"

    withCleanup{
      deleteCategoryByName(slug1)
      deleteCategoryByName(slug2)
    }{
      val category1 = createCategory(slug1, slug1)
      val category2 = createCategory(slug2, slug2)
      val query = Category.query.withPredicate(CategoryQueryModel.get.name.lang(locale).isOneOf(slug1, "not present", slug2))
      client.execute(query).getResults.map(_.getName.get(locale).get).toSet should be(Set(slug1, slug2))
    }
  }

  test("create category"){
    val slug = "create-category-test"
    val name = "create category test"
    val desc = "desc create category test"
    val hint = "0.5"

    withCleanup{
      deleteCategoryByName(name)
      deleteCategoryByName(name + "parent")
    }{
      val newParentCategory = NewCategoryBuilder.create(name + "parent", slug + "parent").description(desc + "parent").orderHint(hint + "3").build
      val parentCategory = client.execute(new CategoryCreateCommand(newParentCategory))
      val reference = Category.reference(parentCategory)
      val newCategory = NewCategoryBuilder.create(name, slug).description(desc).orderHint(hint).parent(reference).build
      val category = client.execute(new CategoryCreateCommand(newCategory))
      category.getName should be(name.localized)
      category.getDescription.get should be(desc.localized)
      category.getSlug should be(slug.localized)
      category.getOrderHint.get should be(hint)
      category.getParent should be(Optional.of(reference.filled(Optional.absent[Category]())))
      parentCategory.getParent should be(Optional.absent())
    }
  }

  test("delete category"){
    val name = "delete-category"
    withCleanup(deleteCategoryByName(name)){
      val newCategory = NewCategoryBuilder.create(name, name).build
      client.execute(new CategoryCreateCommand(newCategory))
      deleteCategoryByName(name)
      getCategoryByName(name) should be(Optional.absent())
    }
  }

  def getCategoryByName(name: String): Optional[Category] = client.execute(new CategoryByNameQuery(Locale.ENGLISH, name)).headOption

  def deleteCategoryByName(name: String): Unit = {
    getCategoryByName(name).foreach { category =>
      client.execute(new CategoryDeleteCommand(category))
    }
  }

  def createCategory(slug: String, name: String): Category = {
    val newCategory = NewCategoryBuilder.create(name, slug).description(name).build
    client.execute(new CategoryCreateCommand(newCategory))
  }
}
