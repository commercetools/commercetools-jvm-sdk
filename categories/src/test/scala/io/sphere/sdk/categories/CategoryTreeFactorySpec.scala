package io.sphere.sdk.categories

import org.scalatest._
import io.sphere.sdk.categories._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import io.sphere.sdk.models.{Reference, LocalizedString}
import java.util.{Comparator, Locale}
import com.google.common.base.Optional
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import com.google.common.collect.Ordering

class CategoryTreeFactorySpec extends FunSuite with Matchers {
  implicit val locale = Locale.ENGLISH
  implicit def stringToLocalizedString(s: String) = LocalizedString.of(locale, s)
  def orphanCategory(id: String) = CategoryBuilder.of(id, s"name $id", s"slug-$id").build

  test("create empty category hierarchy on null"){
    val categoryTree = CategoryTreeFactory.create(null)
    categoryTree.getAllAsFlatList.asScala should be(Nil)
    categoryTree.getRoots.asScala should be(Nil)
  }

  test("create empty category hierarchy"){
    val categoryTree = CategoryTreeFactory.create(Nil)
    categoryTree.getAllAsFlatList.asScala should be(Nil)
    categoryTree.getRoots.asScala should be(Nil)
  }

  test("create flat category hierarchy") {
    val categories = (0 to 3) map (id => orphanCategory(id.toString)) toList
    val categoryTree = CategoryTreeFactory.create(categories)
    categoryTree.getAllAsFlatList.map(_.getId) should be(categories.map(_.getId))
    categoryTree.getRoots.map(_.getId) should be(categories.map(_.getId))
  }

  test("create category hierarchy") {
    val ids = (0 to 3).map(_.toString).toList
    val childIds = "abcde".toList
    val grandChildIds = "uvwx".toList
    val rootCategories: List[Category] = ids map (orphanCategory(_))
    val children: List[Category] = rootCategories map { parent =>
      childIds map { childSubId =>
        new CategoryWrapper(orphanCategory(parent.getId + childSubId)) {
          override def getParent: Optional[Reference[Category]] = Optional.of(Categories.reference(parent))
        }
      }
    } flatten

    val grandChildren: List[Category] = children map { parent =>
      grandChildIds map { childSubId =>
        new CategoryWrapper(orphanCategory(parent.getId + childSubId)) {
          override def getParent: Optional[Reference[Category]] = Optional.of(Categories.reference(parent))
        }
      }
    } flatten


    val categories = rootCategories ++ children ++ grandChildren
    val categoryTree = CategoryTreeFactory.create(categories)
    categoryTree.getAllAsFlatList.map(_.getId).toSet should be(categories.map(_.getId).toSet)
    categoryTree.getRoots.map(_.getId).toSet should be(ids.toSet)
    val byNameSorter = new Comparator[Category] {
      override def compare(o1: Category, o2: Category): Int = o1.getName.get(locale).get().compareTo(o2.getName.get(locale).get())
    }

    val nameSortedRoots = Ordering.from(byNameSorter).immutableSortedCopy(categoryTree.getRoots)
    val category0 = nameSortedRoots.head
    category0.getId should be(ids.head)
    category0.getChildren should have size(childIds.size)
    val sortedChildren = Ordering.from(byNameSorter).immutableSortedCopy(category0.getChildren)
    sortedChildren should have size(childIds.size)
    sortedChildren(1).getId should be("0b")
    val sortedGrandChildren = Ordering.from(byNameSorter).immutableSortedCopy(sortedChildren(1).getChildren)
    sortedGrandChildren should have size(grandChildIds.size)
    sortedGrandChildren.map(_.getId) should be(List("0bu", "0bv", "0bw", "0bx"))

    categoryTree.getById("0bu").get.getId should be("0bu")
    categoryTree.getById("not-present") should be(Optional.absent)
    categoryTree.getBySlug("slug-0bu", locale).get().getId should be("0bu")
    val absentLocale = Locale.GERMAN
    categoryTree.getBySlug("slug-0bu", absentLocale) should be(Optional.absent)
    categoryTree.getBySlug("slug-0bu", absentLocale) should be(Optional.absent)
  }
}
