package com.jfast
package models.generators.simplifiedmodels

import models.openapi.ApiSpecification

import com.jfast.utilities.CasingType
import com.jfast.utilities.GeneralUtilities.createJavaMap

import java.util
import java.util.LinkedList
import scala.jdk.CollectionConverters.*

class SimplifiedPathSpecs(apiSpecification: ApiSpecification, casingType: CasingType) {
  private val pathControllerMap = apiSpecification
    .getPathSpecifications
    .map(x => SimplifiedPathSpecMapper.mapFromPathSpec(x, casingType))
    .groupBy(x => x.getControllerName())
  val baseAddress: String = apiSpecification.getBaseAddress
  val paths: util.LinkedList[SimplifiedPathSpec] = util.LinkedList[SimplifiedPathSpec](
    apiSpecification
      .getPathSpecifications
      .map(x => SimplifiedPathSpecMapper.mapFromPathSpec(x, casingType))
      .asJava)
  val uniqueControllers = pathControllerMap.
  val controllerPaths: util.LinkedList[SimplifiedPathSpec]
}
