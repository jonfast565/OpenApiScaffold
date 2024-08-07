package com.jfast
package models.generators.csharp

import models.generators.simplifiedmodels.SimplifiedType
import models.openapi.{AggregateType, ClassSpecification, FieldType, PropertySpecification}

import com.jfast.utilities.GeneralUtilities

import java.util

class CSharpClassSpec(classSpecification: ClassSpecification) {
  val name : String = classSpecification.name
  var properties : util.LinkedList[SimplifiedType] = util.LinkedList[SimplifiedType]()
  for (prop <- classSpecification.properties) {
    val aggString: String = CSharpTypeMapper.getPropertyFieldType(prop.`type`)
    val name = GeneralUtilities.snakeToPascal(prop.name)
    properties.add(SimplifiedType(name, aggString))
  }
}
