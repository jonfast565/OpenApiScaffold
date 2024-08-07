package com.jfast
package models.generators.simplifiedmodels

import utilities.{CasingType, GeneralUtilities}
import models.openapi.{AggregateType, PathParamLocationType, PathResponse, PathSpecification, PathSpecificationParameter}

import com.jfast.models.generators.csharp.{CSharpClassSpec, CSharpTypeMapper}
import com.jfast.models.generators.python.PythonTypeMapper

import java.util
import scala.jdk.CollectionConverters.*

object SimplifiedPathSpecMapper {
  def mapFromPathSpec(pathSpecification: PathSpecification, casingType: CasingType): SimplifiedPathSpec = {
    val pathVarMapper = (x: PathSpecificationParameter) =>
      SimplifiedPathVar(
        x.name,
        x.location.toString,
        if casingType == CasingType.PythonCasing
        then PythonTypeMapper.getPropertyFieldType(x.`type`)
        else CSharpTypeMapper.getPropertyFieldType(x.`type`),
        x.`type`.customTypeName,
        x.`type`.aggregateType == AggregateType.Array)

    val responseMapper = (x: PathResponse) =>
      SimplifiedResponse(
        x.getStatusCode,
        if casingType == CasingType.PythonCasing
        then PythonTypeMapper.getPropertyFieldType(x.getType)
        else CSharpTypeMapper.getPropertyFieldType(x.getType),
        x.getType.customTypeName,
        x.getType.aggregateType == AggregateType.Array
      )
    val name = if casingType == CasingType.PythonCasing
    then GeneralUtilities.pascalToSnake(pathSpecification.name)
    else pathSpecification.name
    val url = pathSpecification.urlSegment
    val operationType = if casingType == CasingType.PythonCasing
    then pathSpecification.operationType.toString.toLowerCase
    else pathSpecification.operationType.toString

    val pathList = pathSpecification.parameters
      .filter(x => x.location == PathParamLocationType.Path)
      .map(pathVarMapper)
    val queryList = pathSpecification.parameters
      .filter(x => x.location == PathParamLocationType.Query)
      .map(pathVarMapper)
    val body = pathSpecification.parameters
      .filter(x => x.location == PathParamLocationType.Body)
      .map(pathVarMapper)
      .collectFirst(x => x)
      .orNull

    val responses = pathSpecification.responses.map(responseMapper).toList

    val allVariables = pathList ++ queryList ++ (if body != null then List(body) else List())
    val queryLinkedList = util.LinkedList[SimplifiedPathVar](queryList.asJava)
    val pathLinkedList = util.LinkedList[SimplifiedPathVar](pathList.asJava)
    val allVariablesLinkedList = util.LinkedList[SimplifiedPathVar](allVariables.asJava)
    val responsesLinkedList = util.LinkedList[SimplifiedResponse](responses.asJava)

    SimplifiedPathSpec(
      name,
      url,
      operationType,
      if pathLinkedList.size() > 0 then pathLinkedList else null,
      if queryLinkedList.size() > 0 then queryLinkedList else null,
      body,
      if allVariablesLinkedList.size() > 0 then allVariablesLinkedList else null,
      if responsesLinkedList.size() > 0 then responsesLinkedList else null
    )
  }
}
