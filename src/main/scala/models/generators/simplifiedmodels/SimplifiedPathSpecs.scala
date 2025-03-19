package com.jfast
package models.generators.simplifiedmodels

import models.openapi.ApiSpecification

import com.jfast.utilities.CasingType
import com.jfast.utilities.ControllerUtilities.getControllerName
import com.jfast.utilities.GeneralUtilities.createJavaMap

import java.util
import java.util.LinkedList
import scala.jdk.CollectionConverters.*

class SimplifiedPathSpecs(apiSpecification: ApiSpecification, casingType: CasingType, customPathSpecs: Option[List[SimplifiedPathSpec]] = None) {
  private val interiorPaths = if customPathSpecs.isDefined
  then customPathSpecs.get
  else apiSpecification
    .getPathSpecifications
    .map(x => SimplifiedPathSpecMapper.mapFromPathSpec(x, casingType))

  val paths: util.LinkedList[SimplifiedPathSpec] = util.LinkedList[SimplifiedPathSpec](interiorPaths.asJava)
  val baseAddress: String = apiSpecification.getBaseAddress
  val apiName: String = apiSpecification.getName

  private def pathControllerMap: Map[String, List[SimplifiedPathSpec]] = interiorPaths.groupBy(x => x.controllerName())
  def uniqueControllers: List[String] = pathControllerMap.keys.toList

  def controllerPath(key: String): List[SimplifiedPathSpec] = {
    if uniqueControllers.contains(key) then pathControllerMap(key) else List.empty
  }
}
