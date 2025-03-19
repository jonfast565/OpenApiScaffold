package com.jfast
package utilities

import scala.util.control.Breaks._

object ControllerUtilities {
  def getControllerName(path: String): String = {
    val segments = path.split("/").filter(_.nonEmpty)
    var result = ""
    breakable {
      for (segment <- segments) {
        if (segment.equalsIgnoreCase("api") || segment.matches("(?i)^v[1-9]\\d*$")) {
        } else if (segment.startsWith("{") && segment.endsWith("}")) {
          throw new IllegalArgumentException("Controller name cannot be a path variable.")
        } else {
          result = segment
          break
        }
      }
    }
    result
  }
}
