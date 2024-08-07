package com.jfast
package models.generators.python

import models.openapi.{FieldType, AggregateType, ResolvedType}

object PythonTypeMapper {
  def getPropertyFieldType(`type`: ResolvedType) = {
    val typeString = `type`.fieldType match
      case FieldType.LongInteger => "int"
      case FieldType.None => throw new Exception("No field type found")
      case FieldType.Integer => "int"
      case FieldType.DateTime => "datetime.DateTime"
      case FieldType.Object => "dict"
      case FieldType.Custom => s"ClassVar"
      case FieldType.Double => "float"
      case FieldType.TelephoneNumber => "str"
      case FieldType.Boolean => "bool"
      case FieldType.EmailAddress => "str"
      case FieldType.String => "str"

    val aggString = `type`.aggregateType match
      case AggregateType.None => typeString
      case AggregateType.Map => throw new Exception("Unsupported aggregate type")
      case AggregateType.Array => s"list[${typeString}]"
    aggString
  }
}
