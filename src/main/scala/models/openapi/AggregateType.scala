package com.jfast
package models.openapi

import com.google.gson.{TypeAdapter, JsonParseException}
import com.google.gson.stream.{JsonReader, JsonWriter}

enum AggregateType {
  case None, Map, Array
}
