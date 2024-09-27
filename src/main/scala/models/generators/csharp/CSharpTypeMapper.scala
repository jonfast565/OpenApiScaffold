package com.jfast
package models.generators.csharp

import models.openapi.{AggregateType, FieldType, PropertySpecification, ResolvedType}

object CSharpTypeMapper {
  def getPropertyFieldType(`type`: ResolvedType): String = {
    val typeString = `type`.fieldType match
      case FieldType.LongInteger => "long"
      case FieldType.Integer => "int"
      case FieldType.DateTime => "DateTime"
      case FieldType.Object => "JsonDocument"
      case FieldType.Custom => s"${`type`.customTypeName}"
      case FieldType.Double => "double"
      case FieldType.TelephoneNumber => "string"
      case FieldType.Boolean => "bool"
      case FieldType.EmailAddress => "string"
      case FieldType.String => "string"
      case FieldType.None => ""

    val aggString = `type`.aggregateType match
      case AggregateType.None => typeString
      case AggregateType.Map => throw new Exception("Unsupported aggregate type")
      case AggregateType.Array => s"${typeString}[]"
    aggString
  }
}
