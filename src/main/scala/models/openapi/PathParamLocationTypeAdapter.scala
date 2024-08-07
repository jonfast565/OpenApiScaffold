package com.jfast
package models.openapi

import com.google.gson.{JsonParseException, TypeAdapter}
import com.google.gson.stream.{JsonReader, JsonWriter}

object PathParamLocationTypeAdapter extends TypeAdapter[PathParamLocationType]:
  override def write(out: JsonWriter, value: PathParamLocationType): Unit = {
    out.value(value.toString)
  }

  override def read(in: JsonReader): PathParamLocationType = {
    val stringValue = in.nextString()
    PathParamLocationType.values.find(_.toString == stringValue) match {
      case Some(enumValue) => enumValue
      case None => throw new JsonParseException(s"Invalid enum value: $stringValue")
    }
  }
