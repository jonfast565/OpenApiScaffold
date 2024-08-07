package com.jfast
package utilities

object DependencySorter {
  def sortDependencies(dependencies: List[(String, List[String])]): List[String] = {
    // Create a mapping of each element to its dependencies
    val dependencyMap = dependencies.toMap

    // List to store sorted elements
    val sortedList = collection.mutable.ListBuffer[String]()

    // Define DFS function
    def dfs(element: String, visited: collection.mutable.Set[String]): Unit = {
      if (!visited.contains(element)) {
        visited.add(element)
        dependencyMap.getOrElse(element, List.empty).foreach(dep => dfs(dep, visited))
        sortedList.prepend(element)
      }
    }

    // Perform DFS for each element
    val visited = collection.mutable.Set[String]()
    while (visited.size < dependencies.size) {
      dependencies.foreach { case (element, _) =>
        if (!visited.contains(element)) {
          dfs(element, visited)
        }
      }
    }

    sortedList.toList
  }
}
