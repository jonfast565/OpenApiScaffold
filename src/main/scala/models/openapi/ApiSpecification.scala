package com.jfast
package models.openapi

class ApiSpecification (private var name: String, private var baseAddress: String) {
  private var classSpecifications: List[ClassSpecification] = List()
  private var pathSpecifications: List[PathSpecification] = List()

  def getClassSpecifications: List[ClassSpecification] = classSpecifications

  def setClassSpecifications(classSpecifications: List[ClassSpecification]): Unit = {
    this.classSpecifications = classSpecifications
  }

  def getPathSpecifications: List[PathSpecification] = pathSpecifications

  def setPathSpecifications(pathSpecifications: List[PathSpecification]): Unit = {
    this.pathSpecifications = pathSpecifications
  }

  def print(): Unit = {
    classSpecifications.foreach(_.print())
    pathSpecifications.foreach(_.print())
  }

  def getName: String = name

  def setName(name: String): Unit = {
    this.name = name
  }

  def getBaseAddress: String = baseAddress

  def setBaseAddress(baseAddress: String): Unit = {
    this.baseAddress = baseAddress
  }
}
