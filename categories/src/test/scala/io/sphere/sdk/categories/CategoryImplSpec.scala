package io.sphere.sdk.categories

import org.scalatest._
import java.util.Locale
import io.sphere.sdk.models.{Reference, LocalizedString}
import scala.collection.JavaConverters._
import scala.collection.JavaConversions._
import com.google.common.base.Optional

class CategoryImplSpec extends FunSuite with Matchers {
  implicit val locale = Locale.ENGLISH
  implicit def stringToLocalizedString(s: String) = LocalizedString.of(locale, s)

  test("category creation"){
    val child = CategoryBuilder.of("women-shoes-id", "women-shoes-name", "women-shoes-slug").build()
    child.getId should be("women-shoes-id")
    child.getDescription should be(Optional.absent())
    child.getName.get(locale).get() should be("women-shoes-name")
    child.getSlug.get(locale).get should be("women-shoes-slug")

    val children = List(child)
    val ancestors = List(Category.reference("parent"))
    val category: Category = new CategoryWrapper(buildCategory(children, ancestors)) {}//wrapping is not necessary for test, but it tests the wrapper class, too.
    category.getAncestors.asScala should be(ancestors)
    category.getId should be("shoes-id")
    category.getName.get(locale).get should be("shoes-name")
    category.getSlug.get(locale).get should be("shoes-slug")
    category.getDescription.get().get(locale).get should be("shoes-description")
    category.getParent should be(Optional.absent())
    category.getOrderHint.get should be("orderHint")
    category.getChildren.asScala should be(children)
    category.getPathInTree.asScala should be(Nil)
    category.toString should include(category.getId)
  }

  test("category equals and hash code"){
    val categoryA = buildCategory(Nil, Nil)
    val categoryA2 = CategoryBuilder.of(categoryA).build
    val categoryB = CategoryBuilder.of(categoryA).id("other").build()
    categoryA should be(categoryA)
    categoryA should be(categoryA2)
    categoryA should not be(categoryB)
    categoryA.hashCode should be(categoryA2.hashCode())
  }

  def buildCategory(children: List[Category], ancestors: List[Reference[Category]]): Category = {
    CategoryBuilder.of("shoes-old", "shoes-old", "shoes-old").
      id("shoes-id").
      ancestors(ancestors).
      children(children).
      description("shoes-description").
      name("shoes-name").
      slug("shoes-slug").
      version(4).
      orderHint("orderHint").
      build
  }
}
