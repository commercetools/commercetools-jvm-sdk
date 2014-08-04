package io.sphere.sdk.categories

import org.scalatest._
import io.sphere.sdk.categories._
import java.util.Locale
import io.sphere.sdk.models.LocalizedString

class CategorySpec extends FunSuite with Matchers {
  test("build query for slug"){
    new CategoryQuery().bySlug(Locale.ENGLISH, "foo").predicate().get().toSphereQuery should be("""slug(en="foo")""")
  }

  test("CreateCategoryCommand.equals"){
    val newCategory = NewCategoryBuilder.create(LocalizedString.of(Locale.ENGLISH, "name1"), LocalizedString.of(Locale.ENGLISH, "slug1")).build
    val newCategory2 = NewCategoryBuilder.create(LocalizedString.of(Locale.ENGLISH, "name2"), LocalizedString.of(Locale.ENGLISH, "slug2")).build
    val a = new CategoryCreateCommand(newCategory)
    val a2 = new CategoryCreateCommand(newCategory)
    val b = new CategoryCreateCommand(newCategory2)
    a should not be(b)
    a should be(a)
    a should be(a2)
  }

  test("NewCategor.equals"){
    val a = NewCategoryBuilder.create(LocalizedString.of(Locale.ENGLISH, "name1"), LocalizedString.of(Locale.ENGLISH, "slug1")).build
    val a2 = NewCategoryBuilder.create(LocalizedString.of(Locale.ENGLISH, "name1"), LocalizedString.of(Locale.ENGLISH, "slug1")).build
    val b = NewCategoryBuilder.create(LocalizedString.of(Locale.ENGLISH, "name2"), LocalizedString.of(Locale.ENGLISH, "slug2")).build
    a should not be(b)
    a should be(a)
    a should be(a2)
  }
}
