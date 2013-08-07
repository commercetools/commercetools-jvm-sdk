package io.sphere.client
package customobjects

import org.scalatest._
import beans.BeanProperty
import java.util.Collections

case class DemoObjectClass() {
  @BeanProperty var name: String = _
  @BeanProperty var version: Long = _
  @BeanProperty var parents: java.util.List[DemoObjectClass] = _
}

class CustomObjectSpec extends WordSpec with MustMatchers {
  val container = "namespace"
  val keyOfCustomObject = "the-key"
  val stringValue = "world"
  val jsonForComplexObject = s"""{ "container": "$container", "key": "$keyOfCustomObject", "value":
       {"name": "xyz", "version":1, "parents": [
          {"name": "abc", "version":200}, {"name": "uvw", "version": 500}
       ]}
       }"""


  "Get a String" in {
    val client = MockSphereClient.create(customObjectResponse = FakeResponse(
      s"""{ "container": "$container", "key": "$keyOfCustomObject", "value": "$stringValue" }"""))
    val doc = client.customObjects.get(container, keyOfCustomObject).fetch.get
    doc.as(classOf[String]) must be (stringValue)
  }

  "Get a complex object" in {
    val client = MockSphereClient.create(customObjectResponse = FakeResponse(jsonForComplexObject))
    val doc = client.customObjects.get(container, keyOfCustomObject).fetch.get
    val instance: DemoObjectClass = doc.as(classOf[DemoObjectClass])
    instance.name must be ("xyz")
    instance.version must be (1)
    instance.parents.get(0).name must be ("abc")
    instance.parents.get(1).version must be (500)
  }

  "Add a custom object" in {
    val client = MockSphereClient.create(customObjectResponse = FakeResponse(s"""{ "container": "$container", "key": "$keyOfCustomObject", "value":
       {"name": "abc", "version":1, "parents": []}
       }"""))
    val doc = client.customObjects.set(container, keyOfCustomObject, new DemoObjectClass() {
      name="abc"
      version=1
      parents = Collections.emptyList[DemoObjectClass]
    }).execute()
    doc.as(classOf[DemoObjectClass]).name must be ("abc")
  }

  "Delete a custom object" in {
    val client = MockSphereClient.create(customObjectResponse = FakeResponse(jsonForComplexObject))
    val doc = client.customObjects.delete(container, keyOfCustomObject).execute.get
    doc.as(classOf[DemoObjectClass]).name must be ("xyz")
  }
}
