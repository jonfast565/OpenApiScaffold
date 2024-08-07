package com.jfast
package models.openapi

import com.google.gson.{JsonParseException, TypeAdapter}
import com.google.gson.stream.{JsonReader, JsonWriter}

object AggregateTypeAdapter extends TypeAdapter[AggregateType]:
  override def write(out: JsonWriter, value: AggregateType): Unit = {
    out.value(value.toString)
  }

  override def read(in: JsonReader): AggregateType = {
    val stringValue = in.nextString()
    AggregateType.values.find(_.toString == stringValue) match {
      case Some(enumValue) => enumValue
      case None => throw new JsonParseException(s"Invalid enum value: $stringValue")
    }
  }
