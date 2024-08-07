package com.jfast

import com.jfast.utilities.DependencySorter
import org.scalatest.flatspec.AnyFlatSpec

class UtilitiesTests extends AnyFlatSpec {
  private val dependencies = List(
    ("A", List("B", "C")),
    ("B", List("D")),
    ("C", List()),
    ("D", List("E")),
    ("E", List())
  )

  "Dependency test" should "sort dependencies into correct order" in {
    var result = DependencySorter.sortDependencies(dependencies)
    println(result)
  }
}
