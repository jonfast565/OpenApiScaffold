package com.jfast
package converters.openapi

import models.openapi.*
import utilities.GeneralUtilities

import io.swagger.v3.oas.models.media.*
import io.swagger.v3.oas.models.parameters.Parameter
import io.swagger.v3.oas.models.{OpenAPI, Operation, PathItem, responses}
import io.swagger.v3.parser.core.models.SwaggerParseResult

import scala.jdk.CollectionConverters.*

object OpenApiSpecificationConverter {
  def buildApiSpecification(name: String, baseAddress: String, result: SwaggerParseResult): ApiSpecification = {
    val apiSpecification = new ApiSpecification(name, baseAddress)
    val openApiResult: OpenAPI = result.getOpenAPI
    val pathSpecifications = extractPathSpecifications(openApiResult)
    val classSpecifications = extractClassSpecifications(openApiResult)

    apiSpecification.setClassSpecifications(classSpecifications)
    apiSpecification.setPathSpecifications(pathSpecifications)
    apiSpecification.setBaseAddress(baseAddress)
    apiSpecification
  }

  private def extractClassSpecifications(openApiResult: OpenAPI): List[ClassSpecification] = {
    var classSpecifications = List[ClassSpecification]()
    val objects = openApiResult.getComponents.getSchemas
    for ((name, schema) <- objects.asScala) {
      var classProps = List[PropertySpecification]()
      val properties = schema.getProperties
      for ((property, propSchema) <- properties.asScala) {
        val mapKey = property
        classProps = classProps ++ List(PropertySpecification(mapKey, getTypeFromSchema(propSchema)))
      }
      val newClassSpec = ClassSpecification(name)
      newClassSpec.setProperties(classProps)

      classSpecifications = classSpecifications ++ List(newClassSpec)
    }
    classSpecifications
  }

  private def extractPathSpecifications(openApiResult: OpenAPI): List[PathSpecification] = {
    var pathSpecifications = List[PathSpecification]()
    val paths = openApiResult.getPaths
    for ((path, item) <- paths.asScala) {
      var operation: Operation = null
      var operationType: HttpOperationType = null
      if (item.getGet != null) {
        operationType = HttpOperationType.Get
        operation = item.getGet
      } else if (item.getPut != null) {
        operationType = HttpOperationType.Put
        operation = item.getPut
      } else if (item.getPost != null) {
        operationType = HttpOperationType.Post
        operation = item.getPost
      } else if (item.getPatch != null) {
        operationType = HttpOperationType.Patch
        operation = item.getPatch
      } else if (item.getDelete != null) {
        operationType = HttpOperationType.Delete
        operation = item.getDelete
      } else {
        operationType = HttpOperationType.None
        operation = null
      }
      val pathNiceName = getPathNice(path, operationType)
      var counter = 0
      var pathSpecParam = List[PathSpecificationParameter]()
      if (operation.getParameters != null) {
        for (p <- operation.getParameters.asScala) {
          val location = getPathParamLocation(p)
          val pathSchema = p.getSchema
          val `type` = getTypeFromSchema(pathSchema)
          val param = PathSpecificationParameter(
            p.getName,
            location,
            if (location == PathParamLocationType.Path) counter else 0,
            `type`
          )
          pathSpecParam = pathSpecParam ++ List(param)
          if (location == PathParamLocationType.Path) counter += 1
        }
      }
      val requestBody = operation.getRequestBody
      if (requestBody != null) {
        val jsonContent = requestBody.getContent.get("application/json")
        if (jsonContent != null) {
          val contentSchema = jsonContent.getSchema
          val `type` = getTypeFromSchema(contentSchema)
          val param = PathSpecificationParameter("body", PathParamLocationType.Body, 0, `type`)
          pathSpecParam = pathSpecParam ++ List(param)
        }
      }
      val requestResult = operation.getResponses
      var pathResponses = List[PathResponse]()
      if (requestResult != null) {
        if (requestResult.containsKey("200")) {
          val r = requestResult.get("200")
          pathResponses = pathResponses ++ List(getPathResult(r, "200"))
        } else if (requestResult.containsKey("204")) {
          val r = requestResult.get("204")
          pathResponses = pathResponses ++ List(getPathResult(r, "204"))
        } else if (requestResult.containsKey("404")) {
          val r = requestResult.get("404")
          pathResponses = pathResponses ++ List(getPathResult(r, "404"))
        }
      }
      val filteredResponses = pathResponses
        .filter(x => x != null)
      val pathSpec = PathSpecification(pathNiceName, path, operationType, pathSpecParam, filteredResponses)
      pathSpecifications = pathSpecifications ++ List(pathSpec)
    }
    pathSpecifications
  }

