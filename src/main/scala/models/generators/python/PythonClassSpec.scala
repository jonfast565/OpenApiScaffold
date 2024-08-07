package com.jfast
package models.generators.python

import models.openapi.{AggregateType, ClassSpecification, FieldType, PropertySpecification}
import models.generators.simplifiedmodels.SimplifiedType

import java.util

class PythonClassSpec(classSpecification: ClassSpecification) {
    val name : String = classSpecification.name
    var properties : util.LinkedList[SimplifiedType] = util.LinkedList[SimplifiedType]()
    for (prop <- classSpecification.properties) {
      val aggString: String = PythonTypeMapper.getPropertyFieldType(prop.`type`)
      properties.add(SimplifiedType(prop.name, aggString))
    }
}