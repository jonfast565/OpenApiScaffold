package com.jfast
package models.generators.python

import models.openapi.ApiSpecification

import java.util

class PythonClasses(apiSpecification: ApiSpecification) {
  val classes: util.LinkedList[PythonClassSpec] = apiSpecification.getClassSpecifications.getPythonClassSpec
}
