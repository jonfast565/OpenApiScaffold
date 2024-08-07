package com.jfast
package models.openapi

import com.google.gson.{JsonParseException, TypeAdapter}
import com.google.gson.stream.{JsonReader, JsonWriter}

object FieldTypeAdapter extends TypeAdapter[FieldType]:
  override def write(out: JsonWriter, value: FieldType): Unit = {
    out.value(value.toString)
  }

  override def read(in: JsonReader): FieldType = {
    val stringValue = in.nextString()
    FieldType.values.find(_.toString == stringValue) match {
      case Some(enumValue) => enumValue
      case None => throw new JsonParseException(s"Invalid enum value: $stringValue")
    }
  }
