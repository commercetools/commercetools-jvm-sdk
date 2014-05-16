package io.sphere.sdk.categories

import io.sphere.sdk.client.SdkIntegrationTest
import org.scalatest._
import io.sphere.sdk.categories.requests.{GetCategoryByName, DeleteCategoryCommand, CategoryQuery, CreateCategoryCommand}
import io.sphere.sdk.common.models.{VersionedImpl, LocalizedString}
import java.util.Locale
import com.google.common.base.{Function => GFunction, Supplier, Optional}

class CategoryIntegrationSpec extends FunSuite with Matchers with SdkIntegrationTest {
    test("query all categories"){
      val pagedQueryResult = client.execute(new CategoryQuery).get
      pagedQueryResult.getCount should be > (3)
      pagedQueryResult.getTotal should be > (3)
      pagedQueryResult.getOffset should be(0)
      pagedQueryResult.getResults.get(0).getId.length should be > (5)
    }

  test("create category"){
    val slug = "create-category-test"
    val name = "create category test"
    val desc = "desc create category test"
    val hint = "0.5"

    withCleanup(deleteCategoryByName(name)){
      val newCategory = NewCategoryBuilder.create(name, slug).description(desc).orderHint(hint).build
      val category = client.execute(new CreateCategoryCommand(newCategory)).get
      category.getName should be(name.localized)
      category.getDescription.get should be(desc.localized)
      category.getSlug should be(slug.localized)
      category.getOrderHint.get should be(hint)
      category.getParent should be(Optional.absent())
    }
  }

  test("delete category"){
    val name = "delete-category"
    withCleanup(deleteCategoryByName(name)){
      val newCategory = NewCategoryBuilder.create(name, name).build
      client.execute(new CreateCategoryCommand(newCategory)).get
      deleteCategoryByName(name)
      getCategoryByName(name) should be(Optional.absent())
    }
  }

  def getCategoryByName(name: String): Optional[Category] = client.execute(new GetCategoryByName(Locale.ENGLISH, name)).get

  def deleteCategoryByName(name: String): Unit = {
    getCategoryByName(name).foreach { category =>
      println(category + "***************")
      client.execute(new DeleteCategoryCommand(category)).get
    }
  }
}
