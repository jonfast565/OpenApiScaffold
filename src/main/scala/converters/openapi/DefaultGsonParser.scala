package com.jfast
package converters.openapi

import com.google.gson.{Gson, GsonBuilder}
import models.openapi.{AggregateType, AggregateTypeAdapter, FieldType, FieldTypeAdapter, HttpOperationType, HttpOperationTypeAdapter, PathParamLocationType, PathParamLocationTypeAdapter}

import com.jfast.models.serialization.{ImmutableMapTypeAdapter, MutableMapTypeAdapter}

import scala.collection.{immutable, mutable}

object DefaultGsonParser {
  def get() : Gson = {
      val gson = new GsonBuilder()
        .registerTypeAdapter(classOf[AggregateType], AggregateTypeAdapter)
        .registerTypeAdapter(classOf[FieldType], FieldTypeAdapter)
        .registerTypeAdapter(classOf[HttpOperationType], HttpOperationTypeAdapter)
        .registerTypeAdapter(classOf[PathParamLocationType], PathParamLocationTypeAdapter)
        .registerTypeAdapter(classOf[mutable.Map[String, Any]], MutableMapTypeAdapter)
        .registerTypeAdapter(classOf[immutable.Map[String, Any]], ImmutableMapTypeAdapter)
        .setPrettyPrinting()
        .create()
      gson
    }
}
