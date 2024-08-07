package com.jfast
package models.serialization

import com.google.gson.{Gson, TypeAdapter}
import com.google.gson.stream.{JsonReader, JsonToken, JsonWriter}

import scala.collection.{immutable, mutable}

object ImmutableMapTypeAdapter extends TypeAdapter[immutable.Map[String, Any]] {
  private val gson = Gson()

  override def read(reader: JsonReader): immutable.Map[String, Any] = {
    val map = mutable.Map[String, Any]()
    reader.beginObject()
    while (reader.hasNext) {
      val key = reader.nextName()
      val token = reader.peek()
      token match {
        case JsonToken.NULL =>
          reader.nextNull()
          map.put(key, null)
        case JsonToken.STRING => map.put(key, reader.nextString())
        case JsonToken.NUMBER => map.put(key, reader.nextDouble())
        case JsonToken.BOOLEAN => map.put(key, reader.nextBoolean())
        case JsonToken.BEGIN_OBJECT => map.put(key, read(reader))
        case _ => reader.skipValue()
      }
    }
    reader.endObject()
    immutable.Map[String, Any](map.toSeq*)
  }

  override def write(writer: JsonWriter, map: immutable.Map[String, Any]): Unit = {
    writer.beginObject()
    map.foreach { case (key, value) =>
      writer.name(key)
      if (value == null) {
        writer.nullValue()
      } else {
        gson.toJson(value, value.getClass, writer)
      }
    }
    writer.endObject()
  }
}
