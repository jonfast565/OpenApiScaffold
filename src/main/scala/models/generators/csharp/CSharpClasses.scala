package com.jfast
package models.generators.csharp

import models.openapi.ApiSpecification

import java.util

class CSharpClasses(apiSpecification: ApiSpecification) {
  val classes: util.LinkedList[CSharpClassSpec] = apiSpecification.getClassSpecifications.getCSharpClassSpecs
}