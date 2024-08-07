package com.jfast
package models.generators.csharp

import models.generators.python.PythonClassSpec
import models.openapi.ClassSpecification
import utilities.{DependencySorter, ListSorter}

import java.util
import scala.jdk.CollectionConverters.*

extension (classSpecifications: List[ClassSpecification])
  def getCSharpClassSpecs: util.LinkedList[CSharpClassSpec] = {
    val res = for (i <- classSpecifications) yield CSharpClassSpec(i)
    util.LinkedList[CSharpClassSpec](res.asJava)
  }