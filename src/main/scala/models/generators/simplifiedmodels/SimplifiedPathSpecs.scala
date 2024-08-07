package com.jfast
package models.generators.simplifiedmodels

import models.openapi.ApiSpecification

import com.jfast.utilities.CasingType

import java.util
import java.util.LinkedList
import scala.jdk.CollectionConverters.*

class SimplifiedPathSpecs(apiSpecification: ApiSpecification, casingType: CasingType) {
  val baseAddress: String = apiSpecification.getBaseAddress
  val paths: util.LinkedList[SimplifiedPathSpec] = util.LinkedList[SimplifiedPathSpec](
    apiSpecification
      .getPathSpecifications
      .map(x => SimplifiedPathSpecMapper.mapFromPathSpec(x, casingType))
      .asJava)
}
