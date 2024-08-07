package com.jfast
package models.openapi

import com.google.gson.{JsonParseException, TypeAdapter}
import com.google.gson.stream.{JsonReader, JsonWriter}

object HttpOperationTypeAdapter extends TypeAdapter[HttpOperationType]:
  override def write(out: JsonWriter, value: HttpOperationType): Unit = {
    out.value(value.toString)
  }

  override def read(in: JsonReader): HttpOperationType = {
    val stringValue = in.nextString()
    HttpOperationType.values.find(_.toString == stringValue) match {
      case Some(enumValue) => enumValue
      case None => throw new JsonParseException(s"Invalid enum value: $stringValue")
    }
  }

