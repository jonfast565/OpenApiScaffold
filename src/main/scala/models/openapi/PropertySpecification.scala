package com.jfast
package models.openapi


class PropertySpecification (var name: String, var `type`: ResolvedType) {
  def getName: String = name
  def setName(name: String): Unit = this.name = name

  def getType: ResolvedType = `type`
  def setType(`type`: ResolvedType): Unit = this.`type` = `type`
}
