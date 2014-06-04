package scala.io.sphere.sdk.categories

import org.scalatest._
import io.sphere.sdk.categories._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import io.sphere.sdk.models.{Reference, LocalizedString}
import java.util.{Comparator, Locale}
import com.google.common.base.Optional
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

class CategoryTreeFactorySpec extends FunSuite with Matchers {
  implicit val locale = Locale.ENGLISH
  implicit def stringToLocalizedString(s: String) = LocalizedString.of(locale, s)
  def orphanCategory(id: String) = CategoryBuilder.of(id, s"name $id", s"slug-$id").build

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
    val grandChildIds = "uvwxy".toList
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

    val nameSortedRoots = categoryTree.getRoots(byNameSorter)
    val category0 = nameSortedRoots.head
    category0.getId should be(ids.head)
    val children2 = category0.getChildren
    children2 should have size(childIds.size)
    val children1 = category0.getChildren(byNameSorter)
    children1 should have size(childIds.size)
  }
}
