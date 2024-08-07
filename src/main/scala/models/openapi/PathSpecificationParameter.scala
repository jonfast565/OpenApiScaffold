package com.jfast
package models.openapi


class PathSpecificationParameter(
                                  var name: String,
                                  var location: PathParamLocationType,
                                  var positionIndex: Int,
                                  var `type`: ResolvedType
                                ) {
  def getName: String = name
  def setName(name: String): Unit = this.name = name

  def getLocation: PathParamLocationType = location
  def setLocation(location: PathParamLocationType): Unit = this.location = location

  def getPositionIndex: Int = positionIndex
  def setPositionIndex(positionIndex: Int): Unit = this.positionIndex = positionIndex

  def getType: ResolvedType = `type`
  def setType(`type`: ResolvedType): Unit = this.`type` = `type`
}

