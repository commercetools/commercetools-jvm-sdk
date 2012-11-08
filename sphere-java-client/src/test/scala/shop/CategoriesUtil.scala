package de.commercetools.sphere.client
package shop

object CategoriesUtil {
  val categoriesJson =
    """{
    "offset" : 0,
    "count" : 6,
    "total" : 6,
    "results" : [ {
      "id" : "id-sport", "version" : 1, "name" : "Sports cars", "ancestors" : [ ]
    }, {
      "id" : "id-convert", "version" : 1, "name" : "Convertibles", "ancestors" : [ ]
    }, {
      "id" : "id-v6",
      "version" : 2,
      "name" : "V6",
      "ancestors" : [ { "typeId" : "category", "id" : "id-sport"} ],
      "parent" : { "typeId" : "category", "id" : "id-sport" }
    }, {
      "id" : "id-v8",
      "version" : 2,
      "name" : "V8",
      "ancestors" : [ { "typeId" : "category", "id" : "id-sport" } ],
      "parent" : { "typeId" : "category", "id" : "id-sport" }
    }, {
      "id" : "id-super",
      "version" : 2,
      "name" : "Supercharger",
      "ancestors" : [
         { "typeId" : "category", "id" : "id-sport" },
         { "typeId" : "category", "id" : "id-v8"} ],
      "parent" : { "typeId" : "category", "id" : "id-v8" }
    }, {
      "id" : "id-turbo",
      "version" : 2,
      "name" : "Turbocharger",
      "ancestors" : [
        { "typeId" : "category", "id" : "id-sport"},
        { "typeId" : "category", "id" : "id-v8" } ],
      "parent" : { "typeId" : "category", "id" : "id-v8" }
    } ]
  }"""
}
