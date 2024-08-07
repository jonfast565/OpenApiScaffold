package com.jfast
package models.generators.python

import models.openapi.ClassSpecification

import utilities.{DependencySorter, ListSorter}

import java.util
import scala.jdk.CollectionConverters.*

extension (classSpecifications: List[ClassSpecification])
  def getPythonClassSpec: util.LinkedList[PythonClassSpec] = {
    val res = for (i <- classSpecifications) yield PythonClassSpec(i)
    val pythonClasses = util.LinkedList[PythonClassSpec](res.asJava)
    val dependencyList = classSpecifications.map(x => (x.name, x.properties.map(y => y.`type`.customTypeName).filter(x => x != null).filter(_.nonEmpty)))
    val sortedClasses = DependencySorter.sortDependencies(dependencyList).reverse
    val sortedPythonClasses = ListSorter.sortByStringValue(pythonClasses.asScala.toList, sortedClasses, x => x.name)
    util.LinkedList[PythonClassSpec](sortedPythonClasses.asJava)
  }