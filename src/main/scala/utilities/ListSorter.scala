package com.jfast
package utilities

object ListSorter {
  def sortByStringValue[T](list: List[T], stringValues: List[String], extractor: T => String): List[T] = {
    // Create a map of string values to their indices in the reference list
    val indexMap = stringValues.zipWithIndex.toMap

    // Sort the list based on the indices of the extracted string values in the reference list
    list.sortBy(element => indexMap.getOrElse(extractor(element), Int.MaxValue))
  }
}