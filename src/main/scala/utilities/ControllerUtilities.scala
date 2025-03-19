package com.jfast
package utilities

object ControllerUtilities {
  def getControllerName(path: String): String = {
    val segments = path.split("/").filter(_.nonEmpty)
    segments.lastOption.getOrElse("")
  }
}
