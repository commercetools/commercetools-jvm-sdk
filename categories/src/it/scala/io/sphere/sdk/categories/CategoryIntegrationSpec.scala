package io.sphere.sdk.categories

import io.sphere.sdk.client.SdkIntegrationTest
import org.scalatest._
import io.sphere.sdk.categories.commands.CreateCategoryCommand
import io.sphere.sdk.common.models.LocalizedString
import java.util.Locale


class CategoryIntegrationSpec extends WordSpec with ShouldMatchers with SdkIntegrationTest {

  val ClassName = classOf[CategoryServiceImpl].getName

  ClassName must {
    "query categories" in {
      val pagedQueryResult = client.execute(new CategoryQuery).get
      pagedQueryResult.getCount should be > (3)
      pagedQueryResult.getTotal should be > (3)
      pagedQueryResult.getOffset should be(0)
      pagedQueryResult.getResults.get(0).getId.length should be > (5)
    }

    "create category" in {
      val name = LocalizedString.of(Locale.GERMAN, "name1")
      val description = LocalizedString.of(Locale.GERMAN, "description1")
      val slug = LocalizedString.of(Locale.GERMAN, ClassName + "-create-category")
      val newCategory = NewCategoryBuilder.create(name, slug).description(description).orderHint("0.5").build
      val category = client.execute(new CreateCategoryCommand(newCategory)).get
      category.getName should be(name)
      category.getDescription should be(description)
      category.getSlug should be(slug)
      category.getParent should be(None)
    }
  }
}
