package com.jfast
package models.openapi


import utilities.{GeneralUtilities, Printable}

import scala.compiletime.uninitialized

class ClassSpecification (var name: String) extends Printable {
  var properties: List[PropertySpecification] = List()

  def getName: String = name
  def setName(name: String): Unit = this.name = name

  def getProperties: List[PropertySpecification] = properties
  def setProperties(properties: List[PropertySpecification]): Unit = this.properties = properties

  def print(): Unit = {
    println(s"--- Model ${getName} ---")
    for (ps <- getProperties) {
      println(s"${ps.getName} -> ${ps.getType.fieldType}" +
        (if (ps.getType.aggregateType == AggregateType.Array) "[]" else "") +
        (if (GeneralUtilities.stringIsNullOrEmptyOrBlank(ps.getType.customTypeName)) "" else s" of ${ps.getType.customTypeName}"))
    }
    println()
  }
}

