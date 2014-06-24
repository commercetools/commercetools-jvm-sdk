package io.sphere.sdk.models

import org.scalatest._
import com.google.common.base.Optional
import io.sphere.sdk.utils.JsonUtils
import JsonUtils.newObjectMapper
import com.fasterxml.jackson.core.`type`.TypeReference
import io.sphere.sdk.utils.JsonUtils

class ReferenceSpec extends WordSpec with ShouldMatchers {
  val TypeId = "typeId"
  val Id = "123"

  "References" must {
    "contain typeId and id" in {
      val reference = Reference.of[TestEntity](TypeId, Id)
      reference.getId should be(Id)
      reference.getTypeId should be(TypeId)
      reference.getObj should be(Optional.absent())
    }

    "contain optional a value" in {
      newFilledReference.getObj.get should be(newTestEntity)
    }

    "implement toString" in {
      newFilledReference.toString should be("Reference{typeId='typeId', id='123', obj=Optional.of(TestEntity{foo='value'})}")
      newEmptyReference.toString should be("Reference{typeId='typeId', id='123', obj=Optional.absent()}")
    }

    "implement equals" in {
      newFilledReference should be(newFilledReference)
      newEmptyReference should be(newEmptyReference)
      newFilledReference should not be (newEmptyReference)
      newFilledReference should not be (newEmptyReference.filled(new TestEntity("other value")))
      new Reference(TypeId, "Foo", Optional.of("x")) should not be (newEmptyReference)
    }

    "be serializeable to JSON with expanded reference" in {
      val actual = newObjectMapper.writeValueAsString(newFilledReference)
      actual should be( """{"typeId":"typeId","id":"123","obj":{"foo":"value"}}""")
    }

    "be serializeable to JSON without expanded reference" in {
      newObjectMapper.writeValueAsString(newEmptyReference) should be( s"""{"typeId":"$TypeId","id":"$Id","obj":null}""")
    }

    "be deserializable from JSON with expanded reference" in {
      val json = s"""{"typeId":"$TypeId","id": "$Id","obj":{"foo":"value"}}"""
      val actual = newObjectMapper.readValue[Reference[TestEntity]](json, new TypeReference[Reference[TestEntity]] {})
      actual should be(newFilledReference)
    }

    "be deserializable from JSON without expanded reference (empty style)" in {
      val json = s"""{"typeId":"$TypeId","id": "$Id"}"""
      newObjectMapper.readValue(json, classOf[Reference[TestEntity]]) should be(newEmptyReference)
    }

    "be deserializable from JSON without expanded reference (null style)" in {
      val json = s"""{"typeId":"$TypeId","id": "$Id","obj":null}"""
      newObjectMapper.readValue(json, classOf[Reference[TestEntity]]) should be(newEmptyReference)
    }
  }

  def newEmptyReference: Reference[TestEntity] = {
    Reference.of[TestEntity](TypeId, Id)
  }

  def newFilledReference: Reference[TestEntity] = {
    newEmptyReference.filled(newTestEntity)
  }

  def newTestEntity = new TestEntity("value")
}