  private def getPathResult(response: responses.ApiResponse, statusCode: String): PathResponse = {
    try {
      val objectSchema = response.getContent.get("application/json")
      if (objectSchema != null) {
        val actualSchema = objectSchema.getSchema
        val `type` = getTypeFromSchema(actualSchema)
        new PathResponse() {
          setType(`type`)
          setStatusCode(statusCode)
        }
      } else null
    } catch {
      case e: Exception =>
        throw e
    }
  }

  private def getPathParamLocation(p: Parameter): PathParamLocationType = p.getIn match {
    case "path" => PathParamLocationType.Path
    case "query" => PathParamLocationType.Query
    case _ => PathParamLocationType.Body
  }

  private def getPathNice(path: String, operation: HttpOperationType): String = {
    val pathNiceName = path.split("/").filter(!_.equals("api")).filter {
      _.nonEmpty
    }
    val reduction = new StringBuilder(operation.toString)
    var midParamsList = false
    for (nice <- pathNiceName) {
      if (nice.contains("{") || nice.contains("}")) {
        val removedNice = nice.replace("{", "").replace("}", "")
        if (!midParamsList) {
          midParamsList = true
          reduction.append("By").append(GeneralUtilities.capitalize(removedNice))
        } else {
          reduction.append(GeneralUtilities.capitalize(removedNice))
        }
      } else {
        reduction.append(GeneralUtilities.capitalize(nice))
      }
    }
    reduction.toString
  }

  private def getTypeFromSchema(schema: Schema[?]): ResolvedType = {
    val typeStr = if (schema.getType == null) "" else schema.getType
    val format = schema.getFormat
    val reference = schema.get$ref
    typeStr match {
      case "object" => ResolvedType(FieldType.Object, AggregateType.None, null)
      case "array" =>
        val arrayType = schema.asInstanceOf[ArraySchema]
        val items = arrayType.getItems
        val arrayItemCustomType = items.get$ref
        val arrayItemType = items.getType
        if (arrayItemCustomType != null) {
          val propType = GeneralUtilities.removePropComponentString(arrayItemCustomType)
          ResolvedType(FieldType.Custom, AggregateType.Array, propType)
        } else if (arrayItemType != null) {
          ResolvedType(FieldType.String, AggregateType.Array, null)
        } else null
      case "integer" | "number" =>
        if (format == "int64") {
          ResolvedType(FieldType.LongInteger, AggregateType.None, null)
        } else if (format == "double") {
          ResolvedType(FieldType.Double, AggregateType.None, null)
        } else {
          ResolvedType(FieldType.Integer, AggregateType.None, null)
        }
      case "boolean" => ResolvedType(FieldType.Boolean, AggregateType.None, null)
      case "string" =>
        if (format == "telephone-number") {
          ResolvedType(FieldType.TelephoneNumber, AggregateType.None, null)
        } else if (format == "datetime") {
          ResolvedType(FieldType.DateTime, AggregateType.None, null)
        } else {
          ResolvedType(FieldType.String, AggregateType.None, null)
        }
      case _ =>
        if (reference != null) {
          val propType = GeneralUtilities.removePropComponentString(reference)
          ResolvedType(FieldType.Custom, AggregateType.None, propType)
        } else {
          throw new Exception("Type not found")
        }
    }
  }
}

