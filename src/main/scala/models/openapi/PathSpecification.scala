package com.jfast
package models.openapi

import utilities.Printable

class PathSpecification(
                         var name: String,
                         var urlSegment: String,
                         var operationType: HttpOperationType,
                         var parameters: List[PathSpecificationParameter],
                         var responses: List[PathResponse]
                       )  extends Printable {
  def getName: String = name
  def setName(name: String): Unit = this.name = name

  def getOperationType: HttpOperationType = operationType
  def setOperationType(operationType: HttpOperationType): Unit = this.operationType = operationType

  def print(): Unit = {
    println(s"--- Path ${getName} ---")
    println(s"Type: $operationType")
    if (parameters.nonEmpty)
      println("Parameters:")
    for (p <- parameters) {
      println(s"\t${p.getName} - ${p.getLocation}" +
        (if (p.getLocation == PathParamLocationType.Path) s" [${p.getPositionIndex}]" else "") +
        (if (p.getType.customTypeName != null) s" ${p.getType.customTypeName}" +
          (if (p.getType.aggregateType == AggregateType.Array) "[]" else "") else ""))
    }
  }

  def getResponses: List[PathResponse] = responses
  def setResponses(responses: List[PathResponse]): Unit = this.responses = responses

  def getParameters: List[PathSpecificationParameter] = parameters
  def setParameters(parameters: List[PathSpecificationParameter]): Unit = this.parameters = parameters

  def getUrlSegment: String = urlSegment
  def setUrlSegment(urlSegment: String): Unit = this.urlSegment = urlSegment
}

