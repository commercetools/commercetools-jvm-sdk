package io.sphere.sdk.queries

import io.sphere.sdk.categories.Category
import play.twirl.api.Html

//dummy
object categoriesTemplate {
  def render(categories: java.util.List[Category]): Html = {
    Html(categories.toString)
  }
}
